package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.TableController;

import java.awt.event.*;
import model.IConstants;

public class TablePanel extends JPanel implements IConstants{
	
	private JTable table;
	private JScrollPane scrollPane;
	private JButton first;
	private JButton back;
	private JButton next;
	private JButton last;
	private int genCounter;
	
	private TableController controller;
	

	public TablePanel() {
		super();
		super.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		super.setOpaque(true);
		
		this.controller = new TableController();
		
		this.createButtons();
		String[][] arr = {};
		this.table = new JTable(arr, HEADER);
        this.showTable();
	}
	
	private void createButtons() {
		this.first = new JButton("First");
		this.back = new JButton("Back");
		this.next = new JButton("Next");
		this.last = new JButton("Last");
		this.first.addActionListener(this.firstListener());
		this.back.addActionListener(this.backListener());
		this.next.addActionListener(this.nextListener());
		this.last.addActionListener(this.lastListener());
		
		this.add(this.first);
		this.add(this.back);
		this.add(this.next);
		this.add(this.last);
	}
	
	private void showTable() {
        this.scrollPane = new JScrollPane(this.table); 
		this.add(this.scrollPane);
		this.revalidate();
		this.repaint();
	}
	
	private void refreshTable() {
		this.scrollPane.setVisible(false);
		String[][] arr = {}; // aquí hay que conseguir la lista de robots de la generación
		this.table = new JTable(arr, HEADER);
		this.showTable();
	}
	
	private ActionListener firstListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				genCounter = 1;
				refreshTable();
			}
		};
		return action;
	}
	
	private ActionListener backListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				genCounter = (genCounter > 0) ? --genCounter : 0;
				refreshTable();
			}
		};
		return action;
	}
	
	private ActionListener nextListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				genCounter = (genCounter < controller.getSize()) ? ++genCounter : 0;
				refreshTable();
			}
		};
		return action;
	}
	
	private ActionListener lastListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				genCounter = controller.getSize();
				refreshTable();
			}
		};
		return action;
	}
	
	private ListSelectionListener tableSelection() {
		ListSelectionListener listener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
	            System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
	        }
		};
		return listener;
	}
	
	
	
}
