package agentes;


import java.util.ArrayList;

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
	
	
	public AgenteAleatorio(String cor, int pecas) {
		super(cor, pecas);
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
		
		int x = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
		int y = (int) (Math.random()*x);
		
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
		
		agenteTeste n = new agenteTeste(this);
	//	addBehaviour(n);
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

		public agenteTeste(Agent a){
			super(a);
		}
		
		@Override
		public void action(){
			System.out.println("entrou no agente behaviour");
		}
		
		@Override
		public boolean done(){
			return false;
		}
	}
	
}
