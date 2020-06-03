import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Board extends Canvas implements MouseListener  {
	int color;
	boolean enable = false;

	Image img = null;
	Graphics gImg = null;
	
	public Board() {
		
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		if(gImg == null) {
			img = createImage(getWidth(), getHeight());
			gImg = img.getGraphics();
		}
		
		if(img==null) return;
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}	
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
