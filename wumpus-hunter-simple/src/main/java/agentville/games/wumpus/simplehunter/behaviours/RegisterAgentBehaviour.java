package agentville.games.wumpus.simplehunter.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import agentville.games.wumpus.simplehunter.HunterAgent;
import agentville.games.wumpus.world.WumpusConsts;

/**
 * Nachdem das Spiel durch das vorherige Behaviour gefunden wurde, wird jetzt
 * der Agent erst AB- und dann ANgemeldet. Die Abmeldung erlaubt das mehrmalige
 * Starten des Agenten, ohne immer eine neue Map am Spiel laden zu müssen.
 * 
 * @author Marco Steffens
 *
 */
public class RegisterAgentBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getMyLogger(this.getClass().getName());
	
	private int transition = GameBehaviour.TRANS_AGENT_REGISTERED;

	public RegisterAgentBehaviour(Agent a) {
		super(a);
	}

	public void action() {
    	if(log.isLoggable(Logger.WARNING))
    		log.log(Logger.WARNING, "(de-)register agent...");
		
		ACLMessage msg;

		msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(myAgent.getAID());
		msg.addReceiver(((HunterAgent) myAgent).getGameAgentDescription().getName());
//		/*
//		 * Der Agent wird zunaechst von Spiel AB-...
//		 */
//		msg.setContent(WumpusConsts.ACTION_DEREGISTER);
//		myAgent.send(msg);
		
		/*
		 * ... und dann ANgemeldet.
		 */
		msg.setContent(WumpusConsts.ACTION_REGISTER);
		myAgent.send(msg);
		
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

}
