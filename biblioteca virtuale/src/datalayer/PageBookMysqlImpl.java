package datalayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;

import org.apache.commons.lang3.StringUtils;

import com.mysql.jdbc.Blob;

public class PageBookMysqlImpl implements Entity_manager<PageBook_model> {
	@Override
	public PageBook_model setModel_selectField(String element,
			PageBook_model temp, ResultSet rs) {
		try {
			switch (element) {
			case "id_pagebook":
				temp.setId_pagebook(rs.getInt("id_pagebook"));
				break;
			case "num_pag":
				temp.setNum_pag(rs.getInt("num_pag"));
				break;
			case "image":
				temp.setImage((Blob) rs.getBlob("image"));
				break;
			case "book_id":
				List<Book_model> listBooks = new ArrayList<Book_model>();

				String exstm = "id_book=" + rs.getInt("book_id");
				BookMysqlImpl booksql = new BookMysqlImpl();
				listBooks = booksql.selectAll(exstm, true);
				Book_model Book = new Book_model(null, 0, 0, null, null, null);
				for (Book_model elementBook : listBooks) {

					Book = elementBook;
				}
				temp.setBook(Book);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List<PageBook_model> selectAll(String stm, Boolean call) {
		List<PageBook_model> listPages = new ArrayList<PageBook_model>();
		;
		PageBook_model temp = new PageBook_model(0, 0, null, null, null, null);
		Book_model Book = new Book_model(null, 0, 0, null, null, null);
		User_model user=new User_model(0, null, null, null, null);
		Transcript_model Transcript = new Transcript_model(0, null, null, null);
		BookMysqlImpl booksql = new BookMysqlImpl();
		UserMysqlImpl usersql=new UserMysqlImpl();
		TranscriptMysqlImpl transcriptsql = new TranscriptMysqlImpl();
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT * FROM pagebook WHERE " + stm);

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new PageBook_model(0, 0, null, null, null, null);
					;
					if (call == false) {
						List<Book_model> listBooks = new ArrayList<Book_model>();

						String exstm = "id_book=" + rs.getInt("book_id");
						listBooks = booksql.selectAll(exstm, true);
						for (Book_model element : listBooks) {
							Book = new Book_model(null, 0, 0, null, null, null);

							Book = element;
						}
						temp.setBook(Book);
						List<User_model> listUsers = new ArrayList<User_model>();

						 exstm = "id_user=" + rs.getInt("user_id");
						listUsers = usersql.selectAll(exstm, true);
						for (User_model element : listUsers) {
							user = new User_model(0, null, null, null, null);

							user = element;
						}
						temp.setUser(user);
					} else {
						Book = new Book_model(null, 0, 0, null, null, null);
						Book.setId_book(rs.getInt("book_id"));

						temp.setBook(Book);
					}
					List<Transcript_model> listTranscripts = new ArrayList<Transcript_model>();

					String exstm = "pagebook_id=" + rs.getInt("id_pagebook")+" AND is_confirmed='yes'";
					listTranscripts = transcriptsql.selectAll(exstm, true);
					for (Transcript_model element : listTranscripts) {
						Transcript = new Transcript_model(0, null, null, null);
						;

						Transcript = element;
					}
					temp.setTranscript(Transcript);
					Transcript=new Transcript_model(0, null, null, null);
					temp.setImage(rs.getBlob("image"));
					temp.setId_pagebook(rs.getInt("id_pagebook"));
					temp.setNum_pag(rs.getInt("num_pag"));
					temp.setIs_confirmed(rs.getString("is_confirmed"));
					listPages.add(temp);

				}
			}
			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return listPages;
	}

	@Override
	public List<PageBook_model> selectField(List<String> list, String stm) {
		List<PageBook_model> listpages = new ArrayList<>();
		PageBook_model temp = new PageBook_model(0, 0, null, null, null, null);
		String query = StringUtils.join(list, ',');
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT " + query
					+ " FROM pagebook WHERE " + stm);
			rs = statement.executeQuery();
			while (rs.next()) {
				temp = new PageBook_model(0, 0, null, null, null, null);

				for (String element : list) {
					temp = setModel_selectField(element, temp, rs);

				}

				listpages.add(temp);
			}
			con.disconnetti(connection, statement, rs);

		} catch (SQLException e) {

			return null;
		}
		return listpages;
	}

	// @Override
	public Boolean update(String set, String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("update pagebook set" + set
					+ "where " + stm);
			statement.executeUpdate();
			con.disconnetti(connection, statement, rs);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean delete(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		try {

			PreparedStatement statement = connection
					.prepareStatement("delete from pagebook where " + stm);
			statement.execute();

			con.disconnetti(connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean insert(String set, String stm, File image) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		FileInputStream fis;
		try {
			fis = new FileInputStream(image);
		} catch (FileNotFoundException e1) {
			return false;
		}
		try {
			statement = connection.prepareStatement("insert into pagebook ("
					+ set + ") values (?," + stm + ")");
			statement.setBinaryStream(1, fis, (int) image.length());
			statement.execute();

			con.disconnetti(connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<PageBook_model> selectPagesNoTran(boolean call) {
		List<PageBook_model> listPages = new ArrayList<PageBook_model>();
		;
		PageBook_model temp = new PageBook_model(0, 0, null, null, null, null);
		Book_model Book = new Book_model(null, 0, 0, null, null, null);
		BookMysqlImpl booksql = new BookMysqlImpl();

		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT * FROM pagebook P LEFT JOIN transcript T ON P.id_pagebook=T.pagebook_id WHERE (T.is_confirmed='not' OR T.pagebook_id IS NULL) AND P.is_confirmed='yes' ");

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new PageBook_model(0, 0, null, null, null, null);
					;
					if (call == false) {
						List<Book_model> listBooks = new ArrayList<Book_model>();

						String exstm = "id_book=" + rs.getInt("book_id");
						listBooks = booksql.selectAll(exstm, true);
						for (Book_model element : listBooks) {
							Book = new Book_model(null, 0, 0, null, null, null);

							Book = element;
						}
						temp.setBook(Book);
					}
					temp.setImage((Blob) rs.getBlob("image"));
					temp.setId_pagebook(rs.getInt("id_pagebook"));
					temp.setNum_pag(rs.getInt("num_pag"));

					listPages.add(temp);

				}
			}
			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
		return listPages;
	}

	// SELECT PAGINE CON TRASCRIZIONI DA CONFERMARE
	public List<PageBook_model> selectWaitConfirmTran(boolean call) {
		List<PageBook_model> listPages = new ArrayList<PageBook_model>();
		;
		PageBook_model temp = new PageBook_model(0, 0, null, null, null, null);
		Book_model Book = new Book_model(null, 0, 0, null, null, null);
		Transcript_model Transcript = new Transcript_model(0, null, null, null);
		BookMysqlImpl booksql = new BookMysqlImpl();
		TranscriptMysqlImpl transcriptsql = new TranscriptMysqlImpl();
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT * FROM pagebook P LEFT JOIN transcript T ON P.id_pagebook=T.pagebook_id  WHERE T.is_confirmed='wait' ORDER BY T.date_ins ASC");

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new PageBook_model(0, 0, null, null, null, null);
					;
					if (call == false) {
						List<Book_model> listBooks = new ArrayList<Book_model>();

						String exstm = "id_book=" + rs.getInt("book_id");
						listBooks = booksql.selectAll(exstm, true);
						for (Book_model element : listBooks) {
							Book = new Book_model(null, 0, 0, null, null, null);

							Book = element;
						}
						temp.setBook(Book);

					} else {
						Book = new Book_model(null, 0, 0, null, null, null);
						Book.setId_book(rs.getInt("book_id"));

						temp.setBook(Book);
					}
					List<Transcript_model> listTranscripts = new ArrayList<Transcript_model>();

					String exstm = "pagebook_id=" + rs.getInt("id_pagebook");
					listTranscripts = transcriptsql.selectAll(exstm, true);
					for (Transcript_model element : listTranscripts) {
						Transcript = new Transcript_model(0, null, null, null);
						;

						Transcript = element;
					}
					temp.setTranscript(Transcript);
					temp.setImage(rs.getBlob("image"));
					temp.setId_pagebook(rs.getInt("id_pagebook"));
					temp.setNum_pag(rs.getInt("num_pag"));

					listPages.add(temp);

				}
			}
			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
		return listPages;
	}

	// /SELECT PAGINE CON TRASCRIZIONI
	public List<PageBook_model> selectTran(boolean call, String string) {
		List<PageBook_model> listPages = new ArrayList<PageBook_model>();
		;
		PageBook_model temp = new PageBook_model(0, 0, null, null, null, null);
		Book_model Book = new Book_model(null, 0, 0, null, null, null);
		Transcript_model Transcript = new Transcript_model(0, null, null, null);
		BookMysqlImpl booksql = new BookMysqlImpl();
		TranscriptMysqlImpl transcriptsql = new TranscriptMysqlImpl();
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT * FROM pagebook P LEFT JOIN transcript T ON P.id_pagebook=T.pagebook_id  WHERE T.is_confirmed='"
							+ string + "' LIMIT 5");

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new PageBook_model(0, 0, null, null, null, null);
					;
					if (call == false) {
						List<Book_model> listBooks = new ArrayList<Book_model>();

						String exstm = "id_book=" + rs.getInt("book_id");
						listBooks = booksql.selectAll(exstm, true);
						for (Book_model element : listBooks) {
							Book = new Book_model(null, 0, 0, null, null, null);

							Book = element;
						}
						temp.setBook(Book);

					} else {
						Book = new Book_model(null, 0, 0, null, null, null);
						Book.setId_book(rs.getInt("book_id"));

						temp.setBook(Book);
					}
					List<Transcript_model> listTranscripts = new ArrayList<Transcript_model>();

					String exstm = "pagebook_id=" + rs.getInt("id_pagebook");
					listTranscripts = transcriptsql.selectAll(exstm, true);
					for (Transcript_model element : listTranscripts) {
						Transcript = new Transcript_model(0, null, null, null);
						;

						Transcript = element;
					}
					temp.setTranscript(Transcript);
					temp.setImage(rs.getBlob("image"));
					temp.setId_pagebook(rs.getInt("id_pagebook"));
					temp.setNum_pag(rs.getInt("num_pag"));

					listPages.add(temp);

				}
			}
			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
		return listPages;
	}

}