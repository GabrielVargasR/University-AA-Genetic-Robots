package model;

public interface IConstants {
	public static final int POPULATION_SIZE = 26;

	//MOTOR + CAMERA + BATTERY + STATES x STATES
	public static final int GENE_SIZE = 292;

	public static final int GENE_MOTOR_INDEX = 0;
	public static final int GENE_CAMERA_INDEX = 1;
	public static final int GENE_BATTERY_INDEX = 2;
	public static final int GENE_EDGES_INDEX = 3;
	
	public static final double MUTATION_PERCENTAGE = 0.0015;
	
	public static int MAP_SIZE = 20;
	public static int[] MAP_START = {19,0};
	public static int[] MAP_END = {0,19};
	public static double MIN_DISTANCE = 20;
	public static String MAP_PATH = "input/map2.json";
	
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 800;
	
	public static final String[] HEADER = {"ID", "Fitness", "Battery", "Camera", "Motor", "Chromosomes", "ParentA", "ParentB"};
	public static final int ROW_SIZE = HEADER.length;

	public static final int UP_DIRECTION = 0;
	public static final int DOWN_DIRECTION = 1;
	public static final int LEFT_DIRECTION = 2;
	public static final int RIGHT_DIRECTION = 3;

	public static final int EASY_TERRAIN = 0;
	public static final int MID_TERRAIN = 1;
	public static final int HARD_TERRAIN = 2;
	public static final int BLOCKED_TERRAIN = 3;

	public static final int INITIAL_STATE = 0;

	// TODO definir battery levels altos
	//Con 100 llegaba a 16 movimientos con motor bajo
	public static final int[] BATTERY_LEVELS = {100,200,300};
}
