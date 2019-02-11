package com.dong.matko.toolbox.cpw;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginListener;


public class LoginListenerCPW implements LoginListener {
	@SuppressWarnings("unused")
	private JXLoginPane panel;
	public LoginListenerCPW(JXLoginPane panel) {
		this.panel = panel;
	}

	public void loginSucceeded(LoginEvent source) {
		//MainWindow.prefs.putLastLogin(panel.getUserName());
	}
	public void loginStarted(LoginEvent source) {
	}
	public void loginFailed(LoginEvent source) {
	}
	public void loginCanceled(LoginEvent source) {
	}
}
