package Game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

//import Client_Server.Client;


public class Game extends JPanel {
	
	static BufferedImage image, Bomb, heart, bnana, rocket, HunterMan, cursor2;
	static BufferedImage[] bombs, enfejar;
	static Image[] Bmb = new Image[10], Enfejar = new Image[9];
	 
	static Image cursor, Coco;
	int MouseX , MouseY, firstX, firstY, ID;
	int MonkeyNum = 0, t = 0, M = 0, T = 0, Bx =0, players = 1,
		By =0, clickCount = 0, Xplain = 0, Yplain =0,  Num = 0, nextWave = 0,
		HunterMonkey = 0, Survive = 0, NumberOfArrow = 1, nextlevel = 0,
		TirTime = 0, gameover = 0, bomb = 0, heartt = 0, bnanaNum = 0, ActingPower = 0,
		KindOfarrow = 0, score = 0, level = 0, levelInlevel = 0, Time = 0, ToRotate = 0, Heat = 0, ChangeLocation = 0, DrawHeat = 0;
	String name, type;
	int[] InFile;
	BufferedImage insidemenu, coconut;
	ArrayList<Tir> Arrow = new ArrayList<Tir>();
	public ArrayList<Tir> OtherArrow = new ArrayList<Tir>();;
	Point BombPlace = new Point(), MonkeyPlace = new Point();
	ArrayList<Bomb> NewBomb = new ArrayList<Bomb>(); //distance = new ArrayList<Point>();
	static Timer timer;
	Boolean esc = false, drawEnfejar, drawBomb, WaveFinished = false, EnfejarSound = true, MouseMove = true,
			DrawGroup = false, HunterManDead = false, Hit = false, Gamefinished = false, DrawHunterMan = true, shelliking = false;
	static Game game;
	public MonkeyGroups monkeys;
	ArrayList<HunterMen> HunterMen = new ArrayList<HunterMen>();
	ArrayList<Monkey> m = new ArrayList<Monkey>();
	ArrayList<BufferedImage> MonkeyLogo = new ArrayList<BufferedImage>();
	ArrayList<Point> coco = new ArrayList<Point>();
	ArrayList<Coconut> Coconuts = new ArrayList<Coconut>();
	ArrayList<Bnana> bnanas = new ArrayList<Bnana>();
	ArrayList<PowerUp> powerup = new ArrayList<PowerUp>();
	ArrayList<PowerUp> Gift = new ArrayList<PowerUp>();
	ArrayList<GhoolTir> Ghooltir = new ArrayList<GhoolTir>();
	JTextField  Score, heartnumber, coinnumber, rocketnumber;
	public boolean StartDrawGhoolTir = false, Gameover = false, IncreasingHeat = true, reduceHeat = false;

	public Game(String name, int id, String type) {
		super();
		game = this;
		this.name = name;
		this.type = type;
		ID = id;
		MouseY = firstY = 670;
		initialize();
		SetLevel(levelInlevel);
		
	}
	
