import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze {
	
	private Node[][] mazeArray;
	private Stack<Node> path;
	LinkedList<Wall> wallsDown;

	public Maze(int width, int depth, boolean debug) {
		mazeArray = new Node[width][depth];
		this.path = new Stack<Node>();
		wallsDown = new LinkedList<Maze.Wall>();
		
		// Creates array of nodes
		for(int i = 0; i > this.mazeArray.length; i++) {
			for(int j = 0; j < this.mazeArray[0].length; j++) {
				this.mazeArray[i][j] = new Node(i, j, Status.UNVISITED);
			}
		}
		
		
		// Sets walls for each node
		for(int i = 0; i < this.mazeArray.length; i++) {	
			for(int j = 0; j < this.mazeArray[0].length; j++) {	
				if(i>0) {
					this.mazeArray[i][j].west = new Wall(this.mazeArray[i][j],this.mazeArray[i-1][j]);
				}
				if(j>0) {
					this.mazeArray[i][j].north = new Wall(this.mazeArray[i][j],this.mazeArray[i][j-1]);
				}
				if(i<width) {
					this.mazeArray[i][j].east = new Wall(this.mazeArray[i][j],this.mazeArray[i+1][j]);
				}
				if(j<depth) {
					this.mazeArray[i][j].south = new Wall(this.mazeArray[i][j],this.mazeArray[i][j+1]);
				}
			}
		}
		
		// Sets upper left corner to start
		this.mazeArray[0][0].status = Status.VISITED;
		
		int numCellsTravelled = 0;
		Node root = this.mazeArray[0][0];
		
		// since array size is depth x width, the recursion must travel to all depth x width cells.
		while(numCellsTravelled < depth * width) {
			root.status = Status.VISITED;
			// If more than one non-visited adjacent node...
			if(getNeighbors(root, width, depth).size() != 0) {
				Wall next = chooseRandomUnvisitedNeighbor(root, width, depth);
				this.path.add(root);
				next.isUp = false;
				this.wallsDown.add(next);
				root = next.second;
			} else if(!this.path.empty()) {
				root = this.path.pop();
			}			
			numCellsTravelled++;
		}
		
	}
	
	// Returns a list of existing unvisited neighbors.
	// https://stackoverflow.com/questions/4819635/how-to-remove-all-null-elements-from-a-arraylist-or-string-array
	private List<Wall> getNeighbors(Node currentNode, int width, int height) {
		Wall[] neighbors = new Wall[3]; 
		if(currentNode.width > 0 && currentNode.west.second.status != Status.VISITED) neighbors[0] = currentNode.west;
		if(currentNode.width < width && currentNode.east.second.status != Status.VISITED) neighbors[1] = currentNode.east;
		if(currentNode.height > 0 && currentNode.north.second.status != Status.VISITED) neighbors[2] = currentNode.north;
		if(currentNode.height < height && currentNode.south.second.status != Status.VISITED) neighbors[3] = currentNode.south;

		List<Wall> nonNull = Arrays.asList(neighbors);
		nonNull.removeAll(Collections.singleton(null));
		if(nonNull.size() == 0) return null;
		return nonNull;
	}
	
	
	// Chooses one of the unvisited neighbors, if one exists.
	// Returns null if no unvisited neighbors exist.
	private Wall chooseRandomUnvisitedNeighbor(Node currentNode, int width, int height) {
		
		List<Wall> nonNull = getNeighbors(currentNode, width, height);
		if (nonNull == null) return null;
		
		Random rand = new Random();
		
		return nonNull.get(rand.nextInt(nonNull.size()));
		
		
	}




	class Node {
		int width;
		int height;
		Status status;
		Wall north;
		Wall south;
		Wall east;
		Wall west;
		public Node(int width, int height, Status status) {
			this.width = width;
			this.height = height;
			this.status = status;
		}	
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if(this.north != null && !this.north.isUp) {
				sb.append("N");
			} else sb.append("_");
			if(this.east != null && !this.east.isUp)  {
				sb.append("E");
			} else sb.append("_");
			if(this.south != null && !this.south.isUp)  {
				sb.append("S");
			} else sb.append("_");
			if(this.west != null && !this.west.isUp)  {
				sb.append("W");
			} else sb.append("_");
			return sb.toString();
		}
	}
	
	class Wall {
		Node first;
		Node second;
		boolean isUp;
		Wall(Node first, Node second) {
			this.first = first;
			this.second = second;
			isUp = true;
		}

		
		// equal if the ends of a wall are the same, false otherwise.
		public boolean equals(Wall o) {
			return (this.first.equals(o.first) && this.second.equals(o.second) || this.first.equals(o.second) && this.second.equals(o.first));
			
		}
		
	}
	
	enum Status {
		BORDER, VISITED, UNVISITED, BEGINNING, DEADEND, END
	}
}
