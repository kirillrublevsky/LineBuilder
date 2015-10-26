package com.kirillrublevsky;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class LineDrawer extends Swing class JPanel. Its purpose is to draw lines using a list of points.
 * Contains one public method that shows Swing frame and overridden paintComponent method.
 */
public final class LineDrawer extends JPanel {

    /**
     * These constants set width and height of the Swing frame.
     */
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    /**
     * The list of points to draw.
     */
    private List<Point> points;

    /**
     * Private constructor of the class to use in the frame.
     */
    private LineDrawer(List<Point> points) {
        this.points = points;
    }

    /**
     * Method creates Swing frame and populates it with data.
     *
     * @param points list of points to draw
     */
    public static void drawLine(List<Point> points) {
        JFrame frame = new JFrame("Broken line without intersections");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new LineDrawer(points));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Overridden method of JComponent class. Is used when the instance of class is created in drawLine() method.
     * It sets the center of the coordinates grid to the center of the frame, in the loop draws lines and red
     * coloured points.
     *
     * @param g variable of Graphics class
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(WIDTH / 2, HEIGHT / 2);

        for (int i = 0; i < points.size() - 1; i++) {
            g2.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
        g2.setColor(Color.RED);
        for (Point point : points) {
            g2.fillOval(point.x, point.y, 4, 4);
        }
    }
}

