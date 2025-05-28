package entity.characters.npc;

import entity.AbstractEntity;
import entity.Entity;
import entity.KillMethod;
import handlers.Gravity;
import handlers.Scrolling;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Spike
 * The most common and annoying obstacle
 */
public class Spike extends AbstractEntity implements Gravity, Scrolling {
    private Pane node;
    private static final double FOUNDATION = 40;
    private static final double GIRTH = 30;
    private double fallVelocity;
    private double scrollingSpeed;

    public Spike(double x, double y, double spikeWidth, double spikeHeight) {
        createNode(x, y, spikeWidth, spikeHeight);
        isAlive = true;
    }

    private void createNode(double x, double y, double spikeWidth, double spikeHeight) {
        double wedge = spikeHeight - FOUNDATION;
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(0.0, spikeHeight, 0.0, wedge);
        for (double i = 0; i < spikeWidth; i += GIRTH) {
            polygon.getPoints().addAll(i + GIRTH / 2, 0.0, i + GIRTH, wedge);
        }
        polygon.getPoints().addAll(spikeWidth, spikeHeight);
        polygon.setFill(Color.CRIMSON);
        node = new Pane(polygon);
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    @Override
    public int getStrength() {
        return 50;
    }


    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public Bounds getBounds() {
        Bounds actual = node.getBoundsInParent();
        return new BoundingBox(
                actual.getMinX() + GIRTH / 2,
                actual.getMinY() + 10,
                actual.getWidth() - GIRTH,
                actual.getHeight() - FOUNDATION + 10
        );
    }

    @Override
    public void kill(Entity e) {
        e.die(KillMethod.SPIKE);
    }

    @Override
    public void update(double c) {
    }

    @Override
    public void render() {
        node.setLayoutX(node.getLayoutX() + scrollingSpeed);
        node.setLayoutY(node.getLayoutY() + fallVelocity);
    }

    @Override
    public double getFallVelocity() {
        return fallVelocity;
    }

    @Override
    public void setFallVelocity(double fallVelocity) {
        this.fallVelocity = fallVelocity;
    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {
        this.scrollingSpeed = -scrollingSpeed;
    }
}
