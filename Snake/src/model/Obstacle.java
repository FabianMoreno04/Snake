package model;

import java.awt.geom.Ellipse2D;

public class Obstacle {
    private Ellipse2D.Double shape; // Forma del obstáculo
    private long creationTime;

    public Obstacle(Ellipse2D.Double shape) {
        this.shape = shape;
    }
    
    public Ellipse2D.Double getShape() {
        return shape;
    }
    
    public long getCreationTime() {
        return creationTime;
    }
}

