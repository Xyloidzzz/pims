package pims.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB extends DBConnection{

	//Values for Inventory Table
	int id;
	String item_name;
	String description;
	String type;
	String brand;
	int quantity;
	
	//Values for User Info
	int userID;
	String userName;
	String password;
	String fullName;
	String department;
	
	//DB related objects and specific query's
	private DB dc;
	private String queryGET = "SELECT * FROM inv WHERE idInv = '";
	private String queryGETUSER = "SELECT * FROM users WHERE idUsers = '";
	private String queryDELETE = "DELETE FROM inv WHERE idInv = '";
	
	//Get Function for a whole row. Will override current row for current obj so that it can be called multiple times. 
	public void get(int rowID){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement(queryGET + rowID + "'");
                ResultSet rs = pst.executeQuery()) {
			
			rs.next();
			
			this.id = rs.getInt("idInv");
			this.item_name = rs.getString("item_name");
			this.description = rs.getString("description");
			this.type = rs.getString("type");
			this.brand = rs.getString("brand");
			this.quantity = rs.getInt("quantity");
			
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
	}
	
	//Get Function for User version
	public void getUserInfo(int rowID){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement(queryGETUSER + rowID + "'");
                ResultSet rs = pst.executeQuery()) {
			
			rs.next();
			
			this.userID = rs.getInt("idUsers");
			this.userName = rs.getString("userName");
			this.password = rs.getString("password");
			this.fullName = rs.getString("full_name");
			this.department = rs.getString("department");
			
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
	}
	
	//Update Function based on given values.
	public void update(int oldID, int newID, String item, String desc, String typ, String brnd, String qty){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement("UPDATE inv SET idInv = '" + newID + "', " + "item_name = '" + item + "', " 
						+ "description = '" + desc + "', " + "type = '" + typ + "', " + "brand = '" + brnd + "', " + "quantity = '" + qty + "' " 
						+ "WHERE idInv = '" + oldID + "'")) 
		{
			
			pst.executeUpdate();
			
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
	}
	
	//Insert new row based on given values.
	public void insert(int rowID, String item, String desc, String typ, String brnd, int qty){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement("INSERT INTO inv (idInv, item_name, description, type, brand, quantity) VALUES ('" 
		+ rowID + "', '" + item + "', '" + desc + "', '" + typ + "', '" + brnd + "', '" + qty + "')"))
		{
			
			pst.executeUpdate();
			
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
	}
	
	//Delete Whole mf row
	public void deleteROW(int rowID){
		
		dc = new DB();
		
		try(Connection conn = dc.connect();
				PreparedStatement pst = conn.prepareStatement(queryDELETE + rowID + "'")) 
		{
			
			pst.executeUpdate();
			
		} catch (SQLException ex) {
			
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
		}
		
	}
	
	public Boolean checkInt(String s){
		
		try {
			Integer.parseInt(s);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	//We only need getters because out setter is the update() function.
	public int getUserID(){
		return this.userID;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getPass(){
		return this.password;
	}

	public String getFullName(){
		return this.fullName;
	}
	
	public String getDept(){
		return this.department;
	}
	
	//Getters for Inventory Table
	public int getItemID(){
		return this.id;
	}
	
	public String getItemName(){
		return this.item_name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getBrand(){
		return this.brand;
	}
	
	public int getQty(){
		return this.quantity;
	}
	
}
