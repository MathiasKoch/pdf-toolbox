package com.dong.matko.toolbox.counter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TabCounter {

	public static JPanel initUI(){
		final String ending = "pdf";
		final JPanel jplPanel = new JPanel(new BorderLayout());
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JButton openButton = new JButton("Select top directory");
		final JLabel countLabel = new JLabel("Number of " + ending + " files: 0");
		Border empty = new EmptyBorder(10, 10, 10, 10);
		countLabel.setBorder(empty);
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fc.showOpenDialog(jplPanel) == JFileChooser.APPROVE_OPTION) {
					File[] pathFiles = fc.getSelectedFile().listFiles();
					int count = Counter.CountFiles(pathFiles, ending, "", true);
					System.out.println("Number of " + ending + " files: " + count);
					countLabel.setText("Number of " + ending + " files: " + count);
				}
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		jplPanel.add(buttonPanel, BorderLayout.PAGE_START);
		jplPanel.add(countLabel, BorderLayout.CENTER);
		return jplPanel;
	}

}
