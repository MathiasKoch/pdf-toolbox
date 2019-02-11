package com.dong.matko.toolbox.unlocker;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.dong.matko.toolbox.MainWindow;
import com.dong.matko.toolbox.Resources;

public class MenuPaneUnlock extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JButton pdfButton, runButton, helpButton, restore;
	private static JFileChooser fc;
	public static ArrayList<File> filesList = new ArrayList<File>();

	public MenuPaneUnlock() {
		setLayout(new MigLayout("flowy", "5[]5", "5[]10[]10[]push[][]5"));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		final JPanel libelles = new JPanel(new MigLayout("wrap 2, fillx", "5[align left]37", ""));
		libelles.setOpaque(false);
		libelles.setBorder(Resources.createTitledBorder("title.quickhelp"));
		libelles.add(new JLabel(Resources.get("quick.unlock.files")), "wrap");
		libelles.add(new JLabel(Resources.get("quick.unlock.check")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.unlock.options")), "wrap, gaptop 3px");
		libelles.add(new JLabel(Resources.get("quick.unlock.help")), "wrap, gaptop 3px");

		add(libelles,"alignx");


		fc = new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		runButton = new JButton(Resources.get("button.unlock"));
		runButton.setEnabled(false);
		pdfButton = new JButton(Resources.get("button.selectPDF"), createImageIcon("Open16.gif"));
		pdfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fc.showOpenDialog(libelles) == JFileChooser.APPROVE_OPTION) {
					Runnable r = new Runnable() {
						public void run() {
							MainWindow.showModal("Loading..");
							for(int i = TabUnlocker.model.getRowCount() - 1; i >= 0; i--){
								TabUnlocker.model.removeRow(i);
							}
							filesList.clear();
							File[] pathFiles = fc.getSelectedFile().listFiles();
							showFiles(pathFiles);
							MainWindow.hideModal();
							runButton.setEnabled(true);
						}
					};
					new Thread(r).start();
				}
			}
		});
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
				Runnable r = new Runnable() {
					public void run() {
						MainWindow.showModal("Unlocking..");
						int[] selectedrows = TabUnlocker.table.getSelectedRows();
						for(int row : selectedrows){
							if(Unlocker.unlock(filesList.get(row).getAbsolutePath(), TabUnlocker.deleteButton.isSelected())){
								if(TabUnlocker.deleteButton.isSelected()){
									TabUnlocker.table.setValueAt("Unlocked", row, 1);
								}else{
									TabUnlocker.model.removeRow(row);
									TabUnlocker.model.addRow(new Object[]{filesList.get(row).getName(),"Unlocked"});
									filesList.remove(row);
								}
							}
						}
						MainWindow.hideModal();
					}
				};
				new Thread(r).start();
			}
		});

		restore = new JButton(Resources.get("button.unlock.restore"));
		restore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TabUnlocker.deleteButton.setSelected(false);
				for(int i = TabUnlocker.model.getRowCount() - 1; i >= 0; i--){
					TabUnlocker.model.removeRow(i);
				}
				filesList.clear();
				runButton.setEnabled(false);
			}
		});

		add(pdfButton, "alignx center, growx, sg 1");
		add(runButton, "alignx center, growx, sg 1");
		add(restore, "alignx center, growx, sg 1");
		add(helpButton, "alignx center, growx, sg 1");

	}
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabUnlocker.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void showFiles(File[] files) {
		for (File file : files) {
			if (!file.isDirectory()) {
				int i = file.getName().lastIndexOf('.');
				if(file.getName().substring(i+1).toLowerCase().equals("pdf")){
					filesList.add(file);
					String security = Unlocker.isLocked(file)? "Locked" : "Unlocked";
					TabUnlocker.model.addRow(new Object[]{file.getName(),security});
				}
			}       
		}
	}
}
