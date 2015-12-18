package agentes;


import java.util.ArrayList;
import java.util.Hashtable;

import gui.BoardController;
import gui.Singleton;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import logica.TabuleiroLogica;
import logica.Territorio;

public class AgenteAtacante extends AgenteRisk {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TabuleiroLogica tabuleiro;
	BoardController board;
	
	public AgenteAtacante(String cor, int pecas, TabuleiroLogica t, BoardController b) {
		super(cor, pecas);
		this.tabuleiro = t;
		this.board = b;
	}
	
	public String getType() {
		return "Aleatorio";
	}
	
	
	public int escolherTerritorio(TabuleiroLogica tabuleiro){
				
		// Na primeira jogada, escolhe um terreno aleatorio
		if(tabuleiro.getTerritoriosPorAgente(this.getCor()).size()==0){

			return (int) (Math.random()*42);		
		}
				
		
		for (int i = 0; i < tabuleiro.getTerritoriosPorAgente(this.getCor()).size(); i++) {
			int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(i)).getAdjacentes().size();
			
			if(y2!=0){			
				for (int j = 0; j <y2; j++) {
					Territorio escolhido= tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(
							this.getCor()).get(i)).getAdjacentes().get(j);
					
					for(int z=0; z<tabuleiro.getNumTerritorios();z++){
						
						if(escolhido.getNome().equals(tabuleiro.getTerritorio(z).getNome())&& !tabuleiro.getTerritorio(z).isOcupado()){
							return z;

						}
					}
					
				}
			}
		}
		
		return (int) (Math.random()*42);
		
	}
	
	/*
	 * Escolhe um territorio aleatorio da lista de territ�rios do agente e retorna o indice do 
	 * territorio a colocar o exercito
	 */
	public int distribuirExercito(TabuleiroLogica tabuleiro, int numExercitos){
		
		/*if(getNumExercitos()-numExercitos<0 || getNumExercitos()==0){
			return 0;
		}*/
		
		colocarExercitos(numExercitos);
		
		int x = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
		int y = (int) (Math.random()*x);
		
		return tabuleiro.getTerritoriosPorAgente(this.getCor()).get(y);
	}
	
	
	
	
	
	public String distribuirExercitoRecebido(){

		String escolhido=null;
		int territorios= tabuleiro.getTerritoriosPorAgente(getCor()).size();

		int soldadosRecebidos = 0;
		
		int z;
		
outerloop:
		do{
			 z = (int) (Math.random()*territorios);
			
			int totAdjacentes=0;
				
				for(int i=0;i < tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().size();i++){
					
					if(!tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(i).getNome().equals(getCor())){
						totAdjacentes++;
					}
				}
			if(totAdjacentes!=0){
				break outerloop;
			}
			territorios--;
		}while(territorios!=0);		

		
		if(territorios<=2){
		
			escolhido= tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
			soldadosRecebidos=1;

			
		}
		else {

			soldadosRecebidos= (int) Math.ceil(territorios / 3); //cada agente recebe 1 soldado por cada 3 territorios no inicio de cada ronda
			
			if(  tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getpecas()<=5 ){
			
			escolhido= tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
			}
	
		}
		
		return "TROPAS:"+escolhido +"-"+soldadosRecebidos;	
	}
	
	
	
	
	/*
	 * O agente atacante procura sempre selecionar os seus territorios com mais tropas e efetuar ataques nos territorios
	 * adjacentes com menor numero de tropas
	 */
	public String selecionarAtaque() {
		String escolhido = null;
		String t=null;
		

		
		for (int i = 0; i < tabuleiro.getTerritoriosPorAgente(this.getCor()).size(); i++) {
			
			int random = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
			

			
			
			int z= (int) (Math.random()*random);
			int x=0;

			int nPeca = 5;
				do{
					x++;
					if(x==8){
						nPeca--;
					}if(x==16){
						nPeca--;
					}
					if(x==24){
						nPeca--;
					}
					if(x==30){
						nPeca--;
					}
					if(x==42){
						return "Passo a vez";
					}
					z = (int) (Math.random()*random);   // escolhe um territorio aleatoriamente dos teus territorios
				
				}while( tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getpecas()<= nPeca);
			

			
			int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().size();
	
			int p=2;		
			do{
						int y = (int) (Math.random()*y2);			
			
						
						if(!tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(y).getOcupante().equals(getCor())
								&& tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(y).getpecas() < p )
						{
							
						
						
						escolhido = tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getAdjacentes().get(y).getNome();
						t=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(this.getCor()).get(z)).getNome();
						// territorio que vai fazer o ataque nao pode ter so 1 soldado
						
			
						return "ATAQUE:"+escolhido+"-"+t;     // ataque(outro agente) - defesa(proprio agente)
						}
						p++;
			}while(p<=10);
						
		}
		return "Passo a vez";

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
					System.out.println(getCor() +":Quero colocar tropas no seguinte territorio " +reply.getContent());   // ataque - defesa
					
				}
				else if(msg.getContent().contains("tropas inseridas. Permissao para atacar")){
					
					System.out.println(getCor()+": permissao para atacar recebida");
					
					ACLMessage reply = msg.createReply();
					
					if(selecionarAtaque().equals("Passo a vez")){
						reply.setContent("Nao vou atacar");
						System.out.println("entrou------------------------------------------------------");
					}else{
						reply.setContent(selecionarAtaque()); // envia territorio que vai atacar
					}
					send(reply);
					System.out.println(getCor() +":NAO VOU ATACAR  " +reply.getContent());   // ataque - defesa
					
				}
				else{
					
					System.out.println("nao recebeu....");
					
				}
				
			}	
		}
		
		@Override
		public boolean done(){
			return false;
		}

	}
	
	
	
	protected void setup() {
		System.out.println("atacante " + getCor());
		
		
		testBehaviour n = new testBehaviour(this);
		addBehaviour(n);
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("Aleatorio"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);			

	}
	

}