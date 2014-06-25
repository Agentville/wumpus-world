package agentville.games.wumpus.simplehunter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agentville.games.wumpus.simplehunter.listener.WorldModelListener;

public class World {
	
//	private Logger log = Logger.getMyLogger(this.getClass().getName());

	WorldNode[][] worldMap = null;
	
	private List<WorldModelListener> listenersWorld;

	public World(){
		
		worldMap = new WorldNode[4][4];
		listenersWorld = new ArrayList<WorldModelListener>();

		initWorld();
	}

	private void initWorld() {
		/*
		 * Die Knoten der Welt werden erzeugt
		 */
		for (int i = 0; i < worldMap.length; i++ ) {
			for (int j = 0; j < worldMap[i].length; j++) {
				worldMap[i][j] = new WorldNode(i+1, j+1);
			}
		}
		
		/*
		 * Wumpus und Gold sind jeweils einmalig vorhanden
		 * und zufällig verteilt, aber niemals auf dem gleichen
		 * Feld und niemals an Position 1,1 (also [0][3] im Array).
		 */
		Random r = new Random();
		int x, y;
		boolean placed = false;
		while(!placed){
			//Wumpus zufällig plazieren
			x = r.nextInt(worldMap.length);
			y = r.nextInt(worldMap[1].length);
			if(x != 0 || y != 3){
				worldMap[x][y].setWumpus(true);
				placed = true;
			}
		}

		placed = false;
		while (!placed) {
			//Gold zufällig plazieren (aber nicht wo der Wumpus ist)
			x = r.nextInt(worldMap.length);
			y = r.nextInt(worldMap[1].length);
			if ((x != 0 || y != 3) && worldMap[x][y].hasNoWumpus()) {//isEmpty()) {
				worldMap[x][y].setGold(true);
				placed = true;
			}
		}
		
		//Fallen überall mit einer Wahrscheinlichkeit von 0.2 plazieren,
		//aber nicht wo Wumpus oder Gold sind.
		for (x = 0; x < worldMap.length; x++ ) {
			for (y = 0; y < worldMap[x].length; y++) {
				if ((x != 0 || y != 3) && worldMap[x][y].isEmpty()){
					if (Math.random() <= 0.2) {
						worldMap[x][y].setPit(true);
					}
				}
			}
		}
		
		notifyWorldModelListeners();
	}

	public WorldNode[][] getWorldMap() {
		return this.worldMap;
	}

	/*
	 * Listener fürs GUI
	 */
	public void addWorldModelListener(final WorldModelListener listener) {
		if (listener != null && !listenersWorld.contains(listener)) {
			this.listenersWorld.add(listener);
		}
	}
	
	public void removeWorldModelListener(final WorldModelListener listener) {
		this.listenersWorld.remove(listener);
	}
	
	public void notifyWorldModelListeners() {
		for (final WorldModelListener listener : this.listenersWorld) {
			notifyWorldModelListener(listener);
		}
	}

	public void notifyWorldModelListener(final WorldModelListener listener) {
		listener.worldModelChanged(this.getWorldMap());
	}

}
