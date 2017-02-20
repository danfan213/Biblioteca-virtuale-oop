package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.PageBook_model;
import model.User_model;
import view.EditorPageView;
import datalayer.dbms.AuthorMysqlImpl;
import datalayer.dbms.PageBookMysqlImpl;

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
		list = this.database.listRevPages();
		return list;
	}

	// CONFERMA O RIFIUTO DI UNA PAGINA DA PARTE DI UN EDITOR_PAGE

	public boolean responseEditor(String string, PageBook_model page) {
		boolean ris;
		ris = this.database.responseEditorPage(string, page);
		return ris;
	}

	// RITORNA LA LISTA DI TUTTI I LIBRI DI UN AUTORE
	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();
		list = this.database.getAuthorBooks(idAuthor);
		return list;
	}

}