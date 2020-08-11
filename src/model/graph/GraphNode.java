package model.graph;

import java.util.ArrayList;

public class GraphNode<T> {
	public void setContents(T contents) {
		this.contents = contents;
	}

	private T contents;
	private boolean visited;
	private ArrayList<GraphNode<T>> adjacentNodes;
	private ArrayList<Edge<T>> edges;
	private GraphNode<T> last;
	
	public GraphNode(T pContents) {
		this.contents = pContents;
		this.adjacentNodes = new ArrayList<GraphNode<T>>();
		this.edges = new ArrayList<Edge<T>>();
		this.last = null;
		this.visited = false;
	}

	public T getContents() {
		return this.contents;
	}

	public ArrayList<GraphNode<T>> getAdjacentNodes() {
		return this.adjacentNodes;
	}
	
	public ArrayList<Edge<T>> getEdges(){
		return this.edges;
	}
	
	public boolean isVisited() {
		return this.visited;
	}
	
	public void visit() {
		this.visited = true;
	}
	
	public void resetVisit() {
		this.visited = false;
	}
	
	public GraphNode<T> getLast(){
		return this.last;
	}

	public void setLast(GraphNode<T> pLast) {
		this.last = pLast;
	}
	
	public GraphNode<T> getAdjacent(T pValue) {
		for (GraphNode<T> node : this.adjacentNodes) {
			if (node.getContents().equals(pValue)) {
				return node;
			}
		}
		return null;
	}
	
	public Edge<T> getEdge(GraphNode<T> pNode){
		for (Edge<T> edge : this.edges) {
			if (edge.getNodes().get(1).equals(pNode)) {
				return edge;
			}
		}
		return null;
	}
	
	public int getWeight(T pValue) {
		GraphNode<T> node = this.getAdjacent(pValue);
		if (node == null) {
			return Integer.MAX_VALUE;
		}
		Edge<T> edge = this.getEdge(node);
		return edge.getWeight();
	}
	
	public int getWeight(GraphNode<T> pNode) {
		if (this.adjacentNodes.contains(pNode)) {
			Edge<T> edge = this.getEdge(pNode);
			return edge.getWeight();
		} else if(this.equals(pNode)) {
			return 0;
		}
		return Integer.MAX_VALUE;
	}
	
	public void addEdge(GraphNode<T> pNode, int pWeight) {
		if (!this.adjacentNodes.contains(pNode)) {
			this.adjacentNodes.add(pNode);
			this.edges.add(new Edge<T>(this, pNode, pWeight));
		}
	}

	public void removeEdge(GraphNode<T> pNode){
		if(adjacentNodes.contains(pNode)){
			adjacentNodes.remove(pNode);
			edges.remove(getEdge(pNode));
		}
	}
	
	public GraphNode<T> copy(){
		return new GraphNode<T>(this.contents);
	}

	@Override
	public boolean equals(Object pObject) {
		GraphNode<T> node = (GraphNode<T>) pObject;
		return this.getContents().equals(node.getContents());
	}
	
	@Override
	public String toString() {
		return this.contents.toString();
	}
}