package entity.characters.npc;

import entity.AbstractEntity;
import handlers.Gravity;
import handlers.Scrolling;
import javafx.animation.Animation;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Arrays;

/**
 * Chicken
 * Stationary loot
 * Holds methods and sprites for a chicken/loot character
 */

public class Chicken extends AbstractEntity implements Gravity, Scrolling {

    /* Chicken properties */
    private double scrollingSpeed;
    private double fallVelocity;
    private double deltaChange;     // ?


    /* Chicken sprite */
    private ImageView node;
    private static Image[] sprite =
            {new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken1.png").toExternalForm()),
            new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken2.png").toExternalForm()),
            new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken3.png").toExternalForm()),
            new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken4.png").toExternalForm()),
            new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken5.png").toExternalForm()),
            new Image(Chicken.class.getResource("/game_assets/sprites/character/chicken6.png").toExternalForm())};
    private static int spriteCount[] = {24, 47, 40, 67, 86, 32};
    private static int spriteOffsetY[] = {30, 5, 30, 30, 30, 30};
    private static int spriteDuration[] = {1000, 1350, 1200, 1600, 2500, 1600};

    public Chicken(double x, double y, int spriteNumber) {
        setNodeSpriteAnimation(spriteNumber);
        node.setX(x);
        node.setY(y);
        node.setPreserveRatio(true);
        node.setFitHeight(100);
        deltaChange = 1;
        isAlive = true;
    }

    private void setNodeSpriteAnimation(int spriteNumber) {
        Image image = sprite[spriteNumber];
        node = createSprite(image, 45, spriteOffsetY[spriteNumber], 148, 110);
        createAnimation(node, spriteCount[spriteNumber], 6, 45, spriteOffsetY[spriteNumber], 148, 110, spriteDuration[spriteNumber]);
        animation.play();
    }

    /* Setters */
    private void setNodeX(double newX) {
        node.setX(newX);
    }
    private void setNodeY(double newY) {
        node.setY(newY);
    }
    @Override
    public void setScrollingSpeed(double forwardSpeed) {
        this.scrollingSpeed = -forwardSpeed;
    }
    @Override
    public void setFallVelocity(double fallVelocity) {
        this.fallVelocity = fallVelocity;
    }
    /* Setters end */

    /* Getters */
    private double getNodeX() {
        return node.getX();
    }
    private double getNodeY() {
        return node.getY();
    }
    @Override
    public Node getNode() {
        return node;
    }
    @Override
    public Bounds getBounds() {
        Bounds actual = node.getBoundsInParent();
        return new BoundingBox(
                actual.getMinX() + 5,
                actual.getMinY() + 50,
                actual.getWidth() - 10,
                actual.getHeight() - 95);
    }
    @Override
    public double getFallVelocity() {
        return fallVelocity;
    }
    /* Getters end */

    @Override
    public void update(double c) {
        deltaChange = c;
    }

    @Override
    public void render() {
        setNodeX(getNodeX() + this.scrollingSpeed);
        setNodeY(getNodeY() + getFallVelocity());
    }
}
