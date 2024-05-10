import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	ArrayList<Node> nodeList =  new ArrayList<Node>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	int circleRadius = 20;
	
	ArrayList<ArrayList<Boolean>> ajm = new ArrayList<ArrayList<Boolean>>();
	
	public GraphPanel() {
		super();
	}
	
	public ArrayList<String> getConnectedLabels(String label){//8.41 Part 2 Creator 2
		ArrayList<String> toReturn = new ArrayList<String>();
		int b = getIndex(label);		
		for (int i = 0; i < ajm.size(); i++) {
			if ((ajm.get(b).get(i)== true) && (nodeList.get(i).getLabel().equals(label)== false)) {
				toReturn.add(nodeList.get(i).getLabel());
			}
		}
		return toReturn;
	}
	
	
	public void printAdjacency() {
		System.out.println();
		for (int i = 0; i < ajm.size(); i++) {
			for (int j = 0; j <  ajm.get(0).size(); j++) {
				System.out.print(ajm.get(i).get(j)+ "\t");
			}
			System.out.println();
		}
	}
	public void addNode(int newx, int newy, String newlabel) {
		nodeList.add(new Node(newx, newy, newlabel));
		ajm.add(new ArrayList<Boolean>());
		for (int i = 0; i < ajm.size()-1; i++) {
			ajm.get(i).add(false);
		}
		for (int i = 0; i < ajm.size(); i++) {
			ajm.get(ajm.size()-1).add(false);
		}
		printAdjacency();
	}
	
	public void addEdge (Node first, Node second, String newlabel) {//12.02 part6
		edgeList.add(new Edge(first,second, newlabel));
		int findex = 0;
		int sindex = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			if (first.equals(nodeList.get(i))) {
				findex=i;
			}
			if (second.equals(nodeList.get(i))) {
				sindex=i;
			}
		}
		ajm.get(findex).set(sindex, true);
		ajm.get(sindex).set(findex, true);
		printAdjacency();
	}
	
	public Node getNode (int x, int y) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			//Pythagorean theorem 
			double radius = Math.sqrt(Math.pow(x-node.getX(), 2)+ Math.pow(y-node.getY(),2));
			
			if (radius < circleRadius) {
				return node;
			}
		}
		return null;
	}
	public Node getNode(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (s.equals(node.getLabel())) {
				return node;
			}
		}
		return null;
	}
	
	public int getIndex(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (s.equals(node.getLabel())) {
				return i;
			}
		}
		return -1;
	}
	
	
	public boolean nodeExists(String s) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (s.equals(nodeList.get(i).getLabel())) {
				return true;
			}
		}
		return false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draw stuff
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).setHighlighted() == true) {
				g.setColor(Color.red);
			}
			else {
				g.setColor(Color.black);
			}
			g.drawOval(nodeList.get(i).getX()- circleRadius, nodeList.get(i).getY() - circleRadius, circleRadius*2, circleRadius*2);
			g.drawString(nodeList.get(i).getLabel(),nodeList.get(i).getX()-3,nodeList.get(i).getY()+3);
		}
		for (int i = 0; i < edgeList.size(); i++) {
			g.setColor(Color.BLACK);
			g.drawLine(edgeList.get(i).getFirst().getX(), 
						edgeList.get(i).getFirst().getY(), 
						edgeList.get(i).getSecond().getX(), 
						edgeList.get(i).getSecond().getY());
			int fx = edgeList.get(i).getFirst().getX();
			int fy = edgeList.get(i).getFirst().getY();
			int sx = edgeList.get(i).getSecond().getX();
			int sy = edgeList.get(i).getSecond().getY();
			g.drawString(edgeList.get(i).getLabel(), ((Math.min(fx, sx)+ 
					Math.max(sx, fx)) / 2),((Math.min(fy, sy)+ Math.max(sy,fy)) / 2));
		}
		
	
	}
	public void stopHighlighting() {
		for (int i = 0; i < nodeList.size(); i++) {
			nodeList.get(i).setHighlighted(false);
		}
	}
	public int getNodeCount() {
		return nodeList.size();
	}
	public ArrayList<Edge> getEdges(Node n)
	{
		ArrayList<Edge> connectedEdges = new ArrayList<>();
		for (Edge edge : edgeList) {
			if(edge.connectsToNode(n)) {
				connectedEdges.add(edge);
			}
		}
		return connectedEdges;
	}
	
	
	
	
}
