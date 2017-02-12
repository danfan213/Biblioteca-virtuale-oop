package datalayer;
import java.sql.*;
public final class Connect {

private String user="root";
private String pass="";
private boolean attiva=false;
private Connection con;
private String url="jdbc:mysql://localhost/biblioteca?" + "user="+this.user+"&password="+this.pass;

final protected Connection connessione() {
	if (this.attiva==false){
		try{
			Class.forName( "com.mysql.jdbc.Driver" );
			this.con=  DriverManager.getConnection(this.url);
			this.attiva=true;
		}
		catch(ClassNotFoundException e){
			
			
		}
catch(SQLException e){
			
		}
}
	return this.con;

}

final protected void disconnetti(Connection con,PreparedStatement stm,ResultSet rs) throws SQLException{
	con.close();
	stm.close();
	if(rs!=null){
	rs.close();
	}
}


}