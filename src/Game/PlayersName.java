package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class PlayersName extends JPanel{
	ArrayList<JTextField> names = new ArrayList<JTextField>();
	ArrayList<String> name = new ArrayList<String>();
	int x, y, Xlocation, Ylocation, ID;
	private BufferedImage pic;
	private Image cursor;
	private BufferedImage image;
	private int SizeBefore = 0;
	static boolean isServer;
	String playerName;
	
	public PlayersName(String type, String name, int id) {
		super();
		Ylocation = 700;
		if(type == "server") {
			isServer = true;
		}
		if(type == "client") {
			ID = id;
			isServer = false;
		}
		playerName = name;
		initialize();
		
	}

	private void initialize() {
		this.setLayout(null);
		this.setPreferredSize((Toolkit.getDefaultToolkit().getScreenSize()));
		this.requestFocus();
		this.setFocusable(true);
		try {
			pic = ImageIO.read(new File("file/cursor2.png"));
		} catch (IOException e) {}

		Image cursor = pic.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Toolkit tk = getToolkit();
		this.setCursor(tk.createCustomCursor(cursor,new Point(0, 0),"custom cursor"));
		try {
//			pic = ImageIO.read(new File("file/cursor1.png"));
//			cursor = pic.getScaledInstance(40, 50, java.awt.Image.SCALE_SMOOTH);
			image = ImageIO.read(new File("file/jungle3.jpg"));
		} catch (IOException e) {}
//		 if(name.size() > 0) {
//			System.out.println("In");
//			JTextField one = new JTextField(name.get(0));
//			this.add(one);
//		}
		if(isServer == true) {
			if(Server.names.getSize() > 0) {
				for(int i = 0; i < Server.names.getSize(); i++) {
					JTextField one = new JTextField(i +" )  " + Server.names.getElementAt(i));
					names.add(one);
					addJtextField(names, i);
			}
				SizeBefore  = Server.names.getSize();
			}
		}
		if(isServer == false) {
			if(Client.names.getSize() > 0) {
				for(int i = 0; i < Client.names.getSize(); i++) {
					JTextField one = new JTextField(i + " )  " + Client.names.getElementAt(i));
					names.add(one);
					addJtextField(names, i);
			}
				SizeBefore  = Client.names.getSize();
			}
		}
		Timer tm = new Timer(500, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isServer == true) {
						if(Server.names.getSize() > SizeBefore) {
							JTextField one = new JTextField(Server.names.getSize()-1 +" )  " + Server.names.getElementAt(Server.names.getSize()-1));
//							one.addActionListener(new ActionListener() {
//								@Override
//								public void actionPerformed(ActionEvent arg0) {
//																		
//								}
//							});
							names.add(one);
							addJtextField(names, Server.names.getSize()-1);
							SizeBefore = Server.names.getSize();
						}
					}
					if(isServer == false) {
						if(Client.names.getSize() > SizeBefore) {
							JTextField one = new JTextField(Client.names.getSize()-1 +" )  " + Client.names.getElementAt(Client.names.getSize()-1));
							names.add(one);
							addJtextField(names, Client.names.getSize()-1);
							SizeBefore = Client.names.getSize();
						}
					}
					repaint();
				}
		});
		 tm.start();
		 
//		for(int i = 0; i<name.size(); i++) {
//			JTextField jb = new JTextField((i+1) +" )  " + name.get(i));
//			names.add(jb);
//			addJtextField(names, i);
//		}
		
		JButton exit = new JButton();
		exit.setIcon(getImage("file/bt3.png", 200, 70));
		exit.setBounds(750, 600, 200, 70);
		set(exit);
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				exit.setIcon(getImage("file/bt3.png", 200, 70));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				exit.setIcon(getImage("file/bt32.png", 200, 70));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				names.clear();
				Main.frame.setContentPane(new MainMenu(MainMenu.menu.name));
				Main.frame.pack();
				
			}
		});
		this.add(exit);
		
		JButton enter = new JButton();
		enter.setIcon(getImage("file/enter1.png", 200, 70));
		enter.setBounds(450, 600, 200, 70);
		set(enter);
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Server.FinishReading = true;
				Game game = new Game(playerName, -1, "multi");
				game.requestFocus();
				game.setFocusable(true);
				Server.game = game;
				Main.frame.setContentPane(game);
				Main.frame.pack();
				
			}
		});
		enter.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				enter.setIcon(getImage("file/enter1.png", 200, 70));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				enter.setIcon(getImage("file/enter2.png", 200, 70));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		if(isServer) {
			this.add(enter);
		}
		
		
	}
	private void addJtextField(ArrayList<JTextField> n, int i) {
		if(i/7 <= 1)
			n.get(i).setBounds(300, 100+(i*50), 300, 30);
		if(i/8 >= 1)
			n.get(i).setBounds(((int)i/8 + 1) * 300 + 50, 100 + (i%8) * 50, 300, 30);
		n.get(i).setFocusable(false);
		n.get(i).setVisible(true);
		n.get(i).setFont(new Font("Dyuthi", Font.BOLD, 30));
		n.get(i).setForeground(Color.YELLOW);
		n.get(i).setOpaque(false);
		n.get(i).setEditable(false);
		n.get(i).setBorder(new LineBorder(Color.BLACK,0));
		this.add(n.get(i));
		
	}
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
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
//	public void paint(Graphics g) {
//		paintComponent(g);
//		paintBorder(g);
//		paintChildren(g);
//		g.drawImage(cursor, x, y, this);
//		
//	}

}
