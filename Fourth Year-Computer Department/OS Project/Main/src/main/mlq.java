package main;

import java.util.*;

public class mlq {
    public static double avgWait = 0;
    public static double avgTurn = 0;
    public static double avgResponse = 0;
    public static double cpuUtil = 0;
    public static double throughput = 0;
    static class Process {
        String id;
        int arrival;
        int burst;
        int remaining;
        int queue; 

        int startTime = -1;
        int finishTime = -1;
        int responseTime = -1;
        int waitingTime = 0;
        int turnaround = -1;

        public Process(String id, int arrival, int burst, int queue) {
            this.id = id;
            this.arrival = arrival;
            this.burst = burst;
            this.remaining = burst;
            this.queue = queue;
        }

        @Override
        public String toString() {
            return id + "(A=" + arrival + ",B=" + burst + ",q=" + queue + ",r=" + remaining + ")";
        }
    }

    public static void simulateMLQ(List<Process> allProcesses, int rrQuantum) {
        List<Process> q0_SRT = new LinkedList<>();
        List<Process> q1_SJF = new LinkedList<>();
        List<Process> q2_RR = new LinkedList<>();
        List<Process> q3_FCFS = new LinkedList<>();

        int time = 0;
        int quantum = rrQuantum;
        Process currentRR = null;
        int rrCounter = 0;
        Process lastRunning = null;

        List<String> gantt = new ArrayList<>();
        int cpuBusyTime = 0;

        System.out.println("=== MLQ Simulation start ===\n");

        while (true) {
            if (allFinished(allProcesses)) break;

            for (Process p : allProcesses) {
                if (p.arrival == time) {
                    enqueueByQueue(p, q0_SRT, q1_SJF, q2_RR, q3_FCFS);
                    System.out.println("Time " + time + ": Arrival -> " + p);
                }
            }

            Process running = null;
            String algo = "IDLE";

            if (!q0_SRT.isEmpty()) {
                running = getShortestRemaining(q0_SRT);
                algo = "SRT";
            } else if (!q1_SJF.isEmpty()) {
                running = getShortestJob(q1_SJF);
                algo = "SJF";
            } else if (!q2_RR.isEmpty() || currentRR != null) {
                if (currentRR == null) {
                    currentRR = q2_RR.remove(0);
                    rrCounter = 0;
                }
                running = currentRR;
                algo = "RR";
            } else if (!q3_FCFS.isEmpty()) {
                running = q3_FCFS.get(0);
                algo = "FCFS";
            } else {
                break; 
            }

            if (lastRunning != running && lastRunning != null) {
                time++; 
                gantt.add("CS");
                System.out.println("Time " + time + ": Context switch");
            }
            lastRunning = running;

            if (running.startTime == -1) {
                running.startTime = time;
                running.responseTime = running.startTime - running.arrival;
            }

            System.out.println("Time " + time + " -> Running " + running.id + " [" + algo + "] (rem=" + running.remaining + ")");
            running.remaining--;
            gantt.add(running.id);
            cpuBusyTime++;

            incrementWaitingForQueues(q0_SRT, q1_SJF, q2_RR, q3_FCFS, running);

            if (running.remaining == 0) {
                running.finishTime = time + 1;
                running.turnaround = running.finishTime - running.arrival;
                running.waitingTime = running.turnaround - running.burst;
                System.out.println("Time " + (time + 1) + ": Process " + running.id + " finished. Turnaround=" + running.turnaround + ", Waiting=" + running.waitingTime + ", Response=" + running.responseTime);
                removeProcessFromAllQueues(running, q0_SRT, q1_SJF, q2_RR, q3_FCFS);
                if (running == currentRR) {
                    currentRR = null;
                    rrCounter = 0;
                }
            }
            else if (algo.equals("RR")) {
                rrCounter++;
                if (rrCounter == quantum) {
                    q2_RR.add(currentRR);
                    currentRR = null;
                    rrCounter = 0;
                }
            }

            time++;
        }

        System.out.println("\n=== Simulation finished at time " + time + " ===\n");
        printMetrics(allProcesses, cpuBusyTime, time);
        printGantt(gantt);
    }

