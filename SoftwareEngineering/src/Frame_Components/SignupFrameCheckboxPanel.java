package Frame_Components;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SignupFrameCheckboxPanel extends JPanel{
	
	public JCheckBox one = new JCheckBox();
	public JCheckBox two = new JCheckBox();
	
	public SignupFrameCheckboxPanel(String one, String two) {
		this.one.setText(one);
		this.two.setText(two);
		this.setThis(null);
	}
	
	public void setThis(Component prevComp) {
		this.setLayout(new GridLayout(1,2));
		this.addComponents();
		this.setSize(this.getPreferredSize());
	}

	public void setComponents() {}

	public void addComponents() {
		this.add(one);
		this.add(two);
	}

}
