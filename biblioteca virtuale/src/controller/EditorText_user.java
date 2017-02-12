package controller;

import java.util.ArrayList;
import java.util.List;

import model.Author_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;
import view.EditorTextView;
import datalayer.AuthorMysqlImpl;
import datalayer.PageBookMysqlImpl;
import datalayer.TranscriptMysqlImpl;

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
		this.modelPageBook=new PageBookMysqlImpl();
		list=this.modelPageBook.selectWaitConfirmTran(false);
		return list;
	}

		//conferma o rifiuto di una trascrizione da parte di un editor_text
public boolean responseEditor(String string, Transcript_model page) {
		boolean ris;
		this.modelTranscript=new TranscriptMysqlImpl();
		ris=this.modelTranscript.update(" is_confirmed='"+string+"'", "id_transcript="+page.getId_trancscript());
		
		return ris;
	} 

	//lista dei libri di un autore
public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();

		this.modelAuthor = new AuthorMysqlImpl();
		list = this.modelAuthor.selectAll("id_author=" + idAuthor, false);
		return list;
	}

}
