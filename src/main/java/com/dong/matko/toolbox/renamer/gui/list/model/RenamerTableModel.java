package com.dong.matko.toolbox.renamer.gui.list.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dong.matko.toolbox.renamer.bean.FileRenamer;


public class RenamerTableModel extends AbstractTableModel implements MatchingTableModel, UndoableTableModel {

	private static final long serialVersionUID = 1L;

	public static final int COLS = 4;

	private String[] columnNames;
	private ArrayList<FileRenamer> rows;

	public RenamerTableModel(ArrayList<FileRenamer> rows, String[] columns) {
		this.rows = rows;
		this.columnNames = columns;
	}

	public int getColumnCount() {
		return COLS;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setRows(ArrayList<FileRenamer> rows) {
		this.rows = rows;
	}

	public Object getValueAt(int row, int col) {
		FileRenamer fr = rows.get(row);
		switch (col) {
		case 0:
			return fr.isMatching();
		case 1:
			return fr.undoCount();
		case 2:
			return (fr.isDirectory()?"/":"")+fr.getName();
		case 3:
		default:
			return (fr.isDirectory()?"/":"")+fr.getPreview();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {}

	public int getRowCount() {
		if (rows == null) {
			return 0;
		}
		return rows.size();
	}

	public boolean isMatchingRow(int row) {
		return rows.get(row).isMatching();
	}

	public int undoableCount(int row) {
		return rows.get(row).undoCount();
	}

}
