package handlers;

import javafx.geometry.Bounds;
import level.path.GPath;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects object affected by gravity
 * Applies current acceleration if they are not
 * currently colliding with ground
 */
public class GravityHandler {

    public static final double GRAVITY_ACCELERATION = 1.5111;
    private static final double TERMINAL_VELOCITY = 60;
    private Set<Gravity> gravitySet;
    private GPath path;
    private int size;

    // SET A GRAVITY HANDLER
    GravityHandler() {
        this.gravitySet = new HashSet<>();
    }

    public void setPath(GPath path) {
        this.path = path;
    }

    public void add(Gravity e) {
        gravitySet.add(e);
        size++;
    }

    void remove(Gravity e) {
        gravitySet.remove(e);
        size--;
    }

    public int getSize() {
        return size;
    }

    // HANDLE FALLING AND COLLISION PHYSICS
    public void update(double c) {

        gravitySet.forEach(g -> {
            Bounds bounds = g.getBounds();
            double gv = g.getFallVelocity();
            double maxY = g.getBounds().getMaxY();
            int index = path.getIndex(bounds);
            double peak = 700;
            if (index != -1) peak = path.getHeight(bounds, index);
            if (maxY < peak) {
                if (maxY + gv + GRAVITY_ACCELERATION*c > peak) {
                    g.setFallVelocity(peak - maxY + 1);
                } else if (gv < TERMINAL_VELOCITY) {
                    g.setFallVelocity(gv + GRAVITY_ACCELERATION*c);
                } else {
                    g.setFallVelocity(TERMINAL_VELOCITY);
                }
            } else {
                g.setFallVelocity(0);
                if (maxY > peak + 4) {
                    double coefficient = -4;
                    if (index != -1) coefficient = coefficient / (1 + path.get(index).getCoefficient());
                    g.setFallVelocity(coefficient * maxY/peak);
                }
            }
        });
    };
}
