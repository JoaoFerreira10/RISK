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
import javafx.application.Platform;
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
		   System.out.println("\n");
		   System.out.println("coordenador: agente "+aclMessage.getContent());
		   this.send(aclMessage);
		}
	
	
	private void receiveOrderAtack(){
		ACLMessage msg = blockingReceive();
		String mensagem= msg.getContent().toString();   // ATAQUE: e1-e2
		
	

		String mensagemFinal = mensagem.substring(7, mensagem.length());
		
		String[] parts = mensagemFinal.split("-");
		String ataque = parts[0]; // e1
		String defesa = parts[1]; // e2
		
		System.out.println("ataque: "+ataque + " || defesa: "+defesa);
		if(msg.getContent().contains("ATAQUE")){
	 

			ACLMessage reply = msg.createReply();
			
			//parser para decifrar mensagem com territorio e territorio pa atque
			doAtack(tabuleiro,controlador,ataque,defesa);
			reply.setContent("ataque efetuado");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
		}else{
			System.out.println("nao entrou");
		}
		
	}
	
	int soldadosAtaque = 0, soldadosDefesa = 0;
	private void doAtack(TabuleiroLogica tabuleiro, BoardController b, String atacar, String defender){
		
		ArrayList<Integer> dadosAtaque = new ArrayList<Integer>();
		ArrayList<Integer> dadosDefesa = new ArrayList<Integer>();
		
		int ataqueMaisAlto=0, defesaMaisAlto=0, idTerritorioAtaque=0, idTerritorioDefensivo=0;
		
		String nomeAtacante = null;
		String nomeDefensivo = null;
		String vencedor = null;
		
		//do{
		
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
			
		String territorio = tabuleiro.getTerritorio(i).getNome();		
		
			if(territorio.equals(atacar)){
				
				soldadosAtaque= tabuleiro.getTerritorio(i).getpecas();
				System.out.println("SOLDADOS ATAQUE+++++++++++++: "+soldadosAtaque);

				idTerritorioAtaque=i;
				
				
				nomeAtacante = tabuleiro.getTerritorio(i).getOcupante();
				System.out.println("nome atacante: "+nomeAtacante);
			}else if(territorio.equals(defender)){
				
				soldadosDefesa = tabuleiro.getTerritorio(i).getpecas();
				System.out.println("SOLDADOS DEFESA+++++++++++++: "+soldadosDefesa);
				
				idTerritorioDefensivo=i;
				
				nomeDefensivo = tabuleiro.getTerritorio(i).getOcupante();
				System.out.println("nome defensivo: "+nomeDefensivo);
			}	
		//b.preencherTabuleiro(serAtacado, agente);   // ganha o agente que ataca	
		}
		
		
		outerloop:
			do{
				
			dadosAtaque= dadosAtaque(soldadosAtaque);
			dadosDefesa= dadosSerAtacado(soldadosDefesa);
			
				 System.out.println("loooop" +soldadosAtaque +"   "+soldadosDefesa);
				 System.out.println();
			int a = dadosAtaque.size();
			int aa = dadosDefesa.size();
			int menorDados;
			
			if(a<aa)
				menorDados = a;
			else
				menorDados = aa;
			
			if(aa==0){
				System.out.println("erro");
				System.exit(0);
			}
			
			for(int i=0; i< menorDados; i++){
							
			
				ataqueMaisAlto = dadosAtaque.get(i);
				 defesaMaisAlto = dadosDefesa.get(i);
				
				if(ataqueMaisAlto>defesaMaisAlto){
		
					tabuleiro.getTerritorio(idTerritorioDefensivo).removerpecas(1);
					
					soldadosDefesa--;
					System.out.println("PERDEU O DEFENSOR: "+soldadosDefesa);
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(defender, soldadosDefesa);
					    	
					    }
					});
					
					
					//b.preencherTabuleiro(atacar, nomeAtacante);
				}else if (defesaMaisAlto>ataqueMaisAlto){
					
					tabuleiro.getTerritorio(idTerritorioAtaque).removerpecas(1);
					
					soldadosAtaque--;
					
					System.out.println("PERDEU O ATACANTES: "+soldadosAtaque);
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(atacar, soldadosAtaque);
					    	
					    }
					});
					
					//if(soldadosAtaque==0)
					//b.preencherTabuleiro(defender, nomeDefensivo);
					
				}
				System.out.println("pecas ->" +tabuleiro.getTerritorio(idTerritorioAtaque).getpecas());		
			}
			
			//este if saiu do ciclo for
			if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==1 || // aqui passou getpeca==1 porque o atacante nao pode atacar so com 1 soldado
			
					tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==0 )
			{
				
					if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==1){
						
						vencedor = nomeDefensivo;
						tabuleiro.getTerritorio(idTerritorioAtaque).setOcupante(vencedor);
						
						b.preencherTabuleiro(atacar, vencedor);
						System.out.println("TERRITORIO DEFESA+++++++++: "+defender);
						
						// ** NOVO *** 
						
						tabuleiro.getTerritorio(idTerritorioAtaque).addpecas(1);     // ao ganhar poe uma pe�a no territorio que ficou vazio **NOVO 
						soldadosAtaque++;
						
						tabuleiro.getTerritorio(idTerritorioDefensivo).removerpecas(1);     // e retira uma pe�a do territorio que ganhou **NOVO
						soldadosDefesa--;
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	b.colocarPecaTabuleiro(atacar, soldadosAtaque);
						    	
						    	b.colocarPecaTabuleiro(defender, soldadosDefesa);   // tira pe�a do outro territorio ** NOVO
						    }
						});
						}
				
				else if (tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==0){
					
					vencedor = nomeAtacante;
					tabuleiro.getTerritorio(idTerritorioDefensivo).setOcupante(vencedor);
					
					b.preencherTabuleiro(defender, vencedor);
					System.out.println("TERRITORIO ATAQQQ+++++++++: "+atacar);
					
					// ** NOVO *** 
					
					tabuleiro.getTerritorio(idTerritorioDefensivo).addpecas(1);
					soldadosDefesa++;
					
					tabuleiro.getTerritorio(idTerritorioAtaque).removerpecas(1);     // e retira uma pe�a do territorio que ganhou **NOVO
					soldadosAtaque--;
					
					if (tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==1){
						System.out.println("AGORA TEM SO UMA PECA!!!!");
					}
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(defender, soldadosDefesa);   // retira um soldado do vencedor e poe no novo territorio
					    	b.colocarPecaTabuleiro(atacar, soldadosAtaque);
					    }
					});
					}
				
				
				break outerloop;
			}
			
			
			
			}while(true);
		
		
		System.out.println("VENCEDOR!!!! ++++++++++++: "+vencedor);
		
	}
	
	
	
	public ArrayList<Integer> dadosAtaque(int numSoldados){
		ArrayList<Integer> dadosAtaque = new ArrayList<Integer>();

		if(numSoldados==2){
			int x = (int) (Math.random()*6)+1;
			dadosAtaque.add(x);
			System.out.println("dados ataque: "+dadosAtaque);
		}
		else if(numSoldados==3){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosAtaque.add(x);
			dadosAtaque.add(y);
			Collections.sort(dadosAtaque, Collections.reverseOrder());
			System.out.println("dados ataque: "+dadosAtaque);
			
		}
		else if(numSoldados>=4){
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
	
	public ArrayList<Integer> dadosSerAtacado(int numSoldados){
		ArrayList<Integer> dadosDefesa = new ArrayList<Integer>();
	
		if(numSoldados==1){
			int x = (int) (Math.random()*6)+1;
			dadosDefesa.add(x);
			System.out.println("dados defesa: "+dadosDefesa);
			//return dadosSerAtacado;
		}
		else if(numSoldados>=2){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosDefesa.add(x);
			dadosDefesa.add(y);
			Collections.sort(dadosDefesa, Collections.reverseOrder());
			System.out.println("dados defesa: "+dadosDefesa);
			
		}
		return dadosDefesa;
			
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
			super(a, 3000);
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
