package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
public class WaitingFrame extends JFrame implements ActionListener {
	public static int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static int startX = screenWidth / 6; 
	public static int startY = screenHeight / 10;
	
	public static int frameWidth = 1023; //screenWidth*2/3; //1023;
	public static int frameHeight = 614; //screenHeight*2/3; //614;
	
	private JLabel headLabel = new JLabel("Handong Marble", JLabel.CENTER); //한동 마
	private Font headFont = new Font ("Arial", Font.BOLD, frameHeight / 13);
	private JLabel playInfoLabel = new JLabel("Player Information");//플레이어어 정
	private Font headFont2 = new Font ("Arial", Font.BOLD, 30);
	
	public JPanel Ranking = new JPanel();
	private JLabel RankTitle = new JLabel("Ranking");
	private Font bodyFont = new Font ("Arial", Font.BOLD, frameHeight / 70);
	
	public static JLabel rank1 = new JLabel("*1*\tUSER\twins: 0 loses: 0 | win rate:  0%");
	public static JLabel rank2 = new JLabel("+2+\tUSER\twins: 0 loses: 0 | win rate:  0%");
	public static JLabel rank3 = new JLabel("-3-\tUSER\twins: 0 loses: 0 | win rate:  0%");
	public static JLabel rank4 = new JLabel("_4_\tUSER\twins: 0 loses: 0 | win rate:  0%");
	public static JLabel rank5 = new JLabel(".5.\tUSER\twins: 0 loses: 0 | win rate:  0%");
	
	public static GameFrame gameFrame = new GameFrame();
	public static ChatFrame chatFrame = new ChatFrame();

	//Frame components 
	//public Frame_Components.WaitingFramePanel waitPanel = new Frame_Components.WaitingFramePanel();
    BufferedImage img = null;
    JTextField loginTextField;
    JPasswordField passwordField;
    JButton readyButton;
    ImageIcon icon;
    Image tempimg;
 
