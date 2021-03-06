package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class DadosController implements Initializable{
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		comboVermelho.setItems(list);
		comboVermelho.getSelectionModel().select(0);
		comboVerde.setItems(list);
		comboVerde.getSelectionModel().select(0);
		comboAmarelo.setItems(list);
		comboAmarelo.getSelectionModel().select(0);
		comboAzul.setItems(list);
		comboAzul.getSelectionModel().select(0);
		
		//dados.setDisable(true);
		
	}
	
	
	@FXML
	public void goExit(ActionEvent av){
		Stage stage= (Stage) ( (Node) av.getSource()).getScene().getWindow();
	    stage.close();
	}
	
	//@FXML
	//private ChoiceBox agente1, agente2, agente3, agente4;
	
	@FXML private Label red;
	@FXML
	private Label green;
	@FXML
	private Label blue;
	@FXML
	private Label yellow;
	@FXML
	private Label lblV;
	@FXML private Button dados, avancar;
	@FXML private ImageView dado, dado2;
	
	@FXML private ComboBox<String> comboVermelho, comboVerde, comboAmarelo, comboAzul;
	
	
	@FXML
	public void selecionarAgente(){
	
	}
	
	ObservableList<String> list = FXCollections.observableArrayList(
			"Aleatorio","Atacante"
			);
	
	
	@FXML
	public void goLancarDados(ActionEvent e){
		
		comboAmarelo.setDisable(true);
		comboAzul.setDisable(true);
		comboVerde.setDisable(true);
		comboVermelho.setDisable(true);
		
		dado.setVisible(true);
		dado2.setVisible(false);
		    
	    int START = 1;
	    int END = 12;
	    Random random = new Random();

	    Integer value1= showRandomInteger(START, END, random);
	    Integer value2= showRandomInteger(START, END, random);
	    Integer value3= showRandomInteger(START, END, random);
	    Integer value4= showRandomInteger(START, END, random);
	    
	    red.setText(value1.toString());
	    green.setText(value2.toString());
	    blue.setText(value3.toString());
	    yellow.setText(value4.toString());
	      
	    
	    Hashtable<String, Integer> numbers  = new Hashtable<String, Integer>();
	    numbers.put(red.getId(), value1);
	    numbers.put(green.getId(), value2);
	    numbers.put(blue.getId(), value3);
	    numbers.put(yellow.getId(), value4);
	    
	   
	    
	    
	    List<Entry<String, Integer>> greatest = findGreatest(numbers, 4);
	    for (Entry<String, Integer> entry : greatest)
        {
            
        }
	     int maplength = greatest.size();
	     @SuppressWarnings("unchecked")
		Entry<String,Integer>[] test = new Entry[maplength];
	    greatest.toArray(test);
	 
	    
	    if(test[3].getValue() == test[2].getValue()){
	    	 log("EMPATE ENTRE:"+test[2].getKey() + " E "+test[3].getKey());
	    	Random random2 = new Random();
        	Integer valueN1= showRandomInteger(1, 6, random2);
     	    Integer valueN2= showRandomInteger(1, 6, random2);
     	    numbers.put(test[2].getKey(), valueN1);
     	    numbers.put(test[3].getKey(), valueN2);
     
     	    for (Entry<String, Integer> entry : greatest)
           {
               log("depois empate:"+entry); }
     	   lblV.setText(test[3].toString());
	    }
	   
	    Integer maxValueInMap=(Collections.max(numbers.values()));
	    
	    
	    if(test[3].getValue()==maxValueInMap){
	    	lblV.setText(test[3].getKey().toString());}
	    else if (test[2].getValue()==maxValueInMap){
	    	lblV.setText(test[2].getKey().toString());}
	    
	    System.out.println("Primeiro a jogar: "+lblV.getText());
	    Singleton.getInstance().setPrimeiroJogar(lblV.getText());   //guardar o valor do vencedor para usar depois no jogo
	   
	   
	       dados.setDisable(true);
	       avancar.setDisable(false);
	    
	    	   
	    	
	}
	public String label;
	
	public void setLabel(String label) {
        this.label = label;
     }

	public String getLabel() {
        return label;
     }
	
	private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
	    if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);    
	    
	    return randomNumber;
	  }
	  
	  private static void log(String aMessage){
	    System.out.println(aMessage);
	  }

	  
	  
	  @FXML
		public void goToBoard(ActionEvent av) throws IOException{
		  
		  
		  	Singleton.getInstance().setVermelho(comboVermelho.getValue());
			Singleton.getInstance().setVerde(comboVerde.getValue());
			Singleton.getInstance().setAzul(comboAzul.getValue());
			Singleton.getInstance().setAmarelo(comboAmarelo.getValue());
			
			Parent tabuleiro= FXMLLoader.load(getClass().getResource("Tabuleiro.fxml"));
			Scene tabuleiro_scene= new Scene(tabuleiro);
			Stage app_stage= (Stage) ( (Node) av.getSource()).getScene().getWindow();
			app_stage.setResizable(false);
			app_stage.setTitle("RISK");
			app_stage.setScene(tabuleiro_scene);
			app_stage.show();
			
		}
	  
	  private static <K, V extends Comparable<? super V>> List<Entry<K, V>> findGreatest(Map<K, V> map, int n)
  {
      Comparator<? super Entry<K, V>> comparator = 
          new Comparator<Entry<K, V>>()
      {
          @Override
          public int compare(Entry<K, V> e0, Entry<K, V> e1)
          {
              V v0 = e0.getValue();
              V v1 = e1.getValue();
              return v0.compareTo(v1);
          }
      };
      PriorityQueue<Entry<K, V>> highest = 
          new PriorityQueue<Entry<K,V>>(n, comparator);
      for (Entry<K, V> entry : map.entrySet())
      {
          highest.offer(entry);
          while (highest.size() > n)
          {
              highest.poll();
          }
      }

      List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
      while (highest.size() > 0)
      {
          result.add(highest.poll());
      }
      return result;
  }

	
}
