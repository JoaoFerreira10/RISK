package logica;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import gui.*;
import javafx.scene.control.Button;

public class TabuleiroLogica {
	
	
private ArrayList <Territorio> territorios= new ArrayList<Territorio>();

//Europa
Territorio t1=new Territorio("e1", "0", false);
Territorio t2=new Territorio("e2", "0", false);
Territorio t3=new Territorio("e3", "0", false);
Territorio t4=new Territorio("e4", "0", false);
Territorio t5=new Territorio("e5", "0", false);
Territorio t6=new Territorio("e6", "0", false);
Territorio t7=new Territorio("e7", "0", false);

//Asia


	public TabuleiroLogica(){
	
		t1.addAdjacente(t2);
		t1.addAdjacente(t3);
		t1.addAdjacente(t4);
		t2.addAdjacente(t3);
		t2.addAdjacente(t4);
		t3.addAdjacente(t4);
		t3.addAdjacente(t5);
		t4.addAdjacente(t6);
		t4.addAdjacente(t7);
		
				
		
		territorios.add(t1);
		territorios.add(t2);
		territorios.add(t3);
		territorios.add(t4);
		territorios.add(t5);
		territorios.add(t6);
		territorios.add(t7);
		
		
	}
	
	public String getTerritorio(int x){
		
		return territorios.get(x).getNome();
	}
	


}
