package model;

import java.util.ArrayList;

import model.graph.BFS;
import model.graph.Dijkstra;
import model.graph.Graph;
import model.graph.GraphNode;
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
			ArrayList<GraphNode<Position>> path = pathGetter.getPath(graph, pos, new Position(MAP_END[0], MAP_END[1]));
			if(path == null){
				int[] posArray = new int[2];
				posArray[0] = pos.getX();
				posArray[1] = pos.getY();
				distance = ManhattanDistance(posArray, MAP_END);
			}
			else{
				distance = path.size();
			}
			distances[pRow][pCol] = distance;
		}
		return distance;
	}

	private int ManhattanDistance(int[] pStart, int[] pEnd){
		 return Math.abs(pStart[0] - pEnd[0]) + Math.abs(pStart[1] - pEnd[1]);
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
		pathGetter = new BFS<Position>();
		Position leftPos;
		Position upPos = null;
		int weight = 0;
		for (int row = 0; row < MAP_SIZE; row++) {
			leftPos = null;
			for (int col = 0; col < MAP_SIZE; col++) {
				Position pos = new Position(row,col);
				graph.addNode(pos);
				upPos = getAdjacentPosition(pos, UP_DIRECTION);
				if(getTerrainByPos(upPos) == BLOCKED_TERRAIN){
					upPos = null;
				}
				if(getTerrainByPos(pos) != BLOCKED_TERRAIN){
					graph.addEdge(pos, leftPos, weight);
					graph.addEdge(pos, upPos, weight);
					leftPos = pos;
				}
				else{
					leftPos = null;
				}
			}
		}
	}
		public static void main(String[] args) {
		Map map = new Map();
		Position pos = new Position(0,17);
		Position pos2 = new Position(0,18);
		System.out.println(map.graph.getNode(pos).getAdjacentNodes().toString());
		System.out.println(map.graph.getNode(pos2).getAdjacentNodes().toString());
		int distance = map.calculateDistance(2, 17);
		System.out.println(distance);
	}
}
