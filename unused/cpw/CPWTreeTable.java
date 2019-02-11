package com.dong.matko.toolbox.cpw;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import com.dong.matko.toolbox.MainWindow;


public class CPWTreeTable{

	public static PropertyChangeListener progressListener;
	public static MyTreeNode root;
	public static JXTreeTable treeTable;

	public static class MyTreeNode extends DefaultMutableTreeTableNode {
		private boolean loaded = false;

		private int depth;

		@SuppressWarnings("unused")
		private final int index;

		public MyTreeNode(int index, int depth, String name, String RDSPP) {
			this.index = index;
			this.depth = depth;
			add(new DefaultMutableTreeTableNode("Loading...", false));
			setAllowsChildren(true);
			setUserObject(name);
		}

		private void setChildren(List<MyTreeNode> children) {
			remove(0);
			setAllowsChildren(children.size() > 0);
			for (MutableTreeTableNode node : children) {
				add(node);
			}
			loaded = true;
		}


		@Override
		public boolean isLeaf() {
			return false;
		}

		public void loadChildren(final JXTreeTable table, final PropertyChangeListener progressListener) {
			if (loaded) {
				return;
			}
			SwingWorker<List<MyTreeNode>, Void> worker = new SwingWorker<List<MyTreeNode>, Void>() {
				@Override
				protected List<MyTreeNode> doInBackground() throws Exception {
					// Here access database if needed
					setProgress(0);
					List<MyTreeNode> children = new ArrayList<CPWTreeTable.MyTreeNode>();

					ArrayList<CPWFile> files = MainWindow.SPc.getSharePointList("TEKDOC", "", "");
					if (depth < 5) {
						int i = 0;
						for (CPWFile file : files) {
							children.add(new MyTreeNode(i + 1, depth + 1, file.getName(), file.getRDSPP()));
							setProgress((i + 1) * 20);
							i++;
						}
					} else {
						Thread.sleep(1000);
					}
					setProgress(0);
					return children;
				}

				@Override
				protected void done() {
					try {
						setChildren(get());
						table.updateUI();
					} catch (Exception e) {
						e.printStackTrace();
						// Notify user of error.
					}
					super.done();
				}
			};
			if (progressListener != null) {
				worker.getPropertyChangeSupport().addPropertyChangeListener("progress", progressListener);
			}
			worker.execute();
		}


	}

	public static JPanel initUI(){
		root = new MyTreeNode(1, 0, "root", "asfsa");
		List<String> columnNames = Arrays.asList("Name", "RDS-PP No.");
		DefaultTreeTableModel model = new DefaultTreeTableModel(root, columnNames);
		treeTable = new JXTreeTable(model);
		// Build the tree table panel
		treeTable.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(189, 189, 255), Color.WHITE));
		//treeTable.setDefaultEditor(Boolean.class, new DefaultCellEditor(new JCheckBox()));
		JPanel jplPanel = new JPanel( new BorderLayout() );
		final JProgressBar bar = new JProgressBar();
		progressListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				bar.setValue((Integer) evt.getNewValue());
			}
		};
		treeTable.setShowsRootHandles(true);
		treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treeTable.addTreeWillExpandListener(new TreeWillExpandListener() {
			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				TreePath path = event.getPath();
				if (path.getLastPathComponent() instanceof MyTreeNode) {
					MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
					node.loadChildren(treeTable, progressListener);
				}
			}
			@Override
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

			}
		});

		jplPanel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(treeTable);
		scroll.setPreferredSize(new Dimension(544, 400));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jplPanel.add(scroll, BorderLayout.WEST);
		jplPanel.add(bar, BorderLayout.SOUTH);
		return jplPanel;
	}
}
