package pims.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pims.Main;
import pims.config.Authenticator;
import pims.config.DB;
import pims.config.DBConnection;

public class ViewController {
	
	  	@FXML
	    private VBox main_pane;

	    @FXML
	    private Pane sidenav;

	    @FXML
	    private Button dashbutton;

	    @FXML
	    private Button invbutton;

	    @FXML
	    private AnchorPane inv;

	    @FXML
	    private TableView<InvTable> inv_table;

	    @FXML
	    private TableColumn<InvTable, Integer> col_id;

	    @FXML
	    private TableColumn<InvTable, String> col_item;

	    @FXML
	    private TableColumn<InvTable, String> col_desc;

	    @FXML
	    private TableColumn<InvTable, String> col_type;

	    @FXML
	    private TableColumn<InvTable, String> col_brnd;

	    @FXML
	    private TableColumn<InvTable, Integer> col_qty;
	    
	    static ObservableList<InvTable> oblist = FXCollections.observableArrayList();

	    @FXML
	    private Button add_btn;

	    @FXML
	    private Button edit_btn;

	    @FXML
	    private Button delete_btn;

	    @FXML
	    private AnchorPane dash;

	    @FXML
	    private Label user_name;

	    @FXML
	    private Label user_dept;
    
 // Setting up stage and top level container.
 	private Stage primaryStage;
 	private AnchorPane mainLayout;
 	
 	private double xOffset = 0; 
 	private double yOffset = 0;
    
 	//For Dashboard Info
	private DB dc;
	
	@FXML
    public void initialize() {
        
		//Set Up Dashboard
		dc = new DB();
		dc.getUserInfo(Authenticator.CurrentUserID);
		
		user_name.setText(dc.getFullName());
		user_dept.setText(dc.getDept());
		
		startTable();
		
    }
	
	@FXML
    void logout(ActionEvent event) throws IOException {

		startLogin();
		main_pane.getScene().getWindow().hide();
		oblist.clear();
		
    }

    @FXML
    void getDash(ActionEvent event) {
    	
    	inv.setVisible(false);
    	dash.setVisible(true);
    	
    }
    
    @FXML
    void getInv(ActionEvent event) {
    	
    	dash.setVisible(false);
    	inv.setVisible(true);
    	
    }
    
    void refreshTable() {
    	
    	oblist.clear();
    	startTable();
    	
    }
    
    @FXML
    void addItem(ActionEvent event) throws IOException {
    	
    	GaussianBlur blur = new GaussianBlur();
    	
    	main_pane.setEffect(blur);
    	
    	startAdd();
    	
    	main_pane.setEffect(null);
    	
    	refreshTable();
    	
    }
    
