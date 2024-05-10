import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

	public class GraphCreator implements ActionListener, MouseListener 
	{
		
		JFrame frame = new JFrame();
		GraphPanel panel = new GraphPanel();
		JButton nodeB = new JButton("Node");
		JButton edgeB = new JButton("Edge");
		JTextField labelsTF = new JTextField("A");
		JTextField FNode = new JTextField("First");
		JTextField SNode = new JTextField("Second");
		JButton connectedB = new JButton("Test Connected");
		Container west = new Container();
		Container east = new Container();
		Container south = new Container();
		JTextField salesmanstart = new JTextField("A");
		JButton salesmanB = new JButton("Shortest Path");
		final int NODE_CREATE = 0;
		final int EDGE_FIRST = 1;
		final int EDGE_SECOND = 2;
				
		int state = NODE_CREATE;
		Node first = null;
		ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
		
		public GraphCreator() {
			frame.setSize(800,400);
			frame.setLayout(new BorderLayout());
			frame.add(panel, BorderLayout.CENTER);
			west.setLayout(new GridLayout(3,1));
			west.add(nodeB);
			nodeB.addActionListener(this);
			nodeB.setBackground(Color.GREEN);
			west.add(edgeB);
			edgeB.addActionListener(this);
			edgeB.setBackground(Color.GRAY);
			west.add(labelsTF);
			frame.add(west, BorderLayout.WEST);
			east.setLayout(new GridLayout(3,1));
			east.add(FNode);
			east.add(SNode);
			east.add(connectedB);
			connectedB.addActionListener(this);
			frame.add(east, BorderLayout.EAST);
			panel.addMouseListener(this);
			south.setLayout(new GridLayout(1,2));
			south.add(salesmanstart);
			south.add(salesmanB);
			salesmanB.addActionListener(this);
			frame.add(south, BorderLayout.SOUTH);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		
		public static void main(String[] args) 
		{
			new GraphCreator();
	
		}
	
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) 
		{
			
		}
	
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if(state == NODE_CREATE){
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
			}
			else if (state == EDGE_FIRST) 
			{
				Node n = panel.getNode(e.getX(), e.getY());
				if (n != null) 
				{
					first = n;
					state = EDGE_SECOND;
					n.setHighlighted(true);
				}
			}
			else if (state == EDGE_SECOND) 
			{
				Node n = panel.getNode(e.getX(), e.getY());
				if (n != null && !first.equals(n)) 
				{
					String s = labelsTF.getText();
					boolean valid = true;
					for (int i = 0; i < s.length(); i++) 
					{
						if (Character.isDigit(s.charAt(i))==false)
						{
							valid = false;
						}
						if(valid == true) 
						{
							first.setHighlighted(false);
							panel.addEdge(first, n, labelsTF.getText());
							first = null;
							state = EDGE_FIRST;
						}
						else 
						{
							JOptionPane.showMessageDialog(frame, "Can only have digits in edge labels");
						}
					}
				}
			}
			frame.repaint();
			
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {}
	
		@Override
		public void mouseExited(MouseEvent e) {}
	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource().equals(nodeB)) {
				nodeB.setBackground(Color.GREEN);
				edgeB.setBackground(Color.GRAY);
				state = NODE_CREATE;
			}
			if (e.getSource().equals(edgeB)) 
			{
				nodeB.setBackground(Color.GREEN);
				edgeB.setBackground(Color.GRAY);
				state = EDGE_FIRST;
				panel.stopHighlighting();
				frame.repaint();
			}
			if (e.getSource().equals(connectedB)) 
			{
				if (panel.nodeExists(FNode.getText()) == false)
				{
					JOptionPane.showMessageDialog(frame,"First node is not in your graph");
				}
				else if (panel.nodeExists(SNode.getText()) == false) 
				{
					JOptionPane.showMessageDialog(frame,"Second node is not in your graph");
				}
				else
				{
					Queue queue = new Queue();
					ArrayList<String> connectedList = new ArrayList<String>();
					connectedList.add(panel.getNode(FNode.getText()).getLabel());
					ArrayList<String> edges = panel.getConnectedLabels(FNode.getText());
					for (int i = 0; i < edges.size(); i++) 
					{
						//check each connected node
						queue.enqueue(edges.get(i));
					}
					while(queue.isEmpty()==false) 
					{
						String currentNode = queue.dequeue();
						if (connectedList.contains(currentNode) == false)
						{
							connectedList.add(currentNode);
						}
						edges = panel.getConnectedLabels(currentNode);
						for (int i = 0; i < edges.size(); i++) 
						{
							if (connectedList.contains(edges.get(i)) == false) 
							{
								queue.enqueue(edges.get(i));
							}
						}
					}
					if (connectedList.contains(SNode.getText())) 
					{
						JOptionPane.showMessageDialog(frame, "Connected");
					}
					else 
					{
						JOptionPane.showMessageDialog(frame, "Not Connected");
					}
				}
			}
			if (e.getSource().equals(salesmanB)) 
			{
				if (panel.getNode(salesmanstart.getText())!= null) {
					traveling(panel.getNode(salesmanstart.getText()), new ArrayList<Node>(), 0);	
				}
				else {
					JOptionPane.showMessageDialog(frame, "Not a valid starting node");
				}
			}
		
	
	}
		private void traveling(Node n, ArrayList<Node> path, int total) {
	        path.add(n);

	        // If all nodes are visited
	        if (path.size() == panel.getNodeCount()) {
	            handleCompletedPath(path, total);
	        } else {
	        	ArrayList<Edge> getEdges = panel.getEdges(n);
	            for (Edge edge : getEdges) {
	                Node nextNode = edge.getOtherEnd(n);
	                if (!path.contains(nextNode)) {
	                    int newTotal = total + Integer.parseInt(edge.getLabel());
	                    traveling(nextNode, new ArrayList<>(path), newTotal);
	                }
	            }
	        }
	    }

	    private void handleCompletedPath(ArrayList<Node> path, int total) {
	        path.add(path.get(0)); // Adding the starting node to complete the cycle
	        completed.add(new ArrayList<>(path)); // Add a copy of the path to the completed list
	        // Logic to handle the completed path, like storing the shortest path found so far
	    }
	}
