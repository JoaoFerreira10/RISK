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
	
	int soldadosDefensor = 0, soldadosAtacante = 0;
	private void doAtack(TabuleiroLogica tabuleiro, BoardController b, String defensor, String atacante){
		
		ArrayList<Integer> dadosDefensor = new ArrayList<Integer>();
		ArrayList<Integer> dadosAtacante = new ArrayList<Integer>();
		
		int ataqueMaisAlto=0, defesaMaisAlto=0, idTerritorioDefensor=0, idTerritorioAtacante=0;
		
		String nomeAtacante = null;
		String nomeDefensor = null;
		String vencedor = null;
		
		//do{
		
		for (int i = 0; i < tabuleiro.getNumTerritorios(); i++) {
			
		String territorio = tabuleiro.getTerritorio(i).getNome();		
		
			if(territorio.equals(defensor)){
				soldadosDefensor= tabuleiro.getTerritorio(i).getpecas();
				System.out.println("SOLDADOS DEFENSOR+++++++++++++: "+soldadosDefensor);

				idTerritorioDefensor=i;
				
				
				nomeDefensor = tabuleiro.getTerritorio(i).getOcupante();
				System.out.println("NOME DEFENSOR+++++++++++++++++: "+nomeDefensor);
				
			}else if(territorio.equals(atacante)){
				soldadosAtacante = tabuleiro.getTerritorio(i).getpecas();
				System.out.println("SOLDADOS ATACANTE+++++++++++++: "+soldadosAtacante);
				
				idTerritorioAtacante=i;
				
				nomeAtacante = tabuleiro.getTerritorio(i).getOcupante();
				System.out.println("NOME ATACANTE+++++++++++++++++: "+nomeAtacante);
			}	
		//b.preencherTabuleiro(serAtacado, agente);   // ganha o agente que ataca	
		}
		
		
		outerloop:
			do{
				
			
			dadosDefensor= dadosDefensor(soldadosDefensor); // no maximo 2 dados
			dadosAtacante= dadosAtacante(soldadosAtacante);  // no maximo 3 dados
		 
			int a = dadosAtacante.size();
			int aa = dadosDefensor.size();
			int menorDados;
			
			if(a<aa)
				menorDados = a;
			else
				menorDados = aa;
			
			for(int i=0; i< menorDados; i++){
				
			
			
			
		 ataqueMaisAlto = dadosAtacante.get(i);
		 defesaMaisAlto = dadosDefensor.get(i);
		
		if(ataqueMaisAlto>defesaMaisAlto){
			soldadosDefensor--;
			
			tabuleiro.getTerritorio(idTerritorioDefensor).removerpecas(1);
			
			
			System.out.println("PERDEU O DEFENSOR: "+soldadosDefensor);
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	b.colocarPecaTabuleiro(defensor, soldadosDefensor);
			    	
			    }
			});
			
			
			//b.preencherTabuleiro(atacar, nomeAtacante);
		}
		
		else if (defesaMaisAlto>ataqueMaisAlto){
			
			
			
			System.out.println("ATACANTE FICOU COM UM TROPA E DESISTIU DO ATAQUE");
			
			
			tabuleiro.getTerritorio(idTerritorioAtacante).removerpecas(1);
			soldadosAtacante--;
			
			if(soldadosAtacante==1){
			
			System.out.println("PERDEU O ATACANTE, FICOU COM UM: "+soldadosAtacante + " TROPA");
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);
			    	
			    }
			});
			}
			//if(soldadosAtaque==0)
			//b.preencherTabuleiro(defender, nomeDefensivo);
			
		}
			if(tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==1 || 
					tabuleiro.getTerritorio(idTerritorioDefensor).getpecas()==0 )
			{
				
				if(tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==0){
					
					
					vencedor = nomeDefensor;
					tabuleiro.getTerritorio(idTerritorioAtacante).setOcupante(vencedor);
					
					b.preencherTabuleiro(atacante, vencedor);
			
					
					// ** NOVO *** 
					
					if (tabuleiro.getTerritorio(idTerritorioDefensor).getpecas()==1){
										System.out.println("SO TEM UMA PEÇA ---------------------");	
								
										
						/*if(tabuleiro.getTerritorio(tabuleiro.getTerritoriosPorAgente(nomeDefensor.getAdjacentes().get(y).getOcupante().equals(getCor()))
										
										// vai buscar os adjacentes iguais a ele
										System.out.println(tabuleiro.getTerritorio(idTerritorioDefensor).getAdjacentes().get(idTerritorioDefensor).getNome());
										int y2 = tabuleiro.getTerritorio(idTerritorioDefensor).getAdjacentes().size();
										for (int j = 0; j <y2; j++){
											
											int pecas= tabuleiro.getTerritorio(y2).getpecas();
											System.out.println("PECAAS DOS ADJACENTES"+pecas);
										}*/
											
						
											
											}
					
					
					if (tabuleiro.getTerritorio(idTerritorioDefensor).getpecas()==2){
						System.out.println("ENVIAR 1 TROPA!!");
					tabuleiro.getTerritorio(idTerritorioAtacante).addpecas(1);     // ao ganhar poe uma peça no territorio que ficou vazio **NOVO 
					soldadosAtacante++;
					
					tabuleiro.getTerritorio(idTerritorioDefensor).removerpecas(1);     // e retira uma peça do territorio que ganhou **NOVO
					soldadosDefensor--;
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(defensor, soldadosDefensor);  // tira peça do territorio vencedor ** NOVO
					    	
					    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);   
					    }
					});
					
					}
					
					else if (tabuleiro.getTerritorio(idTerritorioDefensor).getpecas()>2){
						System.out.println("ENVIAR 2 TROPAS!!");
						tabuleiro.getTerritorio(idTerritorioAtacante).addpecas(2);     // ao ganhar poe uma peça no territorio que ficou vazio **NOVO 
						soldadosAtacante++;
						soldadosAtacante++;
						
						tabuleiro.getTerritorio(idTerritorioDefensor).removerpecas(2);     // e retira uma peça do territorio que ganhou **NOVO
						soldadosDefensor--;
						soldadosDefensor--;
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	b.colocarPecaTabuleiro(defensor, soldadosDefensor);  // tira peça do outro territorio ** NOVO
						    	
						    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);   
						    }
						});
						
						}
					
					}
				
				else if (tabuleiro.getTerritorio(idTerritorioDefensor).getpecas()==0){  // se o terr. defensor chegar a 0 peças entao ganhou o atacante
					vencedor = nomeAtacante;   // atacante vencedor
					tabuleiro.getTerritorio(idTerritorioDefensor).setOcupante(vencedor);   // troca o territorio defendido pela cor do atacante vencedor
					
					b.preencherTabuleiro(defensor, vencedor); // vencedor

					
					// ** NOVO *** 
					
					if (tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==1){
						System.out.println("SO TEM UMA PEÇA ---------------------");
						
						// vai buscar os adjacentes iguais a ele
						/*System.out.println(tabuleiro.getTerritorio(idTerritorioAtacante).getAdjacentes());
						if(tabuleiro.getTerritorio(idTerritorioAtacante).getAdjacentes().equals(idTerritorioAtacante){						
							System.out.println("NUMERO ADJACENTES: "+y2);
						}
						(int j = 0; j <y2; j++){
							
							int pecas= tabuleiro.getTerritorio(j).getpecas();
							System.out.println("PECAAS DOS ADJACENTES"+pecas);
						}*/
						
					}
					
					
					if (tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()==2){
					
						System.out.println("ENVIAR 1 TROPA!!");
					
					tabuleiro.getTerritorio(idTerritorioDefensor).addpecas(1);
					soldadosDefensor++;
					
					tabuleiro.getTerritorio(idTerritorioAtacante).removerpecas(1);     // e retira uma peça do territorio que ganhou **NOVO
					soldadosAtacante--;
					
				
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);   // retira um soldado do vencedor e poe no novo territorio
					    	b.colocarPecaTabuleiro(defensor, soldadosDefensor);
					    }
					});
					}
					
					else if (tabuleiro.getTerritorio(idTerritorioAtacante).getpecas()>2){
						
						System.out.println("ENVIAR 2 TROPAS!!");
						
						tabuleiro.getTerritorio(idTerritorioDefensor).addpecas(2);
						soldadosDefensor++;
						soldadosDefensor++;
						tabuleiro.getTerritorio(idTerritorioAtacante).removerpecas(2);     // e retira uma peça do territorio que ganhou **NOVO
						soldadosAtacante--;
						soldadosAtacante--;
					
						
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	b.colocarPecaTabuleiro(atacante, soldadosAtacante);   // retira um soldado do vencedor e poe no novo territorio
						    	b.colocarPecaTabuleiro(defensor, soldadosDefensor);
						    }
						});
						}
				
				}
				
				break outerloop;
			}
		
			}
			
			}while(true);
		
		
		System.out.println("VENCEDOR!!!! ++++++++++++: "+vencedor);
		
	}
	
	
	
	public ArrayList<Integer> dadosAtacante(int numSoldados){ 
		ArrayList<Integer> dadosAtacante = new ArrayList<Integer>();
		// se ficar com 1 soldado no ataque, é terminado o ataque
		if(numSoldados==2){
			int x = (int) (Math.random()*6)+1;
			dadosAtacante.add(x);
			System.out.println("dados atacante: "+dadosAtacante);
		}
		else if(numSoldados==3){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosAtacante.add(x);
			dadosAtacante.add(y);
			Collections.sort(dadosAtacante, Collections.reverseOrder());
			System.out.println("dados atacante: "+dadosAtacante);
			
		}
		else if(numSoldados>=4){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			int z = (int) (Math.random()*6)+1;
			dadosAtacante.add(x);
			dadosAtacante.add(y);
			dadosAtacante.add(z);
			Collections.sort(dadosAtacante, Collections.reverseOrder());
			System.out.println("dados atacante: "+dadosAtacante);
		}
		
		return dadosAtacante;
			
	}
	
	public ArrayList<Integer> dadosDefensor(int numSoldados){
		ArrayList<Integer> dadosDefensor = new ArrayList<Integer>();
	
		if(numSoldados==1){
			int x = (int) (Math.random()*6)+1;
			dadosDefensor.add(x);
			System.out.println("dados defensor: "+dadosDefensor);
			//return dadosSerAtacado;
		}
		else if(numSoldados>=2){
			int x = (int) (Math.random()*6)+1;
			int y = (int) (Math.random()*6)+1;
			dadosDefensor.add(x);
			dadosDefensor.add(y);
			Collections.sort(dadosDefensor, Collections.reverseOrder());
			System.out.println("dados defensor: "+dadosDefensor);
			
		}
		return dadosDefensor;
			
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
