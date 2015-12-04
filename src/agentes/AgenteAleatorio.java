package agentes;


import java.util.ArrayList;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logica.Territorio;

public class AgenteAleatorio extends AgenteRisk {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Territorio> territorios = new ArrayList<Territorio>();
	
	
	public AgenteAleatorio(String cor, int pecas) {
		super(cor, pecas);
	}

	
	public int escolherTerritorio(){
		
		return (int) (Math.random()*19);	
	}
	
	public void addTerritorio(Territorio t){
		
		territorios.add(t);
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
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("CentralAgent"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);			

	}
	
}
