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
import datalayer.AuthorMysqlImpl;
import datalayer.BookMysqlImpl;
import datalayer.PageBookMysqlImpl;
import datalayer.TranscriptMysqlImpl;

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
		this.modelPageBook=new PageBookMysqlImpl();
		
		list=this.modelPageBook.selectPagesNoTran(false);
		return list;
	}
	
	
	//ritorna la lista di tutti i libri
	public List<Book_model> searchBook() {
		List<Book_model>list=new ArrayList<>();
		this.modelBook=new BookMysqlImpl();
		String stm="1";
		
		list=this.modelBook.selectAll(stm, false);
		return list;
		
	}






	public List<Book_model> listNameBook() {
		List<Book_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("id_book");
		fields.add("name");
		this.modelBook=new BookMysqlImpl();
		list=this.modelBook.selectField(fields,"1");
		return list;
	}






	public List<Book_model> totalPageBook(Integer idBook) {
		List<Book_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("num_total_page");
		
		this.modelBook=new BookMysqlImpl();
		list=this.modelBook.selectField(fields,"id_book="+idBook);
		return list;
	}






	public List<PageBook_model> checkExistingPage(int numPag, Integer idBook) {
		List<PageBook_model> list=new ArrayList<>();
		List<String> fields=new ArrayList<>();
		fields.add("id_pagebook");
		
		this.modelPageBook=new PageBookMysqlImpl();
		list=this.modelPageBook.selectField(fields,"book_id="+idBook+" AND num_pag="+numPag+" AND is_confirmed='yes'");
		return list;
	}






	public boolean insertNewPage(Integer idBook, File image, int numPag,User_model user) {
boolean ris;
		
		String set="image,book_id,num_pag,is_confirmed,user_id";
		String stm=idBook+","+numPag+",'wait',"+user.getId_User_model();
		this.modelPageBook=new PageBookMysqlImpl();
		ris=this.modelPageBook.insert(set, stm,image);
		
		return ris;
	}






	public Book_model getBook(int idBook) {
		this.modelBook=new BookMysqlImpl();
		List<Book_model> list=new ArrayList<>();
		Book_model book=new Book_model(null, 0, 0, null, null, null);
		list=this.modelBook.selectAll("id_book="+idBook, false);
		for(Book_model element :list){
			book=element;
		}
		return book;
	}






	public boolean InsertNewTranscript(int id_User_model, int id_pagebook,
			String TEI, String string) {
		this.modelTranscript=new TranscriptMysqlImpl();
		String set="TEI,pagebook_id,user_id,is_confirmed";
		String stm="'"+TEI+"',"+id_pagebook+","+id_User_model+",'"+string+"'";
		Boolean ris=this.modelTranscript.insert(set, stm);
		return ris;
	}






	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list=new ArrayList<>();
		
		this.modelAuthor=new AuthorMysqlImpl();
		list=this.modelAuthor.selectAll("id_author="+idAuthor, false);
		return list;
	}

	

}
