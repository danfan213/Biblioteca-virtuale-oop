package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.User_model;
import view.ExpertView;
import datalayer.dbms.AuthorMysqlImpl;
import datalayer.dbms.BookMysqlImpl;
import datalayer.dbms.PageBookMysqlImpl;
import datalayer.dbms.TranscriptMysqlImpl;

public class Expert_user extends Logged_user{
	 public Expert_user(User_model user) {
		 super(user);
	 }
	

	

	

	@Override
	public void Initialize() {
		ExpertView view=new ExpertView(getUser());
		try {
			view.InitializeView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override //override del metodo astratto ritorna le pagine confermate senza trascrizione
	public List<PageBook_model> listPages() {
		List <PageBook_model> list=new ArrayList<>();
		list=this.database.listPages();
		return list;
	}
	
	
	//ritorna la lista di tutti i libri
	public List<Book_model> searchBook() {
		List<Book_model> list = new ArrayList<>();
		list = this.database.searchBook();
		return list;
		
	}






	public List<Book_model> listNameBook() {
		List<Book_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("id_book");
		fields.add("name");
		list=this.database.listNameBook(fields);
		return list;
	}






	public List<Book_model> totalPageBook(Integer idBook) {
		List<Book_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("num_total_page");
		list=this.database.totalPageBook(idBook, fields);
		return list;
	}






	public List<PageBook_model> checkExistingPage(int numPag, Integer idBook) {
		List<PageBook_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("id_pagebook");
		list=this.database.checkExistingPage(numPag, idBook, fields);
		return list;
	}






	public boolean insertNewPage(Integer idBook, File image, int numPag,User_model user) {
		boolean ris;
		ris=this.database.insertNewPage(idBook, image, numPag, user);
		return ris;
	}






	public Book_model getBook(int idBook) {
		Book_model book=new Book_model(null, 0, 0, null, null, null);
		book=this.database.getBook(idBook);
		return book;
	}






	public boolean InsertNewTranscript(int id_User_model, int id_pagebook,
			String TEI, String string) {
		Boolean ris=this.database.InsertNewTranscript(id_User_model, id_pagebook, TEI, string);
		return ris;
	}






	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();
		list = this.database.getAuthorBooks(idAuthor);
		return list;
	}

	

}
