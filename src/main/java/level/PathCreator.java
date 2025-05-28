package level;

import handlers.Scrolling;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

/**
 * @deprecated Use PathBuilder
 */
@Deprecated
public class PathCreator implements Scrolling {
    private Path path;
    private double topLimit;
    private double bottomLimit;
    private double toY;
    private double toX;
    private double scrollingSpeed;
    private boolean autoReplace = false;
    private double createSegmentDistance;
    private double originX;
    private int segmentCount = 0;

    public PathCreator(double x, double y, double topLimit, double bottomLimit) {
        originX = 0;
        this.path = new Path();
        this.topLimit = topLimit;
        this.bottomLimit = bottomLimit;
        initPath(x, y);
    }

    private void initPath(double x, double y) {
        path.setStroke(Color.YELLOWGREEN);
        path.setStrokeWidth(10);
        path.getElements().add(new MoveTo(x, y));
        segmentCount++;
        toX = x;
        toY = y;
    }

    public void addRandomSegment(double length) {
        addRandomSegment(length, true);
    }

    public void addRandomSegment(double length, boolean catchup) {
        //double fromY = toY;
        double fromX = toX;
        double origin = topLimit - toY;
        double bound = bottomLimit - toY;
        toX += length;
        toY += ThreadLocalRandom.current().nextDouble(origin/4, bound/4);
        path.getElements().add(new LineTo(toX, toY));
        segmentCount++;
        // add one more segment if the generation is too slow
        if (catchup && fromX + getX() < 1600) {
            addRandomSegment(length, true);
        }
    }

    public void addRandomSegment(double length, int n) {
        for (int i = 0; i < n; i++) {
            addRandomSegment(length, false);
        }
    }


    public void removeOldestSegment() {
        double segmentX = ((LineTo) path.getElements().get(1)).getX();
        double segmentY = ((LineTo) path.getElements().get(1)).getY();
        originX = segmentX;
        ((MoveTo) path.getElements().get(0)).setX(segmentX);
        ((MoveTo) path.getElements().get(0)).setY(segmentY);
        path.getElements().remove(1);
        segmentCount--;
    }

    public Path getPath() {
        return path;
    }

    public void setAutoReplace(boolean b, double segmentDistance) {
        this.autoReplace = b;
        createSegmentDistance = segmentDistance;
    }

    public double getHeight(double x) {
        double realX = (x - getX() - originX);
        int index = (int) realX / 200 + 1;
        double t = (realX % 200);
        if (index >= segmentCount) index = segmentCount-1;
        double fromY, toY;
        if (index <= 1) {
            fromY = ((MoveTo) path.getElements().get(0)).getY();
            index = 1;
        } else {
            fromY = ((LineTo) path.getElements().get(index-1)).getY();
        }
        toY = ((LineTo) path.getElements().get(index)).getY();
        return t * ((toY - fromY)/200.0f) + fromY;
    }

    private double getX() {
        return path.getLayoutX();
    }

    private void setX(double x) {
        path.setLayoutX(x);
    }

    public void update() {
        if (autoReplace) {
            createSegmentDistance -= scrollingSpeed;
            if (createSegmentDistance <= 0) {
                createSegmentDistance = 200;
                addRandomSegment(200);
                removeOldestSegment();
            }
        }
        render();
    }

    public void render() {
        setX(getX() - scrollingSpeed);
    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {
        this.scrollingSpeed = scrollingSpeed;
    }
}
