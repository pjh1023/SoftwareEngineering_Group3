package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
	static void sendToOne(String msg, String id) {
		try {
			DataOutputStream out = (DataOutputStream)clients.get(id);
			out.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String id = "";
			try { 
//				name = in.readUTF();
//				sendToAll("#"+name+"님이 들어오셨습니다.");
//				clients.put(name, out);
				clients.put(socket.getPort(), out);
				System.out.println("현재 접속자 수는 "+clients.size()+"명 입니다. ");	
				
				while(in !=null) {
					String mssg = in.readUTF(); // message format: [type],[user#],content1,content2,...
			
					if(mssg.startsWith("[Msg]"))
						sendToAll(mssg);
//						sendToOne(mssg, "test1");
					else if(mssg.startsWith("[Login]")) {
						String str[] = mssg.split(",");
						sendToAll(str[0]+","+str[1]+","+loginCheck(str[2],str[3],socket.getInetAddress()+":"+socket.getPort()));
					}
					else if(mssg.startsWith("[IdCheck]")) {
						String str[] = mssg.split(",");
						sendToAll(str[0]+","+str[1]+","+idRedunCheck(str[2]));
					}
					else if(mssg.startsWith("[NickCheck]")) {
						String str[] = mssg.split(",");
						sendToAll(str[0]+","+str[1]+","+nickRedunCheck(str[2]));
					}
					else if(mssg.startsWith("[Register]")) {
						String str[] = mssg.split(",");
						Object[] info = {str[2],str[3],str[4]};
						sendToAll(str[0]+","+str[1]+","+signupUser(info));
					}
					else if(mssg.startsWith("[TopRank]")) {
						sendToAll(mssg+","+getTopRank());
					}
				}
			} catch(IOException e) {}
				finally { 
//					sendToAll("#"+id+"님이 나가셨습니다.");
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
	/////////////////////// Sign up /////////////////////////
	public static boolean idRedunCheck(String id) {
		return Server.LoginDB.checkRedundantId(id);
	}
	public static boolean nickRedunCheck(String nick) {
		return Server.LoginDB.checkRedundantNick(nick);
	}
	public static int signupUser(Object[] info) {
		Server.LoginDB.addUser(info);
		return Server.LoginDB.getUserID((String) info[0]);
	}
	/////////////////////// Ranking /////////////////////////
	public static String getTopRank() {
		ArrayList<String> top5 = Server.RankDB.getTop5(); // nick,win,lose,rate
		String toplist = "";
		for(int i=0; i<top5.size()/4; i++) {
			for(int j=0;j<4;j++)
				toplist+=(top5.get(4*i+j)+"/");
			toplist+=",";
		}
		return toplist;
	}
	
}
