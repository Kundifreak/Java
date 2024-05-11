/* Created by: Nikaansh S.
 * Last Edited: 5/12/23
 * This class has two main search algoritms. One to find if two nodes are connected and another to find the least expensive path between two nodes
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.w3c.dom.NodeList;

public class GraphCreator implements ActionListener, MouseListener{

    //Global Variables
    JFrame frame = new JFrame();
    GraphPanel panel = new GraphPanel();
    JButton nodeB = new JButton("Node");
    JButton edgeB = new JButton("Edge");
    JTextField labelsTF = new JTextField("a");
    JTextField firstNode = new JTextField("first");
    JTextField secondNode = new JTextField("second");
    JButton connected = new JButton("Test Connected");
    Container west = new Container();
    Container east = new Container();
    Container south = new Container();
    JTextField salesmanStartTF = new JTextField("A");
    JButton salesmanB = new JButton("Shortest Path"); 
    final int NODE_CREATE = 0;
    final int EDGE_FIRST = 1;
    final int EDGE_SECOND = 2;
    int state = NODE_CREATE; 
    Node first = null;
    ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
    ArrayList<Integer> totalList = new ArrayList<Integer>();

    public GraphCreator() {
        //Setting the Frame
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER); 

        west.setLayout(new GridLayout(3,1));
        west.add(nodeB);
        nodeB.addActionListener(this);
        nodeB.setBackground(Color.GREEN);
        west.add(edgeB);
        edgeB.setBackground(Color.LIGHT_GRAY);
        edgeB.addActionListener(this);
        west.add(labelsTF);
        frame.add(west, BorderLayout.WEST);

        south.setLayout(new GridLayout(1, 2));
        south.add(salesmanStartTF);
        south.add(salesmanB);
        salesmanB.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);
 
        east.setLayout(new GridLayout(3,1));
        east.add(firstNode);
        east.add(secondNode);
        east.add(connected);
        connected.addActionListener(this);
        frame.add(east, BorderLayout.EAST);

        panel.addMouseListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GraphCreator();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    //What happens when the mouse is released
    public void mouseReleased(MouseEvent e) {
        if (state == NODE_CREATE) {
            panel.addNode(e.getX(), e.getY(), labelsTF.getText());
        }
        else if (state == EDGE_FIRST) {
            Node n = panel.getNode(e.getX(), e.getY());
            if (n != null) {  
                first = n;
                state = EDGE_SECOND; 
                n.setHighlighted(true);
            }
        }
        else if (state == EDGE_SECOND) {
            Node n = panel.getNode(e.getX(), e.getY());
            if (n != null && !first.equals(n)) {
                String s = labelsTF.getText();
                boolean valid = true;
                for (int i = 0; i < s.length(); i++) {
                    if (Character.isDigit(s.charAt(i)) == false) {
                        valid = false;
                    }
                }
                if (valid == true) {
                    first.setHighlighted(false);
                    panel.addEdge(first, n, labelsTF.getText());
                    first = null;
                    state = EDGE_FIRST;
                }
                else {
                    JOptionPane.showMessageDialog(frame , "Can only have digits in edge label.");
                }
            }
        }
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    //What happens when a button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nodeB)) {
            nodeB.setBackground(Color.GREEN);
            edgeB.setBackground(Color.LIGHT_GRAY);
            state = NODE_CREATE;
        }
        if (e.getSource().equals(edgeB)) {
            edgeB.setBackground(Color.GREEN);
            nodeB.setBackground(Color.LIGHT_GRAY);
            state = EDGE_FIRST;
            panel.stopHighlighting();
            frame.repaint();
        }
        if (e.getSource().equals(connected)) {
            if (panel.nodeExists(firstNode.getText()) == false) {
                JOptionPane.showMessageDialog(frame , "First node is not in you graph.");
            }
            else if (panel.nodeExists(secondNode.getText()) == false) {
                JOptionPane.showMessageDialog(frame , "Second node is not in you graph.");
            }
            else {
                Queue queue = new Queue();
                ArrayList<String> connectedList = new ArrayList<String>();
                connectedList.add(panel.getNode(firstNode.getText()).getLabel());
                ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
                for (int i = 0; i < edges.size(); i++) {
                    queue.endQueue(edges.get(i));
                 }
                while (queue.isEmpty() == false) {
                   String currentNode = queue.deQueue();
                   if (connectedList.contains(currentNode) == false) {
                       connectedList.add(currentNode);
                   }
                   edges = panel.getConnectedLabels(currentNode);
                   for (int i = 0; i < edges.size(); i++) {
                       if (connectedList.contains(edges.get(i)) == false) {
                           queue.endQueue(edges.get(i));
                       }
                   }
                }
                if (connectedList.contains(secondNode.getText())) {
                    JOptionPane.showMessageDialog(frame , "Connected");
                }
                else {
                    JOptionPane.showMessageDialog(frame , "Not Connected");
                }
            }
        }
        if (e.getSource().equals(salesmanB)) {
            if (panel.getNode(salesmanStartTF.getText()) != null) {
                travelling(panel.getNode(salesmanStartTF.getText()), new ArrayList<Node>(), 0);
                if (completed.size() != 0) {
                    int cheapest = totalList.get(0);
                    int cheapestPath = 0;
                    int current;
                    for (int i = 0; i < totalList.size(); i++) {
                        current = totalList.get(i);
                        if (current < cheapest) {
                            cheapest = current;
                            cheapestPath = i;
                        }
                    }
                    ArrayList<Node> cheapestFinal = new ArrayList<Node>();
					cheapestFinal.addAll(completed.get(cheapestPath));
                    System.out.print("Cheapest Path: ");
                    for (int i = 0; i < cheapestFinal.size(); i++) {
                        System.out.print(cheapestFinal.get(i).getLabel() + ", ");
                    }
                    System.out.println("");
                    System.out.println("Total Cost: " + cheapest);
                }
            }
            else {
                JOptionPane.showMessageDialog(frame , "Not a valid starting Node!");
            }
        }
    }

    public void travelling(Node n, ArrayList<Node> path, int total) {
        ArrayList<Edge> edgeList = panel.getEdgeList();
        if (path.size() == panel.getNodeList().size()) {//This checks if the path is completed
            completed.add((ArrayList<Node>) path.clone());
            totalList.add(total);
            if (!path.isEmpty()) {
                path.remove(path.size() - 1);
            }
            return;
        }
        else { 
            for (int i = 0; i < edgeList.size(); i++) {//Goes through all the edges and checks if it is connected to the node the salesman is at
                Edge e = edgeList.get(i);
                if(e.getOtherEnd(n) != null) {
                    if (path.contains(e.getOtherEnd(n)) == false) {
                        path.add(e.getOtherEnd(n));
                        travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel()));
                    }
                }
            }
            if (!path.isEmpty()) {
                path.remove(path.size() - 1);
            }
        }
    }

}