package controller;

import javax.swing.*;

import model.GameField;
import model.NivelConfig;
import model.ScorePanel;
import model.ScoreTable;
import model.SnakeFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainMenu {
    public JFrame frame;
    private String playerName;
    
    public MainMenu() {
    	SnakeFrame snakeFrame = new SnakeFrame();
        frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("imagenes/menu.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Establecer el color de fuente en blanco
        Color textColor = Color.WHITE;

        JLabel nombreLabel = new JLabel("Ingrese su nombre: ");
        nombreLabel.setForeground(textColor); // Establece el color de fuente en blanco
        JTextField nombreTextField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(nombreLabel, gbc);
        gbc.gridy = 1;
        menuPanel.add(nombreTextField, gbc);
        
        JLabel nivelLabel = new JLabel("Selecciona el nivel: ");
        nivelLabel.setForeground(textColor);
        String[] niveles = {"Bajo", "Medio", "Alto"};
        JComboBox<String> nivelComboBox = new JComboBox<>(niveles);
        nivelComboBox.setSelectedIndex(1); // Por defecto, seleccionar "Medio"
        
        gbc.gridy = 2;
        menuPanel.add(nivelLabel, gbc);
        gbc.gridy = 3;
        menuPanel.add(nivelComboBox, gbc);


        JButton iniciarJuegoButton = new JButton("Iniciar Juego");
        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 playerName = nombreTextField.getText(); // Captura el nombre del jugador
                 snakeFrame.setPlayerName(playerName);
                 
                 // Obtén el nivel seleccionado
                 String selectedLevel = (String) nivelComboBox.getSelectedItem();
                 NivelConfig nivelConfig = cargarNivelDesdeArchivo(selectedLevel);
                 
                 // Configura la velocidad, tamaño inicial, tiempo de comida y tiempo de obstáculo
                 if (nivelConfig != null) {
                     snakeFrame.setVelocidad(nivelConfig.getVelocidad());
                     snakeFrame.setTamanoInicial(nivelConfig.getTamanoInicial());
                     snakeFrame.setTiempoComida(nivelConfig.getTiempoComida());
                     snakeFrame.setTiempoObstaculo(nivelConfig.getTiempoObstaculo());
                 }
                 
                snakeFrame.setVisible(true);
            }
        });
        gbc.gridy = 4;
        menuPanel.add(iniciarJuegoButton, gbc);

        JButton historialPuntuacionesButton = new JButton("Historial de Puntuaciones");
        historialPuntuacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ScoreTable scoreTable = new ScoreTable();
                scoreTable.setVisible(true);
            }
        });
        gbc.gridy = 5;
        menuPanel.add(historialPuntuacionesButton, gbc);

        // Información del Desarrollador
        JLabel infoDesarrolladorLabel = new JLabel("INFORMACION DEL DESARROLLADOR:");
        Color nuevoColor = Color.ORANGE;
        infoDesarrolladorLabel.setForeground(nuevoColor);
        gbc.gridy = 6;
        menuPanel.add(infoDesarrolladorLabel, gbc);

        JLabel nombreCompletoLabel = new JLabel("Nombre completo: Nelson Fabian Moreno");
        nombreCompletoLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 7;
        menuPanel.add(nombreCompletoLabel, gbc);

        JLabel idEstudianteLabel = new JLabel("Identificación del Estudiante: 202023289");
        idEstudianteLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 8;
        menuPanel.add(idEstudianteLabel, gbc);

        JLabel facultadLabel = new JLabel("Nombre de la facultad: Ingenieria de Sistemas");
        facultadLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 9;
        menuPanel.add(facultadLabel, gbc);

        JLabel escuelaLabel = new JLabel("Nombre de la escuela: Sistemas");
        escuelaLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 10;
        menuPanel.add(escuelaLabel, gbc);

        JLabel anoLabel = new JLabel("Año: 2023");
        anoLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 11;
        menuPanel.add(anoLabel, gbc);

        JLabel cursoLabel = new JLabel("Curso: Ingenieria de Sistemas");
        cursoLabel.setForeground(textColor); // Establece el color de fuente en blanco
        gbc.gridy = 12;
        menuPanel.add(cursoLabel, gbc);

        // Logotipo de la universidad (reemplaza con tu imagen)
        ImageIcon logo = new ImageIcon("imagenes/logoUPTC.jpg");
        JLabel logoLabel = new JLabel(logo);
        gbc.gridy = 13;
        menuPanel.add(logoLabel, gbc);

        frame.add(menuPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void mostrar() {
        frame.setVisible(true);
    }
    
    private NivelConfig cargarNivelDesdeArchivo(String nivelSeleccionado) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/dificultad.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    String nivel = parts[0].trim();
                    if (nivel.equals(nivelSeleccionado)) {
                        int velocidad = Integer.parseInt(parts[1].trim());
                        int tamanoInicial = Integer.parseInt(parts[2].trim());
                        int tiempoComida = Integer.parseInt(parts[3].trim());
                        int tiempoObstaculo = Integer.parseInt(parts[4].trim());
                        return new NivelConfig(velocidad, tamanoInicial, tiempoComida, tiempoObstaculo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si no se encuentra la configuración, devuelve null o establece valores por defecto
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu();
            }
        });
    }
}
