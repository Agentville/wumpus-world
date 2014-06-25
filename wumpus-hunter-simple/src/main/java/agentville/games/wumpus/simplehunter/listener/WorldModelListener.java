package agentville.games.wumpus.simplehunter.listener;

import agentville.games.wumpus.simplehunter.model.WorldNode;

public interface WorldModelListener {

	void worldModelChanged(WorldNode[][] node);
}
