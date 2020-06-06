package Server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import Server.TcpIpMultichatServer.ServerReceiver;

public class Room {
	public int roomNum;
	public Vector<ServerReceiver> userV; //같은 방에 접속한 client정보 
	
	public Room(int roomNum) {
		userV = new Vector<>();
		roomNum = this.roomNum;
	}
	
	public void sendInfo() {
		// 다른멤버 닉네임 보내주기 
		for(int i=0; i<userV.size(); i++) {
			String msg = "[Open],";
			msg += userV.size();
			for (int j=0;j<userV.size();j++) {
				if (i != j)
					msg += "," + userV.get(j).nickname; 
			}
			TcpIpMultichatServer.sendToOne(msg, userV.get(i).socket);
		}
	}
	public void decideTurn() {
		Random random = new Random();
		ArrayList<Integer> order = new ArrayList<> ();
		order.add(34);
		if (userV.size() == 2) {
			order.add(32);
		}
		if (userV.size() == 3) {
			order.add(11);
			order.add(21);
		}
		if (userV.size() == 4) {
			order.add(22);
			order.add(46);
			order.add(36);
		}
		
		Collections.shuffle(order);
		
		//send
		for(int i=0; i<userV.size(); i++) {
			for (int j=0;j<userV.size();j++) {
				if (i != j)
					TcpIpMultichatServer.sendToOne("[Game]," + order.get(j), userV.get(i).socket);
			}
		}
	}


}
