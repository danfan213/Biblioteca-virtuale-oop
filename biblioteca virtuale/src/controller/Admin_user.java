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
import datalayer.AuthorMysqlImpl;
import datalayer.BookMysqlImpl;
import datalayer.PageBookMysqlImpl;
import datalayer.UserMysqlImpl;

public class Admin_user extends Logged_user {

	public Admin_user(User_model user) {
		super(user);
	}

	@Override
	// override del metodo astratto ritorna le pagine non confermate
	public List<PageBook_model> listPages() {
		List<PageBook_model> list = new ArrayList<>();
		this.modelPageBook = new PageBookMysqlImpl();
		String stm = "is_confirmed='not' ORDER BY date_ins DESC LIMIT 5 ";
		list = this.modelPageBook.selectAll(stm, false);
		return list;
	}

	// torna tutti la lista di tutti i libri
	public List<Book_model> searchBook() {
		List<Book_model> list = new ArrayList<>();
		this.modelBook = new BookMysqlImpl();
		String stm = "1";

		list = this.modelBook.selectAll(stm, false);
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
		listUsers = this.modelUser
				.selectField(fields,
						"group_id!=(SELECT id_group from `group` where name='admin_user')");
		return listUsers;
	}

	// cambia il ruolo di un utente
	public boolean updateGroup(String username, String group) {
		Boolean resp;
		this.modelUser = new UserMysqlImpl();
		int id = this.modelUser.getGroupId(group);
		String set = "group_id=" + id;
		String stm = "username='" + username + "'";
		resp = this.modelUser.update(set, stm);
		return resp;
	}

	// ritorna l'elenco di tutti gli utenti ad eccetto dell'admin loggato
	public List<User_model> UsersListmodel() {
		List<User_model> list = new ArrayList<>();
		this.modelUser = new UserMysqlImpl();
		List<String> fields = new ArrayList<>();
		fields.add("username");
		list = this.modelUser.selectField(fields, "id_user!="
				+ this.getUser().getId_User_model() + " ORDER BY username ASC");

		return list;
	}

	public boolean deleteUser(String username) {
		boolean ris = false;
		this.modelUser = new UserMysqlImpl();
		String stm = "username='" + username + "'";
		ris = this.modelUser.delete(stm);

		return ris;
	}

	public boolean deleteBook(String name) {
		boolean ris = false;
		this.modelBook = new BookMysqlImpl();
		String stm = "name='" + name + "'";
		ris = this.modelBook.delete(stm);

		return ris;
	}

	public boolean insertBook(String name, int autore, File image, int anno,
			int numPag) {
		boolean ris;

		String set = "image,name,author_id,num_total_page,release_year";
		String stm = "'" + name + "'," + autore + "," + numPag + "," + anno;
		this.modelBook = new BookMysqlImpl();
		ris = this.modelBook.insert(set, stm, image);

		return ris;
	}

	// lista degli autori che serve per popolare la JComboBox
	public List<Author_model> listAuthor() {
		List<Author_model> list = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		fields.add("id_author");
		fields.add("name");
		this.modelAuthor = new AuthorMysqlImpl();
		list = this.modelAuthor.selectField(fields, "1");
		return list;
	}

	public boolean updateBook(String name, int autore, File image, int anno,
			int numPag, int id) {
		boolean ris;
		String set = "name='" + name + "',author_id=" + autore
				+ ",release_year=" + anno + ",num_total_page=" + numPag;
		String stm = "id_book=" + id;
		this.modelBook = new BookMysqlImpl();
		ris = this.modelBook.update(set, stm, image);

		return ris;
	}

	public boolean insertAuthor(String name, File image) {
		boolean ris;

		String set = "image,name";
		String stm = "'" + name + "'";
		this.modelAuthor = new AuthorMysqlImpl();
		ris = this.modelAuthor.insert(set, stm, image);

		return ris;

	}

	// ritorna la lista di tutti i libri di un autore
	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();

		this.modelAuthor = new AuthorMysqlImpl();
		list = this.modelAuthor.selectAll("id_author=" + idAuthor, false);
		return list;
	}

}
