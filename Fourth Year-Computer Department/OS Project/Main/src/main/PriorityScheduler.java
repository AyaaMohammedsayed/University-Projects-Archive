package main;


import java.util.*;

public class PriorityScheduler {

    static int contextSwitchTime = 1;
    static int quantum = 4;
    public static double avgWaiting = 0;
    public static double avgTurnaround = 0;
    public static double avgResponse = 0;
    public static double cpuUtil = 0;
    public static double throughput = 0;
    public static void priorityScheduling(List<Process> processes, boolean preemptive) {
        if (preemptive) {
            System.out.println("\n=======================================================================");
            System.out.println("Priority (Preemptive) Scheduling:");
            System.out.println("=======================================================================");
            priorityPreemptiveWithRoundRobin(processes);
        } else {
            System.out.println("\n=======================================================================");
            System.out.println("Priority (Non-Preemptive) Scheduling:");
            System.out.println("=======================================================================");
            priorityNonPreemptive(processes);
        }
    }

    private static void priorityPreemptiveWithRoundRobin(List<Process> processes) {

        int time = 0;
        int completed = 0;
        int n = processes.size();
        int totalContextSwitches = 0;

        List<GanttEntry> gantt = new ArrayList<>();
        Process lastProcess = null;

        for (Process p : processes) {
            p.remainingTime = p.burstTime;
            p.startTime = -1;
            p.completionTime = -1;
            p.waitingTime = p.turnaroundTime = p.responseTime = 0;
            p.currentPriority = p.basePriority;
            p.waitingTimeCounter = 0;
        }

        while (completed < n) {

            List<Process> available = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrivalTime <= time && p.remainingTime > 0) {
                    available.add(p);
                }
            }

