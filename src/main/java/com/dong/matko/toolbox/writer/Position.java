package com.dong.matko.toolbox.writer;

import org.apache.commons.lang.WordUtils;

public class Position {


	private TabWriter.V vPos;
	private TabWriter.H hPos;

	Position(TabWriter.V v, TabWriter.H h){
		this.vPos = v;
		this.hPos = h;
	}

	TabWriter.V getVertical(){
		return vPos;
	}

	TabWriter.H getHorizontal(){
		return hPos;
	}

	public String toString(){
		return WordUtils.capitalize(vPos.toString().toLowerCase()) + " " + hPos.toString().toLowerCase();
	}

}
