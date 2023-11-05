package model;

import java.io.Serializable;
import java.util.Date;

public class ScoreEntry implements Serializable {
    private Date dateTime;
    private String playerName;
    private int score;

    public ScoreEntry(String playerName, int score) {
        this.dateTime = new Date();  
        this.playerName = playerName;
        this.score = score;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ScoreEntry{" +
                "dateTime=" + dateTime +
                ", playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }

}

