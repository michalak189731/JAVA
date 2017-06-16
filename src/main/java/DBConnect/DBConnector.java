package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnector {
	
	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DBConnector(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			System.out.println("Conn Sucessful");
			st=con.createStatement();
		}catch(Exception e){
			System.out.println("Error "+e);
		}
	}
	
	public void getData(){
		try{
			String query = "SELECT * FROM `persons`";
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				System.out.println(" Name "+name+" Surname "+surname);
			}
		}catch(Exception e){
			
		}
	}
	
}
