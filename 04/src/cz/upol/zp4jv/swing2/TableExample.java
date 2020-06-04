package cz.upol.zp4jv.swing2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;


public class TableExample extends JFrame {
	
	private static final long serialVersionUID = 1541752522315393245L;

	private JPanel panel = new JPanel();
	private JTable table;
	private JButton btnAdd;
	
	private String[] columns = new String[] { "Name", "Genre", "Salary" };
	private Object[][] values = new Object[][] {{ "Alice", 20, 20000.0 }, { "Bob", 50, 45000.0 }, { "Chuck", 23, 20000.0 }};
	private MyTableModel tableModel;
	
	public TableExample() {
		
	 	// nejjednodussi zpusob vytvoreni tabulky
		table = new JTable(values, columns);

		// tabulka s vlastnim modelem
		tableModel = new MyTableModel();
		//table = new JTable(tableModel);


		// moznost upravit zpusob vykreslovani
		table.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 4951388932705475547L;

			@Override
			public Component getTableCellRendererComponent(JTable arg0,
					Object val, boolean isSelected, boolean isFocused, int row, int col) {
				Component def = super.getTableCellRendererComponent(arg0, val, isSelected, isFocused, row, col);
				if ((val != null) && ((Double)val < 30000)) {
					JLabel lab = new JLabel(val.toString());
					lab.setForeground(Color.RED);
					return lab;
				}
				return def;
				
			}
		});

		btnAdd = new JButton("Add");
		btnAdd.addActionListener((ActionEvent e) -> {
				tableModel.addRow("Joe", 57, 12500.6);
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500, 300));
		
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.add(btnAdd, BorderLayout.SOUTH);
		getContentPane().add(panel);
				
		pack();
	}
	
	/**
	 * Vlastni model popisujici data v tabulce a jejich typy
	 */
	private final class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 8720445364835231767L;

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			return values.length;
		}

		@Override
		public Object getValueAt(int r, int c) {
			return values[r][c];
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// nutne k tomu, abychom rozlisili typ dat k vykresleni
			if (columnIndex == 2) return Double.class;
			return super.getColumnClass(columnIndex);
		}

		@Override
		public String getColumnName(int column) {
			return columns[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return (columnIndex >= 1);
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			values[rowIndex][columnIndex] = aValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	
		public void addRow(String name, int age, double salary) {
			values = Arrays.copyOf(values, values.length + 1);
			values[values.length - 1] = new Object[] { name, age, salary};
			fireTableDataChanged();
		}
	}
}
