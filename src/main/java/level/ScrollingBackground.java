package level;

import handlers.Scrolling;
import javafx.scene.image.ImageView;

/* ScrollingBackground
 *
 * Handles the background scrolling mechanics
 */

public class ScrollingBackground implements Scrolling {

    /* Scrolling properties */
    private double scrollingSpeed = 0;
    private double scrollingSpeedFactor;

    /* Background images and their layouts on the screen*/
    private double IMG_WIDTH;
    private ImageView [] layers;
    private double [] xLayouts;
    private int length;

    public ScrollingBackground (ImageView [] layers,
                                double scrollingSpeedFactor) {

        // STORE ARRAY OF BACKGROUND IMAGES
        this.layers = layers;
        IMG_WIDTH = this.layers[0].getFitWidth();

        // CREATE AN ARRAY OF IMAGE'S POSITIONS ON THE SCREEN
        this.length = layers.length;
        xLayouts = new double[length];

        this.scrollingSpeedFactor = scrollingSpeedFactor;
    }

    @Override
    public void setScrollingSpeed(double scrollingSpeed) {

        // ADJUST SCROLLING SPEED FOR EACH BACKGROUND LAYER
        this.scrollingSpeed = scrollingSpeed * scrollingSpeedFactor;
    }

    void update() {

        // SAVE NEW X LAYOUTS OF EACH IMAGE
        for (int i = 0; i < length; ++i) {
            xLayouts[i] = this.layers[i].getLayoutX() - scrollingSpeed;
        }

        // HANDLE THE OFF-SCREEN IMAGES
        for (int i = 0; i < length; i++) {
            if(xLayouts[i] <= -(i+1) * IMG_WIDTH) {
                if(i == 0)
                    xLayouts[i] = -xLayouts[length-1] - (length+2)*scrollingSpeed + (length - (i+2))*IMG_WIDTH;
                else
                    xLayouts[i] = xLayouts[i-1];
            }
        }
        render();
    }

    private void render() {

        // SET IMAGES TO NEW X POSITION
        for (int i = 0; i < length; ++i) {
            layers[i].setLayoutX(xLayouts[i]);
        }
    }
}