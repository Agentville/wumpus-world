package agentville.games.wumpus.simplehunter.model;

import java.util.ArrayList;
import java.util.List;

public class WorldNode {

//	private Logger log = Logger.getMyLogger(this.getClass().getName());

	int xCoord;
	int yCoord;
	boolean pit;
	boolean wumpus;
	boolean gold;
	List<Hunter> hunters;

	public WorldNode(int xCoord, int yCoord) {
		
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.pit = false;
		this.wumpus = false;
		this.gold = false;
		this.hunters  = new ArrayList<Hunter>();
	}

	public boolean hasPit() {
		return pit;
	}

	public boolean hasWumpus() {
		return wumpus;
	}

	public boolean hasGold() {
		return gold;
	}

	public boolean hasNoPit() {
		return !pit;
	}

	public boolean hasNoWumpus() {
		return !wumpus;
	}

	public boolean hasNoGold() {
		return !gold;
	}
	
	public boolean isEmpty() {
		if (hasNoGold() && hasNoWumpus() && hasNoPit()) {
			return true;
		} else {
			return false;
		}
	}

	public int getxCoord() {
		return xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setPit(boolean pit) {
		this.pit = pit;
	}

	public void setWumpus(boolean wumpus) {
		this.wumpus = wumpus;
	}

	public void setGold(boolean gold) {
		this.gold = gold;
	}
}
