package agentes;


import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logica.TabuleiroLogica;
import logica.Territorio;

public class AgenteAtacante extends AgenteRisk {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgenteAtacante(String cor, int pecas) {
		super(cor, pecas);
	}
	
	public String getType() {
		return "Atacante";
	}
	
	
	public int escolherTerritorio(TabuleiroLogica tabuleiro){
				
		// Na primeira jogada, escolhe um terreno aleatorio
		if(tabuleiro.getTerritoriosPorAgente(this.getCor()).size()==0){

			return (int) (Math.random()*29);		
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
		
		return (int) (Math.random()*29);
		
	}
	
	/*
	 * Escolhe um territorio aleatorio da lista de territórios do agente e retorna o indice do 
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
	
	protected void setup() {
		System.out.println("atacante " + getCor());
		
		
		//agenteTeste n = new agenteTeste(this);
	//	addBehaviour(n);
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("Atacante"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);			

	}

}
