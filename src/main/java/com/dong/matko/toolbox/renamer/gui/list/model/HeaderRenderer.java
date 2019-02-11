package com.dong.matko.toolbox.renamer.gui.list.model;


import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;

public class HeaderRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public HeaderRenderer() {
		super();
	}

	// --- Center display
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JPanel renderedPanel = new JPanel(new MigLayout("", "4[]4", "4[]4"));
		JLabel renderedLabel = new JLabel((String)value);
//			(JLabel) super.getTableCellRendererComponent(
//				table, value, isSelected, hasFocus, row, column);
		renderedLabel.setFont(Resources.BOLD);
		renderedLabel.setHorizontalAlignment(SwingConstants.LEFT);
		renderedPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		renderedPanel.add(renderedLabel, "");
		return renderedPanel;
	}
	
}
