package com.dong.matko.toolbox;

import harsh.p.raval.lightbox.LightBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

// import org.jdesktop.swingx.JXLoginPane;

import com.dong.matko.toolbox.about.TabAbout;
import com.dong.matko.toolbox.counter.TabCounter;
// import com.dong.matko.toolbox.cpw.CPWTreeTable;
// import com.dong.matko.toolbox.cpw.LoginServiceCPW;
// import com.dong.matko.toolbox.cpw.SharePointClient;
// import com.dong.matko.toolbox.cpw.TabCPW;
// import com.dong.matko.toolbox.cpw.UserNameStoreCPW;
import com.dong.matko.toolbox.renamer.TabRenamer;
import com.dong.matko.toolbox.splitter.TabSplitter;
import com.dong.matko.toolbox.unlocker.TabUnlocker;
import com.dong.matko.toolbox.writer.Position;
import com.dong.matko.toolbox.writer.TabWriter;
// import com.dong.matko.toolbox.writer.WriteWord;


public class MainWindow extends JPanel implements ChangeListener{
	private static final long serialVersionUID = 1L;

	// public static SharePointClient SPc = null;

	int lastSelectedIndex = 0;
	boolean loggedIn = false;
	public Preferences prefs;
	private static JFrame frame;
	private static LightBox l = new LightBox();
	private static JPanel p, s;
	// private JPanel CPW;
	public static void showPositionEditor(final String type){
		s = new JPanel(new MigLayout("", "15[]20[]15", "15[][]push[]15"));
		s.setBorder(BorderFactory.createLineBorder(Color.black));
		ArrayList<Position> pos = null;
		if(type.equals("pdf"))
			pos = TabWriter.PDF;
		else if(type.equals("word"))
			pos = TabWriter.WORD;
		else if(type.equals("excel"))
			pos = TabWriter.EXCEL;
		final ArrayList<Position> posFin = pos;
		pos = null;
		if(posFin != null){
			final DefaultListModel<Position> model = new DefaultListModel<Position>();
			for(int i = 0; i < posFin.size(); i++){
				model.addElement(posFin.get(i));
			}
			final JList<Position> list = new JList<Position>(model);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			//list.setVisibleRowCount(-1);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(200, 170));
			JButton ok = new JButton("Accept");
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(type.equals("pdf"))
						TabWriter.PDF = posFin;
					else if(type.equals("word"))
						TabWriter.WORD = posFin;
					else if(type.equals("excel"))
						TabWriter.EXCEL = posFin;
					l.closeLightBox(frame, s);
				}
			});
			final JButton up = new JButton("Move up");
			final JButton down = new JButton("Move down");
			up.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(list.getSelectedIndex() > 0) {
						Collections.swap(posFin, list.getSelectedIndex(), list.getSelectedIndex() - 1);
						Position aObject = model.getElementAt(list.getSelectedIndex());
						Position bObject = model.getElementAt(list.getSelectedIndex() - 1);
						model.set(list.getSelectedIndex(), bObject);
						model.set(list.getSelectedIndex() - 1, aObject);
						list.setSelectedIndex(list.getSelectedIndex() - 1);
						if(list.getSelectedIndex() < posFin.size() - 1) {
							down.setEnabled(true);
						}
					}
					if(list.getSelectedIndex() == 0){
						up.setEnabled(false);
					}
				}
			});
			down.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(list.getSelectedIndex() < posFin.size() - 1) {
						Collections.swap(posFin, list.getSelectedIndex(), list.getSelectedIndex() + 1);
						Position aObject = model.getElementAt(list.getSelectedIndex());
						Position bObject = model.getElementAt(list.getSelectedIndex() + 1);
						model.set(list.getSelectedIndex(), bObject);
						model.set(list.getSelectedIndex() + 1, aObject);
						list.setSelectedIndex(list.getSelectedIndex() + 1);
						if(list.getSelectedIndex() > 0) {
							up.setEnabled(true);
						}
					}
					if(list.getSelectedIndex() == posFin.size() - 1){
						down.setEnabled(false);
					}
				}
			});
			s.add(listScroller, "spany 3");
			s.add(up, "alignx center, wrap, growx, sg 1");
			s.add(down, "alignx center, wrap, growx, sg 1");
			s.add(ok, "alignx center, growx, sg 1");
			s.setSize(250, 200);
			l.createLightBoxEffect(frame,s);
		}
	}
	public static void showModal(String text){
		p = new JPanel();
		java.net.URL imgURL = MainWindow.class.getClassLoader().getResource("ajax-loader.gif");
		ImageIcon loading = new ImageIcon(imgURL);
		JLabel jl = new JLabel("   " + text, loading, JLabel.CENTER);
		loading.setImageObserver(jl);
		p.add(jl);
		p.setSize(100, 100);
		p.setOpaque(false);
		l.createLightBoxEffect(frame,p);
	}
	public static void hideModal(){
		l.closeLightBox(frame, p);
	}
	public void stateChanged(ChangeEvent e)
	{
		JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
		int selectedIndex = tabbedPane.getSelectedIndex();
		// if(tabbedPane.getSelectedComponent() == CPW)
		// {
		// 	Component component = tabbedPane.getComponentAt(selectedIndex);
		// 	if(!loggedIn){
		// 		LoginServiceCPW service = new LoginServiceCPW();
		// 		JXLoginPane loginPane = new JXLoginPane(service, null, new UserNameStoreCPW(), null);
		// 		//service.addLoginListener(new LoginListenerCPW(loginPane));
		// 		JXLoginPane.Status status = JXLoginPane.showLoginDialog(component,loginPane);

		// 		if(status == JXLoginPane.Status.CANCELLED || status == JXLoginPane.Status.NOT_STARTED) {
		// 			tabbedPane.setSelectedIndex(lastSelectedIndex);
		// 		}
		// 		if(status == JXLoginPane.Status.SUCCEEDED){
		// 			loggedIn = true;
		// 			lastSelectedIndex = selectedIndex;
		// 			CPWTreeTable.root.loadChildren(CPWTreeTable.treeTable, CPWTreeTable.progressListener);
		// 		}else{
		// 			tabbedPane.setSelectedIndex(lastSelectedIndex);
		// 		}
		// 	}else{
		// 		lastSelectedIndex = selectedIndex;
		// 	}
		// }else{
		lastSelectedIndex = selectedIndex;
		// }
	}

	public JTabbedPane getContent()
	{
		JTabbedPane mainFrame = TabHelper.createTabbedPane(JTabbedPane.LEFT);

		JPanel WRITER = TabWriter.setupUI();
		TabHelper.addTab(mainFrame, "WRITER", WRITER);

		// CPW = TabCPW.InitUI();
		// TabHelper.addTab(mainFrame, "CPW", CPW);

		JPanel SPLITTER = TabSplitter.initUI();
		TabHelper.addTab(mainFrame, "SPLITTER", SPLITTER);

		JPanel UNLOCKER = TabUnlocker.initUI();
		TabHelper.addTab(mainFrame, "UNLOCKER", UNLOCKER);

		new TabRenamer(mainFrame, frame);

		JPanel COUNTER = TabCounter.initUI();
		TabHelper.addTab(mainFrame, "COUNTER", COUNTER);

		JPanel ABOUT = TabAbout.initUI();
		TabHelper.addTab(mainFrame, "OPTIONS", ABOUT);

		mainFrame.addChangeListener(this);

		return mainFrame;
	}

	public static void main( String args[] )
	{
		frame = new JFrame(Resources.get("application.name"));
		frame.setResizable(false);
		java.net.URL imgURL = MainWindow.class.getClassLoader().getResource("icon.png");
		if (imgURL != null) {
			frame.setIconImage((new ImageIcon(imgURL)).getImage());
		}
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		// WriteWord.write();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
		} catch (UnsupportedLookAndFeelException e) {
		} catch (Exception e) {}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MainWindow().getContent());
		frame.setSize(1000, 542);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( d.width / 2 - 400, d.height/2 - 250 );
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				// if(SPc != null){
				// 	SPc = null;
				// }
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		frame.setVisible(true);
	}
}
