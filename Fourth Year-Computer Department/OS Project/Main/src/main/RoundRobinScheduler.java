package main;


import java.util.*;

class RoundRobinScheduler {

    static int contextSwitchTime = 1;

    public static List<GanttEntry> roundRobin(List<Process> processes, int quantum) {
        int n = processes.size();
        int time = 0;

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        Queue<Process> readyQueue = new LinkedList<>();
        boolean[] inQueue = new boolean[n];

        
        int completed = 0;
        int totalContextSwitches = 0;
        Process lastProcess = null;

        List<GanttEntry> ganttChart = new ArrayList<>();

        while (completed < n) {
            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrivalTime <= time && !inQueue[i] && p.remainingTime > 0) {
                    readyQueue.add(p);
                    inQueue[i] = true;
                }
            }

            Process current = readyQueue.poll();

            if (current == null) {
                int nextArrivalTime = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.remainingTime > 0 && p.arrivalTime > time) {
                        nextArrivalTime = Math.min(nextArrivalTime, p.arrivalTime);
                    }
                }
                if (nextArrivalTime != Integer.MAX_VALUE) {
                    ganttChart.add(new GanttEntry("IDLE", time, nextArrivalTime));
                    time = nextArrivalTime;
                } else break;

                lastProcess = null;
                continue;
            }

            if (lastProcess != null && lastProcess != current) {
                ganttChart.add(new GanttEntry("CS", time, time + contextSwitchTime));
                time += contextSwitchTime;
                totalContextSwitches++;
                
                for (int i = 0; i < n; i++) {
                    Process p = processes.get(i);
                    if (p.arrivalTime <= time && !inQueue[i] && p.remainingTime > 0) {
                        readyQueue.add(p);
                        inQueue[i] = true;
                    }
                }
            }

            if (current.startTime == -1) {
                current.startTime = time;
                current.responseTime = current.startTime - current.arrivalTime;
            }

            int execTime = Math.min(quantum, current.remainingTime);
            int executionEnd = time + execTime;
            
            while (time < executionEnd) {
                for (int i = 0; i < n; i++) {
                    Process p = processes.get(i);
                    if (p.arrivalTime > time && p.arrivalTime <= executionEnd && !inQueue[i] && p.remainingTime > 0) {
                        readyQueue.add(p);
                        inQueue[i] = true;
                    }
                }
                time = executionEnd;
            }
            
            ganttChart.add(new GanttEntry(current.pid, time - execTime, time));
            current.remainingTime -= execTime;

            if (current.remainingTime > 0) {
                readyQueue.add(current);
            } else {
                current.completionTime = time;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                completed++;
            }

            lastProcess = current;
        }

        GanttEntry.printGanttChart(ganttChart);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Round Robin 's Metrix Output: ");

        Helper.printMetrics(processes, time, totalContextSwitches);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Process Table Output: ");
        Helper.printProcessTable(processes);

        return ganttChart;
    }
}