package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	private int size; // temp

	public TablePanel() {
		super();
		super.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		super.setOpaque(true);
		
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
				genCounter = (genCounter < size) ? ++genCounter : 0;
				refreshTable();
			}
		};
		return action;
	}
	
	private ActionListener lastListener() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				genCounter = size;
				refreshTable();
			}
		};
		return action;
	}
	
	private void refreshTable() {
		scrollPane.setVisible(false);
		String[][] arr = {}; // aquí hay que conseguir la lista de robots de la generación
		table = new JTable(arr, HEADER);
		this.showTable();
	}
}
