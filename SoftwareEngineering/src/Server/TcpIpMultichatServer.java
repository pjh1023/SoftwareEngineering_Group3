package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator; 

public class TcpIpMultichatServer {
	static HashMap clients;
	
	TcpIpMultichatServer(){
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(7778);
			System.out.println("server start");  
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다.");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	static void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream)clients.get(it.next());
				out.writeUTF(msg);
				//Frame.chating.append(msg);
			}catch(IOException e) {}
		}
	}
	
	public static void main(String args[]) {
		DB database = DB.getInstance();
		new TcpIpMultichatServer().start();
	} 
	
	static class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		ServerReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(IOException e) {}
		}
		
		public void run() {
			 
			try { 
				clients.put(socket.getPort(), out);
				//clients.put(name, out);
				System.out.println("현재 접속자 수는 "+clients.size()+"명 입니다. ");	
				
				while(in !=null) {
					String mssg = in.readUTF(); // message format: [type],[user#],content1,content2,...
					
					if(mssg.startsWith("[Msg]"))
						sendToAll(mssg);
					else if(mssg.startsWith("[Login]")) {
						String str[] = mssg.split(",");
						sendToAll(str[0]+","+str[1]+","+loginCheck(str[2],str[3],socket.getInetAddress()+":"+socket.getPort()));
					}
					else if(mssg.startsWith("IdCheck")) {
						String str[] = mssg.split(",");
						sendToAll(str[0]+","+str[1]+","+idRedunCheck(str[2]));
					}
				}
			} catch(IOException e) {}
				finally { 
					clients.remove(socket.getPort());
					System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속종료");
					System.out.println("현재 접속자수는 "+clients.size());
				} 
		} 
	}
	
	
	/////////////////////// Log In /////////////////////////
	public static boolean loginCheck(String id, String pw, String ip) {
		return Server.LoginDB.checkLogin(id, pw, ip);
	}
	public static boolean idRedunCheck(String id) {
		return Server.LoginDB.checkRedundantId(id);
	}
	
}
