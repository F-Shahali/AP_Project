package Game;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GhoolTir {

	int  number, X, Y, Counter = 0, Zavie, Xlocation, Ylocation, Xsize, Ysize;
	BufferedImage Tir;
	Image tir;
	boolean drawing = true;
	public GhoolTir(int i) {
		number = i;
		initialize();
	}

	private void initialize() {
		Zavie = number*45;
		if(number == 0 || number == 4) {
			Xsize = 80;
			Ysize = 49;
		}
		if(number == 1 || number == 3 || number== 5 || number == 7) {
			Xsize = Ysize = 95;
		}
		if(number == 2 || number == 6) {
			Xsize = 49;
			Ysize = 80;
		}
		
		if(number == 3 || number == 4 || number == 5)
			X = Monkey.gooril.Xlocation + Monkey.gooril.Xsize/6;
		if(number == 0 || number == 1 || number == 7)
			X = Monkey.gooril.Xlocation + Monkey.gooril.Xsize-Monkey.gooril.Xsize/5;
		if(number == 2 || number == 6)
			X = (Monkey.gooril.Xlocation + Monkey.gooril.Xsize/5 +
					Monkey.gooril.Xlocation + Monkey.gooril.Xsize-Monkey.gooril.Xsize/5)/2;
		if(number == 5 || number == 6 || number == 7)
			Y = Monkey.gooril.Ylocation + 2*Monkey.gooril.Ysize/3;
		if(number == 1 || number == 2 || number == 3)
			Y = Monkey.gooril.Ylocation + Monkey.gooril.Ysize/4;
		if(number == 0 || number == 4)
			Y = Monkey.gooril.Ylocation + Monkey.gooril.Ysize/2;

		try {
			tir = ImageIO.read(new File("file/GhoolTir/" + number + ".png"));
//			tir = Tir.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
		
	}
	void HitToHunterMan() {
		if((Game.game.MouseX+10 <= Xlocation && Game.game.MouseX+80 >= Xlocation) && 
				(Game.game.MouseY+10 <= Ylocation &&
						Game.game.MouseY+90 > Ylocation) && Game.game.Hit == false && Game.game.esc == false) {
			drawing = false;
			if(Game.game.heartt > 0) {
				Game.game.bnanaNum = 0;
				Game.game.heartt--;
				Game.game.Hit = true;
				Game.game.DrawHunterMan = false;
			}
			if(Game.game.heartt == 0) {
				Game.game.HunterManDead = true;
				Game.game.DrawHunterMan = false;
			}
			Game.game.heartnumber.setText(String.valueOf(Game.game.heartt));
			
		}
	}
	
}
