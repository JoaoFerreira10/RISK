package agentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
	
	String red = Singleton.getInstance().getVermelho(); 
	String green = Singleton.getInstance().getVerde(); 
	String blue = Singleton.getInstance().getAzul(); 
	String yellow = Singleton.getInstance().getAmarelo(); 
	
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
		   System.out.println("*** NOVA RONDA ***\n");
		   System.out.println("coordenador: agente "+aclMessage.getContent());
		   this.send(aclMessage);
		}
	
	/*
	 * Recebe as tropas e o territorio de um agente e as distribui pelo territorio respetivo
	 */
	private void sendTroops(TabuleiroLogica tabuleiro, BoardController b, String nomeTerritorio, String qnt){
		
		//conversao de qtd para int
		Integer x= Integer.parseInt(qnt);

		
		for (int j = 0; j < tabuleiro.getNumTerritorios(); j++) {
			
				if(tabuleiro.getTerritorio(j).getNome().equals(nomeTerritorio)){
					
					tabuleiro.getTerritorio(j).addpecas(x);
					int nPecas=tabuleiro.getTerritorio(j).getpecas();
					
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							b.colocarPecaTabuleiro(nomeTerritorio, nPecas);				
						}

					});
					
					break;
				}
		}
	
	}
	
	private void receiveOrderTroops(){
		
		ACLMessage msg = blockingReceive();
		String mensagem= msg.getContent().toString();   // ATAQUE: e1-e2
	    String mensagemFinal = mensagem.substring(7, mensagem.length());
		
		String[] parts = mensagemFinal.split("-");
		
		String ataque = parts[0]; // e1
		String defesa = parts[1]; // e2
		
		//Mensagem do agente a pedir para distribuir tropas
		if(msg.getContent().contains("TROPAS")){
			
			ACLMessage reply = msg.createReply();
			
			//System.out.println("territorio: "+ataque + " || tropas a colocar: "+defesa);
			sendTroops(tabuleiro,controlador,ataque,defesa);
			reply.setContent("tropas inseridas. Permissao para atacar");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
			
			
		}else{
			
			System.out.println("nao recebeu ordem para distribuir tropas");
		}
	}
	
	
	
 	private void receiveOrder(){

		ACLMessage msg = blockingReceive();
	
		//mensagem do agente a pedir para efetuar um ataque
		 if(msg.getContent().contains("ATAQUE")){
			 
			 
				String mensagem= msg.getContent().toString();   // ATAQUE: e1-e2
			    String mensagemFinal = mensagem.substring(7, mensagem.length());
				String[] parts = mensagemFinal.split("-");
				
				String ataque = parts[0]; // e1
				String defesa = parts[1]; // e2
			
			System.out.println("ataque: "+ataque + " || defesa: "+defesa);
			
			ACLMessage reply = msg.createReply();
			
			//parser para decifrar mensagem com territorio e territorio pa atque
			doAtack(tabuleiro,controlador,ataque,defesa);
			reply.setContent("ataque efetuado -> jogada concluída");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
		}

		 else if(msg.getContent().contains("Nao vou atacar")){

				ACLMessage reply = msg.createReply();
				reply.setContent("ataque efetuado -> jogada concluída");
				send(reply);
				System.out.println("coordenador: vai passar a vez");
			}
			else{
				System.out.println("sda");
				ACLMessage reply = msg.createReply();
				reply.setContent("ataque efetuado -> jogada concluída");
				send(reply);
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
		

		
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
			
			String territorio = tabuleiro.getTerritorio(i).getNome();		
		
				if(territorio.equals(atacar)){
					
					soldadosAtaque= tabuleiro.getTerritorio(i).getpecas();
					System.out.println("Soldados do atacante: "+soldadosAtaque);
	
					idTerritorioAtaque=i;
					
					
					nomeAtacante = tabuleiro.getTerritorio(i).getOcupante();
					
				}else if(territorio.equals(defender)){
					
					soldadosDefesa = tabuleiro.getTerritorio(i).getpecas();
					System.out.println("Soldados do defensor: "+soldadosDefesa);
					
					idTerritorioDefensivo=i;
					
					nomeDefensivo = tabuleiro.getTerritorio(i).getOcupante();
					
				}	
		
		}
		
		
		outerloop:
			do{
				
			dadosAtaque= dadosAtaque(soldadosAtaque);
			dadosDefesa= dadosSerAtacado(soldadosDefesa);
			
			
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
					
					System.out.println("Perdeu um soldado defensor: "+soldadosDefesa);
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
					
					System.out.println("Perdeu um soldado atacante: "+soldadosAtaque);
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(atacar, soldadosAtaque);
					    	
					    }
					});
					
					//if(soldadosAtaque==0)
					//b.preencherTabuleiro(defender, nomeDefensivo);
					
				}
						
			}
			
			//este if saiu do ciclo for
			if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==1 || // aqui passou getpeca==1 porque o atacante nao pode atacar so com 1 soldado
			
					tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==0 )
			{
				
				//Se so tiver 1 peca no territorio que esta a atacar, nao podera atacar mais
					if(tabuleiro.getTerritorio(idTerritorioAtaque).getpecas()==1){
						
						System.out.println("Terminou o ataque, numero de soldados = 1");
						
						}
				//se territorio que esta a defender ficar sem trocas, o atacante conquistou o territorio
				else if (tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==0){
					
					vencedor = nomeAtacante;
					tabuleiro.getTerritorio(idTerritorioDefensivo).setOcupante(vencedor);
					System.out.println("Venceu o ataque. Territorio conquistado!");
					b.preencherTabuleiro(defender, vencedor);
				
					
					// ** NOVO *** 
					
					tabuleiro.getTerritorio(idTerritorioDefensivo).addpecas(1);
					soldadosDefesa++;
					
					tabuleiro.getTerritorio(idTerritorioAtaque).removerpecas(1);     // e retira uma peï¿½a do territorio que ganhou **NOVO
					soldadosAtaque--;
					
					if (tabuleiro.getTerritorio(idTerritorioDefensivo).getpecas()==1){
					
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
		   //AID r = new AID ("red@MyMainPlatform", AID.ISGUID);
		   
		ACLMessage msg = blockingReceive();
		
		if(msg!=null){
						
			/*ACLMessage reply = msg.createReply();
			reply.setContent("pong");
			send(reply);
			System.out.println("coordenador envia -->" +reply.getContent());*/
			
				if(msg.getContent().equals("red")){
					if(Singleton.getInstance().isGreenAlive()){
						sendMessage("green@Risk");
					}else if(Singleton.getInstance().isBlueAlive()){
						sendMessage("blue@Risk");
					}else if(Singleton.getInstance().isYellowAlive()){
						sendMessage("yellow@Risk");
					}else{
						endGame("red");
					}					
				}
				else if(msg.getContent().equals("green")){

					 if(Singleton.getInstance().isBlueAlive()){
						sendMessage("blue@Risk");
					}else if(Singleton.getInstance().isYellowAlive()){
						sendMessage("yellow@Risk");
					}else if(Singleton.getInstance().isRedAlive()){
						sendMessage("red@Risk");
					}else{
						endGame("green");
					}
				}
				else if(msg.getContent().equals("blue")){
					
					 if(Singleton.getInstance().isYellowAlive()){
							sendMessage("yellow@Risk");
					}else if(Singleton.getInstance().isRedAlive()){
						sendMessage("red@Risk");
					}else if(Singleton.getInstance().isGreenAlive()){
						sendMessage("green@Risk");
					}else{
						endGame("blue");
					}
				}
				else if(msg.getContent().equals("yellow")){
					
					if(Singleton.getInstance().isRedAlive()){
						sendMessage("red@Risk");
					}else if(Singleton.getInstance().isGreenAlive()){
						sendMessage("green@Risk");
					}else if(Singleton.getInstance().isBlueAlive()){
						sendMessage("blue@Risk");
					}else{
						endGame("yellow");
					}
				}
			
		}else{
			System.out.println("nao recebeu");
		}

		
	}
	
	public void endGame(String agente){
		Scanner scaner = new Scanner(System.in);
		System.out.println("\n\n**** AGENTE "+agente+" GANHOU ****\n");
		System.out.println("Prima qualquer tecla para fechar o jogo");
		

		
		Platform.runLater(new Runnable() {

			@Override
			public void run() 
			{
				if(agente.equals("red"))
				controlador.vencedor.setText(" "+agente + " "+red);
				else if(agente.equals("yellow"))
				controlador.vencedor.setText(" "+agente + " "+yellow);
				else if(agente.equals("green"))
					controlador.vencedor.setText(" "+agente + " "+green);
				else if(agente.equals("blue"))
					controlador.vencedor.setText(" "+agente + " "+blue);
				
				
				
				controlador.placarVencedor.setVisible(true);
				controlador.btn_jogar.setDisable(true);
				
			}
		});
		
		
		if(Singleton.getInstance().isGreenAlive()==false){
			controlador.grT.setText(" X ");
			
		}
		else if(Singleton.getInstance().isYellowAlive()==false){
			controlador.ywT.setText(" X ");
			
		}
		else if(Singleton.getInstance().isRedAlive()==false){
			controlador.vmT.setText(" X ");
			
		}
		else if(Singleton.getInstance().isBlueAlive()==false){
			controlador.blT.setText(" X ");
			}
		
		
		
		
		 scaner.next();
		 System.exit(0);
		 

		 
	}
	
 
	public void setup(){
		
		 System.out.println("primeiro: " + primeiro);
		 testeCoordenador x= new testeCoordenador(this);
		 
		
		 addBehaviour(x);
		 sendMessage(primeiro+"@Risk");   // envia mensagem para o primeiro a jogar
	 }


	public class testeCoordenador extends TickerBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int a=0;
		public testeCoordenador(Agent a) {

			super(a, 10);
		
		}


		@Override
		protected void onTick() {
			
			receiveOrderTroops();
			receiveOrder();
			receiveMessage();
			
			
			
		}
		
	}
	
		
}
