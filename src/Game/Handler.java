package Game;

import java.awt.Point;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Message.ClientMessage;
import Message.ServerMessage;

public class Handler extends Thread{
	int id;
	Socket clienthandler;
	Server ServerMother;
	InputStream fromClientStream;
	OutputStream toClientStream;
	ArrayList<ClientMessage> clientMessages = new ArrayList<>();
	public Map<String, Map<String, Point>> loc = new HashMap<String, Map<String, Point>>();
	public PrintWriter printer;
	Scanner reader;
	String name;
	
	public Handler(Server server,Socket socket) {
		clienthandler = socket;
		ServerMother = server;
		
	}
	@Override
	public void run() {
		try {
			fromClientStream = clienthandler.getInputStream();
			toClientStream = clienthandler.getOutputStream();
			reader = new Scanner(fromClientStream);
			printer = new PrintWriter(toClientStream, true);
			
			name = reader.nextLine();
			ServerMother.addHandler(name, this);
			printer.println(ServerMother.names.getSize()-1);
			
			for(int i = 0; i< ServerMother.names.getSize(); i++) {
				printer.println(ServerMother.names.getElementAt(i));
			}
			for(int i = 0; i < ServerMother.handlers.size(); i++) {
				ServerMother.handlers.get(i).addName(name);
			}
			ServerMother.handlers.add(this);
			while(true) {
				if(ServerMother.FinishReading == true) {
					for(int i = 0; i < ServerMother.handlers.size(); i++) {
						ServerMother.handlers.get(i).printer.println("FinishReading");
					}
					break;
				}
				Thread.sleep(50);
			}
//			String line;
			while(true) {
				String line = reader.nextLine();
				if(line.equals("Tir")) {
					int[] a = new int[4];
					for(int i = 0; i < 4; i++)
						a[i] = Integer.valueOf(reader.nextLine());
					ServerMother.game.AddArrow(a[0], a[1], a[2], a[3], ServerMother.game.OtherArrow, ServerMother.game.ActingPower);
					for(Handler handler : ServerMother.handlers ) {
						if(!(handler.name.equals(Handler.this.name))){
							handler.printer.println("Tir");
							for(int i = 0; i < 4; i++)
								handler.printer.println(a[i]);
						}
					}
				}
				if(line.equals("mouseMoved")) {
					int[] a = new int[3];
					for(int i = 0; i < 3; i++)
						a[i] = Integer.valueOf(reader.nextLine());
					ServerMother.game.mouseMoved(a[0], a[1], a[2]);
					for(Handler handler : ServerMother.handlers ) {
						if(!(handler.name.equals(Handler.this.name))){
							handler.printer.println("mouseMoved");
							for(int i = 0; i < 3; i++)
								handler.printer.println(a[i]);
						}
					}
				}
				if(line.equals("Escape")) {
					ServerMother.game.EscapePress();
					for(Handler handler : ServerMother.handlers ) {
						if(!(handler.name.equals(Handler.this.name))){
							handler.printer.println("Escape");
						}
					}
				}
				if(line.equals("Continue")) {
					ServerMother.Continue++;
					if(ServerMother.Continue == ServerMother.names.getSize()+1) {
						Server.game.Continue(Escape.es, Escape.es.x, Escape.es.y);
						for(Handler handler : ServerMother.handlers ) {
							handler.printer.println("Continue");
						}
						ServerMother.Continue = 0;
					}
				}
				if(line.equals("NonShowHunterMan")) {
					int id = Integer.valueOf(reader.nextLine());
					ServerMother.game.NonShowHunterMan(id);
						for(Handler handler : ServerMother.handlers ) {
							if(!(handler.name.equals(Handler.this.name))) {
								handler.printer.println("NonShowHunterMan");
								handler.printer.println(id);
							}
						}
				}
				if(line.equals("ShowHunterMan")) {
					int id = Integer.valueOf(reader.nextLine());
					ServerMother.game.ShowHunterMan(id);
						for(Handler handler : ServerMother.handlers ) {
							if(!(handler.name.equals(Handler.this.name))) {
								handler.printer.println("ShowHunterMan");
								handler.printer.println(id);
							}
						}
				}
				if(line.equals("HunterManDead")) {
					int id = Integer.valueOf(reader.nextLine());
					ServerMother.game.HunterManDead(id);
						for(Handler handler : ServerMother.handlers ) {
							if(!(handler.name.equals(Handler.this.name))) {
								handler.printer.println("HunterManDead");
								handler.printer.println(id);
							}
						}
				}
				if(line.equals("Exit")) {
					int id = Integer.valueOf(reader.nextLine());
					ServerMother.game.Exit(id);
						for(Handler handler : ServerMother.handlers ) {
							if(!(handler.name.equals(Handler.this.name))) {
								handler.printer.println("Exit");
								handler.printer.println(id);
							}
						}
				}
				Thread.sleep(50);
			}
//			System.out.println(name);
//			printer.print(ServerMother.names);
			
//			while(true) {
//				synchronized(clientMessages) {
//					while(reader.hasNextLine()) {
//						String message = reader.nextLine();
//						ArrayList<ClientMessage> messages = gson.fromJson(message, new TypeToken<ArrayList<ServerMessage>>() {}.getType());
//						clientMessages.addAll(messages);
//					}
//				}
//				Thread.sleep(200);
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addName(String name) {
		printer.println(name);
		printer.flush();
	}
	
	public void SendObject(File pic, int x, int y) {
		
	}

}
