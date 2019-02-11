package com.dong.matko.toolbox.renamer.bean;

import java.io.File;
import java.util.Stack;

import org.apache.commons.io.FilenameUtils;


public class FileRenamer implements Matching {


	private String preview;
	private boolean matching;

	// --- Fo undo implementation
	private Stack<String> oldNames;

	private File file;

	public FileRenamer(String path) {
		file = new File(path);
		preview=getName();
		oldNames = new Stack<String>();
	}

	public FileRenamer(String path, RenamerOption options) {
		file = new File(path);
		preview=getName();
		matching=getName().matches(options.getRegexp().pattern());
		oldNames = new Stack<String>();
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public boolean isMatching() {
		return matching;
	}

	public void setMatching(boolean matching) {
		this.matching = matching;
	}

	public void renameTo(String newName) {
		oldNames.push(getName());
		String fullPath = FilenameUtils.getFullPath(file.getAbsolutePath());
		File newFile = new File(fullPath+newName);
		file.renameTo(newFile);
		file=newFile;
	}

	public int undoRename() {
		if (oldNames.size()>0) {
			String fullPath = FilenameUtils.getFullPath(file.getAbsolutePath());
			File previousFile = new File(fullPath+oldNames.pop());
			file.renameTo(previousFile);
			file=previousFile;
		}
		return oldNames.size();
	}

	public int undoCount() {
		return oldNames.size();
	}

	public String getName() {
		return file.getName();
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

}