	private void initialize() {
		
		MouseX = firstX = (ID+1)*170;
		if(type.equals("one")) {
			MouseX = firstX = 683;
		}
		if(type.equals("multi"))
			MouseMove = false;

		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLayout(null);
		try {
			InFile = new int[9];
			String lines;
			int i =0;
			BufferedReader bw = new BufferedReader(new FileReader("file/" + name + ".data"));
			while((lines = bw.readLine()) != null) {
				InFile[i] = Integer.valueOf(lines);
				i++;
			}
			bw.close();
			bomb = InFile[0]; heartt = InFile[1]; bnanaNum = InFile[2]; ActingPower = InFile[3];
			KindOfarrow = InFile[4]; score = InFile[5]; level = InFile[6]; levelInlevel = InFile[7]; Time = InFile[8];
			
			
		} catch (Exception e) {}
		
		
		if(esc == false) {
			Toolkit tk = getToolkit();
			Game.this.setCursor(tk.createCustomCursor(tk.getImage(""),new Point(),"trans"));
		}
		try {
			Robot robot = new Robot();
			robot.mouseMove(firstX, 600);
		} catch (AWTException e) {}
		
		Main.clip.stop();
		try {
			if(levelInlevel == 5)
				Main.music("file/Bazi5.wav");
			else
				Main.music("file/Bazi" + level + ".wav");
				
		} catch (Exception e1) {}
		
		
		try {
			Bomb = ImageIO.read(new File("file/bomb.png"));
			image = ImageIO.read(new File("file/jungle.jpg"));
			
			 if(type.equals("multi")) {
				 if(ID == -1) {
					 HunterMan = ImageIO.read(new File("file/Server.png"));
					 for(int i = 0; i < Server.names.getSize(); i++) {
						HunterMen player = new HunterMen(i);
						player.x = player.firstx;
						player.y = player.firsty;
						HunterMen.add(player);
						players++;
					 }
				 }
				 if(ID != -1){
					 HunterMan = ImageIO.read(new File("file/hunterMan" + (ID+2) + ".png"));
					 HunterMen server = new HunterMen(-1);
					 server.x = server.firstx;
					 server.y = server.firsty;
					 HunterMen.add(server);
					 for(int i = 0; i < Client.names.getSize(); i++) {
						 players++;
						 if(ID != i) {
							 HunterMen player = new HunterMen(i);
							 player.x = player.firstx;
							 player.y = player.firsty;
							 HunterMen.add(player);
						 }
					 }	
				 }
			 }
			 else
				 HunterMan = ImageIO.read(new File("file/hunterMan1.png"));
				 
			 heart = ImageIO.read(new File("file/heart.png"));
			 bnana = ImageIO.read(new File("file/cursor1.png"));
			 rocket = ImageIO.read(new File("file/rocket.png"));
			 insidemenu = ImageIO.read(new File("file/insidemenu.png"));
			 cursor2 = ImageIO.read(new File("file/cursor2.png"));
		}
		catch (IOException e) {}
		
		
		JLabel H = new JLabel(); setLabel(H, heart, 10, 700, 40, 40); this.add(H);
		JLabel C = new JLabel(); setLabel(C, bnana, 190, 700, 40, 40); this.add(C);
		JLabel R = new JLabel(); setLabel(R, rocket, 100, 700, 40, 40); this.add(R);

		coinnumber = new JTextField(String.valueOf(bnanaNum)); coinnumber.setBounds(235, 705, 40, 40);
		setText(coinnumber);this.add(coinnumber);
		rocketnumber = new JTextField(String.valueOf(bomb)); rocketnumber.setBounds(145, 705, 40, 40);
		setText(rocketnumber);this.add(rocketnumber);
		heartnumber = new JTextField(String.valueOf(heartt)); heartnumber.setBounds(55, 705, 40, 40);
		setText(heartnumber);this.add(heartnumber);
		Score = new JTextField(String.valueOf(score)); Score.setBounds(25, 13, 100, 30);
		setText(Score);this.add(Score);
		
		cursor = HunterMan.getScaledInstance(150, 160, java.awt.Image.SCALE_SMOOTH);

		Timer tm = new Timer(new Tir(KindOfarrow, ActingPower).Speed, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(esc == false && HunterManDead == false && DrawHunterMan == true) {
					if(type == "multi") {
						if(ID == -1) {
							Server.addTir(Xplain, Yplain, KindOfarrow, NumberOfArrow);
						}
						if(ID != -1) {
							Client.addTir(Xplain, Yplain, KindOfarrow, NumberOfArrow);
						}
					}
					if(IncreasingHeat == true)
						AddArrow(Xplain, Yplain, KindOfarrow, NumberOfArrow, Arrow, ActingPower);
				}
				repaint();
			}
		});
		
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(MouseMove == true && HunterManDead == false) {
					MouseX = e.getX() - 35; MouseY = e.getY() - 80;
					Xplain = e.getX(); Yplain = e.getY();
					if(type == "multi") {
						if(ID == -1) {
							Server.mouseMoved(-1, MouseX, MouseY);
						}
						if(ID != -1) {
							Client.mouseMoved(ID, MouseX, MouseY);
						}
					}
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(MouseMove == true && HunterManDead == false) {
					MouseX = e.getX() - 35; MouseY = e.getY() - 80;
					Xplain = e.getX(); Yplain = e.getY();
					if(type == "multi") {
						if(ID == -1) {
							Server.mouseMoved(ID, MouseX, MouseY);
						}
						if(ID != -1) {
							Client.mouseMoved(ID, MouseX, MouseY);
						}
					}
				}
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Heat>0) {
					reduceHeat = true;
				}
				tm.stop();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && HunterManDead == false)
					reduceHeat = false;
					tm.start();
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && HunterManDead == false && DrawHunterMan == true) {
					if(type == "multi") {
						if(ID == -1) {
							Server.addTir(Xplain, Yplain, KindOfarrow, NumberOfArrow);
						}
						if(ID != -1) {
							Client.addTir(Xplain, Yplain, KindOfarrow, NumberOfArrow);
						}
//						Client.addTir(Xplain, Yplain, KindOfarrow, NumberOfArrow);
					}
					
//						for(int i = 0; i < NumberOfArrow; i++) {
//							tir = new Tir(KindOfarrow);
						AddArrow(Xplain, Yplain, KindOfarrow, NumberOfArrow, Arrow, ActingPower);
//							n++;
//						}
//						if(IncreasingHeat == true) {
//							Heat += tir.IncreasingHeat;
//							if(Heat >= 100) {
//								IncreasingHeat = false;
//							}
//						}
						reduceHeat = false;
					
				}
				if(SwingUtilities.isRightMouseButton(e) && HunterManDead == false) {
					Bomb b = new Bomb();
					b.x = e.getX();
					b.y = e.getY();
					if(e.getX()>=683 && e.getY()<=384) {
						Bx = e.getX() - 683; By = 384 - e.getY();
						b.area = 1;
					}else
					
						if(e.getX()<=683 && e.getY()<=384) {
							Bx = 683 - e.getX(); By = 384 - e.getY();
							b.area = 2;
						}else
					
							if(e.getX()<=683 && e.getY()>=384) {
								Bx = 683 - e.getX(); By =  e.getY() - 384;
								b.area = 3;
							}else
					
								if(e.getX()>=683 && e.getY()>=384) {
									Bx = e.getX() - 683; By = e.getY() - 384;
									b.area = 4;
								}
//					b.SetImage();
					double dist = Math.sqrt(Bx*Bx + By*By);
					b.HowManyFiveSingle = (int)(dist/10);
					b.XdistanceToCentre = Bx/b.HowManyFiveSingle;
					b.YdistanceToCentre = By/b.HowManyFiveSingle;
					if(bomb > 0) {
						b.drawBomb = true;
						b.drawEnfejar = true;
						NewBomb.add(b);
						bomb--;
						InFile[0]--;
						rocketnumber.setText(String.valueOf(bomb));
						revalidate();
						repaint();
					}
				}
			}
		});
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 27 && esc == false && HunterManDead == false) {
					if(type.equals("multi")) {
						if(ID == -1) {
							Server.Escape();
						}
						if(ID != -1) {
							Client.Escape();
						}
					}
					EscapePress();
				}
				
			}
		});
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(IncreasingHeat == false) {
					DrawHeat++;
					if(DrawHeat == 80) {
						IncreasingHeat = true;
						DrawHeat = 0;
					}
				}
				if(reduceHeat == true) {
					Heat -= 2;
				}
				Time++;
				if(Time == 60) {
					MouseMove = true;
				}
				if(Hit == true) {
					Survive++;
					if(Survive == 100) {
						try {
							Robot robot = new Robot();
							robot.mouseMove(firstX, firstY);
							MouseX = firstX;
							MouseY = firstY;
							DrawHunterMan = true;
							if(type.equals("multi")) {
								if(ID == -1) {
									Server.ShowHunterMan();
									Server.mouseMoved(-1, firstX, firstY);
								}
								if(ID != -1) {
									Client.ShowHunterMan(ID);
									Client.mouseMoved(ID, firstX, firstY);
								}
							}
						} catch (AWTException e) {}
					}
					if(Survive == 140) {
						Hit = false;
						Survive = 0;
					}
				}
				if(monkeys.StartWave == false) {
					t++;
				}
				if(t == 20 && monkeys.GroupDead == false && levelInlevel != 5) {
//					Random r = new Random();
					if(ID == -1) {
						for(int i = 0; i< m.size(); i++) {
							if(m.get(i).Drawing == true)
								m.get(i).ProbabiltyOfCoconut(i);
						}
					}
					t = 0;
				}
				if(levelInlevel == 2) {
					M++;
					if(M == 50 && ID == -1) {
						Random c = new Random();
						int x = c.nextInt(1200);
						int y = c.nextInt(700);
						monkeys.SetCenter(x, y);
//						System.out.println(monkeys.XEnd + "  " + monkeys.YEnd);
						M = 0;
					}
				}
				if(levelInlevel == 4) {
					HunterMonkey++;
					M++;
					if(M == 70) {
						if(ID == -1) {
							for(int i = 0; i<m.size(); i++) {
								if(m.get(i).hunter == false) {
									Random x = new Random();
									int XEnd = x.nextInt(1200);
									int YEnd = x.nextInt(700);
									m.get(i).SetLocation(i, XEnd, YEnd);
								}
							}
						}
						M = 0;
					}
					if(HunterMonkey == 200) {
						if(m.size() != 0) {
							Random r = new Random();
							int a = r.nextInt(m.size());
							m.get(a).hunter = true;
							m.get(a).XEnd = MouseX+35;
							m.get(a).YEnd = MouseY+80;
							int XCenterToCenter = m.get(a).XEnd - m.get(a).Xlocation;
							int YCenterToCenter = m.get(a).YEnd - m.get(a).Ylocation;
							m.get(a).Zavie = Math.atan2(YCenterToCenter, XCenterToCenter);
//							m.get(a).hunter = false;
							HunterMonkey = 0;
						}
					}
				}
				if(monkeys.LevelFinished == true && Gamefinished == false) {
					nextlevel++;
					if(nextlevel == 70) {
						StartDrawGhoolTir = false;
						level++;
						levelInlevel = 1;
						SetLevel(levelInlevel);
						try {
							Main.clip.stop();
							Main.music("file/Bazi" + level + ".wav");
						} catch (Exception e) {}
						monkeys.LevelFinished = false;
						nextlevel = 0;
					}
				}
				if(monkeys.GroupDead == true && Gamefinished == false && monkeys.LevelFinished == false) {
					levelInlevel++;
					SetLevel(levelInlevel);
				}
				if(monkeys.StartWave == true) {
					nextWave++;
					if(nextWave == 60) {
//						levelInlevel++;
//						System.out.println(levelInlevel);
						monkeys.StartWave = false;
						nextWave = 0;
//						SetLevel(levelInlevel);
					}
				}
				if(levelInlevel == 5 && StartDrawGhoolTir == true && m.size() != 0) {
					TirTime++;
					ChangeLocation++;
					if(ChangeLocation == 100) {
						Random x = new Random();
						int XEnd = x.nextInt(500) + 200;
						int YEnd = x.nextInt(230) - 180;
						m.get(0).SetLocation(0, XEnd, YEnd);
						ChangeLocation = 0;
					}
					if(TirTime == 10) {
						Random r = new Random();
						for(int i = 0; i<8; i++) {
							if(r.nextInt(101)<=25) {
								GhoolTir tir = new GhoolTir(i);
								Sounds s = new Sounds("file/GhoolTir.wav");
								Ghooltir.add(tir);
							}
						}
						TirTime=0;
					}
				}
				if(Gamefinished == true || Gameover == true) {
					gameover++;
					if(gameover == 140) {
						if(type.equals("one"))
							timer.stop();
						InFile[7] = levelInlevel;
						InFile[6] = level;
						InFile[1] = heartt;
						InFile[2] = bnanaNum;
						InFile[3] = ActingPower;
						InFile[4] = KindOfarrow;
						InFile[5] = score;
						InFile[8] = Time;
						try{
							FileWriter fw = new FileWriter("file/" + name + ".data");
							BufferedWriter pw = new BufferedWriter(fw);
							for(int i = 0; i<Game.game.InFile.length; i++) {
								pw.append(String.valueOf(Game.game.InFile[i]));
								pw.newLine();
							}
							pw.flush();
							pw.close();
						}catch(Exception w) {}
						if(type.equals("one")) {
							Main.clip.stop();
							try {
								Main.music("file/menu.wav");
							} catch (Exception e) {}
							Main.frame.setContentPane(new Ranking());
							Main.frame.pack();
						}
					}
				}
				if(HunterManDead == true) {
					gameover++;
					if(gameover == 50) {
						Gameover = true;
						HunterManDead = false;
						gameover = 0;
					}
				}
				if(monkeys.ChangingShape == true) {
					ToRotate++;
					if(ToRotate == 3) {
						monkeys.ChangingShape = false;
						ToRotate = 0;
					}
				}
