package logica;

import java.util.ArrayList;

public class Territorio {

	private String nome, valor;
	private ArrayList <Territorio> adjacentes= new ArrayList<Territorio>();
	
	
	public Territorio(String nome, String valor){		
		this.nome=nome;
		this.valor=valor;
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
