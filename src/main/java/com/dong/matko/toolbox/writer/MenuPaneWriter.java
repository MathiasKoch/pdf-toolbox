package com.dong.matko.toolbox.writer;


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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.MainWindow;
import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.unlocker.Unlocker;

public class MenuPaneWriter extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JButton runButton, helpButton, defaultButton;

	public MenuPaneWriter() {
		setLayout(new MigLayout("flowy", "5[]5", "5[]10[]push[][]5"));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		final JPanel libelles = new JPanel(new MigLayout("wrap 2, fillx", "5[align left]15", ""));
		libelles.setOpaque(false);
		libelles.setBorder(Resources.createTitledBorder("title.quickhelp"));
		libelles.add(new JLabel(Resources.get("quick.writer.excel")), "wrap");
		libelles.add(new JLabel(Resources.get("quick.writer.files")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.writer.location")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.writer.filter")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.writer.stamp")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.writer.options")), "wrap, gaptop 3px");


		add(libelles, "alignx");


		runButton = new JButton(Resources.get("button.writer.run"));
		runButton.setEnabled(false);

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

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(TabWriter.RDSPP != null){
					if(TabWriter.dir != null){
						Runnable r = new Runnable() {
							public void run() {
								try {
									TabWriter.nrFolders = 0;
									TabWriter.errorCount = 0;
									TabWriter.nrFiles = 0;
									TabWriter.currentFile = 0;
									TabWriter.currentFolder = 0;

									File[] files = new File(TabWriter.dir).listFiles();
									countFiles(files);
									showFiles(files);
								} catch (IOException e) {
									e.printStackTrace();
									// Catch error!
								}
							}
						};
						new Thread(r).start();
					}
				}
			}
		});

		defaultButton = new JButton(Resources.get("button.writer.default"));
		defaultButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TabWriter.resetDefault();
			}
		});


		add(runButton, "alignx center, growx, sg 1");
		add(defaultButton, "alignx center, growx, sg 1");
		add(helpButton, "alignx center, growx, sg 1");

	}
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabWriter.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	public static void countFiles(File[] files){
		for (File file : files) {
			if (file.isDirectory()) {
				TabWriter.nrFolders++;
				countFiles(file.listFiles());
			}else{
				TabWriter.nrFiles++;
			}
		}
	}
	public static void showFiles(File[] files) throws IOException {
		MainWindow.showModal("Stamping..");
		for (int i = 0; i < TabWriter.arr.size(); i++) {
			WriterFile f = TabWriter.arr.get(i);
			if (f.getFile().isDirectory()) {
				TabWriter.currentFolder++;
				showFiles(f.getFile().listFiles());
			} else {
				TabWriter.currentFile++;
				if(f.getType().equals("pdf") && TabWriter.pdfCheck.isSelected()){
					if(TabWriter.unlockButton.isSelected() && f.getLocked()){
						if(Unlocker.unlock(f.getFile().getCanonicalPath(), true)){
							if(!WritePdf.write(f, TabWriter.dirIndex, TabWriter.redButton.isSelected())){
								TabWriter.errorCount++;
							}
						}else{
							// Can't unlock
						}
					}else{
						if(!WritePdf.write(f, TabWriter.dirIndex, TabWriter.redButton.isSelected())){
							TabWriter.errorCount++;
						}
					}
				}
				if((f.getType().equals("docx") || f.getType().equals("doc")) && TabWriter.wordCheck.isSelected()){
					// Write Word
				}
				if((f.getType().equals("xlsx") || f.getType().equals("xls")) && TabWriter.wordCheck.isSelected()){
					// Write Excel
				}
			}
		}
		MainWindow.hideModal();
	}
}
