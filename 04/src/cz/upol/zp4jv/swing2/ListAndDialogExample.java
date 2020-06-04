package cz.upol.zp4jv.swing2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class ListAndDialogExample extends JFrame {

	private static final long serialVersionUID = -6990968542961079817L;
	
	private JList<String> mainList;
	private JButton btnAdd;
	private JPanel mainPanel;
	private DefaultListModel<String> listModel;

	private List<String> items = new ArrayList<String>();

	public ListAndDialogExample()  {
		super();
		
		items.add("Foo");
		items.add("Bar");
		
		this.setTitle("Lists & Dialogs");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(400, 400));

		// vytvori seznam s pevne danym listem polozek
		// mainList = new JList(new String [] {"Foo", "Bar" });
		
		// vytvori seznam, ktery zpracovava data z nejakeho konkretniho datoveho modelu
		// nejdriv vytvori model, pak do nej vlozi prvek a priradi jej seznamu
		listModel = new DefaultListModel<>();
		listModel.addElement("Foo");
		mainList = new JList<>(listModel);
		
		btnAdd = new JButton("add");
		btnAdd.addActionListener(this::addAction);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(mainList, BorderLayout.CENTER);
		mainPanel.add(btnAdd, BorderLayout.SOUTH);
		
		this.setContentPane(mainPanel);
		this.pack();
	}
	/**
	 * Udalost, ktera zobrazi formular
	 */
	private void addAction(ActionEvent e) {
		DialogExample dlg = new DialogExample(this); // vytvori dialog a uda, ktery formular je nadrazeny
		dlg.setModal(true);						     // modalita !!!
		dlg.setVisible(true);					     // ceka, dokud nebude uzavren formular 
		if (dlg.getResult() != null)			     // precte hodnotu
			listModel.addElement(dlg.getResult());
		
		dlg.dispose();
	}
}
