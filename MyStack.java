
public class MyStack<Type> {
	
	private class MyStackNode {
		Type nodeObject;
		MyStackNode nodePrevious;
		
		private MyStackNode(Type inputObject, MyStackNode inputNodePrevious) {
			this.nodeObject = inputObject;
			nodePrevious = inputNodePrevious;
		}
		
		private Type getNodeObject() {
			return this.nodeObject;
		}
		
		
		private MyStackNode getNodePrevious() {
			return this.nodePrevious;
		}
		
		@Override
		public String toString() {
			return "NODE:   " + this.nodeObject.toString();
		}
	}
	
	private MyStackNode topNode;
	
	public MyStack() {
		this.topNode = null;
	}
	
	public boolean isEmpty() {
		return this.topNode == null;
	}
	
	public void push(Type item) {
		// sets the top node to a new Node where the new node's previous node is the old top node.
		this.topNode = new MyStackNode(item, this.topNode);
	}
	
	public Type pop() {
		Type poppedNodeObject = this.topNode.getNodeObject();
		// Sets the top node to the previous node before returning the original top node's object.
		this.topNode = this.topNode.getNodePrevious();
		return poppedNodeObject;
	}

	public Type peek() {
		return this.topNode.getNodeObject();
		
	}
	
	public int size() {
		MyStack<Type> tempStack = new MyStack<Type>();
		int counter = 0;
		//for each node, add a node to the temp stack and increase the counter.
		while (!this.isEmpty()) {
			tempStack.push(this.pop());
			counter++;
		}
		// Add nodes back
		while (!tempStack.isEmpty()) {
			this.push(tempStack.pop());
		}
		return counter;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("STACK: ");
		// Prints each node.
		MyStack<Type> tempStack = new MyStack<Type>();
		
		//for each node, add a node to the temp stack and increase the counter.
		while (!this.isEmpty()) {
			Type tempElement = this.pop();
			sb.append("["  + tempElement.toString() + "] ");
			tempStack.push(tempElement);			
		}
		// Add nodes back
		while (!tempStack.isEmpty()) {
			this.push(tempStack.pop());
		}
		return sb.toString();
		
	}
}
