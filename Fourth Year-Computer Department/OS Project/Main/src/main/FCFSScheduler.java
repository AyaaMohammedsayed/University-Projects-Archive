package main;

import java.util.*;
/**
 *
 * @author Nadia
 */
public class FCFSScheduler {
    
    public static double avgWait = 0;
    public static double avgTurn = 0;
    public static double avgResponse = 0;
    public static double cpuUtil = 0;
    public static double throughput = 0;
    
    
    
    public static void fcfsWithMetrics(List<Process> processes) {

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int time = 0;
        int totalburstTime = 0;
        int totalWait = 0;
        int totalTurn = 0;
        int totalResponse = 0;
        int idleTime = 0;
        int CONTEXT_SWITCH = 1;

        System.out.printf(
            "%-5s %-12s %-10s %-10s %-14s %-14s %-14s %-14s\n",
            "PID", "arrivalTime", "burstTime", "startTime",
            "completionTime", "waitingTime", "turnaroundTime", "responseTime"
        );
        List<GanttEntry> ganttEntries = new ArrayList<>();


        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);

            if (time < p.arrivalTime) {
                idleTime += (p.arrivalTime - time);
                time = p.arrivalTime;
            }

            if (i > 0) {
                time += CONTEXT_SWITCH;
                idleTime += CONTEXT_SWITCH;
            }

            p.startTime = time;

            p.completionTime = time + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.startTime - p.arrivalTime;
            p.responseTime = p.waitingTime;

            time = p.completionTime;
            totalburstTime += p.burstTime;
            totalWait += p.waitingTime;
            totalTurn += p.turnaroundTime;
            totalResponse += p.responseTime;

            System.out.printf(
                "%-5s %-12d %-10d %-10d %-14d %-14d %-14d %-14d\n",
                p.pid, p.arrivalTime, p.burstTime, p.startTime,
                p.completionTime, p.waitingTime,
                p.turnaroundTime, p.responseTime
            );
            ganttEntries.add(new GanttEntry(p.pid, p.startTime, p.completionTime));

        }

        avgWait = (double) totalWait / processes.size();
        avgTurn = (double) totalTurn / processes.size();
        avgResponse = (double) totalResponse / processes.size();
        cpuUtil = (double) totalburstTime / (totalburstTime + idleTime) * 100;
        throughput = (double) processes.size() / time;

        double fairness = 0;
        for (Process p : processes)
            fairness += Math.pow(p.waitingTime - avgWait, 2);

        fairness /= processes.size();

        System.out.println();
        GanttEntry.printGanttChart(ganttEntries);
        System.out.println();
        System.out.println("Average Waiting Time = " + String.format("%.3f", avgWait));
        System.out.println("Average Turnaround Time = " + String.format("%.3f", avgTurn));
        System.out.println("Average Response Time = " + String.format("%.3f", avgResponse));
        System.out.println("CPU Utilization = " + String.format("%.3f", cpuUtil) + "%");
        System.out.println("Throughput = " + String.format("%.3f", throughput) + " processes/unit");
        System.out.println("Fairness (Waiting Time Variance) = " + String.format("%.3f", fairness));
    }
}
