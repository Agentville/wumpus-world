package agentville.games.wumpus.simplehunter;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;
import agentville.games.wumpus.simplehunter.behaviours.GameBehaviour;
import agentville.games.wumpus.simplehunter.model.World;
import agentville.games.wumpus.world.WumpusConsts;
import agentville.games.wumpus.world.WumpusPerception;


/**
 * @author Marco Steffens
 * @version 0.1
 */
public class HunterAgent extends Agent {

	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getMyLogger(this.getClass().getName());
	
	private World world;
//	private WumpusGUI gui;
	
	DFAgentDescription gameAgent = null;
	AID topic = null;
	
	//Event-Type für das GuiEvent-Objekt
	public static final int EVENT_CLOSE = 0;

//	public static void main(String[] args){
//		
//    	HunterAgent hunter = new HunterAgent();
//    	hunter.setup();
//    }

    protected void setup() {

    	if(log.isLoggable(Logger.WARNING))
    		log.log(Logger.WARNING, "Hello World! This is the " + getLocalName() + "!");
    	
    	world = new World();
    	
//		gui = new WumpusGUI(this);
//		gui.setSize(680, 575);
//		gui.setLocation(320, 160);
//		gui.setVisible(true);
    	
		TopicManagementHelper hlp;
		try {
			hlp = (TopicManagementHelper) this.getHelper(TopicManagementHelper.SERVICE_NAME);
			this.topic = hlp.createTopic(WumpusConsts.GAME_TOPIC_NAME);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}

		/*
		 * Das Finite State Machine Behaviour, in dem sie
		 * für das Spiel benötigten Behaviour zusammengefasst
		 * sind, wird erzeugt und zum Agenten hinzugefügt.
		 */
		GameBehaviour fsm = new GameBehaviour(this);
		this.addBehaviour(fsm);
   	}
    
    public World getWorld() {
    	return this.world;
    }

	public void setGameAgentDescription(DFAgentDescription description) {

		this.gameAgent = description;
	}
	
	public DFAgentDescription getGameAgentDescription() {

		return this.gameAgent;
	}

	public String processPerceptionAndGetNextStep(WumpusPerception perception) {

		if (perception.getGlitter().equals(WumpusConsts.SENSOR_GLITTER))
			return WumpusConsts.ACTION_GRAB;
		else if (perception.getBump().equals(WumpusConsts.SENSOR_BUMP))
			return WumpusConsts.ACTION_TURN_LEFT;
		else
			return WumpusConsts.ACTION_MOVE;
	}
}
