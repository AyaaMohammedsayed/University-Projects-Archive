
class piriorityQueue {

    process first, last;

    public piriorityQueue() {
        first = last = null;
    }

    public long compareSize(process newProcess, process tempProcess) {
        return Long.compare(newProcess.getSize(), tempProcess.getSize());
    }

    public long compareTime(process newProcess, process tempProcess) {

        return Long.compare(newProcess.getTimeOut(), tempProcess.getTimeOut());
    }

    public void insert(process newProcess) {

        if (isEmpty()) {
            newProcess.next = first;
            first = last = newProcess;

        } else {
            process tempNode = first;
            long timeCompared = compareTime(newProcess, tempNode);
            long sizeCompared = compareSize(newProcess, tempNode);

            if (timeCompared < 0 || (timeCompared == 0 && sizeCompared <= 0)) {
                newProcess.next = first;
                first = newProcess;
            } else {
                while (tempNode.next != null && (compareTime(newProcess, tempNode.next) > 0
                        || (compareTime(newProcess, tempNode.next) == 0 && compareSize(newProcess, tempNode.next) > 0))) {
                    tempNode = tempNode.next;
                }
                newProcess.next = tempNode.next;
                tempNode.next = newProcess;

                if (tempNode == last) {
                    last = newProcess;
                }
            }
        }
    }

    public boolean isEmpty() {
        return last == null && first == null;
    }

    public void printQueue() {
        process temp = first;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public process dequeue() {
        process temp = first;
        if (first.next == null) {
            first = last = null;
        } else {
            first = first.next;
        }
        return temp;

    }
    public process peek()
    {
        return first;
    }

}
