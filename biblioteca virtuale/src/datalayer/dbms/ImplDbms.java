package datalayer.dbms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import model.Author_model;
import model.Book_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;
import datalayer.DatabaseInterface;

public class ImplDbms implements DatabaseInterface {
	private AuthorMysqlImpl authorMysql;
	private BookMysqlImpl  bookMysql;
	private PageBookMysqlImpl pageBookMysql;
	private TranscriptMysqlImpl transcriptMysql;
	private UserMysqlImpl userMysql;


	@Override
	public List<Book_model> searchBook(String title) {
		List<Book_model>list=new ArrayList<>();
		this.bookMysql=new BookMysqlImpl();
		String stm="SELECT * FROM book WHERE name LIKE '%"+title+"%'";
		list=this.bookMysql.selectAll(stm, false);
		return list;
	}

	@Override
	public List<PageBook_model> listPageBookStateNot() {
		List<PageBook_model> list = new ArrayList<>();
		this.pageBookMysql = new PageBookMysqlImpl();
		String stm = " SELECT * FROM pagebook WHERE  is_confirmed='not' ORDER BY date_ins DESC LIMIT 5 ";
		list = this.pageBookMysql.selectAll(stm, false);
		return list;		
	}

	@Override
	public List<User_model> UsersGroups(List<String> fields) {
		this.userMysql = new UserMysqlImpl();
		List<User_model> listUsers = new ArrayList<>();
		String query = StringUtils.join(fields, ',');

		listUsers = this.userMysql
				.selectField(fields,
						"SELECT " + query
					+ " FROM user WHERE  group_id!=(SELECT id_group from `group` where name='admin_user')");
		return listUsers;
	}

	@Override
	public boolean updateGroup(String username, String group) {
		Boolean resp;
		this.userMysql = new UserMysqlImpl();
		int id = this.userMysql.getGroupId(group);
		String stm="update user set group_id=" + id
				+  " where username='" + username + "'";
		
		resp = this.userMysql.update(stm);
		return resp;
	}

	@Override
	public List<User_model> UsersListmodel(int id_user,List<String> fields) {
		List<User_model> list = new ArrayList<>();
		this.userMysql = new UserMysqlImpl();
		String query = StringUtils.join(fields, ',');

		list = this.userMysql.selectField(fields, "SELECT " + query
					+ " FROM user WHERE id_user!="
				+ id_user + " ORDER BY username ASC");

		return list;
	}

	@Override
	public boolean deleteUser(String username) {
		boolean ris = false;
		this.userMysql = new UserMysqlImpl();
		String stm = "delete from user where username='" + username + "'";
		ris = this.userMysql.delete(stm);
		return ris;
	}

	@Override
	public boolean deleteBook(String name) {
		boolean ris = false;
		this.bookMysql = new BookMysqlImpl();
		String stm = "delete from book where name='" + name + "'";
		ris = this.bookMysql.delete(stm);

		return ris;
	}

	@Override
	public boolean insertBook(String name, int autore, File image, int anno,
			int numPag) {
		boolean ris;
		String stm="insert into book (image,name,author_id,num_total_page,release_year) values (?,'" + name + "'," + autore + "," + numPag + "," + anno + ")";
		
		this.bookMysql = new BookMysqlImpl();
		ris = this.bookMysql.insert( stm, image);

		return ris;
	}

	@Override
	public List<Author_model> listAuthor(List<String> fields) {
		List<Author_model> list = new ArrayList<>();
		this.authorMysql = new AuthorMysqlImpl();
		String query = StringUtils.join(fields, ',');

		list = this.authorMysql.selectField(fields, "SELECT " + query
					+ " FROM author WHERE 1");
		return list;
	}

	@Override
	public boolean updateBook(String name, int autore, File image, int anno,
			int numPag, int id) {
		boolean ris;
		String stm="update book set"
				+ " image=?,name='" + name + "',author_id=" + autore
				+ ",release_year=" + anno + ",num_total_page=" + numPag+" where id_book=" + id;
		
		this.bookMysql = new BookMysqlImpl();
		ris = this.bookMysql.update(stm, image);

		return ris;
	}

	@Override
	public boolean insertAuthor(String name, File image) {
		boolean ris;
		String stm="insert into author (image,name) values (?,'" + name + "')";
		
		this.authorMysql = new AuthorMysqlImpl();
		ris = this.authorMysql.insert(stm, image);

		return ris;
	}

	@Override
	public List<Author_model> getAuthorBooks(int idAuthor) {
		List<Author_model> list = new ArrayList<>();

		this.authorMysql = new AuthorMysqlImpl();
		list = this.authorMysql.selectAll("SELECT * FROM author WHERE id_author=" + idAuthor, false);
		return list;
	}

	@Override
	public List<Book_model> searchBook() {
		List<Book_model> list = new ArrayList<>();
		this.bookMysql = new BookMysqlImpl();
		String stm = "SELECT * FROM book WHERE 1";

		list = this.bookMysql.selectAll(stm, false);
		return list;
	}

	@Override
	public List<PageBook_model> listLastPagesStateYes(List<String> fields) {
		List<PageBook_model> list = new ArrayList<>();
		String query = StringUtils.join(fields, ',');
		this.pageBookMysql = new PageBookMysqlImpl();
		list = this.pageBookMysql.selectField(fields,
				"SELECT " + query
					+ " FROM pagebook WHERE is_confirmed='yes' ORDER BY date_ins DESC LIMIT 5");
		return list;
	}

	@Override
	public List<Book_model> searchBasicBook(String title,List<String> fields) {
		List<Book_model> list = new ArrayList<>();
		String query = StringUtils.join(fields, ',');
		this.bookMysql = new BookMysqlImpl();
		String stm = "SELECT " + query
					+ " FROM book WHERE name LIKE '%" + title + "%'";
		list = this.bookMysql.selectField(fields, stm);
		return list;
	}

