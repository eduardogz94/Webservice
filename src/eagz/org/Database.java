package eagz.org;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eagz.org.utilities.PropertiesReader;
import eagz.org.utilities.SingleDB;


public class Database {
	public ResultSet rs;
	private PreparedStatement pstmt;
	private Connection con;
	private PropertiesReader prop = PropertiesReader.getInstance();
	
	public Database(){
		try {
			SingleDB db = SingleDB.getInstance();
			Class.forName(db.getDriver());
			this.con= DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}

	public boolean checkUser(String email, String password) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkuser"));
			this.pstmt.setString(1, email);
			this.pstmt.setString(2,password);
			this.rs = pstmt.executeQuery();
			state = this.rs.next();
			} catch (Exception e) {
				e.getStackTrace();
			}
		return state;
	}
	
	public boolean checkAdmin(String email) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_admin"));
			this.pstmt.setString(1,email);
			this.rs = this.pstmt.executeQuery();
			rs.next();
			if(this.rs.getString("type_id").equals("2")) {
				state = true;
			System.out.println("is admin? "+ state);
			}
			
		} catch(Exception e) {
			e.getStackTrace();
		}
		return state;
	}

	public int userId(String email) {
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_getId"));
		    this.pstmt.setString(1, email);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("id_user");
				return id;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}
	
	
	public void closeCon() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}