package org.northpole.workshop.base.controller.datastruct.stack;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

final class StackImplementation<E> extends LinkedList<E> {
    private Integer top;
    
    public StackImplementation(Integer top) {
        super();
        this.top = top;
    }
    
    protected boolean IsFullSatck() {
        return this.top >= getSize();
    }

    protected void Push(E info) throws Exception {
        if (!IsFullSatck()) {
            this.add(info, 0);
        } else {
            throw new ArrayIndexOutOfBoundsException("Stack is full/ ta lleno pa");
        }
    }

    protected E pop()throws Exception {
        return deleteFirst();
    }

    public Integer getTop() {
        return this.top;
    }
}
