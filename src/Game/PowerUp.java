package Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PowerUp{
	int kind, X, Y, fallCounter = 0, ImageNum = 0;
	ArrayList<BufferedImage> Images = new ArrayList<BufferedImage>();
	BufferedImage gift;
	boolean Drawing = true;
	
	public PowerUp(int kind) {
		this.kind = kind;
		
		try {
			gift = ImageIO.read(new File("file/" + kind + ".png"));
		} catch (IOException e) {}
	}
	PowerUp() {
		try {
			for(int i = 0; i < 25; i++)
				Images.add(ImageIO.read(new File("file/PowerUp/" + (i+1) + ".png")));
		} catch (IOException e) {}
	}

}
