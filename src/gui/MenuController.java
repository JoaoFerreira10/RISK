package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
		
	
	@FXML
	public void goExit(ActionEvent av){
		Stage stage= (Stage) ( (Node) av.getSource()).getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void goToBoard(ActionEvent av) throws IOException{
		
	//	Parent tabuleiro= FXMLLoader.load(getClass().getResource("Tabuleiro.fxml"));
		Parent tabuleiro= FXMLLoader.load(getClass().getResource("Dados.fxml"));
		Scene tabuleiro_scene= new Scene(tabuleiro);
		Stage app_stage= (Stage) ( (Node) av.getSource()).getScene().getWindow();
		app_stage.setResizable(false);
		app_stage.setTitle("RISK");
		app_stage.setScene(tabuleiro_scene);
		app_stage.show();
		
	}
	
}
