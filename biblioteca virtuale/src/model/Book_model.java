package model;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import javax.swing.ImageIcon;

public class Book_model {
	private int id_book;
	private String name;
	private int num_total_page;
	private int release_year;
	private Author_model author;
	private List<PageBook_model> pagebook;
	private ImageIcon image;
	
	
	public Book_model(String name, int num_total_page,
			int release_year, Author_model author,List<PageBook_model> pagebook,ImageIcon image) {
		//super();
//		this.id_book = id_book;
		this.name = name;
		this.num_total_page = num_total_page;
		this.release_year = release_year;
		this.author = author;
		this.pagebook=pagebook;
		this.image=image;
	}


	public int getId_book() {
		return id_book;
	}


	public void setId_book(int id_book) {
		this.id_book = id_book;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getNum_total_page() {
		return num_total_page;
	}


	public void setNum_total_page(int num_total_page) {
		this.num_total_page = num_total_page;
	}


	public int getRelease_year() {
		return release_year;
	}


	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}


	





	public Author_model getAuthor() {
		return author;
	}
	


	public void setAuthor(Author_model author) {
		this.author = author;
	}


	public List<PageBook_model> getPagebook() {
		return pagebook;
	}


	public void setPagebook(List<PageBook_model> pagebook) {
		this.pagebook = pagebook;
	}


	public ImageIcon getImage() {
		return image;
	}


	public void setImage(ImageIcon image) {
		this.image = image;
	}

	
	

	
	

	
	
	
	

}
