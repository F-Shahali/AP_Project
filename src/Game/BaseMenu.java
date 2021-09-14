package Game;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class BaseMenu extends JPanel{
	
	JFrame frame;
	JPanel pnl;
	JButton add;
	JButton remove;
	JButton enter;
	BufferedImage image;
	BufferedImage but;
	JFrame player;
	BufferedReader fileReader;
	static ArrayList<JRadioButton> names;
	ButtonGroup group;
	File thisplayer;
	String SelectedName = "";
	JTextField check;
	int size;

	public BaseMenu() {
		super();
		initialize();
	}
	
	void initialize() {

		this.setLayout(null);
		this.setPreferredSize((Toolkit.getDefaultToolkit().getScreenSize()));
		
		BufferedImage img = null;
		try {
			 img = ImageIO.read(new File("file/cursor2.png"));
		} catch (IOException e) {}
		
		Image cursor = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,new Point(0,0),"custom cursor"));
		
		
		try {
			image = ImageIO.read(new File("file/jungle6.jpeg"));
		} catch (IOException e) {}
	
		File file = new File("file/game.data");
		
		try {
			fileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {}
		
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			while((line = fileReader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {}
		
		JTextField title = new JTextField("Players");
		title.setOpaque(false);
		title.setFont(new Font("FreeSerif", Font.BOLD, 27));
		title.setForeground(Color.ORANGE);
		title.setBounds(650, 20, 90, 30);
		title.setEditable(false);
		title.setBorder(new LineBorder(Color.BLACK,0));
		this.add(title);
		
		names = new ArrayList<JRadioButton>();
		group = new ButtonGroup();
		int linesNum = lines.size();
		for(int i=0; i< linesNum; i++) {
			JRadioButton jb = new JRadioButton(lines.get(i));
			names.add(jb);
			addJradiobutton(names, i);
		}
		
		add = new JButton();
		add.setIcon(getImage("file/button1.png"));
		add.setBounds(300, 500, 200, 70);
		set(add);
		add.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				add.setIcon(getImage("file/button1.png"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				add.setIcon(getImage("file/button12.png"));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				
				if(check != null) {
					check.setVisible(false);
					BaseMenu.this.remove(check);
				}
				ArrayList groupelements = Collections.list(group.getElements());
				size = groupelements.size();
				JTextField addplayer = new JTextField();
				addplayer.setVisible(true);
				addplayer.setFont(new Font("Dyuthi", Font.ROMAN_BASELINE, 23));
				addplayer.setOpaque(false);
				addplayer.setForeground(Color.LIGHT_GRAY);
				BaseMenu.this.add(addplayer);
				if(size/7 <= 1)
					addplayer.setBounds(300, 100+ size*50, 100, 30);
				if(size/8 >= 1)
					addplayer.setBounds(((int)size/8 + 1) * 300 + 50, 100 + (size%8) * 50, 100, 30);
				addplayer.addKeyListener(new KeyListener() {
					
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==KeyEvent.VK_ENTER) {
							
							int re = 0;
							String NewName = addplayer.getText();
							boolean ToCheck = false;
							for(int i = 0; i<lines.size(); i++) {
								if(lines.get(i).equals(NewName)) {
									ToCheck = true;
								}
							}
							if(ToCheck == true) {
								check = new JTextField("This name is iterative !");
								re++;
								BaseMenu.this.remove(addplayer);
								addplayer.setVisible(false);
								if(size/7 <= 1)
									check.setBounds(300, 100+(size*50), 300, 30);
								if(size/8 >= 1)
									check.setBounds(((int)size/8 + 1) * 300 + 50, 100 + (size%8) * 50, 300, 30);
								check.setVisible(true);
								check.setFont(new Font("Caladea", Font.TRUETYPE_FONT, 23));
								check.setOpaque(false);
								check.setForeground(Color.magenta);
								check.setBorder(new LineBorder(Color.BLACK,0));
								check.setEditable(false);
								BaseMenu.this.add(check);
								
							}else {
								size += re;
								thisplayer = new File("file/" + NewName + ".data");
								if(thisplayer.exists() == false)
									try {
										thisplayer.createNewFile();
									} catch (IOException e2) {}
								
								try {
							
									FileWriter fw = new FileWriter(file, true);
									BufferedWriter pw = new BufferedWriter(fw);
									pw.append(NewName);
									pw.newLine();
									pw.flush();
									pw.close();
									
								} catch (IOException e1) {}
								
								BaseMenu.this.remove(addplayer);
								addplayer.setVisible(false);
								
								JRadioButton newbutton = new JRadioButton(NewName);
								names.add(newbutton);
								addJradiobutton(names, size);
								if(size/8 >= 1)
									newbutton.setBounds(((int)size/8 + 1) * 300 + 50, 100 + (size%8) * 50, 300, 30);
								lines.add(newbutton.getText());
								updateUI();
							}
						}
					}

					@Override
					public void keyReleased(KeyEvent arg0) {}

					@Override
					public void keyTyped(KeyEvent arg0) {}
				});
				
			}
		});
		this.add(add);
		
		remove = new JButton();
		remove.setIcon(getImage("file/button2.png"));
		remove.setBounds(583, 500, 200, 70);
		set(remove);
		remove.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				remove.setIcon(getImage("file/button2.png"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				remove.setIcon(getImage("file/button22.png"));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				String line;
				ArrayList<String> name = new ArrayList<String>();
				String removedname = "";
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					
					for(int i = 0; i<lines.size(); i++) {

						if(names.get(i).isSelected())
							removedname = names.get(i).getText();
					}
					while((line = br.readLine()) != null) {
						if(!(line.equals(removedname))) {
							name.add(line);
						}
					}
					br.close();
					FileWriter fw = new FileWriter(file);
				    BufferedWriter pw = new BufferedWriter(fw);
					for(int i = 0; i<name.size(); i++) {
						pw.append(name.get(i));
						pw.newLine();
					}
					pw.flush();
					pw.close();
					File  filetoremove = new File("file/" + removedname + ".data");
					filetoremove.delete();
				}catch(Exception e) {}
				
				for(int i = 0; i < lines.size(); i++) {
					if(lines.get(i).equals(removedname)) {
						names.get(i).setVisible(false);
						for(int j = i+1; j<lines.size(); j++) {
							names.get(j).setLocation(300, names.get(j).getY() - 50);
						}
						group.remove(names.get(i));
						BaseMenu.this.remove(names.get(i));
						names.remove(i);
						lines.remove(i);
						updateUI();
						
					}
					
				}
				
			}
		});
		this.add(remove);
		
		enter = new JButton();
		enter.setIcon(getImage("file/button3.png"));
		enter.setBounds(866, 500, 200, 70);
		set(enter);
		enter.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				enter.setIcon(getImage("file/button3.png"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				enter.setIcon(getImage("file/button32.png"));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Sounds s = new Sounds("file/click.wav");
				for(int i = 0; i<lines.size(); i++) {
					if(names.get(i).isSelected())
						SelectedName = names.get(i).getText();
				}
				MainMenu menu = new MainMenu(SelectedName);
				Main.frame.setContentPane(menu);
				Main.frame.getContentPane().revalidate();
				Main.frame.pack();
				
				
			}
		});
		
		this.add(enter);
		
		
	}

	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public Icon getImage(String st) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(st));
		} catch (IOException e) {}
		Image newbut = img.getScaledInstance(200, 70, java.awt.Image.SCALE_SMOOTH);
		Icon AddImage = new ImageIcon(newbut);
		
		return AddImage;
	}
	
	
	public void addJradiobutton(ArrayList<JRadioButton> b, int num) {
		b.get(num).setMnemonic(KeyEvent.VK_C); 
		b.get(num).setActionCommand(b.get(num).getText());
		b.get(num).setSelected(true);
		if(num/7 <= 1)
			b.get(num).setBounds(300, 100+(num*50), 300, 30);
		if(num/8 >= 1)
			b.get(num).setBounds(((int)num/8 + 1) * 300 + 50, 100 + (num%8) * 50, 300, 30);
		b.get(num).setContentAreaFilled(false);
		b.get(num).setBorderPainted(false);
		b.get(num).setFocusable(false);
		b.get(num).setVisible(true);
		b.get(num).setFont(new Font("Dyuthi", Font.ITALIC, 30));
		b.get(num).setForeground(Color.LIGHT_GRAY);
		group.add(b.get(num));
		this.add(b.get(num));
	}
	
	public void set(JButton bt) {
		
		bt.setContentAreaFilled(false);
		bt.setBorderPainted(false);
		bt.setFocusable(false);
		bt.setVisible(true);
		
	}
	
}
