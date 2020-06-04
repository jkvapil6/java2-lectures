package cz.upol.zp4jv.swing3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;


/**
 * Ukazkove pouziti nove komponenty
 */
public class ExampleForm extends JFrame {


	private JPanel switchPanel = new JPanel();


	private static final long serialVersionUID = 676355654668895961L;

	public static void main(String[] args) {
		JFrame form = new ExampleForm();



		form.setVisible(true);
	}
	
	public ExampleForm() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		
		// pouziti nove komponenty
		//getContentPane().add(new MyComponent());

		//getContentPane().add(switchPanel, BorderLayout.CENTER);
		getContentPane().add(new JSwitch("off", "on", "no", "yes"));
		
		// jednotlivym kombinacim klaves jsou prirazeny nazvy akci
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
		
		// nazvum akci jsou prirazeny konkretni akce
		getRootPane().getActionMap().put("cancel", new AbstractAction() {
			private static final long serialVersionUID = 1049529005738643618L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}});

		
		setPreferredSize(new Dimension(400, 200));
		pack();
	}
}
