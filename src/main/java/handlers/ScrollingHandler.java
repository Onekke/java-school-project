package handlers;

import java.util.HashSet;
import java.util.Set;

/* ScrollingHandler
 *
 * Stores the scrolling speed and sends it to other classes that are using it
 * Adds, removes scrolling entities
 */

public class ScrollingHandler implements Scrolling{

    private Set<Scrolling> scrollingSet;
    private double scrollingSpeed;

    public ScrollingHandler() {
        scrollingSet = new HashSet<>();
    }

    public void setScrollingSpeed(double scrollingSpeed) {
        this.scrollingSpeed = scrollingSpeed;
    }

    public double getScrollingSpeed() {
        return scrollingSpeed;
    }

    public void add(Scrolling e) {
        scrollingSet.add(e);
    }

    public void remove(Scrolling e) {
        scrollingSet.remove(e);
    }

    public void update(double c) {
        scrollingSet.forEach(scrolling -> scrolling.setScrollingSpeed(scrollingSpeed*c));
    }
}
