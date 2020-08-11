package model;

import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

public class MapReader implements IConstants{
	
	private int[][] map;

	public MapReader() {
		try {
			this.loadMap();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadMap() throws FileNotFoundException, IOException, ParseException {
		JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(MAP_PATH));
		JSONArray mapa = (JSONArray) json.get("map");
		
		this.map = new int[MAP_SIZE][MAP_SIZE];
		int i = 0;
		int j;
		
		Iterator mapIt = mapa.iterator();
		Iterator rowIt;
		JSONArray row;
		
		while (mapIt.hasNext()) {
			row = (JSONArray) ((JSONObject) mapIt.next()).get("row");
			rowIt = row.iterator();
			j = 0;
			int[] aRow = new int[20];
			while(rowIt.hasNext()) {
				aRow[j] = (int) ((long) rowIt.next());
				j++;
			}
			this.map[i] = aRow;
			i++;
		}
	}

	public int[][] getMap() {
		return this.map;
	}
	
	public static void main(String[] args) {
		MapReader r = new MapReader();
		
		for (int[] row : r.getMap()) {
			for (int num : row) {
				System.out.print(num + ", ");
			}
			System.out.println("");
		}
	}
}
