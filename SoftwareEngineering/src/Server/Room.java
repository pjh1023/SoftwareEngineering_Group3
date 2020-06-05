package Server;
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
		String msg = "[Open],";
		msg += userV.size();
		for(int i=0; i<4; i++) {
			for (int j=0;j<4;j++) {
				if (i != j)
					msg += "," + userV.get(j).nickname;
			}
			TcpIpMultichatServer.sendToOne(msg, userV.get(i).socket);
		}
	}
	public void decideTurn() {
		Random random = new Random();
		int player[] = new int[userV.size()];
		int sum1 = 0, sum2=0;
		for(int i=0; i<userV.size()-1; i++) {
			player[i] = random.nextInt(6) + random.nextInt(6) + 11;
			if (i >= 2 && player[i]%10 + player[i]/10 == player[0]%10 + player[0]/10) { 
				i--;
				continue;
			}
			else if(i >= 3 && player[i]%10 + player[i]/10 == player[1]%10 + player[1]/10) {
				i--;
				continue;
			}
			sum1 += player[i]/10;
			sum2 += player[i]%10;
		}
		player[userV.size()-1] = (6 - sum1%6) * 10 + (6 - sum2%6);
		
		//send
		for(int i=0; i<4; i++) {
			for (int j=0;j<4;j++) {
				if (i != j)
					TcpIpMultichatServer.sendToOne("[Game]," + player[j], userV.get(i).socket);
			}
		}
	}


}
