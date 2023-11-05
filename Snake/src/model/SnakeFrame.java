package model;


import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import controller.MainMenu;
import view.GBC;

public class SnakeFrame extends JFrame {
    private ScorePanel scorePanel;
    private ScoreTable scoreTable;
    private GameField gameField;
    private Thread thread;
    private Snake snake;
    private String playerName;
    private Direction direction = Direction.UP;
    private boolean started = false;
    private int velocidad; // Agrega un campo para la velocidad
    private int tamanoInicial; // Agrega un campo para el tamaño inicial
    private int tiempoComida; // Agrega un campo para el tiempo de aparición de comida
    private int tiempoObstaculo; // Agrega un campo para el tiempo de aparición de obstáculos
    
    public SnakeFrame() {
        scoreTable = new ScoreTable();
        initComponents();
        initGame();
        initFrame();
        initKeyListener(); 
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    // Agrega los métodos para configurar la velocidad, tamaño inicial, tiempo de comida y tiempo de obstáculo
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setTamanoInicial(int tamanoInicial) {
        this.tamanoInicial = tamanoInicial;
    }

    public void setTiempoComida(int tiempoComida) {
        this.tiempoComida = tiempoComida;
    }

    public void setTiempoObstaculo(int tiempoObstaculo) {
        this.tiempoObstaculo = tiempoObstaculo;
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        
        scorePanel = new ScorePanel();
        add(scorePanel, new GBC(0, 8, 8, 1));
        
        gameField = new GameField(this);
        add(gameField, new GBC(0, 0, 8, 8));
    }
    
    private void initGame() {
        snake = new Snake(gameField, scorePanel);
        
        Runnable r = new Game(gameField, snake, this);
        thread = new Thread(r);
    }
   
    private void initFrame() {
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void newGame() {
        started = true;
        thread.start();
    }
    
    public void gameOver() {        
        int returnValue = JOptionPane.showConfirmDialog(this, 
                "Desea reiniciar el juego? ", "GAME OVER!", JOptionPane
                .OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        switch (returnValue) {
            case JOptionPane.OK_OPTION:
                direction = Direction.UP;
                started = false;
                snake = new Snake(gameField, scorePanel);
                scorePanel.clear();
                gameField.initDefaults();
                scorePanel.repaint();
                gameField.repaint();
                Runnable r = new Game(gameField, snake, this);
                thread = null;
                thread = new Thread(r);
                break;
                
            case JOptionPane.CANCEL_OPTION:
                int playerScore = Integer.parseInt(scorePanel.getScore());
                ScoreEntry scoreEntry = new ScoreEntry(playerName, playerScore);
                Snake snake = new Snake(gameField, scorePanel);
                scoreTable.saveScoreToFile("data/scores.dat", scoreEntry);
                setVisible(false); // Oculta la ventana actual
                MainMenu mainMenu = new MainMenu();
                if (mainMenu != null) {
                      mainMenu.mostrar(); // Muestra la ventana MainMenu existente
                }
                break;
                
            default:
                JOptionPane.showMessageDialog(getParent(), 
                        "Something went wrong :( /n Please relaunch app");
                break;
        }
    }
   
    private void initKeyListener() {
        addKeyListener(new KeyboardHandler());
    }

    private class KeyboardHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direction == Direction.DOWN) return;
                if (!started) newGame();
                if (snake != null) {
                    snake.changeDirection(Direction.UP);
                    direction = Direction.UP;
                }
            }
            
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direction == Direction.UP) return;
                if (!started) newGame();
                if (snake != null) {
                    snake.changeDirection(Direction.DOWN);
                    direction = Direction.DOWN;
                }
            }
            
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direction == Direction.RIGHT) return;
                if (!started) newGame();
                if (snake != null) {
                    snake.changeDirection(Direction.LEFT);
                    direction = Direction.LEFT;
                }
            }
            
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direction == Direction.LEFT) return;
                if (!started) newGame();
                if (snake != null) {
                    snake.changeDirection(Direction.RIGHT);
                    direction = Direction.RIGHT;
                }
            }
        }
    }
}

