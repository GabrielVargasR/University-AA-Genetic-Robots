package model;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


public class Darwin implements IConstants{
	private HashMap<Integer, ArrayList<Robot>> generations;
	private int genCounter;
	private Random random;

	public Darwin() {
		this.generations = new HashMap<Integer, ArrayList<Robot>>();
		this.populateFirstGen();
		this.random = new Random();
		
		// FOR GUI TEST ONLY
		this.genCounter++;
		ArrayList<Robot> gen = new ArrayList<Robot>();
		
		for (int i = 0; i < POPULATION_SIZE; i++) {
			gen.add(new Robot(genCounter, i));
		}
		
		this.generations.put(genCounter, gen);
	}
	
	public Robot getIndividual(int pGen, int pNum) {
		return this.generations.get(pGen).get(pNum);
	}
	
	public ArrayList<Robot> getGeneration(int pGen){
		return this.generations.get(pGen);
	}
	
	public int getGenAmount() {
		return this.genCounter;
	}
	
	// First phase
	private void populateFirstGen() {
		ArrayList<Robot> gen = new ArrayList<Robot>();
		this.genCounter = 1;
		
		for (int i = 0; i < POPULATION_SIZE; i++) {
			gen.add(new Robot(genCounter, i));
		}
		
		this.generations.put(genCounter, gen);
	}	
	
	private double evaluateFitness(Robot pRobot) {
		double distance = pRobot.getDistance();
		double time = pRobot.getTime();
		double cost = pRobot.getCost();
		
		double cDist = (4.0 * (MIN_DISTANCE - distance))/20.0;
		double cTime = (MIN_DISTANCE - distance) / time;
		double cCost = 1.0/cost;
		
		return (cDist + cTime + cCost) / 6.0;
	}
	
	// Second phase
	private Robot[] naturalSelection() {
		ArrayList<Robot> generation = this.generations.get(this.genCounter);
		Robot[] selected = new Robot[POPULATION_SIZE];
		
		double currFit;
		double fitnessSum = 0.0;
		for (Robot robot : generation) {
			// Robots are tested in the map. Their time in the map is set here
			//Walker.walk(robot)
			currFit = this.evaluateFitness(robot);
			robot.setFitness(currFit);
			fitnessSum += currFit;
		}
		
		double currProb;
		ArrayList<Robot> picker = new ArrayList<Robot>();
		for (Robot robot : generation) {
			currProb = robot.getFitness() / fitnessSum;
			currProb *= 100;
			currProb = Math.floor(currProb);
			
			for (int i = 0; i < currProb; i++) {
				picker.add(robot);
			}
		}
		
		int index;
		int selSize = picker.size();
		Collections.shuffle(picker);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			index = this.random.nextInt(selSize);
			selected[i] = picker.get(index);
		}
		
		return selected;
	}
	
	private byte[] swapGenes(byte chrom1, byte chrom2, int pBottom, int pTop) {
		byte new1 = 0b0;
		byte new2 = 0b0;
		
		for (int i = pTop; i > pBottom; i--) {
			new1 <<= 1;
			new2 <<= 1;
			
			new1 += chrom2 >> i & 1;
			new2 += chrom1 >> i & 1;
		}
		
		for (int i = pBottom; i >= 0; i--) {
			new1 <<= 1;
			new2 <<= 1;
			
			new1 += chrom1 >> i & 1;
			new2 += chrom2 >> i & 1;
		}
		
		return new byte[] {new1, new2};
	}
	
	// Third phase
	private void cross(Robot pRobot1, Robot pRobot2) {
		byte[] parent1 = pRobot1.getGenes();
		byte[] parent2 = pRobot2.getGenes();
		byte[] gene1 = new byte[GENE_SIZE];
		byte[] gene2 = new byte[GENE_SIZE];
		
		byte[] temp;
		
		for(int i = 0; i < 3; i++) {
			temp = this.swapGenes(parent1[i], parent2[i], 3, 7);
			gene1[i] = temp[0];
			gene2[i] = temp[1];
		}
		
		// aquí se mandan a cruzar los genes que tienen que ver con la cadena de Markov
		
		// pensar como meter el counter de número dentro de la generación
		Robot new1 = new Robot(gene1, pRobot1, pRobot2, this.genCounter, 0);
		Robot new2 = new Robot(gene2, pRobot1, pRobot2, this.genCounter, 0);
		
		ArrayList<Robot> gen = this.generations.get(this.genCounter);
		gen.add(new1);
		gen.add(new2);
	}
	
	// Fourth phase
	private void mutate() {
		
	}
	
	
	// Fifth phase will go inside this method
	public void run() {
		// loop. Figure out stopping condition for the algorithm
		this.genCounter++;
		this.generations.put(this.genCounter, new ArrayList<Robot>());
		this.naturalSelection();
		this.cross(null, null);
		this.mutate();
	}
	
	public static void main(String[] args) {
		Darwin d = new Darwin();
//		ArrayList<Robot> gen =  d.getGeneration(1);
//		
//		for (Robot r : gen) {
//			System.out.println(r.getId() + ": " +  String.format("%8s", Integer.toBinaryString(r.getBattery() & 0xFF)).replace(' ', '0')
//			+ String.format("%8s", Integer.toBinaryString(r.getCamera() & 0xFF)).replace(' ', '0')+  String.format("%8s", Integer.toBinaryString(r.getMotor() & 0xFF)).replace(' ', '0'));
//		}
		
	
		byte one = (byte) 150;
		byte two = (byte) 25;
		// prints one, then two
		for (int i = 7; i >= 0; i--) {
			System.out.print(one >> i & 1);
		}
		System.out.println();
		for (int i = 7; i >= 0; i--) {
			System.out.print(two >> i & 1);
		}
		System.out.println();
		
		// swap at the middle
		byte three = 0b0;
		byte four = 0b0;
		
		
		for (int i = 7; i > 3; i--) {
			three <<= 1;
			four <<= 1;
			
			three += two >> i & 1;
			four += one >> i & 1;
		}
		
		for (int i = 3; i >= 0; i--) {
			three <<= 1;
			four <<= 1;
			
			three += one >> i & 1;
			four += two >> i & 1;
		}
		
		
		
		for (int i = 7; i >= 0; i--) {
			System.out.print(four >> i & 1);
		}
		System.out.println();
		for (int i = 7; i >= 0; i--) {
			System.out.print(three >> i & 1);
		}
		
	}
}
