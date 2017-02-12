package model;

public class User_model {
	private int id_user;
	private String username;
	private String password;
	private String email;
	private String group;
	
	public User_model(int id_User_model, String username, String password,
			String email,String group) {
		this.id_user = id_User_model;
		this.username = username;
		this.password = password;
		this.email = email;
		this.group=group;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getId_User_model() {
		return id_user;
	}

	public void setId_User_model(int id_User_model) {
		this.id_user = id_User_model;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
