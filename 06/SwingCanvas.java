package cz.upol.zp4jv.swing4;

import javax.swing.*;
import java.awt.*;

import java.util.LinkedList;
import java.util.Optional;

public class SwingCanvas extends JComponent {

    private static final long serialVersionUID = -5810239256305954192L;

    /** seznam vykreslovanych bodu */
    private LinkedList<Point> points = new LinkedList<Point>();

    /** velikost vykreslovanych bodu */
    private int pointSize = 5;

    private int width;
    private int height;

    /** bod nachazejici se pod kurzorem mysi */
    private Optional<Point> currentPoint = Optional.empty();

    public SwingCanvas(LinkedList<Point> points, int width, int height) {
        this.points = points;
        this.width = width;
        this.height = height;
    }

    public void updatePoints(LinkedList<Point> points) {
        this.points = points;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void paintComponent(Graphics g) {
        // explicitnim pretypovanim ziskame sirsi funkcionalitu
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // vykresli kriz, aby bylo videt hranice komponenty
        g2.setColor(Color.GRAY);
        g2.drawLine(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        g2.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, 0);

        // vykresli body do komponenty
        g2.setColor(Color.BLUE);
        for (Point p : points)
            g2.fillOval(p.x - pointSize / 2, p.y - pointSize / 2, pointSize, pointSize);

        // vykresluje bod, kam ukazuje mys
        currentPoint.ifPresent(p -> {
            g2.setColor(Color.RED);
            g2.fillOval(p.x - pointSize / 2, p.y - pointSize / 2, pointSize, pointSize);
        });
    }

    public int getPointSize() {
        return pointSize;
    }

    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }
}