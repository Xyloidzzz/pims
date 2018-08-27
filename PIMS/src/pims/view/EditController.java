package pims.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import pims.config.DB;

public class EditController {

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Label identifier;

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
    
    static int itemID;
    
    @FXML
    public void initialize() {
    	
    	//Initialize DataBase Obj
    	DB dc = new DB();
    	dc.get(itemID);
    	
    	//Change Header Label
    	identifier.setText(Integer.toString(itemID));
    	
    	//Assign all of the text fields.
    	id_field.setText(Integer.toString(dc.getItemID()));
    	item_field.setText(dc.getItemName());
    	description_field.setText(dc.getDescription());
    	type_field.setText(dc.getType());
    	brand_field.setText(dc.getBrand());
    	qty_field.setText(Integer.toString(dc.getQty()));
    	
    }

    @FXML
    void save(ActionEvent event) {

    	//Initialize the DataBase OBJ
    	DB dc = new DB();
    	
    	if(id_field.getText() == null || id_field.getText().trim().isEmpty()) {
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "ID FIELD CANNOT BE BLANK", ButtonType.OK);
        	alert.setTitle("W O A H");
    		alert.showAndWait();
    	}
    	else if(!dc.checkInt(id_field.getText()) || !dc.checkInt(qty_field.getText())){
    		//Alert The User if there is no ID as it is crucial for other functions.
        	Alert alert = new Alert(AlertType.ERROR, "ID AND/OR QUANTITY MUST BE INTEGERS", ButtonType.OK);
        	alert.setTitle("W O A H T H E R E C O W B O Y !");
    		alert.showAndWait();
    	}
    	else {
    		//Take info from the textfields and Update() the database with it.
    		dc.update(itemID, Integer.parseInt(id_field.getText()), item_field.getText(), description_field.getText(), type_field.getText(),
    				brand_field.getText(), qty_field.getText());
    		
    		//Exit Window
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
