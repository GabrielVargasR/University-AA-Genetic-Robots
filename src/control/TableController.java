package control;

import model.*;

public class TableController {
	private Darwin darwin;

	public TableController() {
		this.darwin = new Darwin();
	}
	
	public void getGen(int pNum) {
		// return formatted gen info
	}
	
	public int getSize() {
		return darwin.getGenAmount();
	}

}
