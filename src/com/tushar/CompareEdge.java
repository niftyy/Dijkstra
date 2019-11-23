package com.tushar;

import java.util.Comparator;

public class CompareEdge implements Comparator <Edge>{
    public int compare(Edge e1, Edge e2){
        if(e1.getV1().getName().compareTo(e2.getV1().getName()) == 0)
            return e1.getV2().getName().compareTo(e2.getV2().getName());
        else return e1.getV1().getName().compareTo(e2.getV1().getName());
    }
}
