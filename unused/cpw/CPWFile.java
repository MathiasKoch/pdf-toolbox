package com.dong.matko.toolbox.cpw;


public class CPWFile {

	private String name;
	private String RDSPP;
	private String mod;
	private String modBy;
	private int size;
	private String path;

	public CPWFile(){
	}

	public CPWFile(String _name, String _RDSPP, String _mod, String _modBy, int _size, String _path){
		name = _name;
		RDSPP = _RDSPP;
		mod = _mod;
		modBy = _modBy;
		size = _size;
		path = _path;
	}

	public void setName(String _name){
		this.name = _name;
	}

	public void setRDSPP(String _RDSPP){
		this.RDSPP = _RDSPP;
	}

	public void setMod(String _mod){
		this.mod = _mod;
	}

	public void setModBy(String _modBy){
		this.modBy = _modBy;
	}

	public void setSize(int _size){
		this.size = _size;
	}

	public void setPath(String _path){
		this.path = _path;
	}

	public String getName(){
		return name;
	}

	public String getRDSPP(){
		return RDSPP;
	}

	public String getMod(){
		return mod;
	}

	public String getModBy(){
		return modBy;
	}

	public int getSize(){
		return size;
	}

	public String getPath(){
		return path;
	}

}
