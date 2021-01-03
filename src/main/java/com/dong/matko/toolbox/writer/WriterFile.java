package com.dong.matko.toolbox.writer;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.dong.matko.toolbox.Resources;
import com.dong.matko.toolbox.unlocker.Unlocker;

public class WriterFile {

	private File f;
	// private ArrayList<Position> posList;
	private Position pos;
	private String defaultNrSys = "RDSPP";
	private String block;
	private String F0;
	private String rdspp;
	private String nrSys;
	private String fileName;
	private String customSys = "";
	private String sheet;
	private String rev;
	private String fileType;
	private String location;
	private boolean locked;
	private boolean unlock;
	private boolean red;
	private boolean ready;
	private String drawingTextOne;
	private String drawingTextTwo;
	private String drawingTextThree;

	WriterFile() {

	}

	WriterFile(File doc, String rds) {
		this.locked = Unlocker.isLocked(doc);
		this.fileType = FilenameUtils.getExtension(doc.getAbsolutePath());
		this.f = doc;
		this.rdspp = rds;
	}

	void setWritePos(Position p) {
		this.pos = p;
	}

	void setReadyState(boolean r) {
		this.ready = r;
	}

	boolean getReadyState() {
		return ready;
	}

	void setWritePosList(ArrayList<Position> p) {
		// this.posList = p;
	}

	String getType() {
		return fileType.toLowerCase();
	}

	String getWritePosString() {
		return pos != null ? pos.toString() : Resources.get("tooltip.writer.na");
	}

	Position getWritePos() {
		return pos;
	}

	void setLocation(String l) {
		this.location = l;
	}

	public String getLocation() {
		return this.location;
	}

	void setDrawingText(String text, Integer textNumber) {
		if(textNumber == 1){
			this.drawingTextOne = text.replaceAll("[\\\\/:*?\"<>|]", " ");
		} else if(textNumber == 2){
			this.drawingTextTwo = text.replaceAll("[\\\\/:*?\"<>|]", " ");
		}
		this.drawingTextThree = text.replaceAll("[\\\\/:*?\"<>|]", " ");
	}

	public String getDrawingText(Integer textNumber) {
		if(textNumber == 1){
			return this.drawingTextOne;
		} else if(textNumber == 2){
			return this.drawingTextTwo;
		}
		return this.drawingTextThree;
	}

	void setRev(String r) {
		this.rev = r;
	}

	String getRev() {
		return rev;
	}

	void setUnlock(boolean u) {
		this.unlock = u;
	}

	boolean getUnlock() {
		return unlock;
	}

	void setRedFont(boolean r) {
		this.red = r;
	}

	boolean getRedFont() {
		return red;
	}

	void setFile(File doc) {
		this.fileType = FilenameUtils.getExtension(doc.getAbsolutePath());
		if (fileType.toLowerCase() == "pdf")
			this.locked = Unlocker.isLocked(doc);
		this.f = doc;
	}

	File getFile() {
		return f;
	}

	String getName(boolean includeDrawingText) {
		if (includeDrawingText && this.drawingTextThree.length() > 0) {
			String fileType = FilenameUtils.getExtension(f.getAbsolutePath());
			return f.getName().replace("." + fileType, "") + "_" + this.drawingTextThree + "." + fileType;
		}
		return f.getName();
	}

	void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	void setBlock(String b) {
		this.block = b;
	}

	public String getBlock() {
		return block;
	}

	void setF0(String f) {
		this.F0 = f;
	}

	public String getF0() {
		return F0;
	}

	void setSystem(String s) {
		this.nrSys = s;
	}

	void setCustomSystem(String s) {
		this.customSys = s;
	}

	boolean getLocked() {
		return locked;
	}

	String getSystem() {
		return customSys.length() > 0 ? customSys : nrSys;
	}

	void setRDSPP(String r) {
		this.rdspp = r;
	}

	void setSheet(String s) {
		this.sheet = s;
	}

	public String getSheet() {
		return sheet;
	}

	String appendStr(String str, String strToAppend, String prependTostring, String appendToString) {
		String returnStr = str;
		if (strToAppend != null && strToAppend.length() > 0) {
			if (prependTostring != null && prependTostring.length() > 0) {
				returnStr = returnStr + prependTostring;
			}
			returnStr = returnStr + strToAppend;
			if (appendToString != null && appendToString.length() > 0) {
				returnStr = returnStr + appendToString;
			}
		}
		return returnStr;
	}

	public String getRDSPP(boolean preFix, boolean r, boolean sheetNr) {
		String returnStr = "";
		if (preFix) {
			if (nrSys != null) {
				if (customSys.length() > 0) {
					returnStr = returnStr + customSys + " ";
				} else {
					returnStr = returnStr + nrSys + " No: ";
				}
			} else {
				returnStr = appendStr(returnStr, defaultNrSys, null, " No: ");

			}
		}
		returnStr = appendStr(returnStr, location, null, " ");
		returnStr = appendStr(returnStr, block, null, null);
		returnStr = appendStr(returnStr, F0, null, " ");
		returnStr = appendStr(returnStr, rdspp, null, null);
		if (r) {
			returnStr = appendStr(returnStr, rev, " ", null);
		}
		if (sheetNr) {
			returnStr = appendStr(returnStr, sheet, ", ", null);
		}
		return returnStr;
	}
}
