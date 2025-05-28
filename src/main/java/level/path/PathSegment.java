package level.path;

import handlers.Scrolling;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

/* PathSegment
 *
 * Creates a path segment with given sizes and texture
 * New path segment is used for joining main path segments of different heights
 */

public class PathSegment implements Scrolling {

    Polygon trapezoid;
    private double fromX, fromY, toX;
    double toY, coefficient;
    private double scrollingSpeed;
    private static final Image TEXTURE = new Image(PathSegment.class.getResource("/game_assets/sprites/background/ground_1.2.png").toExternalForm());

    public PathSegment() {

        this(0,0,0,0,0);
    }

    PathSegment(double fromX, double fromY, double toX, double toY) {

        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.coefficient = (toY - fromY) / (toX - fromX);
    }

    /* Getters */

    Node getNode() {
        return trapezoid;
    }

    Bounds getBounds() {
        return trapezoid.getBoundsInParent();
    }

    public double getFromX() {
        return fromX;
    }

    public double getFromY() {
        return fromY;
    }

    public double getToX() {
        return toX;
    }

    public double getToY() {
        return toY;
    }

    public double getCoefficient() {
        return coefficient;
    }

    double getHeight(Bounds bounds) {
        double highestX = (fromY > toY) ? bounds.getMaxX() : bounds.getMinX();
        return (highestX - getBounds().getMinX()) * coefficient + fromY;
    }

    public double getHeight(double x) {
        return (x - getBounds().getMinX()) * coefficient + fromY;
    }
    /* End of getters */

    public PathSegment(double fromX, double fromY, double toX, double toY, double lineWidth) {

        this(fromX, fromY, toX, toY);
        trapezoid = new Polygon(fromX, fromY + lineWidth, fromX, fromY, toX, toY, toX, fromY + lineWidth);
        trapezoid.setFill(new ImagePattern(TEXTURE, 0, 0, 1, 1, true));
//        trapezoid.setFill(Color.IVORY);
    }

    public void update() {
        trapezoid.setLayoutX(trapezoid.getLayoutX() - scrollingSpeed);
    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {
        this.scrollingSpeed = scrollingSpeed;
    }
}
