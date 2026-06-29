package main;


import java.util.List;

public class Helper {
    public static double avgWait = 0;
    public static double avgTurn = 0;
    public static double avgResponse = 0;
    public static double cpuUtil = 0;
    public static double throughput = 0;
    public static void printMetrics(List<Process> processes, int totalTime, int totalContextSwitches) {
        int n = processes.size();
        double totalTAT = 0, totalWT = 0, totalRT = 0;
        int totalCPUTime = 0;

        for (Process p : processes) {
            totalTAT += p.turnaroundTime;
            totalWT += p.waitingTime;
            totalRT += p.responseTime;
            totalCPUTime += p.burstTime;
        }

        cpuUtil = 100.0 * totalCPUTime / totalTime;
        throughput = (double) n / totalTime;
        avgWait = totalWT / n;
        avgTurn = totalTAT / n;
        avgResponse = totalRT / n;
        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
        System.out.printf("Average Response Time: %.2f\n", totalRT / n);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtil);
        System.out.printf("Throughput: %.2f processes/unit time\n", throughput);
        System.out.println("Total Context Switches: " + totalContextSwitches);
        System.out.println("Total Context Switch Time: " + (totalContextSwitchTime(totalContextSwitches, 1)));
    }

    public static int totalContextSwitchTime(int totalContextSwitches, int contextSwitchTime) {
        return contextSwitchTime * totalContextSwitches;
    }

public static void printProcessTable(List<Process> processes) {
    System.out.println();
    System.out.printf("%-8s %-15s %-15s %-15s %-18s %-18s %-15s %-15s %-15s\n",
            "PID", "Arrival Time", "Burst Time", "Start Time",
            "Complete Time", "Turnaround Time", "Waiting Time", "Response Time","Priority");

    for (Process p : processes) {
        System.out.printf("%-8s %-15d %-15d %-15d %-18d %-18d %-15d %-15d  %-15d \n",
                p.pid, p.arrivalTime, p.burstTime, p.startTime,
                p.completionTime, p.turnaroundTime, p.waitingTime, p.responseTime,p.basePriority);
    }
}


}
