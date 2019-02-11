package com.dong.matko.toolbox.renamer.gui.list.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AlternateRowCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private final static Color ROW_COLOR_1 = Color.WHITE;
	private final static Color ROW_COLOR_2 = new Color(0xF1, 0xF5, 0xF9);

	public AlternateRowCellRenderer() {
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderedLabel = (Component) super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);
		if (row%2==0) {
			renderedLabel.setBackground(ROW_COLOR_1);
		} else {
			renderedLabel.setBackground(ROW_COLOR_2);
		}
		return renderedLabel;
	}
}
