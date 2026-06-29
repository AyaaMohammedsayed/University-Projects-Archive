package main;

import java.util.*;

/**
 *
 * @author HANIN
 */
public class MLFQScheduler {
    
    private static final int CONTEXT_SWITCH_TIME = 1;    
    public static double cpuUtilization = 0;
    public static double avgWaitTime = 0;
    public static double throughput = 0;
    public static double avgTat = 0;
    public static double avgRt = 0;
    private static Queue<Process> q1;  
    private static Queue<Process> q2;  
    private static Queue<Process> q3;  
    private static List<Process> allProcesses;
    private static List<Process> completedProcesses;  
    private static int currentTime;
    private static int totalBusyTime;
    private static int nextBoostTime;
    private static int totalContextSwitches;
    private static int totalContextSwitchTime; 

    public static void run(List<Process> processes) {
        cpuUtilization = 0;
        avgWaitTime = 0;
        throughput = 0;
        avgTat = 0;
        avgRt = 0;       
        currentTime = 0;
        totalBusyTime = 0;
        nextBoostTime = 50;
        totalContextSwitches = 0;
        totalContextSwitchTime = 0;       
        q1 = new LinkedList<>();  
        q2 = new LinkedList<>();
        q3 = new LinkedList<>();
        completedProcesses = new ArrayList<>();        
        allProcesses = new ArrayList<>(processes);
        allProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int nextProcessIndex = 0;  
        while (completedProcesses.size() < allProcesses.size()) {
            
            while (nextProcessIndex < allProcesses.size() && 
                    allProcesses.get(nextProcessIndex).arrivalTime <= currentTime) {
                q1.add(allProcesses.get(nextProcessIndex));
                nextProcessIndex++;
            }        
            if (!q1.isEmpty()) {  
                executeRR(q1, 8, q2);  
            } else if (!q2.isEmpty()) {
                executeRR(q2, 16, q3);
            } else if (!q3.isEmpty()) {
                executeFCFS(q3);  
            } else {
                currentTime++;
            }
        }
    }
    
    
    private static void simulateContextSwitch() {
        if (totalBusyTime > 0 || !completedProcesses.isEmpty() || !q1.isEmpty() || !q2.isEmpty() 
            || !q3.isEmpty()) {
            totalContextSwitches++;
            totalContextSwitchTime += CONTEXT_SWITCH_TIME;
            currentTime += CONTEXT_SWITCH_TIME;
        }
    }
    
    private static void executeRR(Queue<Process> currentQ, int timeQuantum, Queue<Process> nextQ) {
        Process p = currentQ.poll();              
        
        if (p.remainingTime < p.burstTime) { 
            simulateContextSwitch();
        } else if (p.remainingTime == p.burstTime) {
            if (totalBusyTime > 0) {
                 simulateContextSwitch();
            }
        }
        
        if (p.startTime == -1) p.startTime = currentTime;        
        int timeSlice = Math.min(p.remainingTime, timeQuantum);                
        totalBusyTime += timeSlice;
        currentTime += timeSlice;
        
        if (currentTime >= nextBoostTime) {
            performAging();
        }        
        
        p.remainingTime -= timeSlice;        
        
        if (p.remainingTime > 0) {
            nextQ.add(p);
        } else {
            finalizeProcess(p);
        }
    }
    
    private static void executeFCFS(Queue<Process> currentQ) {
        Process p = currentQ.poll();
        
        if (p.remainingTime < p.burstTime) {
            simulateContextSwitch();
        } else if (p.remainingTime == p.burstTime) {
            if (totalBusyTime > 0) {
                 simulateContextSwitch();
            }
        }
        
        if (p.startTime == -1) p.startTime = currentTime; 
        int timeSlice = p.remainingTime;        
        totalBusyTime += timeSlice;
        currentTime += timeSlice; 
        
        if (currentTime >= nextBoostTime) {
            performAging();
        }        
        
        p.remainingTime = 0;      
        finalizeProcess(p);
    }
    
    private static void performAging() {
        q1.addAll(q2);
        q2.clear();
        q1.addAll(q3);
        q3.clear();
        nextBoostTime += 50;
        System.out.println("Aging happened at time: " + currentTime);
    }

    private static void finalizeProcess(Process p) {
        p.completionTime = currentTime;
        p.turnaroundTime = p.completionTime - p.arrivalTime;
        p.waitingTime = p.turnaroundTime - p.burstTime;
        completedProcesses.add(p);
    }
    
    public static void printResults() {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",  
                "PID", "Arrival", "Burst", "Priority", "Start", "Finish", "TurnAround", "Waiting");
        System.out.println("----------------------------------------------------------------------------------");

        completedProcesses.sort(Comparator.comparing(p -> p.pid));

        float totalWt = 0, totalTat = 0, totalRt = 0; 
        int totalProcesses = completedProcesses.size();
        
        for (Process p : completedProcesses) {
            int responseTime = p.startTime - p.arrivalTime; 
            p.responseTime = responseTime; 
            totalRt += responseTime;
            
            System.out.printf("%-5s %-10d %-10d %-10d %-10d %-10d %-10d %-10d\n",  
                    p.pid, p.arrivalTime, p.burstTime, p.basePriority,  
                    p.startTime, p.completionTime, p.turnaroundTime, p.waitingTime);
            totalWt += p.waitingTime;
            totalTat += p.turnaroundTime;
        }
        System.out.println("----------------------------------------------------------------------------------");
        
        if (currentTime > 0) {
            cpuUtilization = ((double) totalBusyTime / currentTime) * 100;
        }  
        
        throughput = (double) totalProcesses / currentTime;
        avgWaitTime = totalWt / totalProcesses;
        avgTat = totalTat / totalProcesses;
        avgRt = totalRt / totalProcesses;
        
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
        System.out.printf("Total Context Switches: %d\n", totalContextSwitches);
        System.out.printf("Total Context Switch Time: %d\n", totalContextSwitchTime);
        System.out.printf("Average Waiting Time: %.2f\n", avgWaitTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTat);
        System.out.printf("Average Response Time: %.2f\n", avgRt);
        System.out.printf("Throughput: %.4f processes per time unit\n", throughput);
    }
}