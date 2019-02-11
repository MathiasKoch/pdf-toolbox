package com.dong.matko.toolbox.about;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dong.matko.toolbox.Resources;

public class TabAbout {

	public static JPanel initUI(){
		JPanel jplPanel = new JPanel(new BorderLayout());
		try {
			BufferedImage image = ImageIO.read(TabAbout.class.getResource("icon.png"));
			JLabel picLabel = new JLabel(new ImageIcon(image));
			picLabel.setBorder(new EmptyBorder(20,0,0,0));
			JLabel version = new JLabel(Resources.get("application.version"), JLabel.CENTER);
			jplPanel.add(picLabel, BorderLayout.NORTH);
			jplPanel.add(version);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jplPanel;
	}
}
