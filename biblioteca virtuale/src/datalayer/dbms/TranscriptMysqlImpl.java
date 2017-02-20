package datalayer.dbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Transcript_model;
import model.User_model;

import org.apache.commons.lang3.StringUtils;

public class TranscriptMysqlImpl implements Entity_manager<Transcript_model> {

	@Override
	public Transcript_model setModel_selectField(String element,
			Transcript_model temp, ResultSet rs) {
		try {
			switch (element) {
			case "id_author":
				temp.setId_trancscript(rs.getInt("id_transcript"));

				break;
			case "TEI":
				temp.setTEI(rs.getString("TEI"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List<Transcript_model> selectField(List<String> list, String stm) {
		List<Transcript_model> listpages = new ArrayList<>();
		Transcript_model temp = new Transcript_model(0, null, null, null);
		String query = StringUtils.join(list, ',');
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(stm);
			rs = statement.executeQuery();
			while (rs.next()) {
				temp = new Transcript_model(0, null, null, null);

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

	@Override
	public Boolean delete(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		try {

			PreparedStatement statement = connection
					.prepareStatement(stm);
			statement.execute();

			con.disconnetti(connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Transcript_model> selectAll(String stm, Boolean call) {
		List<Transcript_model> listPages = new ArrayList<Transcript_model>();
		;
		Transcript_model temp = new Transcript_model(0, null, null, null);
		User_model page = new User_model(0, null, null, null, null);
		UserMysqlImpl usersql = new UserMysqlImpl();

		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement(stm);
			
			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new Transcript_model(0, null, null, null);
					System.out.println("2222TEIIIIIIIIIIIIIIIIIIIIII:"+rs.getString("TEI"));
					if (call == false) {
						List<User_model> listUsers = new ArrayList<User_model>();

						String exstm = "SELECT * FROM user WHERE id_user=" + rs.getInt("user_id");
						listUsers = usersql.selectAll(exstm, true);
						for (User_model element : listUsers) {
							page = new User_model(0, null, null, null, null);

							page = element;
						}
						temp.setUser(page);
					}
					
					temp.setTEI(rs.getString("TEI"));
					temp.setId_trancscript(rs.getInt("id_transcript"));

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

	public Boolean insert(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(stm);
			statement.executeUpdate();

			con.disconnetti(connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean update(String stm) {
		Connect con = new Connect();
		Connection connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(stm);
			statement.executeUpdate();
			con.disconnetti(connection, statement, rs);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
