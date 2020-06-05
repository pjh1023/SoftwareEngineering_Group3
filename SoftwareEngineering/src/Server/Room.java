package Server;

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
		//사람수, 다른멤버 닉네임.,
		String msg = "[Open],";
		msg += userV.size();
		for(int i=0; i<4; i++) {
			for (int j=0;j<4;j++) {
				if (i != j)
					msg += "," + userV.get(j).nickname;
			}
				TcpMultichatServer.sendToOne(msg, userV.get(i).socket);
			}
		}
	}

}
