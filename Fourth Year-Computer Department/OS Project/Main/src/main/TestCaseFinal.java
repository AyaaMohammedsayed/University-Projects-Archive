package main;

import java.util.ArrayList;
import java.util.List;

public class TestCaseFinal {

    //General TestCase
    public static List<Process> getGeneralTestCase() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 6, 0));
        processes.add(new Process("P2", 1, 4, 1));
        processes.add(new Process("P3", 2, 7, 2));
        processes.add(new Process("P4", 3, 1 , 3));
        processes.add(new Process("P5", 5, 4, 3));
        processes.add(new Process("P6", 6, 7, 2));
        return processes;
    }
    

    //For test Aging Solve Priority algorithm
    public static List<Process> getPrioritySchedulerStarvationCase() {
        List<Process> processes = new ArrayList<>();
processes.add(new Process("P1", 0, 40, 3)); // low priority
processes.add(new Process("P2", 2, 4, 2));  // higher priority
processes.add(new Process("P3", 4, 10, 1)); // highest priority

        return processes;
    }
   
    //For test Aging Solve  MLF algorithm

    public static List<Process> getMLFStarvationCase() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 40, 1));
        processes.add(new Process("P2", 2, 4, 2));
        processes.add(new Process("P3", 4, 10, 3));
        processes.add(new Process("P4", 80, 30, 1));
        processes.add(new Process("P5", 82, 2, 2));
        return processes;
    }


    public static List<ProcessWithResource> getPrioritySchedulerInversionCase() {
        List<ProcessWithResource> processes = new ArrayList<>();
        Resource file = new Resource();


    processes.add(new ProcessWithResource("P1", 0, 5, 3, file));

    processes.add(new ProcessWithResource("P2", 1, 3, 1, file));

    processes.add(new ProcessWithResource("P3", 2, 2, 2, null));

        return processes;
    }

}
