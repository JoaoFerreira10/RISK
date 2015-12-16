package logica;


public class Coordenador {
	
	String primeiro;
	TabuleiroLogica tabulerio=new TabuleiroLogica();
	
	public Coordenador(String primeiro){
			this.primeiro=primeiro;
			System.out.println(primeiro);
					
	}
	
	public String getCor(){
		
		return primeiro;
	}

}
