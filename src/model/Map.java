package model;

import model.graph.Dijkstra;
import model.graph.Graph;
import model.graph.IGraphPathGettable;

public class Map implements IConstants{
	
	private int[][] layout;
	private int[][] distances;
	private Graph<Position> graph;
	private IGraphPathGettable<Position> pathGetter;

	private int maxDistance;

	public Map() {
		MapReader reader = new MapReader();
		this.layout = reader.getMap();
		initDistances();
		initGraph();
		this.maxDistance = this.calculateDistance(MAP_START[0], MAP_START[1]);

	}

	public int[][] getMap(){
		return this.layout;
	}
	
	public int calculateDistance(int pRow, int pCol) {
		int distance = distances[pRow][pCol];
		if(distance == -1){
			Position pos = new Position(pRow,pCol);
			distance = pathGetter.getPath(graph, pos, new Position(MAP_END[0], MAP_END[1])).size();
			distances[pRow][pCol] = distance;
		}
		return distance;
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

	public int getTerrainByPos(Position pPos){
		if(pPos == null){
			return BLOCKED_TERRAIN;
		}
		return layout[pPos.getX()][pPos.getY()];
	}

	public int[] getAdjacentPos(int[] pPos,int pDirection){
		if(pPos == null) return null;
		int row = pPos[0];
		int col = pPos[1];
		switch (pDirection) {
			case UP_DIRECTION:
				row--;
				break;
			case DOWN_DIRECTION:
				row++;
				break;
			case LEFT_DIRECTION:
				col--;
				break;
			case RIGHT_DIRECTION:
				col++;
				break;
			default:
				break;
		}
		int indexSum = row + col;
		if(row < 0 || col < 0 || indexSum >= MAP_SIZE){
			return null;
		}
		int[] newPos = new int[2];
		newPos[0] = row;
		newPos[1] = col;
		return newPos;
	}

	private Position getAdjacentPosition(Position pPos, int pDirection){
		if(pPos == null) return null;
		int row = pPos.getX();
		int col = pPos.getY();
		switch (pDirection) {
			case UP_DIRECTION:
				row--;
				break;
			case DOWN_DIRECTION:
				row++;
				break;
			case LEFT_DIRECTION:
				col--;
				break;
			case RIGHT_DIRECTION:
				col++;
				break;
			default:
				break;
		}
		int indexSum = row + col;
		if(row < 0 || col < 0 || indexSum >= MAP_SIZE){
			return null;
		}
		Position newPos = new Position(row,col);
		return newPos;
	}
	
	private void initDistances(){
		distances = new int[MAP_SIZE][MAP_SIZE];
		for (int row = 0; row < MAP_SIZE; row++) {
			for (int col = 0; col < MAP_SIZE; col++) {
				distances[row][col] = -1;
			}
		}
	}
	
	private void initGraph() {
		graph = new Graph<Position>();
		pathGetter = new Dijkstra<Position>();
		Position leftPos;
		Position upPos = null;
		int weight = 0;
		for (int row = 0; row < MAP_SIZE; row++) {
			leftPos = null;
			for (int col = 0; col < MAP_SIZE; col++) {
				Position pos = new Position(row,col);
				weight = 0;
				upPos = getAdjacentPosition(pos, UP_DIRECTION);
				graph.addNode(pos);
				if(getTerrainByPos(pos) == BLOCKED_TERRAIN){
					weight = 100;
				}
				graph.addEdge(pos, leftPos, weight);
				graph.addEdge(pos, upPos, weight);
				leftPos = pos;
			}
		}
	}
		public static void main(String[] args) {
		Map map = new Map();
		Position pos = new Position(0,17);
		System.out.println(map.graph.getNode(pos).getAdjacentNodes().toString());
		int distance = map.calculateDistance(0, 18);
		System.out.println(distance);
	}
}
