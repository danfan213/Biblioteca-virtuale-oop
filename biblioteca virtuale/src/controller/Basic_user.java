package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Book_model;
import model.PageBook_model;
import view.BasicView;
import datalayer.BookMysqlImpl;
import datalayer.PageBookMysqlImpl;

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
		this.modelPageBook = new PageBookMysqlImpl();
		list = this.modelPageBook.selectField(fields,
				" is_confirmed='yes' ORDER BY date_ins DESC LIMIT 5");
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
		this.modelBook = new BookMysqlImpl();
		fields.add("name");
		fields.add("image");
		fields.add("num_total_page");
		String stm = "name LIKE '%" + title + "%'";
		list = this.modelBook.selectField(fields, stm);
		return list;

	}

	// ritorna le ultime 5 pagine con la trascrizione accettata
	public List<PageBook_model> listTranscripts() {
		List<PageBook_model> list = new ArrayList<>();

		this.modelPageBook = new PageBookMysqlImpl();
		list = this.modelPageBook.selectTran(false, "yes");
		return list;
	}

}
