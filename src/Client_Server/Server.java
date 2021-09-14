package Client_Server;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import Message.ServerMessage;

public class Server extends Thread {
	public  int Players = 0;
	public String name;
	int port;
	int levels;
	int ClientNumber = 1;
	ServerSocket ss;
	ArrayList<Handler> handlers = new ArrayList<Handler>();
	public static boolean FinishReading = false;
//	public static ArrayList<String> names = new ArrayList<String>();
	public static DefaultListModel<String> names = new DefaultListModel<String>();
	
	public Server(int port) {
		this.port = port;
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
	public static void addTir(ArrayList<Handler> handle, int x, int y, int kindOfarrow, int numberOfArrow) {
		for(Handler handler : handle) {
			handler.printer.println("Tir");
			handler.printer.println(x);
			handler.printer.println(y);
			handler.printer.println(kindOfarrow);
			handler.printer.println(numberOfArrow);
		}
	}

}
