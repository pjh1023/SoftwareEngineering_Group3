package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class ClientNetwork {

	public static ClientSender cs ;
	Socket socket;
	boolean isReady;
	
	
	
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
							System.out.println("Available ID");
						}
						else {
							System.out.println("Existing ID");
							
						}
					}
					else if(message.contains("[NickCheck]")) {
						String str[] = message.split(",");
						if(str[2].equals("true")) {
							System.out.println("Available Nickname");
						}
						else {
							System.out.println("Existing Nickname");
							
						}
					}
				}catch(IOException e) {}
				
			}//while
		}//run
	}
}
