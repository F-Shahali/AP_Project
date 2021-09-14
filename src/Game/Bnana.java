package Game;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Bnana {
	int fallCounter = 0, X, Y;
	static BufferedImage bnana;
	static Image bnanaa;
	boolean Drawing = true;
	
	public Bnana(int x, int y) {
		this.X = x;
		this.Y = y;
		initialize();
	}

	private void initialize() {
		try {
			bnana = ImageIO.read(new File("file/bna.png"));
			bnanaa = bnana;
		}catch (Exception e) {}
		
	}

}
