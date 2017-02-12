package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.PageBook_model;
import model.User_model;
import view.EditorPageView;
import datalayer.AuthorMysqlImpl;
import datalayer.PageBookMysqlImpl;

public class EditorPage_user extends Logged_user {
	public EditorPage_user(User_model user) {
		super(user);
	}

	@Override
	public void Initialize() {
		EditorPageView view = new EditorPageView(getUser());
		try {
			view.InitializeView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	// OVERRIDE DEL METODO ASTRATTO RITORNA LE PAGINE IN ATTESA DI REVISIONE
	public List<PageBook_model> listPages() {
		List<PageBook_model> list = new ArrayList<>();
		this.modelPageBook = new PageBookMysqlImpl();
		list = this.modelPageBook.selectAll("is_confirmed='wait' ORDER BY date_ins DESC", false);
		return list;
	}

	// CONFERMA O RIFIUTO DI UNA PAGINA DA PARTE DI UN EDITOR_PAGE

	public boolean responseEditor(String string, PageBook_model page) {
		boolean ris;
		this.modelPageBook = new PageBookMysqlImpl();
		ris = this.modelPageBook.update(" is_confirmed='" + string + "'",
				"id_pageBook=" + page.getId_pagebook());
		return ris;
	}

	// RITORNA LA LISTA DI TUTTI I LIBRI DI UN AUTORE
	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();

		this.modelAuthor = new AuthorMysqlImpl();
		list = this.modelAuthor.selectAll("id_author=" + idAuthor, false);
		return list;
	}

}