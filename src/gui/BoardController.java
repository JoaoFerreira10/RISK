package gui;

import agentes.AgenteAleatorio;
import agentes.AgenteAtacante;
import agentes.AgenteDefensivo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logica.TabuleiroLogica;


public class BoardController implements EventHandler<ActionEvent>{

	//Stage primaryStage;
	String primeiro = Singleton.getInstance().getPrimeiroJogar(); 
	TabuleiroLogica tabuleiro = new TabuleiroLogica();
	
	@FXML
	private Label e1, e2, e3, e4, e5, e6, e7;

	
	//botao jogar
	public void iniciarJogo(ActionEvent ev){
		
		
		int x= (int) (Math.random()*(8-1)+1);

		criarAgentes("a","Agente Aleatorio", "Agente Aleatorio", "Agente Aleatorio","Agente Aleatorio");
		preencherTabuleiro(tabuleiro.getTerritorio(x), "red");
			
	}
	
	
	public void criarAgentes(String primeiro, String agente1, String agente2, String agente3, String agente4){
		
		//agente vermelho
		if(agente1.equals("Agente Aleatorio")){
			AgenteAleatorio vermelho= new AgenteAleatorio("red",0);
		}else if(agente1.equals("Agente Atacante")){
			AgenteAtacante vermelho = new AgenteAtacante("red",0);
		}else if(agente1.equals("Agente Defensivo")){
			AgenteDefensivo vermelho = new AgenteDefensivo("red", 0);
		}
		
		//agente verde
		if(agente2.equals("Agente Aleatorio")){
			AgenteAleatorio verde= new AgenteAleatorio("green",0);
		}else if(agente2.equals("Agente Atacante")){
			AgenteAtacante verde = new AgenteAtacante("green",0);
		}else if(agente2.equals("Agente Defensivo")){
			AgenteDefensivo verde = new AgenteDefensivo("green", 0);
		}
		
		//agente azul
		if(agente3.equals("Agente Aleatorio")){
			AgenteAleatorio azul = new AgenteAleatorio("blue",0);
		}else if(agente3.equals("Agente Atacante")){
			AgenteAtacante azul = new AgenteAtacante("blue",0);
		}else if(agente3.equals("Agente Defensivo")){
			AgenteDefensivo azul = new AgenteDefensivo("blue",0);
		}
		
		//agente amarelo
		if(agente4.equals("Agente Aleatorio")){
			AgenteAleatorio amarelo = new AgenteAleatorio("yellow",0);
		}else if(agente4.equals("Agente Atacante")){
			AgenteAtacante amarelo  = new AgenteAtacante("yellow",0);
		}else{
			AgenteDefensivo amarelo  = new AgenteDefensivo("yellow",0);
		}
			
		
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
