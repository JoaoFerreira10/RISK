package gui;

import agentes.AgenteAleatorio;
import agentes.AgenteAtacante;
import agentes.AgenteDefensivo;
import agentes.AgenteRisk;
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
	AgenteRisk amarelo, vermelho, azul, verde;
	
	@FXML
	private Label e1, e2, e3, e4, e5, e6, e7, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12;

	
	//botao jogar
	public void iniciarJogo(ActionEvent ev){
		criarAgentes("a","Aleatorio", "Aleatorio", "Aleatorio","Aleatorio");			
	}
	
	/*
	 * Método que instancia os 4 agentes
	 */	
	public void criarAgentes(String primeiro, String agente1, String agente2, String agente3, String agente4){
		
		System.out.println("entrou em criaragentes");
		
		//agente vermelho
		/*if(agente1.equals("Aleatorio")){
			System.out.println("entrou no if");
			AgenteAleatorio vermelho= new AgenteAleatorio("red",0);
			escolherTerritorios(vermelho, vermelho, vermelho, vermelho);
		}else if(agente1.equals("Atacante")){
			AgenteAtacante vermelho = new AgenteAtacante("red",0);
		}else{
			AgenteDefensivo vermelho = new AgenteDefensivo("red", 0);
		}
		
		//agente verde
		if(agente2.equals("Aleatorio")){
			AgenteAleatorio verde= new AgenteAleatorio("green",0);
		}else if(agente2.equals("Atacante")){
			AgenteAtacante verde = new AgenteAtacante("green",0);
		}else if(agente2.equals("Defensivo")){
			AgenteDefensivo verde = new AgenteDefensivo("green", 0);
		}
		
		//Azul
		if(agente3.equals("Aleatorio")){
			AgenteAleatorio azul = new AgenteAleatorio("blue",0);
		}else if(agente3.equals("Atacante")){
			AgenteAtacante azul = new AgenteAtacante("blue",0);
		}else if(agente3.equals("Defensivo")){
			AgenteDefensivo azul = new AgenteDefensivo("blue",0);
		}
		
		//Amarelo
		if(agente4.equals("Aleatorio")){
			 amarelo = new AgenteAleatorio("yellow",0);
		}else if(agente4.equals("Atacante")){
			 amarelo  = new AgenteAtacante("yellow",0);
		}else{
			 amarelo  = new AgenteDefensivo("yellow",0);
		}*/
		
		
		AgenteAleatorio vermelho= new AgenteAleatorio("red",10);
		AgenteAleatorio verde= new AgenteAleatorio("green",10);
		AgenteAleatorio azul = new AgenteAleatorio("blue",10);
		AgenteAleatorio amarelo = new AgenteAleatorio("yellow",10);
		
		escolherTerritorios(vermelho, verde, azul, amarelo);
	}
	
	
	/*
	 * Método que recebe como argumentos os 4 agentes instanciados em "criaragentes"
	 * e chama os metodos dos agentes "escolherTerritorio"
	 */	
	public void escolherTerritorios(AgenteRisk a1, AgenteRisk a2, AgenteRisk a3, AgenteRisk a4){

		int territoriosOcupados=0;
		
		AgenteRisk[] agentes= new AgenteRisk[4];
		agentes[0]=a1;
		agentes[1]=a2;
		agentes[2]=a3;
		agentes[3]=a4;
		
		System.out.println("agentes instanciados");
		System.out.println("territorios-->"+tabuleiro.getNumTerritorios());
		
	outerloop:
		do{		
			
				for(AgenteRisk a : agentes){
					
					if(a instanceof AgenteAleatorio){
												
							while(true){
								int x= ((AgenteAleatorio) a).escolherTerritorio();
								
								if(tabuleiro.territorioOcupado(x)==false){
									System.out.println(x);
									preencherTabuleiro(tabuleiro.getTerritorio(x), a.getCor());
									tabuleiro.ocuparTerritorio(x, a.getName());
									territoriosOcupados++;
									break;
								}
						}				
					}
						if(territoriosOcupados==tabuleiro.getNumTerritorios()){
							break outerloop;
						}
				}
		}while(true);
		
		
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
		else if(territorio.equals("a1"))
			a1.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a2"))
			a2.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a3"))
			a3.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a4"))
			a4.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a5"))
			a5.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a6"))
			a6.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a7"))
			a7.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a8"))
			a8.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a9"))
			a9.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a10"))
			a10.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a11"))
			a11.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("a12"))
			a12.setStyle("-fx-background-color: "+ cor+";");
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
