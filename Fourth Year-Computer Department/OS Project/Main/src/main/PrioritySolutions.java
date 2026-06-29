package main;

import java.util.*;

class Resource {
    private boolean locked = false;
    public Process owner = null;

    public boolean isLocked() { return locked; }
    public void lock(Process p) { locked = true; owner = p; }
    public void unlock() { locked = false; owner = null; }
}

class ProcessWithResource extends Process {
    public Resource res;

    public ProcessWithResource(String pid, int arrivalTime, int burstTime, int priority, Resource res) {
        super(pid, arrivalTime, burstTime, priority);
        this.res = res;
    }
}

class GanttEntry {
    public String pid;
    public int start, end;

    public GanttEntry(String pid, int start, int end) {
        this.pid = pid;
        this.start = start;
        this.end = end;
    }

    public static void printGanttChart(List<GanttEntry> chart) {
        System.out.println("Gantt Chart:");
        for (GanttEntry e : chart) {
            System.out.print("| " + e.pid + "(" + e.start + "-" + e.end + ") ");
        }
        System.out.println("|");
    }
}

public class PrioritySolutions {

    static int contextSwitchTime = 1;
    static int agingThreshold = 5;

    public static void preemptivePriorityScheduling(List<Process> processes, int quantum) {
        int time = 0, completed = 0;
        int n = processes.size();
        List<GanttEntry> gantt = new ArrayList<>();
        Process lastProcess = null;

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                Comparator.comparingInt((Process p) -> p.currentPriority)
                        .thenComparingInt(p -> p.arrivalTime)
        );

        List<Process> processList = new ArrayList<>(processes);
        for (Process p : processList) {
            p.remainingTime = p.burstTime;
            p.startTime = -1;
            p.completionTime = -1;
            p.waitingTime = p.turnaroundTime = p.responseTime = 0;
            p.currentPriority = p.basePriority;
            p.waitingTimeCounter = 0;
        }

        processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int nextProcessIdx = 0;

        while (completed < n) {
            while (nextProcessIdx < n && processList.get(nextProcessIdx).arrivalTime <= time) {
                Process p = processList.get(nextProcessIdx);
                readyQueue.add(p);
                nextProcessIdx++;
            }

            if (readyQueue.isEmpty()) {
                if (nextProcessIdx < n) {
                    int nextArrival = processList.get(nextProcessIdx).arrivalTime;
                    gantt.add(new GanttEntry("IDLE", time, nextArrival));
                    time = nextArrival;
                    lastProcess = null;
                    continue;
                } else break;
            }

            applyPriorityInheritance(readyQueue, time);

            applyAging(readyQueue, time, lastProcess);

            Process cur = readyQueue.poll();

            if (lastProcess != null && lastProcess != cur && lastProcess.remainingTime > 0) {
                gantt.add(new GanttEntry("CS", time, time + contextSwitchTime));
                time += contextSwitchTime;
                while (nextProcessIdx < n && processList.get(nextProcessIdx).arrivalTime <= time) {
                    Process p = processList.get(nextProcessIdx);
                    readyQueue.add(p);
                    nextProcessIdx++;
                }
            }

            if (cur.startTime == -1) {
                cur.startTime = time;
                cur.responseTime = cur.startTime - cur.arrivalTime;
            }

            int execTime = Math.min(quantum, cur.remainingTime);
            gantt.add(new GanttEntry(cur.pid, time, time + execTime));
            cur.remainingTime -= execTime;
            time += execTime;

            if (cur.remainingTime == 0) {
                cur.completionTime = time;
                cur.turnaroundTime = cur.completionTime - cur.arrivalTime;
                cur.waitingTime = cur.turnaroundTime - cur.burstTime;
                completed++;

                if (cur instanceof ProcessWithResource pwr && pwr.res != null && pwr.res.owner == cur) {
                    pwr.res.unlock();
                }
            } else {
                readyQueue.add(cur);
            }

            lastProcess = cur;
        }

        GanttEntry.printGanttChart(gantt);
        printProcessTable(processList);
    }

    private static void applyPriorityInheritance(Queue<Process> readyQueue, int time) {
        List<Process> processes = new ArrayList<>(readyQueue);
        for (Process p : processes) {
            if (p instanceof ProcessWithResource pwr) {
                if (pwr.res != null && pwr.res.isLocked() && pwr.res.owner != null) {
                    Process owner = pwr.res.owner;
                    if (processes.contains(owner) && owner.currentPriority > p.currentPriority) {
                        int oldPriority = owner.currentPriority;
                        owner.currentPriority = p.currentPriority;
                        System.out.println("[PI] Time " + time + ": " + owner.pid +
                                " oldPriority=" + oldPriority +
                                ", newPriority=" + owner.currentPriority +
                                ", due to " + p.pid);
                    }
                }
            }
        }
    }

    private static void applyAging(Collection<Process> processes, int currentTime, Process lastProcess) {
        for (Process p : processes) {
            if (p != lastProcess) {
                p.waitingTimeCounter++;
                if (p.waitingTimeCounter >= agingThreshold && p.currentPriority > 0) {
                    int oldPriority = p.currentPriority;
                    p.currentPriority--;
                    p.waitingTimeCounter = 0;
                    System.out.println("[Aging] Time " + currentTime + ": " + p.pid +
                            " oldPriority=" + oldPriority +
                            ", newPriority=" + p.currentPriority);
                }
            }
        }
    }

    private static void printProcessTable(List<Process> processes) {
        System.out.println("Process Table:");
        System.out.printf("%-6s %-7s %-6s %-7s %-11s %-11s %-9s %-9s %-9s %-9s\n",
                "PID","Arrival","Burst","Start","Completion","Turnaround","Waiting","Response","BasePrio","CurPrio");
        for (Process p : processes) {
            System.out.printf("%-6s %-7d %-6d %-7d %-11d %-11d %-9d %-9d %-9d %-9d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.startTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime, p.responseTime,
                    p.basePriority, p.currentPriority
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Aging Test ===");
  List<Process> processes = TestCaseFinal.getPrioritySchedulerStarvationCase();
        preemptivePriorityScheduling(processes, 4);

  List<ProcessWithResource> processesFinal = TestCaseFinal.getPrioritySchedulerInversionCase();
  
        preemptivePriorityScheduling(new ArrayList<>(processesFinal), 4);
        System.out.println();
        System.out.println("=== Aging Test (MLFQ) ===");
          List<Process> processesMLFQ = TestCaseFinal.getMLFStarvationCase();
          MLFQScheduler.run(processesMLFQ);
          MLFQScheduler.printResults();

        
    }
}
