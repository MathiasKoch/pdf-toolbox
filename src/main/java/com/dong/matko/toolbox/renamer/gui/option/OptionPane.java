package com.dong.matko.toolbox.renamer.gui.option;



import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;

public class OptionPane extends JPanel {

	private static final long serialVersionUID = 1L;

	public OptionPane(String title) {
		super(new MigLayout());
		setBorder(Resources.createTitledSeparator(title));
	}

	public OptionPane(LayoutManager layout, String title) {
		super(layout);
		setBorder(Resources.createTitledSeparator(title));
	}

}
