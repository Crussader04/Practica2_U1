package org.northpole.workshop.base.controller.datastruct.graphs;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class DirectedGrap extends Graph {

    private Integer nro_vertex;
    private Integer nro_edge;
    private LinkedList<Adjacency> list_adjacencies [];

    public DirectedGrap(Integer nro_vertex) {
        this.nro_vertex = nro_vertex;
        this.nro_edge = 0;
        this.list_adjacencies = new LinkedList[nro_vertex + 1];
        for (int i = 1; i <= nro_vertex; i++) {
            list_adjacencies[i] = new LinkedList<>();
        }
    }

    @Override
    public Integer nro_vertex() {
        // TODO Auto-generated method stub
        return this.nro_vertex;
    }

    @Override
    public Integer nro_edge() {
        // TODO Auto-generated method stub
        return this.nro_edge;
    }

    @Override
    public Adjacency exist_edge(Integer o, Integer d) {
        // TODO Auto-generated method stub
        Adjacency band = null;
        if (o.intValue() <= nro_vertex.intValue() && d.intValue() <= nro_vertex.intValue()) {
            LinkedList<Adjacency> list = list_adjacencies[o];
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adj : matrix) {
                    if (adj.getdestiny().intValue() == d.intValue()) {
                        band = adj;
                        break;
                    }
                }
            }
        }
        return band;
    }

    @Override
    public float weight_edge(Integer o, Integer d) {
        // TODO Auto-generated method stub
        Adjacency adj = exist_edge(o, d);
        if (adj != null) {
            return adj.getWeight();
        }
        return Float.NaN;
    }

    @Override
    public void insert(Integer o, Integer d) {
        // TODO Auto-generated method stub
        insert(o, d, Float.NaN);
    }

    @Override
    public void insert(Integer o, Integer d, float weight) {
        // TODO Auto-generated method stub
        if (o.intValue() <= nro_vertex.intValue() && d.intValue() <= nro_vertex.intValue()) {
            if (exist_edge(o, d) == null) {
                nro_edge++;
                Adjacency aux = new Adjacency(o, weight);
                aux.setWeight(weight);
                aux.setDestiny(d);
                list_adjacencies[o].add(aux);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Vertex origin o destiny index out");
        }
    }

    @Override
    public LinkedList<Adjacency> adjacencies(Integer o) {
        return list_adjacencies[o];
    }

    public LinkedList<Adjacency> [] getList_adjacencies() {
        return this.list_adjacencies;
    }

    public void setList_adjacencies(LinkedList<Adjacency>[] list_adjacencies) {
        this.list_adjacencies = list_adjacencies;
    }

    public void setNro_edge(Integer nro_edge) {
        this.nro_edge = nro_edge;
    }
}