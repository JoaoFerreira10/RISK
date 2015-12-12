package agentes;


import java.util.ArrayList;

import gui.BoardController;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logica.TabuleiroLogica;
import logica.Territorio;

public class AgenteNovo extends AgenteRisk {
	
				
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ArrayList<Territorio> territorios = new ArrayList<Territorio>();
	TabuleiroLogica tabuleiro;
	BoardController board;
	
	public AgenteNovo(String cor, int pecas, TabuleiroLogica t, BoardController b) {
		super(cor, pecas);
		 tabuleiro= t;
		 board = b;
	}

	

	
	

	
	protected void setup() {
		System.out.println("agente novo");
		
		agenteTeste n = new agenteTeste(this);
		Object[] args = getArguments();
		if(args == null){
		System.out.println("entrou");
		}
		
		//System.out.println("aaaa: "+ tt.getNumTerritorios());
		
		for (int i = 0; i < tabuleiro.getTerritoriosPorAgente("red").size(); i++) {
			int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente("red").get(i)).getAdjacentes().size();
			System.out.println("territorios adj:" + y2);
			}
		
		//board.preencherTabuleiro("e1", "black");
		
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

		//private TabuleiroLogica x;
		public agenteTeste(Agent a){
			super(a);
			//x=tabuleiro;
			//this.x=tab;
		
		}
		
		@Override
		public void action(){
			System.out.println("entrou no agente behaviour");
			
			
			
			/*for (int i = 0; i < tabuleiro.getTerritoriosPorAgente("red").size(); i++) {
				int y2=tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente("red").get(i)).getAdjacentes().size();
				System.out.println("territorios adj:" + y2);
				}*/
			
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
