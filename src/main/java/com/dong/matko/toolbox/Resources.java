package com.dong.matko.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Resources {

	private final static ResourceBundle rsb = ResourceBundle.getBundle("gui", new Locale(System.getProperty("user.language")));

	private final static Border border = BorderFactory.createEtchedBorder();
	private final static Border separator = BorderFactory.createMatteBorder(1,0,0,0, Color.GRAY);

	// --- Font definition
	private final static Font FONT = UIManager.getFont("Label.font");
	public final static Font NORMAL = new Font(FONT.getFamily(), FONT.getStyle(), FONT.getSize());
	public final static Font SMALL = new Font(FONT.getFamily(), FONT.getStyle(), FONT.getSize()-2);
	public final static Font TITLE = new Font(FONT.getFamily(), Font.BOLD, 26);
	public final static Font BOLD = new Font(FONT.getFamily(), Font.BOLD, FONT.getSize());

	public static String get(String key) {
		return rsb.getString(key);
	}

	public static TitledBorder createTitledBorder(String title) {
		return BorderFactory.createTitledBorder(border,
				get(title),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION);
	}

	public static TitledBorder createTitledSeparator(String title) {
		return BorderFactory.createTitledBorder(separator,
				get(title),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION);
	}

}
