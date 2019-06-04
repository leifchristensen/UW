import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Maze {
	
	private Node[][] mazeArray;
	private Queue<Node> path;
	private ArrayList<Wall> wallList;

	public Maze(int width, int depth, boolean debug) {
		this.path = new LinkedList<Maze.Node>();
		mazeArray = new Node[width][depth];
		// Sets borders to border status and inner nodes to unvisited		
		for(int i = 0; i < this.mazeArray.length; i++) {
			for(int j = 0; j < this.mazeArray[0].length; j++) {
				this.mazeArray[i][j] = new Node(i,j,Status.UNVISITED);
					
				
			}
		}
		
		// Sets walls for each node
		for(int i = 0; i < this.mazeArray.length; i++) {
			for(int j = 0; j < this.mazeArray[0].length; j++) {
				if(i>0) this.mazeArray[i][j].west = this.mazeArray[i-1][j];
				if(j>0) this.mazeArray[i][j].north = this.mazeArray[i][j-1];
				if(i<width) this.mazeArray[i][j].east = this.mazeArray[i+1][j];
				if(j<depth) this.mazeArray[i][j].south = this.mazeArray[i][j+1];
			}
		}
		
		// Sets upper left corner to start
		this.mazeArray[0][0].status = Status.BEGINNING;
		this.path.add(this.mazeArray[0][0]);
		// Add walls of beginning to the wall list
		
		addUnvisitedNeighbor(this.mazeArray[width][depth]);
		
		
		
	}
	



	private void addUnvisitedNeighbor(Node endNode) {
		// TODO Auto-generated method stub
		
	}




	class Node {
		int width;
		int height;
		Status status;
		Node north;
		Node south;
		Node east;
		Node west;
		public Node(int width, int height, Status border) {
			this.width = width;
			this.height = height;
			this.status = status;
		}	
		
	}
	
	class Wall {
		Node first;
		Node second;
		Wall(Node first, Node second) {
			this.first = first;
			this.second = second;
		}
	}
	
	enum Status {
		BORDER, VISITED, UNVISITED, BEGINNING, DEADEND, END
	}
}
