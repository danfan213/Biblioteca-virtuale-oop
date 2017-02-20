package view;

import gui.HomeAdmin;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.User_model;
import controller.Admin_user;

public class AdminView {
	User_model user;
	Admin_user controllerAdmin;

	public AdminView(User_model user) {
		super();
		this.user = user;
	}

	public AdminView() {
		super();
	}

	public void InitializeView() throws IOException {
		HomeAdmin window = new HomeAdmin();
		window.getHome(user);

	}

	public HashMap<String, String> usersGroup() {
		HashMap<String, String> ris = new HashMap<>();
		List<User_model> usMod = new ArrayList<>();
		this.controllerAdmin = new Admin_user(user);
		usMod = this.controllerAdmin.UsersGroups();
		for (User_model m : usMod) {
			ris.put(m.getUsername(), m.getGroup());
		}

		return ris;
	}

	public boolean updateGroup(String username, String Group) {
		boolean resp;
		this.controllerAdmin = new Admin_user(user);
		resp = this.controllerAdmin.updateGroup(username, Group);
		return resp;
	}

	public void logout(JFrame frame) {
		frame.dispose();
		this.controllerAdmin = new Admin_user(user);
		this.controllerAdmin.log();

	}

	public List<String> usernameList() {
		List<String> list = new ArrayList<>();
		List<User_model> listModel = new ArrayList<>();
		this.controllerAdmin = new Admin_user(this.user);
		listModel = this.controllerAdmin.UsersListmodel();
		for (User_model m : listModel) {
			list.add(m.getUsername());
		}
		return list;
	}

	public boolean deleteUser(String username) {
		boolean ris;
		this.controllerAdmin = new Admin_user(user);
		ris = this.controllerAdmin.deleteUser(username);

		return ris;
	}

	public boolean deleteBook(String name) {
		boolean ris;
		this.controllerAdmin = new Admin_user(user);
		ris = this.controllerAdmin.deleteBook(name);

		return ris;
	}

	public List<PageBook_model> lastPages() {
		List<PageBook_model> list = new ArrayList<>();
		this.controllerAdmin = new Admin_user(user);
		list = this.controllerAdmin.listPages();
		return list;
	}

//	public ImageIcon getImage(java.sql.Blob blob) {
//		byte[] image = null;
//		try {
//			image = blob.getBytes(1, (int) blob.length());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		Image img = Toolkit.getDefaultToolkit().createImage(image);
//		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
//				.getScaledInstance(200, 280, Image.SCALE_DEFAULT));
//
//		return icon;
//	}
//
//	public ImageIcon getImageAuthor(java.sql.Blob blob) {
//		byte[] image = null;
//		try {
//			image = blob.getBytes(1, (int) blob.length());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		Image img = Toolkit.getDefaultToolkit().createImage(image);
//		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
//				.getScaledInstance(150, 200, Image.SCALE_DEFAULT));
//		icon=new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
//		return icon;
//	}
//	
//	public ImageIcon getImageSingle(java.sql.Blob blob) {
//		byte[] image = null;
//		try {
//			image = blob.getBytes(1, (int) blob.length());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		Image img = Toolkit.getDefaultToolkit().createImage(image);
//		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
//				.getScaledInstance(350, 500, Image.SCALE_DEFAULT));
//
//		return icon;
//	}
	
	

	public List<Book_model> getBookSearch(String title) {
		List<Book_model> list = new ArrayList<>();
		this.controllerAdmin = new Admin_user(user);

		list = this.controllerAdmin.searchBook(title);

		return list;
	}

	// ///Torno l'hashmap per avere un'associazione diretta tra nome libro e
	// modello
	public HashMap<String, Book_model> getBookManagerList() {
		HashMap<String, Book_model> list = new HashMap<>();
		List<Book_model> listRis = new ArrayList<>();
		this.controllerAdmin = new Admin_user(null);
		listRis = this.controllerAdmin.searchBook();
		for (Book_model m : listRis) {
			list.put(m.getName(), m);
		}
		return list;

	}

	public boolean insertBook(String name, int autore, File image, int anno,
			int numPag) throws FileNotFoundException {
		boolean ris;
		this.controllerAdmin = new Admin_user(null);
		ris = this.controllerAdmin
				.insertBook(name, autore, image, anno, numPag);
		return ris;
	}

	public boolean insertBook(String name, int autore, int anno, int numPag)
			throws FileNotFoundException {
		File image = new File("C:\\Users\\Sony\\Desktop\\SCRUBS\\book.jpg");
		boolean ris;
		this.controllerAdmin = new Admin_user(null);
		ris = this.controllerAdmin
				.insertBook(name, autore, image, anno, numPag);

		return ris;
	}

	public HashMap<String, Integer> listAuthor() {
		HashMap<String, Integer> list = new HashMap<>();
		List<Author_model> listQuery = new ArrayList<>();
		this.controllerAdmin = new Admin_user(null);
		listQuery = this.controllerAdmin.listAuthor();
		for (Author_model m : listQuery) {
			list.put(m.getName(), m.getId_author());
		}

		return list;
	}

	public boolean updateBook(String name, int autore, File image, int anno,
			int numPag, int id) {
		boolean ris = false;
		this.controllerAdmin = new Admin_user(null);
		ris = this.controllerAdmin.updateBook(name, autore, image, anno,
				numPag, id);

		return ris;
	}

	public boolean insertAuthor(String name, File image) {
		boolean ris;
		this.controllerAdmin = new Admin_user(null);
		ris = this.controllerAdmin.insertAuthor(name, image);
		return ris;
	}

	public List<Book_model> getAuthorBookList(int idAuthor) {
		List<Book_model> listBook = new ArrayList<>();
		List<Author_model> listAuthor = new ArrayList<>();
		Author_model author = new Author_model(0, null, null);
		this.controllerAdmin = new Admin_user(user);
		listAuthor = this.controllerAdmin.getAuthorBooks(idAuthor);
		for (Author_model element : listAuthor) {
			author = element;
		}
		listBook = author.getList_book();
		return listBook;
	}

}
