package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Book_model;
import model.PageBook_model;
import view.BasicView;
import datalayer.dbms.BookMysqlImpl;
import datalayer.dbms.PageBookMysqlImpl;

public class Basic_user extends Logged_user {

	@Override
	// override del metodo astratto torna numero pagine immagine e il libro di
	// cui fa parte delle ultime 5 pagine confermate
	public List<PageBook_model> listPages() {
		List<PageBook_model> list = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		fields.add("num_pag");
		fields.add("image");
		fields.add("book_id");
		list = this.database.listLastPagesStateYes(fields);
		return list;
	}

	@Override
	public void Initialize() throws IOException {
		BasicView view = new BasicView();
		view.InitializeView();

	}

	@Override
	// ///dynamic binding per basic_user ricerca solo il nome,la copertina e il
	// numero di pagine del libro
	public List<Book_model> searchBook(String title) {
		List<Book_model> list = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("image");
		fields.add("num_total_page");
		list = this.database.searchBasicBook(title, fields);
		return list;

	}

	// ritorna le ultime 5 pagine con la trascrizione accettata
	public List<PageBook_model> listTranscripts() {
		List<PageBook_model> list = new ArrayList<>();
		list = this.database.listLastTranscripts();
		return list;
	}

}
