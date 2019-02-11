package com.dong.matko.toolbox.cpw;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;


public class TabCPW {
	private static JTextArea log;
	private static String tempDownloadFolder = "";

	public static JPanel InitUI(){
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JXMultiSplitPane msp = new JXMultiSplitPane(); 
		
		String layoutDef = 	"(ROW " +
				"(LEAF name=left weight=0.75)" +
				"(COLUMN weight=0.25" +
				"(LEAF name=right.top weight=0.10)" +
				"(LEAF name=right.midTop weight=0.35)" +
				"(LEAF name=right.midBot weight=0.35)" +
				"(LEAF name=right.bot weight=0.20)" +
				")" +
				")";
		MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel( layoutDef ); 
		msp.getMultiSplitLayout().setModel( modelRoot ); 
		msp.setPaintBorderInsets(true);
		msp.setDividerSize(8);
		msp.add(CPWTreeTable.initUI(), "left"); 
		msp.add(selectedDocuments(23), "right.top" ); 
		msp.add(buttons(), "right.midTop" ); 
		msp.add(log(), "right.midBot" ); 
		msp.add(selectedFile("Documentname.pdf", "rdspp number", "21/07-13", "Pia K.", "210 KB"), "right.bot");
		// ADDING A BORDER TO THE MULTISPLITPANE CAUSES ALL SORTS OF ISSUES 
		msp.setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) ); 
		jp.add(msp, BorderLayout.CENTER);

		return jp;
	}
	private static JPanel selectedDocuments(int count){
		JPanel jplPanel = new JPanel();
		Font font = new Font("Arial", Font.PLAIN, 11);
		String text = count + " documents selected for RDS-PP writing.";
		JLabel jlbDisplay = new JLabel(text);
		jlbDisplay.setFont(font);
		jlbDisplay.setHorizontalAlignment(JLabel.LEFT);
		jplPanel.setLayout(new GridLayout(1, 1));
		jplPanel.add(jlbDisplay);
		return jplPanel;
	}
	private static JPanel buttons(){
		JPanel jplPanel = new JPanel();
		JButton download = new JButton();
		Font font = new Font("Arial", Font.PLAIN, 11);
		download.setText("Download & write");
		download.setFont(font);
		download.setHorizontalAlignment(JButton.LEFT);
		download.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Download selected files to temp "tempDownloadFolder" folder & run "rdspp-writer"
				// Enable "Preview, Approve & Upload" buttons on finished download & write
			}
		});
		JButton preview = new JButton();
		preview.setFont(font);
		preview.setEnabled(false);
		preview.setText("Preview");
		preview.setHorizontalAlignment(JButton.LEFT);
		preview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(tempDownloadFolder));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		final CheckButton approve = new CheckButton("Approve");
		approve.setText("Approve");
		approve.setFont(font);
		approve.setEnabled(false);
		approve.setHorizontalAlignment(JButton.LEFT);
		approve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(approve.isChecked()){
					approve.setChecked(false);
				}else{
					approve.setChecked(true);
				}
			}
		});
		JButton upload = new JButton();
		upload.setText("Upload");
		upload.setFont(font);
		upload.setEnabled(false);
		upload.setHorizontalAlignment(JButton.LEFT);
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(approve.isChecked()){
					// Upload edited files & delete "tempDownloadFolder"
				}else{
					appendLog("You must approve the changes, and check the approve button before uploading!/r");
				}
			}
		});
		jplPanel.setLayout(new GridLayout(4, 1));
		jplPanel.setMinimumSize(new Dimension(200, 140));
		jplPanel.add(download);
		jplPanel.add(preview);
		jplPanel.add(approve);
		jplPanel.add(upload);
		return jplPanel;
	}
	private static JScrollPane log(){
		Font font = new Font("Arial", Font.PLAIN, 11);
		log = new JTextArea(15,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		log.setFont(font);
		log.setLineWrap(true); 
		log.setWrapStyleWord(true);
		JScrollPane logScrollPane = new JScrollPane(log);
		logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		return logScrollPane;
	}
	public static void appendLog(String text){
		log.append(text);
	}
	public static void setLog(String text){
		log.setText(text);
	}
	public static void clearLog(){
		log.setText("");
	}
	public static String getLog(){
		return log.getText();
	}
	private static JPanel selectedFile(String file, String RDSPP, String modDate, String modName, String size){
		Font font = new Font("Arial", Font.PLAIN, 11);
		JPanel jplPanel = new JPanel();
		JLabel fileText = new JLabel("File: " + file);
		fileText.setFont(font);
		fileText.setHorizontalAlignment(JLabel.LEFT);
		JLabel rdsppText = new JLabel("RDS-PP No.: " + RDSPP);
		rdsppText.setFont(font);
		rdsppText.setHorizontalAlignment(JLabel.LEFT);
		JLabel ModifiedText = new JLabel("Last modified: " + modDate + ", by: " + modName);
		ModifiedText.setFont(font);
		ModifiedText.setHorizontalAlignment(JLabel.LEFT);
		JLabel sizeText = new JLabel("File Size: " + size);
		sizeText.setFont(font);
		sizeText.setHorizontalAlignment(JLabel.LEFT);
		jplPanel.setLayout(new GridLayout(4, 1));
		jplPanel.add(fileText);
		jplPanel.add(rdsppText);
		jplPanel.add(ModifiedText);
		jplPanel.add(sizeText);
		jplPanel.setPreferredSize(new Dimension(200,110));
		return jplPanel;
	}
}
class CheckButton extends JButton {

	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox = new JCheckBox();

	public CheckButton(String text) {
		super(text);
		setHorizontalTextPosition(LEFT);
		setHorizontalAlignment(RIGHT);
		setIcon(new Icon() {

			private Icon checkIcon = UIManager.getIcon("CheckBox.icon");

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				checkIcon.paintIcon(checkBox, g, x, y);
			}

			@Override
			public int getIconWidth() {
				return checkIcon.getIconWidth();
			}

			@Override
			public int getIconHeight() {
				return checkIcon.getIconHeight();
			}
		});

	}

	public void setChecked(boolean checked) {
		checkBox.setSelected(checked);
		repaint();
	}

	public boolean isChecked() {
		return checkBox.isSelected();
	}
}
