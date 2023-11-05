package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class GameField extends JPanel {
	
	public static final int PANEL_WIDTH = 400;
	public static final int PANEL_HEIGHT = 400;
	private boolean gameOverNotified = false;
	private SnakeFrame snakeFrame;
	private ObstacleManager obstacleManager;
	private List<Ellipse2D.Double> snakeParts;
	private Food apple;

	public GameField(SnakeFrame snakeFrame) {
		 this.snakeFrame = snakeFrame;
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.DARK_GRAY);
		obstacleManager = new ObstacleManager(this); // Inicializa ObstacleManager
		Thread obstacleManagerThread = new Thread(obstacleManager); // Inicializa el hilo del ObstacleManager
		obstacleManagerThread.start();
	}
	
	public int getWidth() {
		return PANEL_WIDTH; 
	}
	
	public int getHeight() {
		return PANEL_HEIGHT; 
	}
	
	public void initDefaults() {
		apple = new Food(100, 100);
		snakeParts = Collections
				.synchronizedList(new ArrayList<Ellipse2D.Double>());
		snakeParts.add(new Ellipse2D.Double(260, 260, 20, 20));
		snakeParts.add(new Ellipse2D.Double(260, 280, 20, 20));
		snakeParts.add(new Ellipse2D.Double(260, 300, 20, 20));
		snakeParts.add(new Ellipse2D.Double(260, 320, 20, 20));	
		
		
	    obstacleManager = new ObstacleManager(this);
	    obstacleManager.startObstacleManagerThread();
	}
	
	public void setSnakeParts(List<Ellipse2D.Double> snakeParts) {
		this.snakeParts = snakeParts;
	}
	public List<Ellipse2D.Double> getSnakeParts() {
	    return snakeParts;
	}
	
	public void setApple(Food apple) {
		this.apple = apple;
	}
	
	public Food getApple() {
		return apple;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Verificar colisión con obstáculos
	    boolean collision = false;
	    for (Ellipse2D.Double part : snakeParts) {
	        for (Obstacle obstacle : obstacleManager.getObstacles()) {
	            if (part.intersects(obstacle.getShape().getBounds2D())) {
	                collision = true;
	                break;
	            }
	        }
	    }

	    if (collision && !gameOverNotified) {
	    	gameOverNotified = true;
	        snakeFrame.gameOver();
	         // Establece la bandera a true para evitar mostrar el aviso nuevamente
	        return;
	    }
	    
		for (Obstacle obstacle : obstacleManager.getObstacles()) {
	        g2.setColor(Color.ORANGE); // Cambia el color de los obstáculos a naranja
	        Ellipse2D.Double obstacleShape = obstacle.getShape();
	        g2.fill(obstacleShape);
	    }

	    if (!snakeParts.isEmpty()) { // Verificar si la lista no está vacía
	        // Luego, puedes acceder a la cabeza de la serpiente
	        Ellipse2D.Double head = snakeParts.get(0);
	        // Realizar la representación gráfica de la cabeza de la serpiente
	        g.setColor(Color.GREEN);
	        g.fillOval((int) head.getX(), (int) head.getY(), (int) head.getWidth(), (int) head.getHeight());
	    }
		
		
		// Draw the apple
		g2.setPaint(Color.GREEN);
		g2.fillOval((int) apple.getShape().getMinX() + 5, (int) apple.getShape()
				.getMinY() + 5, 15, 15);
		
		// Draw the snake parts
		g2.setPaint(new Color(34, 136, 215)); // BLUE
		for (Ellipse2D e : snakeParts) {
			g2.fill(e);
		}
		
		// Draw the head of the snake
		g2.setPaint(new Color(215, 34, 38));  // RED
		g2.fill(snakeParts.get(0));
		
	}
}
