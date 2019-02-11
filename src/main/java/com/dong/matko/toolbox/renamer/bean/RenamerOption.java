package com.dong.matko.toolbox.renamer.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RenamerOption {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			DATE_FORMAT);

	// --- Date
	private String day;
	private String month;
	private String year;
	// --- Formating
	private boolean trim;
	private boolean spacesAllowed;
	private boolean periods;
	private boolean doubleSpaces;
	private boolean lowercase;
	private boolean uppercase;
	private boolean capitalize;
	private boolean swapCase;
	private boolean uncapitalize;
	private boolean parenthesis;
	// --- Increment
	private int incrStart;
	private int incrDigits;
	private int incrStep;
	// --- Regexp
	private Pattern regexp;
	private int groupCount;
	// --- Remove and replace
	private String remove;
	private String replace;
	private String replaceWith;
	// --- Output format
	private String output;
	// --- Renaming process
	private boolean prompt;
	// --- Exploring options
	private boolean recursive;
	// --- Preview
	private boolean livePreview;

	public RenamerOption() {}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public boolean isSpacesAllowed() {
		return spacesAllowed;
	}

	public void setSpacesAllowed(boolean spacesAllowed) {
		this.spacesAllowed = spacesAllowed;
	}

	public boolean isPeriods() {
		return periods;
	}

	public void setPeriods(boolean periods) {
		this.periods = periods;
	}

	public boolean isDoubleSpaces() {
		return doubleSpaces;
	}

	public void setDoubleSpaces(boolean doubleSpaces) {
		this.doubleSpaces = doubleSpaces;
	}

	public boolean isLowercase() {
		return lowercase;
	}

	public void setLowercase(boolean lowercase) {
		this.lowercase = lowercase;
		if (lowercase) {
			this.uppercase=false;
			this.capitalize=false;
			this.swapCase=false;
			this.uncapitalize=false;
		}
	}

	public boolean isUppercase() {
		return uppercase;
	}

	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
		if (uppercase) {
			this.lowercase=false;
			this.capitalize=false;
			this.swapCase=false;
			this.uncapitalize=false;
		}
	}

	public boolean isCapitalize() {
		return capitalize;
	}

	public void setCapitalize(boolean capitalize) {
		this.capitalize = capitalize;
		if (capitalize) {
			this.lowercase=false;
			this.uppercase=false;
			this.swapCase=false;
			this.uncapitalize=false;
		}
	}

	public boolean isParenthesis() {
		return parenthesis;
	}

	public void setParenthesis(boolean parenthesis) {
		this.parenthesis = parenthesis;
	}

	public boolean isPrompt() {
		return prompt;
	}

	public void setPrompt(boolean prompt) {
		this.prompt = prompt;
	}

	public int getIncrStart() {
		return incrStart;
	}

	public void setIncrStart(int incrStart) {
		this.incrStart = incrStart;
	}

	public int getIncrDigits() {
		return incrDigits;
	}

	public void setIncrDigits(int incrDigits) {
		this.incrDigits = incrDigits;
	}

	public int getIncrStep() {
		return incrStep;
	}

	public void setIncrStep(int incrStep) {
		this.incrStep = incrStep;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public Pattern getRegexp() {
		return regexp;
	}

	public void setRegexp(Pattern regexp) {
		this.regexp = regexp;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setDate() {
		String[] date = sdf.format(Calendar.getInstance().getTime()).split("/");
		day = date[0];
		month = date[1];
		year = date[2];
	}

	public String getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}
	public String getRemove() {
		return remove;
	}

	public void setRemove(String remove) {
		this.remove = remove;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getReplaceWith() {
		return replaceWith;
	}

	public void setReplaceWith(String replaceWith) {
		this.replaceWith = replaceWith;
	}

	public boolean isSwapCase() {
		return swapCase;
	}

	public void setSwapCase(boolean swapcase) {
		this.swapCase = swapcase;
		if (swapCase) {
			this.lowercase=false;
			this.uppercase=false;
			this.capitalize=false;
			this.uncapitalize=false;
		}
	}

	public boolean isUncapitalize() {
		return uncapitalize;
	}

	public void setUncapitalize(boolean uncapitalize) {
		this.uncapitalize = uncapitalize;
		if (uncapitalize) {
			this.lowercase=false;
			this.uppercase=false;
			this.capitalize=false;
			this.swapCase=false;
		}
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public boolean isLivePreview() {
		return livePreview;
	}

	public void setLivePreview(boolean livePreview) {
		this.livePreview = livePreview;
	}

}
