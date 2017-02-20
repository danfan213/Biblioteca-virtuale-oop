package view;

import gui.HomeEditorPage;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.User_model;
import controller.EditorPage_user;

public class EditorPageView {

	private User_model user;
	private EditorPage_user controllerEditorPage;

	public EditorPageView(User_model user) {
		super();
		this.user = user;
	}

	public EditorPageView() {
		super();
	}

	public void InitializeView() throws IOException {
		HomeEditorPage window = new HomeEditorPage();
		window.getHome(user);

	}

//	public ImageIcon getImage(java.sql.Blob blob) {
//		byte[] image = null;
//		try {
//			image = blob.getBytes(1, (int) blob.length());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Image img = Toolkit.getDefaultToolkit().createImage(image);
//		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
//				.getScaledInstance(200, 300, Image.SCALE_DEFAULT));
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
//
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

	public void logout(JFrame frame) {
		frame.dispose();
		this.controllerEditorPage = new EditorPage_user(user);
		this.controllerEditorPage.log();
	}

	public List<PageBook_model> listPages() {
		this.controllerEditorPage = new EditorPage_user(user);
		List<PageBook_model> list = new ArrayList<>();
		list = this.controllerEditorPage.listPages();

		return list;

	}

	public boolean acceptPage(PageBook_model n) {
		boolean ris;
		this.controllerEditorPage = new EditorPage_user(user);
		ris = this.controllerEditorPage.responseEditor("yes", n);
		return ris;
	}

	public boolean refusePage(PageBook_model n) {
		boolean ris;
		this.controllerEditorPage = new EditorPage_user(user);
		ris = this.controllerEditorPage.responseEditor("not", n);
		return ris;
	}

	public List<Book_model> getBookSearch(String title) {
		List<Book_model> list = new ArrayList<>();
		this.controllerEditorPage = new EditorPage_user(user);

		list = this.controllerEditorPage.searchBook(title);

		return list;
	}

	public List<Book_model> getAuthorBookList(int idAuthor) {
		List<Book_model> listBook = new ArrayList<>();
		List<Author_model> listAuthor = new ArrayList<>();
		Author_model author = new Author_model(0, null, null);
		this.controllerEditorPage = new EditorPage_user(user);
		listAuthor = this.controllerEditorPage.getAuthorBooks(idAuthor);
		for (Author_model element : listAuthor) {
			author = element;
		}
		listBook = author.getList_book();
		return listBook;
	}

}
