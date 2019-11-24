package com.GraphVisualization;

public class State {
    private Vertex v,parent;
    private int cost;

    public State(Vertex v, Vertex parent, int cost) {
        this.v = v;
        this.parent = parent;
        this.cost = cost;
    }

    public Vertex getV() {
        return v;
    }

    public void setV(Vertex v) {
        this.v = v;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
