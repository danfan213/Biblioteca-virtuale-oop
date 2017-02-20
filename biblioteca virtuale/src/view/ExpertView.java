package view;

import gui.HomeExpert;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
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
import controller.Expert_user;

public class ExpertView {
	private User_model user;
	private Expert_user controllerExpert;

	public ExpertView(User_model user) {
		super();
		this.user = user;
	}

	public ExpertView() {
		super();
	}

	public void InitializeView() throws IOException {
		HomeExpert window = new HomeExpert();

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

	public List<Book_model> getBookSearch(String title) {
		List<Book_model> list = new ArrayList<>();
		this.controllerExpert = new Expert_user(user);

		list = this.controllerExpert.searchBook(title);

		return list;
	}

	public List<Book_model> getBookList() {
		List<Book_model> listRis = new ArrayList<>();
		this.controllerExpert = new Expert_user(null);
		listRis = this.controllerExpert.searchBook();

		return listRis;

	}

	public void logout(JFrame frame) {
		frame.dispose();
		this.controllerExpert = new Expert_user(user);
		this.controllerExpert.log();
	}

	public HashMap<String, Integer> listBook() {
		List<Book_model> listRis = new ArrayList<>();
		HashMap<String, Integer> list = new HashMap<>();
		this.controllerExpert = new Expert_user(null);
		listRis = this.controllerExpert.listNameBook();
		for (Book_model m : listRis) {
			list.put(m.getName(), m.getId_book());
		}
		return list;
	}

	public boolean insertNewPage(Integer idBook, File image, int numPag,
			User_model user) {
		boolean ris;
		this.controllerExpert = new Expert_user(null);
		ris = this.controllerExpert.insertNewPage(idBook, image, numPag, user);
		return ris;
	}

	public boolean checkNumPage(Integer idBook, int numPag) {
		int totalPageBook = 0;

		List<Book_model> list = new ArrayList<>();
		list = this.controllerExpert.totalPageBook(idBook);
		for (Book_model m : list) {
			totalPageBook = m.getNum_total_page();
		}
		if (numPag > totalPageBook) {
			return false;
		}
		List<PageBook_model> listPage = new ArrayList<>();
		listPage = this.controllerExpert.checkExistingPage(numPag, idBook);
		if (!listPage.isEmpty()) {
			return false;
		}

		return true;
	}

	public List<PageBook_model> listPagesTran() {
		List<PageBook_model> list = new ArrayList<>();
		this.controllerExpert = new Expert_user(user);
		list = this.controllerExpert.listPages();
		return list;
	}

	public boolean InsertNewTrascript(PageBook_model page, String text) {
		boolean ris = false;
		Book_model book = new Book_model(null, 0, 0, null, null, null);

		this.controllerExpert = new Expert_user(user);

		int idBook = page.getBook().getId_book();
		book = this.controllerExpert.getBook(idBook);
		text = text.replaceAll("'", "''");

		String TEI = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<TEI.2>"
				+ "<teiHeader>\n" + "<fileDesc>\n" + "<titleStmt>\n"
				+ "<title>"
				+ book.getName()
				+ "</title>\n"
				+ "<year>("
				+ book.getRelease_year()
				+ ")</year>"
				+ "<author>"
				+ book.getAuthor().getName()
				+ "</author>\n"
				+ "</titleStmt>\n"
				+ "<publicationStmt>\n"
				+ "<user>"
				+ user.getUsername()
				+ "</user>\n"
				+ "</publicationStmt>\n"
				+

				"</fileDesc>\n"
				+ "</teiHeader>\n"
				+ "<text>\n"
				+ "<front>\n"
				+ "<titlePage>\n"
				+ "<docTitle>\n"
				+ "<titlePart>"
				+ page.getNum_pag()
				+ "</titlePart>\n"
				+ "</docTitle>\n"
				+ "</titlePage>\n"
				+ "</front>\n"
				+ "<body>\n"
				+ "<div>"
				+ text
				+ "</div>\n" + "</body>\n" + "</text>\n" + "</TEI.2>";

		ris = this.controllerExpert.InsertNewTranscript(
				user.getId_User_model(), page.getId_pagebook(), TEI, "wait");

		return ris;
	}

	public List<Book_model> getAuthorBookList(int idAuthor) {
		List<Book_model> listBook = new ArrayList<>();
		List<Author_model> listAuthor = new ArrayList<>();
		Author_model author = new Author_model(0, null, null);
		this.controllerExpert = new Expert_user(user);
		listAuthor = this.controllerExpert.getAuthorBooks(idAuthor);
		for (Author_model element : listAuthor) {
			author = element;
		}
		listBook = author.getList_book();
		return listBook;
	}

}
