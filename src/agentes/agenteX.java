package agentes;

import logica.TabuleiroLogica;

public class agenteX extends AgenteRisk {

	public agenteX(String cor, int Exercitos, TabuleiroLogica t) {
		super(cor, Exercitos);
		// TODO Auto-generated constructor stub
	}

	public void setup(){
		
		
		System.out.println("agente -->"+ getAID().getLocalName());
		
	}
	
	
	
	
	
}
