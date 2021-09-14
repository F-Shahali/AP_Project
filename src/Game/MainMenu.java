package Game;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MainMenu extends JPanel{
	
	BufferedImage image;
	BufferedImage img;
	BufferedImage pic;
	JButton Continue;
	JButton start;
	JButton exit;
	JButton Rank;
	String name;
	Image cursor;
	int x, y;
	int bomb = 0, heart = 0, coin = 0, powerOfarrow = 0, KindOfarrow = 0, score = 0, level = 0, levelInlevel = 0;
	private int AbleToContinue = 0;
	private Image Exit;
	private Image Start;
	private Image Conti;
	static MainMenu menu;

	public MainMenu(String name) {
		super();
		this.name = name;
		menu = this;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setPreferredSize((Toolkit.getDefaultToolkit().getScreenSize()));
		this.requestFocus();
		this.setFocusable(true);
		int[] InFile = new int[9];
		String lines;
		int i =0;
		try {
			BufferedReader bw = new BufferedReader(new FileReader("file/" + name + ".data"));
			while((lines = bw.readLine()) != null) {
				InFile[i] = Integer.valueOf(lines);
				i++;
			}
			bw.close();
			bomb = InFile[0]; heart = InFile[1]; coin = InFile[2]; powerOfarrow = InFile[3];
			KindOfarrow = InFile[4]; score = InFile[5]; level = InFile[6]; levelInlevel = InFile[7]; 
			
			
		} catch (Exception e) {}
		try {
			image = ImageIO.read(new File("file/jungle3.jpg"));
		} catch (IOException e) {}
		
		JTextField ToHello = new JTextField("Welcome " + name +" :)");
		ToHello.setOpaque(false);
		ToHello.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 30));
		ToHello.setForeground(Color.BLUE);
		ToHello.setEditable(false);
		ToHello.setBorder(new LineBorder(Color.BLACK,0));
		ToHello.setBounds(580, 240, 300, 40);
		ToHello.setToolTipText("Click here to change the player!");
		ToHello.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				ToHello.setForeground(Color.BLUE);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				ToHello.setForeground(Color.GRAY);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				BaseMenu bm = new BaseMenu();
				Main.frame.setContentPane(bm);
				Main.frame.pack();
			}
		});
		this.add(ToHello);
		Main.frame.pack();
		
		JLabel logo = new JLabel(new ImageIcon("file/Logo2.png"));
		logo.setBounds(480, 10, 389, 111);
		this.add(logo);
		
		try {
			 pic = ImageIO.read(new File("file/cursor2.png"));
		} catch (IOException e) {}

		cursor = pic.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Toolkit tk = getToolkit();
		MainMenu.this.setCursor(tk.createCustomCursor(cursor,new Point(0, 0),"custom cursor"));
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				repaint();
				x = e.getX(); y = e.getY();
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				repaint();
				x = e.getX(); y = e.getY();
			}
		});
		
		Continue = new JButton();
		Continue.setIcon(getImage("file/bt1.png", 300, 70));
		Continue.setBounds(530, 300, 300, 70);
		set(Continue);
		Continue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				Game game = new Game(name, -1, "one");
				Main.frame.setContentPane(game);
				game.requestFocus();
				game.setFocusable(true);
				Main.frame.pack();
				
			}
		});
		Continue.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				Continue.setIcon(getImage("file/bt1.png", 300, 70));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				Continue.setIcon(getImage("file/bt12.png", 300, 70));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		if(score > 0) {
			this.add(Continue);
			AbleToContinue = 1;
		}
		
		start = new JButton();
		start.setIcon(getImage("file/bt2.png", 300, 70));
		start.setBounds(530, AbleToContinue*100 + 300, 300, 70);
		set(start);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				int[] parameters = new int[9];
				parameters[0] = 3; parameters[1] = 5; parameters[2] = 0; parameters[3] = 0; parameters[4] = 1;
				parameters[5] = 0; parameters[6] = 1; parameters[7] = 1; parameters[8] = 0;
				
				try{
					FileWriter fw = new FileWriter("file/" + name + ".data");
					BufferedWriter pw = new BufferedWriter(fw);
					for(int i = 0; i<parameters.length; i++) {
						pw.append(String.valueOf(parameters[i]));
						pw.newLine();
					}
					pw.flush();
					pw.close();
				}catch(Exception e) {}
				HowManyPlayer hm = new HowManyPlayer(name);
				Main.frame.getContentPane().add(hm);
				MainMenu.this.validate();
				for(Component component : MainMenu.this.getComponents()) {
				    if(! (component.equals(logo)))
				    	component.setEnabled(false);
				}
				MainMenu.this.revalidate();
				hm.setVisible(true);
				Main.frame.pack();
				Main.frame.getContentPane().revalidate();
				
			}
		});
		start.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				start.setIcon(getImage("file/bt2.png", 300, 70));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				start.setIcon(getImage("file/bt22.png", 300, 70));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		this.add(start);
		
		exit = new JButton();
		exit.setIcon(getImage("file/bt3.png", 200, 70));
		exit.setBounds(580, AbleToContinue*100 + 400, 200, 70);
		set(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				Sounds s = new Sounds("file/click.wav");
			}
		});
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
			}
		});
		this.add(exit);
		
		
		
		
		Rank = new JButton();
		Rank.setIcon(getImage("file/ranking1.png", 200, 70));
		Rank.setBounds(580, AbleToContinue*100 + 500, 200, 70);
		set(Rank);
		Rank.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				Main.frame.setContentPane(new Ranking());
				Main.frame.pack();
				
			}
		});
		Rank.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				Rank.setIcon(getImage("file/ranking1.png", 200, 70));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Rank.setIcon(getImage("file/ranking2.png", 200, 70));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		this.add(Rank);
		
	}
	
    public void set(JButton bt) {
		
		bt.setContentAreaFilled(false);
		bt.setBorderPainted(false);
		bt.setFocusable(false);
		bt.setVisible(true);
		
	}
	
	public ImageIcon getImage(String st, int w, int h) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(st));
		} catch (IOException e) {}
		Image newbut = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		ImageIcon AddImage = new ImageIcon(newbut);
		
		return AddImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	

}