    public void setThis() {
    	this.setVisible(true);
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
 
    // 생성자
    public WaitingFrame() {
        setTitle("Waiting Page");
        this.setBounds(startX, startY, frameWidth, frameHeight);
        //setSize(1600, 900);
        //this.add(waitPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        // 레이아웃 설정
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        layeredPane.setLayout(null);
 
        //제목 설정 
		headLabel.setFont(headFont);
		headLabel.setForeground(new Color(15, 19, 114));
		headLabel.setSize(headLabel.getPreferredSize().width, headLabel.getPreferredSize().height);
		headLabel.setLocation(frameWidth / 4 - headLabel.getPreferredSize().width / 2, frameHeight/7);
        
        layeredPane.add(headLabel);
        //플레이어정보 
        playInfoLabel.setFont(headFont2);
        playInfoLabel.setForeground(new Color(79,79,79));
        playInfoLabel.setSize(playInfoLabel.getPreferredSize().width, playInfoLabel.getPreferredSize().height);
        playInfoLabel.setLocation(100, 250);
        
        layeredPane.add(playInfoLabel);
        
       
        
        
        // 패널1
        // 이미지 받아오기
        try {
            img = ImageIO.read(new File("img/handong.jpg"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
        }
         
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 1600, 900);
                
 
        //준비 버튼 추가
        icon = new ImageIcon("img/ready.png");
        readyButton = new JButton(resizeIcon(icon, 100, 50));
        readyButton.setBounds(850, 500, 100, 50);
        readyButton.addActionListener(this);
        
        // 버튼 투명처리
        readyButton.setBorderPainted(false);
        readyButton.setFocusPainted(false);
        readyButton.setContentAreaFilled(false);
 
        layeredPane.add(readyButton);
        
        showRank();
        
        Ranking.setLayout(new BoxLayout(Ranking, BoxLayout.Y_AXIS));
        Ranking.add(RankTitle);
        Ranking.add(Box.createVerticalStrut(10));
        Ranking.add(rank1);
        Ranking.add(Box.createVerticalStrut(10));
        Ranking.add(rank2);
        Ranking.add(Box.createVerticalStrut(10));
        Ranking.add(rank3);
        Ranking.add(Box.createVerticalStrut(10));
        Ranking.add(rank4);
        Ranking.add(Box.createVerticalStrut(10));
        Ranking.add(rank5);
        
        layeredPane.add(Ranking);
        
        
        
        // 마지막 추가들
        layeredPane.add(panel);
        add(layeredPane);
        setVisible(false);
    }
    
	String user1 = "1st";
	String user2 = "2nd";
	String user3 = "3rd";
	String user4 = "4th";
	String user5 = "5th";
	
	int win1 = 5;
	int win2 = 4;
	int win3 = 3;
	int win4 = 2;
	int win5 = 1;
	
	int lose1 = 0;
	int lose2 = 1;
	int lose3 = 2;
	int lose4 = 3;
	int lose5 = 4;
	
	float rate1 = (float)win1/(win1+lose1);
	float rate2 = (float)win2/(win2+lose2);
	float rate3 = (float)win3/(win3+lose3);
	float rate4 = (float)win4/(win4+lose4);
	float rate5 = (float)win5/(win5+lose5);
	
    Font font1 = new Font ("Arial", Font.BOLD, 24);
    Font font2 = new Font ("Arial", Font.BOLD, 22);
    Font font3 = new Font ("Arial", Font.BOLD, 20);
    Font font4 = new Font ("Arial", Font.BOLD, 18);
    Font font5 = new Font ("Arial", Font.BOLD, 16);
	
    public void showRank() {
        //Ranking
        Ranking.setBounds(500, 230, 300, 250);
        Ranking.setBackground(new Color(255,255,255,200));
        
        RankTitle.setFont(headFont2);
        RankTitle.setLocation(610, 240);
        //RankTitle.setSize(RankTitle.getPreferredSize().width, RankTitle.getPreferredSize().height);
        


       user1 = user1 + " | " + Integer.toString(win1) + "승 " + Integer.toString(lose1)+ "패  승률 : " + Float.toString(rate1* 1000 /10)+"%";
       rank1.setText(user1);
       rank1.setForeground(new Color(204,153,0));
       rank1.setFont(font1);
       
       user2 = user2 + " | " + Integer.toString(win2) + "승 " + Integer.toString(lose2)+ "패  승률 : " + Float.toString(rate2* 1000 /10)+"%";
       rank2.setText(user2);
       rank2.setForeground(new Color(110,110,110));
       rank2.setFont(font2);
       
       user3 = user3 + " | " + Integer.toString(win3) + "승 " + Integer.toString(lose3)+ "패  승률 : " + Float.toString(rate3* 1000 /10)+"%";
       rank3.setText(user3);
       rank3.setForeground(new Color(102,051,000));
       rank3.setFont(font3);
       
       user4 = user4 + " | " + Integer.toString(win4) + "승 " + Integer.toString(lose4)+ "패  승률 : " + Float.toString(rate4* 1000 /10)+"%";
       rank4.setText(user4);
       rank4.setForeground(Color.black);
       rank4.setFont(font4);
       
       user5 = user5 + " | " + Integer.toString(win5) + "승 " + Integer.toString(lose5)+ "패  승률 : " + Float.toString(rate5* 1000/10)+"%";
       rank5.setText(user5);
       rank5.setForeground(Color.black);
       rank5.setFont(font5);
        
    }
    
    
    private static ImageIcon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
 }
 
    class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
            //플레이어정보 박스 
            g.setColor(new Color(255,255,255,200));
            g.fillRect(80, 230, 350, 300);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
		this.dispose();
    	gameFrame.setThis();
    	
    	chatFrame.setThis();
    	chatFrame.setVisible(true);
    	
    	//chat Frame enable over here
    	
    }
 
}