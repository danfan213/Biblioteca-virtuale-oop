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

import org.apache.commons.lang3.StringUtils;

public class AuthorMysqlImpl implements Entity_manager<Author_model> {
	@Override
	public Author_model setModel_selectField(String element, Author_model temp,
			ResultSet rs) {
		try {
			switch (element) {
			case "id_author":
				temp.setId_author(rs.getInt("id_author"));
				break;
			case "name":
				temp.setName(rs.getString("name"));
				break;
			case "image":
				temp.setImage(rs.getBlob("image"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List<Author_model> selectAll(String stm, Boolean call) {
		List<Author_model> listAuthors = new ArrayList<Author_model>();
		Author_model temp = new Author_model(0, null, null);
		BookMysqlImpl booksql = new BookMysqlImpl();
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT * FROM author WHERE " + stm);
			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new Author_model(0, null, null);
					if (call == false) {
						List<Book_model> listBooks = new ArrayList<Book_model>();
						String exstm = "author_id=" + rs.getInt("id_author");
						listBooks = booksql.selectAll(exstm, true);
						temp.setList_book(listBooks);
					}
					temp.setImage(rs.getBlob("image"));
					temp.setId_author(rs.getInt("id_author"));
					temp.setName(rs.getString("name"));
					listAuthors.add(temp);
				}
			}
			con.disconnetti(connection, statement, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listAuthors;
	}

	@Override
	public List<Author_model> selectField(List<String> list, String stm) {
		List<Author_model> listAuthors = new ArrayList<>();
		Author_model temp = new Author_model(0, null, null);
		String query = StringUtils.join(list, ',');
		Connect con = new Connect();
		Connection connection = con.connessione();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT " + query
					+ " FROM author WHERE " + stm);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				temp = new Author_model(0, null, null);

				for (String element : list) {
					temp = setModel_selectField(element, temp, rs);

				}
				listAuthors.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listAuthors;
	}

	// @Override
	public Boolean update(String set, String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("update author set" + set
					+ "where " + stm);
			statement.executeQuery();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean delete(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("delete from author where "
					+ stm);

			statement.execute();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

			statement = connection.prepareStatement("insert into author ("
					+ set + ") values (?," + stm + ")");
			statement.setBinaryStream(1, fis, (int) image.length());
			statement.execute();

			con.disconnetti(connection, statement, rs);

			return true;
		} catch (SQLException | FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();

			return false;
		}
	}

}
