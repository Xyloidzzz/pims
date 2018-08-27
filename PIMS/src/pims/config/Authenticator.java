package pims.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authenticator {

	private DB dc;
	private String query = "SELECT * FROM users";
	
	//User ID for current user logged in.
	public static int CurrentUserID;
	
	
	/*
	 * Types of Login Errors Possible
	 * 
	 *  null = no error
	 * 
	 * "pass" = Wrong Password
	 * 
	 * "user" = Wrong Username
	 * 
	 * "both" = Wrong Everything
	 * 
	 */
	public static String loginERR;
	private String consoleError;
	
	public final Boolean authenticate(String user, String pass){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {
			
			String dbUser;
			String dbPass;
			
			while(rs.next()){
				
				dbUser = rs.getString("username");
				dbPass = rs.getString("password");
				CurrentUserID = rs.getInt("idUsers");
				
				if (user.equals(dbUser) && pass.equals(dbPass)){
					
					Authenticator.loginERR = null;
					
					return true;
				}
				else if(user.equals(dbUser) && !pass.equals(dbPass)){
					
					consoleError = "Wrong Password";
					
					Authenticator.loginERR = "pass";
					
				}
				
			}
			
					
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
		System.out.println(consoleError);
		return false;
		
	}
	
}
