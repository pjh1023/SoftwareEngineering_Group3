import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Board extends Canvas implements MouseListener  {
	final int size   = 15 ;   // 오목판 줄 수
	final int cell = 30; // 오목판 줄 간격
	
	final int BLACK=-1;
	final int WHITE=1;
	
	int [][]map;
	int color;
	boolean enable = false;

	Image img = null;
	Graphics gImg = null;
	
	public Board() {
		map = new int[size+2][];
		for(int i=0; i<map.length; i++)
			map[i] = new int[size + 2];
//		setBackground(new Color(205,165,60));
//		setSize(size*(cell+1)+size, size*(cell+1)+size);
		
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		if(gImg == null) {
			img = createImage(getWidth(), getHeight());
			gImg = img.getGraphics();
		}
		
		if(img==null) return;
//		drawBoard(g);
	}
	
	public void drawBoard(Graphics g) {
		gImg.setColor(Color.BLACK);
		
		for(int i=1; i<=size;i++) {
			gImg.drawLine(cell, i * cell, cell * size, i * cell);
			gImg.drawLine(i * cell, cell, i * cell, cell * size);
		}
		
		g.drawImage(img,0,0,this);
	}
	/*





	*/
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}	
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}