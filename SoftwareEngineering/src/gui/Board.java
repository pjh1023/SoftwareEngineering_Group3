package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import backend.game.Land;

public class Board extends JPanel {

	private ArrayList<Square> allSquares = new ArrayList<Square>();
	private ArrayList<Square> unbuyableSquares = new ArrayList<Square>(); // squares like "Go", "Chances" etc...
	
	public ArrayList<Square> getUnbuyableSquares(){
		return unbuyableSquares;
	}
	
	public ArrayList<Square> getAllSquares(){
		return allSquares;
	}
	
	public Square getSquareAtIndex(int location) {
		return allSquares.get(location);
	}

	public Board(int xCoord, int yCoord, int width, int height, ArrayList<Land> board) {
		setBackground(new Color(51, 255, 153)); //add color
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, width, height);
		this.setLayout(null);
		initializeSquares(board);
	}

	private void initializeSquares(ArrayList<Land> board) {		
		// squares on the top
		
		Square square00 = new Square(6,6,100,100,board.get(0),135);
		this.add(square00);
		allSquares.add(square00);
		unbuyableSquares.add(square00);
		
		Square square01 = new Square(106,6,100,100,board.get(1),180);
		this.add(square01);
		allSquares.add(square01);
		
		Square square02 = new Square(206,6,100,100,board.get(2),180);
		this.add(square02);
		allSquares.add(square02);
		
		Square square03 = new Square(306,6,100,100,board.get(3),180);
		this.add(square03);
		allSquares.add(square03);
		
		Square square04 = new Square(406,6,100,100,board.get(4),180);
		this.add(square04);
		allSquares.add(square04);
		
		Square square05 = new Square(506,6,100,100,board.get(5),-135);
		this.add(square05);
		allSquares.add(square05);
		unbuyableSquares.add(square05);

		// squares on the right
		Square square06 = new Square(506,106,100,100,board.get(6),-90);
		this.add(square06);
		allSquares.add(square06);
		
		Square square07 = new Square(506,206,100,100,board.get(7),-90);
		this.add(square07);
		allSquares.add(square07);
		
		Square square08 = new Square(506,306,100,100,board.get(8),-90);
		this.add(square08);
		allSquares.add(square08);
		
		Square square09 = new Square(506,406,100,100,board.get(9),-90);
		this.add(square09);
		allSquares.add(square09);
		
		Square square10 = new Square(506,506,100,100,board.get(10),-45);
		this.add(square10);
		allSquares.add(square10);
		unbuyableSquares.add(square10);

		// squares on the bottom
		Square square11 = new Square(406,506,100,100,board.get(11),0);
		this.add(square11);
		allSquares.add(square11);
		
		Square square12 = new Square(306,506,100,100,board.get(12),0);
		this.add(square12);
		allSquares.add(square12);
		
		Square square13 = new Square(206,506,100,100,board.get(13),0);
		this.add(square13);
		allSquares.add(square13);
		
		Square square14 = new Square(106,506,100,100,board.get(14),0);
		this.add(square14);
		allSquares.add(square14);
		
		Square square15 = new Square(6,506,100,100,board.get(15),45);
		this.add(square15);
		allSquares.add(square15);
		unbuyableSquares.add(square15);
		
		// squares on the left
		Square square16 = new Square(6,406,100,100,board.get(16),90);
		this.add(square16);
		allSquares.add(square16);
		
		Square square17 = new Square(6,306,100,100,board.get(17),90);
		this.add(square17);
		allSquares.add(square17);
		
		Square square18 = new Square(6,206,100,100,board.get(18),90);
		this.add(square18);
		allSquares.add(square18);
		
		Square square19 = new Square(6,106,100,100,board.get(19),90);
		this.add(square19);
		allSquares.add(square19);		

		JLabel lblMonopoly = new JLabel("HGUMARBLE"){
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				AffineTransform aT = g2.getTransform();
				Shape oldshape = g2.getClip();
				double x = getWidth()/2.0;
				double y = getHeight()/2.0;
				aT.rotate(Math.toRadians(-145), x, y);
				g2.setTransform(aT);
				g2.setClip(oldshape);
//				Graphics2D g2d = (Graphics2D) g;
//		        int w2 = getWidth() / 2;
//		        int h2 = getHeight() / 2;
//		        g2d.rotate( Math.PI*1/3, w2, h2);
				super.paintComponent(g);
			}
		};
		lblMonopoly.setForeground(Color.WHITE);
		lblMonopoly.setBackground(Color.RED);
		lblMonopoly.setOpaque(true);
		lblMonopoly.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonopoly.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblMonopoly.setBounds(179, 277, 263, 55);
		this.add(lblMonopoly);
		
	}

	public void paintComponent(Graphics g) {
		// 판 회전시키기
		Graphics2D g2d = (Graphics2D) g;
        int w2 = getWidth() / 2;
        int h2 = getHeight() / 2;
        g2d.rotate( Math.PI, w2, h2);
		super.paintComponent(g);
	}




}
