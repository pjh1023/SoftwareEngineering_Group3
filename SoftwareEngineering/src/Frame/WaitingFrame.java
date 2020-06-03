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
	private Font headFont2 = new Font ("Arial", Font.BOLD, frameHeight / 20);
	public JPanel Ranking = new JPanel();
	private JLabel RankTitle = new JLabel("Ranking");
	
	public static GameFrame gameFrame = new GameFrame();
	
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
        
        
        //Ranking
        Ranking.setBounds(550, 230, 200, 300);
        Ranking.setBackground(new Color(255,255,255,200));
        
        RankTitle.setFont(headFont2);
        RankTitle.setLocation(565, 245);
        RankTitle.setSize(RankTitle.getPreferredSize().width, RankTitle.getPreferredSize().height);
        
        layeredPane.add(RankTitle);
        layeredPane.add(Ranking);
        
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
        readyButton.setBounds(800, 500, 200, 100);
        readyButton.addActionListener(this);
        
        // 버튼 투명처리
        readyButton.setBorderPainted(false);
        readyButton.setFocusPainted(false);
        readyButton.setContentAreaFilled(false);
 
        layeredPane.add(readyButton);
 
   
        
        
        
        // 마지막 추가들
        layeredPane.add(panel);
        add(layeredPane);
        setVisible(false);
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
            g.fillRect(80, 230, 430, 300);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
		this.dispose();
    	gameFrame.setThis();
    	//chat Frame enable over here
    	
    }
 
}