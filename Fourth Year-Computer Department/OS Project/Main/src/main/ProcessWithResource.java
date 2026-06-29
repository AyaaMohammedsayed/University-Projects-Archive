package main;

public class ProcessWithResource extends Process {
      public Resource res;


    public ProcessWithResource(String pid, int arrivalTime, int burstTime, int priority, Resource res) {
        super(pid, arrivalTime, burstTime, priority);
        this.res = res;
    }
}