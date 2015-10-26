package com.kirillrublevsky;

import java.awt.*;
import java.util.List;

/**
 * Class Utils contains utility static method that prints a list of points.
 */
public final class Utils {

    /**
     * Don't let anyone instantiate this class.
     */
    private Utils() {}

    /**
     * Prints a list of points with x and y coordinates
     *
     * @param points List of points to print
     */
    public static void printPoints(List<Point> points) {
        for (Point point : points) {
            System.out.print("(" + (int) point.getX() + ", " + (int) point.getY() + "), ");
        }
        System.out.println("\n");
    }
}
