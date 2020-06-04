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

import Network.ClientNetwork.ClientSender;


public class ClientNetwork {

	public static ClientSender cs ;
	Socket socket;
	boolean isReady;
	public static int userID; 
	
	
	
	public void connect(String nickname) {
		try {
			String serverIp = "192.168.0.9"; 
			Socket socket = new Socket(serverIp, 7778); //portNum
			System.out.println("client connected");
			Thread sender = new Thread(new ClientSender(socket, nickname)); 
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			cs = new ClientSender(socket, nickname);
			
			sender.start();
			receiver.start();
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
						if(str[2].equals("true")) {
							// login Success
							Frame.Main.loginFrame.logPanel.setVisible(false);
							Frame.Main.loginFrame.remove(Frame.Main.loginFrame.logPanel);
							Frame.Main.loginFrame.dispose();
							Frame.Main.waitingFrame = new Frame.WaitingFrame(); //대기방으로 넘어가기 
							Frame.Main.waitingFrame.setThis();
							
							ClientSender.sendMsg("[TopRank],"+Network.ClientNetwork.userID);
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
								Frame.WaitingFrame.rank1.setText("*1*\t"+inform[0]+"\tWins:"+inform[1]+"\tLoses:"+inform[2]+"\tWin Rate:"+inform[3]+"%");
								break;
							case 1:
								Frame.WaitingFrame.rank2.setText("=2=\t"+inform[0]+"\tWins:"+inform[1]+"\tLoses:"+inform[2]+"\tWin Rate:"+inform[3]+"%");
								break;
							case 2:
								Frame.WaitingFrame.rank3.setText("+3+\t"+inform[0]+"\tWins:"+inform[1]+"\tLoses:"+inform[2]+"\tWin Rate:"+inform[3]+"%");
								break;
							case 3:
								Frame.WaitingFrame.rank4.setText("-4-\t"+inform[0]+"\tWins:"+inform[1]+"\tLoses:"+inform[2]+"\tWin Rate:"+inform[3]+"%");
								break;
							case 4:
								Frame.WaitingFrame.rank5.setText(".5.\t"+inform[0]+"\tWins:"+inform[1]+"\tLoses:"+inform[2]+"\tWin Rate:"+inform[3]+"%");
								break;
							}
							
						}
					}
					
				}catch(IOException e) {}
				
			}//while
		}//run
	}
}
