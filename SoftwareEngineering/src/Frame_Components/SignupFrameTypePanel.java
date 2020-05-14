package Frame_Components;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupFrameTypePanel extends JPanel{
	private JLabel[] labels = {new JLabel("ID: "), new JLabel("PW: "), new JLabel("Name: "),  new JLabel("Nickname: "), new JLabel("Gender: ")};
	
	public JTextField idTextF = new JTextField();
	public JPasswordField pwTextF = new JPasswordField();
	public JTextField nameTextF = new JTextField();
	public JTextField nickNameTextF = new JTextField();
	public SignupFrameCheckboxPanel genderCheckB = new SignupFrameCheckboxPanel("Male", "Female");
	
	private Component[] fields = {idTextF, pwTextF, nameTextF, nickNameTextF, genderCheckB};
	
	public static Font font = new Font ("Arial", Font.BOLD, Frame.SignupFrame.frameHeight / 30);
	
	private void setLabels(){
		for (JLabel label : labels) {
			label.setFont(font);
			label.setHorizontalAlignment(JLabel.LEFT);
		}
	}
	
	public void setThis(Component prevComp) {
		this.setBounds(Frame.SignupFrame.frameWidth / 6, Frame.SignupFrame.frameHeight / 7, Frame.SignupFrame.frameWidth / 2, Frame.SignupFrame.frameHeight * 4 / 7);
		this.setLayout(new GridLayout(labels.length, 2));
		this.setComponents();
		this.addComponents();
		this.setSize(this.getPreferredSize());
	}

	public void setComponents() {
		setLabels();
	}

	public void addComponents() {
		for (int i = 0; i < labels.length; i++) {
			this.add(labels[i]);
			this.add(fields[i]);
		}
	}

}
