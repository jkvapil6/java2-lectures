package cz.upol.zp4jv.swing2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Priklad na pouziti seznamu s vlastnim modelem
 */
public class CustomModelListExample extends JFrame {
	
	private static final long serialVersionUID = 7072358438662567666L;
	private JList<Integer> mainList;
	private JButton btnAdd;
	private JPanel mainPanel;
	
	private EvenIntsListModel listModel;

	public CustomModelListExample()  {
		super();
		
		this.setTitle("Custom Model");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(400, 400));
		
		listModel = new EvenIntsListModel(10);
		mainList = new JList<>(listModel);
		
		btnAdd = new JButton("add");
		btnAdd.addActionListener(e -> listModel.add());
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(mainList, BorderLayout.CENTER);
		mainPanel.add(btnAdd, BorderLayout.SOUTH);
		
		this.setContentPane(mainPanel);
		this.pack();
	}
	
	/**
	 * Vlastni model obsahujici prvnich <i>n</i> sudych cisel
	 */
	private static final class EvenIntsListModel extends AbstractListModel<Integer> {

		private static final long serialVersionUID = -1733824189990842822L;
		
		private int count;
		
		public EvenIntsListModel(int n) {
			this.count = n;
		}

		@Override
		public int getSize() {
			return count;
		}

		@Override
		public Integer getElementAt(int index) {
			return (index + 1) * 2;
		}

		public void add() {
			this.count++;
			// nutne aby doslo k aktualizaci uzivatelskeho prvku
			fireIntervalAdded(this, 0, count);
		}
	}
}
