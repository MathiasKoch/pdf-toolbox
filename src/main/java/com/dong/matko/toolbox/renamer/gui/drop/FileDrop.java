package com.dong.matko.toolbox.renamer.gui.drop;

import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

public class FileDrop {

	private transient DropTargetListener dropListener;

	public FileDrop(final Component c, final boolean recursive, final Listener listener) {

		dropListener = new DropTargetListener() {

			public void dragEnter(DropTargetDragEvent evt) {
				if (isDragOk(evt)) {
					evt.acceptDrag(DnDConstants.ACTION_COPY);
				} else {
					evt.rejectDrag();
				}
			}

			public void dragOver(DropTargetDragEvent evt) {}

			@SuppressWarnings("unchecked")
			public void drop(DropTargetDropEvent evt) {
				try {
					Transferable tr = evt.getTransferable();

					if (tr.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.javaFileListFlavor)) {
						evt.acceptDrop(DnDConstants.ACTION_COPY);

						List<Object> fileList = (List<Object>)tr.getTransferData(DataFlavor.javaFileListFlavor);

						File[] filesTemp = new File[fileList.size()];
						fileList.toArray(filesTemp);
						final java.io.File[] files = filesTemp;

						if (listener != null) {
							listener.filesDropped(files);
						}

						evt.getDropTargetContext().dropComplete(true);
					} else {
						DataFlavor[] flavors = tr.getTransferDataFlavors();
						boolean handled = false;
						for (int zz = 0; zz < flavors.length; zz++) {
							if (flavors[zz].isRepresentationClassReader()) {
								evt.acceptDrop(DnDConstants.ACTION_COPY);

								Reader reader = flavors[zz].getReaderForText(tr);

								BufferedReader br = new BufferedReader(reader);

								if (listener != null) {
									listener.filesDropped(createFileArray(br));
								}

								evt.getDropTargetContext().dropComplete(true);
								handled = true;
								break;
							}
						}
						if (!handled) {
							evt.rejectDrop();
						}
					}
				}
				catch (java.io.IOException io) {
					evt.rejectDrop();
				}
				catch (java.awt.datatransfer.UnsupportedFlavorException ufe) {
					evt.rejectDrop();
				}
				finally {
					// --- Reset border
				}
			}

			public void dragExit(DropTargetEvent evt) {
				// --- Change border
			}

			public void dropActionChanged(DropTargetDragEvent evt) {
				if (isDragOk(evt)) {
					evt.acceptDrag(DnDConstants.ACTION_COPY);
				} else {
					evt.rejectDrag();
				}
			}
		};

		makeDropTarget(c, recursive);
	}

	private static String ZERO_CHAR_STRING = "" + (char) 0;

	private static File[] createFileArray(BufferedReader bReader) {
		try {
			List<File> list = new ArrayList<File>();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				try {
					if (ZERO_CHAR_STRING.equals(line)) {
						continue;
					}
					File file = new File(new URI(line));
					list.add(file);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return (java.io.File[]) list.toArray(new File[list.size()]);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return new File[0];
	}

	private void makeDropTarget(final java.awt.Component c, boolean recursive) {
		final DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(dropListener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		c.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent evt) {
				Component parent = c.getParent();
				if (parent == null) {
					c.setDropTarget(null);
				} else {
					new DropTarget(c, dropListener);
				}
			}
		});
		if (c.getParent() != null)
			new DropTarget(c, dropListener);

		if (recursive && (c instanceof Container)) {
			Container cont = (Container)c;

			Component[] comps = cont.getComponents();

			for (int i = 0; i < comps.length; i++)
				makeDropTarget(comps[i], recursive);
		}
	}

	private boolean isDragOk(final DropTargetDragEvent evt) {
		boolean ok = false;
		DataFlavor[] flavors = evt.getCurrentDataFlavors();
		int i = 0;
		while (!ok && i<flavors.length) {
			final DataFlavor curFlavor = flavors[i];
			if (curFlavor.equals(DataFlavor.javaFileListFlavor) || curFlavor.isRepresentationClassReader()) {
				ok = true;
			}
			i++;
		}
		return ok;
	}

	public static boolean remove(java.awt.Component c) {
		return remove(null, c, true);
	}

	public static boolean remove(PrintStream out, Component c, boolean recursive) {
		c.setDropTarget(null);
		if (recursive && (c instanceof java.awt.Container)) {
			Component[] comps = ((Container)c).getComponents();
			for (int i = 0; i < comps.length; i++) {
				remove(out, comps[i], recursive);
			}
			return true;
		}
		else {
			return false;
		}
	}

	public static interface Listener {

		public abstract void filesDropped(java.io.File[] files);

	}

	public static class Event extends java.util.EventObject {

		private static final long serialVersionUID = 1L;
		private java.io.File[] files;

		public Event(java.io.File[] files, Object source) {
			super(source);
			this.files = files;
		}

		public java.io.File[] getFiles() {
			return files;
		}

	}

}
