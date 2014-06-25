package agentville.games.wumpus.simplehunter.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import agentville.games.wumpus.simplehunter.model.WorldNode;

/**
 * Panel für die Anzeige eines Knotens der Welt.
 * 
 * @author Marco Steffens
 */
public class WorldNodePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
//	private Logger log = Logger.getMyLogger(this.getClass().getName());

    private BufferedImage image;

	public WorldNodePanel() {
		
		this.setBackground(Color.green);
	}
	public WorldNodePanel(BufferedImage image) {
		
		this.setBackground(Color.darkGray);
		this.image = image;
	}
	
	public WorldNodePanel(WorldNode node) {
		
		this.setBackground(Color.red);//lightGray);
	}
	
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }
	
}