            if (available.isEmpty()) {
                int nextArr = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.remainingTime > 0 && p.arrivalTime > time)
                        nextArr = Math.min(nextArr, p.arrivalTime);
                }
                if (nextArr == Integer.MAX_VALUE) break; // nothing more
                gantt.add(new GanttEntry("IDLE", time, nextArr));
                time = nextArr;
                lastProcess = null;
                continue;
            }

            available.sort(Comparator.comparingInt(p -> p.currentPriority));
            int highest = available.get(0).currentPriority;

            List<Process> samePriority = new ArrayList<>();
            for (Process p : available) {
                if (p.currentPriority == highest)
                    samePriority.add(p);
            }

            if (samePriority.size() == 1) {
                Process cur = samePriority.get(0);

                if (lastProcess != null && lastProcess != cur) {
                    gantt.add(new GanttEntry("CS", time, time + contextSwitchTime));
                    time += contextSwitchTime;
                    totalContextSwitches++;
                }

                if (cur.startTime == -1) {
                    cur.startTime = time;
                    cur.responseTime = cur.startTime - cur.arrivalTime;
                }

                gantt.add(new GanttEntry(cur.pid, time, time + 1));
                cur.remainingTime -= 1;
                time += 1;

                for (Process p : processes) {
                    if (p != cur && p.arrivalTime <= time && p.remainingTime > 0) {
                        p.waitingTimeCounter++;
                    }
                }

                if (cur.remainingTime == 0) {
                    cur.completionTime = time;
                    cur.turnaroundTime = cur.completionTime - cur.arrivalTime;
                    cur.waitingTime = cur.turnaroundTime - cur.burstTime;
                    completed++;
                }

                lastProcess = cur;
            }

            else {
                Queue<Process> q = new LinkedList<>();
                samePriority.sort(Comparator.comparingInt((Process p) -> p.arrivalTime).thenComparing(p -> p.pid));
                for (Process p : samePriority) q.add(p);

                while (!q.isEmpty()) {
                    Process cur = q.poll();

                    List<Process> check = new ArrayList<>();
                    for (Process p : processes)
                        if (p.arrivalTime <= time && p.remainingTime > 0)
                            check.add(p);
                    check.sort(Comparator.comparingInt(p -> p.currentPriority));
                    if (!check.isEmpty() && check.get(0).currentPriority < highest) {
                        break;
                    }

                    if (lastProcess != null && lastProcess != cur) {
                        gantt.add(new GanttEntry("CS", time, time + contextSwitchTime));
                        time += contextSwitchTime;
                        totalContextSwitches++;
                    }

                    if (cur.startTime == -1) {
                        cur.startTime = time;
                        cur.responseTime = cur.startTime - cur.arrivalTime;
                    }

                    int t = Math.min(quantum, cur.remainingTime);

                    gantt.add(new GanttEntry(cur.pid, time, time + t));
                    cur.remainingTime -= t;
                    time += t;

                    for (Process p : processes) {
                        if (p.arrivalTime <= time && p.remainingTime > 0 && p.currentPriority == highest && !q.contains(p) && p != cur) {
                            // If it's not already in queue, add it
                            q.add(p);
                        }
                    }

                    if (cur.remainingTime > 0) {
                        q.add(cur);
                    } else {
                        cur.completionTime = time;
                        cur.turnaroundTime = cur.completionTime - cur.arrivalTime;
                        cur.waitingTime = cur.turnaroundTime - cur.burstTime;
                        completed++;
                    }

                    lastProcess = cur;

                    check.clear();
                    for (Process p : processes)
                        if (p.arrivalTime <= time && p.remainingTime > 0)
                            check.add(p);
                    check.sort(Comparator.comparingInt(p -> p.currentPriority));
                    if (!check.isEmpty() && check.get(0).currentPriority < highest) {
                        break;
                    }
                }
            }
        }

        GanttEntry.printGanttChart(gantt);
        System.out.println("-----------------------------------------------------------------------");
        computeMetricsFromGantt(processes, gantt, time);
        System.out.println("-----------------------------------------------------------------------");
        printProcessTable(processes);
    }

    private static void computeMetricsFromGantt(List<Process> processes, List<GanttEntry> gantt, int totalTime) {
        Map<String, Integer> firstStart = new HashMap<>();    
        Map<String, Integer> lastEnd = new HashMap<>();     
        int cpuBusy = 0;
        int contextSwitches = 0;
        Set<String> completedP = new HashSet<>();

        for (GanttEntry e : gantt) {
            if (e.pid.equals("CS")) {
                contextSwitches++;
            } else if (e.pid.equals("IDLE")) {
              
            } else {
                cpuBusy += (e.end - e.start);

                if (!firstStart.containsKey(e.pid)) {
                    firstStart.put(e.pid, e.start);
                }
                lastEnd.put(e.pid, e.end);
                completedP.add(e.pid);
            }
        }

        int n = processes.size();
        double totalTurnaround = 0;
        double totalWaiting = 0;
        double totalResponse = 0;

        Map<String, Integer> burstMap = new HashMap<>();
        Map<String, Integer> arrivalMap = new HashMap<>();
        for (Process p : processes) {
            burstMap.put(p.pid, p.burstTime);
            arrivalMap.put(p.pid, p.arrivalTime);
        }

        for (Process p : processes) {
            String pid = p.pid;
            if (!lastEnd.containsKey(pid)) {
                // Not completed according to Gantt -> leave fields as -1 or compute partial if needed
                p.completionTime = -1;
                p.turnaroundTime = -1;
                p.waitingTime = -1;
                p.startTime = firstStart.getOrDefault(pid, -1);
                p.responseTime = p.startTime == -1 ? -1 : p.startTime - p.arrivalTime;
                continue;
            }
            int completion = lastEnd.get(pid);
            int arrival = arrivalMap.get(pid);
            int burst = burstMap.get(pid);

            int turnaround = completion - arrival;
            int waiting = turnaround - burst;
            int response = firstStart.containsKey(pid) ? (firstStart.get(pid) - arrival) : -1;

            totalTurnaround += turnaround;
            totalWaiting += waiting;
            if (response >= 0) totalResponse += response;
            else totalResponse += 0;

            p.completionTime = completion;
            p.turnaroundTime = turnaround;
            p.waitingTime = waiting;
            p.startTime = firstStart.getOrDefault(pid, -1);
            p.responseTime = response;
        }

        avgTurnaround = totalTurnaround / n;
        avgWaiting = totalWaiting / n;
        avgResponse = totalResponse / n;
        cpuUtil = totalTime > 0 ? (100.0 * cpuBusy / totalTime) : 0.0;
        throughput = totalTime > 0 ? ((double) completedP.size() / totalTime) : 0.0;

        System.out.println("Metrics (calculated from Gantt): ");
        System.out.printf("Total time: %d\n", totalTime);
        System.out.printf("Context switches: %d\n", contextSwitches);
        System.out.printf("CPU busy time: %d\n", cpuBusy);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtil);
        System.out.printf("Throughput: %.4f process/unit-time\n", throughput);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaround);
        System.out.printf("Average Waiting Time: %.2f\n", avgWaiting);
        System.out.printf("Average Response Time: %.2f\n", avgResponse);
    }

    private static void printProcessTable(List<Process> processes) {
        System.out.println("Process Table:");
        System.out.printf("%-6s %-7s %-6s %-7s %-11s %-11s %-9s %-9s\n",
                "PID", "Arrival", "Burst", "Start", "Completion", "Turnaround", "Waiting", "Response");
        for (Process p : processes) {
            System.out.printf("%-6s %-7d %-6d %-7d %-11d %-11d %-9d %-9d\n",
                    p.pid,
                    p.arrivalTime,
                    p.burstTime,
                    p.startTime,
                    p.completionTime,
                    p.turnaroundTime,
                    p.waitingTime,
                    p.responseTime
            );
        }
    }

    private static void priorityNonPreemptive(List<Process> processes) {
        int n = processes.size();
        int time = 0;
        List<GanttEntry> ganttChart = new ArrayList<>();
        int completed = 0;
        int totalContextSwitches = 0;

        for (Process p : processes) {
            p.remainingTime = p.burstTime;
            p.startTime = -1;
            p.completionTime = -1;
            p.waitingTime = p.turnaroundTime = p.responseTime = 0;
            p.currentPriority = p.basePriority;
            p.waitingTimeCounter = 0;
        }

        Process lastProcess = null;

        while (completed < n) {
            Process current = null;
            int highestPriority = Integer.MAX_VALUE;
            int earliestArrival = Integer.MAX_VALUE;

            for (Process p : processes) {
                if (p.arrivalTime <= time && p.remainingTime > 0) {
                    if (p.currentPriority < highestPriority) {
                        highestPriority = p.currentPriority;
                        current = p;
                        earliestArrival = p.arrivalTime;
                    } else if (p.currentPriority == highestPriority && p.arrivalTime < earliestArrival) {
                        current = p;
                        earliestArrival = p.arrivalTime;
                    }
                }
            }

            if (current == null) {
                int nextArrivalTime = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.remainingTime > 0 && p.arrivalTime > time) {
                        nextArrivalTime = Math.min(nextArrivalTime, p.arrivalTime);
                    }
                }
                if (nextArrivalTime == Integer.MAX_VALUE) break;
                ganttChart.add(new GanttEntry("IDLE", time, nextArrivalTime));
                time = nextArrivalTime;
                lastProcess = null;
                continue;
            }

            if (lastProcess != null && lastProcess != current) {
                ganttChart.add(new GanttEntry("CS", time, time + contextSwitchTime));
                time += contextSwitchTime;
                totalContextSwitches++;
            }

            if (current.startTime == -1) {
                current.startTime = time;
                current.responseTime = current.startTime - current.arrivalTime;
            }

            int execTime = current.remainingTime;
            ganttChart.add(new GanttEntry(current.pid, time, time + execTime));
            current.remainingTime = 0;
            time += execTime;

            current.completionTime = time;
            current.turnaroundTime = current.completionTime - current.arrivalTime;
            current.waitingTime = current.turnaroundTime - current.burstTime;
            completed++;

            lastProcess = current;
        }

        GanttEntry.printGanttChart(ganttChart);
        System.out.println("-----------------------------------------------------------------------");
        computeMetricsFromGantt(processes, ganttChart, /*totalTime*/ determineTotalTimeFromGantt(ganttChart));
        System.out.println("-----------------------------------------------------------------------");
        printProcessTable(processes);
    }

    private static int determineTotalTimeFromGantt(List<GanttEntry> gantt) {
        int t = 0;
        for (GanttEntry e : gantt) {
            if (e.end > t) t = e.end;
        }
        return t;
    }

}
