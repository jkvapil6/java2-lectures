package cz.upol.zp4jv.swing4;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SwingFib extends JFrame {

	private static final long serialVersionUID = -5894245064728900823L;
	
	private JButton 	btnStart;
	private JButton 	btnStop;
	private JTextField 	txtInput;
	private JLabel 		lblOutput;
	
	public SwingFib() {
		super();
		
		/////////// inicializace grafickeho rozhrani ///////////
		
		this.setTitle("Demo");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 70);
		
		txtInput = new JTextField("30", 5);
		lblOutput = new JLabel("Vysledek:");

		btnStart = new JButton("start");
		btnStop = new JButton("stop");
		btnStop.setEnabled(false);
		
		this.add(txtInput);
		this.add(lblOutput);
		this.add(btnStart);
		this.add(btnStop);
		
		///////// konec inicializace grafickeho rozhrani ///////////
		
		//
		// tato udalost slouzi k zahajeni vypoctu
		//
		btnStart.addActionListener(e -> {
			final int arg = Integer.parseInt(txtInput.getText()); 	// nacte vstup
			int x = fib(arg);										// provede vypocet
			displayResult(x);										// zobrazi vysledek
		});

		//
		// tato udalost bude provedena v momente, kdy uzivatel explicitne prerusi vypocet
		//
		btnStop.addActionListener(e ->  { } );		// mel by se prerusit vypocet, ale neni jak 
	}
	
	/**
	 * Upravi graficke rozhrani tak, aby signalizovalo, ze probiha vypocet
	 */
	public void adjustGUI() {
		lblOutput.setText("Wait!");
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	}
	
	/**
	 * Zobrazi vysledek vypoctu
	 * @param value -- vysledna hodnota 
	 */
	public void displayResult(int value) {
		lblOutput.setText(Integer.toString(value));
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}
	
	/**
	 * Zobrazi, ze vypocet byl prerusen
	 */
	public void displayAbort() {
		lblOutput.setText("Aborted");
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}
	
	/**
	 * Vypocte fibonnaciho cislo, slouzi k demonstraci narocneho vypoctu
	 */
	public static int fib( int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		return fib(n - 1) + fib(n - 2);
	}

	public static void main(String [] args) {
		JFrame frame = new SwingFib();
		frame.setVisible(true);
	}
}