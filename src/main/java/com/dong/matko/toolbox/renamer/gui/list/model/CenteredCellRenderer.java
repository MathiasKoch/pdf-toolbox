package com.dong.matko.toolbox.renamer.gui.list.model;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CenteredCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public CenteredCellRenderer() {
		super();
	}

	// --- Center display
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel renderedLabel = (JLabel) super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);
		renderedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		return renderedLabel;
	}
}
