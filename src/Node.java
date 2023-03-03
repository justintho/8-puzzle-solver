
public class Node implements Comparable<Node>{
	Node parent;
	private int h;
	private int g;
	private String puzzle;
	
	//Constructor
	public Node(Node parent, int h, int g, String puzzle) {
		this.parent = parent;
		this.h = h;
		this.g = g;
		this.puzzle = puzzle;
	}
	
	//Returns parent node
	public Node getParent() {
		return parent;
	}
	
	//Returns h value
	public int getH() {
		return h;
	}
	
	//Returns g value
	public int getG() {
		return g;
	}

	//Returns the state of the puzzle
	public String getPuzzle() {
		return puzzle;
	}

	//CompareTo function to help PriorityQueue choose lowest cost node.
	//If f value is less than other node, return -1;
	//Else if f value is greater than other node, return 1;
	//Else return 0 for equal
	@Override
	public int compareTo(Node n) {
        if((this.getH() + this.getG()) < (n.getH() + n.getG())){
            return -1;
        }
        else if((this.getH() + this.getG()) > (n.getH() + n.getG())){
            return 1;
        }
        else
            return 0;
    }
}
