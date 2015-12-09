package logica;
import java.util.ArrayList;


public class TabuleiroLogica {
	
	
private ArrayList <Territorio> territorios= new ArrayList<Territorio>();

//Europa
Territorio t1=new Territorio("e1", 0, false);
Territorio t2=new Territorio("e2", 0, false);
Territorio t3=new Territorio("e3", 0, false);
Territorio t4=new Territorio("e4", 0, false);
Territorio t5=new Territorio("e5", 0, false);
Territorio t6=new Territorio("e6", 0, false);
Territorio t7=new Territorio("e7", 0, false);

//Asia
Territorio t8=new Territorio("a1", 0, false);
Territorio t9=new Territorio("a2", 0, false);
Territorio t10=new Territorio("a3", 0, false);
Territorio t11=new Territorio("a4", 0, false);
Territorio t12=new Territorio("a5", 0, false);
Territorio t13=new Territorio("a6", 0, false);
Territorio t14=new Territorio("a7", 0, false);
Territorio t15=new Territorio("a8", 0, false);
Territorio t16=new Territorio("a9", 0, false);
Territorio t17=new Territorio("a10", 0, false);
Territorio t18=new Territorio("a11", 0, false);
Territorio t19=new Territorio("a12", 0, false);

//Oceania

Territorio o1=new Territorio("o1", 0, false);
Territorio o2=new Territorio("o2", 0, false);
Territorio o3=new Territorio("o3", 0, false);
Territorio o4=new Territorio("o4", 0, false);

//Africa

Territorio af1=new Territorio("af1", 0, false);
Territorio af2=new Territorio("af2", 0, false);
Territorio af3=new Territorio("af3", 0, false);
Territorio af4=new Territorio("af4", 0, false);
Territorio af5=new Territorio("af5", 0, false);
Territorio af6=new Territorio("af6", 0, false);



	public TabuleiroLogica(){
	
		//Europa
		t1.addAdjacente(t2);
		t1.addAdjacente(t3);
		t1.addAdjacente(t4);
		t2.addAdjacente(t5);
		t2.addAdjacente(t4);
		t2.addAdjacente(t6);
		t2.addAdjacente(t7);
		t3.addAdjacente(t4);
		t3.addAdjacente(t1);
		t3.addAdjacente(t6);
		t4.addAdjacente(t6);
		t4.addAdjacente(t3);
		t4.addAdjacente(t2);
		t4.addAdjacente(t13);
		t4.addAdjacente(t14);
		t4.addAdjacente(t17);
		
		t5.addAdjacente(t1);
		t5.addAdjacente(t2);
		t5.addAdjacente(t6);
		t5.addAdjacente(t7);
		t6.addAdjacente(t1);
		t6.addAdjacente(t2);
		t6.addAdjacente(t3);
		t6.addAdjacente(t4);
		t6.addAdjacente(t5);
		t7.addAdjacente(t5);
		t7.addAdjacente(t6);	
		t7.addAdjacente(t2);
		
		//Asia
		t8.addAdjacente(t11);
		t8.addAdjacente(t12);
		t8.addAdjacente(t13);
		t8.addAdjacente(t15);
		t8.addAdjacente(t16);
		t8.addAdjacente(t17);
		t8.addAdjacente(t18);
		t9.addAdjacente(t10);
		t9.addAdjacente(t11);
		t9.addAdjacente(t15);
		t9.addAdjacente(t19);
		t10.addAdjacente(t9);
		t10.addAdjacente(t11);
		t10.addAdjacente(t12);
		t11.addAdjacente(t9);
		t11.addAdjacente(t8);
		t11.addAdjacente(t10);
		t11.addAdjacente(t12);
		t11.addAdjacente(t15);
		t12.addAdjacente(t8);
		t12.addAdjacente(t10);
		t12.addAdjacente(t11);
		t12.addAdjacente(t13);
		t12.addAdjacente(t17);
		t13.addAdjacente(t8);
		t13.addAdjacente(t12);
		t13.addAdjacente(t17);
		t13.addAdjacente(t4);
		t14.addAdjacente(t3);
		t14.addAdjacente(t4);
		t14.addAdjacente(t16);
		t14.addAdjacente(t17);
		t15.addAdjacente(t8);
		t15.addAdjacente(t9);
		t15.addAdjacente(t11);
		t15.addAdjacente(t19);
		t16.addAdjacente(t14);
		t16.addAdjacente(t8);
		t16.addAdjacente(t17);
		t16.addAdjacente(t18);
		t17.addAdjacente(t4);
		t17.addAdjacente(t14);
		t17.addAdjacente(t16);
		t17.addAdjacente(t8);
		t17.addAdjacente(t13);
		t18.addAdjacente(o1);
		t18.addAdjacente(t16);
		t18.addAdjacente(t8);
		t19.addAdjacente(t15);
		t19.addAdjacente(t9);
		t19.addAdjacente(t8);
		
		//Oceania
		
		o1.addAdjacente(o2);
		o1.addAdjacente(t18);
		o1.addAdjacente(o3);
		o1.addAdjacente(o4);
		o2.addAdjacente(o1);
		o2.addAdjacente(o3);
		o2.addAdjacente(o4);
		o3.addAdjacente(o1);
		o3.addAdjacente(o4);
		o4.addAdjacente(o2);
		o4.addAdjacente(o3);
		
		//Africa
		
		af1.addAdjacente(t1);
		af1.addAdjacente(af2);
		//af1.addAdjacente(as2);   //america do sul
		af1.addAdjacente(af4);
		af1.addAdjacente(af3);
		af2.addAdjacente(t3);
		af2.addAdjacente(t14);
		af2.addAdjacente(af3);
		af2.addAdjacente(af1);
		af3.addAdjacente(t14);
		af3.addAdjacente(af2);
		af3.addAdjacente(af1);
		af3.addAdjacente(af4);
		af3.addAdjacente(af5);
		af3.addAdjacente(af6);
		af4.addAdjacente(af1);
		af4.addAdjacente(af3);
		af4.addAdjacente(af5);
		af4.addAdjacente(af6);
		af5.addAdjacente(af6);
		af5.addAdjacente(af4);
		af5.addAdjacente(af3);
		af6.addAdjacente(af5);
		af6.addAdjacente(af3);
		
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
		territorios.add(o1);
		territorios.add(o2);
		territorios.add(o3);
		territorios.add(o4);
		territorios.add(af1);
		territorios.add(af2);
		territorios.add(af3);
		territorios.add(af4);
		territorios.add(af5);
		territorios.add(af6);
			
	}
	
	public Territorio getTerritorio(int x){
		
		return territorios.get(x);
	}	
	
	public boolean territorioOcupado(int x){
		return territorios.get(x).isOcupado();
	}
	
	public void ocuparTerritorio(int x, String agente){
		territorios.get(x).setOcupado(true);
		territorios.get(x).setOcupante(agente);
	}
	
	public ArrayList<Integer> getTerritoriosPorAgente(String agente){
		
		ArrayList<Integer> x= new ArrayList<Integer>();

			for(int i=0;i< territorios.size();i++){
				if(territorios.get(i).getOcupante()!=null){
				if(territorios.get(i).getOcupante().equals(agente))
					x.add(i);
				}
			}				
		return x;		
	}	
	
	public int getNumTerritorios(){
		return territorios.size();
	}

}
