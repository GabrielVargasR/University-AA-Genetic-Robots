package model.graph;

import java.util.ArrayList;

public class Edge<T> implements Comparable <Edge<T>>{
	private ArrayList<GraphNode<T>> nodes;
	private int weight;
	
	public Edge(GraphNode<T> pNode1, GraphNode<T> pNode2, int pWeight) {
		this.nodes = new ArrayList<GraphNode<T>>();
		this.nodes.add(pNode1);
		this.nodes.add(pNode2);
		this.weight = pWeight;
	}
	
	public ArrayList<GraphNode<T>> getNodes(){
		return this.nodes;
	}
	
	public int getWeight() {
		return this.weight;
	}
	

	@Override
	public int compareTo(Edge<T> pOtherEdge) {
		if (this.weight < pOtherEdge.getWeight()) {
			return -1;
		} else if (this.weight > pOtherEdge.getWeight()) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object pObject) {
		if (this == pObject) {
			return true;
		} 
		Edge<T> otherEdge = (Edge<T>) pObject;
		if ((this.nodes.get(0)==otherEdge.getNodes().get(1))&&(this.nodes.get(1)==otherEdge.getNodes().get(0))) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "" + this.nodes.get(0) + "-" + this.nodes.get(1) + ": " + this.weight;
	}
	
}