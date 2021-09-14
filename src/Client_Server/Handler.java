package Client_Server;

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
				Thread.sleep(200);
			}
			String line;
			while(true) {
				if(reader.nextLine().equals("Tir")) {
					for(Handler handler : ServerMother.handlers ) {
						if(!(handler.name.equals(Handler.this.name))){
							handler.printer.println("Tir");
							for(int i = 0; i < 4; i++)
								handler.printer.println(reader.nextLine());
						}
					}
				}
				
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
