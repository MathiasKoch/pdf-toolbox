package com.dong.matko.toolbox.cpw;

import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;

import org.jdesktop.swingx.auth.UserNameStore;

import com.dong.matko.toolbox.MainWindow;


public class UserNameStoreCPW extends UserNameStore {
	private static final String USER_KEY = "usernames";
	private static final String NUM_KEY = "usernames.length";

	@SuppressWarnings("unused")
	private static final String DEFAULT_APP_NAME = "PDFTool";
	private Preferences prefs;
	private String[] userNames;
	private PropertyChangeSupport pcs;

	public UserNameStoreCPW() {
		pcs = new PropertyChangeSupport(this);
		userNames = new String[0];
		loadUserNames();
	}

	public void loadUserNames() {
		initPrefs();
		if (prefs != null) {
			int n = prefs.getInt(NUM_KEY, 0);
			String[] names = new String[n];
			for (int i = 0; i < n; i++) {
				names[i] = prefs.get(USER_KEY + "." + i, null);
			}
			setUserNames(names);
		}
	}

	public void saveUserNames() {
		initPrefs();
		if (prefs != null) {
			prefs.putInt(NUM_KEY, userNames.length);
			for (int i = 0; i < userNames.length; i++) {
				prefs.put(USER_KEY + "." + i, userNames[i]);
			}
		}
	}

	public String[] getUserNames() {
		return userNames;
	}

	public void setUserNames(String[] userNames) {
		if (this.userNames != userNames) {
			String[] old = this.userNames;
			this.userNames = userNames == null ? new String[0] : userNames;
			pcs.firePropertyChange("userNames", old, this.userNames);
		}
	}

	public void addUserName(String name) {
		if (!containsUserName(name)) {
			String[] newNames = new String[userNames.length + 1];
			for (int i=0; i<userNames.length;i++){
				newNames[i] = userNames[i];
			}
			newNames[newNames.length - 1] = name;
			setUserNames(newNames);
		}
	}

	public void removeUserName(String name) {
		if (containsUserName(name)) {
			String[] newNames = new String[userNames.length - 1];
			int index = 0;
			for (String s : userNames) {
				if (!s.equals(name)) {
					newNames[index++] = s;
				}
			}
			setUserNames(newNames);
		}
	}

	public boolean containsUserName(String name) {
		for (String s : userNames) {
			if (s.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Preferences getPreferences() {
		return prefs;
	}

	public void setPreferences(Preferences prefs) {
		initPrefs();
		if (this.prefs != prefs) {
			Preferences old = this.prefs;
			this.prefs = prefs;
			pcs.firePropertyChange("preferences", old, prefs);
			//if prefs is null, this next method will create the default prefs node
			loadUserNames();
		}
	}

	private void initPrefs() {
		if (prefs == null) {
			prefs = Preferences.userNodeForPackage(MainWindow.class);
			// Use a relative path
			prefs = Preferences.userRoot().node("/PDF");
		}
	}
}
