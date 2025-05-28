package entity.characters.npc;

import entity.AbstractEntity;
import handlers.Scrolling;
import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

/**
 * Butterfly
 * Flying loot
 */

public class Butterfly extends AbstractEntity implements Scrolling {

    /* FlyingEntity sprites */
    private ImageView node;
    private static final Image IMAGE_RIGHT = new Image(Butterfly.class.getResource("/game_assets/sprites/character/butterfly2.png").toExternalForm());
    private static final Image IMAGE_LEFT = new Image(Butterfly.class.getResource("/game_assets/sprites/character/butterfly2_left.png").toExternalForm());
//    private static final Image IMAGE_LEFT = new Image(FlyingEntity.class.getResource("/game_assets/sprites/character/butterfly3.png").toExternalForm());

    /* FlyingEntity properties */
    private int change = -1;
    private double scrollingSpeed;
    private double offset;
    private double YVelocity;
    private double XVelocity;
    private double deltaChange;

    public Butterfly(double x, double y, double offset) {
        this.offset = offset;
        YVelocity = offset;
        XVelocity = ThreadLocalRandom.current().nextDouble(0, 10);
        final Image image;
        if (XVelocity < 2) {
            XVelocity = -1 - XVelocity;
            image = IMAGE_RIGHT;
        } else {
            image = IMAGE_LEFT;
        }
        // CREATE ANIMATION SPRITE
//        node = createSprite(image, 84, 14, 10, 20, 70, 65, 1800);
        node = createSprite(image,10, 20, 70, 65);
        createAnimation(node, 84, 14, 10, 20, 70, 65, 1800);
        animation.play();
        node.setX(x);
        node.setY(y);
        node.setPreserveRatio(true);
        node.setFitHeight(45);
        isAlive = true;
    }

    /*  Setters */
    private void setNodeX(double newX) {
        node.setX(newX);
    }
    private void setNodeY(double newY) {
        node.setY(newY);
    }
    @Override
    public void setScrollingSpeed(double scrollingSpeed) {
        this.scrollingSpeed = -scrollingSpeed;
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
        return node.getBoundsInParent();
    }
    /* Getters end */

    @Override
    public void update(double c) {

        deltaChange = c;
        if (abs(YVelocity) > offset) change *= -1;
        YVelocity += change;                        // removed *c to fix random falling and rising
    }

    @Override
    public void render() {

        setNodeX(getNodeX() + scrollingSpeed + XVelocity* deltaChange);
        setNodeY(getNodeY() + YVelocity);
    }
}
