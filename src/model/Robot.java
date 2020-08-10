package model;

import java.util.Random;

public class Robot implements IConstants {

	private String id;
	private int motorType;
	private int cameraType;
	private int batteryType;
	
	private byte[] genes;

	private double cost;
	private int distance;
	private int time;
	private int batteryLevel;

	private double fitness;

	private Robot parentA;
	private Robot parentB;

	//* FIRST GEN
	public Robot(int pGen, int pNum) {
		byte[] randomGenes = generateRandomGenes();
		this.initializeRobot(randomGenes,null,null,pGen,pNum);
	}
	//* NEW GEN
	public Robot(byte[] pGenes, Robot pParA, Robot pParB, int pGen, int pNum) {
		initializeRobot(pGenes, pParA, pParB, pGen, pNum);
	}

	private void initializeRobot(byte[] pGenes, Robot pParA, Robot pParB, int pGen, int pNum){
		this.id = "g" + pGen + "-n" + pGen;
		this.genes = pGenes;
		this.parentA = pParA;
		this.parentB = pParB;
		this.genes = pGenes;
		this.motorType = this.calculateType(genes[GENE_MOTOR_INDEX]);
		this.cameraType = this.calculateType(genes[GENE_CAMERA_INDEX]);
		this.batteryType = this.calculateType(genes[GENE_BATTERY_INDEX]);
		this.batteryLevel = getBatteryMaxLevel(batteryType); 
		this.calculateCost();
	}

	public boolean canTraverse(int pTerrainType) {
		return (motorType - pTerrainType) >= 0;
	}

	public boolean hasEnoughBattery(int pTerrainType) {
		consumeBattery(pTerrainType);
		return batteryLevel > 0;
	}

	public int getCameraVision() {
		return getCameraType() + 1;
	}

	public int calculateTerrainBattConsumption(int pTerrainType) {
		return 1 + pTerrainType;
	}

	public void increaseTime() {
		time++;
	}

	private int getBatteryMaxLevel(int pBatteryType){
		return BATTERY_LEVELS[pBatteryType];
	}

	private int calculateBatteryConsumption(int pTerrainType) {
		return calculateTerrainBattConsumption(pTerrainType) + cameraType;
	}

	private void consumeBattery(int pTerrainType) {
		batteryLevel -= calculateBatteryConsumption(pTerrainType);
	}

	private byte[] generateRandomGenes() {
		Random rand = new Random();
		byte[] genesArray = new byte[GENE_SIZE];
		for (int byteNumber = 0; byteNumber < GENE_SIZE; byteNumber++) {
			genesArray[byteNumber] = (byte) rand.nextInt(256);
		}
		return genesArray;
	}

	private int calculateType(byte pSpec) {
		if (Byte.toUnsignedInt(pSpec) < 85) {
			return 0;
		} else if (Byte.toUnsignedInt(pSpec) < 171) {
			return 1;
		} else if (Byte.toUnsignedInt(pSpec) < 256) {
			return 2;
		}
		return -1;
	}

	private void calculateCost() {
		this.cost = (double) (this.motorType + this.cameraType + this.batteryType) / 3;
	}

	// ---------------------------- Getters & Setters ----------------------------
	public int getBatteryLevel() {
		return batteryLevel;
	}

	public String getId() {
		return this.id;
	}

	public int getMotorType() {
		return this.motorType;
	}

	public int getCameraType() {
		return this.cameraType;
	}

	public int getBatteryType() {
		return this.batteryType;
	}

	public byte getMotor() {
		return genes[GENE_MOTOR_INDEX];
	}

	public byte getCamera() {
		return genes[GENE_CAMERA_INDEX];
	}

	public byte getBattery() {
		return genes[GENE_BATTERY_INDEX];
	}

	public byte[] getGenes() {
		return this.genes;
	}

	public double getCost() {
		return this.cost;
	}

	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Robot getParentA() {
		return parentA;
	}

	public Robot getParentB() {
		return parentB;
	}

	public double getFitness() {
		return this.fitness;
	}

	public void setFitness(double pFitness) {
		this.fitness = pFitness;
	}

	public static void main(String[] args) {

		Robot r = new Robot(1, 1);

		byte bateria = r.getBattery();
		byte camara = r.getCamera();
		byte motor = r.getMotor();

		int bat = r.getBatteryType();
		int cam = r.getCameraType();
		int mot = r.getMotorType();

		double costo = r.getCost();

		System.out.println("ID: " + r.getId());
		System.out.println("Batería: " + String.format("%8s", Integer.toBinaryString(bateria & 0xFF)).replace(' ', '0')
				+ "(" + Byte.toUnsignedInt(bateria) + ")" + " - tipo: " + bat);
		System.out.println("Camara: " + String.format("%8s", Integer.toBinaryString(camara & 0xFF)).replace(' ', '0')
				+ "(" + Byte.toUnsignedInt(camara) + ")" + " - tipo: " + cam);
		System.out.println("Motor: " + String.format("%8s", Integer.toBinaryString(motor & 0xFF)).replace(' ', '0')
				+ "(" + Byte.toUnsignedInt(motor) + ")" + " - tipo: " + mot);
		System.out.println("Costo: " + costo);

	}
}
