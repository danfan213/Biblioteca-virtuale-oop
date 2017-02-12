package model;

import com.mysql.jdbc.Blob;

public class PageBook_model {
	private int id_pagebook;
	private int num_pag;
	private Book_model book;
	private java.sql.Blob image;
	private Transcript_model transcript;
	private User_model user;
	private String is_confirmed;
	
	public PageBook_model(int id_pagebook, int num_pag, Book_model book,
			Blob image,Transcript_model transcript,User_model user) {
		this.id_pagebook = id_pagebook;
		this.num_pag = num_pag;
		this.book = book;
		this.image = image;
		this.transcript=transcript;
		this.user=user;
	}

	public int getId_pagebook() {
		return id_pagebook;
	}

	public void setId_pagebook(int id_pagebook) {
		this.id_pagebook = id_pagebook;
	}

	public int getNum_pag() {
		return num_pag;
	}

	public void setNum_pag(int num_pag) {
		this.num_pag = num_pag;
	}

	public Book_model getBook() {
		return book;
	}

	public void setBook(Book_model book) {
		this.book = book;
	}

	public java.sql.Blob getImage() {
		return image;
	}

	public void setImage(java.sql.Blob blob) {
		this.image = blob;
	}

	public Transcript_model getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript_model transcript) {
		this.transcript = transcript;
	}

	public User_model getUser() {
		return user;
	}

	public void setUser(User_model user) {
		this.user = user;
	}

	public String getIs_confirmed() {
		return is_confirmed;
	}

	public void setIs_confirmed(String is_confirmed) {
		this.is_confirmed = is_confirmed;
	}
	
	
	

}
