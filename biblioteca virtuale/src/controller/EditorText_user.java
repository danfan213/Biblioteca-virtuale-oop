package controller;

import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;
import view.EditorTextView;
import datalayer.dbms.AuthorMysqlImpl;
import datalayer.dbms.PageBookMysqlImpl;
import datalayer.dbms.TranscriptMysqlImpl;

public class EditorText_user extends Logged_user {
	public EditorText_user(User_model user) {
		 super(user);
	 }
	

		@Override
	public void Initialize() {
		EditorTextView view=new EditorTextView(getUser());
		view.InitializeView();
}

	
	@Override //override del metodo astratto torna le trascrizioni da revisionare
	public List<PageBook_model> listPages() {
		List<PageBook_model>list=new ArrayList<>();
		list=this.database.listRevTran();
		return list;
	}

		//conferma o rifiuto di una trascrizione da parte di un editor_text
public boolean responseEditor(String string, Transcript_model page) {
		boolean ris;
		ris=this.database.responseEditorTran(string, page);
		
		return ris;
	} 

	//lista dei libri di un autore
public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();
		list = this.database.getAuthorBooks(idAuthor);
		return list;
	}

}
