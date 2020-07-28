package model;

import java.util.HashMap;
import java.util.ArrayList;

public class Darwin implements IConstants{
	private HashMap<Integer, ArrayList<Robot>> generations;
	private int genCounter;

	public Darwin() {
		this.generations = new HashMap<Integer, ArrayList<Robot>>();
		this.populateFirstGen();
	}
	
	private void populateFirstGen() {
		ArrayList<Robot> gen = new ArrayList<Robot>();
		this.genCounter = 1;
		
		for (int i = 0; i < POPULATION_SIZE; i++) {
			gen.add(new Robot(genCounter, i));
		}
		
		this.generations.put(genCounter, gen);
	}
	
	public ArrayList<Robot> getGeneration(int pGen){
		return this.generations.get(pGen);
	}
	
	private void evaluateFitness(Robot pRobot) {
		int distance = pRobot.getDistance();
		int time = pRobot.getTime();
		double cost = pRobot.getCost();
	}
	
	private void naturalSelection() {}
	
	private void mate() {}
	
	private void mutate() {}
	
	public void run() {}
	
	public static void main(String[] args) {
		Darwin d = new Darwin();
		ArrayList<Robot> gen =  d.getGeneration(1);
		
		for (Robot r : gen) {
			System.out.println(r.getId() + ": " +  String.format("%8s", Integer.toBinaryString(r.getBattery() & 0xFF)).replace(' ', '0')
			+ String.format("%8s", Integer.toBinaryString(r.getCamera() & 0xFF)).replace(' ', '0')+  String.format("%8s", Integer.toBinaryString(r.getMotor() & 0xFF)).replace(' ', '0'));
		}
	}
}
