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
Territorio t8=new Territorio("a1", "0", false);
Territorio t9=new Territorio("a2", "0", false);
Territorio t10=new Territorio("a3", "0", false);
Territorio t11=new Territorio("a4", "0", false);
Territorio t12=new Territorio("a5", "0", false);
Territorio t13=new Territorio("a6", "0", false);
Territorio t14=new Territorio("a7", "0", false);
Territorio t15=new Territorio("a8", "0", false);
Territorio t16=new Territorio("a9", "0", false);
Territorio t17=new Territorio("a10", "0", false);
Territorio t18=new Territorio("a11", "0", false);
Territorio t19=new Territorio("a12", "0", false);


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
		territorios.add(t8);
		territorios.add(t9);
		territorios.add(t10);
		territorios.add(t11);
		territorios.add(t12);
		territorios.add(t13);
		territorios.add(t14);
		territorios.add(t15);
		territorios.add(t16);
		territorios.add(t17);
		territorios.add(t18);
		territorios.add(t19);
		
		
	}
	
	public String getTerritorio(int x){
		
		return territorios.get(x).getNome();
	}
	
	public boolean territorioOcupado(int x){
		return territorios.get(x).isOcupado();
	}
	
	public void ocuparTerritorio(int x, String name){
		territorios.get(x).setOcupado(true);
		territorios.get(x).setOcupante(name);
	}
	
	
	public int getNumTerritorios(){
		return territorios.size();
	}


}
