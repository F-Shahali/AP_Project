package Game;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Ranking extends JPanel{
	
	int bomb = 0, heartt = 0, bnanaNum = 0, powerOfarrow = 0,
			KindOfarrow = 0, score = 0, level = 0, levelInlevel = 0;
	int[] InFile;
	ArrayList<Players> Players;
	private BufferedImage image;
	private BufferedReader fileReader;
	private BufferedImage pic;
	private Image cursor;
	protected int y;
	protected int x;
	ArrayList<JTextField> names = new ArrayList<JTextField>();
	private JButton exit;
	private BufferedImage img;
	
	public Ranking() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setPreferredSize((Toolkit.getDefaultToolkit().getScreenSize()));
		this.requestFocus();
		this.setFocusable(true);
		try {
			 img = ImageIO.read(new File("file/cursor2.png"));
		} catch (IOException e) {}
		
		Image cursor = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,new Point(0,0),"custom cursor"));
//		Toolkit tk = getToolkit();
//		setCursor(tk.createCustomCursor(tk.getImage(""),new Point(),"trans"));
		File file = new File("file/game.data");
		
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			fileReader = new BufferedReader(new FileReader(file));
			while((line = fileReader.readLine()) != null) {
				lines.add(line);
			}
//			 pic = ImageIO.read(new File("file/cursor1.png"));
//			 cursor = pic.getScaledInstance(40, 50, java.awt.Image.SCALE_SMOOTH);
			image = ImageIO.read(new File("file/jungle3.jpg"));
		} catch (IOException e) {}
		
//		int linesNum = lines.size();
//		for(int i=0; i< linesNum; i++) {
//			JTextField jb = new JTextField(lines.get(i));
//			names.add(jb);
//			addJtextField(names, i);
//		}
		Players = new ArrayList<Players>();
		
		for(int i = 0; i < BaseMenu.names.size(); i++) {
			Players.add(new Players(BaseMenu.names.get(i).getText()));
		}
		Collections.sort(Players, Collections.reverseOrder());
		for(int i = 0; i<Players.size(); i++) {
			JTextField jb = new JTextField((i+1) +" )  " +Players.get(i).name);
			String name = Players.get(i).name;
			jb.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				
				@Override
				public void mousePressed(MouseEvent arg0) {}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					PlayersSituation s = new PlayersSituation(name);
					Main.frame.setContentPane(s);
					Main.frame.pack();
				}
			});
			names.add(jb);
			addJtextField(names, i);
		}
		exit = new JButton();
		exit.setIcon(getImage("file/bt3.png", 200, 70));
		exit.setBounds(600, 500, 200, 70);
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
				Sounds s = new Sounds("file/click.wav");
				Main.frame.setContentPane(new MainMenu(MainMenu.menu.name));
				Main.frame.pack();
				
			}
		});
		this.add(exit);
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

}
