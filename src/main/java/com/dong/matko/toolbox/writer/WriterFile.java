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
	private String customSys = "";
	private String sheet;
	private String rev;
	private String fileType;
	private String location;
	private boolean locked;
	private boolean unlock;
	private boolean red;
	private boolean ready;
	private String drawingText;

	WriterFile(){

	}

	WriterFile(File doc, String rds){
		this.locked = Unlocker.isLocked(doc);
		this.fileType = FilenameUtils.getExtension(doc.getAbsolutePath());
		this.f = doc;
		this.rdspp = rds;
	}

	void setWritePos(Position p){
		this.pos = p;
	}

	void setReadyState(boolean r){
		this.ready = r;
	}

	boolean getReadyState(){
		return ready;
	}

	void setWritePosList(ArrayList<Position> p){
		// this.posList = p;
	}

	String getType(){
		return fileType.toLowerCase();
	}

	String getWritePosString(){
		return pos != null? pos.toString() : Resources.get("tooltip.writer.na");
	}

	Position getWritePos(){
		return pos;
	}

	void setLocation(String l){
		this.location = l;
	}

	String getLocation(){
		return this.location;
	}

	void setDrawingText(String text){
		this.drawingText = text.replaceAll("[\\\\/:*?\"<>|]", " ");;
	}

	String getDrawingText(){
		return this.drawingText;
	}

	void setRev(String r){
		this.rev = r;
	}

	String getRev(){
		return rev;
	}

	void setUnlock(boolean u){
		this.unlock = u;
	}

	boolean getUnlock(){
		return unlock;
	}

	void setRedFont(boolean r){
		this.red = r;
	}

	boolean getRedFont(){
		return red;
	}

	void setFile(File doc){
		this.fileType = FilenameUtils.getExtension(doc.getAbsolutePath());
		if(fileType.toLowerCase() == "pdf") this.locked = Unlocker.isLocked(doc);
		this.f = doc;
	}

	File getFile(){
		return f;
	}

	String getName(boolean includeDrawingText){
		if(includeDrawingText && this.drawingText.length() > 0){
			String fileType = FilenameUtils.getExtension(f.getAbsolutePath());
			return f.getName().replace("." + fileType, "") + "_" + this.drawingText + "." + fileType;
		}
		return f.getName();
	}

	void setBlock(String b){
		this.block = b;
	}

	String getBlock(){
		return block;
	}

	void setF0(String f){
		this.F0 = f;
	}

	String getF0(){
		return F0;
	}

	void setSystem(String s){
		this.nrSys = s;
	}

	void setCustomSystem(String s){
		this.customSys = s;
	}

	boolean getLocked(){
		return locked;
	}

	String getSystem(){
		return customSys.length() > 0? customSys : nrSys;
	}

	void setRDSPP(String r){
		this.rdspp = r;
	}

	void setSheet(String s){
		this.sheet = s;
	}

	String getSheet(){
		return sheet;
	}

	String getRDSPP(boolean preFix, boolean r, boolean sheetNr){
		return (preFix? nrSys != null? customSys.length() > 0? customSys + " " : nrSys + " No: " : defaultNrSys + " No: " : "") + (location.length() > 0? location + " " : "") + block + F0 + " " + rdspp + (r? rev != null? " " + rev : "" : "") + (sheetNr? sheet != null? ", " + sheet : "" : "");
	}
}
