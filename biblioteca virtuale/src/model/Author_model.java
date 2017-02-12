package model;

import java.sql.Date;
import java.util.List;

public class Author_model {
	private int id_author;
	private String name;
	private java.sql.Blob image;
	private List<Book_model> list_book;

	public Author_model(int id_Author_model, String name,
			java.sql.Blob image) {
		// this.id_Author_model = id_Author_model;
		this.name = name;
		this.image= image;
	}

	public int getId_author() {
		return id_author;
	}

	public void setId_author(int id_author) {
		this.id_author = id_author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	

	public java.sql.Blob getImage() {
		return image;
	}

	public void setImage(java.sql.Blob image) {
		this.image = image;
	}

	public List<Book_model> getList_book() {
		return list_book;
	}

	public void setList_book(List<Book_model> list_book) {
		this.list_book = list_book;
	}

}
