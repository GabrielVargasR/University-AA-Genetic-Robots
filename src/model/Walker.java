package model;
// Up: 0
// Down: 1
// Left:2
// Right:3

import java.util.NavigableMap;
import java.util.TreeMap;

public class Walker {
    Map map;
    MarkovChain chain;
    Robot robot;
    NavigableMap<Double,Integer> statesHash;

    public Walker(){
        map = new Map();
        statesHash = createStatesHash();
        int statesNum = statesHash.size() * 4;
        chain = new MarkovChain(statesNum);
    }

    public void walk(Robot pRobot){
        
    }

    private NavigableMap<Double,Integer> createStatesHash(){
        NavigableMap<Double, Integer> hash = new TreeMap<Double, Integer>();
        //Multiplos de 4 para dejar espacio para las 4 direcciones
        hash.put(0.0, 0);    // 0-1  => 0
        hash.put(1.0, 4);    // 1-2  => 4
        hash.put(2.0, 8);    // 2-3  => 8
        hash.put(3.0, 12);   // 3-4  => 12
        return hash;
    }
    private int getStateNumber(double pCost,int direction){
        if (pCost < statesHash.firstEntry().getKey() || pCost > statesHash.lastEntry().getKey()) {
            System.out.println("Cost out of range");
            return 0;
        } 
        else {
           return statesHash.floorEntry(pCost).getValue() + direction;
        }
    }
}