package Game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class MonkeyGroups {
	int Xcenter = 0, Ycenter = 0, Num = 0, MonkeyDeadNum = 0, XEnd = 10, YEnd = 10 , Radius = 0,
			XCenterToCenter, YCenterToCenter, kind;
	double distance, Zavie;
	boolean GroupDead = false, StartWave = true, Arrive = false, LevelFinished = false, releaseBomb = false, ChangingShape = false;
	ArrayList<Point> coco = new ArrayList<Point>();
	ArrayList<Coconut> Coconuts = new ArrayList<Coconut>();
	ArrayList<Bnana> bnana = new ArrayList<Bnana>();

	public MonkeyGroups(int num, int kinds, ArrayList<Monkey> m) {
		Num = m.size();
		if(kinds != 5)
			for(int i = 0; i< num; i++) {
				m.add(new Monkey((i%kinds)+1));
			}
		if(kinds == 5)
			m.add(new Monkey(5));
	}
	public MonkeyGroups(int num, int kind1, int kind2, ArrayList<Monkey> m) {
		for(int i = 0; i< num/2; i++) {
			Monkey m1 = new Monkey(kind1);
			m.add(m1);
			Monkey m2 = new Monkey(kind2);
			m.add(m2);
		}
		
	}
	public MonkeyGroups(int num, int kind1, int kind2, int kind3, ArrayList<Monkey> m) {
//		for(int i = 0; i< num/3; i++) {
//			Monkey m1 = new Monkey(kind1);
//			m.add(m1);
//			Monkey m2 = new Monkey(kind2);
//			m.add(m2);
//			Monkey m3 = new Monkey(kind3);
//			m.add(m3);
//		}
	}
	public MonkeyGroups(int num, int kind1, int kind2, int kind3, int kind4, ArrayList<Monkey> m) {
		for(int i = 0; i< num/4; i++) {
			Monkey m1 = new Monkey(kind1);
			m.add(m1);
			Monkey m2 = new Monkey(kind2);
			m.add(m2);
			Monkey m3 = new Monkey(kind3);
			m.add(m3);
			Monkey m4 = new Monkey(kind4);
			m.add(m4);
		}
	}
	//	void CreatGroup(int num, int kind, ArrayList<Monkey> m) {
//		for(int i = 0; i <num; i++) {
//			Monkey monkey = new Monkey(kind);
//			m.add(monkey);
//			
//		}
//	}
	void DeleteGroup(ArrayList<Monkey> m) {
		for(int i = 0; i < m.size(); i++) {
			m.remove(i);
		}
	}
	void CircularGroup(Graphics2D g2, ArrayList<Monkey> m, ArrayList<Tir> Arrow) {
		BombChecker(m);
		for(int i = 0; i<m.size(); i++) {
			HitChecker(m.get(i));
			MonkeyDead(Arrow, m.get(i));
			UpdateScore(m.get(i));
			if(m.get(i).MonkeyDead == true) {
				m.get(i).ProbabilityOfBnana();
				m.get(i).ProbabiltyOfPowerUp();
				Sounds s = new Sounds("file/MonkeyDead.wav");
				m.remove(i);
				MonkeyDeadNum++;
//				m.get(i).Drawing = false;
				for(int j = 0; j<m.size(); j++) {
					m.get(j).counter = 0;
				}
				ChangingShape = true;
			}
		}
		if(m.size() == 0) {
			this.GroupDead = true;
			MonkeyDeadNum = 0;
		}
		for(int i = 0; i< m.size(); i++) {
			if(m.get(i).MonkeyDead == false) {
				m.get(i).Xlocation = (int)(this.Xcenter+(Math.cos(2*i*Math.PI/m.size() + m.get(i).counter*2)*(180 - MonkeyDeadNum*5)));
				m.get(i).Ylocation = (int)(this.Ycenter+(Math.sin(2*i*Math.PI/m.size() + m.get(i).counter*2)*(180 - MonkeyDeadNum*5)));
				g2.drawImage(m.get(i).MonkeyLogo.get(m.get(i).MonkeyNum),
						m.get(i).Xlocation, m.get(i).Ylocation, null);
				if(m.get(i).reverseNum == true) {
					m.get(i).MonkeyNum--;
					if(m.get(i).MonkeyNum == 0)
						m.get(i).reverseNum = false;
				}
				else {
					m.get(i).MonkeyNum++;
				if(m.get(i).MonkeyNum == 4)
					m.get(i).reverseNum = true;
				}
				if(ChangingShape == false)
					m.get(i).counter++;
				this.Xcenter += 2*Math.cos(this.Zavie);
				this.Ycenter += 2*Math.sin(this.Zavie);
			}
		}
	}
	void SetCenter(int x, int y) {
		double XCenterToCenter = x - this.Xcenter;
		double YCenterToCenter = y - this.Ycenter;
		this.Zavie = Math.atan2(YCenterToCenter, XCenterToCenter);
		if(Game.game.type.equals("multi") && Game.game.ID == -1) {
			Server.setCenter(x, y);
		}
//		distance = Math.sqrt((double)(XCenterToCenter*XCenterToCenter + YCenterToCenter*YCenterToCenter));
	}
	void RectangularGroup(ArrayList<Monkey> m, ArrayList<Tir> Arrow, Graphics2D g2) {
		BombChecker(m);
		for(int i = 0; i<m.size(); i++) {
//			System.out.println(m.size());
			HitChecker(m.get(i));
			MonkeyDead(Arrow, m.get(i));
			UpdateScore(m.get(i));
			if(m.get(i).MonkeyDead == true) {
				m.get(i).ProbabilityOfBnana();
				m.get(i).ProbabiltyOfPowerUp();
				Sounds s = new Sounds("file/MonkeyDead.wav");
				m.remove(i);
				
			}
		}
		if(m.size() == 0) {
//			System.out.println("Yes");
			this.GroupDead = true;
			this.MonkeyDeadNum = 0;
		}
//		if(Game.game.ID == -1) {
			for(int i = 0; i<m.size(); i++) {
				if(m.get(i).MonkeyDead == false){
					m.get(i).Xlocation = (1350+(i%8)*100)-(m.get(i).counter*5);
					m.get(i).Ylocation =  70+(int)(i/8)*100;
					int l = m.get(i).MonkeyNum;
					m.get(i).MonkeyLogo.get(l);
					g2.drawImage(m.get(i).MonkeyLogo.get(m.get(i).MonkeyNum),
							m.get(i).Xlocation, m.get(i).Ylocation, null);
//					Server.MonkeyLocation(i, m.get(i).MonkeyNum, m.get(i).Xlocation, m.get(i).Ylocation);
					if(m.get(i).reverseNum == true) {
						m.get(i).MonkeyNum--;
						if(m.get(i).MonkeyNum == 0)
							m.get(i).reverseNum = false;
					}
					else {
						m.get(i).MonkeyNum++;
					if(m.get(i).MonkeyNum == 4)
						m.get(i).reverseNum = true;
					}
					if(m.get(i).counter < 278 && m.get(i).ReduceCounter == false) {
						m.get(i).counter++;
						if(m.get(i).counter == 278) {
							m.get(i).ReduceCounter = true;
						}
					}
					if(m.get(i).ReduceCounter == true) {
						m.get(i).counter-- ;
						if(m.get(i).counter == 155)
							m.get(i).ReduceCounter = false;
					}
				}
			}
//		}
		
	}
	void RotatoryGroup(Graphics2D g2, ArrayList<Monkey> m, ArrayList<Tir> Arrow) {
		BombChecker(m);
		for(int i = 0; i<m.size(); i++) {
			HitChecker(m.get(i));
			MonkeyDead(Arrow, m.get(i));
			UpdateScore(m.get(i));
			if(m.get(i).MonkeyDead == true) {
				m.get(i).ProbabilityOfBnana();
				m.get(i).ProbabiltyOfPowerUp();
				Sounds s = new Sounds("file/MonkeyDead.wav");
				m.remove(i);
				for(int j = 0; j<m.size(); j++) {
					m.get(j).counter = 0;
				}
				ChangingShape = true;
			}
		}
		if(m.size() == 0) {
			this.GroupDead = true;
			MonkeyDeadNum = 0;
		}
		for(int i = 0; i< m.size(); i++) {
			if(m.get(i).MonkeyDead == false) {
				m.get(i).Xlocation = (int)(660 -(Math.cos(2*i*Math.PI/m.size() + 6*m.get(i).counter)*(600 - Radius*10)));
				m.get(i).Ylocation = (int)(300 -(Math.sin(2*i*Math.PI/m.size() + 6*m.get(i).counter)*(600 - Radius*10)));
				g2.drawImage(m.get(i).MonkeyLogo.get(m.get(i).MonkeyNum),
						m.get(i).Xlocation, m.get(i).Ylocation, null);
				if(m.get(i).reverseNum == true) {
					m.get(i).MonkeyNum--;
					if(m.get(i).MonkeyNum == 0)
						m.get(i).reverseNum = false;
				}
				else {
					m.get(i).MonkeyNum++;
				if(m.get(i).MonkeyNum == 4)
					m.get(i).reverseNum = true;
				}
				if(ChangingShape == false)
					m.get(i).counter++;
				if(Radius < 34)
					Radius++;
			}
		}
	}
	void Entehary(Graphics2D g2, ArrayList<Monkey> m, ArrayList<Tir> Arrow) {
		BombChecker(m);
		for(int i = 0; i<m.size(); i++) {
			HitChecker(m.get(i));
			MonkeyDead(Arrow, m.get(i));
			UpdateScore(m.get(i));
			if(m.get(i).MonkeyDead == true) {
				m.get(i).ProbabilityOfBnana();
				m.get(i).ProbabiltyOfPowerUp();
				Sounds s = new Sounds("file/MonkeyDead.wav");
				m.remove(i);
			}
		}
		if(m.size() == 0) {
			this.GroupDead = true;
			try {
				Main.clip.stop();
				Main.music("file/Bazi5.wav");
			} catch (Exception e) {}
			MonkeyDeadNum = 0;
		}
		for(int i = 0; i< m.size(); i++) {
			
			if(m.get(i).MonkeyDead == false){
				g2.drawImage(m.get(i).MonkeyLogo.get(m.get(i).MonkeyNum),
						m.get(i).Xlocation, m.get(i).Ylocation, null);
				if(m.get(i).reverseNum == true) {
					m.get(i).MonkeyNum--;
					if(m.get(i).MonkeyNum == 0)
						m.get(i).reverseNum = false;
				}
				else {
					m.get(i).MonkeyNum++;
				if(m.get(i).MonkeyNum == 4)
					m.get(i).reverseNum = true;
				}
				if(m.get(i).hunter == true) {
					m.get(i).Xlocation += 6*Math.cos(m.get(i).Zavie);
					m.get(i).Ylocation += 6*Math.sin(m.get(i).Zavie);
					
					if(Math.ceil(m.get(i).XEnd - m.get(i).Xlocation) <= 2) {
						if(m.get(i).MonkeyDead == false) {
							m.get(i).hunter = false;
							Random x = new Random();
							int XEnd = x.nextInt(1200);
							int YEnd = x.nextInt(700);
							m.get(i).SetLocation(i, XEnd, YEnd);
						}
					}
				}else {
					m.get(i).Xlocation += 3*Math.cos(m.get(i).Zavie);
					m.get(i).Ylocation += 3*Math.sin(m.get(i).Zavie);
				}
			}
		}
	}
	void Ghool(Graphics2D g, ArrayList<Tir> Arrow, ArrayList<Monkey> m) {
		
		MonkeyDead(Arrow, m.get(0));
		HitChecker(m.get(0));
		if(m.get(0).MonkeyDead == false) {
			g.setFont(new Font("IranNastaliq", Font.ITALIC, 40));
			g.setColor(new Color(255,255,204));
			g.drawString("" + m.get(0).heart, 650, 40);
			if(m.get(0).counter < 80) {
				m.get(0).Xlocation = 410;
				m.get(0).Ylocation = -500+4*m.get(0).counter;
			}
			
			g.drawImage(m.get(0).MonkeyLogo.get(m.get(0).MonkeyNum), m.get(0).Xlocation, m.get(0).Ylocation, null);
			m.get(0).MonkeyNum++;
			if(m.get(0).MonkeyNum == 12)
				m.get(0).MonkeyNum = 0;
			if(m.get(0).counter < 80) {
				m.get(0).counter++;
			}
			if(m.get(0).counter == 80) {
				Game.game.StartDrawGhoolTir = true;
				m.get(0).Xlocation += 5*Math.cos(m.get(0).Zavie);
				m.get(0).Ylocation += 5*Math.sin(m.get(0).Zavie);
			}
		}
		if(m.get(0).MonkeyDead == true) {
			GroupDead = true;
			Main.clip.stop();
			Sounds s = new Sounds("file/NextLevel.wav");
			Random r = new Random();
			for(int i = 0; i<5; i++) {
				int t = r.nextInt(2);
				if(t == 0) {
					PowerUp p = new PowerUp();
					p.X = m.get(0).Xlocation + m.get(0).Xsize/2 + i%3 * 30;
					p.Y = m.get(0).Ylocation + m.get(0).Ysize/2 + (int)(i/3) * 30;
					Game.game.powerup.add(p);
				}
				if(t == 1) {
					PowerUp p = new PowerUp(r.nextInt(3)+1);
					p.X = m.get(0).Xlocation + m.get(0).Xsize/2 + i%3 * 30;
					p.Y = m.get(0).Ylocation + m.get(0).Ysize/2 + (int)(i/3) * 30;
					Game.game.Gift.add(p);
				}
			}
			if(Game.game.level == 4) {
				Game.game.Gamefinished = true;
				Main.clip.stop();
				try {
					Main.music("file/Finish.wav");
				} catch (Exception e) {}
			}
			m.remove(0);
			Game.game.score += 3*Game.game.bnanaNum;
			Game.game.bomb++;
			Game.game.bnanaNum = 0;
			Game.game.coinnumber.setText(String.valueOf(Game.game.bnanaNum));
			Game.game.Score.setText(String.valueOf(Game.game.score));
			Game.game.rocketnumber.setText(String.valueOf(Game.game.bomb));
			LevelFinished = true;
		}
	}
	void MonkeyDead(ArrayList<Tir> tir, Monkey m) {
		for(int i = 0; i<tir.size(); i++) {
				if((tir.get(i).XNow >= m.Xlocation+m.Xsize/5 &&
						 tir.get(i).XNow < m.Xlocation + (m.Xsize-m.Xsize/5)) && 
						(tir.get(i).YNow > m.Ylocation + m.Ysize/2 &&
								tir.get(i).YNow < m.Ylocation + (m.Ysize-m.Ysize/3)) && tir.get(i).drawing == true ) {
					m.heart -= tir.get(i).ActingPower;
					if(m.heart <= 0) {
						m.MonkeyDead = true;
					}
					tir.get(i).drawing = false;
					
				}
		}
	}
	void BombChecker(ArrayList<Monkey> m) {
		if(releaseBomb == true) {
			for(int i = 0; i< m.size(); i++) {
				m.get(i).MonkeyDead = true;
			}
//			GroupDead = true;
		}
		releaseBomb = false;
	}
	void DeleteBnana(ArrayList<Tir> tir, Bnana bnana) {
		for(int i = 0; i<tir.size(); i++) {
			if((tir.get(i).XNow >= bnana.X+10 && tir.get(i).XNow <= bnana.X + 300) && 
					(tir.get(i).YNow >= bnana.Y + bnana.fallCounter*5 + 10&&
							tir.get(i).YNow <= bnana.Y + bnana.fallCounter*5 + 40) && tir.get(i).drawing == true) {
				bnana.Drawing = false;
				tir.get(i).drawing = false;
			}
		}
	}
	
	void GetBnana(Bnana bnana) {
		if((bnana.X+20 >= Game.game.MouseX + 30 && bnana.X+20 <= Game.game.MouseX + 100) && 
					(Game.game.MouseY + 40 <= bnana.Y+bnana.fallCounter*5+50 &&
							Game.game.MouseY+90 > bnana.Y+bnana.fallCounter*5+50) && Game.game.esc == false) {
			bnana.Drawing = false;
			Game.game.bnanaNum++;
			Game.game.InFile[2] = Game.game.bnanaNum;
			Game.game.coinnumber.setText(String.valueOf(Game.game.bnanaNum));
		}
	}
	void GetPower(PowerUp p) {
		if((p.X+20 >= Game.game.MouseX + 30 && p.X+20 <= Game.game.MouseX + 100) && 
					(Game.game.MouseY + 40 <= p.Y+p.fallCounter*5+20 &&
							Game.game.MouseY+90 > p.Y+p.fallCounter*5+20) && Game.game.esc == false) {
			p.Drawing = false;
			Game.game.ActingPower++;
			
		}
	}
	void GetGift(PowerUp p) {
		if((p.X+20 >= Game.game.MouseX + 30 && p.X+20 <= Game.game.MouseX + 100) && 
				(Game.game.MouseY + 40 <= p.Y+p.fallCounter*5+20 &&
						Game.game.MouseY+90 > p.Y+p.fallCounter*5+20) && Game.game.esc == false) {
		p.Drawing = false;
		if(p.kind == Game.game.KindOfarrow) {
			Game.game.ActingPower++;
		}
		else
			Game.game.KindOfarrow = p.kind;
		
	}
	}
	void DeleteCoconut(Coconut coco) {
		if((coco.X+20 >= Game.game.MouseX+50 && coco.X + 20 <= Game.game.MouseX + 100) && 
				(Game.game.MouseY + 50 <= coco.Y+ coco.fallCounter*5 + 50 &&
						Game.game.MouseY+90 > coco.Y + coco.fallCounter*5 + 50) && 
				Game.game.HunterManDead == false && Game.game.esc == false && Game.game.Hit == false) {
		coco.Drawing = false;
		Game.game.ActingPower = 0;
		Game.game.NumberOfArrow = 1;
		if(Game.game.heartt == 0) {
			Game.game.HunterManDead = true;
			if(Game.game.type.equals("multi") ) {
				if(Game.game.ID == -1) {
					Server.HunterManDead();
				}
				if(Game.game.ID != -1) {
					Client.HunterManDead(Game.game.ID);
				}
			}
		}
		if(Game.game.heartt > 0) {
//			System.out.println("yes");
			Game.game.bnanaNum = 0;
			Game.game.heartt--;
			Game.game.Hit = true;
			Game.game.DrawHunterMan = false;
			if(Game.game.type.equals("multi") ) {
				if(Game.game.ID == -1) {
					Server.NonShowHunterMan();
				}
				if(Game.game.ID != -1) {
					Client.NonShowHunterMan(Game.game.ID);
				}
			}
		}
		Game.game.heartnumber.setText(String.valueOf(Game.game.heartt));
	}
	}
	void HitChecker(Monkey m) {
		if(m.kind != 5) {
			if((Game.game.MouseX+30 <= m.Xlocation+50 && Game.game.MouseX+120 > m.Xlocation+50) && 
					(Game.game.MouseY+30 <= m.Ylocation+60 &&
							Game.game.MouseY+130 > m.Ylocation+60) && Game.game.Hit == false &&
							Game.game.HunterManDead == false && Game.game.esc == false) {
				m.MonkeyDead = true;
				m.ProbabilityOfBnana();
				m.ProbabiltyOfPowerUp();
				Game.game.ActingPower = 0;
				Game.game.NumberOfArrow = 1;
				MonkeyDeadNum++;
				if(Game.game.heartt > 0) {
					Game.game.bnanaNum = 0;
					Game.game.ActingPower = 0;
					Game.game.heartt--;
					Game.game.Hit = true;
					Game.game.DrawHunterMan = false;
					Game.game.coinnumber.setText(String.valueOf(Game.game.bnanaNum));
				}
				if(Game.game.heartt == 0)
					Game.game.HunterManDead = true;
				if(Game.game.HunterManDead != true)
					Game.game.heartnumber.setText(String.valueOf(Game.game.heartt));
			}
	
		}
		if(m.kind == 5) {
			if((Game.game.MouseX+70 >= m.Xlocation+m.Xsize/5 && Game.game.MouseX+70 <= m.Xlocation+(m.Xsize-m.Xsize/5)) && 
					(Game.game.MouseY+80 >= m.Ylocation+m.Ysize/2 &&
							Game.game.MouseY+80 <= m.Ylocation + (m.Ysize-m.Ysize/3))
					&& Game.game.Hit == false && Game.game.esc == false) {
				m.heart -= 20;
				if(m.heart <= 0) {
					m.MonkeyDead = true;
				}
				Game.game.ActingPower = 0;
				if(Game.game.heartt > 0) {
					Game.game.bnanaNum = 0;
					Game.game.heartt--;
					Game.game.Hit = true;
					Game.game.DrawHunterMan = false;
					Game.game.coinnumber.setText(String.valueOf(Game.game.bnanaNum));
				}
				if(Game.game.heartt == 0)
					Game.game.HunterManDead = true;
				if(Game.game.HunterManDead != true)
					Game.game.heartnumber.setText(String.valueOf(Game.game.heartt));
			}
		}
		
	}
	
	void UpdateScore(Monkey m) {
		if(m.MonkeyDead == true) {
			Game.game.score += m.kind;
			Game.game.Score.setText(String.valueOf(Game.game.score));
		}
		
	}
	

}
