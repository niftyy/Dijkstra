package com.tushar;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GUI_Frame extends JFrame {
    private  GUI_Panel pane;

    public GUI_Frame() {
        super("Application");
        Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
        GUI_Frame parent = this;
        this.pane = new GUI_Panel((int)window.getWidth(),(int)window.getHeight());
        //############################################################################
        MenuBar menu_bar = new MenuBar();
        Menu menu = new Menu("Menu");
        //############################################################################
        MenuItem renew = new MenuItem("New");
        renew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.renew();
            }
        });
        menu.add(renew);
        //############################################################################
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(exit);
        //############################################################################
        menu_bar.add(menu);

        Menu graphFunctions = new Menu("Graph");
        //############################################################################
        Menu vertex = new Menu("Vertex");
        //############################################################################
        MenuItem add_vertex = new MenuItem("Add");
        add_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name = JOptionPane.showInputDialog("Enter vertex name");
                if(vertex_name == null) return;
                String x = JOptionPane.showInputDialog("Enter x-coordinate");
                if(x == null) return;
                String y = JOptionPane.showInputDialog("Enter y-coordinate");
                if(y == null) return;
                pane.addVertex(vertex_name, Integer.parseInt(x), Integer.parseInt(y));
            }
        });
        vertex.add(add_vertex);
        //############################################################################
        MenuItem search_vertex = new MenuItem("Search");
        search_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name = JOptionPane.showInputDialog("Enter vertex name");
                if(vertex_name == null) return;
                try {
                    Vertex v = pane.findVertex(vertex_name);
                    if(v == null)
                        throw new GraphException("Vertex not Found");
                    String message = "Vertex name : " + v.getName() + "\n";
                    message += "X Coordinate : " + v.getX() + "\n";
                    message += "Y Coordinate : " + v.getY() + "\n";
                    JOptionPane.showMessageDialog(parent,message);
                } catch (GraphException err){
                    JOptionPane.showMessageDialog(parent, err.getMessage());
                }
            }
        });
        vertex.add(search_vertex);
        //############################################################################
        MenuItem modify_vertex = new MenuItem("Modify");
        modify_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name = JOptionPane.showInputDialog("Enter vertex name");
                try {
                    if(vertex_name == null) return;
                    Vertex v = pane.findVertex(vertex_name);
                    if(v == null) throw new GraphException("Vertex not found.");
                    String x = JOptionPane.showInputDialog("Enter new x-coordinate");
                    if(x == null) return;
                    String y = JOptionPane.showInputDialog("Enter new y-coordinate");
                    if(y == null) return;
                    pane.alterVertex(v, Integer.parseInt(x), Integer.parseInt(y));
                } catch (GraphException err){
                    JOptionPane.showMessageDialog(parent, err.getMessage());
                }
            }
        });
        vertex.add(modify_vertex);
        //############################################################################
        MenuItem delete_vertex = new MenuItem("Delete");
        delete_vertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name = JOptionPane.showInputDialog("Enter vertex name");
                pane.deleteVertex(vertex_name);
            }
        });
        vertex.add(delete_vertex);
        //############################################################################
        graphFunctions.add(vertex);
        //############################################################################
        Menu edge = new Menu("Edge");
        //############################################################################
        MenuItem add_edge = new MenuItem("Add");
        add_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name1 = JOptionPane.showInputDialog("Enter the first vertex name");
                if(vertex_name1 == null) return;
                String vertex_name2 = JOptionPane.showInputDialog("Enter the second vertex name");
                if(vertex_name2 == null) return;
                String cost = JOptionPane.showInputDialog("Enter the cost");
                if(cost == null) return;
                pane.addEdge(vertex_name1, vertex_name2, Integer.parseInt(cost));
            }
        });
        edge.add(add_edge);
        //############################################################################
        MenuItem search_edge = new MenuItem("Search");
        search_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name1 = JOptionPane.showInputDialog("Enter the first vertex name");
                String vertex_name2 = JOptionPane.showInputDialog("Enter the second vertex name");
                try {
                    Edge edge = pane.findEdge(vertex_name1, vertex_name2);
                    if(edge == null) throw new GraphException("Edge not found");
                    String message = "Edge vertex 1 : " + edge.getV1().getName() + "\n";
                    message += "Edge vertex 2 : " + edge.getV2().getName() + "\n";
                    message += "Edge cost : " + edge.getCost() + "\n";
                    JOptionPane.showMessageDialog(parent, message);
                } catch (GraphException err){
                    JOptionPane.showMessageDialog(parent, err.getMessage());
                }
            }
        });
        edge.add(search_edge);
        //############################################################################
        MenuItem modify_edge = new MenuItem("Modify");
        modify_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String vertex_name1 = JOptionPane.showInputDialog("Enter the first vertex name");
                    String vertex_name2 = JOptionPane.showInputDialog("Enter the second vertex name");
                    Edge edge = pane.findEdge(vertex_name1,vertex_name2);
                    if(edge == null) throw new GraphException("Edge not found");
                    int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter new cost"));
                    pane.alterEdge(edge,cost);
                } catch (GraphException err){
                    JOptionPane.showMessageDialog(parent, err.getMessage());
                }
            }
        });
        edge.add(modify_edge);
        //############################################################################
        MenuItem delete_edge = new MenuItem("Delete");
        delete_edge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertex_name1 = JOptionPane.showInputDialog("Enter the first vertex name");
                String vertex_name2 = JOptionPane.showInputDialog("Enter the second vertex name");
                pane.deleteEdge(vertex_name1,vertex_name2);
            }
        });
        edge.add(delete_edge);
        //############################################################################
        graphFunctions.add(edge);
        //############################################################################
        MenuItem input_file = new MenuItem("Input File");
        input_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(j.getSelectedFile().getAbsolutePath());
                    try{
                        Scanner sc = new Scanner(file);
                        int v = Integer.parseInt(sc.nextLine());
                        StringTokenizer st;
                        for(int i=0 ; i<v ; i++){
                            st = new StringTokenizer(sc.nextLine());
                            String name = st.nextToken();
                            int x = Integer.parseInt(st.nextToken());
                            int y = Integer.parseInt(st.nextToken());
                            pane.addVertex(name, x, y);
                        }
                        int edges = Integer.parseInt(sc.nextLine());
                        for(int i=0 ; i<edges ; i++){
                            st = new StringTokenizer(sc.nextLine());
                            String from = st.nextToken();
                            String to = st.nextToken();
                            int cost = Integer.parseInt(st.nextToken());
                            pane.addEdge(from , to, cost);
                        }
                    } catch(Exception error){
                        System.out.println(error.getMessage());
                    }
                }
                else
                    System.out.println("the user cancelled the operation");
            }
        });
        graphFunctions.add(input_file);
        //############################################################################
        MenuItem save_graph = new MenuItem("Save Graph");
        save_graph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    System.out.println(j.getSelectedFile().getAbsolutePath());
                    File newFile = new File(j.getSelectedFile().getAbsolutePath());
                    try {
                        newFile.createNewFile();
                        FileWriter fw = new FileWriter(j.getSelectedFile().getAbsolutePath());
                        int vertices = pane.getVertices().size();
                        String s = vertices + "\n";
                        fw.write(s);
                        for(Vertex v: pane.getVertices()){
                            s = v.getName() + " " + v.getX() + " " + v.getY() + "\n";
                            fw.write(s);
                        }
                        int edges = pane.getEdges().size();
                        s = edges + "\n";
                        fw.write(s);
                        for(Edge edge: pane.getEdges()){
                            s = edge.getV1().getName() + " " + edge.getV2().getName() + " " + edge.getCost() + "\n";
                            fw.write(s);
                        }
                        JFrame frame = new JFrame("Save Graph");
                        JOptionPane.showMessageDialog(frame,"Saved Successfully");
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    System.out.println("the user cancelled the operation");
            }
        });
        graphFunctions.add(save_graph);
        //############################################################################
        menu_bar.add(graphFunctions);

        Menu dijkstra = new Menu("Dijkstra");
        MenuItem Animate = new MenuItem("Animate");
        Animate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = JOptionPane.showInputDialog("Enter source");
                String destination = JOptionPane.showInputDialog("Enter destination");
                String []options = {"circle", "square", "rectangle"};
                Object shape = JOptionPane.showInputDialog(null, "Choose your shape", "Shape", JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                pane.dijkstra(source,destination, false, false, (String)shape);
            }
        });
        dijkstra.add(Animate);
        MenuItem Path = new MenuItem("Path");
        Path.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = JOptionPane.showInputDialog("Enter source");
                String destination = JOptionPane.showInputDialog("Enter destination");
                pane.dijkstra(source, destination, true, false, null);
            }
        });
        dijkstra.add(Path);
        //############################################################################
        MenuItem drawPath = new MenuItem("Draw Path");
        drawPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = JOptionPane.showInputDialog("Enter source");
                String destination = JOptionPane.showInputDialog("Enter destination");
                pane.dijkstra(source, destination, false, true, null);
            }
        });
        dijkstra.add(drawPath);
        //############################################################################
        menu_bar.add(dijkstra);
        setMenuBar(menu_bar);
        //############################################################################
        getContentPane().setLayout(new BorderLayout());
        add(this.pane,BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
