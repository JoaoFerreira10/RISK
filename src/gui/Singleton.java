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
    private String vencedor;
    
    
    
    public boolean isRedAlive() {
		return redAlive;
	}

	public void setRedAlive(boolean redAlive) {
		this.redAlive = redAlive;
	}

	public boolean isGreenAlive() {
		return greenAlive;
	}

	public void setGreenAlive(boolean greenAlive) {
		this.greenAlive = greenAlive;
	}

	public boolean isBlueAlive() {
		return blueAlive;
	}

	public void setBlueAlive(boolean blueAlive) {
		this.blueAlive = blueAlive;
	}

	public boolean isYellowAlive() {
		return yellowAlive;
	}

	public void setYellowAlive(boolean yellowAlive) {
		this.yellowAlive = yellowAlive;
	}

	private boolean redAlive=true, greenAlive=true, blueAlive=true, yellowAlive=true;
    
    
    

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
   
   
   public void setVencedorDoJogo (String vencedor) {
       this.vencedor = vencedor;
   }
   public String getVencedorDoJogo() {
		return vencedor;
	}

}