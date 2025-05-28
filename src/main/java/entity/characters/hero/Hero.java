package entity.characters.hero;

import entity.AbstractEntity;
import entity.Entity;
import entity.KillMethod;
import handlers.Gravity;
import handlers.GravityHandler;
import javafx.animation.Animation;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hero
 * Holds all the necessary information on the game main character
 * Class methods include physics update for jump/fall, new position rendering
 * as well as methods for checking whether the hero is dead or have killed someone
 */

public class Hero extends AbstractEntity implements Gravity {

    /* Hero properties */
    private static final int STRENGTH = 0;
    private static final int SIZE = 120;
    private static final int HEIGHT_CRAWL = 10;
    private static final double JUMP_SPEED = -12;
    private static final double JUMP_BOOST_FACTOR = 0.7;
    private double fallingSpeed = 0;
    private boolean jump, jumping, crawl;
    private static double jumpSpeed = JUMP_SPEED;       // required for setting setJump height when crawling
    private double deltaChange;                         // for rendering the falling
    private long lastJump;                            // ?
    private double nodeHeight;

    /* Hero sprites */
    private ImageView node;
//    private static final Image IMAGE = new Image(Hero.class.getResource("/game_assets/sprites/character/cat3.png").toExternalForm());
    private static final Image IMAGE = new Image(Hero.class.getResource("/game_assets/sprites/character/cat_2.2.png").toExternalForm());
//    private static final Image JUMP = new Image(Hero.class.getResource("/game_assets/sprites/character/cat1_left_jump.png").toExternalForm());
//    private static final Image IDLE = new Image(Hero.class.getResource("/game_assets/sprites/character/cat1_left_idle.png").toExternalForm());
//    private static final Image IMAGE = new Image(Hero.class.getResource("/game_assets/sprites/character/cat2.2_left.png").toExternalForm());

    private Logger logger = Logger.getLogger(Hero.class.getName());

    public Hero(double x, double y) {

//        node = createSprite(IMAGE, 8, 2, 15, 55, 400, 220, 1000);
//        node = createSprite(IMAGE, 8, 4, 40, 30, 450, 300, 1000);
//        node = createSprite(IMAGE, 12, 1, 10, 0, 400, 200, 600);
        node = createSprite(IMAGE, 40, 30, 450, 300);
        createAnimation(node, 8, 4, 40, 30, 450, 300, 700);
//        animation.play();
//        createNode();
        setNodeX(x);
        setNodeY(y);
        node.setPreserveRatio(true);
        node.setFitHeight(SIZE);
        isAlive = true;
        nodeHeight = SIZE;
    }

//    private void createNode() {
//
//        node = IMAGE;
//        node.setPreserveRatio(true);
//        node.setFitHeight(SIZE);
//    }

    /* Setters */
    public void setNodeX(double newX) {
        node.setX(newX);
    }
    private void setNodeY(double newY) {
        node.setY(newY);
    }
    public void setJump(boolean b) {
        this.jump = b;
    }
    private void setNodeHeight(double height) {
//        node.setFitHeight(height);
        nodeHeight = height;
    }
    @Override
    public void setFallVelocity(double fallVelocity) {
        this.fallingSpeed = fallVelocity;
        if (jumping && fallVelocity == 0) {
            lastJump = System.nanoTime();                 // ?
            jumping = false;
            animation.play();
        }
    }
        /* Setters end */

    /* Getters */
    public double getNodeX() {
        return node.getX();
    }
    private double getNodeY() {
        return node.getY();
    }
    @Override
    public int getStrength() {
        return STRENGTH;
    }
    @Override
    public Node getNode() {
        return node;
    }
    @Override
    public double getFallVelocity() {
        return fallingSpeed;
    }
    @Override
    public Bounds getBounds() {
        Bounds actual = node.getBoundsInParent();
        return new BoundingBox(
                actual.getMinX() + 5,
                actual.getMinY() + 50,
                actual.getWidth() - 50,
                nodeHeight - 60
        );
    }

    public Animation getAnimation() {
        return animation;
    }

    /* Getters end */

    public void crawl(boolean b) {
        if (b && node.getBoundsInParent().getMaxY() < 560) {
            setNodeHeight(HEIGHT_CRAWL);
            animation.playFrom(animation.getCycleDuration());
            jumpSpeed = 0;
        } else {
            setNodeHeight(SIZE);
            jumpSpeed = JUMP_SPEED;

        }
    }

    @Override
    public void kill(Entity e) {
        logger.log(Level.INFO, "Defeated: " + e.getClass().getName());
        e.die(KillMethod.KOSHISHTA);
    }

    @Override
    public void die(KillMethod killMethod) {
        logger.log(Level.INFO, "Killed by: " + killMethod);
        isAlive = false;
    }

    @Override
    public void update(double c) {
        this.deltaChange = c;
        /* JUMPING */
        if (jump) {
            animation.playFrom(animation.getCycleDuration().divide(1.8));
            if (!jumping && System.nanoTime() - lastJump > 100000000) {
                fallingSpeed = jumpSpeed;
                jumping = true;
            } else if (0 > fallingSpeed) {
                fallingSpeed -= (GravityHandler.GRAVITY_ACCELERATION * c * JUMP_BOOST_FACTOR);
            }
        }
    }

    @Override
    public void render() {
        setNodeY(getNodeY() + fallingSpeed * deltaChange);
        setNodeX(getNodeX());
    }
}
