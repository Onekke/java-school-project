package level.path;

import handlers.ScrollingHandler;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/* PathBuilder
 *
 * Adds path segments to the list
 * Puts segments from the list in the game scene
 * Updates segments positions
 * Removes segments off-screen
 */
public class PathBuilder implements GPath {
    private Logger logger = Logger.getLogger(PathBuilder.class.getName());

    private Pane root;
    private ScrollingHandler scrollingHandler;
    private List<PathSegment> segments;
    private int segmentCount;

    public PathBuilder(ScrollingHandler scrollingHandler, Pane pane) {
        this.scrollingHandler = scrollingHandler;
        this.root = pane;
        segments = new ArrayList<>();
        segmentCount = 0;
    }

    public void addSegment(PathSegment pathSegment) {
        final boolean add = segments.add(pathSegment);
        if (!add) {
            logger.warning("Adding path segment failed");
            return;
        }
        scrollingHandler.add(pathSegment);
        root.getChildren().add(pathSegment.getNode());
        segmentCount++;
    }

    public void removeSegment(int index) {
        if (index > segmentCount - 1) {
            logger.warning("Attempted remove of nonexistent segment");
            return;
        }
        PathSegment pathSegment = segments.get(index);
        segments.remove(index);
        scrollingHandler.remove(pathSegment);
        root.getChildren().remove(pathSegment.getNode());
        segmentCount--;
    }

    public void removeAllSegments() {
        int listSize = segmentCount;
        for (int i = 0; i < listSize; i++) {
            removeSegment(0);
        }
    }

    public PathSegment get(int index) {
        return segments.get(index);
    }

    public int getIndex(Bounds bounds) {
        int index = (int) segments.stream().takeWhile(pathSegment -> !pathSegment.getBounds().intersects(bounds)).count();
        return  (index >= segmentCount) ? -1 : index;
    }

    public double getHeight(Bounds bounds, int index) {
        PathSegment currentSegment = segments.get(index);
        return currentSegment.getHeight(bounds);
    }

    public double getMaxX() {
        return segments.get(segmentCount - 1).getBounds().getMaxX();
    }

    public double getMinX() {
        return segments.get(0).getBounds().getMinX();
    }

    public int getSegmentCount() {
        return segmentCount;
    }

    public void update() {
        segments.forEach(PathSegment::update);
    }
}
