package datalayer;

import java.io.File;
import java.util.List;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;

public interface DatabaseInterface {
	
public List<Book_model> searchBook(String title);
public List<PageBook_model> listPageBookStateNot();//ADMIN_USER LISTA PAGINE LIBRO RIFIUTATE
public List<User_model> UsersGroups(List<String> fields);//ADMIN_USER: LISTA DI TUTTI GLI UTENTI REGISTRATI CON I RELATIVI GRUPPI
public boolean updateGroup(String username, String group);// AGGIORNAMENTO DEL GRUPPO DELL'UTENTE PASSATO COME PARAMETRO
public List<User_model> UsersListmodel(int id_user,List<String> fields);//LISTA DI TUTTI GLI UTENTI REGISTRATI AD ECCETTO DELL'AMMINISTRATORE CHE LA RICHIAMA
public boolean deleteUser(String username); //DELETE DI UN UTENTE
public boolean deleteBook(String name); //DELETE DI UN LIBRO
public boolean insertBook(String name, int autore, File image, int anno,int numPag); //INSERIMENTO DI UN NUOVO LIBRO
public List<Author_model> listAuthor(List<String> fields); //LISTA DI TUTTI GLI AUTORI
public boolean updateBook(String name, int autore, File image, int anno,int numPag, int id); //AGGIORNAMENTO DI UN LIBRO
public boolean insertAuthor(String name, File image); //INSERIMENTO DI UN NUOVO AUTORE

/////
public List<Author_model> getAuthorBooks(int idAuthor);//LISTA DI TUTTI I LIBRI DI UN AUTORE
public List<Book_model> searchBook(); //LISTA DI TUTTI I LIBRI DEL CATALOGO
//////

//BASIC USER
public List<PageBook_model> listLastPagesStateYes(List<String> fields);//BASIC_USER LISTA DELLE ULTIME 5 PAGINE CONFERMATE
public List<Book_model> searchBasicBook(String title,List<String> fields);//BASIC_USER LISTA DELLE COPERTINE E NOMI DEL LIBRO IN BASE AL TITOLO
public List<PageBook_model> listLastTranscripts();//LISTA DELLE ULTIME 5 PAGINE CON TRASCRIZIONI CONFERMATE

//EDITOR_PAGE
public List<PageBook_model> listRevPages();// LISTA PAGINE DA REVISIONARE
public boolean responseEditorPage(String string, PageBook_model page);// ESITO REVISIONE EDITOR_PAGE

//EDITOR_TEXT
public List<PageBook_model> listRevTran();//LISTA PAGINE CON TRASCRIZIONI DA REVISIONARE
public boolean responseEditorTran(String string, Transcript_model page); //ESITO REVISIONE EDITOR_TEXT

//EXPERT_USER
public List<PageBook_model> listPages(); //LISTA DI TUTTE LE PAGINE CONFERMATE IN ATTESA DI UNA TRASCRIZIONE
public List<Book_model> listNameBook(List<String> fields);  //LISTA DI TUTTI I NOMI DEL LIBRO DEL CATALOGO
public List<Book_model> totalPageBook(Integer idBook,List<String> fields); //NUMERO PAGINE DEL LIBRO PASSATO COME PARAMETRO
public List<PageBook_model> checkExistingPage(int numPag, Integer idBook,List<String> fields);// VERIFICA SE ESISTE UNA PAGINA CONFERMATA CON LO STESSO NUMERO DI PAGINA
public boolean insertNewPage(Integer idBook, File image, int numPag,User_model user); //INSERIMENTO DI UNA NUOVA PAGINA
public Book_model getBook(int idBook);// MODEL_BOOK DEL LIBRO CON L'ID COME PARAMETRO
public boolean InsertNewTranscript(int id_User_model, int id_pagebook,String TEI, String string); //INSERIMENTO NUOVA TRASCRIZIONE

//REGISTRAZIONE
public boolean checkReg(String username, String password, String email,List<String> list);

//LOGIN
public List<User_model> checkLog(String username, char[] passwordpar);

}
