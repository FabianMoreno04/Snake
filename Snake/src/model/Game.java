package model;

public class Game implements Runnable {

	public static final int DELAY = 400;
	

	private SnakeFrame frame;
	private GameField gameField;
	private Snake snake;
	private Food apple;
	  private boolean isRunning = true;
	
	public Game(GameField gameField, Snake snake, SnakeFrame frame) {
		apple = new Food(100, 100);
		this.frame = frame;
		this.snake = snake;
		this.gameField = gameField;

		this.gameField.setSnakeParts(snake.getParts());
		this.gameField.setApple(apple);
	}
	
	public void stopGame() {
		isRunning = false;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				snake.move();
				snake.check();
				if (snake.isGameOver()) {
					Thread.currentThread().interrupt();
				}
				if (!Thread.currentThread().isInterrupted()) {
					gameField.repaint();
				}
				Thread.sleep(DELAY);
			}
		} catch (InterruptedException ex) {
			frame.gameOver();
		}
	}
}
