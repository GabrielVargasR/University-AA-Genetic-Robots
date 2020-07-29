package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.IConstants;

public class Display extends JFrame implements IConstants{
	private TablePanel tablePanel;
	private JPanel buttons;
	private GridBagConstraints constraints;

	public Display() {
		super("Genetic Robots");
		super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		super.setResizable(false);
		
		this.constraints = new GridBagConstraints();
		
		this.tablePanel = new TablePanel();
		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.gridx = 0;
		this.constraints.gridy = 0;
		
		
		this.add(this.tablePanel, this.constraints);
		initializeButtons();
		super.setVisible(true);
	}
	
	private void initializeButtons() {
		this.buttons = new JPanel();
		
		JButton first = new JButton("First");
		JButton back = new JButton("Back");
		JButton next = new JButton("Next");
		JButton last = new JButton("Last");
		first.addActionListener(tablePanel.firstListener());
		back.addActionListener(tablePanel.backListener());
		next.addActionListener(tablePanel.nextListener());
		last.addActionListener(tablePanel.lastListener());
		
		this.buttons.add(first);
		this.buttons.add(back);
		this.buttons.add(next);
		this.buttons.add(last);
		
		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.gridx = 0;
		this.constraints.gridy = 1;
		
		this.add(this.buttons, this.constraints);
	}
	
	public static void main(String[] args) {
		Display d = new Display();
	}
}
