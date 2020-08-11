package model.graph;

import java.util.ArrayList;
import java.util.Stack;

public class Kruskal <T> implements IGraphPathGettable<T> {
	private ArrayList<GraphNode<T>> path;
	private ArrayList<GraphNode<T>> invertedPath;
	private ArrayList<GraphNode<T>> nodes;
	private ArrayList<Edge<T>> edges;
	private Graph<T> mst;
	private Stack<GraphNode<T>> stack;
	
	public Kruskal() {
		this.path = new ArrayList<GraphNode<T>>();
		this.invertedPath = new ArrayList<GraphNode<T>>();
		this.nodes = null;
		this.edges = null;
		this.mst = new Graph<T>();
		this.stack = new Stack<GraphNode<T>>();
	}
	@Override
	public ArrayList<GraphNode<T>> getPath(Graph<T> pGraph, T pValue1, T pValue2) {
		// Calculates MST
		pGraph.clearVisits();
		this.nodes = pGraph.getNodes();
		this.edges = pGraph.getEdges();
		this.calculateMST(pGraph);
		
		// Gets nodes from parameter values
		GraphNode<T> start = this.mst.getNode(pValue1);
		GraphNode<T> end = this.mst.getNode(pValue2);
		
		// Prepares structures for generation of path from MST
		pGraph.clearVisits();
		this.invertedPath.clear();
		this.path.clear();
		this.stack.clear();
		this.stack.push(start);
		
		while(!this.stack.isEmpty()) {
			GraphNode<T> current = this.stack.pop();
			current.visit();
			for (GraphNode<T> adjNode : current.getAdjacentNodes()) {
				if (!adjNode.isVisited()) {
					adjNode.visit();
					adjNode.setLast(current);
					this.stack.push(adjNode);
				}
				if (adjNode.equals(end)) {
					this.stack.clear();
					break;
				}
			}
		}
		this.invertedPath = generatePath(this.invertedPath, end);
		for (int invPathIndex = this.invertedPath.size() - 1; invPathIndex >= 0; invPathIndex--) {
			this.path.add(this.invertedPath.get(invPathIndex));
		}
		
		
		return this.path;
	}
	
	private void calculateMST(Graph<T> pGraph) {
		Edge<T> edge;
		ArrayList<GraphNode<T>> edgeNodes;
		Edge<T> newEdge;
		GraphNode<T> newNode1;
		GraphNode<T> newNode2;
		
		for (GraphNode<T> node : this.nodes) {
			this.mst.addNode(node.copy());
		}
		
		for (int edgeIndex = 0; edgeIndex < this.edges.size(); edgeIndex++) {
			edge = this.edges.get(edgeIndex);
			edgeNodes = edge.getNodes();
			if (edgeNodes.get(0).isVisited() && edgeNodes.get(1).isVisited()) {
				continue;
			}
			
			edgeNodes.get(0).visit();
			edgeNodes.get(1).visit();
			newNode1 = this.mst.getNode(edgeNodes.get(0).getContents());
			newNode2 = this.mst.getNode(edgeNodes.get(1).getContents());
			this.mst.addEdge(newNode1, newNode2, edge.getWeight());
			
			if (this.visitMissing()) {
				continue;
			}
			break;
		}
	}
	
	private boolean visitMissing() {
		boolean visitStatus = false;
		for (GraphNode<T> node : this.nodes) {
			if (node.isVisited()) {
				continue;
			}
			visitStatus = true;
		}
		return visitStatus;
	}
	
	private ArrayList<GraphNode<T>> generatePath(ArrayList<GraphNode<T>> pArray, GraphNode<T> pNode){
		if (pNode == null) {
			return pArray;
		} else {
			pArray.add(pNode);
			return generatePath(pArray, pNode.getLast());
		}
	}
}
