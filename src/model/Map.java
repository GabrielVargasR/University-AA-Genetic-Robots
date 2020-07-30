package model;

public class Map implements IConstants{
	
	private int[][] layout;

	public Map() {
		MapReader reader = new MapReader();
		this.layout = reader.getMap();
	}
}
