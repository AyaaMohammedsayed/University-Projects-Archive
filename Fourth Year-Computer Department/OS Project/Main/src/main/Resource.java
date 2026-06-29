package main;

public class Resource {
     private boolean locked = false;
    public Process owner = null;

    public boolean isLocked() { return locked; }
    public void lock(Process p) { locked = true; owner = p; }
    public void unlock() { locked = false; owner = null; }
}