	@Override
	public List<PageBook_model> listLastTranscripts() {
		List<PageBook_model> list = new ArrayList<>();

		this.pageBookMysql = new PageBookMysqlImpl();
		list = this.pageBookMysql.selectTran(false, "yes");
		return list;
	}

	@Override
	public List<PageBook_model> listRevPages() {
		List<PageBook_model> list = new ArrayList<>();
		this.pageBookMysql = new PageBookMysqlImpl();
		list = this.pageBookMysql.selectAll("SELECT * FROM pagebook WHERE is_confirmed='wait' ORDER BY date_ins DESC", false);
		return list;
	}

	@Override
	public boolean responseEditorPage(String string, PageBook_model page) {
		boolean ris;
		this.pageBookMysql = new PageBookMysqlImpl();
		ris = this.pageBookMysql.update("update pagebook set is_confirmed='" + string + "'"+
				"where id_pageBook=" + page.getId_pagebook());
		return ris;
	}

	@Override
	public List<PageBook_model> listRevTran() {
		List<PageBook_model>list=new ArrayList<>();
		this.pageBookMysql=new PageBookMysqlImpl();
		list=this.pageBookMysql.selectWaitConfirmTran(false);
		return list;
	}

	@Override
	public boolean responseEditorTran(String string, Transcript_model page) {
		boolean ris;
		this.transcriptMysql=new TranscriptMysqlImpl(); 
		ris=this.transcriptMysql.update("update transcript set is_confirmed='"+string+"'"+ "where  id_transcript="+page.getId_trancscript());
		
		return ris;
	}

	@Override
	public List<PageBook_model> listPages() {
		List <PageBook_model> list=new ArrayList<>();
		this.pageBookMysql=new PageBookMysqlImpl();
		
		list=this.pageBookMysql.selectPagesNoTran(false);
		return list;
	}

	@Override
	public List<Book_model> listNameBook(List<String> fields) {
		List<Book_model> list=new ArrayList<>();
		String query = StringUtils.join(fields, ',');
		this.bookMysql=new BookMysqlImpl();
		list=this.bookMysql.selectField(fields,"SELECT " + query
					+ " FROM book WHERE 1");
		return list;
	}

	@Override
	public List<Book_model> totalPageBook(Integer idBook,List<String> fields) {
		List<Book_model> list=new ArrayList<>();
		this.bookMysql=new BookMysqlImpl();
		String query = StringUtils.join(fields, ',');
		list=this.bookMysql.selectField(fields,"SELECT " + query
					+ " FROM book WHERE id_book="+idBook);
		return list;
	}

	@Override
	public List<PageBook_model> checkExistingPage(int numPag, Integer idBook,List<String> fields) {
		List<PageBook_model> list=new ArrayList<>();
		String query = StringUtils.join(fields, ',');
		this.pageBookMysql=new PageBookMysqlImpl();
		list=this.pageBookMysql.selectField(fields,"SELECT " + query
					+ " FROM pagebook WHERE book_id="+idBook+" AND num_pag="+numPag+" AND is_confirmed='yes'");
		return list;
	}

	@Override
	public boolean insertNewPage(Integer idBook, File image, int numPag,
			User_model user) {
		boolean ris;
		String stm="insert into pagebook (image,book_id,num_pag,is_confirmed,user_id) values (?," + idBook+","+numPag+",'wait',"+user.getId_User_model() + ")";
		this.pageBookMysql=new PageBookMysqlImpl();
		ris=this.pageBookMysql.insert(stm,image);
		
		return ris;
	}

	@Override
	public Book_model getBook(int idBook) {
		this.bookMysql=new BookMysqlImpl();
		List<Book_model> list=new ArrayList<>();
		Book_model book=new Book_model(null, 0, 0, null, null, null);
		list=this.bookMysql.selectAll("SELECT * FROM book WHERE id_book="+idBook, false);
		for(Book_model element :list){
			book=element;
		}
		return book;
	}

	@Override
	public boolean InsertNewTranscript(int id_User_model, int id_pagebook,
			String TEI, String string) {
		this.transcriptMysql=new TranscriptMysqlImpl();
		String set="TEI,pagebook_id,user_id,is_confirmed";
		String stm="insert into transcript (TEI,pagebook_id,user_id,is_confirmed) values ('"+TEI+"',"+id_pagebook+","+id_User_model+",'"+string+"')";
		Boolean ris=this.transcriptMysql.insert(stm);
		return ris;
	}

	@Override
	public boolean checkReg(String username, String password, String email,List<String> list) {
		this.userMysql  = new UserMysqlImpl();
		List<User_model> listModel = new ArrayList<>();
		String query = StringUtils.join(list, ',');

		String stm = "SELECT " + query
					+ " FROM user WHERE username='" + username + "'";

		listModel = this.userMysql.selectField(list, stm);

		if (!listModel.isEmpty()) {

			return false;
		}
		
		boolean test = this.userMysql.insert("insert into user (username,password,email,group_id) values ('"
				+ username + "','" + password + "','" + email + "',2 )");
		return test;
	}
	
	@Override
	public List<User_model> checkLog(String username, char[] passwordpar){
		
		this.userMysql = new UserMysqlImpl();
		List<User_model> listModel = new ArrayList<>();
		String password=String.valueOf(passwordpar);
		String stm = "SELECT * FROM user WHERE username='" + username + "' and password='" + password
				+ "'";
		listModel = this.userMysql.selectAll(stm, true);
		if (listModel.isEmpty()) {

			return listModel = new ArrayList<>();
		} else
			return listModel;
	}
	}


