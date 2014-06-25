package agentville.games.wumpus.simplehunter.behaviours;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import agentville.games.wumpus.simplehunter.HunterAgent;
import agentville.games.wumpus.world.WumpusPerception;
import agentville.games.wumpus.world.WumpusPerceptionOntology;

public class PlayGameBehaviour extends SimpleBehaviour {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getMyLogger(this.getClass().getName());
	
	private int transition = GameBehaviour.TRANS_GAME_OVER;

	private WumpusPerception perception = null;
	private Ontology ontology = null;
	private ContentManager man = null;
	private SLCodec codec = null;
	private ACLMessage reply;

	public void onStart() {

		ontology = WumpusPerceptionOntology.getInstance();
		
		man = myAgent.getContentManager();
		codec = new SLCodec();
		man.registerOntology(ontology);
		man.registerLanguage(codec);
	}
	
	public void action() {
		
		do {
			ACLMessage msgPerception = myAgent.blockingReceive();
//	    	if(log.isLoggable(Logger.WARNING))
//				try {
//					log.log(Logger.WARNING, "Nachricht empfangen: " + ((WumpusPerception) msgPerception.getContentObject()).toString());
//				} catch (UnreadableException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			
	    	if (msgPerception.getPerformative() == ACLMessage.INFORM){
	    		
	    		perception = new WumpusPerception(msgPerception.getContent());
	    		
//				ContentElement content = null;
//				try {
//			    	content = man.extractContent(msgPerception);
//			    	if(log.isLoggable(Logger.WARNING))
//			    		log.log(Logger.WARNING, "Content extrahiert");
//					perception = (WumpusPerception) content;
//				} catch (UngroundedException e) {
//			    	if(log.isLoggable(Logger.WARNING))
//			    		log.log(Logger.WARNING, "Ungrounded Exception: " + e.getMessage());
//					throw new RuntimeException(e);
//				} catch (CodecException e) {
//			    	if(log.isLoggable(Logger.WARNING))
//			    		log.log(Logger.WARNING, "Codec Exception: " + e.getMessage());
//					throw new RuntimeException(e);
//				} catch (OntologyException e) {
//			    	if(log.isLoggable(Logger.WARNING))
//			    		log.log(Logger.WARNING, "Ontology Exception: " + e.getMessage());
//					throw new RuntimeException(e);
//				}
		    	if(log.isLoggable(Logger.WARNING))
		    		log.log(Logger.WARNING, "Hunter-Perception: " + perception.toString());
	    	} else {
		    	if(log.isLoggable(Logger.WARNING)) {
		    		log.log(Logger.WARNING, "Keine INFORM-Message, sondern: " + msgPerception.getPerformative());
		    		log.log(Logger.WARNING, "(REFUSE wäre: " + ACLMessage.REFUSE + ")");
		    	}
		    	break;
		    }
	    	
			String nextAction = null;
			nextAction = ((HunterAgent) myAgent).processPerceptionAndGetNextStep(perception);

			reply = msgPerception.createReply();
			reply.setPerformative(ACLMessage.REQUEST);
			reply.setContent(nextAction);
			myAgent.send(reply);
			
			
		} while (true);
		
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//		msg.setContent(WumpusConsts.ACTION_DEREGISTER);
//		myAgent.send(msg);
	}

	public int onEnd() {

		return transition; 
	}

	public boolean done() {
		return true;
	}
}
