package datalayer.dbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import model.User_model;

public class UserMysqlImpl implements Entity_manager<User_model> {
	private Connection connection;

	@Override
	public User_model setModel_selectField(String element, User_model temp,
			ResultSet rs) {
		try {
			switch (element) {
			case "id_user":
				temp.setId_User_model(rs.getInt("id_user"));
				break;
			case "username":
				temp.setUsername(rs.getString("username"));
				break;
			case "password":
				temp.setPassword(rs.getString("password"));
				break;
			case "email":
				temp.setEmail(rs.getString("email"));
				break;
			case "group_id":
				int idGroup = rs.getInt("group_id");
				PreparedStatement statement = this.connection
						.prepareStatement("SELECT name FROM `group` WHERE id_group="
								+ idGroup);
				ResultSet gr = statement.executeQuery();
				if (gr != null) {
					while (gr.next()) {
						temp.setGroup(gr.getString("name"));
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return temp;
	}

	@Override
	public List<User_model> selectAll(String stm, Boolean call) {
		List<User_model> listUsers = new ArrayList<User_model>();
		;
		User_model temp = new User_model(0, null, null, null, null);

		Connect con = new Connect();
		this.connection = con.connessione();
		PreparedStatement statement;
		ResultSet rs = null;

		try {
			statement = this.connection
					.prepareStatement(stm);

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					temp = new User_model(0, null, null, null, null);
					int idGroup = rs.getInt("group_id");
					PreparedStatement statement2 = this.connection
							.prepareStatement("SELECT name FROM `group` WHERE id_group="
									+ idGroup);
					ResultSet gr = statement2.executeQuery();
					if (gr != null) {
						while (gr.next()) {
							temp.setGroup(gr.getString("name"));
						}

					}
					temp.setEmail(rs.getString("email"));

					temp.setUsername(rs.getString("username"));
					temp.setId_User_model(rs.getInt("id_user"));
					temp.setPassword(rs.getString("password"));
					listUsers.add(temp);
				}
			}

			con.disconnetti(this.connection, statement, rs);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return listUsers;
	}

	@Override
	public List<User_model> selectField(List<String> list, String stm) {
		List<User_model> listUsers = new ArrayList<>();
		User_model temp = new User_model(0, null, null, null, null);
		String query = StringUtils.join(list, ',');
		Connect con = new Connect();
		this.connection = con.connessione();
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(stm);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				temp = new User_model(0, null, null, null, null);

				for (String element : list) {
					temp = setModel_selectField(element, temp, rs);

				}
				listUsers.add(temp);
			}
			con.disconnetti(this.connection, statement, rs);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return listUsers;
	}

	public Boolean update(String stm) {
		Connect con = new Connect();
		this.connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(stm);

			statement.executeUpdate();
			con.disconnetti(this.connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int getGroupId(String group) {
		int id = 0;
		Connect con = new Connect();
		this.connection = con.connessione();
		PreparedStatement statement;
		ResultSet rs = null;

		try {

			statement = this.connection
					.prepareStatement("SELECT id_group FROM `group` WHERE name='"
							+ group + "'");

			rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					id = rs.getInt("id_group");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return id;
	}

	@Override
	public Boolean delete(String stm) {
		Connect con = new Connect();
		this.connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = this.connection
					.prepareStatement(stm);

			statement.execute();

			con.disconnetti(this.connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean insert( String stm) {
		Connect con = new Connect();
		this.connection = con.connessione();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(stm);
			statement.executeUpdate();

			con.disconnetti(this.connection, statement, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
