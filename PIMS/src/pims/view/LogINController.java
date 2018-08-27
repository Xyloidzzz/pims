package pims.view;

import java.io.IOException;
import pims.config.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pims.Main;

public class LogINController {

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Rectangle rect;

    @FXML
    private TextField user_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button btn_signin;
    
    @FXML
    private ImageView user_error;
    
    @FXML
    private ImageView pass_error;
    
    static Stage primaryStage;
	private VBox mainLayout;
	
	private Authenticator auth;
	
	@FXML
    void login(ActionEvent event) throws IOException {
		
		String username = user_field.getText();
		String password = pass_field.getText();
		
		auth = new Authenticator();
		
		Boolean in = auth.authenticate(username, password);
		
		if(in){
			
			startMain();
			main_pane.getScene().getWindow().hide();
			
		}
		else{
			if(Authenticator.loginERR.equals("both")){
				
					user_error.setVisible(true);
					pass_error.setVisible(true);
				
			}
			else if(Authenticator.loginERR.equals("user")){
				
					user_error.setVisible(true);
					pass_error.setVisible(false);
				
			}
			else if(Authenticator.loginERR.equals("pass")){
				
					user_error.setVisible(false);
					pass_error.setVisible(true);
				
			}
		}
		
    }
	
	@FXML
	void close(ActionEvent event){
		
		System.exit(0);
		
	}
	
	private void startMain() throws IOException{
		
		LogINController.primaryStage = new Stage();
		
		LogINController.primaryStage.setTitle("PIMS"); // Setting the title of the window.
		
		//Setting Size Parameters
		LogINController.primaryStage.setMinWidth(900);
		LogINController.primaryStage.setMinHeight(650);
		
		LogINController.primaryStage.setMaximized(true); //Always fill screen when starting up.
		
		showMainView(); // Call the loader and xml file.
		
	}
    
    private void showMainView() throws IOException {
		// Load main frame window that hold all the other objects.
		
		FXMLLoader loader = new FXMLLoader(); //Initiate Loader
		loader.setLocation(Main.class.getResource("view/MainView.fxml")); // Call the FXML file
		mainLayout = loader.load(); // Load that mf
		
		// Show the scene
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}