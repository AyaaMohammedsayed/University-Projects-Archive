package main;

import java.util.List;

public class GanttEntry {
    public String pid;
    public int start;
    public int end;

    public GanttEntry(String pid, int start, int end) {
        this.pid = pid;
        this.start = start;
        this.end = end;
    }
        public static void printGanttChart(List<GanttEntry> chart) {
        System.out.println("Gantt Chart:");
        for (GanttEntry e : chart) {
            System.out.print("| " + e.pid + "(" + e.start + "-" + e.end + ") ");
        }
        System.out.println("|");
    }
}
