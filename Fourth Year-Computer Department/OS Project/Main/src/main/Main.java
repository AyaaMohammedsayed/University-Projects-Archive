package main;
import java.util.*;

public class Main {

    static class AlgorithmStats {
        String name;
        double avgWaiting;
        double avgTurnaround;
        double avgResponse;
        double cpuUtilization;
        double throughput;

        public AlgorithmStats(String name, double avgWaiting, double avgTurnaround, double avgResponse, double cpuUtilization, double throughput) {
            this.name = name;
            this.avgWaiting = avgWaiting;
            this.avgTurnaround = avgTurnaround;
            this.avgResponse = avgResponse;
            this.cpuUtilization = cpuUtilization;
            this.throughput = throughput;
        }
    }

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Implementation Different Process Scheduling Algorithms ");
        System.out.println("-----------------------------------------------------------------------");

        List<Process> processes = TestCaseFinal.getGeneralTestCase();
        int quantum = 4;
        
        List<AlgorithmStats> results = new ArrayList<>();

        String[] algorithms = {"FCFS", "SJF", "RR", "Priority Preemptive","Priority Non-Preemptive", "SRT", "MLFQ", "MLQ"};
        
        for (String algo : algorithms) {
            
            resetProcesses(processes); 
            
            AlgorithmStats currentStats = null;

            switch (algo) {
                case "FCFS":
                    FCFS(processes);
                    
                    currentStats = new AlgorithmStats("FCFS", 
                            FCFSScheduler.avgWait, 
                            FCFSScheduler.avgTurn, 
                            FCFSScheduler.avgResponse, 
                            FCFSScheduler.cpuUtil,
                            FCFSScheduler.throughput);
                    break;

                case "SJF":
                    SJF(processes);
                    currentStats = new AlgorithmStats("SJF", 
                            Schedular_SJF.avgWait, 
                            Schedular_SJF.avgTurn, 
                            Schedular_SJF.avgResponse, 
                            Schedular_SJF.cpuUtil,
                            Schedular_SJF.throughput);
                    break;

                case "RR":
                    RR(processes, quantum);
                    currentStats = new AlgorithmStats("RR", 
                            Helper.avgWait, 
                            Helper.avgTurn, 
                            Helper.avgResponse, 
                            Helper.cpuUtil,
                            Helper.throughput);
                    break;

                case "Priority Preemptive":
                    PriorityP(processes);
                    currentStats = new AlgorithmStats("Priority Preemptive", 
                            PriorityScheduler.avgWaiting, 
                            PriorityScheduler.avgTurnaround, 
                            PriorityScheduler.avgResponse, 
                            PriorityScheduler.cpuUtil,
                            PriorityScheduler.throughput);
                    break;
                    
                case "Priority Non-Preemptive":
                    PriorityNon(processes);
                    currentStats = new AlgorithmStats("Priority Non-Preemptive", 
                            PriorityScheduler.avgWaiting, 
                            PriorityScheduler.avgTurnaround, 
                            PriorityScheduler.avgResponse, 
                            PriorityScheduler.cpuUtil,
                            PriorityScheduler.throughput);
                    break;

                case "SRT":
                    SRT(processes);
                    currentStats = new AlgorithmStats("SRT", 
                            SRTSchedular.avgWaitingTime, 
                            SRTSchedular.avgTurnaroundTime, 
                            SRTSchedular.avgResponseTime, 
                            SRTSchedular.cpuUtilization,
                            SRTSchedular.throughput);
                    break;

                case "MLFQ":
                    MLFQ(processes);
                    currentStats = new AlgorithmStats("MLFQ", 
                            MLFQScheduler.avgWaitTime, 
                            MLFQScheduler.avgTat, 
                            MLFQScheduler.avgRt, 
                            MLFQScheduler.cpuUtilization,
                            MLFQScheduler.throughput); 
                    break;

                case "MLQ":
                    MLQ(processes);
                    currentStats = new AlgorithmStats("MLQ", 
                            mlq.avgWait, 
                            mlq.avgTurn, 
                            mlq.avgResponse, 
                            mlq.cpuUtil, 
                            mlq.throughput);
                    break;
            }

            if (currentStats != null) {
                results.add(currentStats);
            }
        }

