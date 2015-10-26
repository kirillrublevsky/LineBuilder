package com.kirillrublevsky;

import java.awt.*;
import java.util.List;

import static com.kirillrublevsky.LineDrawer.drawLine;
import static com.kirillrublevsky.PointGenerator.generatePoints;
import static com.kirillrublevsky.PointSorter.getSortedPoints;
import static com.kirillrublevsky.Utils.printPoints;

/**
 * Class Application contains main() method that runs application.
 */
public final class Application {

    /**
     * Don't let anyone instantiate this class.
     */
    private Application() {}

    /**
     * Runs application. The logic step by step is:
     * 1. Generate a list of points to draw;
     * 2. Print a number of generated points;
     * 3. Print all generated points using method of Utils class;
     * 4. Create a list of sorted points;
     * 5. Print all sorted points using method of Utils class;
     * 6. Draw a broken line using drawLine() method;
     * 7. Catch exceptions.
     */
    public static void main(String[] args) {
        try {
            List<Point> generatedPoints = generatePoints();
            System.out.println("Number of generated points: " + generatedPoints.size() + "\n");
            System.out.print("Generated points: ");
            printPoints(generatedPoints);

            List<Point> sortedPoints = getSortedPoints(generatedPoints);
            System.out.print("Sorted points: ");
            printPoints(sortedPoints);
            drawLine(sortedPoints);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}