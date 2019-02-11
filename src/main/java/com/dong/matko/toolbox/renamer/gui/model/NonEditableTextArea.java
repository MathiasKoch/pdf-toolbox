package com.dong.matko.toolbox.renamer.gui.model;


import java.awt.Color;

import javax.swing.JTextArea;

import com.dong.matko.toolbox.Resources;

public class NonEditableTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;

	public NonEditableTextArea(String text) {
		super(text);
		setOpaque(false);
		setBackground(new Color(255, 255, 255, 0));
		setEditable(false);
		setLineWrap(false);
		// --- Set font to the default Label font
		setFont(Resources.NORMAL);
	}

}
