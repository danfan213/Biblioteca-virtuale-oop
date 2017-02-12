package model; 

public class Transcript_model {
	private int id_trancscript;
	private String TEI;
	private String text;
	private User_model user;
	
	
	public Transcript_model(int id_trancscript, String tEI,
			 String text,User_model user) {
		super();
		this.id_trancscript = id_trancscript;
		TEI = tEI;
		this.text = text;
		this.user=user;
	}
	
	public void setId_trancscript(int id_trancscript) {
		this.id_trancscript = id_trancscript;
	}

	public void setTEI(String tEI) {
		TEI = tEI;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(User_model user) {
		this.user = user;
	}

	

	public int getId_trancscript() {
		return id_trancscript;
	}

	public String getTEI() {
		return TEI;
	}



	public String getText() {
		return text;
	}

	public User_model getUser() {
		return user;
	}
	
	
	
	
	
}
