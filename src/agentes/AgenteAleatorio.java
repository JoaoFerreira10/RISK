package agentes;


import java.util.ArrayList;

import gui.BoardController;
import gui.Singleton;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import logica.TabuleiroLogica;
import logica.Territorio;

import static gui.Singleton.*;

public class AgenteAleatorio extends AgenteRisk {
	
				
	private static final long serialVersionUID = 1L;
	//private ArrayList<Territorio> territorios = new ArrayList<Territorio>();
	
	TabuleiroLogica tabuleiro;
	BoardController board;
	
	
	public AgenteAleatorio(String cor, int pecas, TabuleiroLogica t, BoardController b) {
		super(cor, pecas);
		this.tabuleiro = t;
		this.board = b;
	}
	
	public String getType() {
		return "Aleatorio";
	}

	
	public int escolherTerritorio(){
		
		return (int) (Math.random()*29);	
	}
	
	public void addTerritorio(Territorio t){
		
		//territorios.add(t);
	}
	
	/*
	 * Neste método escolhe um territorio aleatorio da lista de territórios do agente
	 */
	public int distribuirExercito(TabuleiroLogica tabuleiro, int numExercitos){
		
		if(getNumExercitos()-numExercitos<0 || getNumExercitos()==0){
			return 0;
		}
		
		colocarExercitos(numExercitos);
		
		int x = tabuleiro.getTerritoriosPorAgente(this.getCor()).size();
		int y = (int) (Math.random()*x);
		
		return tabuleiro.getTerritoriosPorAgente(this.getCor()).get(y);
	}
	
	protected void setup() {
	//	Object[] args = getArguments();

		System.out.println("aleatorio " + getCor());

		
		testBehaviour t = new testBehaviour(this);
		addBehaviour(t);
				
		
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		
		sd.setType("Aleatorio"); 
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);			

	}
	
	public  ArrayList<Territorio> getTerritories() {
		return tabuleiro.getObjetoTerritorio(getCor());
	}
	
	public boolean play() {
		
		
		System.out.println(getTerritories().get(0).getNome() + " - " + getTerritories().get(0).getpecas());
		
		return false;
	}
	
	
//	private void sendMessage() {
//		   AID r = new AID ("red-Aleatorio@MyMainPlatform", AID.ISGUID);
//		   
//		   r.addAddresses("http://localhost:7778/acc");
//		   ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
//		   aclMessage.addReceiver(r);
//		   aclMessage.setContent("ping");
//		   this.send(aclMessage);
//		}
	

	public class testBehaviour extends SimpleBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int n=0;
		TabuleiroLogica x;
		public testBehaviour(Agent a){
			super(a);
			//x=b;
		}
		
		@Override
		public void action(){



		if (Singleton.getInstance().getPrimeiroJogar().equals(getCor()) 
					&& Singleton.getInstance().getState() == Singleton.GAME_START) {
				
				//sendMessage();
				
				System.out.println("entrou em behaviour-1"+getLocalName()+"------ "+getAID().getName());
		
				Singleton.getInstance().setState(Singleton.GAME_RUNNING);
			}
			else{
	         
			

			ACLMessage msg = receive();
			
			if(msg!=null){
				
				System.out.println(getCor()+" recebeu--> "+msg.getContent()+" de -->" +msg.getSender());
				ACLMessage reply = msg.createReply();
				reply.setContent("pong");
				send(reply);
				System.out.println(getCor() +" envia -->" +reply.getContent());
				
			}else{
				System.out.println("nao recebeu");
			}
			
			}	
		}
		
		@Override
		public boolean done(){
			return false;
		}

	}
	
}
