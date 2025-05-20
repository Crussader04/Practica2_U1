package org.northpole.workshop.base.controller.datastruct.stack;

public class Stack<E> {
    private StackImplementation<E> stack;

    public Stack(Integer top) {
        this.stack = new StackImplementation<>(top);
    }

    public boolean push(E data) throws Exception {
        try {
            stack.Push(data);
            return true;
        } catch (Exception e) {
            return false;
            // TODO: handle exception
        }
    }

    public E pop() throws Exception {
        try {
            return stack.pop();
            
        } catch (Exception e) {
            return null;
            // TODO: handle exception
        }
    }

    public boolean IsFullSatck() {
        return stack.IsFullSatck();
    }

    public Integer top() {
        return stack.getTop();
    }

    public Integer size() {
        return stack.getSize();
    }
}
