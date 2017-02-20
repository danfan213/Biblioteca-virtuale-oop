package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Book_model;
import model.PageBook_model;
import model.User_model;
import view.LoginView;
import datalayer.dbms.AuthorMysqlImpl;
import datalayer.dbms.BookMysqlImpl;
import datalayer.dbms.ImplDbms;
import datalayer.dbms.PageBookMysqlImpl;
import datalayer.dbms.TranscriptMysqlImpl;
import datalayer.dbms.UserMysqlImpl;

public abstract class Logged_user {
	
	private User_model user;
	protected UserMysqlImpl modelUser;
	protected PageBookMysqlImpl modelPageBook;
	protected BookMysqlImpl modelBook;
	protected AuthorMysqlImpl modelAuthor;
	protected TranscriptMysqlImpl modelTranscript;
	protected ImplDbms database=new ImplDbms(); 
	
	
	public Logged_user(User_model user) {
		super();
		this.user = user;
	}
	
	public Logged_user(){
		this.user=null;
	}

	public User_model getUser() {
		return user;
	}

	public void setUser(User_model user) {
		this.user = user;
	}
	public  void log(){
		this.setUser(null);
		LoginView log=new LoginView();
		log.Initialize();
	}
	
	
	public List<Book_model> searchBook(String title) {			
		List<Book_model>list=new ArrayList<>();
		list=this.database.searchBook(title);
		return list;
		
	}	
	public abstract List<PageBook_model>listPages(); 
	public abstract void Initialize() throws IOException;
	


	

}
