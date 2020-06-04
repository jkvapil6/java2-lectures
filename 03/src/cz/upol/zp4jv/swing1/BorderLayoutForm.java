package cz.upol.zp4jv.swing1;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class BorderLayoutForm extends JFrame {
	
	private static final long serialVersionUID = 2318977166804428747L;
	
	private JPanel     mainPanel;
	private JTextField txtCnt;

	private JTextArea  txtaEncode;
	private JTextArea  txtaDecode;

	private JLabel     lbTop;
	private JLabel     lbN;
	private JLabel     lbEncode;
	private JLabel     lbDecode;

	private JButton    btnEncode;
	private JButton    btnDecode;
	private JPanel     bottomPanel;
	private JPanel     centerPanel;

	private JMenuBar mainMenu;
	private JMenu    menuFile;
	
	public BorderLayoutForm() {
		this.setTitle("Caesarova šifra");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);


		/*** MENU ***/

		mainMenu = new JMenuBar();

		menuFile = new JMenu("Program");
		menuFile.setMnemonic(KeyEvent.VK_F);

		JMenuItem menuItemEncode = new JMenuItem("Zakódovat");
		menuItemEncode.addActionListener(e -> encode());

		JMenuItem menuItemDecode = new JMenuItem("Dekódovat");
		menuItemDecode.addActionListener(e -> decode());

		menuFile.add(menuItemEncode);
		menuFile.add(menuItemDecode);

		JMenuItem menuItemExit = new JMenuItem("Ukončit");
		menuItemExit.addActionListener((ActionEvent e) -> {
			setVisible(false);
			dispose();
		});

		menuFile.add(menuItemExit);
		mainMenu.add(menuFile);

		this.setJMenuBar(mainMenu);

		/*** BODY LAYOUT ***/

		mainPanel = new JPanel(new BorderLayout());

		// text arrays
		txtaEncode = new JTextArea("ZADEJ TEXT A KLIKNI NA ZAKODUJ");
		//txtaEncode.setBackground(Color.lightGray);
		txtaEncode.setFont(new Font("Courier", Font.PLAIN,16));
		txtaEncode.setBorder(new LineBorder(Color.gray));
		txtaEncode.setPreferredSize(new Dimension(400, 60));

		txtaDecode = new JTextArea("PREKLAD");
		txtaDecode.setBorder(new LineBorder(Color.gray));
		txtaDecode.setPreferredSize(new Dimension(400, 60));
		txtaDecode.setFont(new Font("Courier", Font.PLAIN,16));

		txtCnt = new JTextField();
		txtCnt.setFont(new Font("Courier", Font.PLAIN,16));
		txtCnt.setText("0");
		txtCnt.setPreferredSize(new Dimension(400, 30));

		// labels
		lbTop = new JLabel("Aplikace pro kódování textu pomocí Caesarovy šifry.");
		lbTop.setFont(new Font("Courier", Font.BOLD,14));
		lbTop.setOpaque(true); // nastavi, aby stitek mel pozadi
		lbTop.setBackground(Color.GRAY);
		lbTop.setForeground(Color.BLACK);
		lbTop.setPreferredSize(new Dimension(500, 50));
		lbTop.setHorizontalAlignment(0);

		lbN = new JLabel("O kolik míst se má vstup zakódovat (celé číslo):");
		lbN.setFont(new Font("Courier", Font.BOLD,16));
		lbEncode = new JLabel("Text k zakódování (dekódovaný):");
		lbEncode.setFont(new Font("Courier", Font.BOLD,16));
		lbDecode = new JLabel("Text k dekódování (zakódovaný):");
		lbDecode.setFont(new Font("Courier", Font.BOLD,16));

		// buttons
		btnEncode = new JButton("Zakóduj");
		btnEncode.setFont(new Font("Courier", Font.PLAIN,16));
		btnDecode = new JButton("Dekóduj");
		btnDecode.setFont(new Font("Courier", Font.PLAIN,16));

		// BOTTOM PANEL
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(btnEncode);
		bottomPanel.add(btnDecode);

		// CENTER PANEL
		centerPanel = new JPanel();
		Border padding = BorderFactory.createEmptyBorder(20, 10, 10, 10);
		centerPanel.setBorder(padding);

		centerPanel.add(lbN, BorderLayout.NORTH);
		centerPanel.add(txtCnt, BorderLayout.NORTH);
		centerPanel.add(lbEncode, BorderLayout.NORTH);
		centerPanel.add(txtaEncode, BorderLayout.NORTH);
		centerPanel.add(lbDecode, BorderLayout.NORTH);
		centerPanel.add(txtaDecode, BorderLayout.NORTH);
		centerPanel.add(bottomPanel, BorderLayout.NORTH);

		mainPanel.add(lbTop, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		btnEncode.addActionListener(e -> encode());
		btnDecode.addActionListener(e -> decode());
		
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(450, 450));
		this.pack();
	}

	private void encode() {
		try {
			if (CaesarCipher.checkInput(txtaEncode.getText())) {
				txtaDecode.setText(CaesarCipher.caesarEncode( txtaEncode.getText(),
						Integer.parseInt(txtCnt.getText()), true));
			} else {
				JOptionPane.showMessageDialog(BorderLayoutForm.this, "Lze přeložit pouze znaky \"ABCDEFGHIJKLMNOPQRSTUVWXYZ. \"", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(BorderLayoutForm.this, "n must be number!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void decode() {
		try {
			if (CaesarCipher.checkInput(txtaDecode.getText())) {
				txtaEncode.setText(CaesarCipher.caesarEncode( txtaDecode.getText(),
						Integer.parseInt(txtCnt.getText()), false));
			} else {
				JOptionPane.showMessageDialog(BorderLayoutForm.this, "Lze přeložit pouze znaky \"ABCDEFGHIJKLMNOPQRSTUVWXYZ. \"", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(BorderLayoutForm.this, "n must be number!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
