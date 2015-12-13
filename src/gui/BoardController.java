package gui;

import java.util.Iterator;

import agentes.AgenteAleatorio;
import agentes.AgenteAleatorio.testBehaviour;
import agentes.AgenteAtacante;
import agentes.AgenteDefensivo;
import agentes.AgenteRisk;
import agentes.AgenteCoordenador;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logica.TabuleiroLogica;



public class BoardController implements EventHandler<ActionEvent>{

	//Stage primaryStage;
	String primeiro = Singleton.getInstance().getPrimeiroJogar(); 
	String agente1 = Singleton.getInstance().getVermelho(); 
	String agente2 = Singleton.getInstance().getVerde(); 
	String agente3 = Singleton.getInstance().getAzul(); 
	String agente4 = Singleton.getInstance().getAmarelo(); 
	TabuleiroLogica tabuleiro = new TabuleiroLogica();
	AgenteRisk amarelo, vermelho, azul, verde;
	
	@FXML//Territorios
	private Label e1, e2, e3, e4, e5, e6, e7, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, o1, o2, o3, o4,
	af1, af2, af3, af4, af5, af6, Gronelandia, Alasca, EsteCanada, NorteCanada, SulCanada, OesteCanada, OesteEUA, EsteEUA, Mexico,
	Colombia, Brasil, Peru, Argentina;

	@FXML
	private Label vmT, grT, blT, ywT;
	
	//botao jogar
	public void iniciarJogo(ActionEvent ev){
		
		criarAgentes(primeiro,agente1, agente2, agente3,agente4);
			
		//setupJADE(agentes);
	}
	
	public void atualizaEstadoJogo(String cor){
		System.out.println("ATUALIZA ESTADO");
		
		if(cor.equals("red")){
			System.out.println("entrou");
		Integer territoriosVermelho= tabuleiro.getTerritoriosPorAgente(cor).size();
		vmT.setText(territoriosVermelho.toString());
		}
		
		
		
		
		/*Integer territoriosVerde= tabuleiro.getTerritoriosPorAgente(verde.getCor()).size();
		Integer territoriosAzul= tabuleiro.getTerritoriosPorAgente(azul.getCor()).size();
		Integer territoriosAmarelo= tabuleiro.getTerritoriosPorAgente(amarelo.getCor()).size();
		
		vmT.setText(territoriosVermelho.toString());
		grT.setText(territoriosVerde.toString());
		blT.setText(territoriosAzul.toString());
		ywT.setText(territoriosAmarelo.toString());*/
		System.out.println("ATUALIZADO ESTADO");
	}
	
