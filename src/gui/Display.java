package gui;

import javax.swing.JFrame;
import model.IConstants;

public class Display extends JFrame implements IConstants{
	private TablePanel tablePanel;

	public Display() {
		super("Genetic Robots");
		super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(null);
		super.setResizable(false);
		
		this.tablePanel = new TablePanel();
		this.tablePanel.setVisible(true);
		this.add(this.tablePanel);
		super.setVisible(true);
	}
	
	public static void main(String[] args) {
		Display d = new Display();
	}
}
