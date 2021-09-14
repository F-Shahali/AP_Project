package Game;

import java.awt.Image;
import java.awt.Point;
import java.awt.Window.Type;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;


//import game.Location;
import Message.ClientMessage;
import Message.ServerMessage;

public class Client extends Thread{

	String IP;
	int port, ID, Xlocation, Ylocation;
	public String name;
	static PrintStream printer;
	Scanner reader;
	ObjectInputStream oi;
	public static DefaultListModel<String> names = new DefaultListModel<String>();
	public static boolean FinishReading = false;
	
	Image image;
	boolean moving;
	private Game game;

	public Client(String IP, int port, String name) {
		this.IP = IP;
		this.port = port;
		this.name = name;
		try {
			image = ImageIO.read(new File("file/hunterMan" + (ID+2) + ".png")).getScaledInstance(150, 160, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
	}
	public Client(String name) {
		this.name = name;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getID() {
		return ID;
	}

	@Override
	public void run() {
		super.run();
		Xlocation = 683 - (4 - ID)*170;
		try {
			Socket socket;
			socket = new Socket(IP, port);

			printer = new PrintStream(socket.getOutputStream());
			reader = new Scanner(socket.getInputStream());
			printer.println(name);
			int i = 0;
			String line;
			while((line = reader.nextLine()) != null) {
				if(i == 0) {
					ID = Integer.valueOf(line);
				}
				else {
					if(line.equals("FinishReading")) {
						break;
					}
					names.addElement(line);
				}
				i++;
			}
//			Game game;
			if(line.equals("FinishReading")) {
				game = new Game(name, ID, "multi");
				game.requestFocus();
				game.setFocusable(true);
				Main.frame.setContentPane(game);
				Main.frame.pack();
			}
			while(true) {
				String li = reader.nextLine();
				if(li.equals("Escape")) {
					game.EscapePress();
				}
				if(li.equals("Tir")) {
					int[] a = new int[4];
					for(int t = 0; t < 4; t++) {
						a[t] = Integer.valueOf(reader.nextLine());
					}
					game.AddArrow(a[0], a[1], a[2], a[3], game.OtherArrow, game.ActingPower);
				}
				if(li.equals("Continue")) {
					game.Continue(Escape.es, Escape.es.x, Escape.es.y);
				}
				if(li.equals("mouseMoved")) {
					int[] a = new int[3];
					for(int t = 0; t < 3; t++) {
						a[t] = Integer.valueOf(reader.nextLine());
					}
					game.mouseMoved(a[0], a[1], a[2]);
//					li = reader.nextLine();
				}
				if(li.equals("Coconut")) {
					int n = Integer.valueOf(reader.nextLine());
					game.AddCoconut(game.m.get(n));
//					li = reader.nextLine();
				}
				if(li.equals("SetLocation")) {
					int[] a = new int[3];
					for(int t = 0; t < 3; t++) {
						a[t] = Integer.valueOf(reader.nextLine());
					}
					game.m.get(a[0]).SetLocation(a[0], a[1], a[2]);
					game.repaint();
//					li = reader.nextLine();
				}
				if(li.equals("SetCenter")) {
					int[] a = new int[3];
					for(int t = 0; t < 3; t++) {
						a[t] = Integer.valueOf(reader.nextLine());
					}
					game.monkeys.SetCenter(a[0], a[1]);
					game.repaint();
//					li = reader.nextLine();
				}
				if(li.equals("NonShowHunterMan")) {
					game.NonShowHunterMan(Integer.valueOf(reader.nextLine()));
				}
				if(li.equals("ShowHunterMan")) {
					game.ShowHunterMan(Integer.valueOf(reader.nextLine()));
				}
				if(li.equals("Exit")) {
					int id = Integer.valueOf(reader.nextLine());
					game.Exit(id);
				}
				if(li.equals("FinishGame")) {
					Escape.es.Exit(name);
				}
					Thread.sleep(50);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		


	}
	public static void addTir(int x, int y, int kindOfarrow, int numberOfArrow) {
		printer.println("Tir");
		printer.println(x);
		printer.println(y);
		printer.println(kindOfarrow);
		printer.println(numberOfArrow);
	}
	
	public static void mouseMoved(int id, int x, int y) {
		printer.println("mouseMoved");
		printer.println(id);
		printer.println(x);
		printer.println(y);
	}

	public static void Escape() {
		printer.println("Escape");
		
	}
	public static void AddContinue() {
		printer.println("Continue");
		
	}
	public static void NonShowHunterMan(int id) {
		printer.println("NonShowHunterMan");
		printer.println(id);
	}
	public static void ShowHunterMan(int id) {
		printer.println("ShowHunterMan");
		printer.println(id);
		
	}
	public static void HunterManDead(int id) {
		printer.println("HunterManDead");
		printer.println(id);
		
	}
	public static void Exit(int id) {
		printer.println("Exit");
		printer.println(id);
	}
}
