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
import java.util.ArrayList; 

public class TcpIpMultichatServer {
	public static HashMap clients;
	static Socket socket;
	public static ArrayList<ServerReceiver> queue = new ArrayList<ServerReceiver>();
	//serverReceiver가 portnum이랑 out 정보 가지고 있엉
	
	TcpIpMultichatServer(){
		clients = new HashMap();
		Collections.synchronizedMap(clients);
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
				queue.add(thread); //client정보 queue에 담아
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
			}catch(IOException e) {}
		}
	}
	public static void sendToOne(String msg, Socket socket) {
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
		int roomNum;
		public int userID; 
		public String nickname;
		
		public static ArrayList<Room> rooms = new ArrayList<Room>();
		
		ServerReceiver(Socket socket, TcpIpMultichatServer server){
			this.socket = socket;
			roomNum = -1;
			
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
//						sendToAll(mssg);
//						sendToOne(mssg, socket);
						for(int i=0; i<4; i++) {
							if (this.roomNum >= 0) {
								sendToOne(mssg, rooms.get(this.roomNum).userV.get(i).socket);
							}
						}

					}
					else if(mssg.startsWith("[Ready]")) {
						System.out.println(queue.size());
						if(queue.size() % 4 == 0) {
							Room room = new Room(queue.size()/4); //queue.size()/4 == roomNumber
							rooms.add(room);
							
							for(int i=queue.size()-1; i>=queue.size()-4; i--) {
								queue.get(i).roomNum = rooms.size() - 1;
								room.userV.add(queue.get(i)); //portNum으로 client 가져왕 
							}
						}
					
					}
					else if(mssg.startsWith("[Login]")) { // receive format: [login],userID,nickname,id,pw
						String str[] = mssg.split(",");
						int usrID = Server.LoginDB.getUserID((String) str[3]);
						sendToOne(str[0]+","+usrID+","+Server.LoginDB.getNickname(usrID)+","+loginCheck(str[3],str[4],socket.getInetAddress()+":"+socket.getPort()), socket);
						nickname = str[3];
						userID = usrID;
					}
					else if(mssg.startsWith("[IdCheck]")) {
						String str[] = mssg.split(",");
						sendToOne(str[0]+","+str[1]+","+idRedunCheck(str[2]), socket);
					}
					else if(mssg.startsWith("[NickCheck]")) {
						String str[] = mssg.split(",");
						sendToOne(str[0]+","+str[1]+","+nickRedunCheck(str[2]), socket);
					}
					else if(mssg.startsWith("[Register]")) {
						String str[] = mssg.split(",");
						Object[] info = {str[2],str[3],str[4]};
						sendToOne(str[0]+","+str[1]+","+signupUser(info), socket);
					}
					else if(mssg.startsWith("[TopRank]")) {
						sendToOne(mssg+","+getTopRank(), socket);
					}
					else if(mssg.startsWith("[Info]")) {
						String str[] = mssg.split(",");
						sendToOne(mssg+","+getInfo(str[2], Integer.parseInt(str[1])), socket);
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
	public static String getInfo(String id, Integer userId) {
		ArrayList<String> info = Server.LoginDB.getUserInfo(id);
		ArrayList<Integer> rankInfo = Server.RankDB.getWinLose(userId);
		String information = "";
		for(int i=0; i<info.size(); i++)
			information += (info.get(i)+"/");
		for(int i=0; i<rankInfo.size(); i++)
			information += (rankInfo.get(i)+"/");
		return information;
	}
}
