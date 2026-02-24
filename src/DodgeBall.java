import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> balls;
	private ArrayList<GRect> enemies;
	private GLabel text;
	private Timer movement;
	private RandomGenerator rgen;
	private int numTimes;
	
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int MAX_ENEMIES = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 300;
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		balls = new ArrayList<GOval>();
		enemies = new ArrayList<GRect>();
		
		text = new GLabel("Enemies : 0", 10, WINDOW_HEIGHT - 10);
		add(text);
		
		numTimes = 0;
		
		movement = new Timer(MS, this);
		movement.start();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		moveAllBallsOnce();
		moveAllEnemiesOnce();
		checkForCollisions();
		
		if (numTimes % 40 == 0) {
			addAnEnemy();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		for(GOval b:balls) {
			if(b.getX() < SIZE * 2.5) {
				return;
			}
		}
		addABall(e.getY());     
	}
	
	private void addABall(double y) {
		GOval ball = makeBall(SIZE/2, y);
		add(ball);
		balls.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	private void addAnEnemy() {
		GRect enemy = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT-SIZE));
		enemies.add(enemy);
		text.setLabel("Enemies: " + enemies.size());
		add(enemy);
	}
	
	public GRect makeEnemy(double y) {
		GRect temp = new GRect(WINDOW_WIDTH-SIZE, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.GREEN);
		temp.setFilled(true);
		return temp;
	}

	private void moveAllBallsOnce() {
		for(GOval ball:balls) {
			ball.move(SPEED, 0);
		}
	}
	
	private void moveAllEnemiesOnce() {
		for (GRect enemy:enemies) {
			enemy.move(0, rgen.nextInt(-2, 3));
		}
	}
	
	private void checkForCollisions() {
		for (GOval ball : balls) {
			double checkX = ball.getX() + SIZE;
			double checkY = ball.getY() + SIZE / 2;
			GObject obj = getElementAt(checkX, checkY);
			
			if (obj instanceof GRect) {
				GRect enemy = (GRect) obj;
				remove(enemy);
				enemies.remove(enemy);
				text.setLabel("Enemies: " + enemies.size());
				break;
			}
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new DodgeBall().start();
	}
}