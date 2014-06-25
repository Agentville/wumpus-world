package agentville.games.wumpus.simplehunter;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * @author Marco Steffens
 * @version 0.1
 */
public class Hunter {

	public static final String THIS_AGENT_NAME = "Hunter2";

//	private Logger log = Logger.getMyLogger(this.getClass().getName());
	
	public static void main(String[] args){
		
		String host;
		int port;
		String platform = null;		//default name
		boolean main = false;		//'normal'-container

		host = "localhost";
		port = -1;				//default-port 1099
		
		Runtime runtime = Runtime.instance();
		Profile profile = null;
		AgentContainer container = null;

		profile = new ProfileImpl(host, port, platform, main);
		//Notwendig fuer den topic-Service:
		profile.setParameter(Profile.SERVICES, 
				"jade.core.event.NotificationService; " +
				"jade.core.messaging.TopicManagementService");
		
		//Container erzeugen
		container = runtime.createAgentContainer(profile);
		
		 // Agenten erzeugen und startet - oder aussteigen.
		try {

			AgentController agent = container.createNewAgent(
					THIS_AGENT_NAME, 
					HunterAgent.class.getName(), 
					args);
			agent.start();

		} catch(StaleProxyException e) {
			//Wenn das schon nicht geht, ist eh alles egal.
			throw new RuntimeException(e);
		}			
    }
}
