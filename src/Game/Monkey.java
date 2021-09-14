package Game;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Monkey {
	int kind, heart, probabilityOfCoconut, Xlocation, Ylocation,
	MonkeyNum = 0, counter = 0, randomNum, XEnd, YEnd, BnanaRandom, Xsize = 110, Ysize = 120, PowerRandom, CoconutSpeed;
	boolean MonkeyDead = false, reverseNum = false , ReduceCounter = false, getLocation, hunter = false, StartWave = true, Drawing = true;
	BufferedImage Gooril;
	Image Ghool;
	double Zavie;
	int Heart;
	PowerUp p;
	ArrayList<BufferedImage> MonkeyLogo = new ArrayList<BufferedImage>();
	static Monkey gooril;
	
	public Monkey(int kind){
		this.kind = kind;
		initialize();
	}
	
	private void initialize() {
		
		if(kind != 5) {
			this.Xsize = 120;
			this.Ysize = 130;
			for(int i = 0; i<5; i++) {
				try {
					MonkeyLogo.add(ImageIO.read(new File("file/Monkey/m"+ kind + "/M" + kind + "-" + (i+1) + ".png")));
				} catch (IOException e) {}
			
			}
		}
			
		if(kind == 1) {
			this.heart = (int) (2*Math.floor(Math.sqrt(Game.game.players)));
			Heart = heart;
			this.probabilityOfCoconut = 5;
			this.CoconutSpeed = 5;
		}
		if(kind == 2) {
			this.heart = (int) (3*Math.floor(Math.sqrt(Game.game.players)));
			Heart = heart;
			this.probabilityOfCoconut = 5;
			this.CoconutSpeed = 5;
		}
		if(kind == 3) {
			this.heart = (int) (5*Math.floor(Math.sqrt(Game.game.players)));;
			Heart = heart;
			this.probabilityOfCoconut = 10;
			this.CoconutSpeed = 10;
		}
		if(kind == 4) {
			this.heart = (int) (8*Math.floor(Math.sqrt(Game.game.players)));;
			this.probabilityOfCoconut = 20;
			this.CoconutSpeed = 10;
		}
		if(kind == 5) {
			gooril = this;
			this.heart = (int) (250*Game.game.level * Math.floor(Math.sqrt(Game.game.players)));
			Heart = heart;
			this.Xsize = 610;//300;
			this.Ysize = 710;//250;
//			try {
//				Gooril = ImageIO.read(new File("file/Gooril.png"));
//			} catch (IOException e) {}
			for(int i = 0; i<12; i++) {
				try {
					MonkeyLogo.add(ImageIO.read(new File("file/Gooril/" + (i+1) + ".png")));
				} catch (IOException e) {}
			
			}
		}
		
		
	}
	void SetLocation(int i, int a, int b) {
		int XCenterToCenter = a - this.Xlocation;
		int YCenterToCenter = b - this.Ylocation;
		this.Zavie = Math.atan2(YCenterToCenter, XCenterToCenter);
		if(Game.game.type.equals("multi") && Game.game.ID == -1) {
			Server.setLocation(i, a, b);
		}
	}
	void ProbabilityOfBnana() {
		Random r = new Random();
		BnanaRandom = r.nextInt(101);
		if(BnanaRandom <= 6) {
			Bnana b = new Bnana(this.Xlocation+60, this.Ylocation+65);
			Game.game.bnanas.add(b);
		}
	}
	void ProbabiltyOfCoconut(int i) {
		Random r = new Random();
		randomNum = r.nextInt(101);
		if(randomNum <= this.probabilityOfCoconut) {
//			System.out.println(randomNum);
			Coconut c = new Coconut(this.Xlocation+60, this.Ylocation+130, CoconutSpeed);
			Game.game.Coconuts.add(c);
			Sounds s = new Sounds("file/Coconut.wav");
			if(Game.game.type.equals("multi"))
				Server.FallCoconut(i);
		}
	}
	void ProbabiltyOfPowerUp() {
		Random r = new Random();
		PowerRandom = r.nextInt(101);
		if(PowerRandom <= 3) {
			int t = r.nextInt(2);
			if(t == 0) {
				p = new PowerUp();
				p.X = this.Xlocation;
				p.Y = this.Ylocation;
				Game.game.powerup.add(p);
			}
			if(t == 1) {
				p = new PowerUp(r.nextInt(3)+1);
				p.X = this.Xlocation;
				p.Y = this.Ylocation;
				Game.game.Gift.add(p);
			}
			
		}
	}
	void GhoolDead(ArrayList<Tir> Arrow, Monkey m) {
		for(int i = 0; i<Arrow.size(); i++) {
			if((Arrow.get(i).x >= m.Xlocation+20 && Arrow.get(i).x < m.Xlocation + (m.Xsize-20)) && 
					(Arrow.get(i).y-Arrow.get(i).Distance*10 > m.Ylocation+80 &&
							Arrow.get(i).y-Arrow.get(i).Distance*10 < m.Ylocation + (m.Ysize-30)) && Arrow.get(i).drawing == true) {
				m.heart -= Arrow.get(i).power;
				if(m.heart <= 0) {
					m.MonkeyDead = true;
					m.ProbabilityOfBnana();
				}
//				System.out.println(m.get(j).BnanaRandom);
//				m.get(j).Xlocation = -200;
//				m.get(j).Ylocation = -200;
				Arrow.get(i).drawing = false;
//				Game.game.score += m.get(j).kind;
//				Game.game.Score.setText(String.valueOf(Game.game.score));
				
			}
	}
	}

//	public Image[] MonkeyPic(int kind) throws IOException {
//		BufferedImage[] monkey = new BufferedImage[5];
//		Image[] MonkeyLogo = new Image[5];
//		for(int i = 0; i<5; i++) {
//			monkey[i] = ImageIO.read(new File("file/Monkey/m"+ kind + "/M" + kind + "-" + (i+1) + ".png"));
//			MonkeyLogo[i] = monkey[i].getScaledInstance(50, 60, java.awt.Image.SCALE_SMOOTH);
//		}
//		return MonkeyLogo;
//	}
}
