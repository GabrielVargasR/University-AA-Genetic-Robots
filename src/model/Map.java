package model;

public class Map implements IConstants{
	
	private int[][] layout;
	private int maxDistance;

	public Map() {
		MapReader reader = new MapReader();
		this.layout = reader.getMap();
		this.maxDistance = this.calculateDistance(MAP_START[0], MAP_START[1]);
	}
	
	public int[][] getMap(){
		return this.layout;
	}
	
	public int calculateDistance(int pX, int pY) {
		// TODO calculate Manhattan distance from coordinate to MAP_END
		return Integer.MAX_VALUE;
	}
	
	public int getMaxDistance() {
		return this.maxDistance;
	}
}
