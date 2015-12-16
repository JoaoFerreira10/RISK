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
				
				String ataque = parts[0]; // alvo
				String defesa = parts[1]; // ataque
			
			System.out.println("ataque: "+ataque + " || defesa: "+defesa);
			
			ACLMessage reply = msg.createReply();
			
			//parser para decifrar mensagem com territorio e territorio pa atque
			doAtack(tabuleiro,controlador,ataque,defesa);
			reply.setContent("ataque efetuado -> jogada conclu�da");
			send(reply);	
			System.out.println("coordenador: " +reply.getContent());
		}

		 else if(msg.getContent().contains("Nao vou atacar")){

				ACLMessage reply = msg.createReply();
				reply.setContent("ataque efetuado -> jogada conclu�da");
				send(reply);
				System.out.println("coordenador: vai passar a vez");
				
			}
			else if(msg.getContent().contains("Passo a vez")){
				System.out.println("sda");
				System.out.println(msg.getContent());
				ACLMessage reply = msg.createReply();
				reply.setContent("ataque efetuado -> jogada conclu�da");
				send(reply);
			}
			else{
				System.out.println("dsdasdsaddasd");
			}
		 
	
	}


	
	int soldadosAlvo = 0, soldadosAtacante = 0;
	String vencedor = null;
	private void doAtack(TabuleiroLogica tabuleiro, BoardController b, String alvo, String atacante){
		
		ArrayList<Integer> dadosAtacante = new ArrayList<Integer>();
		ArrayList<Integer> dadosAlvo = new ArrayList<Integer>();
		
		int atacanteMaisAlto=0, alvoMaisAlto=0, idTerritorioAtacante=0, idTerritorioAlvo=0;
		
		String nomeAtacante = null;
		String nomeAlvo = null;
	
		

		//percorre os territorios todos
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
			
			
			String territorio = tabuleiro.getTerritorio(i).getNome();		
		
				if(territorio.equals(alvo)){
					
					soldadosAlvo= tabuleiro.getTerritorio(i).getpecas();
					System.out.println("Soldados do alvo: "+soldadosAlvo);
	
					idTerritorioAlvo=i;
										
					nomeAlvo = tabuleiro.getTerritorio(i).getOcupante();
					
					
				}else if(territorio.equals(atacante)){
					
					soldadosAtacante = tabuleiro.getTerritorio(i).getpecas();
					System.out.println("Soldados do atacante: "+soldadosAtacante);
					
					idTerritorioAtacante=i;
					
					nomeAtacante = tabuleiro.getTerritorio(i).getOcupante();
					
				}	
		
		}
		
		
		outerloop:
			do{
				
			dadosAtacante= dadosAtacante(soldadosAtacante);
			dadosAlvo= dadosAlvo(soldadosAlvo);
			
			
				 System.out.println();
				 
			int a = dadosAtacante.size();
			int aa = dadosAlvo.size();
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
							
			
				atacanteMaisAlto = dadosAtacante.get(i);
				alvoMaisAlto = dadosAlvo.get(i);
				
				if(atacanteMaisAlto>alvoMaisAlto){
		
					tabuleiro.getTerritorio(idTerritorioAlvo).removerpecas(1);
					
					soldadosAlvo--;
					
					System.out.println("ALVO perdeu um soldado. Total de soldados: "+soldadosAlvo);
					
					Platform.runLater(new Runnable() {// atualizacao da gui
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(alvo, soldadosAlvo);
					    	
					    }
					});
					
					
					//b.preencherTabuleiro(atacar, nomeAtacante);
				}else if (alvoMaisAlto>atacanteMaisAlto){
					
					tabuleiro.getTerritorio(idTerritorioAtacante).removerpecas(1);
					
					soldadosAtacante--;
					
					System.out.println("ATACANTE perdeu um soldado. Total de soldados: "+soldadosAtacante);
					
					Platform.runLater(new Runnable() {//atualizacao da gui
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);
					    	
					    }
					});
					
					
				}
						
			}
			
			//este if saiu do ciclo for
			if(tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==1 || // aqui passou getpeca==1 porque o atacante nao pode atacar so com 1 soldado
			
					tabuleiro.getTerritorio(idTerritorioAlvo).getpecas()==0 )
			{
				
				//Se so tiver 1 peca no territorio que esta a atacar, nao podera atacar mais
					if(tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==1){
						
						System.out.println("RETREAT. Terminou o ataque, numero de soldados = 1");
						
						}
				//se territorio que esta a defender ficar sem trocas, o atacante conquistou o territorio
				else if (tabuleiro.getTerritorio(idTerritorioAlvo).getpecas()==0){
					
					vencedor = nomeAtacante;
					tabuleiro.getTerritorio(idTerritorioAlvo).setOcupante(vencedor);
					System.out.println("Venceu o ataque. Territorio conquistado!!!");
					
					
				System.out.println("passou");
					
					// ** NOVO *** 
					
					tabuleiro.getTerritorio(idTerritorioAlvo).addpecas(1);
					System.out.println("pecas : "+tabuleiro.getTerritorio(idTerritorioAlvo).getpecas());
					soldadosAlvo++;
					
					
					tabuleiro.getTerritorio(idTerritorioAtacante).removerpecas(1);
					System.out.println("pecas atacante : "+tabuleiro.getTerritorio(idTerritorioAtacante).getpecas());// e retira uma pe�a do territorio que ganhou **NOVO
					soldadosAtacante--;
					

							Platform.runLater(new Runnable() {
							    @Override
							    public void run() {
									b.preencherTabuleiro(alvo, vencedor);
							    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);   // retira um soldado do vencedor e poe no novo territorio
							    	b.colocarPecaTabuleiro(alvo, soldadosAlvo);
							    }
							});
					
					}
								
				break outerloop;
			}
						
			
			}while(true);
		
				
	}
	
	
	
	public ArrayList<Integer> dadosAtacante(int numSoldados){
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
	
	public ArrayList<Integer> dadosAlvo(int numSoldados){
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
				   
		ACLMessage msg = blockingReceive();
		
		if(tabuleiro.getTerritoriosPorAgente("red").size()==0){
			Singleton.getInstance().setRedAlive(false);
		}
		if(tabuleiro.getTerritoriosPorAgente("green").size()==0){
			Singleton.getInstance().setGreenAlive(false);
		}
		if(tabuleiro.getTerritoriosPorAgente("blue").size()==0){
			Singleton.getInstance().setBlueAlive(false);
		}
		if(tabuleiro.getTerritoriosPorAgente("yellow").size()==0){
			Singleton.getInstance().setYellowAlive(false);
		}

		
		if(msg!=null){
	
			
			
			
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
