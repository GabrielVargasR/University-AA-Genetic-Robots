package control;

import java.util.ArrayList;
import view.Display;

import model.*;

public class GUIController implements IConstants{
	private Darwin darwin;
	private Display display;

	public GUIController() {
		this.darwin = new Darwin();
		this.darwin.run();
	}
	
	public String [][] getGeneration(int pNum) {
		ArrayList<Robot> gen = this.darwin.getGeneration(pNum);
		
		if (gen == null) {
			return new String[][] {};
		}
		
		String [][] robots = new String[POPULATION_SIZE][];
		
		Robot rob;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			rob = gen.get(i);
			String parA = (rob.getParentA() == null) ? "n/a" : ""+rob.getParentA().getId();
			String parB = (rob.getParentB() == null) ? "n/a" : ""+rob.getParentB().getId();
			
			// {"ID", "Fitness", "Battery", "Camera", "Motor", "Chromosomes", "ParentA", "ParentB"};
			robots[i] = new String[] {rob.getId(), Double.toString(rob.getFitness()), ""+rob.getBatteryType(), ""+rob.getCameraType(), ""+rob.getMotorType(), 
					String.format("%8s", Integer.toBinaryString(rob.getBattery() & 0xFF)).replace(' ', '0'), parA, parB
			};
		}
		
		return robots;
	}
	
	public void displayRobotInfo(String pId) {
		String[] idInfo = pId.replace("g", "").replace("n", "").split("-");
		Robot rob = darwin.getIndividual(Integer.parseInt(idInfo[0]), Integer.parseInt(idInfo[1]));
		String info = rob.getId() + ": " +  String.format("%8s", Integer.toBinaryString(rob.getBattery() & 0xFF)).replace(' ', '0')
				+ String.format("%8s", Integer.toBinaryString(rob.getCamera() & 0xFF)).replace(' ', '0')+  String.format("%8s", Integer.toBinaryString(rob.getMotor() & 0xFF)).replace(' ', '0');
		
		info += "\nCost: " + rob.getCost();
		info += "\nDistance: " + rob.getDistance();
		info += "\nTime: " + rob.getTime();
		
		info += "\n\nPath: ";
		
		for (String move : rob.getPath()) {
			info += move + ", ";
		}
		
		this.display.displayRobotInfo(info);
	}
	
	public int getSize() {
		return darwin.getGenAmount();
	}
	
	public void setDisplay(Display pDisplay) {
		this.display = pDisplay;
	}
	
	public static void main(String[] args) {
		String id = "g15-n19";
		id = id.replace("g", "").replace("n", "");
		String[] nums = id.split("-");
		System.out.println(nums[0]);
		System.out.println(nums[1]);
	}
}
