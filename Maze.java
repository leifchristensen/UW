import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

import javax.swing.event.ListSelectionEvent;

public class Maze {
	
	private Node[][] mazeArray;
	private HashSet<Wall> wallSet;
	private HashMap<Node, HashSet<Wall>> mapNodeToWalls;
	private HashMap<Wall, Node[]> mapWallToNodes;
	private Stack<Node> path;
	private LinkedList<Node> pathToEnd;

	public Maze(int width, int depth, boolean debug) {
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
		for(int i = 1; i < this.mazeArray.length-1; i++) {	
			for(int j = 1; j < this.mazeArray[0].length-1; j++) {	
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i + 1][j]));
				
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i-1][j]));
				
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i][j+1]));
				
					this.wallSet.add(new Wall(this.mazeArray[i][j], this.mazeArray[i][j-1]));
					
			}
		}
		
		// Maps walls to Nodes
		for(int i = 0; i < this.mazeArray.length; i++) {	
			for(int j = 0; j < this.mazeArray[0].length; j++) {	
				Node searchNode = this.mazeArray[i][j];
				HashSet<Wall> resultWalls = new HashSet<Wall>();
				this.wallSet.stream().filter(w -> w.containsNode(searchNode)).forEach(resultWalls::add);
				this.mapNodeToWalls.put(this.mazeArray[i][j], resultWalls);
			}
		}
		
		// Map Nodes to Walls
		for(Wall w : wallSet) {
			Node[] nodesFromWall = new Node[] {w.first, w.second};
			mapWallToNodes.put(w, nodesFromWall);
		}
		
		// Sets upper left corner to start
		Node root = this.mazeArray[1][1];
		this.path.add(root);
		
		while(!this.path.isEmpty()) {
			root.status = Status.VISITED;
			if(root.width == width && root.height == depth) {
				((Stack<Node>) this.path.clone()).forEach(this.pathToEnd::add);
			}
			// If more than one non-visited adjacent node...
			List<Wall> neighbors = getNeighbors(root, width, depth);
			if(neighbors != null && neighbors.size() != 0) {
				this.path.add(root);
				Wall nextWall = chooseRandomUnvisitedNeighbor(root, width, depth);
				nextWall.isUp = false;
				Node[] nodesOfWall = mapWallToNodes.get(nextWall);
				// Updates wall map to use down wall
				mapWallToNodes.put(nextWall, nodesOfWall);
				
				//this.wallsDown.add(next);
				// If the first node of the wall is the current root, update the root to the wall's second node
				if(nextWall.first.equals(root)) {
					root = nextWall.second;
				} else {
					root = nextWall.first;
				}
				
				
			} else if(!this.path.empty()) {
				root = this.path.pop();
			}			
			
			if(debug) display();
		}
		
		// Prints the maze
		display();
	}
	
	public void display() {
		StringBuilder sb = new StringBuilder();
		for(int j = 1; j < this.mazeArray[0].length-1; j++) {
			//Top Row
			for(int i = 1; i < this.mazeArray.length-1; i++) {				
				sb.append("X");
				//North
				Node searchNode = mazeArray[i][j];
				Node searchNodeNorth = mazeArray[i-1][j];
				HashSet<Wall> searchWalls = this.mapNodeToWalls.get(searchNode);
				Wall resultWall = searchWalls.stream().filter(w -> w.containsNode(searchNodeNorth)).findFirst().orElse(null);
				if(resultWall.isUp) {
					sb.append("X");
				} else sb.append(" ");
							
			} 
			sb.append("X");
			sb.append(System.getProperty("line.separator"));
			
			//Mid Row Row
			for(int i = 1; i < this.mazeArray.length-1; i++) {
				//West
				Node searchNode = mazeArray[i][j];
				HashSet<Wall> searchWalls = this.mapNodeToWalls.get(searchNode);
				Wall resultWall = searchWalls.stream().filter(w -> w.containsNode(searchNode)).findFirst().orElse(null);
				if(resultWall.isUp) {
					sb.append("X");
				} else sb.append(" ");
				//Center
				if(this.pathToEnd.contains(searchNode)) {
					sb.append("+");
				} else if (searchNode.status == Status.VISITED) {
					sb.append("V");
				} else sb.append(" ");
								
			}
			sb.append("X");	
			sb.append(System.getProperty("line.separator"));
			
		}
		sb.append(System.getProperty("line.separator"));
		System.out.print(sb.toString());
	}
	
	
	// Returns a list of existing unvisited neighbors.
	// https://stackoverflow.com/questions/4819635/how-to-remove-all-null-elements-from-a-arraylist-or-string-array
	private List<Wall> getNeighbors(Node currentNode, int width, int height) {
		//Wall[] neighbors = new Wall[4]; 
		List<Wall> returnList = new LinkedList<Maze.Wall>();
		HashSet<Wall> neighbors = mapNodeToWalls.get(currentNode);
		neighbors.forEach(w -> {
			if(w.first.status == Status.UNVISITED) {
				returnList.add(w);
			}
		});
		return returnList;
	}
	
	
	// Chooses one of the unvisited neighbors, if one exists.
	// Returns null if no unvisited neighbors exist.
	private Wall chooseRandomUnvisitedNeighbor(Node currentNode, int width, int height) {
		
		List<Wall> nonNull = new LinkedList<Maze.Wall>();		
		nonNull.addAll(getNeighbors(currentNode, width, height));
		
		Random rand = new Random();
		
		return nonNull.get(rand.nextInt(nonNull.size()));
		
		
	}


	private void printMaze() {
		for(int j = 0; j < this.mazeArray[0].length; j++) {
		for(int i = 0; i < this.mazeArray.length; i++) {
				
			System.out.print(this.mazeArray[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		
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
			sb.append(this.height);
			sb.append(".");
			sb.append(this.width);
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
