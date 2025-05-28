package entity;

import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Methods required to be processed by EntityHandler
 */
public interface Entity {
    default int getStrength() {
        return 0;
    }

    Node getNode();

    Bounds getBounds();

    boolean isAlive();

    void kill(Entity e);

    default void die() {
        die(KillMethod.UNSPECIFIED);
    }

    void die(KillMethod killMethod);

    KillMethod getCauseOfDeath();

    void update(double c);

    void render();

    Animation getAnimation();
}
