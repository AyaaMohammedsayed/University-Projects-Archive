
package main;
import java.util.*;


public class Process {
    public String pid;
    public int arrivalTime;
    public int burstTime;
    public int startTime = -1;
    public int remainingTime;
    public int completionTime;
    public int waitingTime;
    public int turnaroundTime;
    public int responseTime;
    public int basePriority;       
    public int currentPriority;   
    public int waitingTimeCounter; 



    public Process(String pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.basePriority = priority;
        this.currentPriority = priority;
        this.waitingTimeCounter = 0;
    }
public void reset(){
        this.startTime = -1;
        this.remainingTime = this.burstTime; 
        this.completionTime = 0;

        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;

        this.currentPriority = this.basePriority; 
        this.waitingTimeCounter = 0;
    }

}

