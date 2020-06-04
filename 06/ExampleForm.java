package cz.upol.zp4jv.swing4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;


/**
 * Ukazkove pouziti nove komponenty
 */
public class ExampleForm extends JFrame {

	private static final long serialVersionUID = 676355654668895961L;

	public static void main(String[] args) {
		JFrame form = new ExampleForm();
		form.setVisible(true);
	}
	
	public ExampleForm() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		LinkedList<Point> points = new LinkedList<Point>();

		int canvasWidth = 300;		// y. souradnice = index bodu
		int canvasHeight = 300;		// x. souradnice = hodnota bodu

		int nums[] = new int[canvasHeight];

		for (int i = 0; i < canvasHeight; i++) {
			nums[i] = (int)(Math.random() * canvasWidth);
			points.add(new Point(nums[i], i));
		}

		// pouziti nove komponenty
		getContentPane().add(new SwingCanvas(points, canvasWidth, canvasHeight));

		// jednotlivym kombinacim klaves jsou prirazeny nazvy akci
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
		
		// nazvum akci jsou prirazeny konkretni akce
		getRootPane().getActionMap().put("cancel", new AbstractAction() {
			private static final long serialVersionUID = 1049529005738643618L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}});

		
		setPreferredSize(new Dimension(400, 400));
		pack();
	}
}
