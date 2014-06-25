package agentville.games.wumpus.simplehunter.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import agentville.games.wumpus.simplehunter.HunterAgent;

public class WumpusGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private HunterAgent model;
	
	public WumpusGUI(HunterAgent wumpus) {
		
		this.model = wumpus;
		
		this.setTitle(model.getLocalName() + " GUI - Version 1.0");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			doClose();}});
	
		this.setLayout(null);
		
		WorldPanel worldPanel = new WorldPanel(model.getWorld());
		worldPanel.setLocation(20, 20);
		this.add(worldPanel);
		
		this.pack();
		this.setResizable(false);
	}
	
	public void doClose() {
//		GuiEvent e = new GuiEvent(null, WumpusAgent.EVENT_CLOSE);
//		myAgent.onGuiEvent(e);
	}
}
