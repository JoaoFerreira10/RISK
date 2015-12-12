package agentes;

import javax.swing.plaf.TableUI;

import gui.BoardController;
import gui.Singleton;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import logica.TabuleiroLogica;

public class AgenteCoordenador extends Agent{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String primeiro= Singleton.getInstance().getPrimeiroJogar();
	
	TabuleiroLogica tabuleiro;
	BoardController controlador;
	
	public AgenteCoordenador(TabuleiroLogica tabuleiro, BoardController controlador){
		
		this.controlador=controlador;
		this.tabuleiro=tabuleiro;
		
	}
 
 
 
	private void sendMessage(String id) {

		AID r = new AID (id, AID.ISGUID);
		   
		   r.addAddresses("http://localhost:7778/acc");
		   ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		   aclMessage.addReceiver(r);
		   aclMessage.setContent(id+" tem permissão para jogar");
		   
		   System.out.println("coordenador: agente "+aclMessage.getContent());
		   this.send(aclMessage);
		}
	
	private void receiveOrderAtack(){
		ACLMessage msg = blockingReceive();
		
		if(msg.getContent().contains("ATAQUE")){
	
			ACLMessage reply = msg.createReply();
			
			//parser para decifrar mensagem com territorio e territorio pa atque
			doAtack(tabuleiro,controlador,"e1","e2");
			reply.setContent("ataque efetuado");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
		}else{
			System.out.println("nao entrou");
		}
		
	}
	
	private void doAtack(TabuleiroLogica tabuleiro, BoardController b, String atacar, String serAtacado){
		System.out.println("entrou aQUIIIQII");
		String agente = null;
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
		String territorio = tabuleiro.getTerritorio(i).getNome();
		if(territorio.equals(atacar)){
			int soldadosAtaque= tabuleiro.getTerritorio(i).getpecas();
			agente = tabuleiro.getTerritorio(i).getOcupante();
		}else if(territorio.equals(serAtacado)){
			int soldadosAtacado = tabuleiro.getTerritorio(i).getpecas();
			
		}
		System.out.println("ser atacado: "+serAtacado + "atacar: "+agente);
		b.preencherTabuleiro(serAtacado, agente);
		
		}
	}
	
	private void receiveMessage() {
		   //AID r = new AID ("red-Aleatorio@MyMainPlatform", AID.ISGUID);
		   
		ACLMessage msg = blockingReceive();
		
		if(msg!=null){
			
			
			
			/*ACLMessage reply = msg.createReply();
			reply.setContent("pong");
			send(reply);
			System.out.println("coordenador envia -->" +reply.getContent());*/
			
			if(msg.getContent().equals("red")){
				sendMessage("green-Aleatorio@Risk");
			}
			else if(msg.getContent().equals("green")){
				sendMessage("blue-Aleatorio@Risk");
			}
			else if(msg.getContent().equals("blue")){
				sendMessage("yellow-Aleatorio@Risk");
			}
			else if(msg.getContent().equals("yellow")){
				sendMessage("red-Aleatorio@Risk");
			}
			
		}else{
			System.out.println("nao recebeu");
		}
		}
	
 
	public void setup(){
		
		 System.out.println("primeiro: " + primeiro);
		 testeCoordenador x= new testeCoordenador(this);
		 
		
		 addBehaviour(x);
		 sendMessage(primeiro+"-Aleatorio@Risk");   // envia mensagem para o primeiro a jogar
	 }


	public class testeCoordenador extends TickerBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int a=0;
		public testeCoordenador(Agent a) {
			super(a, 4000);
		}

		/*@Override
		public void action() { 
			a++;
			//System.out.println("coordenador: enviar msg");
			receiveOrderAtack();
			receiveMessage();
			
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return a==5;
		}*/

		@Override
		protected void onTick() {
			receiveOrderAtack();
			receiveMessage();
			
			
		}
		
	}
	
	
	
}
