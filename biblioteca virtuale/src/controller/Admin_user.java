package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.User_model;
import view.AdminView;
import datalayer.dbms.AuthorMysqlImpl;
import datalayer.dbms.BookMysqlImpl;
import datalayer.dbms.PageBookMysqlImpl;
import datalayer.dbms.UserMysqlImpl;

public class Admin_user extends Logged_user {

	public Admin_user(User_model user) {
		super(user);
	}

	@Override
	// override del metodo astratto ritorna le pagine non confermate
	public List<PageBook_model> listPages() {
		List<PageBook_model> list = new ArrayList<>();
		list=this.database.listPageBookStateNot();
		return list;
	}

	// torna tutti la lista di tutti i libri
	public List<Book_model> searchBook() {
		List<Book_model> list = new ArrayList<>();
		list = this.database.searchBook();
		return list;

	}

	@Override
	public void Initialize() {
		AdminView view = new AdminView(getUser());
		try {
			view.InitializeView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ritorna l'elenco di tutti gli utenti ad eccetto degli admin
	public List<User_model> UsersGroups() {
		this.modelUser = new UserMysqlImpl();
		List<String> fields = new ArrayList<>();
		List<User_model> listUsers = new ArrayList<>();
		fields.add("username");
		fields.add("group_id");
		listUsers = this.database.UsersGroups(fields);
		return listUsers;
	}

	// cambia il ruolo di un utente
	public boolean updateGroup(String username, String group) {
		Boolean resp;
		resp=this.database.updateGroup(username, group);
		return resp;
	}

	// ritorna l'elenco di tutti gli utenti ad eccetto dell'admin loggato
	public List<User_model> UsersListmodel() {
		List<User_model> list = new ArrayList<>();
		this.modelUser = new UserMysqlImpl();
		List<String> fields = new ArrayList<>();
		fields.add("username");
		list = this.database.UsersListmodel(getUser().getId_User_model(), fields);

		return list;
	}

	public boolean deleteUser(String username) {
		boolean ris = false;
		ris = this.database.deleteUser(username);
		return ris;
	}

	public boolean deleteBook(String name) {
		boolean ris = false;
		ris = this.database.deleteBook(name);
		return ris;
	}

	public boolean insertBook(String name, int autore, File image, int anno,
			int numPag) {
		boolean ris;
		ris = this.database.insertBook(name, autore, image, anno, numPag);
		return ris;
	}

	// lista degli autori che serve per popolare la JComboBox
	public List<Author_model> listAuthor() {
		List<Author_model> list = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		fields.add("id_author");
		fields.add("name");
		list = this.database.listAuthor(fields);
		return list;
	}

	public boolean updateBook(String name, int autore, File image, int anno,
			int numPag, int id) {
		boolean ris;
		ris = this.database.updateBook(name, autore, image, anno, numPag, id);
		return ris;
	}

	public boolean insertAuthor(String name, File image) {
		boolean ris;
		ris = this.database.insertAuthor(name, image);
		return ris;

	}

	// ritorna la lista di tutti i libri di un autore
	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();
		list = this.database.getAuthorBooks(idAuthor);
		return list;
	}

}
