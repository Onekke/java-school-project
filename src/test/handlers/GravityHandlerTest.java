package handlers;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import level.path.GPath;
import level.path.PathBuilder;
import level.path.PathSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

class GravityHandlerTest {
    private GravityHandler gravityHandler;

    @BeforeEach
    void setUp() {
        gravityHandler = new GravityHandler();
    }

    @Test
    void addAndRemove() {
        Gravity obj = new GravityImpl();
        gravityHandler.add(obj);
        assertEquals(1, gravityHandler.getSize());
        gravityHandler.remove(obj);
        assertEquals(0, gravityHandler.getSize());
    }

    @Test
    void update() {
        GPath path = new GPathImpl();
        gravityHandler.setPath(path);
        double updateSpeed = 2;
        Gravity lowFPS = new GravityImpl();
        gravityHandler.add(lowFPS);
        for (int i = 0; i < 300; i++) {
            gravityHandler.update(updateSpeed);
        }
        gravityHandler.remove(lowFPS);

        updateSpeed = 0.5;
        Gravity highFPS = new GravityImpl();
        gravityHandler.add(highFPS);
        for (int i = 0; i < 1200; i++) {
            gravityHandler.update(updateSpeed);
        }
        gravityHandler.remove(highFPS);

        System.out.println(
                "TEST update(): 30fps 10 seconds: " + lowFPS.getFallVelocity() +
                "  120fps 10 seconds: " + highFPS.getFallVelocity() +
                " difference: " + (abs(lowFPS.getFallVelocity() - highFPS.getFallVelocity()))
        );
        assertEquals(lowFPS.getFallVelocity(), highFPS.getFallVelocity(), 0.03125);
    }

    private class GravityImpl implements Gravity {
        private double fallVelocity;
        private Bounds staticBounds;

        public GravityImpl() {
            fallVelocity = -100000000;
            staticBounds = new BoundingBox(0, 0, 100, 100);
        }

        @Override
        public void setFallVelocity(double fallVelocity) {
            this.fallVelocity = fallVelocity;
        }

        @Override
        public double getFallVelocity() {
            return fallVelocity;
        }

        @Override
        public Bounds getBounds() {
            return staticBounds;
        }
    }

    private class GPathImpl implements GPath {
        @Override
        public int getIndex(Bounds bounds) {
            return 0;
        }

        @Override
        public double getHeight(Bounds bounds, int index) {
            return 60000;   // Number doesn't matter as long as it does not intersect staticBounds of GravityImpl and is larger
        }

        @Override
        public PathSegment get(int index) {
            return null;    // Only fallSpeed is tested here
        }
    }
}