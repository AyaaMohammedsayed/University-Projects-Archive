class memBlock
{
    private long size;
    private long startAddress;
    private long endAddress;

    public memBlock(long size,long startAddress)
    {
        this.size=size;
      
        this.startAddress=startAddress;
        this.endAddress =startAddress +size-1;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(long startAddress) {
        this.startAddress = startAddress;
    }

    public void setEndAddress(long endAddress) {
        this.endAddress = endAddress;
    }

    public long getEndAddress() {
        return endAddress;
    }
    
}