import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int MAX_STEPS = 10;
	private GLabel myLabel;
	private Timer t;
	private int numTimes;

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		myLabel = new GLabel("# of times called?", 0, 100);
		add(myLabel);
		numTimes = 0;
		t = new Timer(1000, this);
		t.setInitialDelay(3000);
		t.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		
		myLabel.move(5, 0);
		myLabel.setLabel("# of times called?" + numTimes);
		
		if (numTimes == MAX_STEPS) {
			t.stop();
		}
	}
	
	public static void main(String[] args) {
		new MyFirstTimer().start();
	}
}