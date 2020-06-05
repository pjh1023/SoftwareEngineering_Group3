package Network;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Frame.ChatFrame;
import backend.game.BoundedBuffer;


public class ClientNetwork {

	public static ClientSender cs ;
	public static Socket socket;
	boolean isReady;
	public static int userID; 
	public static String nickname;
	
	private BoundedBuffer<Integer> inBuf;
	private BoundedBuffer<Integer> outBuf;
	
	//sjb - 172.17.220.115 
	//pjh- 192.168.0.9
	
	public void connect(String nickname) {
		try {
//			String serverIp = "192.168.0.12";
			String serverIp = "192.168.0.9";
			Socket socket = new Socket(serverIp, 7778); //portNum
			System.out.println("client connected");
			Thread sender = new Thread(new ClientSender(socket, nickname)); 
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			cs = new ClientSender(socket, nickname);
			
			sender.start();
			receiver.start();
			inBuf = new BoundedBuffer<Integer>();
			outBuf = new BoundedBuffer<Integer>();
		} catch(ConnectException ce) {
			ce.printStackTrace();
		} catch(Exception e){}
	}
	
	
	public static class ClientSender extends Thread{
		Socket socket;
		static DataOutputStream out;
		String nickname;
		String id;
		ClientSender(Socket socket, String nickname){
			this.socket = socket;
			this.nickname = nickname;
			
			try {
				out = new DataOutputStream(socket.getOutputStream());
			} catch(Exception e) {}
		}
		
		public void run() {
			Scanner scanner = new Scanner(System.in);
			try {
				if(out!=null)
					out.writeUTF(nickname);
				
				while(out != null)
					out.writeUTF("["+nickname+"]"+scanner.nextLine());//input
			} catch(IOException e) {}
		}
		
		public static void sendMsg(String msg) {
			try {
				out.writeUTF(msg);
			}catch(Exception e) {}
		}
	}
	
