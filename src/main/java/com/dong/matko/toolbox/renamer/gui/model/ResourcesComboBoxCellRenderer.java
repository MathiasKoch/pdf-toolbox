package com.dong.matko.toolbox.renamer.gui.model;



import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.dong.matko.toolbox.Resources;



@SuppressWarnings("rawtypes")
public class ResourcesComboBoxCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public ResourcesComboBoxCellRenderer() {
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setText(" "+Resources.get((String)value));
		return this;
	}

}
