package view;

import gui.Registration;

import javax.swing.JFrame;

import controller.UserCon;

public class RegistrationView {
	JFrame regPanel;
	UserCon controllerUser = new UserCon();

	public void InitializeView() {
		Registration window = new Registration();
		this.regPanel = window.initialize();
	}

	public boolean registrazione(String username, String password, String email) {
		boolean ris = this.controllerUser.checkReg(username, password, email);
		if (ris == false) {
			return false;
		} else {
			return true;
		}
	}

	public void register() {

		LoginView log = new LoginView();

		log.Initialize();
	}

}
