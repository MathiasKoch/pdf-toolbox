package com.dong.matko.toolbox.renamer.gui.model;


import javax.swing.JLabel;

import com.dong.matko.toolbox.Resources;

public class TinyLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public TinyLabel(String label) {
		super(label);
		setFont(Resources.SMALL);
	}
	
}
