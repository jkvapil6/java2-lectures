package cz.upol.zp4jv.swing4;

import java.awt.*;
import java.util.LinkedList;
import java.util.function.Predicate;

import javax.swing.*;

public class SwingSort extends JFrame {

    private static final long serialVersionUID = -5894245064728900823L;

    private JButton 	btnStart;
    private JButton 	btnStop;
    private JButton 	btnShuffle;

    private JPanel      panControl;
    private JPanel      panCanvas;

    private SortThread thr;

    private LinkedList<Point> points;

    private int canvasWidth = 300;		// y. souradnice = index bodu
    private int canvasHeight = 300;		// x. souradnice = hodnota bodu

    private SwingCanvas canvas;

    private int sortedIndex = 0;


    public SwingSort() {
        super();

        /////////// inicializace grafickeho rozhrani ///////////

        this.setTitle("Sort Visualizer");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);

        btnShuffle = new JButton("shuffle");
        btnStart = new JButton("start");
        btnStop = new JButton("stop");
        btnStop.setEnabled(false);

        panControl = new JPanel();
        panControl.setLayout(new FlowLayout());

        panControl.add(btnShuffle);
        panControl.add(btnStart);
        panControl.add(btnStop);

        this.add(panControl, BorderLayout.NORTH);

        points = shufflePoints(canvasWidth, canvasHeight);

        panCanvas = new JPanel();
        panCanvas.setLayout(new FlowLayout());

        canvas = new SwingCanvas(points, canvasWidth, canvasHeight);
        panCanvas.add(canvas);
        this.add(panCanvas, BorderLayout.CENTER);


        ///////// konec inicializace grafickeho rozhrani ///////////
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        //////////////// inicializace udalosti /////////////////////

        btnShuffle.addActionListener(e -> {

            points = shufflePoints(canvasWidth, canvasHeight);

            canvas.updatePoints(points);
            repaint();

        });

        btnStart.addActionListener(e -> {
            adjustGUI();
            repaint();

            thr = new SortThread(points, sortedIndex);
            thr.start();
        });

        btnStop.addActionListener(e -> {
            thr.cancel();
        });

        /////////// konec inicializace udalosti //////////////
    }


    /**
     * Vygeneruje nahodne body v rozsahu zadane velikosti a sirky platna
     */
    public LinkedList<Point> shufflePoints(int width, int height) {
        points = new LinkedList<Point>();

        for (int i = 0; i < height; i++) {
            points.add(new Point((int)(Math.random() * width), i));
        }
        return points;
    }

    /**
     * Upravi graficke rozhrani tak, aby signalizovalo, ze probiha vypocet
     */
    public void adjustGUI() {
        btnShuffle.setEnabled(false);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    /**
     * Zobrazi vysledek vypoctu
     */
    public void displayResult(LinkedList<Point> points) {

        btnShuffle.setEnabled(true);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        setSortedIndex(0);
        canvas.updatePoints(points);
        repaint();
    }

    /**
     * Zobrazi, ze vypocet byl prerusen
     */
    public void displayAbort(int sortedIndex) {
        setSortedIndex(sortedIndex);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        canvas.updatePoints(points);
        repaint();
    }

    public void redraw() {
        repaint();
    }

    public void setSortedIndex(int sortedIndex) {
        this.sortedIndex = sortedIndex;
    }

    /**
     * Vlakno realizujici vypocet
     */
    private class SortThread extends Thread {

        private boolean cancelled = false;

        private int sortedIndex;

        private LinkedList<Point> points;

        public SortThread(LinkedList<Point> points, int sortedIndex) {
            super();
            this.points = points;
            this.sortedIndex = sortedIndex;
        }

        @Override
        public void run() {

            try {
                sortPoints(points);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (isCancelled()) displayAbort(sortedIndex);
                    else displayResult(points);
                }
            });
        }

        private void sortPoints(LinkedList<Point> points) throws InterruptedException {

            while (!cancelled && sortedIndex < points.size()) {
                sortNext(points, sortedIndex++);
                redraw();
                Thread.sleep(30);
            }
        }

        private synchronized void sortNext (LinkedList<Point> points, int i) {
            int min = i;

            for (int j = i; j < points.size(); j++) {
                if (points.get(j).x < points.get(min).x) {
                    min = j;
                }
            }

            Point p1 = new Point(points.get(min).x, points.get(i).y);
            Point p2 = new Point(points.get(i).x, points.get(min).y);

            points.set(i, p1);
            points.set(min, p2);
        }


        public synchronized boolean isCancelled() {
            return cancelled;
        }

        public synchronized void cancel() {
            this.cancelled = true;
        }
    }

    public static void main(String [] args) {
        JFrame frame = new SwingSort();
        frame.setVisible(true);
    }
}