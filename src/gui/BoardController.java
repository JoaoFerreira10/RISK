package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import logica.Coordenador;
import logica.TabuleiroLogica;


public class BoardController implements EventHandler<ActionEvent>{

	//Stage primaryStage;
	String str1 = Singleton.getInstance().getPrimeiroJogar(); 
	TabuleiroLogica tabuleiro = new TabuleiroLogica();
	
	@FXML
	private Label e1, e2, e3, e4, e5, e6, e7;
	@FXML
	private Label[] label={e1, e2, e3, e4, e5, e6, e7};
	
	//botao jogar
	public void iniciarJogo(ActionEvent ev){
		
		
		int x= (int) (Math.random()*(8-1)+1);

		preencherTabuleiro(tabuleiro.getTerritorio(x), "red");
			
	}
	
	public void preencherTabuleiro(String territorio, String cor){
		
		//Europa
		if(territorio.equals("e1"))
			e1.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e2"))
			e2.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e3"))
			e3.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e4"))
			e4.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e5"))
			e5.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e6"))
			e6.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("e7"))
			e7.setStyle("-fx-background-color: "+ cor+";");
	}
	
	
	
	
	@Override
	public void handle(ActionEvent ev) {
		// TODO Auto-generated method stub
		
		Button btn = (Button) ev.getSource();
		String id = btn.getId();
		System.out.println(id);
//		buttonClicked(btn);
	}
	
	
	/*public void buttonClicked (Button b){
		System.out.println(cl.getText());
		  // vai buscar o valor guardado
		cl.setText(str1);
		System.out.println("dsadsd");
		 System.out.println("String: "+str1);
		 if(str1.contains("vermelho")){
			 labeltext("red");
		 } 
		 else if(str1.contains("azul")){
			 labeltext("blue");
		 }
		 else if(str1.contains("verde")){
			 labeltext("green");
		 }
		 else if(str1.contains("amarelo")){
			 labeltext("yellow");
		 }
		
		Integer value=Integer.parseInt(b.getText());
		System.out.println("ok...");
		b.setStyle("-fx-background-color: blue;");
		value++;
		b.setText(value.toString());
		//System.out.println(value);
	}*/
	
}
