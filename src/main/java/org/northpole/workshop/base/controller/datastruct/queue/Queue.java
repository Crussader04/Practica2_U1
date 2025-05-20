package org.northpole.workshop.base.controller.datastruct.queue;

public class Queue<E> {
    private QueueImplementation<E> Queue;

    public Queue(Integer top) {
        this.Queue = new QueueImplementation<>(top);
    }

    public boolean queue(E data) throws Exception {
        try {
            Queue.Queue(data);
            return true;
        } catch (Exception e) {
            return false;
            // TODO: handle exception
        }
    }

    public E dequeue() throws Exception {
        try {
            return Queue.dequeue();
            
        } catch (Exception e) {
            return null;
            // TODO: handle exception
        }
    }

    public boolean IsFullQueue() {
        return Queue.IsFullQueue();
    }

    public Integer top() {
        return Queue.getTop();
    }

    public Integer Size() {
        return Queue.getSize();
    }
}
