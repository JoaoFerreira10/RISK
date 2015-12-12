package agentes;

import java.util.ArrayList;
import java.util.Collections;

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
		   aclMessage.setContent(id+" tem permissao para jogar");
		   
		   System.out.println("coordenador: agente "+aclMessage.getContent());
		   this.send(aclMessage);
		}
	
	
	private void receiveOrderAtack(){
		ACLMessage msg = blockingReceive();
		String mensagem= msg.getContent().toString();   // ATAQUE: e1-e2

		String mensagemFinal = mensagem.substring(7, mensagem.length());
		
		String[] parts = mensagemFinal.split("-");
		String ataque = parts[0]; // e1
		String serAtacado = parts[1]; // e2
		
		System.out.println("--------> aquaque: "+ataque + "ser atacado: "+serAtacado);
		
		if(msg.getContent().contains("ATAQUE")){
	
			ACLMessage reply = msg.createReply();
			
			//parser para decifrar mensagem com territorio e territorio pa atque
			doAtack(tabuleiro,controlador,ataque,serAtacado);
			reply.setContent("ataque efetuado");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
		}else{
			System.out.println("nao entrou");
		}
	
	}
	
	private void doAtack(TabuleiroLogica tabuleiro, BoardController b, String atacar, String serAtacado){
		
		ArrayList<Integer> dadosAtaque = new ArrayList<Integer>();
		ArrayList<Integer> dadosAtacado = new ArrayList<Integer>();
		
		int soldadosAtaque = 0, soldadosAtacado = 0, ataqueMaisAlto=0, atacadoMaisAlto=0, idTerritorioAtaque=0, idTerritorioDefensivo=0;
		String agenteAtacar = null;
		String agenteDefender = null;
		String vencedor=null;
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
			
		String territorio = tabuleiro.getTerritorio(i).getNome();		
		
			if(territorio.equals(atacar)){
				idTerritorioAtaque=i;
				soldadosAtaque= tabuleiro.getTerritorio(i).getpecas();				
				agenteAtacar = tabuleiro.getTerritorio(i).getOcupante();
				
			}else if(territorio.equals(serAtacado)){
				idTerritorioDefensivo=i;
				soldadosAtacado = tabuleiro.getTerritorio(i).getpecas();
				agenteDefender = tabuleiro.getTerritorio(i).getOcupante();
			}
				
		}


outerloop:
	do{	
			dadosAtaque= dadosAtaque(soldadosAtaque);
			dadosAtacado= dadosSerAtacado(soldadosAtacado);
		
			int a = dadosAtaque.size();
			int aa = dadosAtacado.size();
			int menorDados;
			
			
			
			if(a<=aa)
				menorDados = a;
			else
				menorDados = aa;
		
			
		for (int i = 0; i < menorDados; i++) {
							
				ataqueMaisAlto = dadosAtaque.get(i);
				atacadoMaisAlto = dadosAtacado.get(i);
				
				
					if(ataqueMaisAlto>atacadoMaisAlto){
						tabuleiro.getTerritorio(idTerritorioDefensivo).removerPecas(1);
						System.out.println("PECAS ATACANTE---------> "+tabuleiro.getTerritorio(idTerritorioAtaque).getpecas());
						soldadosAtacado--;
						System.out.println("soldados atacado"+soldadosAtacado);
						System.out.println(serAtacado+"--------"+soldadosAtacado);
						Integer qq=10;

					}
					else if (atacadoMaisAlto>ataqueMaisAlto){
						
						tabuleiro.getTerritorio(idTerritorioAtaque).removerPecas(1);
						System.out.println("PECAS DEFENSIVO---------> "+tabuleiro.getTerritorio(idTerritorioAtaque).getpecas());
						soldadosAtaque--;
						System.out.println("soldados ataque"+soldadosAtaque);
					}
					
						if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==0 || 
								tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==0){
							
									if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==0){
										vencedor=agenteDefender;	
										tabuleiro.getTerritorio(idTerritorioAtaque).setOcupante(vencedor);
									}else{
										vencedor=agenteAtacar;
										tabuleiro.getTerritorio(idTerritorioDefensivo).setOcupante(vencedor);
									}
							
							break outerloop;
						}
			}
		
		}while(true);
		
		

	}
	
	/*
	 * Dado do jogador que efetua o ataque
	 */
	public ArrayList<Integer> dadosAtaque(int numSoldados){
		ArrayList<Integer> dadosAtaque = new ArrayList<Integer>();
	
		if(numSoldados==1){
			int x = (int) (Math.random()*6)+1;
			dadosAtaque.add(x);
			System.out.println("dados ataque: "+dadosAtaque);
		}
		else if(numSoldados==2){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosAtaque.add(x);
			dadosAtaque.add(y);
			Collections.sort(dadosAtaque, Collections.reverseOrder());
			System.out.println("dados ataque: "+dadosAtaque);
			
		}
		else {
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			int z = (int) (Math.random()*6)+1;
			dadosAtaque.add(x);
			dadosAtaque.add(y);
			dadosAtaque.add(z);
			Collections.sort(dadosAtaque, Collections.reverseOrder());
			System.out.println("dados ataque: "+dadosAtaque);
		}
		
		return dadosAtaque;
			
	}
	
	/*
	 * Dado do jogador a ser atacado
	 */
	public ArrayList<Integer> dadosSerAtacado(int numSoldados){
		ArrayList<Integer> dadosSerAtacado = new ArrayList<Integer>();
	
		if(numSoldados==1){
			int x = (int) (Math.random()*6)+1;
			dadosSerAtacado.add(x);
			System.out.println("dados atacado: "+dadosSerAtacado);
			//return dadosSerAtacado;
		}
		else {
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosSerAtacado.add(x);
			dadosSerAtacado.add(y);
			Collections.sort(dadosSerAtacado, Collections.reverseOrder());
			System.out.println("dados atacado: "+dadosSerAtacado);
			
		}
		return dadosSerAtacado;
			
	}
	
	private void receiveMessage() {
		   //AID r = new AID ("red-Aleatorio@MyMainPlatform", AID.ISGUID);
		   
		ACLMessage msg = blockingReceive();
		
		if(msg!=null){
			
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
			super(a, 1500);
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
