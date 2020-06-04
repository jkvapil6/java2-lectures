package cz.upol.zp4jv.swing3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


public class MyComponent extends JComponent {
	
	private static final long serialVersionUID = -5810239256305954192L;
	
	/** seznam vykreslovanych bodu */
	private LinkedList<Point> points = new LinkedList<Point>();
	
	/** velikost vykreslovanych bodu */
	private int pointSize = 20;
	
	/** bod nachazejici se pod kurzorem mysi */
	private Optional<Point> currentPoint = Optional.empty();
	
	public MyComponent() {
		// metody MouseListeneru umoznuji reagovat na zakladni akce s mysi
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				currentPoint = Optional.empty();
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				currentPoint = Optional.of(new Point(e.getX(), e.getY()));
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				points.add(new Point(e.getX(), e.getY()));
				repaint();
			}
		});
		
		// metody MouseMotionListeneru umoznuji reagovat na pohyb mysi
		// nad komponentou; MouseAdapter umoznuje implementovat jen
		// skutecne pouzivane akce; ve stejnem duchu by sel MouseAdapter
		// pouzit i v pripade MouseListeneru
		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				currentPoint = Optional.of(new Point(e.getX(), e.getY()));
				repaint();
			}
		});
		
		// navazani stisku klavesy delete na akci, ktera odstrani posledni bod
		// ze seznamu bodu
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deletePoint");
		getActionMap().put("deletePoint", new AbstractAction() {

			private static final long serialVersionUID = -2283628469873898932L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (points.size() > 0) points.removeLast();
				repaint();
			}
		});
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
		
		// vypise pocet vykreslenych bodu
		g2.setColor(Color.BLACK);
		g2.drawString("Bodu: " + points.size(), 0, this.getHeight());
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}
}
