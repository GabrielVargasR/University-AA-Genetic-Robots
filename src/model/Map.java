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
	
	public int calculateDistance(int pX, int pY) {
		int distance = distances[pX][pY];
		if(distance == -1){
			Position pos = new Position(pX,pY);
			distance = pathGetter.getPath(graph, pos, new Position(MAP_END[0], MAP_END[1])).size();
			System.out.println("Distance");
			System.out.println(distance);
			distances[pX][pY] = distance;
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
		int posX = pPos[0];
		int posY = pPos[1];

		switch (pDirection) {
			case UP_DIRECTION:
				posX--;
				break;
			case DOWN_DIRECTION:
				posX++;
				break;
			case LEFT_DIRECTION:
				posY--;
				break;
			case RIGHT_DIRECTION:
				posY++;
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

	private Position getAdjacentPosition(Position pPos, int pDirection){
		if(pPos == null) return null;
		int posX = pPos.getX();
		int posY = pPos.getY();
		switch (pDirection) {
			case UP_DIRECTION:
				posX--;
				break;
			case DOWN_DIRECTION:
				posX++;
				break;
			case LEFT_DIRECTION:
				posY--;
				break;
			case RIGHT_DIRECTION:
				posY++;
				break;
			default:
				break;
		}
		int indexSum = posX + posY;
		if(posX < 0 || posY < 0 || indexSum >= MAP_SIZE){
			return null;
		}
		Position newPos = new Position(posX,posY);
		return newPos;
	}
	
	private void initDistances(){
		distances = new int[MAP_SIZE][MAP_SIZE];
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				distances[i][j] = -1;
			}
		}
	}
	
	private void initGraph() {
		graph = new Graph<Position>();
		pathGetter = new Dijkstra<Position>();
		Position leftPos;
		Position upPos = null;
		int weight = 0;
		for (int x = 0; x < MAP_SIZE; x++) {
			leftPos = null;
			for (int y = 0; y < MAP_SIZE; y++) {
				Position pos = new Position(x,y);
				weight = 0;
				upPos = getAdjacentPosition(pos, UP_DIRECTION);
				graph.addNode(pos);
				if(getTerrainByPos(pos) == BLOCKED_TERRAIN){
					weight = 100;
				}
				graph.addEdge(leftPos, pos, weight);
				graph.addEdge(upPos, pos, weight);
				leftPos = pos;
			}
		}
	}
		public static void main(String[] args) {
		Map map = new Map();
		System.out.println(map.graph.getEdges().size());
		
		int distance = map.calculateDistance(0, 0);
		System.out.println(map.graph.toString());
	}
}
