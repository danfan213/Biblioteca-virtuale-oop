package view;

import gui.HomeBasic;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Book_model;
import model.PageBook_model;
import controller.Basic_user;

public class BasicView {
	Basic_user controllerBasic;
	public void InitializeView() {
		HomeBasic window = new HomeBasic();
	
		try {
			 window.getHome();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void logout(JFrame frame) {
		frame.dispose();
		this.controllerBasic = new Basic_user();
		this.controllerBasic.log();

	}

	public List<Book_model> getBookSearch(String title) {
		List<Book_model> list = new ArrayList<>();
		this.controllerBasic = new Basic_user();

		list = this.controllerBasic.searchBook(title);

		return list;
	}

	public ImageIcon getImage(java.sql.Blob blob) {
		byte[] image = null;
		try {
			image = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image img = Toolkit.getDefaultToolkit().createImage(image);
		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
				.getScaledInstance(200, 280, Image.SCALE_DEFAULT));

		return icon;
	}

	public List<PageBook_model> listPages() {
		List<PageBook_model> list = new ArrayList<>();
		this.controllerBasic = new Basic_user();
		list = this.controllerBasic.listPages();
		return list;
	}

	public List<PageBook_model> listTrascripts() {
		List<PageBook_model> list = new ArrayList<>();
		this.controllerBasic = new Basic_user();
		list = this.controllerBasic.listTranscripts();
		return list;
	}

}
