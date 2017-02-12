package view;

import gui.Login;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.User_model;
import controller.UserCon;

public class LoginView {

	private JFrame logJFrame;
	UserCon controllerUser = new UserCon();
	private List<User_model> listModel = new ArrayList<>();

	

	public void Initialize() {
		Login frame = new Login();
		this.logJFrame = frame.Initialize();
	}

	public void registrazione() {
		this.controllerUser.InitReg();
	}

	public List<User_model> loginCheck(String username, char[] password) {

		this.listModel = this.controllerUser.checkLog(username, password);
		return this.listModel;

	}

	public void logAsBasic() {
		User_model u = new User_model(0, null, null, null, null);
		UserCon con = new UserCon(u);
		con.basicUserLog();
	}

	public void login(User_model u) {
		UserCon con = new UserCon(u);
		con.userLogin();
	}

}
