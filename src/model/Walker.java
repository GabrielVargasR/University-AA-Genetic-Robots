package model;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Walker implements IConstants {
    private Map map;
    private MarkovChain chain;
    private Robot robot;
    private NavigableMap<Double, Integer> statesHash;
    private int[] initialMapPos;
    private int[] currentMapPos;

    public Walker() {
        map = new Map();
        statesHash = createStatesHash();
        int statesNum = 1 + (statesHash.size() * 4);
        chain = new MarkovChain(statesNum, INITIAL_STATE);
        initialMapPos = MAP_START;
        currentMapPos = initialMapPos;
    }

    public void walk(Robot pRobot) {
        robot = pRobot;
        chain.assignGraphWeight(robot.getGenes(),GENE_EDGES_INDEX);
        resetMapPosition();
        while (robot.getBatteryLevel() > 0 && !arrived()) {
            robot.addToPath(currentMapPos);
            int currentTerrain = map.getTerrain(currentMapPos);
            if (robot.canTraverse(currentTerrain) && robot.hasEnoughBattery(currentTerrain)) {
                robot.increaseTime();
                int[] adjacentStates = getAdjacentStates();
                int chosenDir = chain.getNextMove(adjacentStates);
                int[] nextPos = map.getAdjacentPos(currentMapPos, chosenDir);
                if (nextPos != null) {
                    currentMapPos = nextPos;
                    chain.setCurrentState(adjacentStates[chosenDir]);
                }
                System.out.println("CONTINUE");
                System.out.println("Battery Level: " + robot.getBatteryLevel());
                System.out.println("--------------------------");
                continue;
            }
            System.out.println("BREAK");
            System.out.println("Can traverse: " + robot.canTraverse(currentTerrain));
            System.out.println("Battery Level: " + robot.getBatteryLevel());
            System.out.println("--------------------------");

            break;
        }
        System.out.println("FINISH");
        System.out.println("Current map position: " + currentMapPos[0] + "," + currentMapPos[1]);
        System.out.println("Battery Level: " + robot.getBatteryLevel());
        System.out.println("Time: " + robot.getTime());
        System.out.println("Distance: " + robot.getDistance());
        System.out.println("--------------------------");
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
        for (int square = 1; square <= visibleSquares; square++) {
            position = map.getAdjacentPos(position, pDirection);
            terrainTraverseCost += getCost(position, square);
        }
        terrainTraverseCost /= visibleSquares;
        return getStateNumber(terrainTraverseCost, pDirection);
    }

    // ? Se iban a agregar pesos según el squareNum pero hay que rethink eso
    // TODO Eliminar squareNum si no se va a usar
    private double getCost(int[] pPosition, int squareNum) {
        int terrainType = map.getTerrain(pPosition);
        return robot.calculateTerrainBattConsumption(terrainType);
    }

    // Multiplos de 4 para dejar espacio para las 4 direcciones
    // Empieza en 1 para dejar campo al estado inicial
    private NavigableMap<Double, Integer> createStatesHash() {
        NavigableMap<Double, Integer> hash = new TreeMap<Double, Integer>();
        hash.put(1.0, 1); // 0-1 => 1
        hash.put(2.0, 5); // 1-2 => 5
        hash.put(3.0, 9); // 2-3 => 9
        hash.put(4.0, 13); // 3-4 => 13
        return hash;
    }

    private int getStateNumber(double pCost, int pDirection) {
        if (pCost < statesHash.firstEntry().getKey() || pCost > statesHash.lastEntry().getKey()) {
            System.out.println("Cost out of range");
            System.out.println(pCost);
            return 0;
        } else {
            return statesHash.floorEntry(pCost).getValue() + pDirection;
        }
    }

    private void resetMapPosition() {
        currentMapPos = initialMapPos;
        chain.resetToInitialState();
    }

    private boolean arrived() {
        return currentMapPos[0] == MAP_END[0] && currentMapPos[1] == MAP_END[1];
    }

    public static void main(String[] args) {
        Robot robot = new Robot(1,1);
        Walker walker = new Walker();
        walker.walk(robot);
    }
}