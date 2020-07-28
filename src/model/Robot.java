package model;

import java.util.Random;

public class Robot {
	
	private String id;
	private int motor;
	private int camera;
	private int battery;
	private int genes;

	public Robot(int pGen, int pNum) {
		// for first generation
		Random rand = new Random();
		this.id = "g" + pGen + "-n" + pNum;
		this.motor = rand.nextInt(256);
		this.camera = rand.nextInt(256);
		this.battery = rand.nextInt(256);
		this.genes = this.constructGenes();
	}
	
	public Robot(int pGenes) {
		// for new generations
	}
	
	private int constructGenes() {
		return 0;
	}
}
