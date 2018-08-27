package pims.config;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class DBConnection {
	
	public Connection connection;
	
	private Config cfg;
	
	public Connection connect(){
		
		cfg = new Config();
		
		String dbName = cfg.getProperty("dbName");
		String username = cfg.getProperty("dbUsername");
		String password = cfg.getProperty("dbPassword");
		String dbHost = cfg.getProperty("dbHost");
		
		try{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName + "?useSSL=false", username, password);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "Sorry, we're having trouble connecting to your database! Try again!", ButtonType.OK);
        	alert.setTitle(": O");
    		alert.showAndWait();
		}
		
		return connection;
		
	}
	
	/*
	 * Connection Template
	 * 
	 */ 
//		dc = new DB();
//		
//		try(Connection conn = dc.connect();
//				PreparedStatement pst = conn.prepareStatement(query);
//                ResultSet rs = pst.executeQuery()) {
//			
//			
//			
//		} catch (SQLException ex) {
//			
//			Logger lgr = Logger.getLogger(DBConnection.class.getName());
//            lgr.log(Level.SEVERE, ex.getMessage(), ex);
//            
//		}
}
