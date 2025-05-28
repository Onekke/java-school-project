package level.path;

import handlers.ScrollingHandler;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathBuilderTest {
    private PathBuilder pathBuilder;

    @BeforeEach
    void setUp() {
        ScrollingHandler scrollingHandler = new ScrollingHandler();
        Pane root = new Pane();
        pathBuilder = new PathBuilder(scrollingHandler, root);
    }

    @Test
    void get() {
        pathBuilder.removeAllSegments();
        pathBuilder.addSegment(new PathSegment());
        pathBuilder.addSegment(new PathSegment());
        assertEquals(2, pathBuilder.getSegmentCount());
    }

    @Test
    void addSegment() {
        pathBuilder.addSegment(new PathSegment());
        assertEquals(1, pathBuilder.getSegmentCount());
    }

    @Test
    void removeSegment() {
        pathBuilder.addSegment(new PathSegment());
        pathBuilder.addSegment(new PathSegment());
        pathBuilder.removeSegment(1);
        assertEquals(1, pathBuilder.getSegmentCount());
    }

    @Test
    void removeSegmentEmpty() {
        pathBuilder.removeSegment(0);
        assertEquals(0, pathBuilder.getSegmentCount());
    }

    @Test
    void removeAllSegments() {
        pathBuilder.addSegment(new PathSegment());
        pathBuilder.addSegment(new PathSegment());
        assertEquals(2, pathBuilder.getSegmentCount());
        pathBuilder.removeAllSegments();
        assertEquals(0, pathBuilder.getSegmentCount());
    }
}