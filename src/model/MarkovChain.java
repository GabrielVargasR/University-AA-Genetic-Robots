package model;

import java.util.Random;

public class MarkovChain {

    private int currentState;
    private int initialState;
    private int[][] graph;
    private final int STATES; 

    public MarkovChain(int pStatesNumber,int pInitialState){
        initialState = pInitialState;
        STATES = pStatesNumber;
        graph = new int[STATES][STATES];
    }

    public void resetToInitialState(){
        currentState = initialState;
    }

    public void setCurrentState(int pCurrentState) {
        this.currentState = pCurrentState;
    }
    //*Parametros:
    //* Byte array of size STATES x STATES with graph edges wights
    //* Index to start getting bytes from array
    public void assignGraphWeight(byte[] pWeights,int pStartIndex){
        int weightsIndex = pStartIndex;
        for ( int row = 0; row < STATES; row++) {
            for (int col = 0; col < STATES; col++) {
                graph[row][col] = pWeights[weightsIndex];
                weightsIndex++;
            }
        }
    }

    public int getNextMove(int[] pAdjacentStates){
        int[] edgesWeight = new int[4];
        int edgesSum = 0;
        int adjacentState;

        for (int adjacentPosition = 0; adjacentPosition < pAdjacentStates.length; adjacentPosition++) {
            adjacentState = pAdjacentStates[adjacentPosition];
            edgesWeight[adjacentPosition] = graph[currentState][adjacentState];
            edgesSum+= edgesWeight[adjacentPosition];
        }

        double[] edgesProb = normalizeEdgesProb(edgesWeight, edgesSum);
        int pickedAdjacentPos = pickAdjacentPosition(edgesProb);
        return pickedAdjacentPos;
    }

    private double[] normalizeEdgesProb(int[] pEdgesWeight,int pEdgesSum){
        double[] edgesProb = new double[pEdgesWeight.length];
        for (int adjacentPosition = 0; adjacentPosition < edgesProb.length; adjacentPosition++) {
            edgesProb[adjacentPosition] = (double)pEdgesWeight[adjacentPosition] / pEdgesSum;
        }
        return edgesProb;
    }
    private int pickAdjacentPosition(double[] pEdgesProbability){
        Random random = new Random();
        double randomBoundary = random.nextDouble();
        double probSum = 0;
        int chosenEdge = 0;
        while(probSum < randomBoundary){
            probSum = probSum + (pEdgesProbability[chosenEdge++]);
        } 
        return chosenEdge-1;
    }
}