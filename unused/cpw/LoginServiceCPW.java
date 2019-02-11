package com.dong.matko.toolbox.cpw;
import org.jdesktop.swingx.auth.LoginService;

import com.dong.matko.toolbox.MainWindow;

public class LoginServiceCPW extends LoginService {
	public LoginServiceCPW() {
		System.out.println("Created service");
	}
	public boolean authenticate(final String username, char[] pass, String server) throws Exception {
		MainWindow.SPc = new SharePointClient(username, String.valueOf(pass));
		if(MainWindow.SPc.initSoaps()){
			return true;
		}
		return false;
	}
}