package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.TableController;

import java.awt.event.*;
import model.IConstants;

public class TablePanel extends JPanel implements IConstants{
	
	private JTable table;
	private JScrollPane scrollPane;
	private ListSelectionListener tableListener;
	private int genCounter;
	
	private TableController controller;
	

	public TablePanel() {
		super();
//		super.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
//		super.setOpaque(true);
		
		this.controller = new TableController();
		this.genCounter = 1;
		this.tableSelection();
		
		
		String[][] arr = controller.getGeneration(this.genCounter);
		this.table = new JTable(arr, HEADER);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.getSelectionModel().addListSelectionListener(this.tableListener);
        this.showTable();
	}
	
	private void showTable() {
        this.scrollPane = new JScrollPane(this.table); 
		this.add(this.scrollPane);
		this.revalidate();
		this.repaint();
	}
	
	private void refreshTable() {
		this.scrollPane.setVisible(false);
		String[][] arr = controller.getGeneration(this.genCounter);
		this.table = new JTable(arr, HEADER);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.getSelectionModel().addListSelectionListener(this.tableListener);
		this.showTable();
	}
	
	private void tableSelection() {
		tableListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
				if (!event.getValueIsAdjusting()) {
					String info = controller.getRobotInfo(table.getValueAt(table.getSelectedRow(), 0).toString());
					System.out.println(info);
				}
	        }
		};
	}
	
	public ActionListener firstListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				genCounter = 1;
				refreshTable();
			}
		};
		return action;
	}
	
	public ActionListener backListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				genCounter = (genCounter > 1) ? --genCounter : 1;
				refreshTable();
			}
		};
		return action;
	}
	
	public ActionListener nextListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				genCounter = (genCounter < controller.getSize()) ? ++genCounter : controller.getSize();
				refreshTable();
			}
		};
		return action;
	}
	
	public ActionListener lastListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				genCounter = controller.getSize();
				refreshTable();
			}
		};
		return action;
	}
	
	
}
