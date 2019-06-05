/*
 * Leif Christensen
 * Assignment 5
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze {
	
	private int width;
	private int depth;
	private boolean debug;
	
	private Node[][] mazeArray;
	private HashSet<Wall> wallSet;
	private HashMap<Node, HashSet<Wall>> mapNodeToWalls;
	private HashMap<Wall, Node[]> mapWallToNodes;
	private Stack<Node> path;
	private LinkedList<Node> pathToEnd;

	public Maze(int width, int depth, boolean debug) {
		this.width = width;
		this.depth = depth;
		this.debug = debug;
		
		mazeArray = new Node[width+2][depth+2];
		wallSet = new HashSet<Maze.Wall>();
		this.mapNodeToWalls = new HashMap<Maze.Node, HashSet<Wall>>();
		this.mapWallToNodes = new HashMap<Maze.Wall, Maze.Node[]>();
		this.path = new Stack<Node>();
		this.pathToEnd = new LinkedList<Maze.Node>();
		
		// Creates array of nodes
		for(int i = 0; i < this.mazeArray.length; i++) {
			for(int j = 0; j < this.mazeArray[0].length; j++) {
				this.mazeArray[i][j] = new Node(i, j, Status.UNVISITED);
				if(i==0||i==mazeArray.length-1||j==0||j==mazeArray[0].length-1) {
					this.mazeArray[i][j].status = Status.BORDER;
				}
			}
		}
		
		// Creates set of unique walls
		for(int i = 0; i < this.mazeArray.length-1; i++) {	
			for(int j = 0; j < this.mazeArray[0].length-1; j++) {	
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i + 1][j]));
				
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i][j+1]));
					
			}
		}
		
		// Maps Nodes to Walls
		for(int i = 0; i < this.mazeArray.length; i++) {	
			for(int j = 0; j < this.mazeArray[0].length; j++) {	
				Node searchNode = this.mazeArray[i][j];
				HashSet<Wall> resultWalls = new HashSet<Wall>();
				this.wallSet.stream().filter(w -> w.containsNode(searchNode)).forEach(resultWalls::add);
				this.mapNodeToWalls.put(this.mazeArray[i][j], resultWalls);
			}
		}
		
		// Map Walls to Nodes
		for(Wall w : wallSet) {
			Node[] nodesFromWall = new Node[] {w.first, w.second};
			mapWallToNodes.put(w, nodesFromWall);
		}
		
		runPath();
		
		// Prints the maze
		display();
	}
	
	private void runPath() {
		// Sets upper left corner to start
				Node root = this.mazeArray[1][1];
				this.path.add(root);
				
				while(!this.path.isEmpty()) {
					root.status = Status.VISITED;
					
					// If more than one non-visited adjacent node...
					List<Wall> neighbors = getNeighbors(root, width, depth);
					if(neighbors != null && neighbors.size() != 0) {
						// Adds the node to the path. If dead-end, path is popped until a node with a neighbor is returned.
						// This ensures that the path always contains the shortest path to the end.
						this.path.add(root);
						
						// Gets a wall from the current node to an unvisited neighbor.
						Wall nextWall = chooseRandomUnvisitedNeighbor(root, width, depth);
						
						// Sets the wall to down and updates the wall map.
						nextWall.isUp = false;						
						Node[] nodesOfWall = mapWallToNodes.get(nextWall);
						mapWallToNodes.put(nextWall, nodesOfWall);
						
						// Set the next node to the other side of the wall.
						// If the first node of the wall is the current root, update the root to the wall's second node
						if(nextWall.first.equals(root)) {
							root = nextWall.second;
						} else {
							root = nextWall.first;
						}
						
						
					} else if(!this.path.empty()) {
						root = this.path.pop();
					}			
					if(root.width == width && root.height == depth) {
						((Stack<Node>) this.path.clone()).forEach(this.pathToEnd::add);
						pathToEnd.add(root);
					}
					if(debug) display();
				}
	}
	
	public void display() {
		StringBuilder sb = new StringBuilder();
		for(int j = 1; j < this.mazeArray[0].length-1; j++) {
			//Top Row
			for(int i = 1; i < this.mazeArray.length-1; i++) {				
				sb.append("X ");
				//North
				Node searchNode = mazeArray[i][j];
				Node searchNodeNorth = mazeArray[i][j-1];
				
				// Searches the map of nodes to walls for a wall from the current node to the north node.
				HashSet<Wall> searchWalls = this.mapNodeToWalls.get(searchNode);
				Wall resultWall = searchWalls.stream().filter(w -> w.containsNode(searchNodeNorth)).findFirst().orElse(null);
				
				if(resultWall.isUp) {
					sb.append("X ");
				} else sb.append("  ");
							
			} 
			sb.append("X ");
			sb.append(System.getProperty("line.separator"));
			
			//Mid Row 
			for(int i = 1; i < this.mazeArray.length-1; i++) {
				//West
				Node searchNode = mazeArray[i][j];
				Node searchNodeWest = mazeArray[i-1][j];
				
				HashSet<Wall> searchWalls = this.mapNodeToWalls.get(searchNode);
				Wall resultWall = searchWalls.stream().filter(w -> w.containsNode(searchNodeWest)).findFirst().orElse(null);
				
				if(resultWall.isUp) {
					sb.append("X ");
				} else sb.append("  ");
				//Center
				if(this.pathToEnd.contains(searchNode)) {
					sb.append("+ ");
				} else if (!path.isEmpty() && searchNode.status == Status.VISITED) {
					sb.append("v ");
				} else sb.append("  ");
								
			}
			sb.append("X");	
			sb.append(System.getProperty("line.separator"));
			
		}
		for(int i = 1; i < this.mazeArray.length-1; i++) {				
			sb.append("X X ");
		}
		sb.append("X");
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		System.out.print(sb.toString());
	}
	
	
	// Returns a list of existing unvisited neighbors.
	private List<Wall> getNeighbors(Node currentNode, int width, int height) {
		//Wall[] neighbors = new Wall[4]; 
		List<Wall> returnList = new LinkedList<Maze.Wall>();
		HashSet<Wall> neighbors = mapNodeToWalls.get(currentNode);
		neighbors.forEach(w -> {
			if(w.otherNode(currentNode).status == Status.UNVISITED) {
				returnList.add(w);
			}
		});
		return returnList;
	}
	
	
	// Chooses one of the unvisited neighbors, if one exists.
	private Wall chooseRandomUnvisitedNeighbor(Node currentNode, int width, int height) {
		List<Wall> nonNull = new LinkedList<Maze.Wall>();		
		nonNull.addAll(getNeighbors(currentNode, width, height));
		Random rand = new Random();
		return nonNull.get(rand.nextInt(nonNull.size()));		
	}

	
	class Node {
		int width;
		int height;
		Status status;
		public Node(int width, int height, Status status) {
			this.width = width;
			this.height = height;
			this.status = status;
		}	
		
		public String toString() {
			StringBuilder sb = new StringBuilder("|");
			sb.append(this.width);
			sb.append(".");
			sb.append(this.height);
			sb.append("|");
			
			if(pathToEnd.contains(this)) {
				sb.append("*");
			} else sb.append(" ");
			

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
		
		public boolean containsNode(Node searchNode) {
			if (searchNode == null) return false;
			if (searchNode == first || searchNode.equals(second)) return true;
			return false;
		}

		// given an end node, returns the other end node.
		public Node otherNode(Node searchNode) {
			if (searchNode == null) return null;
			if (searchNode == this.first) return second;
			if (searchNode == this.second) return first;
			return null;
		}
		
		public String toString() {
			return "::" + first.width + "." + first.height + "-" + second.width + "." + second.height + "::" + "\r\n";
		}
		
		public int hashCode() {
			return this.first.hashCode() * this.second.hashCode();
			
		}
		
		// equal if the ends of a wall are the same, false otherwise.
		public boolean equals(Wall o) {
			boolean matchFirst = false;
			boolean matchSecond = false;
			
			if(o == null) return false;
			// First Node
			if(this.first == null) {
				if(this.first == o.first || this.first == o.second) matchFirst = true;
			} else {
				if(this.first.equals(o.first) || this.first.equals(o.second)) matchFirst = true;
			}
			// Second Node
			if(this.second == null) {
				if(this.second == o.first || this.second == o.second) matchSecond = true;
			} else {
				if(this.second.equals(o.first) || this.second.equals(o.second)) matchFirst = true;
			}
			return matchFirst && matchSecond;
			
		}
		
	}
	
	enum Status {
		VISITED, UNVISITED, BORDER
	}
}
