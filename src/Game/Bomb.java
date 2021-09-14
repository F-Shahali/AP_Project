package Game;


import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bomb {
	BufferedImage[] images, enfejar;
	BufferedImage image;
	Image[] bombs = new Image[10], Enfejar = new Image[9];
	Boolean drawEnfejar, drawBomb;
	Point EnfejarPlace = new Point();
	int x, y, XdistanceToCentre = 0, YdistanceToCentre = 0, area, distance = 0,
			HowManyFiveSingle, BombNum = 0, EnfejarNum = 0;
	
	public Bomb() {
		initialize();
	}

	private void initialize() {
		
		try {
//			image = ImageIO.read(new File("file/bomb.png"));
//			 images = new BufferedImage[10];
			 enfejar = new BufferedImage[9];
			 for(int i =0; i<9; i++) {
				enfejar[i] = ImageIO.read(new File("file/Enfejar/" + i + ".png"));
				Enfejar[i] = enfejar[i].getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
				
			 }
//			 for(int i = 0; i<10; i++) {
//				 images[i] = ImageIO.read(new File("file/Bomb/2" + (i+1) + ".png"));
//				 bombs[i] = images[i].getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			 
		}catch (IOException e) {}
		
	}
	void SetImage() {
		try {
			if(area == 1 || area == 4) {
				 for(int i = 0; i<10; i++) {
					 images[i] = ImageIO.read(new File("file/Bomb/1" + (i+1) + ".png"));
					 bombs[i] = images[i].getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				 }
			 }
			 if(area == 2 || area == 3) {
				 for(int i = 0; i<10; i++) {
					 images[i] = ImageIO.read(new File("file/Bomb/2" + (i+1) + ".png"));
					 bombs[i] = images[i].getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				 }
			 }
		}catch (IOException e) {}
	}
	
}
