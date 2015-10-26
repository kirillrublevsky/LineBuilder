package com.kirillrublevsky;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Class PointSorter contains public static method that creates a list of sorted points to build broken line
 * without intersections. Also it contains several utility methods.
 */
public final class PointSorter {

    /**
     * Don't let anyone instantiate this class.
     */
    private PointSorter() {}

    /**
     * Returns a list of sorted points to build a broken line without intersections. Uses several utility methods.
     * Logic performed step by step:
     * 1. Create an empty list of points;
     * 2. Find a center of smallest outer rectangle (or circle) that contains all points of the list;
     * 3. The center will be utility point pointB that will help us to build first vector;
     * 4. Find pointA point - the most remote from the center point needed to build first vector;
     * 5. Set number of iterations to perform;
     * 6. In the loop we find next point, add it to the sorted list and remove previous point from the input list;
     * 7. Add the last point left in the input list to the sorted list.
     *
     * @param points a list of points to process
     * @return List<Point> a list of sorted points
     */
    public static List<Point> getSortedPoints(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<>();
        Point pointB = findCenter(points);
        Point pointA = findFirstPoint(pointB, points);

        Point nextPoint;
        int iterations = points.size() - 1;

        for (int i = 0; i < iterations; i++) {
            sortedPoints.add(pointA);
            points.remove(pointA);
            nextPoint = findNextPoint(points, pointA, pointB);
            pointB = pointA;
            pointA = nextPoint;
        }
        sortedPoints.add(points.get(0));
        return sortedPoints;
    }

    /**
     * Returns a point - center of the outer rectangle. Method iterates all points of the list and checks if x and y
     * coordinates of each point are min or max in order to find extreme coordinates to build virtual outer rectangle.
     * Then it finds the center of this rectangle.
     *
     * @param points a list of points to process
     * @return Point the center of the outer rectangle
     */
    private static Point findCenter(List<Point> points) {
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int currentX;
        int currentY;
        for (Point element : points) {
            currentX = element.x;
            currentY = element.y;
            if (currentX < minX) {
                minX = currentX;
            }
            if (currentX > maxX) {
                maxX = currentX;
            }
            if (currentY < minY) {
                minY = currentY;
            }
            if (currentY > maxY) {
                maxY = currentY;
            }
        }
        return new Point((minX + maxX) / 2, (minY + maxY) / 2);
    }

    /**
     * Returns the most remote point from the center of the outer rectangle (or circle). This point is the first one
     * in the list of sorted points. To do that we use the loop where find distance between each point of the input
     * list and the center of the outer circle. Return the point with the largest distance from the center.
     *
     * @param points a list of points to process
     * @param center a center of the outer circle
     * @return Point the most remote point
     */
    private static Point findFirstPoint(Point center, List<Point> points) {
        Point firstPoint = null;
        double maxDistance = 0;
        double currentDistance;

        for (Point point : points) {
            currentDistance = findDistance(point, center);
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                firstPoint = point;
            }
        }
        return firstPoint;
    }

    /**
     * Returns the distance between two points using its x and y coordinates.
     *
     * @param one first point
     * @param two second point
     * @return double distance between two points
     */
    private static double findDistance(Point one, Point two) {
        int differenceX = one.x - two.x;
        int differenceY = one.y - two.y;
        return sqrt(pow(differenceX, 2) + pow(differenceY, 2));
    }

    /**
     * Returns next point to add to the sorted list. Uses vectors and angles between them. In order to find this point
     * we need to find the smallest outer angle between two vectors AB and AC. We assume that point A is previous one
     * in the list of sorted points, point B stands before point A in this list and point C is next point after point A.
     * The goal is to find point C by checking all the points of the list. That is why points A and B are removed from
     * this list. Logic performed step by step:
     * <p/>
     * 1. Initialize smallest angle and smallest distance with their theoretical max value;
     * 2. In the loop check each point of the list. We assume that current point is point C and find outer angle
     * between vectors AB and AC as well as length of the AC vector;
     * 3. If current angle is smaller than the smallest vector found we assume that this is the smallest vector,
     * the current distance is the smallest distance and current point is the point we need;
     * 4. If current angle is equal to the smallest angle (possibility is very low), we check the distance - if current
     * distance is smaller than smallest distance, we assume that this is now a smallest distance and current point
     * is the one we need. It is on the same line as previous one but on the shorter distance.
     *
     * @param points a list of points
     * @param pointA point A
     * @param pointB point B
     * @return Point the point that stands after point A in the sorted list
     */
    private static Point findNextPoint(List<Point> points, Point pointA, Point pointB) {
        Point nextPoint = null;
        double smallestAngle = 360;
        double smallestDistance = Double.POSITIVE_INFINITY;
        double currentAngle;
        double currentDistance;

        for (Point point : points) {
            currentAngle = findOuterAngle(pointA, pointB, point);
            currentDistance = findDistance(pointA, point);
            if (currentAngle < smallestAngle) {
                smallestAngle = currentAngle;
                smallestDistance = currentDistance;
                nextPoint = point;
            } else if (currentAngle == smallestAngle) {
                if (currentDistance < smallestDistance) {
                    smallestDistance = currentDistance;
                    nextPoint = point;
                }
            }
        }
        return nextPoint;
    }

    /**
     * Returns an outer angle between vectors AB and AC. Uses static methods of Math class. First we find inner angle
     * between these vectors using x and y coordinates of points A, B and C. Than we deduct this inner angle from
     * 360 degrees to find outer angle. Also we need to mention that cos value in our calculations can exceed 1 or -1
     * because of Java language reasons, so we need to check that. Is is because cos cannot be larger than 1 by
     * modulus. If it happens it should be set to 1 or -1.
     *
     * @param pointA point A
     * @param pointB point B
     * @param pointC point C
     * @return double an outer angle between AB and AC vectors
     */
    private static double findOuterAngle(Point pointA, Point pointB, Point pointC) {
        int xAB = pointB.x - pointA.x;
        int yAB = pointB.y - pointA.y;
        int xAC = pointC.x - pointA.x;
        int yAC = pointC.y - pointA.y;

        double scalarMultiplication = xAB * xAC + yAB * yAC;
        double modulusAB = sqrt(pow(xAB, 2) + pow(yAB, 2));
        double modulusAC = sqrt(pow(xAC, 2) + pow(yAC, 2));
        double cos = scalarMultiplication / (modulusAB * modulusAC);
        if (cos < -1) {
            cos = -1;
        } else if (cos > 1) {
            cos = 1;
        }
        return 360 - toDegrees(acos(cos));
    }
}