package agentes;


import java.util.ArrayList;

import gui.BoardController;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logica.TabuleiroLogica;
import logica.Territorio;

public class AgenteAleatorio extends AgenteRisk {
	
				
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ArrayList<Territorio> territorios = new ArrayList<Territorio>();
	
	TabuleiroLogica tabuleiro;
	BoardController board;
	
	public AgenteAleatorio(String cor, int pecas, TabuleiroLogica t, BoardController b) {
		super(cor, pecas);
		tabuleiro= t;
		board = b;
	}

	
	public int escolherTerritorio(){
		
		return (int) (Math.random()*29);	
	}
	
	public void addTerritorio(Territorio t){
		
		//territorios.add(t);
	}
	
	/*
	 * Neste método escolhe um territorio aleatorio da lista de territórios do agente
	 */
	public int distribuirExercito(TabuleiroLogica tabuleiro, int numExercitos){
		
		if(getNumExercitos()-numExercitos<0 || getNumExercitos()==0){
			return 0;
		}
		
		colocarExercitos(numExercitos);
		
		int x = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();   // vai ver que territorios tem um agente
		int y = (int) (Math.random()*x);   // escolhe um territorio aleatorio
		
		
		
		return tabuleiro.getTerritoriosPorAgente(this.getCor()).get(y);
	}
	
	
	
	public void receiveSoldiers(int numSoldiers) {
	/*	ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
		// Can't place soldiers without territories
		if(playerTerritories.size() == 0) {
			return new ReceiveAction();
		}
		
		int index, size = playerTerritories.size();
		ReceiveAction action = new ReceiveAction();

		// Choose a random territory for each soldier received.
		for (int i = 0; i < numSoldiers; i++) {
			index = r.nextInt(size);
			action.addSoldiersTerritory(1, playerTerritories.get(index));
		}
		return action;*/
	}
	
	protected void setup() {
		System.out.println("aleatorio " + getCor());
		
		for (int i = 0; i < tabuleiro.getTerritoriosPorAgente(getCor()).size(); i++) {
			int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(getCor()).get(i)).getAdjacentes().size();
			System.out.println("territorios adj do " + getCor() + " : " + y2);
			}
		
		agenteTeste n = new agenteTeste(this);
		//addBehaviour(n);
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("Aleatorio"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);			

	}
	
	public class agenteTeste extends SimpleBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private TabuleiroLogica x;
		public agenteTeste(Agent a){
			super(a);
			//x=tabuleiro;
			//this.x=tab;
		
		}
		
		@Override
		public void action(){
			System.out.println("entrou no agente behaviour");
		//	int num;
		//	num= x.getNumTerritorios();
		//	System.out.println("territorios num:" + num);
			/*for (int i = 0; i < tabuleiro.getTerritoriosPorAgente(getCor()).size(); i++) {
				int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(getCor()).get(i)).getAdjacentes().size();
				System.out.println("territorios adj:" + y2);
				}*/
		}
		
		@Override
		public boolean done(){
			return false;
		}
	}
	
}
