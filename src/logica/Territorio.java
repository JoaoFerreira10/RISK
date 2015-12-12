package logica;

import java.util.ArrayList;

public class Territorio {

	private String nome, ocupante=null;
	private int pecas=0;
	private boolean ocupado;		
	private ArrayList <Territorio> adjacentes= new ArrayList<Territorio>();
	


	public Territorio(String nome, int pecas, boolean ocupado){		
		this.nome=nome;
		this.pecas=pecas;
		this.ocupado=ocupado;
	}
	
	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void addAdjacente(Territorio territorio){
		adjacentes.add(territorio);
	}
	
	public ArrayList<Territorio> getAdjacentes(){
		return adjacentes;
	}

	public String getOcupante() {
		return ocupante;
	}

	public void setOcupante(String ocupante) {
		this.ocupante = ocupante;
	}
	
	public int getpecas() {
		return pecas;
	}

	public void addpecas(int pecas) {
		this.pecas += pecas;
	}
	
	public void removerPecas(int pecas) {
		this.pecas -= pecas;
	}
	
	
}
