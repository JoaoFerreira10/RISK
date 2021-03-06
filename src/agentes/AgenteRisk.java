package agentes;

import jade.core.Agent;

public abstract class AgenteRisk extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cor;
	private int numExercitos;
	
	
	public AgenteRisk(String cor, int Exercitos){
		this.cor=cor;
		this.numExercitos=Exercitos;
		
	}
	
	public String getType() {
		return "none";
	}

	public String getCor() {
		return cor;
	}

	public String getId() {
		return this.getCor();//+"-"+this.getType();
	}

	public int getNumExercitos() {
		return numExercitos;
	}

	public void addExercitos(int numExercitos) {
		this.numExercitos += numExercitos;
	}
	
	public void colocarExercitos(int numExercitos){
		this.numExercitos-=numExercitos;
	}
	
}
