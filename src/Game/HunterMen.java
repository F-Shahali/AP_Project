package Game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HunterMen {
	Image image;
	int ID, survive = 0;;
	int x, y;
	final int firstx, firsty;
	boolean DrawHunterMan = true, HunterManDead = false;
	public HunterMen(int ID) {
		this.ID = ID;
		try {
			if(ID == -1) {
				image = ImageIO.read(new File("file/Server.png")).getScaledInstance(150, 160, java.awt.Image.SCALE_SMOOTH);
			}
			else
				image = ImageIO.read(new File("file/hunterMan" + (ID+2) + ".png")).getScaledInstance(150, 160, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
		firstx = (ID+1)*170;
		firsty = 600;
	}

}
