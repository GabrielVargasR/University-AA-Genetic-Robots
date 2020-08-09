package model;

import java.util.NavigableMap;
import java.util.TreeMap;

public class Walker implements IConstants {
    Map map;
    MarkovChain chain;
    Robot robot;
    NavigableMap<Double, Integer> statesHash;
    int[] currentMapPos = { 0, 0 };

    public Walker() {
        map = new Map();
        statesHash = createStatesHash();
        int statesNum = statesHash.size() * 4;
        chain = new MarkovChain(statesNum+1); //+1 para agregar el estado inicial
    }

    public void walk(Robot pRobot) {
        chain.assignGraphWeight(robot.getGenes());
        resetMapPosition();
        while (robot.getBatteryLevel() > 0 && !arrived()) {
            int currentTerrain = map.getTerrain(currentMapPos);
            if (robot.canTraverse(currentTerrain) && robot.consumeBattery(currentTerrain)) {
                robot.increaseTime();
                int chosenDir = chooseDir();
                int[] nextPos = map.getAdjacentPos(currentMapPos, chosenDir);
                if (nextPos != null) {
                    currentMapPos = nextPos;
                }
                continue;
            }
            break;
        }
    }

    private int chooseDir() {
        int[] adjacentStates = getAdjacentStates();
        return chain.getNextMove(adjacentStates);
    }

    private int[] getAdjacentStates() {
        int[] adjacentStates = new int[4];
        for (int direction = 0; direction < 4; direction++) {
            adjacentStates[direction] = getState(currentMapPos, direction);
        }
        return adjacentStates;
    }

    private int getState(int[] pPosition, int pDirection) {
        int visibleSquares = robot.getCameraVision();
        double terrainTraverseCost = 0;
        int[] position = pPosition;
        for (int square = 1; square <+ visibleSquares; square++) {
            position = map.getAdjacentPos(position, pDirection);
            terrainTraverseCost+= getCost(position, square);
        }
        terrainTraverseCost /= visibleSquares;
        return getStateNumber(terrainTraverseCost, pDirection);
    }

    private double getCost(int[] pPosition,int squareNum) {
        int terrainType = map.getTerrain(pPosition);
        //Se iban a agregar pesos según el squareNum
        //return robot.calculateTerrainBattConsumption(terrainType) / squareNum  ;
        return robot.calculateTerrainBattConsumption(terrainType);
    }

    private NavigableMap<Double, Integer> createStatesHash() {
        NavigableMap<Double, Integer> hash = new TreeMap<Double, Integer>();
        // Multiplos de 4 para dejar espacio para las 4 direcciones
        //Empieza en 1 para dejar campo al estado inicial
        hash.put(1.0, 1); // 0-1 => 1
        hash.put(2.0, 5); // 1-2 => 5
        hash.put(3.0, 9); // 2-3 => 9
        hash.put(4.0, 13); // 3-4 => 13
        return hash;
    }

    private int getStateNumber(double pCost, int pDirection) {
        if (pCost < statesHash.firstEntry().getKey() || pCost > statesHash.lastEntry().getKey()) {
            System.out.println("Cost out of range");
            return 0;
        } else {
            return statesHash.floorEntry(pCost).getValue() + pDirection;
        }
    }

    private void resetMapPosition() {
        currentMapPos[0] = 0;
        currentMapPos[1] = 0;
        chain.resetToInitialState();
    }

    private boolean arrived() {
        return currentMapPos[0] == MAP_END[0] && currentMapPos[1] == MAP_END[1];
    }

    public static void main(String[] args) {
        int[] a = { 1, 1 };
        int[] b = { 1, 1 };
        if (a.equals(b)) {
            System.out.println("Si");
        }
        System.out.println("Si");
    }
}