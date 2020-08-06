package model;
public class MarkovChain {

    private int currentState;
    private int[][] graph;
    private final int STATES; 

    public MarkovChain(int statesNumber){
        currentState = 0;
        STATES = statesNumber;
        graph = new int[STATES][STATES];
    }

    public void resetToInitialState(){
        currentState = 0;
    }

    //Recibe un array de tamano STATES X STATES con los pesos de cada arco
    public void assignGraphWeight(int[] pWeights){
        int weightsIndex = 0;
        for ( int row = 0; row < STATES; row++) {
            for (int col = 0; col < STATES; col++) {
                graph[row][col] = pWeights[weightsIndex];
                weightsIndex++;
            }
        }
    }

    //Recibe un array de los estados que están adjacentes
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
        //! Probar la division si se trunca hay que castear a double
        System.out.println(edgesProb.toString());

        int pickedAdjacentPos = pickAdjacentPosition(edgesProb);
        currentState = pAdjacentStates[pickedAdjacentPos];
        return pickedAdjacentPos;
    }

    private double[] normalizeEdgesProb(int[] pEdgesWeight,int pEdgesSum){
        double[] edgesProb = new double[4];
        for (int adjacentPosition = 0; adjacentPosition < edgesProb.length; adjacentPosition++) {
            edgesProb[adjacentPosition] = pEdgesWeight[adjacentPosition] / pEdgesSum;
        }
        return edgesProb;
    }
    private int pickAdjacentPosition(double[] pEdgesProbability){
        int chosenEdge = 0;
        //Pick a random node base on probability
        return chosenEdge;
    }
}