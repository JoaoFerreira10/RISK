package agentes;


import java.util.ArrayList;

import gui.BoardController;
import gui.Singleton;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import logica.TabuleiroLogica;
import logica.Territorio;

import static gui.Singleton.*;

public class AgenteAleatorio extends AgenteRisk {
	
				
	private static final long serialVersionUID = 1L;
	
	TabuleiroLogica tabuleiro;
	BoardController board;
	
	
	public AgenteAleatorio(String cor, int pecas, TabuleiroLogica t, BoardController b) {
		super(cor, pecas);
		this.tabuleiro = t;
		this.board = b;
	}
	
	public String getType() {
		return "Aleatorio";
	}

	
	public int escolherTerritorio(){
		
		return (int) (Math.random()*42);	
	}
	
	public void addTerritorio(Territorio t){
		
		//territorios.add(t);
	}
	
	
	/*
	 * Neste m√©todo escolhe um territorio aleatorio da lista de territ√≥rios do agente
	 */
	public int distribuirExercito(TabuleiroLogica tabuleiro, int numExercitos){
		
			if(getNumExercitos()-numExercitos<0 || getNumExercitos()==0){
				return 0;
			}
		
		colocarExercitos(numExercitos);
		
		int x = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
		int y = (int) (Math.random()*x);
		
		return tabuleiro.getTerritoriosPorAgente(this.getCor()).get(y);
	}
	

	
	protected void setup() {

		System.out.println("aleatorio " + getCor());
		
		testBehaviour t = new testBehaviour(this);
		addBehaviour(t);
				
		
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("Aleatorio"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);	
		
	}

	
	
	public String distribuirExercitoRecebido(){
		
		String escolhido=null;
		int territorios= tabuleiro.getTerritoriosPorAgente(getCor()).size();

		int soldadosRecebidos = 0;
		

		
		int z = (int) (Math.random()*territorios);
		
	
		
		
		// quando sÛ tem dois ou menos territorios poe os reforÁos l·
		if(territorios<=2){
		
		/*for (int i = 0; i < territorios; i++) {
						
						int soldados= tabuleiro.getTerritorio(i).getpecas();
						
			if(soldados==1 && territorios<=2){*/
			escolhido= tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
			soldadosRecebidos=1;
			System.out.println("SOLDADOS QUANDO SO TEM UM TERRITORIO COM 1 TROPA: "+soldadosRecebidos);
			

			//}
			
		//}
		}
		else {
			soldadosRecebidos= (int) Math.ceil(territorios / 3); //cada agente recebe 1 soldado por cada 3 territorios no inicio de cada ronda
			
			if(  tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getpecas()==1 ){
			
			escolhido= tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
			}
			
			
			
			
		}
		
		return "TROPAS:"+escolhido +"-"+soldadosRecebidos;
		
	}
	
	public String selecionarAtaque() {
		String escolhido = null;
		String t=null;
		

		
		for (int i = 0; i < tabuleiro.getTerritoriosPorAgente(this.getCor()).size(); i++) {
			
			int random = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
			

			int z;
			int x=0;
				do{
					x++;
					if(x==42){
						return "Passo a vez";
					}
					z = (int) (Math.random()*random);   // escolhe um territorio aleatoriamente dos teus territorios
				
				}while( tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getpecas()<=1 );
			

			
			int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().size();
			
		
			int y = (int) (Math.random()*y2);
			

			if(!tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(y).getOcupante().equals(getCor()))
			{
				
			
			
			escolhido = tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(y).getNome();
			t=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
			// territorio que vai fazer o ataque nao pode ter so 1 soldado
			

			return "ATAQUE:"+escolhido+"-"+t;     // ataque(outro agente) - defesa(proprio agente)
			}

						
		}
		return "Passo a vez";

	}

	
	
	public  ArrayList<Territorio> getTerritories() {
		return tabuleiro.getObjetoTerritorio(getCor());
	}
	

	

	

	public class testBehaviour extends SimpleBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int n=0;
		TabuleiroLogica x;
		public testBehaviour(Agent a){
			super(a);
			//x=b;
		}
		
		@Override
		public void action(){

			
			int territorios= tabuleiro.getTerritoriosPorAgente(getCor()).size();
			if(territorios==0){    // quando um agente perde 
				
				if(getCor().equals("blue")){
				Singleton.getInstance().setBlueAlive(false);
				doDelete();}
				else if (getCor().equals("red")){
					Singleton.getInstance().setRedAlive(false);
				doDelete();}
				else if (getCor().equals("yellow")){
				Singleton.getInstance().setYellowAlive(false);
				doDelete();}
				else if (getCor().equals("green")){
					Singleton.getInstance().setGreenAlive(false);
				doDelete();}
						
			}
			

if(Singleton.getInstance().getPrimeiroJogar().equals(getCor())){
	
	//System.out.println("\n******Ronda**********\n");
}

		if (Singleton.getInstance().getPrimeiroJogar().equals(getCor()) 
					&& Singleton.getInstance().getState() == Singleton.GAME_START) {
						
				Singleton.getInstance().setState(Singleton.GAME_RUNNING);
			}
			else{

			ACLMessage msg = blockingReceive();

			
				if(msg.getContent().contains("ataque efetuado")){
					
					ACLMessage reply = msg.createReply();
					reply.setContent(getCor());
					send(reply);
					System.out.println(getCor() +": Passo a vez. \n\n\n");
					
					
				}else if(msg.getContent().contains("permissao para jogar")){
					
					System.out.println(getCor()+": permissao recebida");
					
					//Atualiza o numero de soldados para a gui
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							board.atualizaEstadoJogo(getCor());
							
						}
					});
						
				
					ACLMessage reply = msg.createReply();
					
					reply.setContent(distribuirExercitoRecebido());  //TROPAS: escolhido -soldadosRecebidos;
					send(reply);
					System.out.println(getCor() +": quero colocar tropas no seguinte territorio " +reply.getContent());   // ataque - defesa
					
				}
				else if(msg.getContent().contains("tropas inseridas. Permissao para atacar")){
					
					System.out.println(getCor()+": permissao para atacar recebida");
					
					ACLMessage reply = msg.createReply();
					
					if(selecionarAtaque().equals("Passo a vez")){
						reply.setContent("Nao vou atacar");
						
					}else{
						reply.setContent(selecionarAtaque()); // envia territorio que vai atacar
						System.out.println(getCor() +": quero atacar o seguinte territorio " +reply.getContent());   // ataque - defesa
					}
					send(reply);
					//System.out.println(getCor() +":NAO VOU ATACAR  " +reply.getContent());   // ataque - defesa
					
				}
				else{
					
					System.out.println("nao recebeu");
					
				}
				
			}	
		}
		
		@Override
		public boolean done(){
			return false;
		}

	}
	
}
