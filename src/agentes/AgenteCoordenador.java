package agentes;

import gui.BoardController;
import gui.Singleton;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import logica.TabuleiroLogica;

public class AgenteCoordenador extends Agent{

	
	String primeiro= Singleton.getInstance().getPrimeiroJogar();
	
	public AgenteCoordenador(TabuleiroLogica tabuleiro, BoardController controlador){
		
		
		
	}
 
 
 
	private void sendMessage() {
		   AID r = new AID ("red-Aleatorio@MyMainPlatform", AID.ISGUID);
		   
		   r.addAddresses("http://localhost:7778/acc");
		   ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		   aclMessage.addReceiver(r);
		   aclMessage.setContent("ping");
		   this.send(aclMessage);
		}
	
 
	public void setup(){
		 
		 testeCoordenador x= new testeCoordenador(this);
		 addBehaviour(x);
	 }
 
 

	public class testeCoordenador extends SimpleBehaviour{
		
		public testeCoordenador(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			System.out.println("enviar msg");
			sendMessage();
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
}
