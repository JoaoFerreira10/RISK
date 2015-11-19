package gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Tabuleiro extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root  = FXMLLoader.load(Tabuleiro.class.getResource("Tabuleiro.fxml"));
			//StackPane root = new StackPane();
			Scene scene = new Scene(root,1120,700);
			//root.setId("map");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("RISK");
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


}
