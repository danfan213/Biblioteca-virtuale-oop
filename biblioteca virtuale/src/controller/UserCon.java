package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.User_model;
import view.LoginView;
import view.RegistrationView;
import datalayer.dbms.ImplDbms;
import datalayer.dbms.UserMysqlImpl;

public class UserCon {
	private User_model user;
	private ImplDbms database=new ImplDbms();
	public UserCon(User_model user) {
		super();
		this.user = user;
	}

	public UserCon() {
		super();
	}

	// / pannello login
	public void InitLog() {
		LoginView logView = new LoginView();
		logView.Initialize();
	}

	// pannello registrazione
	public void InitReg() {
		RegistrationView reg = new RegistrationView();
		reg.InitializeView();
	}

	// login in base al gruppo dell'utente
	public void userLogin() {
		String group = this.user.getGroup();
		User_model userTemp = this.user;
		if (group.equals("admin_user")) {
			Admin_user admin = new Admin_user(userTemp);
			admin.Initialize();
		} else if (group.equals("expert_user")) {
			Expert_user expert = new Expert_user(userTemp);
			expert.Initialize();
		} else if (group.equals("editor_page")) {
			EditorPage_user editorPage = new EditorPage_user(userTemp);
			editorPage.Initialize();
		} else if (group.equals("editor_text")) {
			EditorText_user editorText = new EditorText_user(userTemp);
			editorText.Initialize();
		}

	}

	// login come utente basic
	public void basicUserLog() {

		Basic_user basic = new Basic_user();
		try {
			basic.Initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// registrazione di un utente come expert_user
	public boolean checkReg(String username, String password, String email) {
		List<String> list = new ArrayList<>();
		list.add("id_user");
		boolean test=this.database.checkReg(username, password, email, list);

		return test;
	}

	// controllo se esiste utente per login
	public List<User_model> checkLog(String username, char[] passwordpar) {
		List<User_model> listModel = new ArrayList<>();
		
		listModel =this.database.checkLog(username, passwordpar) ;
		System.out.println("ciao");
		return listModel;
	}

}
