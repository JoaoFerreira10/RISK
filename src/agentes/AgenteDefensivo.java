package agentes;

public class AgenteDefensivo extends AgenteRisk {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgenteDefensivo(String cor, int pecas) {
		super(cor, pecas);
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return "Defensivo";
	}



}