    static void enqueueByQueue(Process p, List<Process> q0, List<Process> q1, List<Process> q2, List<Process> q3) {
        switch (p.queue) {
            case 0 -> q0.add(p);
            case 1 -> q1.add(p);
            case 2 -> q2.add(p);
            case 3 -> q3.add(p);
            default -> throw new IllegalArgumentException("Invalid queue: " + p.queue);
        }
    }

    static void incrementWaitingForQueues(List<Process> q0, List<Process> q1, List<Process> q2, List<Process> q3, Process running) {
        for (Process p : q0) if (p != running) p.waitingTime++;
        for (Process p : q1) if (p != running) p.waitingTime++;
        for (Process p : q2) if (p != running) p.waitingTime++;
        for (Process p : q3) if (p != running) p.waitingTime++;
    }

    static void removeProcessFromAllQueues(Process p, List<Process> q0, List<Process> q1, List<Process> q2, List<Process> q3) {
        q0.remove(p); q1.remove(p); q2.remove(p); q3.remove(p);
    }

    static boolean allFinished(List<Process> processes) {
        for (Process p : processes) if (p.remaining > 0) return false;
        return true;
    }

    static Process getShortestRemaining(List<Process> q) {
        Process shortest = null;
        for (Process p : q) if (shortest == null || p.remaining < shortest.remaining) shortest = p;
        return shortest;
    }

    static Process getShortestJob(List<Process> q) {
        Process shortest = null;
        for (Process p : q) if (shortest == null || p.burst < shortest.burst) shortest = p;
        return shortest;
    }

    static void printMetrics(List<Process> processes, int cpuBusyTime, int totalTime) {
        System.out.println("Per-process metrics:");
        System.out.println(String.format("%-4s %-7s %-7s %-7s %-8s %-10s %-10s %-10s", "ID", "Arrival", "Burst", "Start", "Finish", "Waiting", "Turnaround", "Response"));
        for (Process p : processes) {
            System.out.println(String.format("%-4s %-7d %-7d %-7d %-8d %-10d %-10d %-10d",
                    p.id, p.arrival, p.burst, p.startTime, p.finishTime, p.waitingTime, p.turnaround, p.responseTime));
        }

        double totWait = 0, totTurn = 0, totResp = 0;
        for (Process p : processes) { totWait += p.waitingTime; totTurn += p.turnaround; totResp += p.responseTime; }
        avgWait = totWait / processes.size();
        avgTurn = totTurn / processes.size();
        avgResponse = totResp / processes.size();
        System.out.printf("\nAverage waiting = %.2f, Average turnaround = %.2f, Average response = %.2f\n",
                totWait / processes.size(), totTurn / processes.size(), totResp / processes.size());

        cpuUtil = ((double) cpuBusyTime / totalTime) * 100;
        throughput = (double) processes.size() / totalTime;
        System.out.printf("CPU Utilization = %.2f%%, Throughput = %.2f processes/unit time\n", cpuUtil, throughput);
    }

    static void printGantt(List<String> gantt) {
        System.out.println("\nGantt Chart (time units):");
        int n = gantt.size();
        if (n == 0) { System.out.println("[no execution]"); return; }
        StringBuilder line = new StringBuilder();
        StringBuilder times = new StringBuilder();
        int start = 0;
        String cur = gantt.get(0);
        for (int i = 1; i <= n; i++) {
            String s = (i < n) ? gantt.get(i) : null;
            if (s != null && s.equals(cur)) continue;
            line.append("| ").append(cur).append(" ");
            times.append(start).append("    ");
            start = i;
            cur = s;
        }
        times.append(n);
        line.append("|");
        System.out.println(line.toString());
        System.out.println(times.toString());
    }

    public static void run() {
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 6, 0));
        processes.add(new Process("P2", 1, 4, 1));
        processes.add(new Process("P3", 2, 7, 2));
        processes.add(new Process("P4", 3, 1 , 3));
        processes.add(new Process("P5", 5, 4, 3));
        processes.add(new Process("P6", 6, 7, 2));

        simulateMLQ(processes, 2);
    }
}
