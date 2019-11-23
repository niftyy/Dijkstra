package com.tushar;

import java.util.Comparator;

public class CompareVertex implements Comparator<Vertex> {
    public int compare(Vertex v1, Vertex v2){
        return v1.getName().compareTo(v2.getName());
    }
}
