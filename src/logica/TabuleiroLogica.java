package logica;
import java.util.ArrayList;
import java.util.Hashtable;

import gui.*;
import javafx.scene.control.Button;

public class TabuleiroLogica {
	
	
private Hashtable <String, Territorio> territorios= new Hashtable<String,Territorio>();

//Europa
Territorio t1=new Territorio("e1", "0");
Territorio t2=new Territorio("e2", "0");
Territorio t3=new Territorio("e3", "0");
Territorio t4=new Territorio("e4", "0");
Territorio t5=new Territorio("e5", "0");
Territorio t6=new Territorio("e6", "0");
Territorio t7=new Territorio("e7", "0");

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
		
				
		
		territorios.put("e1",t1);
		territorios.put("e2",t2);
		territorios.put("e3",t3);
		territorios.put("e4",t4);
		territorios.put("e5",t5);
		territorios.put("e6",t6);
		territorios.put("e7",t7);
		
		
	}


	
	
}
