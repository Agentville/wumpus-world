package agentville.games.wumpus.simplehunter.view;

import jade.util.Logger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import agentville.games.wumpus.simplehunter.listener.WorldModelListener;
import agentville.games.wumpus.simplehunter.model.World;
import agentville.games.wumpus.simplehunter.model.WorldNode;

public class WorldPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getMyLogger(this.getClass().getName());

	World model;
	WorldNode[][] worldMap;
	WorldNodePanel[][] worldGUI = new WorldNodePanel[4][4];

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc;

	public WorldPanel(World world) {
		
		this.model = world;
		this.worldMap = model.getWorldMap();
		
		JLabel label;
		
		BufferedImage gold = null;
		BufferedImage pit = null;
		BufferedImage wumpus = null;
//		BufferedImage hunter = null;
//		BufferedImage empty = null;
		
		try {                
			gold = ImageIO.read(new File("src/main/resources/images/gold.png"));
			pit = ImageIO.read(new File("src/main/resources/images/pit.jpg"));
			wumpus = ImageIO.read(new File("src/main/resources/images/wumpus.png"));
//			hunter = ImageIO.read(new File("src/main/resources/images/hunter.png"));
//			empty = ImageIO.read(new File("src/main/resources/images/empty.png"));
		} catch (IOException ex) {
			// handle exception...
	    	if(log.isLoggable(Logger.WARNING))
	    		log.log(Logger.WARNING, ex.getMessage().toString());
		}
		
		this.setBorder(new TitledBorder("The Wumpus World"));
		this.setSize(450,450);
		
		gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		gbc = new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 0, 0);
		label = new JLabel("x");
		gbl.setConstraints(label, gbc);
		this.add(label);
		
		gbc = new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 0, 0);
		label = new JLabel("y");
		gbl.setConstraints(label, gbc);
		this.add(label);
		
		for (int i=0; i<4; ++i) {
			gbc = new GridBagConstraints(((i*3)+2), 13, 3, 1, 0.1, 0.1, 
					GridBagConstraints.CENTER, 
					GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0);
			label = new JLabel(""+(i+1));
			gbl.setConstraints(label, gbc);
			this.add(label);
		}
		
		for (int i=3; i>=0; --i) {
			gbc = new GridBagConstraints(0, ((i*3)), 1, 3, 0.1, 0.1, 
					GridBagConstraints.CENTER, 
					GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0);
			label = new JLabel(""+(4-i));
			gbl.setConstraints(label, gbc);
			this.add(label);
		}
		
		for (int i=0; i<4; ++i) {
			for (int j=0; j<4; ++j) {
				gbc = new GridBagConstraints(((i*3)+2), ((j*3)), 3, 3, 1.0, 1.0, 
						GridBagConstraints.CENTER, 
						GridBagConstraints.BOTH, 
						new Insets(1, 1, 1, 1), 0, 0);
				if(worldMap[i][j].hasGold()) {
					worldGUI[i][j] = new WorldNodePanel(gold);
				} else if(worldMap[i][j].hasPit()) {
					worldGUI[i][j] = new WorldNodePanel(pit);
				} else if(worldMap[i][j].hasWumpus()) {
					worldGUI[i][j] = new WorldNodePanel(wumpus);
				} else {
					worldGUI[i][j] = new WorldNodePanel();
				}
				gbl.setConstraints(worldGUI[i][j], gbc);
				worldGUI[i][j].setSize(30, 30);
				worldGUI[i][j].setMinimumSize(new Dimension(30,30));
				worldGUI[i][j].setMaximumSize(new Dimension(30,30));
				worldGUI[i][j].setPreferredSize(new Dimension(30,30));
				this.add(worldGUI[i][j]);
			}		
		}
		
		model.addWorldModelListener(new WorldModelListener() {
			public void worldModelChanged(final WorldNode[][] world) {
				setValues(world);
			}
		});
	
	}

	protected void setValues(WorldNode[][] world) {
		// TODO Auto-generated method stub
		
	}

}
