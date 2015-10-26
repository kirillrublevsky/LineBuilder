package com.kirillrublevsky;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class PointGenerator contains public static method that generates random list of points and one private utility
 * method.
 */
public final class PointGenerator {

    /**
     * Don't let anyone instantiate this class.
     */
    private PointGenerator() {}

    /**
     * The static instance of Random used in static methods of the class.
     */
    private static Random random = new Random();

    /**
     * Returns a randomly generated list of points. Uses utility method. The logic step by step is:
     * 1. Create empty list of points;
     * 2. Randomly generate number of points from 20 to 39;
     * 3. In the loop randomly generate x and y coordinates and create new point using them, then add it to list.
     *
     * @return List<Point> randomly generated list of points
     */
    public static List<Point> generatePoints() {
        List<Point> points = new ArrayList<>();
        int numberOfPoints = random.nextInt(20) + 20;
        for (int i = 0; i < numberOfPoints; i++) {
            points.add(new Point(generatePointCoordinate(), generatePointCoordinate()));
        }
        return points;
    }

    /**
     * Private utility method. Returns a randomly generated coordinate (x or y) from 0 to 199.
     * With probability of 0.5 makes this int negative.
     *
     * @return int randomly generated coordinate
     */
    private static int generatePointCoordinate() {
        int coordinate = random.nextInt(200);
        if (random.nextDouble() >= 0.5) {
            coordinate *= -1;
        }
        return coordinate;
    }
}