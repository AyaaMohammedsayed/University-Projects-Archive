package main;


import java.util.List;

/**
 *
 * @author hana
 */
public class SRTSchedular {

    int current_time = 0;
    int completed = 0;
    int n;
    int totalContextSwitches = 0;
    String lastExecutedPid = null;
    int totalCpuTime = 0;
    public static double avgTurnaroundTime = 0;
    public static double avgWaitingTime = 0;
    public static double avgResponseTime = 0;
    public static double cpuUtilization = 0;
    public static double throughput = 0;
    final int contextSwitchCost = 1;

    public void Run(List<Process> processes) {
        n = processes.size();
        completed = 0;
        current_time = 0;
        totalContextSwitches = 0;
        lastExecutedPid = null;
        totalCpuTime = 0;

        while (completed < n) {
            int mintime = Integer.MAX_VALUE;
            int indx = -1; 
            boolean found = false;

            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrivalTime <= current_time && p.remainingTime > 0) {
                    if (p.remainingTime < mintime) {
                        mintime = p.remainingTime;
                        indx = i;
                        found = true;
                    } else if (p.remainingTime == mintime) {
                        if (p.arrivalTime < processes.get(indx).arrivalTime) {
                            indx = i;
                        }
                    }
                }
            }

            if (!found) {
                current_time++;
                lastExecutedPid = null;
                continue;
            }

            Process currentp = processes.get(indx);
            if (lastExecutedPid != null && !currentp.pid.equals(lastExecutedPid)) {
                totalContextSwitches++;
                current_time += contextSwitchCost;
                lastExecutedPid = currentp.pid;
                continue; 
            }
 
            lastExecutedPid = currentp.pid;

            if (currentp.startTime == -1) {
                currentp.startTime = current_time;
                currentp.responseTime = currentp.startTime - currentp.arrivalTime;
            }

            currentp.remainingTime--;
            current_time++;
            totalCpuTime++;

            if (currentp.remainingTime == 0) {
                completed++;
                currentp.completionTime = current_time;
                currentp.turnaroundTime = currentp.completionTime - currentp.arrivalTime;
                currentp.waitingTime = currentp.turnaroundTime - currentp.burstTime;

            }
        }

        CalculateAndPrintMetrics(processes);
    }

    public void CalculateAndPrintMetrics(List<Process> processes) {

        System.out.println("PID\tArrival\tBurst\tFinish\tTurnaround\tWaiting\tResponse");
        System.out.println("------------------------------------------------------------------");
        float totalWt = 0, totalTat = 0, totalRes = 0; // turn around and waiting time

        for (Process p : processes) {
            System.out.println(
                    p.pid + "\t"
                    + p.arrivalTime + "\t"
                    + p.burstTime + "\t"
                    + p.completionTime + "\t"
                    + p.turnaroundTime + "\t\t"
                    + p.waitingTime + "\t"
                    + p.responseTime // Added Response Time
            );

            totalWt += p.waitingTime;
            totalTat += p.turnaroundTime;
            totalRes += p.responseTime;
        }
        System.out.println("------------------------------------------------------------------");
        int totalProcesses = processes.size();

        avgTurnaroundTime = totalTat / totalProcesses;
        avgWaitingTime = totalWt / totalProcesses;
        avgResponseTime = totalRes / totalProcesses;

        double totalTime = current_time;

        cpuUtilization = (totalCpuTime / totalTime) * 100.0;

        throughput = totalProcesses / totalTime;

        int totalContextSwitchTime = totalContextSwitches * contextSwitchCost;

        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Response Time: %.2f\n", avgResponseTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
        System.out.printf("Throughput: %.2f processes/unit time\n", throughput);
        System.out.printf("Total Context Switches: %d\n", totalContextSwitches);
        System.out.printf("Total Context Switch Time: %d\n", totalContextSwitchTime);
        System.out.println("\n--- Process Execution Details ---");

    }
}