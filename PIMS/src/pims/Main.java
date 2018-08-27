package pims;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	// Setting up stage and top level container.
	private Stage primaryStage;
	private AnchorPane root;
	
	private double xOffset = 0; 
	private double yOffset = 0;
	
	public void start(Stage primaryStage) throws IOException{
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("PIMS"); // Setting the title of the window.
		
		//Remove Windows Decorations for future thing.
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		
		//Setting Size Parameters
		this.primaryStage.setMinWidth(900);
		this.primaryStage.setMinHeight(450);
		
		this.primaryStage.setMaxWidth(900);
		this.primaryStage.setMaxHeight(450);
		
		this.primaryStage.setResizable(false);
		
		showMainView(); // Call the loader and xml file.
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
		
	}
	
	private void showMainView() throws IOException {
		// Load main frame window that hold all the other objects.
		
		FXMLLoader loader = new FXMLLoader(); //Initiate Loader
		loader.setLocation(Main.class.getResource("view/LoginSplash.fxml")); // Call the FXML file
		root = loader.load(); // Load that mf
		
		// Show the scene
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		//Initialize GUI and Objects

		launch(args); // Launch this puppy.
		
	}

}
