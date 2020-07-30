package model;

public interface IConstants {
	public static final int POPULATION_SIZE = 20;
	
	public static int MAP_SIZE = 20;
	public static int[] MAP_START = {19,0};
	public static int[] MAP_END = {0,19};
	public static double MIN_DISTANCE = 20;
	public static String MAP_PATH = "input/map1.json";
	
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 800;
	
	public static final String[] HEADER = {"ID", "Fitness", "Battery", "Camera", "Motor", "Chromosomes", "ParentA", "ParentB"};
	public static final int ROW_SIZE = HEADER.length;
}
