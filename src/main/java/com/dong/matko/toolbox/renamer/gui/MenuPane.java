package com.dong.matko.toolbox.renamer.gui;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.renamer.gui.action.RenameButton;
import com.dong.matko.toolbox.renamer.gui.action.UndoButton;
import com.dong.matko.toolbox.renamer.gui.model.BlueLabel;
import com.dong.matko.toolbox.renamer.gui.option.RestoreButton;
import com.dong.matko.toolbox.renamer.ui.RenameMediator;

public class MenuPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JButton helpButton;

	public MenuPane(ActionListener al, RenameMediator m) {
		super(new MigLayout("flowy", "5[]5", "5[]10[]10[]push[][]5"));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		JPanel libelles = new JPanel(new MigLayout("wrap 2, fillx", "[align left]71[]", ""));
		libelles.setOpaque(false);
		libelles.setBorder(Resources.createTitledBorder("title.shortcuts"));
		libelles.add(new JLabel(Resources.get("format.string.date.day")), "");
		libelles.add(new BlueLabel(Resources.get("format.date.day")), "");
		libelles.add(new JLabel(Resources.get("format.string.date.month")), "");
		libelles.add(new BlueLabel(Resources.get("format.date.month")), "");
		libelles.add(new JLabel(Resources.get("format.string.date.year")), "");
		libelles.add(new BlueLabel(Resources.get("format.date.year")), "");
		libelles.add(new JLabel(Resources.get("format.string.increment")), "");
		libelles.add(new BlueLabel(Resources.get("format.increment")), "");
		libelles.add(new JLabel(Resources.get("format.string.filename")), "");
		libelles.add(new BlueLabel(Resources.get("format.filename")), "");
		libelles.add(new JLabel(Resources.get("format.string.extension")), "");
		libelles.add(new BlueLabel(Resources.get("format.extension")), "");
		libelles.add(new JLabel(Resources.get("format.string.group")), "");
		libelles.add(new BlueLabel(Resources.get("format.group")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.drawing.one")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.drawing.one")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.drawing.two")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.drawing.two")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.drawing.three")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.drawing.three")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.location")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.location")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.block")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.block")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.F0")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.F0")), "");
		libelles.add(new JLabel(Resources.get("format.string.tecdoc.RDSPP")), "");
		libelles.add(new BlueLabel(Resources.get("format.tecdoc.RDSPP")), "");

		add(libelles, 					"alignx");

		helpButton = new JButton(Resources.get("button.help"));
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					InputStream in = getClass().getResourceAsStream("guide.pdf");
					File tmpFile;
					try {
						tmpFile = File.createTempFile("userguide", ".pdf");
						OutputStream out = new FileOutputStream(tmpFile);
						byte[] buffer = new byte[1024];
						int len;
						while ((len = in.read(buffer)) != -1) {
							out.write(buffer, 0, len);
						}
						out.close();
						in.close();
						Desktop.getDesktop().open(tmpFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		add(new RenameButton(al, m), 	"alignx center, growx, sg 1");
		add(new UndoButton(al, m), 		"alignx center, growx, sg 1");
		add(new RestoreButton(al, m), 	"alignx center, growx, sg 1");
		add(helpButton, 	"alignx center, growx, sg 1");

	}

}
