package logica;

import java.util.ArrayList;

public class Territorio {

	private String nome, valor;
	private boolean ocupado;
	
	
	private ArrayList <Territorio> adjacentes= new ArrayList<Territorio>();
	


	public Territorio(String nome, String valor, boolean ocupado){		
		this.nome=nome;
		this.valor=valor;
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
	
	public String getValor(){
		return valor;
	}
	
	public void addAdjacente(Territorio territorio){
		adjacentes.add(territorio);
	}
	
	
	
	public ArrayList<Territorio> getAdjacentes(){
		return adjacentes;
	}

}
