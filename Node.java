
public class Node {

	int x;
	int y; 
	String label;
    boolean highlighted;
	
	public Node(int newx, int newy, String newlabel) {
		x = newx;
		y = newy;
		label = newlabel;
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean setHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	
	
}
