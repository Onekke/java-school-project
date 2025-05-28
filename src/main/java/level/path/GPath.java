package level.path;

import javafx.geometry.Bounds;

/**
 * Interface for access by GravityHandler
 * Any object to be used as path in game should
 * implement this interface
 */
public interface GPath {
    int getIndex(Bounds bounds);

    double getHeight(Bounds bounds, int index);

    PathSegment get(int index);
}
