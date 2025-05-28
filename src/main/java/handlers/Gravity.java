package handlers;

import javafx.geometry.Bounds;

/* Gravity
 *
 * Stores fall velocity
 * Calls for the bounds of a game object
 */

public interface Gravity {

    void setFallVelocity(double fallVelocity);
    double getFallVelocity();
    Bounds getBounds();
}
