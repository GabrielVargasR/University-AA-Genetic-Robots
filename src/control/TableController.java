package control;

import java.util.ArrayList;

import model.*;

public class TableController implements IConstants{
	private Darwin darwin;

	public TableController() {
		this.darwin = new Darwin();
	}
	
	public String [][] getGeneration(int pNum) {
		ArrayList<Robot> gen = this.darwin.getGeneration(pNum);
		
		if (gen == null) {
			return new String[][] {{}};
		}
		
		String [][] robots = new String[POPULATION_SIZE][];
		
		Robot rob;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			rob = gen.get(i);
			String parA = (rob.getParentA() == null) ? "n/a" : ""+rob.getParentA().getId();
			String parB = (rob.getParentB() == null) ? "n/a" : ""+rob.getParentB().getId();
			
			// {"ID", "Fitness", "Battery", "Camera", "Motor", "Chromosomes", "ParentA", "ParentB"};
			robots[i] = new String[] {rob.getId(), "0", ""+rob.getBatteryType(), ""+rob.getCameraType(), ""+rob.getMotorType(), 
					String.format("%8s", Integer.toBinaryString(rob.getBattery() & 0xFF)).replace(' ', '0'), parA, parB
			};
		}
		
		return robots;
	}
	
	public String getRobotInfo(String pId) {
		String[] idInfo = pId.replace("g", "").replace("n", "").split("-");
		Robot rob = darwin.getIndividual(Integer.parseInt(idInfo[0]), Integer.parseInt(idInfo[1]));
		// TODO definir qué info se va a imprimir específicamente del robot
		return rob.getId() + ": " +  String.format("%8s", Integer.toBinaryString(rob.getBattery() & 0xFF)).replace(' ', '0')
				+ String.format("%8s", Integer.toBinaryString(rob.getCamera() & 0xFF)).replace(' ', '0')+  String.format("%8s", Integer.toBinaryString(rob.getMotor() & 0xFF)).replace(' ', '0');
	}
	
	public int getSize() {
		return darwin.getGenAmount();
	}
	
	public static void main(String[] args) {
		String id = "g15-n19";
		id = id.replace("g", "").replace("n", "");
		String[] nums = id.split("-");
		System.out.println(nums[0]);
		System.out.println(nums[1]);
	}

}
