package com.tushar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.swing.Timer;

public class GUI_Panel extends JPanel {
    private int windowWidth;
    private int windowHeight;
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private Shape shape;
    private Shape start, end;
    private Timer timer;
    private int num, x, y, x1, x2, y1, y2, m_new, slope_error_new;
    private boolean isMouseDown;
    private ArrayList<Vertex> tracedPath;
    private Vertex selectedVertex;
    GUI_Panel(int height,int width){
        super();
        this.windowWidth = width;
        this.windowHeight = height;
        this.isMouseDown = false;
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();;
        this.tracedPath = null;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(45, 45, 45));
        setVisible(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                    int x =  e.getX();
                    int y = e.getY();
                    if(x%20 > 10){
                        x+=20;
                    }
                    if(y%20 > 10){
                        y+=20;
                    }
                    selectedVertex.setX(x/20);
                    selectedVertex.setY(y/20);
                    removeAll();
                    revalidate();
                    repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                selectedVertex = null;
                for(Vertex v: vertices){
                    int x2 = v.getX();
                    int y2 = v.getY();
                    if(Point2D.distance(x, y, x2*20, y2*20) < 20){
                        selectedVertex = v;
                        System.out.println("found");
                        removeAll();
                        revalidate();
                        repaint();
                        // TODO add key listener to delete selected vertex
                        return;
                    }
                }
                String name = JOptionPane.showInputDialog("Enter vertex name");
                if(name == null) return;
                if(x%20 > 10){
                    x+=20;
                }
                if(y%20 > 10){
                    y+=20;
                }
                addVertex(name,x/20,y/20);
            }
        });
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void paint(Graphics g){
        super.paint(g);
        drawGrid(g);
        g.setColor(new Color(255,255,255));
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0;i < vertices.size();i++){
            g.drawString(vertices.get(i).getName(),vertices.get(i).getX()*20-5,vertices.get(i).getY()*20+5);
            g.drawOval(vertices.get(i).getX()*20 - 10,vertices.get(i).getY()*20 - 10,20,20);
        }
        for(int i = 0;i < edges.size();i++){
            g.drawLine(edges.get(i).getV1().getX()*20,edges.get(i).getV1().getY()*20,edges.get(i).getV2().getX()*20,edges.get(i).getV2().getY()*20);
        }
        g2d.setStroke(new BasicStroke(3));
        if(start != null && end !=null){
            g.setColor(new Color(157, 255, 38));
            g2d.drawOval((int)start.getX()*20-10, (int)start.getY()*20-10, 20, 20);
            g2d.drawOval((int)end.getX()*20-10, (int)end.getY()*20-10, 20, 20);
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(14, 192, 255));
            for(int i=0 ; i<tracedPath.size()-1 ; i++){
                g2d.drawLine(tracedPath.get(i).getX()*20, tracedPath.get(i).getY()*20, tracedPath.get(i+1).getX()*20, tracedPath.get(i+1).getY()*20);
            }
        }
        if(shape != null){
            g.setColor(new Color(255,0,0));
            if(shape instanceof Circle)
                g.drawOval((int) shape.getX(),(int) shape.getY(), (int) shape.getLength(), (int) shape.getWidth());
            else if(shape instanceof Square)
                g.drawRect((int) shape.getX()*20-10,(int) shape.getY()*20-10, (int) shape.getLength(), (int) shape.getWidth());
            else if(shape instanceof Rectangle)
                g.drawRect((int) shape.getX()*20-10,(int) shape.getY()*20-10, (int) shape.getLength(), (int) shape.getWidth());
        }

        if(selectedVertex != null){
            g.setColor(new Color(255, 198, 22));
            g.drawOval(selectedVertex.getX()*20-10, selectedVertex.getY()*20-10, 20, 20);
        }
    }

    public void drawGrid(Graphics g){
        g.setColor(new Color(65,65,65));
        for(int i = 0;i < 200;i++){
            g.drawLine(0,i*20,2000,i*20);
        }
        for(int i = 0;i < 200;i++){
            g.drawLine(i*20,0,i*20,2000);
        }
    }

    public void addVertex(String name,int x,int y){
        Vertex new_vertex = new Vertex(x,y,name);
        this.vertices.add(new_vertex);
        removeAll();
        revalidate();
        repaint();
    }

    public Vertex findVertex(String vertex_name){
        int i = 0;
        while(this.vertices.size() > i && this.vertices.get(i).getName().compareTo(vertex_name) != 0){
            i++;
        }
        return this.vertices.get(i);
    }

    public void alterVertex(Vertex v,int x,int y){
        v.setX(x);
        v.setY(y);
        removeAll();
        revalidate();
        repaint();
    }

    public void deleteVertex(String vertex_name){
        for(int i = 0;i < vertices.size();i++){
            if(vertices.get(i).getName().compareTo(vertex_name) == 0){
                vertices.remove(vertices.get(i));
                break;
            }
        }
        int i = 0;
        while(i < edges.size()){
            if(edges.get(i).getV1().getName().compareTo(vertex_name) == 0 || edges.get(i).getV2().getName().compareTo(vertex_name) == 0){
                edges.remove(edges.get(i));
            } else {
                i++;
            }
        }
        removeAll();
        revalidate();
        repaint();
    }

    public void addEdge(String vertex_name1,String vertex_name2,int cost){
        Vertex v1 = findVertex(vertex_name1);
        Vertex v2 = findVertex(vertex_name2);
        Edge new_edge = new Edge(v1,v2,cost);
        this.edges.add(new_edge);
        removeAll();
        revalidate();
        repaint();
    }

    public Edge findEdge(String vertex_name1,String vertex_name2){
        int i = 0;
        while(i < edges.size()){
            if(edges.get(i).getV1().getName().compareTo(vertex_name1) == 0 || edges.get(i).getV2().getName().compareTo(vertex_name2) == 0){
                return edges.get(i);
            } else {
                i++;
            }
        }
        return null;
    }

    public void alterEdge(Edge e,int cost){
        e.setCost(cost);
        removeAll();
        revalidate();
        repaint();
    }

    public void deleteEdge(String vertex_name1,String vertex_name2){
        int i = 0;
        while(i < edges.size()){
            if(edges.get(i).getV1().getName().compareTo(vertex_name1) == 0 || edges.get(i).getV2().getName().compareTo(vertex_name2) == 0){
                edges.remove(edges.get(i));
            } else {
                i++;
            }
        }
        removeAll();
        revalidate();
        repaint();
    }

    public void dijkstra(String source, String destination, boolean text, boolean onGraph, String shape){
        Comparator<State> comparator = new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if(o1.getCost() > o2.getCost()){
                    return 1;
                } else if(o1.getCost() < o2.getCost()){
                    return -1;
                } else{
                    return o1.getV().getName().compareTo(o2.getV().getName());
                }
            }
        };
        PriorityQueue<State> queue = new PriorityQueue<State>(comparator);
        Vertex source_vertex = findVertex(source);
        Vertex destination_vertex = findVertex(destination);
        ArrayList<State> visited = new ArrayList<State>();
        queue.add(new State(source_vertex,null,0));
        while(queue.size() > 0){
            State current = queue.poll();
            if(current.getV().getName().compareTo(destination_vertex.getName()) == 0){
                visited.add(current);
                ArrayList<Vertex> traced = trace(destination_vertex, visited, text);
                if(timer != null && timer.isRunning()){
                    timer.stop();
                }
                if(onGraph){
                    drawPath(traced);
                } else if(text){
                    displayPath(traced);
                } else {
                    drawPath(traced);
                    animate(traced, shape);
                }
                return;
            }
            if(!isVisited(current.getV(),visited)){
                ArrayList<State> temp = getNextStates(current,visited);
                while(temp.size() > 0){
                    queue.add(temp.remove(0));
                }
            }
            visited.add(current);
        }
        JFrame frame = new JFrame("A");
        JOptionPane.showMessageDialog(frame,"Not Found!!");
    }

    public ArrayList<State> getNextStates(State current,ArrayList<State> visited){
        ArrayList<State> next = new ArrayList<State>();
        int i = 0;
        while(edges.size() > i){
            if(edges.get(i).getV1().getName().compareTo(current.getV().getName()) == 0){
                if(!isVisited(edges.get(i).getV2(),visited)){
                    next.add(new State(edges.get(i).getV2(),current.getV(),edges.get(i).getCost() + current.getCost()));
                }
            }
            if(edges.get(i).getV2().getName().compareTo(current.getV().getName()) == 0){
                if(!isVisited(edges.get(i).getV1(),visited)){
                    next.add(new State(edges.get(i).getV1(),current.getV(),edges.get(i).getCost() + current.getCost()));
                }
            }
            i++;
        }
        return next;
    }

    public boolean isVisited(Vertex current,ArrayList<State> visited){
        for(int i = 0;i<visited.size();i++){
            if(visited.get(i).getV().getName().compareTo(current.getName()) == 0){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Vertex> trace(Vertex destination, ArrayList<State> visited, boolean text){
        ArrayList<Vertex> traced = new ArrayList<Vertex>();
        while(destination != null){
            traced.add(0,destination);
            destination = findParent(destination, visited);
        }
        return traced;
    }

    public Vertex findParent(Vertex destination,ArrayList<State> visited){
        for(int i=0;i<visited.size();i++){
            if(visited.get(i).getV().getName().compareTo(destination.getName()) == 0){
                return visited.get(i).getParent();
            }
        }
        return null;
    }

    public void animate(ArrayList<Vertex> traced, String s){
        if(s.compareTo("circle") == 0)
            shape = new Circle(traced.get(0).getX()*20-10,traced.get(0).getY()*20-10);
        else if(s.compareTo("square") == 0)
            shape = new Square(traced.get(0).getX(),traced.get(0).getY());
        else if(s.compareTo("rectangle") == 0)
            shape = new Rectangle(traced.get(0).getX(),traced.get(0).getY());
        num = 0;
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dx = (int)(traced.get((num+1)%traced.size()).getX()*20-10 - shape.getX());
                int dy = (int)(traced.get((num+1)%traced.size()).getY()*20-10 - shape.getY());
                if(dx == 0 && dy == 0){
                    num++;
                    if(num == traced.size()-1){
                        shape.setX(traced.get(0).getX()*20-10);
                        shape.setY(traced.get(0).getY()*20-10);
                        num = 0;
                    }
                } else {
                    double steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
                    steps = steps/10;
                    double Xinc = dx / (double) steps;
                    double Yinc = dy / (double) steps;
                    double X = shape.getX(), Y = shape.getY();
                    X += Xinc;
                    Y += Yinc;
                    shape.setX(X = Math.round(X));
                    shape.setY(Y = Math.round(Y));
                    removeAll();
                    revalidate();
                    repaint();
                }
            }
        });
        timer.start();
    }


    public void animate(ArrayList<Vertex> traced){
        shape = new Circle(traced.get(0).getX(),traced.get(0).getY());
        // initialize the loop
        x1 = (int)shape.getX();
        y1 = (int)shape.getY();
        x2 = traced.get(1).getX();
        y2 = traced.get(1).getY();
        x = x1;
        y = y1;
        m_new = 2 * (y2 - y1);
        slope_error_new = m_new - (x2 - x1);
        num = 0;
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x > x2){
                    //num++;
                    if(num == traced.size()-1){
                        shape.setX(traced.get(0).getX());
                        shape.setY(traced.get(0).getY());
                        num = 0;
                    }
                    num++;
                    // reinitialize for next vertex
                    x1 = (int)shape.getX();
                    y1 = (int)shape.getY();
                    x2 = traced.get(num).getX();
                    y2 = traced.get(num).getY();
                    x = x1;
                    y = y1;
                } else {
                    shape.setX(x);
                    shape.setY(y);
                    slope_error_new += m_new;
                    if (slope_error_new >= 0) {
                        y++;
                        slope_error_new -= 2 * (x2 - x1);
                    }
                    x++;
                    removeAll();
                    revalidate();
                    repaint();
                }
            }
        });
        timer.start();
    }

    public void displayPath(ArrayList<Vertex> traced){
        String path = "Path:\n";
        for(Vertex v: traced){
            path += v.getName();
            path += " ";
        }
        JFrame frame = new JFrame("Path");
        JOptionPane.showMessageDialog(frame, path);
    }

    public void drawPath(ArrayList<Vertex> traced){
        start = new Circle(traced.get(0).getX(), traced.get(0).getY());
        end = new Circle(traced.get(traced.size()-1).getX(), traced.get(traced.size()-1).getY());
        tracedPath = traced;
        removeAll();
        revalidate();
        repaint();
    }

    public void renew(){
        while (vertices.size()>0){
            vertices.remove(0);
        }
        while (edges.size()>0){
            edges.remove(0);
        }
        removeAll();
        revalidate();
        repaint();
    }
}