        printComparisonReport(results);
    }

    public static void printComparisonReport(List<AlgorithmStats> results) {
        if (results.isEmpty()) return;

        System.out.println("\n\n==========================================================================================================");
        System.out.println("                                      FINAL COMPARISON REPORT                                             ");
        System.out.println("==========================================================================================================");
        System.out.printf("%-25s | %-12s | %-12s | %-12s | %-12s | %-12s\n", 
                "Algorithm", "Avg Waiting", "Avg Turnaround", "Avg Response", "CPU Util %", "Throughput");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        double minWait = Double.MAX_VALUE;
        double minTurn = Double.MAX_VALUE;
        double minResp = Double.MAX_VALUE;
        double maxUtil = -1.0;
        double maxThroughput = -1.0;

        for (AlgorithmStats stats : results) {
            System.out.printf("%-25s | %-12.2f | %-14.2f | %-12.2f | %-11.2f%% | %-12.4f\n", 
                    stats.name, stats.avgWaiting, stats.avgTurnaround, stats.avgResponse, stats.cpuUtilization, stats.throughput);

            if (stats.avgWaiting < minWait) minWait = stats.avgWaiting;
            if (stats.avgTurnaround < minTurn) minTurn = stats.avgTurnaround;
            if (stats.avgResponse < minResp) minResp = stats.avgResponse;
            if (stats.cpuUtilization > maxUtil) maxUtil = stats.cpuUtilization;
            if (stats.throughput > maxThroughput) maxThroughput = stats.throughput;
        }

        System.out.println("==========================================================================================================");
        System.out.println("WINNERS (Includes Ties):");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Best Avg Waiting Time    : " + getWinners(results, minWait, "WAIT") + " (" + String.format("%.2f", minWait) + ")");
        System.out.println("Best Avg Turnaround Time : " + getWinners(results, minTurn, "TURN") + " (" + String.format("%.2f", minTurn) + ")");
        System.out.println("Best Avg Response Time   : " + getWinners(results, minResp, "RESP") + " (" + String.format("%.2f", minResp) + ")");
        System.out.println("Highest CPU Utilization  : " + getWinners(results, maxUtil, "UTIL") + " (" + String.format("%.2f", maxUtil) + "%)");
        System.out.println("Highest Throughput       : " + getWinners(results, maxThroughput, "TP") + " (" + String.format("%.4f", maxThroughput) + ")");
        System.out.println("==========================================================================================================");
    }

    private static String getWinners(List<AlgorithmStats> results, double targetValue, String type) {
        StringBuilder winners = new StringBuilder();
        for (AlgorithmStats stats : results) {
            double valueToCheck = 0;
            switch (type) {
                case "WAIT": valueToCheck = stats.avgWaiting; break;
                case "TURN": valueToCheck = stats.avgTurnaround; break;
                case "RESP": valueToCheck = stats.avgResponse; break;
                case "UTIL": valueToCheck = stats.cpuUtilization; break;
                case "TP":   valueToCheck = stats.throughput; break;
            }

            if (Math.abs(valueToCheck - targetValue) < 0.0001) {
                if (winners.length() > 0) winners.append(", ");
                winners.append(stats.name);
            }
        }
        return winners.toString();
    }

    public static void resetProcesses(List<Process> processes) {
        for (Process p : processes) {
            p.reset();
        }
    }

    public static void FCFS(List<Process> processes) {
        System.out.println("First-Come-First-Serve Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        FCFSScheduler.fcfsWithMetrics(processes);
        System.out.println("-----------------------------------------------------------------------");
    }

    public static void SJF(List<Process> processes) {
        System.out.println("Shortest-Job-First Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        Schedular_SJF.runSJF(processes);
        System.out.println("-----------------------------------------------------------------------");
    }

    public static void SRT(List<Process> processes) {
        System.out.println("Shortest-Remain-Time Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        SRTSchedular srt = new SRTSchedular();
        srt.Run(processes);
        System.out.println("-----------------------------------------------------------------------");
    }

    public static void RR(List<Process> processes, int quantum) {
        System.out.println("Round Robin Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        RoundRobinScheduler.roundRobin(processes, quantum);
        System.out.println("-----------------------------------------------------------------------");
    }

    public static void PriorityP(List<Process> processes) {
        System.out.println("Priority Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        PriorityScheduler.priorityScheduling(processes, true);
        System.out.println("-----------------------------------------------------------------------");
    }

    public static void PriorityNon(List<Process> processes) {
        System.out.println("Priority Scheduler Output: ");
        System.out.println("-----------------------------------------------------------------------");
        PriorityScheduler.priorityScheduling(processes, false);
        System.out.println("-----------------------------------------------------------------------");
    }

public static void MLQ(List<Process> processes) {
         System.out.println("Multi Level Queue Scheduler Output: ");
         System.out.println("-----------------------------------------------------------------------");
         mlq.run();
         System.out.println("-----------------------------------------------------------------------");
    }

    public static void MLFQ(List<Process> processes) {
         System.out.println("Multi Level Feedback Scheduler Output: ");
         System.out.println("-----------------------------------------------------------------------");
         MLFQScheduler.run(processes);
         MLFQScheduler.printResults();
         System.out.println("-----------------------------------------------------------------------");
    }
    

}