	class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		
		ClientReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			}catch(IOException e) {}
		}
		
		public void run() {
			while(in!=null) {
				try {
					String message = in.readUTF(); // message format: [type],[user#],content1,content2
					
					if(message.contains("[Msg]")) {
						String str[] = message.split(",");
//						System.out.println(str[0]+str[1]+str[2]);
						ChatFrame.chat_out(str[1] + str[2]);
					}
					else if(message.contains("[Ready]")) {
						
					}
					else if(message.contains("[Win]")) {
						
					}
					else if(message.contains("[Lose]")) {
						
					}
					else if(message.contains("[Exit]")) {
						
					}
					else if(message.contains("[Login]")) {
						String str[] = message.split(",");
						if(str[3].equals("true")) { //str[2] is id
							// login Success
							Frame.Main.loginFrame.logPanel.setVisible(false);
							Frame.Main.loginFrame.remove(Frame.Main.loginFrame.logPanel);
							Frame.Main.loginFrame.dispose();
							Frame.Main.waitingFrame = new Frame.WaitingFrame(); //대기방으로 넘어가기 
							Frame.Main.waitingFrame.setThis();
							
							userID = Integer.parseInt(str[1]);
							nickname = str[2];
							System.out.println("userID: "+userID+"\tNickname: "+nickname);
							ClientSender.sendMsg("[TopRank],"+Network.ClientNetwork.userID);
							ClientSender.sendMsg("[Info],"+Network.ClientNetwork.userID+","+Frame_Components.LoginFramePanel.loginTypePanel.idTextF.getText());
						}
						else {
//							System.out.println("login Failed");
//							DB.getInstance().updateCurrentWrongCount(mainClasses.MainController.mainFrame.logPanel.getLogInfoPanel().idTextF.getText());
							JOptionPane.showMessageDialog(null, "Wrong ID or PW!", "WARNING", JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(message.contains("[IdCheck]")) {
						String str[] = message.split(",");
						if(str[2].equals("true")) {
							Frame_Components.SignupFramePanel.iCheckResult = true;
							Frame_Components.SignupFramePanel.notice.setText("Available ID :)");
							Frame_Components.SignupFramePanel.notice.setForeground(new Color(0,200,0));
							System.out.println("Available ID");
						}
						else {
							Frame_Components.SignupFramePanel.iCheckResult = false;
							Frame_Components.SignupFramePanel.notice.setText("Invalid ID");
							Frame_Components.SignupFramePanel.notice.setForeground(Color.red);
							System.out.println("Existing ID");
						}
						
						if(Frame_Components.SignupFramePanel.iCheckResult&&Frame_Components.SignupFramePanel.nCheckResult) {
							Frame_Components.SignupFramePanel.notice.setText("Register Enable!");
							Frame_Components.SignupFramePanel.notice.setForeground(new Color(0,200,0));
							Frame_Components.SignupFramePanel.regButton.setEnabled(true);
						}
					}
					else if(message.contains("[NickCheck]")) {
						String str[] = message.split(",");
						if(str[2].equals("true")) {
							Frame_Components.SignupFramePanel.nCheckResult = true;
							Frame_Components.SignupFramePanel.notice.setText("Available Nickname :)");
							Frame_Components.SignupFramePanel.notice.setForeground(new Color(0,200,0));
							System.out.println("Available Nickname");
						}
						else {
							Frame_Components.SignupFramePanel.nCheckResult = false;
							Frame_Components.SignupFramePanel.notice.setText("Invalid Nickname");
							Frame_Components.SignupFramePanel.notice.setForeground(Color.red);
							System.out.println("Existing Nickname");
						}
						
						if(Frame_Components.SignupFramePanel.iCheckResult&&Frame_Components.SignupFramePanel.nCheckResult) {
							Frame_Components.SignupFramePanel.notice.setText("Register Enable!");
							Frame_Components.SignupFramePanel.notice.setForeground(new Color(0,200,0));
							Frame_Components.SignupFramePanel.regButton.setEnabled(true);
						}
					}
					else if(message.contains("[Register]")) {
						String str[] = message.split(",");
						userID = Integer.parseInt(str[2]);
						System.out.println("Successfully registered");
					}
					else if(message.contains("[TopRank]")) { // received format: nick/wins/loses/rate,nick/wins/loses/rate,...
						String str[] = message.split(",");
						ArrayList<String> top5 = new ArrayList<String>();
						for(int i=2; i<str.length; i++) {
							top5.add(str[i]);
						}
						for(int i=0; i<top5.size(); i++) {
							String inform[] = top5.get(i).split("/");
							switch(i) {
							case 0:
								Frame.WaitingFrame.rank1.setText("1st | "+inform[0]+"\t"+inform[1]+"승\t"+inform[2]+"패\t승률 : "+(Double.parseDouble(inform[3])*100)+"%");
								break;
							case 1:
								Frame.WaitingFrame.rank2.setText("2nd | "+inform[0]+"\t"+inform[1]+"승\t"+inform[2]+"패\t승률 : "+(Double.parseDouble(inform[3])*100)+"%");
								break;
							case 2:
								Frame.WaitingFrame.rank3.setText("3rd | "+inform[0]+"\t"+inform[1]+"승\t"+inform[2]+"패\t승률 : "+(Double.parseDouble(inform[3])*100)+"%");
								break;
							case 3:
								Frame.WaitingFrame.rank4.setText("4th | "+inform[0]+"\t"+inform[1]+"승\t"+inform[2]+"패\t승률 : "+(Double.parseDouble(inform[3])*100)+"%");
								break;
							case 4:
								Frame.WaitingFrame.rank5.setText("5th | "+inform[0]+"\t"+inform[1]+"승\t"+inform[2]+"패\t승률 : "+(Double.parseDouble(inform[3])*100)+"%");
								break;
							}
							
						}
					}
					else if(message.contains("[Info]")) {
						System.out.println(message);
						String str[] = message.split(",");
						String inform[] = str[3].split("/"); // id/pw/nickname/wins/loses
						Frame.WaitingFrame.myID.setText("ID:\t"+inform[0]);
						Frame.WaitingFrame.myNickName.setText("Nickname:\t"+inform[2]);
						Frame.WaitingFrame.myRate.setText("Wins:\t"+inform[3]+"\tLoses:\t"+inform[4]);
					}
					else if (message.startsWith("[Game]")) {
						System.out.print(message);
						String str[] = message.split(",");
						outBuf.push(0);
					}					
					else if(message.contains("[Open]")) {
						Frame.WaitingFrame.self.setVisible(false);
						Frame.WaitingFrame.gameFrame.setThis();
				    	
						Frame.WaitingFrame.chatFrame.setThis();
						Frame.WaitingFrame.chatFrame.setVisible(true);
				    	
				    	//chat Frame enable over here
						Frame.WaitingFrame.gameFrame.setThis();
						Frame.WaitingFrame.chatFrame.setThis();
						Frame.WaitingFrame.chatFrame.setVisible(true);
					}
					
				}catch(IOException e) {}
				
			}//while
		}//run
	}
}
