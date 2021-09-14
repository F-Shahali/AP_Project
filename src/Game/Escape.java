package Game;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Escape extends JPanel {
	
//	JButton Continue, exit;
	Image back ;
	String name;
	static Escape es;
	int x,y;
	
	public Escape(String nm, int x, int y) {
		initialize();
		this.name = nm;
		this.x = x;
		this.y = y;
		es = this;
	}

	private void initialize() {
		this.setBounds(600, 250, 200, 200);
//		this.setPreferredSize(new Dimension(300, 200));
		this.setLayout(null);
		this.setOpaque(false);
		
		try {
			BufferedImage image = ImageIO.read(new File("file/insidemenu.png"));
			back = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
		
		JButton Continue = new JButton();
		Continue.setIcon(getImage("file/Picture3.png", 150, 60));
		Continue.setBounds(25, 30, 150, 60);
		set(Continue);
		Continue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(Game.game.ID == -1 && Game.game.type.equals("multi")) {
					Server.Continue++;
					if(Server.Continue == Server.names.getSize()+1) {
						Server.game.Continue(Escape.this, Server.game.firstX, Server.game.firstY);
						for(Handler handler : Server.handlers ) {
							handler.printer.println("Continue");
						}
						Server.Continue = 0;
					}
				}
				if(Game.game.ID != -1) {
					Client.printer.println("Continue");
				}
				
				if(Game.game.type.equals("one")) {
					Game.game.Continue(Escape.this, x, y);
				}
			}
		});
		Continue.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				Continue.setIcon(getImage("file/Picture3.png", 150, 60));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Continue.setIcon(getImage("file/Picture1.png", 150, 60));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		this.add(Continue);
		
		JButton exit = new JButton();
		exit.setIcon(getImage("file/Picture4.png", 150, 60));
		exit.setBounds(25, 110, 150, 60);
		set(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.clip.stop();
				try {
					Main.music("file/menu.wav");
					Main.clip.start();
				} catch (Exception e1) {}
				if(Game.game.type.equals("multi")) {
					if(Game.game.ID == -1) {
						Server.FinishGame();
					}
					if(Game.game.ID != -1) {
						Client.Exit(Game.game.ID);
					}
				}
				Exit(name);
				
			}
		});
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				exit.setIcon(getImage("file/Picture4.png", 150, 60));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				exit.setIcon(getImage("file/Picture2.png", 150, 60));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		Main.frame.pack();
		this.add(exit);
		this.revalidate();
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0,this);
	}
//	public void paint(Graphics g) {
//		paintComponent(g);
//		paintBorder(g);
//		paintChildren(g);
//		g.drawImage(cursor, x, y, this);
//		
//	}
	
    public void set(JButton bt) {
		
		bt.setContentAreaFilled(false);
		bt.setBorderPainted(false);
		bt.setFocusable(false);
		bt.setVisible(true);
		
	}
    public Icon getImage(String st, int w, int h) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(st));
		} catch (IOException e) {}
		Image newbut = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		Icon AddImage = new ImageIcon(newbut);
		
		return AddImage;
	}
    public void Exit(String name) {
    	Game.game.InFile[7] = Game.game.levelInlevel;
		Game.game.InFile[6] = Game.game.level;
		Game.game.InFile[1] = Game.game.heartt;
		Game.game.InFile[2] = Game.game.bnanaNum;
		Game.game.InFile[3] = Game.game.ActingPower;
		Game.game.InFile[4] = Game.game.KindOfarrow;
		Game.game.InFile[5] = Game.game.score;
		Game.game.InFile[8] = Game.game.Time;
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
		Game.game.esc = false;
		Main.frame.setContentPane(new MainMenu(name));
		Main.frame.pack();
		Toolkit tk = getToolkit();
		Game.game.setCursor(tk.createCustomCursor(tk.getImage(""),new Point(),"trans"));
    }
    

}
