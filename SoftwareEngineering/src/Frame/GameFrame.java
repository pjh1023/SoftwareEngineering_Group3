package Frame;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class GameFrame extends JFrame{
	public static int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static int startX = 0;
	public static int startY = 0;

	public static int frameWidth = screenWidth*2/3;
	public static int frameHeight = screenHeight-25;
	
	
	public Frame_Components.GameFramePanel gamePanel = new Frame_Components.GameFramePanel();
	
	public void setThis() {
		this.setBounds(startX, startY, frameWidth, frameHeight); // setBounds로 왼쪽위(시작지점) x,y값과 원하는 frame의 크기를 넣어줍니다.
		getContentPane().setLayout(null); // Frame과 Panel에는 layout을 지정해줄 수 있습니다. null로 하게되면 자유롭게 component들을 위치 지정해서 넣어줄 수 있고(노가다) 다른 layout들은 찾아보시면 좋을 것 같습니다 :)
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(gamePanel); // Frame에 Panel을 넣어줍니다. 실제 Panel에 버튼과 빈칸, 스크롤 등이 부착되어있습니다.
		this.setVisible(true); // setVisible을 통해서 해당 frame을 보이게/안보이게 지정할 수 있습니다.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫으면 프로그램이 종료하도록 합니다.
	}
	
}
