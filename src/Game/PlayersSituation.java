package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class PlayersSituation extends JPanel{

	private BufferedImage pic;
	private Image img;
	private BufferedImage image;
	ArrayList<JTextField> situations = new ArrayList<JTextField>();
	String name;

	public PlayersSituation(String name) {
		this.name = name;
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
		try {
			pic = ImageIO.read(new File("file/cursor1.png"));
			image = ImageIO.read(new File("file/jungle3.jpg"));
		} catch (IOException e) {}
		Players p = new Players(name);
		JTextField wave = new JTextField("Passed Waves : " + p.getWave());
		JTextField score = new JTextField("Score : " + p.getScore());
		JTextField time = new JTextField("Passed Time : " + p.getTime());
		situations.add(wave);
		situations.add(score);
		situations.add(time);
		for(int i = 0; i< situations.size(); i++) {
			addJtextField(situations, i);
		}
		JButton exit = new JButton();
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
	public Icon getImage(String st, int w, int h) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(st));
		} catch (IOException e) {}
		Image newbut = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		Icon AddImage = new ImageIcon(newbut);
		
		return AddImage;
	}
	public void set(JButton bt) {
	
	bt.setContentAreaFilled(false);
	bt.setBorderPainted(false);
	bt.setFocusable(false);
	bt.setVisible(true);
	
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
