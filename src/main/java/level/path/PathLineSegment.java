package level.path;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

/* PathLineSegment
 *
 * Creates a path segment with give sizes and texture
 * New path segment represent the main ground
 * Always a straight line
 */

public class PathLineSegment extends PathSegment {
    private static final Image TEXTURE = new Image(PathSegment.class.getResource("/game_assets/sprites/background/ground_1.2.png").toExternalForm());

    public PathLineSegment(double fromX, double fromY, double length, double lineWidth) {

        //  CREATE NEW SEGMENT
        super(fromX, fromY, fromX + length, fromY);
        trapezoid = new Polygon(fromX, fromY + lineWidth, fromX, fromY, fromX + length, toY, fromX + length, fromY + lineWidth);

        // SET SEGMENTS TEXTURE
        trapezoid.setFill(new ImagePattern(TEXTURE, 0, 0, 1, 1, true));
//        trapezoid.setFill(Color.SEAGREEN);
        coefficient = 0;
    }
}
