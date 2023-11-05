package model;

import java.awt.ScrollPane;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.io.Serializable;

public class Snake extends Thread implements Serializable {

    private Direction currentDirection;
    private Direction nextDirection;
    private Direction direction;
    private GameField gameField ;
    private int growthCounter = 0;
    private ScorePanel scorePanel ;
    public static final int XSIZE = 20; // 
    public static final int YSIZE = 20; 
    private List<Obstacle> obstacles;
    private int level;
    private Ellipse2D.Double ass; 
    private Ellipse2D.Double temp;
    private boolean over = false;
    private List<Ellipse2D.Double> snakeParts = new ArrayList<>();
    
    public Snake(GameField gameField, ScorePanel scorePanel) {
        this.gameField = gameField;
        this.scorePanel = scorePanel;
        initDefaults();
        // Agrega la cabeza de la serpiente
        snakeParts.add(new Ellipse2D.Double(260, 260, XSIZE, YSIZE));
    }
    public List<Ellipse2D.Double> getSnakeParts() {
        return snakeParts;
    }
  
    private void initDefaults() {
        snakeParts = Collections.synchronizedList(new ArrayList<Ellipse2D.Double>());
        snakeParts.add(new Ellipse2D.Double(260, 260, 20, 20));
        snakeParts.add(new Ellipse2D.Double(260, 280, 20, 20));
        snakeParts.add(new Ellipse2D.Double(260, 300, 20, 20));
        snakeParts.add(new Ellipse2D.Double(260, 320, 20, 20));
    }
    @Override
    public void run() {
        while (!isGameOver()) {
            updateDirection();
            move();
            check();
            try {
                Thread.sleep(100 - level * 10); // Aumenta la velocidad según el nivel (ajusta este valor)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDirection() {
        if (currentDirection != Direction.UP && nextDirection == Direction.DOWN) {
            currentDirection = Direction.DOWN;
        } else if (currentDirection != Direction.DOWN && nextDirection == Direction.UP) {
            currentDirection = Direction.UP;
        } else if (currentDirection != Direction.RIGHT && nextDirection == Direction.LEFT) {
            currentDirection = Direction.LEFT;
        } else if (currentDirection != Direction.LEFT && nextDirection == Direction.RIGHT) {
            currentDirection = Direction.RIGHT;
        }
    }
    public boolean isGameOver() {
        return over;
    }

    public void grow() {
        growthCounter++; // Incrementa el contador de crecimiento
    }
    
    public List<Ellipse2D.Double> getParts() {
        return snakeParts;
    }
    
    public void move() {
        switch (direction) {
            case UP:
                moveBody();
                moveHead(0, -20); // Mover hacia arriba
                break;
            case DOWN:
                moveBody();
                moveHead(0, 20); // Mover hacia abajo
                break;
            case LEFT:
                moveBody();
                moveHead(-20, 0); // Mover hacia la izquierda
                break;
            case RIGHT:
                moveBody();
                moveHead(20, 0); // Mover hacia la derecha
                break;
            default:
                new Exception("Unexpected Direction value!").printStackTrace();
                break;
        }
    }
    
    private void moveHead(int deltaX, int deltaY) {
        Ellipse2D.Double head = snakeParts.get(0);
        double maxX = gameField.getBounds().getMaxX();
        double maxY = gameField.getBounds().getMaxY();

        double newMinX = (head.getMinX() + deltaX) % maxX;
        double newMinY = (head.getMinY() + deltaY) % maxY;

        if (newMinX < 0) {
            newMinX += maxX;
        }
        if (newMinY < 0) {
            newMinY += maxY;
        }

        head.setFrame(newMinX, newMinY, XSIZE, YSIZE);
    }

    private void moveBody() {
        for (int i = snakeParts.size() - 1; i > 0; i--) {
            if (i == snakeParts.size() - 1) {
                ass = (Ellipse2D.Double) snakeParts.get(i).clone();
            }
            temp = (Ellipse2D.Double) snakeParts.get(i - 1).clone();
            snakeParts.set(i, temp);
        }
    }

    public void check() {
        Ellipse2D.Double head = snakeParts.get(0);
        Food food = gameField.getApple();

        // Check if the snake has eaten itself
        for (int i = 1; i < snakeParts.size(); i++) {
            if (head.getMinX() == snakeParts.get(i).getMinX()
                    && head.getMinY() == snakeParts.get(i).getMinY()) {
                over = true;
                return;
            }
        }

        // Check if the snake has eaten an apple
        if (head.getMinX() == food.getShape().getMinX()
                && head.getMinY() == food.getShape().getMinY()) {
            scorePanel.addPoints(10);
            food.next(this);
            snakeParts.add(ass);
        }
    }
    
    public boolean checkCollision() {
        Ellipse2D.Double head = snakeParts.get(0);
        
        // Verificar colisiones con obstáculos
        for (Obstacle obstacle : obstacles) {
            if (head.intersects(obstacle.getShape().getBounds2D())) {
                over = true;
                return true;
            }
        }

        // Verificar colisiones consigo misma
        for (int i = 1; i < snakeParts.size(); i++) {
            if (head.intersects(snakeParts.get(i).getBounds2D())) {
                over = true;
                return true;
            }
        }
        
        return false;
    }
    
    public void resetSnakePosition() {
        if (!snakeParts.isEmpty()) { 
            Ellipse2D.Double head = snakeParts.get(0);
            double maxX = gameField.getBounds().getMaxX();
            double maxY = gameField.getBounds().getMaxY();

            if (head.getMinX() < 0) {
                head.setFrame(maxX - XSIZE, head.getMinY(), XSIZE, YSIZE);
            } else if (head.getMaxX() > maxX) {
                head.setFrame(0, head.getMinY(), XSIZE, YSIZE);
            } else if (head.getMinY() < 0) {
                head.setFrame(head.getMinX(), maxY - YSIZE, XSIZE, YSIZE);
            } else if (head.getMaxY() > maxY) {
                head.setFrame(head.getMinX(), 0, XSIZE, YSIZE);
            }
        }
    }

    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

 
}


