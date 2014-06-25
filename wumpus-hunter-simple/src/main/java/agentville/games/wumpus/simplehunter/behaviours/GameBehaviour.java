package agentville.games.wumpus.simplehunter.behaviours;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

/**
 * Das Finite State Machine Behaviour sorgt für den eigentlichen Ablauf des 
 * Spiels bzw. des Agenten. Es besteht seinerseits wiederrum aus drei Behaviours:
 * - SearchGameBehaviour (TickerBehaviour)
 * - RegisterAgentBehaviour (OneShotBehaviour)
 * - PlayGameBehaviour (SimpleBehaviour)
 * De-registriert wird im RegisterAgentBehaviour vor dem registrieren, da ja im 
 * Spiel nicht de-registriert werden soll und das so also nur für die Testläufe 
 * notwendig ist. Ein De-registrieren ohne vorheriges Registrieren ist problemlos
 * möglich.
 */
public class GameBehaviour extends FSMBehaviour {

	private static final long serialVersionUID = 1L;

	//FSM state values:
	private static String STATE_SEARCH   	= "Search";
	private static String STATE_REGISTER 	= "Register";
	private static String STATE_PLAY 		= "Play";
	
	//FSM transition values:
	protected static int TRANS_GAME_FOUND   	= 0;
	protected static int TRANS_AGENT_REGISTERED = 1;
	protected static int TRANS_GAME_OVER 		= 2;
	
	public GameBehaviour(Agent a) {
		super(a);
		
		//Register states
		registerFirstState(new SearchGameBehaviour(a, 2500), STATE_SEARCH);
		registerState(new RegisterAgentBehaviour(a), STATE_REGISTER);
		registerLastState(new PlayGameBehaviour(), STATE_PLAY);
		
		//Register Transition
		registerTransition(STATE_SEARCH, STATE_REGISTER, TRANS_GAME_FOUND);
		registerTransition(STATE_REGISTER, STATE_PLAY, TRANS_AGENT_REGISTERED);
		
		scheduleFirst();
	}
	
	public void onStart() {

	}
	
	public int onEnd() {
		
//		try {
//			DFService.deregister(myAgent);
//		} catch (FIPAException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return 0;
	}
}
