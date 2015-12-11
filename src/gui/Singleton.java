package gui;



public class Singleton {
	public static final int GAME_START = 0;
	public static final int GAME_RUNNING = 1;
	public static final int GAME_END = 2;
	
	public Singleton() {
		this.state = GAME_START;
	}
	
    private static Singleton instance = new Singleton();
    public static Singleton getInstance(){
        return instance;
    }

    private String primeiroJogar;   // variavel a ser usada entre controladores
    private String vermelho, verde, amarelo, azul;
    private int state;
    

    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getVerde() {
		return verde;
	}

	public void setVerde(String verde) {
		this.verde = verde;
	}

	public String getAmarelo() {
		return amarelo;
	}

	public void setAmarelo(String amarelo) {
		this.amarelo = amarelo;
	}

	public String getAzul() {
		return azul;
	}

	public void setAzul(String azul) {
		this.azul = azul;
	}

	public String getVermelho() {
		return vermelho;
	}

	public void setVermelho(String vermelho) {
		this.vermelho = vermelho;
	}

	public String getPrimeiroJogar() {
       return primeiroJogar;
    }

   public void setPrimeiroJogar(String primeiroJogar) {
       this.primeiroJogar = primeiroJogar;
   }

}