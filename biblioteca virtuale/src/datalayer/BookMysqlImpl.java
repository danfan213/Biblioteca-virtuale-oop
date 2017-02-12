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

import model.Author_model;
import model.Book_model;
import model.PageBook_model;

import org.apache.commons.lang3.StringUtils;

import com.mysql.jdbc.Blob;

public class BookMysqlImpl implements Entity_manager<Book_model> {

	@Override
	public Book_model setModel_selectField(String element, Book_model temp,
			ResultSet rs) {
		try {
			switch (element) {
			case "id_book":
				temp.setId_book(rs.getInt("id_book"));
				break;
			case "name":
				temp.setName(rs.getString("name"));
				break;
			case "release_year":
				temp.setRelease_year(rs.getInt("release_year"));
				break;
			case "num_total_page":
				temp.setNum_total_page(rs.getInt("num_total_page"));
				break;
			case "image":
				temp.setImage((Blob) rs.getBlob("image"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List<Book_model> selectAll(String stm, Boolean call) {
		Book_model temp = new Book_model(null, 0, 0, null, null, null);
		Author_model author = new Author_model(0, null, null);
		List<Book_model> listBooks = new ArrayList<Book_model>();
		List<Author_model> listAuthors = new ArrayList<Author_model>();
		AuthorMysqlImpl authorsql = new AuthorMysqlImpl();
		PageBookMysqlImpl Pagebooksql = new PageBookMysqlImpl();
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT * FROM book WHERE "
					+ stm);
			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new Book_model(null, 0, 0, null, null, null);
					if (call == false) {
						String exstm = "id_author=" + rs.getInt("author_id");
						listAuthors = authorsql.selectAll(exstm, true);
						for (Author_model element : listAuthors) {
							author = new Author_model(0, null, null);
							author = element;
						}
						temp.setAuthor(author);
					}
					List<PageBook_model> listpages = new ArrayList<PageBook_model>();
					String exstm = "book_id=" + rs.getInt("id_book")+" ORDER BY num_pag ASC";
					listpages = Pagebooksql.selectAll(exstm, true);
					temp.setPagebook(listpages);
					temp.setImage(rs.getBlob("image"));
					temp.setId_book(rs.getInt("id_book"));
					temp.setName(rs.getString("name"));
					temp.setNum_total_page(rs.getInt("num_total_page"));
					temp.setRelease_year(rs.getInt("release_year"));
					listBooks.add(temp);

				}

			}

			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listBooks;
	}

	@Override
	public List<Book_model> selectField(List<String> list, String stm) {
		List<Book_model> listBooks = new ArrayList<>();
		Book_model temp = new Book_model(null, 0, 0, null, null, null);
		String query = StringUtils.join(list, ',');
		Connect con = new Connect();
		Connection connection = con.connessione();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT " + query
					+ " FROM book WHERE " + stm);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				temp = new Book_model(null, 0, 0, null, null, null);

				for (String element : list) {
					temp = setModel_selectField(element, temp, rs);

				}
				listBooks.add(temp);
			}
			con.disconnetti(connection, statement, rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listBooks;
	}

	public Boolean update(String set, String stm, File image) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			if (image != null) {
				FileInputStream fis = new FileInputStream(image);
				statement = connection.prepareStatement("update book set"
						+ " image=?," + set + " where " + stm);

				statement.setBinaryStream(1, fis, (int) image.length());
			} else {
				statement = connection.prepareStatement("update book set "
						+ set + " where " + stm);
			}
			statement.executeUpdate();
			con.disconnetti(connection, statement, rs);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean delete(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("delete from book where "
					+ stm);

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

			statement = connection.prepareStatement("insert into book (" + set
					+ ") values (?," + stm + ")");
			statement.setBinaryStream(1, fis, (int) image.length());
			statement.execute();

			con.disconnetti(connection, statement, rs);

			return true;
		} catch (SQLException | FileNotFoundException e2) {
			return false;
		}
	}
}