	/*
	 * Método que instancia os 4 agentes
	 */	
	public void criarAgentes(String primeiro, String agente1, String agente2, String agente3, String agente4){
		
		AgenteRisk vermelho, verde, azul, amarelo;
		
		//Vermelho
		if(agente1.equals("Aleatorio")){
			 vermelho= new AgenteAleatorio("red", 50, tabuleiro, this);
		}else if(agente1.equals("Atacante")){
			 vermelho = new AgenteAtacante("red",50);
		}else{
			 vermelho = new AgenteDefensivo("red", 50);
		}
		
		//Verde
		if(agente2.equals("Aleatorio")){
			 verde= new AgenteAleatorio("green",50, tabuleiro, this);
		}else if(agente2.equals("Atacante")){
			 verde = new AgenteAtacante("green",50);
		}else{
			 verde = new AgenteDefensivo("green", 50);
		}
		
		//Azul
		if(agente3.equals("Aleatorio")){
			 azul = new AgenteAleatorio("blue",50, tabuleiro, this);
		}else if(agente3.equals("Atacante")){
			 azul = new AgenteAtacante("blue",50);
		}else{
			 azul = new AgenteDefensivo("blue",50);
		}
		
		//Amarelo
		if(agente4.equals("Aleatorio")){
			 amarelo = new AgenteAleatorio("yellow",50, tabuleiro, this);
		}else if(agente4.equals("Atacante")){
			 amarelo  = new AgenteAtacante("yellow",50);
		}else{
			 amarelo  = new AgenteDefensivo("yellow",50);
		}
				
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
		
	outerloop:
		do{					
				for(AgenteRisk a : agentes){
					
					if(a instanceof AgenteAleatorio){
												
							while(true){
								int x= ((AgenteAleatorio) a).escolherTerritorio();
								
								if(tabuleiro.territorioOcupado(x)==false){
									new PingPong();
									preencherTabuleiro(tabuleiro.getTerritorio(x).getNome(), a.getCor());
									a.colocarExercitos(1);
									tabuleiro.getTerritorio(x).addpecas(1);
									colocarPecaTabuleiro(tabuleiro.getTerritorio(x).getNome(), 1);
									tabuleiro.ocuparTerritorio(x, a.getCor());
									territoriosOcupados++;
									
									
									break;
								}
						}
							
					}
					
					if(a instanceof AgenteAtacante){
						
						while(true){
							int x= ((AgenteAtacante) a).escolherTerritorio(tabuleiro);

							if(tabuleiro.territorioOcupado(x)==false){
								preencherTabuleiro(tabuleiro.getTerritorio(x).getNome(), a.getCor());
								a.colocarExercitos(1);
								tabuleiro.getTerritorio(x).addpecas(1);
								colocarPecaTabuleiro(tabuleiro.getTerritorio(x).getNome(), 1);
								tabuleiro.ocuparTerritorio(x, a.getCor());
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
				
		System.out.println("Territorios acupados.");
		
		distribuirExercitos(agentes);

		
		setupJADE(agentes, new AgenteCoordenador(tabuleiro, this)); // JADE
		
		
		// fun�ao jogar , que cria os 4 agentes no RMA --- for(int i=0;i<map.getNumTrucks();i++){
		
	}
	
	
	public void distribuirExercitos(AgenteRisk[] agentes){
				
		int totalExercitos=0;
		
		for( AgenteRisk a: agentes){
			totalExercitos+=a.getNumExercitos();
		}

	outerloop:
	do{
		for( AgenteRisk a: agentes){
						
			if(a instanceof AgenteAtacante){
				if(a.getNumExercitos()!=0){
				int x=((AgenteAtacante) a).distribuirExercito(tabuleiro, 1);
				tabuleiro.getTerritorio(x).addpecas(1);
	
					colocarPecaTabuleiro(tabuleiro.getTerritorio(x).getNome(),
							tabuleiro.getTerritorio(x).getpecas());
					totalExercitos--;
				}
				
		}
			
			if(a instanceof AgenteAleatorio){
				if(a.getNumExercitos()!=0){
			int x=((AgenteAleatorio) a).distribuirExercito(tabuleiro, 1);

			tabuleiro.getTerritorio(x).addpecas(1);

				colocarPecaTabuleiro(tabuleiro.getTerritorio(x).getNome(),
						tabuleiro.getTerritorio(x).getpecas());	
				totalExercitos--;
				}
		}
			
			if(totalExercitos==0){
				break outerloop;
			}
		} 
	}while(true);	
		
		System.out.println("exercito"+totalExercitos);
	}
	
	
	
	/*
	 * Método que altera o número de exercitos na label
	 */	
	public void colocarPecaTabuleiro(String territorio, Integer pecas){
		
		if(territorio.equals("e1"))
			e1.setText(pecas.toString());
		else if(territorio.equals("e2"))
			e2.setText(pecas.toString());
		else if(territorio.equals("e3"))
			e3.setText(pecas.toString());
		else if(territorio.equals("e4"))
			e4.setText(pecas.toString());
		else if(territorio.equals("e5"))
			e5.setText(pecas.toString());
		else if(territorio.equals("e6"))
			e6.setText(pecas.toString());
		else if(territorio.equals("e7"))
			e7.setText(pecas.toString());
		else if(territorio.equals("a1"))
			a1.setText(pecas.toString());
		else if(territorio.equals("a2"))
			a2.setText(pecas.toString());
		else if(territorio.equals("a3"))
			a3.setText(pecas.toString());
		else if(territorio.equals("a4"))
			a4.setText(pecas.toString());
		else if(territorio.equals("a5"))
			a5.setText(pecas.toString());
		else if(territorio.equals("a6"))
			a6.setText(pecas.toString());
		else if(territorio.equals("a7"))
			a7.setText(pecas.toString());
		else if(territorio.equals("a8"))
			a8.setText(pecas.toString());
		else if(territorio.equals("a9"))
			a9.setText(pecas.toString());
		else if(territorio.equals("a10"))
			a10.setText(pecas.toString());
		else if(territorio.equals("a11"))
			a11.setText(pecas.toString());
		else if(territorio.equals("a12"))
			a12.setText(pecas.toString());
		else if(territorio.equals("o1"))
			o1.setText(pecas.toString());
		else if(territorio.equals("o2"))
			o2.setText(pecas.toString());
		else if(territorio.equals("o3"))
			o3.setText(pecas.toString());
		else if(territorio.equals("o4"))
			o4.setText(pecas.toString());
		else if(territorio.equals("af1"))
			af1.setText(pecas.toString());
		else if(territorio.equals("af2"))
			af2.setText(pecas.toString());
		else if(territorio.equals("af3"))
			af3.setText(pecas.toString());
		else if(territorio.equals("af4"))
			af4.setText(pecas.toString());
		else if(territorio.equals("af5"))
			af5.setText(pecas.toString());
		else if(territorio.equals("af6"))
			af6.setText(pecas.toString());		
		else if(territorio.equals("Gronelandia"))
			Gronelandia.setText(pecas.toString());
		else if(territorio.equals("Alasca"))
			Alasca.setText(pecas.toString());
		else if(territorio.equals("NorteCanada"))
			NorteCanada.setText(pecas.toString());
		else if(territorio.equals("EsteCanada"))
			EsteCanada.setText(pecas.toString());
		else if(territorio.equals("SulCanada"))
			SulCanada.setText(pecas.toString());
		else if(territorio.equals("OesteCanada"))
			OesteCanada.setText(pecas.toString());
		else if(territorio.equals("EsteEUA"))
			EsteEUA.setText(pecas.toString());
		else if(territorio.equals("OesteEUA"))
			OesteEUA.setText(pecas.toString());
		else if(territorio.equals("Mexico"))
			Mexico.setText(pecas.toString());
		else if(territorio.equals("Colombia"))
			Colombia.setText(pecas.toString());
		else if(territorio.equals("Peru"))
			Peru.setText(pecas.toString());
		else if(territorio.equals("Brasil"))
			Brasil.setText(pecas.toString());
		else if(territorio.equals("Argentina"))
			Argentina.setText(pecas.toString());

	}
		
	
	/*
	 * Método que altera a cor da label
	 */
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
		else if(territorio.equals("o1"))
			o1.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("o2"))
			o2.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("o3"))
			o3.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("o4"))
			o4.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af1"))
			af1.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af2"))
			af2.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af3"))
			af3.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af4"))
			af4.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af5"))
			af5.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("af6"))
			af6.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Alasca"))
			Alasca.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("NorteCanada"))
			NorteCanada.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("OesteCanada"))
			OesteCanada.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("SulCanada"))
			SulCanada.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("EsteCanada"))
			EsteCanada.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("OesteEUA"))
			OesteEUA.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("EsteEUA"))
			EsteEUA.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Mexico"))
			Mexico.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Gronelandia"))
			Gronelandia.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Colombia"))
			Colombia.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Peru"))
			Peru.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Brasil"))
			Brasil.setStyle("-fx-background-color: "+ cor+";");
		else if(territorio.equals("Argentina"))
			Argentina.setStyle("-fx-background-color: "+ cor+";");
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
	
	
	static ContainerController myContainer;
	private static AgentContainer container;
		
	private static void setupJADE(AgenteRisk[] agentes, AgenteCoordenador coordenador) {
		
			Profile profile=new ProfileImpl("localhost", 1099,
			Profile.PLATFORM_ID);
			
			profile.setParameter(Profile.PLATFORM_ID, "Risk");
			profile.setParameter("gui", "ture");
			
		
			AgentController agent;
			
			container=Runtime.instance().createMainContainer(profile);

			
			        try
			        {
			            container.start();
			            container.createNewAgent("rma", "jade.tools.rma.rma",null).start();

			          //para cada agente verifica se � aleatorio, atacante, ... e adiciona � RMA cada agente	
			            	for(int i=0;i<agentes.length;i++){		 	
			    			
			            		
			            		if(agentes[i] instanceof AgenteAleatorio){
			    				agent = container.acceptNewAgent(agentes[i].getId(), agentes[i]);	
			    				//agent.start();
			            		}
				    			if(agentes[i] instanceof AgenteAtacante){
				    				agent = container.acceptNewAgent(agentes[i].getId(), agentes[i]);
				    				//agent.start();
				    			}
				    			
			            	}	

			            	
			            	for (AgenteRisk x : agentes) {
			            		container.getAgent(x.getId()).start();
			            	}
			            	
			            	agent=container.acceptNewAgent("coordenador", coordenador);
			            		container.getAgent("coordenador").start();
			        
			        }

			        catch(Exception ex)

			        {

			        }	
	}
	
}
