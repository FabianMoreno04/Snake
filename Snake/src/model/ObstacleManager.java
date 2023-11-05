package model;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class ObstacleManager implements Runnable {
    private List<Obstacle> obstacles;
    private GameField gameField;
    private Thread obstacleManagerThread;
    private static final double OBSTACLE_WIDTH = 15; 
    private static final double OBSTACLE_HEIGHT = 15; 

    public ObstacleManager(GameField gameField) {
        this.gameField = gameField;
        obstacles = new ArrayList<>();
    }
    public void startObstacleManagerThread() {
        obstacleManagerThread = new Thread(this); // Inicializa el hilo
        obstacleManagerThread.start();
    }

    @Override
    public void run() {
        while (true) {
            Ellipse2D.Double obstacleShape = generateObstacleShape();
            Obstacle obstacle = new Obstacle(obstacleShape);
           
            synchronized (obstacles) {
                obstacles.add(obstacle);
            }
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
       
            
            synchronized (obstacles) {
                obstacles.remove(obstacle); 
            }
        }
    }
    
    private Ellipse2D.Double generateObstacleShape() {
    	double x = Math.random() * (gameField.getWidth() - OBSTACLE_WIDTH);
    	x = Math.max(x, 0); 
    	double y = Math.random() * (gameField.getHeight() - OBSTACLE_HEIGHT);
    	y = Math.max(y, 0);
        double width = OBSTACLE_WIDTH;
        double height = OBSTACLE_HEIGHT;
        
        Ellipse2D.Double obstacleShape = new Ellipse2D.Double(x, y, width, height);
        return obstacleShape;
    }
    
   
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
   
}










