package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Controller implements EventHandler<ActionEvent>{

	Stage primaryStage;
	
	@FXML
	private Label cl;
	
	@Override
	public void handle(ActionEvent ev) {
		// TODO Auto-generated method stub
		
		Button btn = (Button) ev.getSource();
		String id = btn.getId();
		System.out.println(id);
		buttonClicked(btn);
	}
	
	public void labeltext(String color){
		cl.setStyle("-fx-background-color: "+color +";");
	}



	
	public void buttonClicked (Button b){
		System.out.println(cl.getText());
		cl.setText("aaaa");
		
		Integer value=Integer.parseInt(b.getText());
		System.out.println("ok...");
		b.setStyle("-fx-background-color: blue;");
		value++;
		b.setText(value.toString());
		//System.out.println(value);
	}
	
	
}
