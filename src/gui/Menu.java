package gui;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import agentes.AgenteAleatorio;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {
	
	static Runtime runtime;
	
	@Override
	public void start(Stage primaryStage) throws Exception {		
		
		try {
			Parent root  = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(root,600,325);
			primaryStage.setScene(scene);
			primaryStage.setTitle("RISK");
			primaryStage.show();
			//setupJADE();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch();
		}	
	
	

	
	
}
