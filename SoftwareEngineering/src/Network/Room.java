package Network;

import java.util.HashMap;
import java.util.Vector;

import Server.TcpIpMultichatServer;
import Server.TcpIpMultichatServer.ServerReceiver;

public class Room {
	public int roomNum;
	public Vector<ServerReceiver> userV; //같은 방에 접속한 client정보 
	
	public Room(int roomNum) {
		userV = new Vector<>();
		roomNum = this.roomNum;
	}

}
