package cz.upol.zp4jv.swing3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Optional;


public class JSwitch extends JComponent {

	private static final long serialVersionUID = -5810239256305954192L;

	/** bod nachazejici se pod kurzorem mysi po kliknuti */
	private Optional<Point> currentPoint = Optional.empty();
	private Optional<Point> previousPoint = Optional.empty();

	/** nazvy stavu (on/off..) */
	private String stateName00;
	private String stateName01;
	private String stateName10;
	private String stateName11;

	DoubleSwitch state;

	public JSwitch(String state00, String state01, String state10, String state11) {

		this.stateName00 = state00;
		this.stateName01 = state01;
		this.stateName10 = state10;
		this.stateName11 = state11;

		state = new DoubleSwitch();

		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentPoint = Optional.empty();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPoint = Optional.of(new Point(e.getX(), e.getY()));
				repaint();
			}
		});


		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		getActionMap().put("Enter", new AbstractAction() {

			private static final long serialVersionUID = -2283628469873898932L;

			@Override
			public void actionPerformed(ActionEvent e) {
				state.switchFirst();
				state.switchSecond();
				repaint();
			}
		});

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left-arrow");
		getActionMap().put("Left-arrow", new AbstractAction() {

			private static final long serialVersionUID = -2283628469873898932L;

			@Override
			public void actionPerformed(ActionEvent e) {
				state.turnFirstOff();
				state.turnSecondOff();
				repaint();
			}
		});

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right-arrow");
		getActionMap().put("Right-arrow", new AbstractAction() {

			private static final long serialVersionUID = -2283628469873898932L;

			@Override
			public void actionPerformed(ActionEvent e) {
				state.turnFirstOn();
				state.turnSecondOn();
				repaint();
			}
		});
	}


	// vola se pokazde, kdyz se prekresluje okno
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		redrawByState(g2);

		drawTopBackground(g2);
		drawBottomBackground(g2);

		currentPoint.ifPresent(p -> {

			// osetreni vicenasobneho hlaseni zmen
			if (currentPoint.equals(previousPoint)) {
				return;
			} else previousPoint = currentPoint;

			if (currentPoint.get().getY() < (this.getHeight() / 2) && currentPoint.get().getX() < (this.getWidth() / 2)) state.turnFirstOff();
			if (currentPoint.get().getY() < (this.getHeight() / 2) && currentPoint.get().getX() > (this.getWidth() / 2)) state.turnFirstOn();
			if (currentPoint.get().getY() > (this.getHeight() / 2) && currentPoint.get().getX() < (this.getWidth() / 2)) state.turnSecondOff();
			if (currentPoint.get().getY() > (this.getHeight() / 2) &&  currentPoint.get().getX() > (this.getWidth() / 2)) state.turnSecondOn();
		});

		redrawByState(g2);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, getHeight() / 3));
		g2.drawString(stateName00, this.getWidth() / 6 , this.getHeight() / 3);
		g2.drawString(stateName01, this.getWidth() / 2, this.getHeight() / 3);
		g2.drawString(stateName10, this.getWidth() / 6, this.getHeight() - this.getHeight() / 8);
		g2.drawString(stateName11, this.getWidth() / 2, this.getHeight() - this.getHeight() / 8);
	}

	private void redrawByState(Graphics2D g2) {

		switch (state.getState()) {
			case 0:
				drawTopLeftSwitch(g2);
				drawBottomLeftSwitch(g2);
				break;
			case 1:
				drawTopLeftSwitch(g2);
				drawBottomRightSwitch(g2);
				break;
			case 2:
				drawTopRightSwitch(g2);
				drawBottomLeftSwitch(g2);
				break;
			case 3:
				drawTopRightSwitch(g2);
				drawBottomRightSwitch(g2);
				break;
		}
	}

	private void drawBottomRightSwitch(Graphics2D g2) {
		drawBottomBackground(g2);
		g2.setColor(Color.decode("#f2f2f2"));
		g2.fillRoundRect(getWidth() / 2, (getHeight() / 2) +  (getHeight() / 11), getWidth() / 3, (getHeight() - (getHeight()/4)) / 2, 20, 20);
	}

	private void drawBottomLeftSwitch(Graphics2D g2) {
		drawBottomBackground(g2);
		g2.setColor(Color.decode("#f2f2f2"));
		g2.fillRoundRect(getWidth() / 10, (getHeight() / 2) +  (getHeight() / 11) , getWidth() / 3, (getHeight() - (getHeight()/4)) / 2, 20, 20);
	}

	private void drawTopRightSwitch(Graphics2D g2) {
		drawTopBackground(g2);
		g2.setColor(Color.decode("#f2f2f2"));
		g2.fillRoundRect(getWidth() / 2, getHeight()/18, getWidth() / 3, (getHeight() - (getHeight()/4)) / 2, 20, 20);
	}

	private void drawTopLeftSwitch(Graphics2D g2) {
		drawTopBackground(g2);
		g2.setColor(Color.decode("#f2f2f2"));
		g2.fillRoundRect(getWidth() / 10, getHeight()/18, getWidth() / 3, (getHeight() - (getHeight()/4)) / 2, 20, 20);
	}

	private void drawBottomBackground(Graphics2D g2) {
		g2.setColor(Color.decode("#afc6e9"));
		g2.fillRoundRect(0, (getHeight() / 2) + (getHeight()/25), getWidth(), (getHeight() - (getHeight()/25)) / 2, 20, 20);
	}

	private void drawTopBackground(Graphics2D g2) {
		g2.setColor(Color.decode("#afc6e9"));
		g2.fillRoundRect(0, 0, getWidth(), (getHeight() - (getHeight()/25)) / 2, 20, 20);
	}
}
