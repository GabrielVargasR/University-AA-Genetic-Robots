package model;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


public class Darwin implements IConstants{
	private HashMap<Integer, ArrayList<Robot>> generations;
	private int genCounter;
	private Random random;
	private Walker walker;

	public Darwin() {
		this.generations = new HashMap<Integer, ArrayList<Robot>>();
		this.populateFirstGen();
		this.random = new Random();
		this.walker = new Walker();
		
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
		ArrayList<Robot> generation = this.generations.get(--this.genCounter);
		Robot[] selected = new Robot[POPULATION_SIZE];
		
		double currFit;
		double fitnessSum = 0.0;
		for (Robot robot : generation) {
			walker.walk(robot);
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
		System.out.println("selSize: "+selSize);
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
	private void cross(Robot pRobot1, Robot pRobot2, int pRobNum) {
		byte[] parent1 = pRobot1.getGenes();
		byte[] parent2 = pRobot2.getGenes();
		byte[] gene1 = new byte[GENE_SIZE];
		byte[] gene2 = new byte[GENE_SIZE];
		
		byte[] temp;
		
		for(int i = 0; i < GENE_SIZE; i++) {
			temp = this.swapGenes(parent1[i], parent2[i], 3, 7);
			gene1[i] = temp[0];
			gene2[i] = temp[1];
		}
		
		Robot new1 = new Robot(gene1, pRobot1, pRobot2, this.genCounter, pRobNum);
		Robot new2 = new Robot(gene2, pRobot1, pRobot2, this.genCounter, ++pRobNum);
		
		ArrayList<Robot> gen = this.generations.get(this.genCounter);
		gen.add(new1);
		gen.add(new2);
	}
	
	// Fourth phase
	private void mutate() {
		int bits = POPULATION_SIZE * GENE_SIZE * 8; // amount of bits in a generation
		int modAmount = (int) (bits * MUTATION_PERCENTAGE);
		
		ArrayList<Robot> gen = this.generations.get(this.genCounter);
		
		int index; // which bit gets modified (number relative to the whole matrix)
		int bit; // which bit gets modified (number relative to the byte)
		int byteIndex; // which byte in a robot's genes gets modified
		int robot; // which robot gets modified
		for (int i = 0; i < modAmount; i++) {
			index = this.random.nextInt(bits);
			bit = index % 8;
			byteIndex = index / 8;
			robot = byteIndex / POPULATION_SIZE;
			byteIndex %= POPULATION_SIZE; 
			
			 gen.get(robot).mutate(bit, byteIndex);
		}
	}
	
	
	// Fifth phase will go inside this method
	public void run() {
		
		Robot[] selected;
		// temporary condition. Can be changed to take into account generation variance or general fitness
		while (this.genCounter < 50) {
			this.genCounter++;
			this.generations.put(this.genCounter, new ArrayList<Robot>());
			selected = this.naturalSelection();
			for (int i = 0; i < POPULATION_SIZE; i+=2) {
				this.cross(selected[i], selected[++i], i);
			}
			this.mutate();
		}
	}
	
	public static void main(String[] args) {
		Darwin d = new Darwin();
//		ArrayList<Robot> gen =  d.getGeneration(1);
//		
//		for (Robot r : gen) {
//			System.out.println(r.getId() + ": " +  String.format("%8s", Integer.toBinaryString(r.getBattery() & 0xFF)).replace(' ', '0')
//			+ String.format("%8s", Integer.toBinaryString(r.getCamera() & 0xFF)).replace(' ', '0')+  String.format("%8s", Integer.toBinaryString(r.getMotor() & 0xFF)).replace(' ', '0'));
//		}
		
		/*
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
		*/
		
		/*
		Random random = new Random();
		byte[] a = {(byte) 0b10101010, (byte) 0b11110000, (byte) 0b10001000};
		byte[] b = {(byte) 0b11110000, (byte) 0b10001000, (byte) 0b10101010};
		byte[] c = {(byte) 0b10001000, (byte) 0b10101010, (byte) 0b11110000};
		
		int bits = 3 * 3 * 8;
		int modAmount = 2;
		
		int index; // which bit gets modified (number relative to the whole matrix)
		int bit; // which bit gets modified (number relative to the byte)
		int byteIndex; // which byte in a robot's genes gets modified
		int robot; // which robot gets modified
		for (int i = 0; i < modAmount; i++) {
			index = random.nextInt(bits);
			bit = index % 8;
			byteIndex = index / 8;
			robot = byteIndex / 3;
			byteIndex %= 3;
			
			
			System.out.println("Index: " + index + ", Bit: " + bit + ", byteIndex: " + byteIndex + ", Robot: " + robot);
		}
		*/
	}
}
