package org.northpole.workshop.base.controller.datastruct.graphs;

public class Adjacency {
    private Integer destiny;
    private Float weight;

    public Integer getdestiny() {
        return this.destiny;
    }

    public void setDestiny(Integer destiny) {
        this.destiny = destiny;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Adjacency(Integer destiny, Float weight) {
        this.destiny = destiny;
        this.weight = weight;
    }
}
