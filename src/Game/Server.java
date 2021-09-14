package Game;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Server extends Thread {
	public  int Players = 0;
	public String name;
	int port;
	int levels;
	int ClientNumber = 1;
	ServerSocket ss;
	Image image;
	int ID;
	static Game game;
	static ArrayList<Handler> handlers = new ArrayList<Handler>();
	public static boolean FinishReading = false;
//	public static ArrayList<String> names = new ArrayList<String>();
	public static DefaultListModel<String> names = new DefaultListModel<String>();
	static int Continue = 0;
	
	public Server(int port) {
		this.port = port;
		this.ID = -1;
		try {
			image = ImageIO.read(new File("file/hunterMan" + (ID+2) + ".png")).getScaledInstance(150, 160, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
	}
	public void SetName(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
//		super.run();
		try {
			ss = new ServerSocket(port);
			while(ClientNumber <= Players) {
				Socket socket = ss.accept();
				Handler ClientHandler = new Handler(this,socket);
				ClientHandler.start();
//				handlers.add(ClientHandler);
//				for(int i = 0; i < handlers.size()-1; i++) {
//					handlers.get(i).addName();
//				}
				ClientNumber++;
				
//				System.out.println(names);
			}
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addHandler(String clientName,Handler h){
		names.addElement(clientName);
	}
	public static void addTir(int x, int y, int kindOfarrow, int numberOfArrow) {
		for(Handler handler : handlers) {
			handler.printer.println("Tir");
			handler.printer.println(x);
			handler.printer.println(y);
			handler.printer.println(kindOfarrow);
			handler.printer.println(numberOfArrow);
		}
	}
	public static void mouseMoved(int id, int x, int y) {
		for(Handler handler : handlers) {
			handler.printer.println("mouseMoved");
			handler.printer.println(id);
			handler.printer.println(x);
			handler.printer.println(y);
		}
	}
	public static void FallCoconut(int i) {
		for(Handler handler : handlers) {
			handler.printer.println("Coconut");
			handler.printer.println(i);
		}
	}
	public static void setCenter(int x, int y) {
		for(Handler handler : handlers) {
			handler.printer.println("SetCenter");
			handler.printer.println(x);
			handler.printer.println(y);
		}
		
	}
	public static void setLocation(int i, int a, int b) {
		for(Handler handler : handlers) {
			handler.printer.println("SetLocation");
			handler.printer.println(i);
			handler.printer.println(a);
			handler.printer.println(b);
		}
		
	}
	public static void Escape() {
		for(Handler handler : handlers) {
			handler.printer.println("Escape");
		}
	}
	public static void NonShowHunterMan() {
		for(Handler handler : handlers) {
			handler.printer.println("NonShowHunterMan");
			handler.printer.println(-1);
		}
	}
	public static void ShowHunterMan() {
		for(Handler handler : handlers) {
			handler.printer.println("ShowHunterMan");
			handler.printer.println(-1);
		}
	}
	public static void HunterManDead() {
		for(Handler handler : handlers) {
			handler.printer.println("HunterManDead");
			handler.printer.println(-1);
		}
		
	}
	public static void FinishGame() {
		for(Handler handler : handlers) {
			handler.printer.println("FinishGame");
		}
		
	}

}
