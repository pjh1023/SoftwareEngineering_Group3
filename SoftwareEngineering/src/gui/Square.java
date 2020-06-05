package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Frame.GameFrame;
import backend.game.City;
import backend.game.Land;

public class Square extends JButton {

	private int number;
	String description;
	JLabel nameLabel;
	static int totalSquares = 0;
	private ArrayList<JPanel> colorBoxes;
	
	private Land land;
	
	public int getRentPrice() {
		if (land instanceof City) {
			City city = (City)land;
			return city.getTollFee();
		}
		else
			return -1;
	}
	public Square(int xCoord, int yCoord, int width, int height, Land land, int rotationDegrees) {
		number = totalSquares;
		totalSquares++;
		this.land = land;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, width, height);
		this.setLayout(null);
		this.colorBoxes = new ArrayList<JPanel>();
		
		for (int i = 0; i < 4; i++) {
			JPanel colorBox = new JPanel();
			colorBoxes.add(colorBox);
			this.add(colorBox);
			colorBox.setBackground(Color.white);
			colorBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		}

		if(rotationDegrees == 0) {
			nameLabel = new JLabel(land.getName());
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setBounds(0,20,this.getWidth(),20);
			this.add(nameLabel);
		} else {	
			// rotating a Jlabel: https://www.daniweb.com/programming/software-development/threads/390060/rotate-jlabel-or-image-in-label
			
			nameLabel = new JLabel(land.getName()) {
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D)g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					AffineTransform aT = g2.getTransform();
					Shape oldshape = g2.getClip();
					double x = getWidth()/2.0;
					double y = getHeight()/2.0;
					aT.rotate(Math.toRadians(rotationDegrees), x, y);
					g2.setTransform(aT);
					g2.setClip(oldshape);
					super.paintComponent(g);
				}
			};
			if(rotationDegrees == 90) {
				nameLabel.setBounds(20, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == -90) {
				nameLabel.setBounds(-10, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == 180) {
				nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == 135 || rotationDegrees == -135 || rotationDegrees == -45 || rotationDegrees == 45) {
				nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
			}
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(nameLabel);
		} 
		this.setBackground(new Color(230, 230, 230));
	}

	public void paintComponent(Graphics g) {
//		// 판 회전시키기
//		Graphics2D g2d = (Graphics2D) g;
//        int w2 = getWidth() / 2;
//        int h2 = getHeight() / 2;
//        g2d.rotate( Math.PI, w2, h2);
		super.paintComponent(g);
		if (land instanceof City) {
			if (this.number / 5 == 0) {
				for (int i= 1; i<= colorBoxes.size(); i++)
					colorBoxes.get(i-1).setBounds(0, this.getHeight() - 20, this.getWidth() * i/ colorBoxes.size(), 20 );
					
				//colorBox.setBounds(0, this.getHeight()-20, this.getWidth(), 20);
			}
			else if (this.number/ 5 == 1) {
				for (int i= 1; i<= colorBoxes.size(); i++)
					colorBoxes.get(i-1).setBounds(0, 0, 20, this.getHeight() * i/ colorBoxes.size() );
				//colorBox.setBounds(0, 0, 20, this.getHeight());
			}
			else if (this.number / 5 == 2) {
				for (int i= 1; i<= colorBoxes.size(); i++)
					colorBoxes.get(i-1).setBounds(0, 0, this.getWidth() * i/ colorBoxes.size(), 20 );
				//colorBox.setBounds(0,0, this.getWidth(), 20);
			}
			else {
				for (int i= 1; i<= colorBoxes.size(); i++)
					colorBoxes.get(i-1).setBounds(this.getWidth()-20, 0, 20, this.getHeight() * i/ colorBoxes.size() );
				//colorBox.setBounds(this.getWidth()-20, 0, 20, this.getHeight());
			}
		}
	}

	public void setBoxColor(int pNum) {
		if (land instanceof City) {
			City c = (City)land;
			if (pNum >= 0) 
				colorBoxes.get(c.getLevel()).setBackground(GameFrame.COLORS[pNum] );
		}
	}
	public int getNumber() {
		return this.number;
	}

}
