package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.MainMenu;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class ScoreTable extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ScoreTable() {
        setTitle("Historial de Puntuaciones");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel();
        model.addColumn("Fecha y Hora");
        model.addColumn("Nombre del Jugador");
        model.addColumn("Puntuación");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Cargar datos desde un archivo serializado (ajusta la ruta del archivo)
        List<ScoreEntry> scores = loadScoresFromFile("data/scores.dat");
        for (ScoreEntry entry : scores) {
            Object[] rowData = {entry.getDateTime(), entry.getPlayerName(), entry.getScore()};
            model.addRow(rowData);
        }
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	MainMenu mainMenu = new MainMenu();
                mainMenu.mostrar();
            }
        });
    }

    private List<ScoreEntry> loadScoresFromFile(String filename) {
        List<ScoreEntry> scores = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object objeto = ois.readObject();
            if (objeto instanceof List<?>) {
                scores = (List<ScoreEntry>) objeto;
            } else {
                System.out.println("Error: El objeto serializado no es una Lista");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void saveScoreToFile(String fileName, ScoreEntry scoreEntry) {
        List<ScoreEntry> scores = loadScoresFromFile(fileName);
        scores.add(scoreEntry);
        
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

