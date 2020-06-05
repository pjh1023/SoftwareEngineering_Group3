package Frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import Network.ClientNetwork;

//import Frame_Components.ChatPanel;

import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ChatFrame extends JFrame implements ActionListener{

	//private Frame_Components.ChatPanel chatPanel = new Frame_Components.ChatPanel();

	public static final int width = Frame.GameFrame.frameWidth;
	public static final int height = Frame.GameFrame.frameHeight;
	
	static JTextArea ta= new JTextArea(20,20); 

	JButton send = new JButton("SEND");
	JTextField tf= new JTextField();
	public JPanel chatPanel = new JPanel();
	BorderLayout br = new BorderLayout();
	
	public ChatFrame () {
		this.setThis();
		this.setTitle("Chatting ROOM");
		
		tf.addActionListener(this);
		send.addActionListener(this);

	}
	
	public void setThis() {
		this.setBounds(1000,100, 300, 500);
		getContentPane().setLayout(br);
		this.setLayout(br);
		this.addComponents();
		this.setComponents();
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫으면 프로그램이 종료하도록 합니다.
	}
	public void addComponents() {
		this.add(BorderLayout.SOUTH, chatPanel);
		chatPanel.add(tf);
		chatPanel.add(send);
		this.add(BorderLayout.CENTER, ta);
		this.pack();
	}

	public void setComponents() {
		tf.setColumns(20); 
		tf.addKeyListener(new KeyAdapter() { 
            public void keyReleased(KeyEvent e) {
                 char keyCode = e.getKeyChar();
                 if (keyCode == KeyEvent.VK_ENTER) {
                     actionPerformed(null); 
                } 
            }

        });


		send.setBackground(Color.yellow);
	}
	public static void chat_out(String msg) {
		msg += "\n";
		ta.append(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    	if(e.getSource().equals(send)) {
//    		System.out.println("push");
    		if(tf.getText().equals(""))
    			return;
    		ClientNetwork.inBuf.push("[Msg],"+"["+Main.nickname+"]: ,"+tf.getText());
    		//ClientNetwork.cs.sendMsg("[Msg],"+"["+Main.nickname+"]: ,"+tf.getText());
    		tf.setText("");
    	}
	}

	
}