package org.northpole.workshop.base.controller.datastruct.graphs;

import java.lang.reflect.Array;
import java.util.HashMap;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class DirectLableGraph <E> extends DirectedGrap{

    protected E labels [];
    protected HashMap<E, Integer> dicVertex;
    private Class clazz;


    public DirectLableGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(this.clazz, nro_vertex + 1 );
        dicVertex = new HashMap<>(nro_vertex);
        //TODO Auto-generated constructor stub
    }
    
    public Adjacency exist_edge_label(E o, E d) {
        if (isLabelGraph()) {
            return exist_edge(getVertex(o), getVertex(d));
        } return null;
    }

    public void insert_label(E o, E d, Float weight) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), weight);
        }
    }

    public void insert_label(E o, E d) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d),  Float.NaN);
        }
    }

    public LinkedList<Adjacency> adjacencies_label(E o) {
        if (isLabelGraph()) {
            return adjacencies(getVertex(o));
        }return new LinkedList<>();
    }

    public void label_vertex(Integer vertex, E data){
        labels[vertex] = data;
        dicVertex.put(data, vertex);
    }

    public Boolean isLabelGraph() {
        Boolean band = true;
        for (int i = 1; i <= nro_vertex(); i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVertex(E label) {
        return dicVertex.get(label);
    }

    public E getLabel(Integer i) {
        
        return labels[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= nro_vertex(); i++){
            sb.append("Vertex = ").append(getLabel(i)).append(" \n");
            LinkedList<Adjacency> list = adjacencies(i);
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adjacency : matrix) {
                    sb.append(" \tAdjacency ").append("\n").append(" \tVertex = ")
                    .append(String.valueOf(getLabel(adjacency.getdestiny())));
                    if (!adjacency.getWeight().isNaN()) {
                        sb.append("weight ="+ adjacency.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}
