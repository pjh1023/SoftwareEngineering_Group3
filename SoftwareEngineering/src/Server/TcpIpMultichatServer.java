package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import Network.Room; 

public class TcpIpMultichatServer {
	public static HashMap clients;
	static Vector<Room> roomV;
	static Socket socket;
	
	TcpIpMultichatServer(){
		clients = new HashMap();
		Collections.synchronizedMap(clients);
		
		roomV = new Vector<>();
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		socket = null;
		try {
			serverSocket = new ServerSocket(7778);
			System.out.println("server start");  
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다.");
				ServerReceiver thread = new ServerReceiver(socket, this);
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
	static void sendToOne(String msg, Socket socket) {
		try {
			DataOutputStream out = (DataOutputStream)clients.get(socket.getPort());
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
	
	public static class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		Room myRoom;
		Vector<Room> roomV;
		int count=0;
		ServerReceiver(Socket socket, TcpIpMultichatServer server){
			roomV = server.roomV;
			this.socket = socket;
			
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(IOException e) {
				e.printStackTrace();
			}
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
					if(mssg == null) return;
					if(mssg.trim().length() > 0) {
						System.out.println("from Client: "+mssg+":"+socket.getInetAddress().getHostAddress());
					} //server에서 상황 모니터
					
					if(mssg.startsWith("[Msg]")) {
						//sendToAll(mssg);
						sendToOne(mssg, socket);
					}
					else if(mssg.startsWith("[Ready]")) {
						
						String str[] = mssg.split(",");
//						if(clients.size() %4 == 1) { // 1,5,9,13...
//							myRoom = new Room(count++);
//							for(int i=0; i<roomV.size(); i++) {
//								Room r = roomV.get(i);
//								if(r.roomNum == ) {
//									myRoom = r;
//									break;
//								}
//							}
//					
//							roomV.add(myRoom);
//							myRoom.userV.add(this);
//						}
						
//						sendToRoom("");
					}
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
		
		public void sendToRoom(String msg) {
			for(int i=0; i<myRoom.userV.size(); i++) {
				ServerReceiver receiver = myRoom.userV.get(i);
				try {
					messageTo(msg);
				} catch(IOException e) {
					
				}
			}
		}
		public void messageTo(String msg) throws IOException{ //특정 client에게 전달 
//			(DataOutputStream)clients.get(it.next());
			OutputStream out = socket.getOutputStream();
//			out.writeUTF(msg);
			out.write((msg + "\n").getBytes());
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
