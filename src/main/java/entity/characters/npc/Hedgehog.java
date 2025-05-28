package entity.characters.npc;

import entity.AbstractEntity;
import handlers.Gravity;
import handlers.Scrolling;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Not implemented
 * Walking obstacle
 */
public class Hedgehog extends AbstractEntity implements Gravity, Scrolling {
    private static final int STRENGTH = 8;
    private double XVelocity;
    private Pane node;

    public Hedgehog(double x, double y, double XVelocity) {
        this.XVelocity = XVelocity;
        createNode(x, y);
    }

    private void createNode(double x, double y) {

    }


    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public Bounds getBounds() {
        return null;
    }

    @Override
    public void update(double c) {

    }

    @Override
    public void render() {

    }

    @Override
    public double getFallVelocity() {
        return 0;
    }

    @Override
    public void setFallVelocity(double fallVelocity) {

    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {

    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }
}
