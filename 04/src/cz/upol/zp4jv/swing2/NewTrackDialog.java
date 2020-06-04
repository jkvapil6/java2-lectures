package cz.upol.zp4jv.swing2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

public class NewTrackDialog extends JDialog {

	private static final long serialVersionUID = 7261964341021226831L;

	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancel;

	private JTextField txtFirst, txtSecond, txtName;
	private JLabel lbFirst;
	private JLabel lbSecond;
	private JLabel lbName;

	private Track result = null;

	public NewTrackDialog(NewPlaylistDialog parentFrame) {
		super(parentFrame);
		setTitle("Add/Edit Playlist");
		
		lbFirst = new JLabel("Location:");
		lbSecond = new JLabel("Duration:");
		lbName = new JLabel("Name:");
		txtFirst = new JTextField();
		txtFirst.setColumns(30);
		txtSecond = new JTextField();
		txtSecond.setColumns(30);
		txtName = new JTextField();
		txtName.setColumns(30);
		
		// usporadani do mrizky 2 x 2 s pomoci GroupLayout
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
									.addComponent(lbFirst)
									.addComponent(lbSecond)
									.addComponent(lbName))
						.addGroup(layout.createParallelGroup()
									.addComponent(txtFirst)
									.addComponent(txtSecond)
									.addComponent(txtName))); // nastavuje navic vlastnosti komponente
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(lbFirst)
											.addComponent(txtFirst))

						.addGroup(layout.createParallelGroup()
											.addComponent(lbSecond)
											.addComponent(txtSecond))

						.addGroup(layout.createParallelGroup()
											.addComponent(lbName)
											.addComponent(txtName)));
		
		inputPanel.add(lbFirst);
		inputPanel.add(txtFirst);
		
		// usporadani do mrizky 1 x 2
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(5);
		btnLayout.setVgap(5);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener((ActionEvent e) -> {
			// ulozi vysledek
			List<Track> tracklist = new LinkedList<>();
			Track temp = new Track(txtFirst.getText(), txtSecond.getText(), txtName.getText());

			//result[0] = txtFirst.getText();
			//result[1] = txtSecond.getText();
			result = temp;
			setVisible(false);
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener((ActionEvent e) -> {
			// signalizuje, ze vysledek nema byt pouzit
			result = null;
			setVisible(false);
		});
	
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	  
		pack();
	}

	public Track getResult() {
		return result;
	}

}