    @FXML
    void editItem(ActionEvent event) throws IOException {
    	
    	InvTable model = inv_table.getSelectionModel().getSelectedItem();
    	
    	if(model == null) {
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "YOU MUST SELECT AN ITEM ON THE TABLE!", ButtonType.OK);
        	alert.setTitle("S T O P | R I G H T | T H E R E!");
    		alert.showAndWait();
    	}
    	else{
	    	EditController.itemID = model.getId();
	    	
	    	GaussianBlur blur = new GaussianBlur();
	    	
	    	main_pane.setEffect(blur);
	    	
	    	startEdit();
	    	
	    	main_pane.setEffect(null);
	    	
	    	refreshTable();
    	}
    	
    }
    
    @FXML
    void deleteItem(ActionEvent event) {
    	
    	DB func = new DB();
    	InvTable model = inv_table.getSelectionModel().getSelectedItem();
    	
    	if(model == null) {
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "YOU MUST SELECT AN ITEM ON THE TABLE!", ButtonType.OK);
        	alert.setTitle("S T O P | R I G H T | T H E R E!");
    		alert.showAndWait();
    	}
    	else {
	    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.CANCEL);
	    	alert.setTitle("Delete Item?");
	    	alert.showAndWait();
	    	
	    	if (alert.getResult() == ButtonType.YES) {
	        	func.deleteROW(model.getId());
	        	
	        	refreshTable();
	    	}
	    	
    	}
    	
    }
    
    private void startTable(){
    	
    	dc = new DB();
    	
    	//Set Up Table
    		try(Connection conn = dc.connect();
    				PreparedStatement pst = conn.prepareStatement("SELECT * FROM inv");
    	            ResultSet rs = pst.executeQuery()) {
    				
    			while(rs.next()){
    					
    				oblist.add(new InvTable(rs.getInt("idInv"), rs.getString("item_name"), rs.getString("description"), rs.getString("type"),
    						 rs.getString("brand"), rs.getInt("quantity")));
    					
    			}
    				
    		} catch (SQLException ex) {
    				
    			Logger lgr = Logger.getLogger(DBConnection.class.getName());
    	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
    	            
    		}
    			
    		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    		col_item.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    		col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
    		col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
    		col_brnd.setCellValueFactory(new PropertyValueFactory<>("brand"));
    		col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    			
    		inv_table.setItems(oblist);
    	
    }
    
    private void startLogin() throws IOException{
		
		this.primaryStage = new Stage();
		
		this.primaryStage.setTitle("PIMS"); // Setting the title of the window.
		
		//Remove Windows Decorations for future thing.
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		
		//Setting Size Parameters
		this.primaryStage.setMinWidth(900);
		this.primaryStage.setMinHeight(450);
				
		this.primaryStage.setMaxWidth(900);
		this.primaryStage.setMaxHeight(450);
		
		this.primaryStage.setResizable(false);
		
		showLogInView(); // Call the loader and xml file.
		
		
	}
    
    private void showLogInView() throws IOException {
		// Load main frame window that hold all the other objects.
		
		FXMLLoader loader = new FXMLLoader(); //Initiate Loader
		loader.setLocation(Main.class.getResource("view/LoginSplash.fxml")); // Call the FXML file
		mainLayout = loader.load(); // Load that mf
		
		// Show the scene
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		
		mainLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		primaryStage.show();
		
	}
    
    private void startEdit() throws IOException{
		
		this.primaryStage = new Stage();
		
		//Remove Windows Decorations for future thing.
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		
		//Set the Parent stage for windows to recognize modality.
		this.primaryStage.initOwner(LogINController.primaryStage);
		
		// Specifies the modality for new window.
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
		
		//Setting Size Parameters
		this.primaryStage.setMinWidth(400);
		this.primaryStage.setMinHeight(600);
		
		this.primaryStage.setMaxWidth(400);
		this.primaryStage.setMaxHeight(600);
		
		this.primaryStage.setResizable(false);
		
		showEditView(); // Call the loader and xml file.
		
	}

    private void showEditView() throws IOException {
		// Load main frame window that hold all the other objects.
		
		FXMLLoader loader = new FXMLLoader(); //Initiate Loader
		loader.setLocation(Main.class.getResource("view/EditScreen.fxml")); // Call the FXML file
		mainLayout = loader.load(); // Load that mf
		
		// Show the scene
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		
		mainLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		primaryStage.showAndWait();
		
	}
    
    private void startAdd() throws IOException{
		
		this.primaryStage = new Stage();
		
		//Remove Windows Decorations for future thing.
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		
		//Set the Parent stage for windows to recognize modality.
		this.primaryStage.initOwner(LogINController.primaryStage);
		
		// Specifies the modality for new window.
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
		
		//Setting Size Parameters
		this.primaryStage.setMinWidth(400);
		this.primaryStage.setMinHeight(600);
		
		this.primaryStage.setMaxWidth(400);
		this.primaryStage.setMaxHeight(600);
		
		this.primaryStage.setResizable(false);
		
		showAddView(); // Call the loader and xml file.
		
	}
    
    private void showAddView() throws IOException {
		// Load main frame window that hold all the other objects.
		
		FXMLLoader loader = new FXMLLoader(); //Initiate Loader
		loader.setLocation(Main.class.getResource("view/AddItemScreen.fxml")); // Call the FXML file
		mainLayout = loader.load(); // Load that mf
		
		// Show the scene
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		
		mainLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		primaryStage.showAndWait();
		
	}
    

}