//package Client_Server;
//
//import java.awt.Point;
//import java.awt.Window.Type;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//import javax.swing.DefaultListModel;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import Game.*;
////import game.Location;
//import Message.ClientMessage;
//import Message.ServerMessage;
//
//public class Client extends Thread{
//
//	String IP;
//	int port, ID, Xlocation, Ylocation;
//	public String name;
//	static PrintStream printer;
//	Scanner scanner;
//	public static DefaultListModel<String> names = new DefaultListModel<String>();
//	public static boolean FinishReading = false;
//	public Map<String, Map<String, Point>> Location = new HashMap<String, Map<String,Point>>();
//	ArrayList <ClientMessage>clientMessages = new ArrayList<ClientMessage>();
//	ArrayList <ServerMessage>serverMessages = new ArrayList<ServerMessage>();
//	private transient Gson gson = new Gson();
//	boolean moving;
//
//	public Client(String IP, int port, String name) {
//		this.IP = IP;
//		this.port = port;
//		this.name = name;
//	}
//	public void setID(int iD) {
//		ID = iD;
//	}
//	public int getID() {
//		return ID;
//	}
//
//	@Override
//	public void run() {
//		super.run();
//		Xlocation = 700 - (4 - ID)*170;
//		try {
//			Socket socket;
//			System.out.println(IP +" " + port);
//			socket = new Socket(IP, port);
//
//			printer = new PrintStream(socket.getOutputStream());
//			scanner = new Scanner(socket.getInputStream());
//			printer.println(name);
//			int i = 0;
//			String line;
//			while((line = scanner.nextLine()) != null) {
//				if(i == 0) {
//					ID = Integer.valueOf(line);
//				}
//				else {
//					if(line.equals("FinishReading")) {
//						System.out.println(line);
//						break;
//					}
//					names.addElement(line);
//				}
//				i++;
//				System.out.println(names);
//			}
//			if(line.equals("FinishReading")) {
//				Game game = new Game(name, ID, "multi");
//				game.requestFocus();
//				game.setFocusable(true);
//				Main.frame.setContentPane(game);
//				Main.frame.pack();
//			}
////			while(true) {
////				try {
////					loc = (Map<String, Map<String, Point>>)oi.readObject();
////				} catch (ClassNotFoundException e) {}
////			}
////			System.out.println(names);
////			try {
////				names = (DefaultListModel<String>)is.readObject();
////				System.out.println(names);
////			} catch (ClassNotFoundException e) {}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//
//
//	}
//	public static void addTir(int x, int y, int kindOfarrow, int numberOfArrow) {
//		printer.println("Tir");
//		printer.println(x);
//		printer.println(y);
//		printer.println(kindOfarrow);
//		printer.println(numberOfArrow);
//	}
//
//	public static  synchronized void sendLocation(String name, Map<String, Point> location) {
//			printer.println(location);
//	}
//
//	public boolean isMoving() {
//		return moving;
//	}
//}
