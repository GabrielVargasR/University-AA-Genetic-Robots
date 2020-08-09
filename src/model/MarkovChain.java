package model;

import java.util.Random;

public class MarkovChain {

    private int currentState;
    private int initialState;
    private int[][] graph;
    private final int STATES; 

    public MarkovChain(int pStatesNumber){
        initialState = 0;
        STATES = pStatesNumber;
        graph = new int[STATES][STATES];
    }

    public void resetToInitialState(){
        currentState = initialState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }
    //Recibe un array de tamano STATES X STATES con los pesos de cada arco
    public void assignGraphWeight(byte[] pWeights){
        int weightsIndex = 0;
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

    public double[] normalizeEdgesProb(int[] pEdgesWeight,int pEdgesSum){
        double[] edgesProb = new double[pEdgesWeight.length];
        for (int adjacentPosition = 0; adjacentPosition < edgesProb.length; adjacentPosition++) {
            edgesProb[adjacentPosition] = (double)pEdgesWeight[adjacentPosition] / pEdgesSum;
        }
        return edgesProb;
    }
    //Tested
    public int pickAdjacentPosition(double[] pEdgesProbability){
        Random random = new Random();
        //? Podria dar problemas con probs muy pequeñas
        //nextInt lo da entre 0 y 1 si se cambia podria dar 0 entonces habria que poner un i 
        double randomBoundary = random.nextInt(100)+1;
        double probSum = 0;
        int chosenEdge = 0;
        while(probSum < randomBoundary){
            probSum = probSum + (pEdgesProbability[chosenEdge++])*100;
        } 
        return chosenEdge-1;
    }

    public static void main(String[] args) {
        MarkovChain markovChain = new MarkovChain(3);
        int[] d = {1,5,10};
        double[] t = markovChain.normalizeEdgesProb(d, 16);
        for (double e : t) {
            System.out.println(e);
        }
        int pick = 1;
        while(pick != 0){
            pick = markovChain.pickAdjacentPosition(t);
            System.out.println(pick);
        }
    }
}