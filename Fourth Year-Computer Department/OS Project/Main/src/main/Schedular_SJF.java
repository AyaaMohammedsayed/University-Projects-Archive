package main;

import java.util.*;
/**
 *
 * @author Nadia
 */

public class Schedular_SJF {
    public static double avgWait = 0;
    public static double avgTurn = 0;
    public static double avgResponse = 0;
    public static double cpuUtil = 0;
    public static double throughput = 0;
    
    public static void runSJF(List<Process> processes) {

        int n = processes.size();
        int completed = 0;
        int time = 0;
        int idleTime = 0;
        int CONTEXT_SWITCH = 1; 
        boolean[] isCompleted = new boolean[n];
        Process prev = null;

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));



        List<GanttEntry> ganttEntries = new ArrayList<>();


        System.out.printf(
            "%-5s %-12s %-10s %-10s %-14s %-14s %-14s %-14s\n",
            "PID", "arrivalTime", "burstTime", "startTime",
            "completionTime", "waitingTime", "turnaroundTime", "responseTime"
        );

        while (completed != n) {

            int idx = -1;
            int minburstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrivalTime <= time && !isCompleted[i]) {
                    if (p.burstTime < minburstTime) {
                        minburstTime = p.burstTime;
                        idx = i;
                    } else if (p.burstTime == minburstTime) {
                        if (p.arrivalTime < processes.get(idx).arrivalTime) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                Process p = processes.get(idx);

                if (time < p.arrivalTime) {
                    idleTime += p.arrivalTime - time;
                    time = p.arrivalTime;
                }

                if (prev != null && prev.pid != p.pid) {
                    time += CONTEXT_SWITCH;
                    idleTime += CONTEXT_SWITCH;
                }

                p.startTime = time;
                p.completionTime = time + p.burstTime;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.startTime - p.arrivalTime;
                p.responseTime = p.waitingTime;
                time = p.completionTime;
                isCompleted[idx] = true;
                completed++;

                System.out.printf(
                    "%-5s %-12d %-10d %-10d %-14d %-14d %-14d %-14d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.startTime,
                    p.completionTime, p.waitingTime,
                    p.turnaroundTime, p.responseTime
                );

                prev = p;
                ganttEntries.add(new GanttEntry(p.pid, p.startTime, p.completionTime));


            } else {
                time++;
                idleTime++;
            }
        }

        int totalburstTime = 0, totalWait = 0, totalTurn = 0, totalResponse = 0;
        double fairness = 0;

        for (Process p : processes) {
            totalburstTime += p.burstTime;
            totalWait += p.waitingTime;
            totalTurn += p.turnaroundTime;
            totalResponse += p.responseTime;
        }

        avgWait = (double) totalWait / n;
        avgTurn = (double) totalTurn / n;
        avgResponse = (double) totalResponse / n;
        cpuUtil = (double) totalburstTime / (totalburstTime + idleTime) * 100;
        throughput = (double) n / time;

        for (Process p : processes)
            fairness += Math.pow(p.waitingTime - avgWait, 2);
            fairness /= processes.size(); // Variance

        System.out.println();
        GanttEntry.printGanttChart(ganttEntries);
        System.out.println();    
        System.out.println("Average Waiting Time        = " + String.format("%.3f", avgWait));
        System.out.println("Average Turnaround Time     = " + String.format("%.3f", avgTurn));
        System.out.println("Average Response Time       = " + String.format("%.3f", avgResponse));
        System.out.println("CPU Utilization             = " + String.format("%.3f", cpuUtil) + "%");
        System.out.println("Throughput (proc/unit time) = " + String.format("%.3f", throughput));
        System.out.println("Fairness (Waiting Variance) = " + String.format("%.3f", fairness));
    }
}
