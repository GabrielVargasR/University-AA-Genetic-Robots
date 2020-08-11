package model.graph;

import java.util.ArrayList;

public class Warshall<T> implements IGraphPathGettable<T> {
    private static Warshall singleton;
    private Graph<T> original;
    private Graph<T> connectionGraph;
    public Warshall(){
        connectionGraph = null;
        original = null;
    }
    public static Warshall getInstance(){
        if (singleton == null) {
            singleton = new Warshall();
        }
        return singleton;
    }
    @Override
    public ArrayList<GraphNode<T>> getPath(Graph<T> pGraph, T pStartContent, T pEndContent) {
        calculateWarshall(pGraph);
        return getPath(pStartContent, pEndContent);
    }

    public Graph<T> getConnectionGraph() {
        return connectionGraph;
    }

    public void calculateWarshall(Graph<T> pGraph){
        initValues(pGraph);
        for (GraphNode<T> linkNode: connectionGraph.getNodes()
             ) {
            for (GraphNode<T> sourceNode: connectionGraph.getNodes()
            ) {
                for (GraphNode<T> endNode: connectionGraph.getNodes()
                ) {
                    if (connectionGraph.areAdjacent(sourceNode,endNode)){
                        continue;
                    }
                    if(connectionGraph.areAdjacent(sourceNode,linkNode) && connectionGraph.areAdjacent(linkNode,endNode)){
                        addConnection(sourceNode,endNode,linkNode);
                    }
                }
            }
        }
    }
    private ArrayList<GraphNode<T>> getPath(T startContent, T endContent){
        ArrayList<GraphNode<T>> list = new ArrayList<GraphNode<T>>();
        GraphNode<T> startNode = connectionGraph.getNode(startContent);
        for (GraphNode<T> node:startNode.getAdjacentNodes()
        ) {
            if(node.getContents().equals(endContent)){
                getOriginalNodePath(getPath(node), list);
            }
        }
        return list;
    }
    private void initValues(Graph<T> pGraph){
        original = pGraph;
        connectionGraph = duplicateGraph(pGraph);
    }
    private ArrayList<GraphNode<T>> getPath(GraphNode<T> pNode){
        ArrayList<GraphNode<T>> list = new ArrayList<GraphNode<T>>();
        while(pNode != null){
            list.add(0,pNode);
            pNode = pNode.getLast();
        }
        return list;
    }
    private void getOriginalNodePath(ArrayList<GraphNode<T>> pCopyPath,ArrayList<GraphNode<T>> pRetPath){
        for (GraphNode<T> copyPathNode:pCopyPath
             ) {
            pRetPath.add(original.getNode(copyPathNode.getContents()));
        }
    }
    private void addConnection(GraphNode<T> pSourceNode, GraphNode<T> pEndNode,GraphNode<T> pLinkNode){
        GraphNode<T> newNode = new GraphNode<T>(pEndNode.getContents());
        newNode.setLast(getSourceLinkNodeCopy(pSourceNode,pLinkNode));
        pSourceNode.addEdge(newNode,0);
    }
    private GraphNode<T>getSourceLinkNodeCopy(GraphNode<T> pSourceNode,GraphNode<T> pLinkNode){
        for (GraphNode<T> node:pSourceNode.getAdjacentNodes()
             ) {
            if(pLinkNode.equals(node)){
                return node;
            }
        }
        return null;
    }
    private Graph<T> duplicateGraph(Graph<T> pGraph){
        Graph<T> newGraph = new Graph<T>();
        GraphNode<T> newGraphNode;
        for (GraphNode<T> node:pGraph.getNodes()
             ) {
            newGraphNode = new GraphNode<T>(node.getContents());
            newGraph.addNode(newGraphNode);
            for (GraphNode<T> adjacent:node.getAdjacentNodes()
                 ) {
                newGraphNode.addEdge(new GraphNode<T>(adjacent.getContents()),0);
            }
        }
        return newGraph;
    }

    public static void main(String[] args) {
        Graph<Integer> g1 = new Graph<Integer>();
        Warshall<Integer> w = Warshall.getInstance();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.addNode(5);
        g1.addNode(6);
        g1.addEdge(1,2,0);
        g1.addEdge(2,3,0);
        g1.addEdge(6,4,0);
        g1.addEdge(6,3,0);
        g1.print();
        w.calculateWarshall(g1);
        System.out.println(w.getPath(1,4).toString());
    }
}
