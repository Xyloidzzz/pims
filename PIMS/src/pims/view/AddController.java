package pims.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import pims.config.DB;

public class AddController {

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button main_btn;

    @FXML
    private Button cancel;

    @FXML
    private TextField id_field;

    @FXML
    private TextField item_field;

    @FXML
    private TextField description_field;

    @FXML
    private TextField type_field;

    @FXML
    private TextField brand_field;

    @FXML
    private TextField qty_field;

    @FXML
    void addItem(ActionEvent event) {
    	
    	DB itemAdder = new DB();
    	
    	if(id_field.getText() == null || id_field.getText().trim().isEmpty()) {
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "ID FIELD CANNOT BE BLANK", ButtonType.OK);
        	alert.setTitle("W O A H");
    		alert.showAndWait();
    	}
    	else if(!itemAdder.checkInt(id_field.getText()) || !itemAdder.checkInt(qty_field.getText())){
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "ID AND/OR QUANTITY MUST BE INTEGERS", ButtonType.OK);
        	alert.setTitle("W O A H T H E R E C O W B O Y !");
    		alert.showAndWait();
    	}
    	else{
	    	itemAdder.insert(Integer.parseInt(id_field.getText()), item_field.getText(), description_field.getText(), type_field.getText(), brand_field.getText(), Integer.parseInt(qty_field.getText()));
	    	
	    	System.out.println("New Item Added!");
	    	
	    	main_pane.getScene().getWindow().hide();
    	}
    	
    }

    @FXML
    void cancel(ActionEvent event) {
    	
    	main_pane.getScene().getWindow().hide();
    	
    }
    
    @FXML
	void close(ActionEvent event){
		
    	main_pane.getScene().getWindow().hide();
		
	}
    
}
