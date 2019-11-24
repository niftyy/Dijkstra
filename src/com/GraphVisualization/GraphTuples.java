package com.GraphVisualization;

import javax.swing.*;
import java.util.ArrayList;

public class GraphTuples {
    public int num;
    public Shape shape;
    public ArrayList<Vertex> tracedPath;
    public Vertex start, end;
    public Timer timer;

    public GraphTuples(Shape shape, ArrayList<Vertex> tracedPath) {
        this.shape = shape;
        this.tracedPath = tracedPath;
        this.start = tracedPath.get(0);
        this.end = tracedPath.get(tracedPath.size()-1);
    }
}
