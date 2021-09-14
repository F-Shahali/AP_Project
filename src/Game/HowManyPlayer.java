package Game;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import Client_Server.Client;
//import Client_Server.Server;

public class HowManyPlayer extends JPanel{
	Image back ;
	static String IP, port;
	boolean IsServer = false, IsClient = false;
	String name;
	JButton b, Client;
	Server s;
	Client c;
	
	
	public HowManyPlayer(String name) {
		initialize();
		this.name = name;
	}

	private void initialize() {
		this.setBounds(120, 65, 500, 290);
//		this.setBounds(740, 20, 500, 290);
//		this.setPreferredSize(new Dimension(300, 200));
		this.setLayout(null);
		this.setOpaque(false);
		
		try {
			BufferedImage image = ImageIO.read(new File("file/HowMany2.png"));
			back = image.getScaledInstance(500, 290, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
		
		JButton Exit = new JButton();
		JButton One = new JButton();
		JButton multiPlayer = new JButton();
		JButton Server = new JButton();
		JButton Client = new JButton();
		JButton Ok = new JButton();
		
		JTextField Port = new JTextField("Server Port");
		Port.setEditable(true);
		Port.setVisible(true);
		Port.setFont(new Font("Gentium", Font.ROMAN_BASELINE, 23));
		Port.setOpaque(false);
		Port.setForeground(Color.WHITE);
		Port.setBounds(105, 60, 200, 30);
		
		JTextField IP = new JTextField("Server IP");
		IP.setEditable(true);
		IP.setVisible(true);
		IP.setFont(new Font("Gentium", Font.ROMAN_BASELINE, 23));
		IP.setOpaque(false);
		IP.setForeground(Color.WHITE);
		IP.setBounds(105, 110, 200, 30);
		
		JTextField num = new JTextField("number of players");
		num.setEditable(true);
		num.setVisible(true);
		num.setFont(new Font("Gentium", Font.ROMAN_BASELINE, 23));
		num.setOpaque(false);
		num.setForeground(Color.WHITE);
		num.setBounds(105, 110, 200, 30);
		
		AddButton(One, "one", 105, 50, 150, 60);
		One.addActionListener(new ActionListener() {
			
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
		this.add(One);
		
		AddButton(multiPlayer, "multi", 175, 120, 150, 60);
		multiPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				HowManyPlayer.this.add(Server);
				HowManyPlayer.this.add(Client);
				HowManyPlayer.this.remove(One);
				HowManyPlayer.this.remove(multiPlayer);
				Main.frame.pack();
				
			}
		});
		this.add(multiPlayer);
		
		AddButton(Exit, "exit", 265, 190, 90, 50);
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				HowManyPlayer.this.setVisible(false);
				MainMenu.menu.remove(HowManyPlayer.this);
				for(Component component : MainMenu.menu.getComponents()) {
				    component.setEnabled(true);
				}
//				Main.main.BuildServerOrClient = false;
			}
		});
		this.add(Exit);
		
		AddButton(Server, "server", 105, 50, 150, 60);
		Server.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				HowManyPlayer.this.add(num);
				HowManyPlayer.this.add(Port);
				HowManyPlayer.this.add(Ok);
				IsServer = true;
				HowManyPlayer.this.remove(Server);
				HowManyPlayer.this.remove(Client);
				Main.frame.pack();
			}
		});
		
		AddButton(Client, "client", 175, 120, 150, 60);
		Client.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				HowManyPlayer.this.add(IP);
				HowManyPlayer.this.add(Port);
				HowManyPlayer.this.add(Ok);
				IsClient = true;
				HowManyPlayer.this.remove(Server);
				HowManyPlayer.this.remove(Client);
				Main.frame.pack();
				
			}
		});
		 AddButton(Ok, "done", 300, 80, 90, 50);
		 Ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sounds sound = new Sounds("file/click.wav");
				if(IsServer == true ) {
					Server s = new Server(Integer.valueOf(Port.getText()));
					s.Players = Integer.valueOf(num.getText());
					s.SetName(name);
					s.start();
					IsServer = false;
					PlayersName names = new PlayersName("server", s.name, -1);
					Main.frame.setContentPane(names);
					Main.frame.pack();
				}
				if(IsClient == true ) {
					Client c = new Client(IP.getText(), Integer.valueOf(Port.getText()), name);
					c.start();
					IsClient = false;
					PlayersName names = new PlayersName("client", c.name, c.getID());
					Main.frame.setContentPane(names);
					Main.frame.pack();
//					System.out.println(c.name);
				}
//				Main.main.BuildServerOrClient(Port.getText(), IP.getText(), name);
//				Main.main.IP = IP.getText();
//				Main.main.Port = Port.getText();
//				Main.name = name;
//				Main.BuildServerOrClient = true;
				
			}
		});

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
    public void AddButton(JButton b, String name, int x, int y, int z, int w) {
    	b.setIcon(getImage("file/" + name +"1.png", z, w));
		b.setBounds(x, y, z, w);
		set(b);
		b.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				b.setIcon(getImage("file/" + name +"1.png", z, w));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				b.setIcon(getImage("file/" + name +"2.png", z, w));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
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

}

