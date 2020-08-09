package model;

import java.util.Random;

public class Robot implements IConstants{
	
	private String id;
	private int motorType;
	private int cameraType;
	private int batteryType;
	
	private byte motor;
	private byte camera;
	private byte battery;
	private byte[] genes;
	
	private double cost;
	private int distance;
	private int time;
	private int batteryLevel;
	
	private double fitness;
	
	private Robot parentA;
	private Robot parentB;


	public Robot(int pGen, int pNum) {
		// for first generation
		Random rand = new Random();
		this.id = "g" + pGen + "-n" + pNum;
		
		
		this.motor = (byte) rand.nextInt(256);
		this.camera = (byte) rand.nextInt(256);
		this.battery = (byte) rand.nextInt(256);
		
		this.motorType = this.calculateType(this.motor);
		this.cameraType = this.calculateType(this.camera);
		this.batteryType = this.calculateType(this.battery);

		batteryLevel = 100; //TODO extraerlo de los genes
		
		this.calculateCost();
		
		this.constructGenes();
		
		this.parentA = null;
		this.parentB = null;

	}
	
	public Robot(byte[] pGenes, Robot pParA, Robot pParB, int pGen, int pNum) {
		// for new generations
		this.parentA = pParA;
		this.parentB = pParB;
		this.genes = pGenes;
		
		// TODO extract info from genes
	}

	public boolean canTraverse(int pTerrainType){
		return (motorType - pTerrainType) >= 0;
	}

	public int getCameraVision(){
		return getCameraType()+1;
	}

	public boolean consumeBattery(int pTerrainType){
		
		batteryLevel =- calculateBatteryConsumption(pTerrainType);

		return batteryLevel < 0;
	}

	public int calculateTerrainBattConsumption(int pTerrainType){
		return 1 + pTerrainType;
	}
	public void increaseTime() {
		time++;
	}
	private int calculateBatteryConsumption(int pTerrainType){
		//toma por hecho que puede pasar por ese terreno
		return calculateTerrainBattConsumption(pTerrainType)+ cameraType;
	}

	private void constructGenes() {
		this.genes = new byte[GENE_SIZE];
		this.genes[0] = this.motor;
		this.genes[1] = this.camera;
		this.genes[2] = this.battery;
	}
	
	private int calculateType(byte pSpec) {
		if (Byte.toUnsignedInt(pSpec) < 85) {
			return 1;
		} else if (Byte.toUnsignedInt(pSpec) < 171) {
			return 2;
		} else if (Byte.toUnsignedInt(pSpec) < 256) {
			return 3;
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
		return this.motor;
	}

	public byte getCamera() {
		return this.camera;
	}

	public byte getBattery() {
		return this.battery;
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
		
		Robot r = new Robot(1,1);
		
		byte bateria = r.getBattery();
		byte camara = r.getCamera();
		byte motor = r.getMotor();
		
		int bat = r.getBatteryType();
		int cam = r.getCameraType();
		int mot = r.getMotorType();
		
		double costo = r.getCost();
		
		System.out.println("ID: " + r.getId());
		System.out.println("Batería: " + String.format("%8s", Integer.toBinaryString(bateria & 0xFF)).replace(' ', '0') + "(" + Byte.toUnsignedInt(bateria) + ")" + " - tipo: " + bat);
		System.out.println("Camara: " + String.format("%8s", Integer.toBinaryString(camara & 0xFF)).replace(' ', '0') + "(" + Byte.toUnsignedInt(camara) + ")"+ " - tipo: " + cam);
		System.out.println("Motor: " + String.format("%8s", Integer.toBinaryString(motor & 0xFF)).replace(' ', '0') + "(" + Byte.toUnsignedInt(motor) + ")"+ " - tipo: " + mot);
		System.out.println("Costo: " + costo);
		
	}
}
