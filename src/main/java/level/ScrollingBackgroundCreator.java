package level;

import handlers.ScrollingHandler;

import java.util.HashSet;
import java.util.Set;

/* ScrollingBackgroundCreator
 *
 * Creates a set of all background layers
 */

public class ScrollingBackgroundCreator {

    private ScrollingHandler scrollingHandler;
    private Set<ScrollingBackground> scrollingBackgrounds;

    public ScrollingBackgroundCreator() {
        this.scrollingBackgrounds = new HashSet<>();
    }

    public void addLayer(ScrollingBackground sb) {
        scrollingBackgrounds.add(sb);
    }

    public void update() {
        scrollingBackgrounds.forEach(ScrollingBackground::update);
    }

    public void setScrollingHandler(ScrollingHandler scrollingHandler) {

        if (this.scrollingHandler != null) {
            scrollingBackgrounds.forEach(sb -> this.scrollingHandler.remove(sb));
        }
        this.scrollingHandler = scrollingHandler;
        scrollingBackgrounds.forEach(sb -> this.scrollingHandler.add(sb));
    }
}