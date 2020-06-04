package cz.upol.zp4jv.swing1;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuForm extends JFrame {

	private static final long serialVersionUID = -833672688842568759L;

	private JMenuBar mainMenu;
	private JMenu    menuFile;
	private JMenu    menuEdit;

	public MenuForm() {
		super();
		this.setTitle("Form #3");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// vytvori objekt predstavujici hlavni radek menu
		mainMenu = new JMenuBar();

		// vytvori objekt menu: ,,File'' (pristupny pres Alt+F)
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);

		// vytvori polozku v menu a navaze na ni udalost
		JMenuItem menuItemNew = new JMenuItem("New");
		menuItemNew.addActionListener(e -> System.out.println("new"));

		// pripoji polozku menu do menu
		menuFile.add(menuItemNew);

		// vytvori dalsi polozku v menu
		JMenuItem menuItemExit = new JMenuItem("Exit");

		// nastavi klavesovou zkratku
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener((ActionEvent e) -> {
			System.out.println("So long!");
			// ukonceni prace s oknem
			setVisible(false);
			dispose();
		});
		menuFile.add(menuItemExit);

		// dalsi menu
		menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);

		mainMenu.add(menuFile);
		mainMenu.add(menuEdit);

		// nastavi formulari menu
		this.setJMenuBar(mainMenu);

		this.setPreferredSize(new Dimension(400, 400));
		this.pack();
	}
}
