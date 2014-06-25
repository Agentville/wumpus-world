package agentville.games.wumpus.simplehunter.behaviours;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.Logger;
import agentville.games.wumpus.simplehunter.HunterAgent;
import agentville.games.wumpus.world.WumpusConsts;

/**
 * Dieses Behaviour sucht in regelmäßigen Abständen in den Yellow Pages nach dem
 * Service, den der Spiele-Agent anbietet. Wird der Service gefunden, wird der
 * GameAgentDescriptor an diesem Agenten gespeichert und dieses Behaviour
 * beendet sich.
 *   
 * @author Marco Steffens
 *
 */
public class SearchGameBehaviour extends TickerBehaviour {

	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getMyLogger(this.getClass().getName());
	
	private int transition = GameBehaviour.TRANS_GAME_FOUND;
	
	ServiceDescription filter = null;
	DFAgentDescription dfd = null;
	DFAgentDescription[] results = null;
	
	public SearchGameBehaviour(Agent a, long period) {
		super(a, period);
		
		this.filter = new ServiceDescription();
		this.dfd = new DFAgentDescription();

		//Erzeugt den Filter für den Spiele-Service
		this.filter.setType(WumpusConsts.GAME_SERVICE_TYPE);
		this.dfd.addServices(filter);
	}

	public void onTick() {
    	if(log.isLoggable(Logger.WARNING))
    		log.log(Logger.WARNING, "searching for game agent...");
			
		try {
			/*
			 * Sucht in den YP nach dem Service und damit nach dem Agenten, 
			 * der den Service anbietet.
			 */
			this.results = DFService.search(myAgent, dfd);

			if (this.results.length == 0) {
		    	if(log.isLoggable(Logger.WARNING))
		    		log.log(Logger.WARNING, "no agent with service-type '" +
		    				WumpusConsts.GAME_SERVICE_TYPE + 
		    				"' found, try again later");
			} else if (this.results.length == 1) {
		    	if(log.isLoggable(Logger.WARNING))
		    		log.log(Logger.WARNING, "found agent '" 
							+ results[0].getName().getLocalName() 
							+ "' with service-type '" + WumpusConsts.GAME_SERVICE_TYPE);
				/*
				 * Ein Spiele-Agent gefunden, der wird am Agenten gemerkt,
				 * dieser Behaviour hat seine Aufgabe erfüllt und beendet sich.
				 */
				((HunterAgent) myAgent).setGameAgentDescription(results[0]);
				this.stop();
			} else {
		    	if(log.isLoggable(Logger.WARNING))
		    		log.log(Logger.WARNING, "unexpected number of agents with service" +
							" 'wumpus-game', try again later");
			}
		} catch (FIPAException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int onEnd() {
    	if(log.isLoggable(Logger.WARNING))
    		log.log(Logger.WARNING, "onEnd");
		
		return transition;
	}
}