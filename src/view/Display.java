package view;

import control.GUIController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.IConstants;

public class Display extends JFrame implements IConstants{
	private TablePanel tablePanel;
	private JPanel buttons;
	private JTextArea robotInfo;
	private GridBagConstraints constraints;
	
	private GUIController controller;

	public Display() {
		super("Genetic Robots");
		super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		super.setResizable(false);
		
		this.constraints = new GridBagConstraints();
		this.controller = new GUIController();
		this.controller.setDisplay(this);
		
		this.tablePanel = new TablePanel(this.controller);
		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.gridx = 0;
		this.constraints.gridy = 0;
		this.constraints.weightx = 1;
		this.constraints.weighty = 1;
//		this.constraints.gridwidth = 1;
//		this.constraints.gridheight = 1;
		this.add(this.tablePanel, this.constraints);
		
		this.initializeButtons();
		this.createInfoArea();
		super.setLocation(400, 100);
		super.setVisible(true);
		super.pack();
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
	
	private void createInfoArea() {
		this.robotInfo = new JTextArea(10, 10);
//		this.robotInfo.setEditable(false);
		
		JScrollPane robotInfoPane = new JScrollPane(this.robotInfo);
		robotInfoPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		robotInfoPane.setPreferredSize(new Dimension(450, 400));
		
		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.gridx = 1;
		this.constraints.gridy = 0;
		this.constraints.weightx = 1;
		this.constraints.weighty = 1;
//		this.constraints.gridwidth = 1;
//		this.constraints.gridheight = 1;
		
		this.add(robotInfoPane, this.constraints);
	}
	
	public void displayRobotInfo(String pInfo) {
		this.robotInfo.setText(pInfo);
	}
	
	public static void main(String[] args) {
		Display d = new Display();
	}
}
