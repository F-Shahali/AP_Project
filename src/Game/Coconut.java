package Game;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Coconut {
	
	int fallCounter = 0, X, Y, coconutSpeed;
	BufferedImage coconut;
	static Image Coco;
	boolean Drawing = true;
	
	public Coconut(int x, int y, int speed) {
		this.X = x;
		this.Y = y;
		coconutSpeed = speed;
		initialize();
	}

	private void initialize() {
		try {
			coconut = ImageIO.read(new File("file/coco.png"));
			Coco = coconut;//getScaledInstance(40, 50, java.awt.Image.SCALE_SMOOTH);
		}catch (Exception e) {}
		
	}
	

}
