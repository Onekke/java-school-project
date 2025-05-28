package entity;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Base class for entities
 * Implements some Entity methods to avoid code repetition
 */
public abstract class AbstractEntity implements Entity {
    protected boolean isAlive;
    protected KillMethod deathBy;
    protected Animation animation;

    protected ImageView createSprite(Image image, int offsetX, int offsetY, int width, int height) {
        ImageView sprite = new ImageView(image);
        sprite.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        return sprite;
    }

    protected void createAnimation(ImageView sprite, int count, int columns,int offsetX, int offsetY, int width, int height, int milliseconds) {
        animation = new SpriteAnimation(sprite, Duration.millis(milliseconds), count, columns, offsetX, offsetY, width, height);
        animation.setCycleCount(Animation.INDEFINITE);
    }

    public void setIsAlive(boolean status) {
        isAlive = status;
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void kill(Entity e) {
        e.die(KillMethod.UNSPECIFIED);
    }

    @Override
    public void die(KillMethod killMethod) {
        deathBy = killMethod;
        isAlive = false;
    }

    @Override
    public KillMethod getCauseOfDeath() {
        return deathBy;
    }
}

