/* Created by: Nikaansh S.
 * Last Edited: 5/12/23
 * This class has draws the board along with the code for manipulation edges and nodes
 */

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    
    ArrayList<Node> nodeList = new ArrayList<Node>();

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    ArrayList<Edge> edgeList = new ArrayList<Edge>();

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();

    int circleRadius = 20;

    public GraphPanel() {
        super();
    }

    //Returns the an arraylist of all lables right next to the node that is being searched
    public  ArrayList<String> getConnectedLabels(String label) {
        ArrayList<String> toReturn = new ArrayList<String>();
        int b = getIndex(label);
        for (int i = 0; i < adjacency.size(); i++) {
            if (adjacency.get(b).get(i) == true && !nodeList.get(i).getLabel().equals(label)) {
                 toReturn.add(nodeList.get(i).getLabel());   
            }
        }
        return toReturn;
    }

    //Gets coordinated and outputs where the node is located
    public Node getNode(int x, int y) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i); 
            //a squared plus b squared= c squared
            double radius = Math.sqrt(Math.pow(x - node.getX(), 2) + Math.pow(y - node.getY(), 2));
            if (radius < circleRadius) {
                return node;
            }   
        }
        return null;
    }

    //This gets the node matching the string
    public Node getNode(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node n = nodeList.get(i);
            if (s.equals(n.getLabel())) {
                return n;
            }
        }
        return null;
    }

    //Gets the index values of the node
    public int getIndex(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node n = nodeList.get(i);
            if (s.equals(n.getLabel())) {
                return i;
            }
        }
        return -1;
    }

    //Checks if the nodein question exists
    public boolean nodeExists(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (s.equals(nodeList.get(i).getLabel())) {
                return true;
            }
        }
        return false;
    }

    //adds an edge to the board
    public void addEdge (Node first, Node second, String newLabel) {
        edgeList.add(new Edge(first, second, newLabel));
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < adjacency.size(); i++) {
            if (first.equals(nodeList.get(i))) {
                firstIndex = i;
            }
            if (second.equals(nodeList.get(i))) {
                secondIndex = i;
            }
        }
        adjacency.get(firstIndex).set(secondIndex, true);
        adjacency.get(secondIndex).set(firstIndex, true);
        printAdjacency();
    }

    //adds a node to the board
    public void addNode(int newx, int newy, String newLable) {
        nodeList.add(new Node(newx, newy, newLable));
        adjacency.add(new ArrayList<Boolean>());
        for (int i = 0; i < adjacency.size(); i++) {
            adjacency.get(i).add(false);
        }
        for (int i = 0; i < adjacency.size(); i++) {
            adjacency.get(adjacency.size() - 1).add(false);
        }
        printAdjacency();
    }

    //prints out the adjecent matrix
    public void printAdjacency() {
        System.out.println();
        for (int i = 0; i < adjacency.size(); i++) {
            for (int j = 0; j < adjacency.size(); j++) {
                System.out.print(adjacency.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    //paints the board
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw my stuff
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getHighlighted() == true) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawOval(nodeList.get(i).getX() - circleRadius, nodeList.get(i).getY() - circleRadius, circleRadius * 2, circleRadius * 2);
            g.drawString(nodeList.get(i).getLabel(), nodeList.get(i).getX() - 10, nodeList.get(i).getY() - 10);
        }
        for (int i = 0; i < edgeList.size(); i++) {
            g.setColor(Color.BLACK);
            if (edgeList.get(i).getFirst().getX() < edgeList.get(i).getSecond().getX()) {
                g.drawLine(edgeList.get(i).getFirst().getX() + circleRadius, edgeList.get(i).getFirst().getY(), edgeList.get(i).getSecond().getX() - circleRadius, edgeList.get(i).getSecond().getY());
            }
            else if (edgeList.get(i).getFirst().getX() > edgeList.get(i).getSecond().getX()) {
                g.drawLine(edgeList.get(i).getFirst().getX() - circleRadius, edgeList.get(i).getFirst().getY(), edgeList.get(i).getSecond().getX() + circleRadius, edgeList.get(i).getSecond().getY());
            }
            int fx = edgeList.get(i).getFirst().getX();
            int fy = edgeList.get(i).getFirst().getY();
            int sx = edgeList.get(i).getSecond().getX();
            int sy = edgeList.get(i).getSecond().getY();
            g.drawString(edgeList.get(i).getLabel(), Math.min(fx, sx) + (Math.abs(sx-fx) / 2), Math.min(fy, sy) + (Math.abs(sy-fy) / 2));
        }
    }

    //stop highlighting a node
    public void stopHighlighting() {
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setHighlighted(false);
        }
    }
}