//				revalidate();
				repaint();
			}
	
		});
		timer.start();
		if(levelInlevel == 1 && level == 1 && type.equals("multi")) {
			EscapePress();
		}
	}
	
	public void AddArrow(int x, int y, int KindOfarrow, int NumberOfArrow, ArrayList<Tir> Arrow, int ActingPower) {
		if(esc == false) {
			Sounds shellik = new Sounds("file/TirMusic.wav");
			Tir t = null;
			int n = 0;
			for(int i = 0; i < NumberOfArrow; i++) {
				t = new Tir(KindOfarrow, ActingPower);
				if(IncreasingHeat == true) {
					Heat += t.IncreasingHeat;
					if(Heat >= 100) {
						IncreasingHeat = false;
					}
				}
				if(NumberOfArrow == 1) {
					t.degree = 90;
					t.x = x+35;
					t.y = y-98;
				}
				if(NumberOfArrow == 2) {
					t.degree = 90;
					t.x = x+30 + n*10;
					t.y = y-98;
				}
				if(NumberOfArrow == 3) {
					t.degree = 95-n*5;
					t.x = x+25 + n*10;
					t.y = y-(95+(n%2)*3);
				}
				if(NumberOfArrow == 4) {
					if(n == 2)
						t.degree = 90;
					else {
						if(n == 3)
							t.degree = 80;
						else
							t.degree = 100-n*10;
					}
					t.x = x+ 27 + n*5;
					if(n == 2)
						t.y = y-98;
					else
						t.y = y-(95+(n%3)*3);
				}
				try {
					t.tir = ImageIO.read(new File("file/Tir/" + KindOfarrow + "-" + t.degree + ".png"));
				} catch (IOException l) {}
				Arrow.add(t);
				n++;
			}
		
		}
		revalidate();
		repaint();
	}

	public void SetLevel(int levelInlevel) {
		int num = 0;
		if(levelInlevel == 1) 
			num = 32;
		if(levelInlevel == 2) 
			num = 12;
		if(levelInlevel == 3) 
			num = 15;
		if(levelInlevel == 4) 
			num = 18;
		if(level == 1) {
			monkeys = new MonkeyGroups(num, 1, m);
		}
		if(level == 2) {
			monkeys = new MonkeyGroups(num, 2, m);
		}
		if(level == 3) {
			monkeys = new MonkeyGroups(num, 3, m);
		}
		if(level == 4)
			monkeys = new MonkeyGroups(num, 4, m);
		if(levelInlevel == 4) {
			for(int i = 0; i<m.size()/2; i++) {
				m.get(i).Ylocation = 20+i*40;
				m.get(i).Xlocation = -5;
			}
			for(int i = m.size()/2; i<m.size(); i++) {
				m.get(i).Ylocation = 20+i*30;
				m.get(i).Xlocation = 1366;
			}
		}
		if(levelInlevel == 5) {
			monkeys = new MonkeyGroups(1, 5, m);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setFocusable(true);
		g.drawImage(image, 0, 0,this);
		if(DrawHunterMan == true && HunterManDead == false) {
			g.drawImage(cursor, MouseX, MouseY, this);
		}
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).DrawHunterMan == true && HunterMen.get(i).HunterManDead == false)
				g.drawImage(HunterMen.get(i).image, HunterMen.get(i).x, HunterMen.get(i).y, this);
		}
		g.drawImage(insidemenu, 5, 5, 400, 40, this);
		Graphics2D g2 = (Graphics2D)(g);
		HeatShowing(g2);
		for(int i = 0; i< Arrow.size(); i++) {
			if(Arrow.get(i).drawing == true) {
				Arrow.get(i).XNow = Arrow.get(i).x + (int)(Arrow.get(i).Distance*10 * Math.cos((Math.PI*Arrow.get(i).degree)/180));
				Arrow.get(i).YNow = Arrow.get(i).y - Arrow.get(i).Distance*10;
				g2.drawImage(Arrow.get(i).tir,
						Arrow.get(i).XNow, Arrow.get(i).YNow, this);
				if(Arrow.get(i).Distance > 1000) {
					Arrow.remove(i);
				}else
					Arrow.get(i).Distance++;
			}
//			if(Arrow.get(i).drawing == false)
//				Arrow.remove(i);
		}
		for(int i = 0; i< OtherArrow.size(); i++) {
			if(OtherArrow.get(i).drawing == true) {
				OtherArrow.get(i).XNow = OtherArrow.get(i).x + (int)(OtherArrow.get(i).Distance*10 * Math.cos((Math.PI*OtherArrow.get(i).degree)/180));
				OtherArrow.get(i).YNow = OtherArrow.get(i).y - OtherArrow.get(i).Distance*10;
				g2.drawImage(OtherArrow.get(i).tir,
						OtherArrow.get(i).XNow, OtherArrow.get(i).YNow, this);
				if(OtherArrow.get(i).Distance > 1000) {
					OtherArrow.remove(i);
				}else
					OtherArrow.get(i).Distance++;
			}
//			if(Arrow.get(i).drawing == false)
//				Arrow.remove(i);
		}
		if(HunterManDead == true) {
			g2.setColor(new Color(153, 0, 0));
			g2.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 60));
			g2.drawString("Game Over !!", 580, 310);
		}
		if(HunterManDead == true || Gamefinished == true) {
			g2.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 40));
			g2.drawString("Your Scor: " + score, 600, 360);
		}
		DrawCococnut(g2);
		DrawBnana(g2);
		DrawPowerUp(g2);
		DrawGift(g2);
		if(monkeys.LevelFinished == false)
			DrawGroup(g2, m, Arrow);
		if(levelInlevel == 5)
			DrawGhoolTir(g2);
		
		if(monkeys.LevelFinished == true) {
			g.setColor(new Color(153, 0, 0));
			g.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 40));
			g.drawString("\" Congratulation \"", 550, 310);
		}
		for(int i =0; i< NewBomb.size() ; i++) {
			if(NewBomb.get(i).area == 1 && NewBomb.get(i).drawBomb == true) {
				g2.drawImage(Bomb, NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance,
						NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance, this);
				NewBomb.get(i).EnfejarPlace.x = NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance;
				NewBomb.get(i).EnfejarPlace.y = NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance;
			}
			
			if(NewBomb.get(i).area == 2 && NewBomb.get(i).drawBomb == true) {
				g2.drawImage(Bomb, NewBomb.get(i).x + NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance,
						NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance, this);
				NewBomb.get(i).EnfejarPlace.x = NewBomb.get(i).x + NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance;
				NewBomb.get(i).EnfejarPlace.y = NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance;
			}
			
			if(NewBomb.get(i).area == 3 && NewBomb.get(i).drawBomb == true) {
				g2.drawImage(Bomb, NewBomb.get(i).x + NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance,
						NewBomb.get(i).y - NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance, this);
				NewBomb.get(i).EnfejarPlace.x = NewBomb.get(i).x + NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance;
				NewBomb.get(i).EnfejarPlace.y = NewBomb.get(i).y - NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance;
			}
			
			if(NewBomb.get(i).area == 4 && NewBomb.get(i).drawBomb == true) {
				g2.drawImage(Bomb, NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance,
						NewBomb.get(i).y - NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance, this);
				NewBomb.get(i).EnfejarPlace.x = NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance;
				NewBomb.get(i).EnfejarPlace.y = NewBomb.get(i).y - NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance;
			}
			
			if(NewBomb.get(i).distance >= NewBomb.get(i).HowManyFiveSingle) {
				NewBomb.get(i).drawBomb = false;
				if(EnfejarSound == true) {
					Sounds enfejar = new Sounds("file/Enfejar.wav");
					EnfejarSound = false;
				}
//				monkeys.GroupDead = true;
				if(NewBomb.get(i).EnfejarNum <= 8) {
//					System.out.println("in" + t);
					g2.drawImage(NewBomb.get(i).Enfejar[NewBomb.get(i).EnfejarNum], NewBomb.get(i).EnfejarPlace.x, NewBomb.get(i).EnfejarPlace.y, this);
					NewBomb.get(i).EnfejarNum++;
					if(NewBomb.get(i).EnfejarNum == 9) {
						if(monkeys.kind != 5) {
							monkeys.releaseBomb = true;
//							monkeys.DeleteGroup(m);
						}
						if(monkeys.kind == 5) {
							m.get(0).heart -= 50;
							if(m.get(0).heart <= 0) {
								m.get(0).MonkeyDead = true;
							}
						}
						NewBomb.remove(i);
						EnfejarSound = true;
						break;
					}
				}
				
			}else {
				NewBomb.get(i).distance++;
				if( NewBomb.get(i).BombNum == 9)
					NewBomb.get(i).BombNum = 0;
				else
					NewBomb.get(i).BombNum++;
			}
		}
			
	}
	void DrawGroup(Graphics2D g2, ArrayList<Monkey> m, ArrayList<Tir> Arrow) {
		if(levelInlevel == 1) {
			if(monkeys.StartWave == true)
				StartWaveMessage(levelInlevel, g2);
			if(monkeys.StartWave == false)
				monkeys.RectangularGroup(m, Arrow, g2);
		}
		if(levelInlevel == 2) {
			if(monkeys.StartWave == false)
				monkeys.CircularGroup(g2, m, Arrow);
			if(monkeys.StartWave == true) {
				StartWaveMessage(levelInlevel, g2);
			}
		}
		if(levelInlevel == 3) {
			if(monkeys.StartWave == false)
				monkeys.RotatoryGroup(g2, m, Arrow);
			if(monkeys.StartWave == true) {
				StartWaveMessage(levelInlevel, g2);
				g2.setColor(new Color(0, 204, 0));
				g2.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 35));
				g2.drawString("Safe Place", 620, 400);
			}
		}
		if(levelInlevel == 4) {
			if(monkeys.StartWave == true) {
				StartWaveMessage(levelInlevel, g2);
			}
			if(monkeys.StartWave == false)
				monkeys.Entehary(g2, m, Arrow);
		}
		if(levelInlevel == 5) {
			if(monkeys.StartWave == true)
				StartWaveMessage(5, g2);
			if(monkeys.StartWave == false) {
				monkeys.Ghool(g2, Arrow, m);
			}
		}
	}
	void StartWaveMessage(int i, Graphics2D g2) {
//		monkeys.DeleteGroup(m);
		g2.setFont(new Font("IranNastaliq", Font.ROMAN_BASELINE, 60));
		g2.setColor(new Color(102,51,0));
		g2.drawString("Level  " + level, 585, 310);
		g2.setFont(new Font("IranNastaliq", Font.ROMAN_BASELINE, 40));
		g2.drawString("Wave  " + i, 615, 360);
		
	}
	
	void setLabel(JLabel Label, BufferedImage bi, int a, int b, int c, int d) {
		Image im;
		im = bi.getScaledInstance(c, d, java.awt.Image.SCALE_SMOOTH);
		Label.setIcon(new ImageIcon(im));
		Label.setBounds(a, b, c, d);
	}
	void DrawBomb(Graphics2D g, int i) {
		g.drawImage(NewBomb.get(i).bombs[NewBomb.get(i).BombNum], NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance,
				NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance, this);
		NewBomb.get(i).EnfejarPlace.x = NewBomb.get(i).x - NewBomb.get(i).XdistanceToCentre*NewBomb.get(i).distance;
		NewBomb.get(i).EnfejarPlace.y = NewBomb.get(i).y + NewBomb.get(i).YdistanceToCentre*NewBomb.get(i).distance;
	}
	void DrawCococnut(Graphics2D g2) {
		for(int t = 0 ; t< Coconuts.size(); t++) {
			 monkeys.DeleteCoconut(Coconuts.get(t));
			 if(Coconuts.get(t).Drawing == false)
				 Coconuts.remove(t);
		}
		for(int t = 0 ; t< Coconuts.size(); t++) {
			if(Coconuts.get(t).Drawing == true) {
				g2.drawImage(Coconuts.get(t).coconut,
						Coconuts.get(t).X, Coconuts.get(t).Y + Coconuts.get(t).fallCounter*Coconuts.get(t).coconutSpeed, null);
				if(Coconuts.get(t).fallCounter >800)
					Coconuts.remove(t);
				else
					Coconuts.get(t).fallCounter++;
			}
		}
	}
	void DrawBnana(Graphics2D g) {
		for(int i = 0 ; i<bnanas.size(); i++) {
			monkeys.GetBnana(bnanas.get(i));
			monkeys.DeleteBnana(Arrow, bnanas.get(i));
			if(bnanas.get(i).Drawing == false) {
				bnanas.remove(i);
			}
		}
		for(int i = 0 ; i<bnanas.size(); i++) {
				g.drawImage(bnanas.get(i).bnana, bnanas.get(i).X,
						bnanas.get(i).Y + bnanas.get(i).fallCounter*5, null);
				if(bnanas.get(i).fallCounter >800)
					bnanas.remove(i);
				else
					bnanas.get(i).fallCounter++;
		}
	}
	void DrawGhoolTir(Graphics2D g) {
		for(int i = 0; i < Ghooltir.size(); i++) {
			Ghooltir.get(i).HitToHunterMan();
			if(Ghooltir.get(i).drawing == false)
				Ghooltir.remove(i);
		}
		for(int i = 0; i< Ghooltir.size(); i++) {
			if(Ghooltir.get(i).drawing == true) {
				Ghooltir.get(i).Xlocation = Ghooltir.get(i).X + (int)(Ghooltir.get(i).Counter*6*(Math.cos((Math.PI*Ghooltir.get(i).Zavie)/180)/Math.sin(Math.PI/4)));
				Ghooltir.get(i).Ylocation = Ghooltir.get(i).Y - (int)(Ghooltir.get(i).Counter*6*(Math.sin((Math.PI*Ghooltir.get(i).Zavie)/180)/Math.sin(Math.PI/4)));
				g.drawImage(Ghooltir.get(i).tir,
						Ghooltir.get(i).Xlocation,
						Ghooltir.get(i).Ylocation, this);
				if(Ghooltir.get(i).Counter > 300) {
					Ghooltir.remove(i);
				}else
					Ghooltir.get(i).Counter++;
			}
		}
	}
	void DrawPowerUp(Graphics2D g) {
		for(int i = 0; i < powerup.size(); i++) {
			monkeys.GetPower(powerup.get(i));
			if(powerup.get(i).Drawing == false)
				powerup.remove(i);
		}
		for(int i = 0; i < powerup.size(); i++) {
			if(powerup.get(i).Drawing == true) {
				g.drawImage(powerup.get(i).Images.get(powerup.get(i).ImageNum), powerup.get(i).X,
						powerup.get(i).Y + powerup.get(i).fallCounter*5, null);
				powerup.get(i).ImageNum++;
				if(powerup.get(i).ImageNum == 25)
					powerup.get(i).ImageNum = 0;
				if(powerup.get(i).fallCounter >800)
					powerup.remove(i);
				else
					powerup.get(i).fallCounter++;	
			}
		}
	}
	void HeatShowing(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawRect(135, 9, 100, 30);
		if(Heat > 0) {
			g.setColor(Color.RED);
			g.fillRect(135, 9, Heat, 30);
		}
	}
	
	void DrawGift(Graphics2D g) {
		for(int i = 0; i < Gift.size(); i++) {
			monkeys.GetGift(Gift.get(i));
			if(Gift.get(i).Drawing == false)
				Gift.remove(i);
		}
		for(int i = 0; i < Gift.size(); i++) {
			if(Gift.get(i).Drawing == true) {
				g.drawImage(Gift.get(i).gift, Gift.get(i).X,
						Gift.get(i).Y + Gift.get(i).fallCounter*5, null);
				if(Gift.get(i).fallCounter >800)
					Gift.remove(i);
				else
					Gift.get(i).fallCounter++;	
			}
		}
	}
	void setText(JTextField jf) {
		jf.setOpaque(false);
		jf.setFont(new Font("Dyuthi", Font.BOLD, 25));
		jf.setForeground(Color.WHITE);
		jf.setEditable(false);
		jf.setBorder(new LineBorder(Color.BLACK,0));
		jf.setVisible(true);
		Main.frame.pack();
		
	}
	void EscapePress() {
		if(esc == false) {
			esc = true;
			Escape insidemenu = new Escape(Game.this.name, MouseX, MouseY);
			this.MouseMove = false;
			timer.stop();
			Image cursor = cursor2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
			Game.this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,new Point(0,0),"custom cursor"));
			Game.this.add(insidemenu);
			insidemenu.setVisible(true);
			
		}
	}
	void mouseMoved(int id, int x, int y) {
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).ID == id) {
				HunterMen.get(i).x = x;
				HunterMen.get(i).y = y;
				repaint();
			}
		}
	}
	void NonShowHunterMan(int id) {
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).ID == id) {
				HunterMen.get(i).DrawHunterMan = false;
				repaint();
			}
		}
	}
	public void ShowHunterMan(int id) {
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).ID == id) {
				HunterMen.get(i).DrawHunterMan = true;
				repaint();
			}
		}
		
	}
	void AddCoconut(Monkey m) {
		Coconut c = new Coconut(m.Xlocation+60, m.Ylocation+130, m.CoconutSpeed);
		Game.game.Coconuts.add(c);
		Sounds s = new Sounds("file/Coconut.wav");
		repaint();
	
	}
	public void Continue(Escape es, int x, int y) {
    	es.setVisible(false);
		this.remove(es);
		this.esc = false;
		Game.game.MouseMove = true;
		this.MouseX = x;
		this.MouseY = y;
		this.timer.start();
		Main.frame.pack();
		Toolkit tk = getToolkit();
		this.setCursor(tk.createCustomCursor(tk.getImage(""),new Point(),"trans"));
		try {
			Robot robot = new Robot();
			robot.mouseMove(x+35, y+80);
		} catch (AWTException e) {}
    }

	public void HunterManDead(int id) {
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).ID == id) {
				HunterMen.get(i).DrawHunterMan = false;
				HunterMen.get(i).HunterManDead = true;
				repaint();
			}
		}
		
	}

	public void Exit(int id) {
		for(int i = 0; i < HunterMen.size(); i++) {
			if(HunterMen.get(i).ID == id) {
				HunterMen.remove(i);
			}
		}
	}
	public void DrawMonkey(int i,int monkeyNum, int x, int y) {
		
	}


}
