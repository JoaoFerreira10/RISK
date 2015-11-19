package agentes;

import jade.core.Agent;

public abstract class AgenteRisk extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cor;
	private int numPecas;
	
	
	public AgenteRisk(String cor, int pecas){
		this.cor=cor;
		this.numPecas=pecas;
		
	}

	public String getCor() {
		return cor;
	}

	public int getNumPecas() {
		return numPecas;
	}

	public void addPecas(int numPecas) {
		this.numPecas += numPecas;
	}
	
	public void removerPecas(int numPecas){
		this.numPecas-=numPecas;
	}
	
}
