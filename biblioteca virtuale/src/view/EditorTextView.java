package view;

import gui.HomeEditorText;

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
import model.Transcript_model;
import model.User_model;
import controller.EditorText_user;

public class EditorTextView {
	private User_model user;
	private EditorText_user controllerEditorText;

	private JFrame home;

	public EditorTextView(User_model user) {
		super();
		this.user = user;
	}

	public EditorTextView() {
		super();
	}

	public void InitializeView() {
		HomeEditorText window = new HomeEditorText();
		try {
			this.home = window.getHome(this.user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ImageIcon getImage(java.sql.Blob blob) {
		byte[] image = null;
		try {
			image = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Image img = Toolkit.getDefaultToolkit().createImage(image);
		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
				.getScaledInstance(200, 300, Image.SCALE_DEFAULT));

		return icon;
	}

	public ImageIcon getImageAuthor(java.sql.Blob blob) {
		byte[] image = null;
		try {
			image = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Image img = Toolkit.getDefaultToolkit().createImage(image);
		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
				.getScaledInstance(150, 200, Image.SCALE_DEFAULT));

		return icon;
	}

	public ImageIcon getImageSingle(java.sql.Blob blob) {
		byte[] image = null;
		try {
			image = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Image img = Toolkit.getDefaultToolkit().createImage(image);
		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
				.getScaledInstance(350, 500, Image.SCALE_DEFAULT));

		return icon;
	}

	public void logout(JFrame frame) {
		frame.dispose();
		this.controllerEditorText = new EditorText_user(user);
		this.controllerEditorText.log();
	}

	public List<PageBook_model> listPages() {
		this.controllerEditorText = new EditorText_user(user);
		List<PageBook_model> list = new ArrayList<>();
		list = this.controllerEditorText.listPages();

		return list;

	}

	public boolean acceptPage(Transcript_model transcript_model) {
		boolean ris;
		this.controllerEditorText = new EditorText_user(user);
		ris = this.controllerEditorText.responseEditor("yes", transcript_model);
		return ris;
	}

	public boolean refusePage(Transcript_model transcript_model) {
		boolean ris;
		this.controllerEditorText = new EditorText_user(user);
		ris = this.controllerEditorText.responseEditor("not", transcript_model);
		return ris;
	}

	public List<Book_model> getBookSearch(String title) {
		List<Book_model> list = new ArrayList<>();
		this.controllerEditorText = new EditorText_user(user);

		list = this.controllerEditorText.searchBook(title);

		return list;
	}

	public List<Book_model> getAuthorBookList(int idAuthor) {
		List<Book_model> listBook = new ArrayList<>();
		List<Author_model> listAuthor = new ArrayList<>();
		Author_model author = new Author_model(0, null, null);
		this.controllerEditorText = new EditorText_user(user);
		listAuthor = this.controllerEditorText.getAuthorBooks(idAuthor);
		for (Author_model element : listAuthor) {
			author = element;
		}
		listBook = author.getList_book();
		return listBook;
	}
}
