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

	public int getTerrain(int[] pPos){
		if(pPos == null){
			return BLOCKED_TERRAIN;
		}
		return layout[pPos[0]][pPos[1]];
	}

	public int[] getAdjacentPos(int[] pPos,int pDirection){
		int posX = pPos[0];
		int posY = pPos[1];
		switch (pDirection) {
			case UP_DIRECTION:
				posY++;
				break;
			case DOWN_DIRECTION:
				posY--;
				break;
			case LEFT_DIRECTION:
				posX--;
				break;
			case RIGHT_DIRECTION:
				posX++;
				break;
			default:
				break;
		}
		int indexSum = posX + posY;
		if(posX < 0 || posY < 0 || indexSum >= MAP_SIZE){
			return null;
		}
		int[] newPos = new int[2];
		newPos[0] = posX;
		newPos[1] = posY;
		return newPos;
	}
}
