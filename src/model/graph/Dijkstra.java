package model.graph;

import java.util.ArrayList;
import java.util.Hashtable;

public class Dijkstra <T> implements IGraphPathGettable<T> {
	private ArrayList<GraphNode<T>> path;
	private Hashtable<GraphNode<T>, Integer> set;
	private ArrayList<GraphNode<T>> visited;
	private ArrayList<GraphNode<T>> nodes;
	
	public Dijkstra() {
		this.path = new ArrayList<GraphNode<T>>();
		this.set = new Hashtable<GraphNode<T>, Integer>();
		this.visited = new ArrayList<GraphNode<T>>();
		this.nodes = null;
	}
	
	public ArrayList<GraphNode<T>> calculateDijkstra(Graph<T> pGraph, T pValue1, T pValue2){
		
		this.path.clear();
		pGraph.clearVisits();
		
		if (pGraph.contains(pValue1) && pGraph.contains(pValue2)) {
			// Resets structures used for the algorithm
			this.set.clear();
			this.visited.clear();
			this.nodes = pGraph.getNodes();
			
			// Gets nodes from parameter values
			GraphNode<T> start = pGraph.getNode(pValue1);
			GraphNode<T> end = pGraph.getNode(pValue2);
			
			// Initializes set with max integer
			for (GraphNode<T> node : nodes){
				this.set.put(node, Integer.MAX_VALUE);
			}
			
			
			this.set.put(start, start.getWeight(start));
			this.visited.add(start);
			
			for (GraphNode<T> adjacentNode : start.getAdjacentNodes()) {
				this.set.put(adjacentNode, start.getWeight(adjacentNode));
				adjacentNode.setLast(start);
			}
			
			this.dijkstraStep(this.getNextNode(start));
			this.path.add(start);
			this.buildPath(end);
		}
		
		return this.path;
	}
	
	private GraphNode<T> getNextNode(GraphNode<T> pNode){
		
		if (this.visited.size() == this.nodes.size()) {
			return null;
		}
		
		int currentMin = Integer.MAX_VALUE;
		GraphNode<T> currentMinNode = pNode;
		for(GraphNode<T> adjacentNode : pNode.getAdjacentNodes()) {
			if(this.visited.contains(adjacentNode)) {
				continue;
			}
			if (pNode.getWeight(adjacentNode) < currentMin) {
				currentMinNode = adjacentNode;
				currentMin = pNode.getWeight(adjacentNode);
			}
		}
		return currentMinNode;
	}
	
	private void dijkstraStep(GraphNode<T> pNode) {
		
		if (this.nodes.size() == this.visited.size()) {
			return;
		}
		
		
		int currentWeight;
		int newWeight;
		for (GraphNode<T> adjacentNode : pNode.getAdjacentNodes()) {	
			if ((pNode.getWeight(adjacentNode) == Integer.MAX_VALUE) || (this.set.get(pNode) == Integer.MAX_VALUE)) {
				continue;
			}
			currentWeight = this.set.get(adjacentNode);
			newWeight = this.set.get(pNode) + pNode.getWeight(adjacentNode);
			if (newWeight < currentWeight) {
				this.set.put(adjacentNode, newWeight);
				adjacentNode.setLast(pNode);
			}
		}
		
		this.visited.add(pNode);
		this.dijkstraStep(this.getNextNode(pNode));
	}
	
	private void buildPath(GraphNode<T> pNode) {
		if (pNode.getLast() == null) {
			return;
		}
		this.buildPath(pNode.getLast());
		this.path.add(pNode);
	}

	@Override
	public ArrayList<GraphNode<T>> getPath(Graph<T> pGraph, T pStartContent, T pEndContent) {
		return calculateDijkstra(pGraph, pStartContent, pEndContent);
	}
}
