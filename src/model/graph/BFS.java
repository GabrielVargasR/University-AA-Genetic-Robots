package model.graph;

import java.util.ArrayList;
import java.util.LinkedList;

public class BFS<T> implements IGraphPathGettable<T> {
    LinkedList<GraphNode<T>> queue;
    ArrayList<GraphNode<T>> path;
    ArrayList<GraphNode<T>> visited;

    public BFS(){
        this.queue = new LinkedList<GraphNode<T>>();
        this.path = new ArrayList<GraphNode<T>>();
        this.visited = new ArrayList<GraphNode<T>>();
    }

	@Override
	public ArrayList<GraphNode<T>> getPath(Graph<T> pGraph, T pStartContent, T pEndContent) {
        GraphNode<T> endNode = executeBFS(pGraph, pStartContent, pEndContent);
        path.clear();
        if(endNode != null){
            buildPath(endNode);
            return path;
        }
        return null;
	}

    private GraphNode<T> executeBFS(Graph<T> pGraph, T pValue1, T pValue2){
        pGraph.clearVisits();
        pGraph.clearLasts();
        queue.clear();

		if (pGraph.contains(pValue1) && pGraph.contains(pValue2)){
            this.visited.clear();
            // Gets nodes from parameter values
			GraphNode<T> start = pGraph.getNode(pValue1);
            GraphNode<T> end = pGraph.getNode(pValue2);
            
            this.visited.add(start);
            this.queue.addLast(start);

            GraphNode<T> node;
            while(!queue.isEmpty()){
                node = queue.remove();
                if(node.equals(end)){
                    return node;
                }
                for (GraphNode<T> adjacentNode : node.getAdjacentNodes()) {
                    if(visited.contains(adjacentNode)){
                        continue;
                    }
                    visited.add(adjacentNode);
                    adjacentNode.setLast(node);
                    queue.addLast(adjacentNode);
                }
            }
        }
        return null;
    }
    private void buildPath(GraphNode<T> pNode) {
		if (pNode.getLast() == null) {
			return;
		}
		this.buildPath(pNode.getLast());
		this.path.add(pNode);
	}

    public static void main(String[] args) {
		Graph g = new Graph<Integer>();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addEdge(4, 1, 1);
		g.addEdge(1, 2, 1);
		//g.addEdge(1, 1, 1);
		g.addEdge(2, 3, 1);
		
		IGraphPathGettable get = new BFS<Integer>();
		int distance = get.getPath(g, 1, 1).size();
		System.out.println(distance);
		distance = get.getPath(g, 1, 3).size();
		System.out.println(distance);
		distance = get.getPath(g, 1, 2).size();
		System.out.println(distance);
		distance = get.getPath(g, 1, 5).size();
		System.out.println(distance);

		System.out.println(g.getEdges().toString());
	}
}