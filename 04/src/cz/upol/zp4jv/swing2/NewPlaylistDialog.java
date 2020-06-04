package cz.upol.zp4jv.swing2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewPlaylistDialog extends JDialog {

	private static final long serialVersionUID = 7261964341021226831L;

	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JPanel tracksPanel = new JPanel();

	private JButton btnOk;
	private JButton btnTracks;
	private JButton btnCancel;

	private JTextField txtFirst;
	private JTextField txtSecond;
	private JLabel lbFirst;
	private JLabel lbSecond;
	private JLabel lbTracks;

	private DefaultListModel<String> listModel; // seznam treku
	private JList<String> trackList;

	private Playlist result = null;
	private List<Track> tracklist;

	public NewPlaylistDialog(JFrame parentFrame, Playlist edited) {
		super(parentFrame);
		setTitle("Add/Edit Playlist");
		setPreferredSize(new Dimension(450, 640));

		tracklist = new LinkedList<>();

		lbFirst = new JLabel("Name:");
		lbSecond = new JLabel("Genre:");
		lbTracks = new JLabel("Tracks:");
		lbTracks.setHorizontalAlignment(SwingConstants.CENTER);
		txtFirst = new JTextField();
		txtFirst.setColumns(30);
		txtSecond = new JTextField();
		txtSecond.setColumns(30);
		
		// usporadani do mrizky 2 x 2 s pomoci GroupLayout
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
									.addComponent(lbFirst)
									.addComponent(lbSecond))
						.addGroup(layout.createParallelGroup()
									.addComponent(txtFirst)
									.addComponent(txtSecond))); // nastavuje navic vlastnosti komponente
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(lbFirst)
											.addComponent(txtFirst))
						.addGroup(layout.createParallelGroup()
											.addComponent(lbSecond)
											.addComponent(txtSecond)));
		
		inputPanel.add(lbFirst);
		inputPanel.add(txtFirst);
		
		// usporadani do mrizky 1 x 2
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(5);
		btnLayout.setVgap(5);
		
		btnOk = new JButton("Ok");
		btnOk.setEnabled(false);
		btnOk.addActionListener((ActionEvent e) -> {
			// ulozi vysledek
			Playlist temp = new Playlist(txtFirst.getText(), txtSecond.getText(), tracklist);
			result = temp;
			setVisible(false);
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener((ActionEvent e) -> {
			// signalizuje, ze vysledek nema byt pouzit
			result = null;
			setVisible(false);
		});

		btnTracks = new JButton("Add Track");
		btnTracks.addActionListener(this::addActionAddTrack);

		listModel = new DefaultListModel<>();
		trackList = new JList<>(listModel);

		tracksPanel.setLayout(new BorderLayout());
		tracksPanel.add(lbTracks, BorderLayout.NORTH);
		tracksPanel.add(trackList, BorderLayout.CENTER);

		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnTracks);
		buttonPanel.add(btnCancel);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(tracksPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// nastavit defaultni placeholder
		if (edited != null) {
			tracklist = edited.getTracks();
			txtFirst.setText(edited.getName());
			txtSecond.setText(edited.getGenre());
			for (Track t : edited.getTracks()) {
				listModel.addElement(t.getName().concat(" ").concat(t.getLocation()).concat(" ").concat(t.getDuration()));
			}
		}
		pack();
	}

	public Playlist getResult() {
		return result;
	}

	// todo : osetrit, ze po pridani treku nebudeme moci pridat plylist bez nazvu nebo zanru
	/**
	 * Udalost, ktera prida trek do playlistu
	 */
	private void addActionAddTrack(ActionEvent e) {

		NewTrackDialog dlg = new NewTrackDialog(this);
		dlg.setModal(true);
		dlg.setVisible(true);

		if (dlg.getResult() != null) {
			Track temp = dlg.getResult();
			tracklist.add(temp);
			listModel.addElement(temp.getName().concat(" ").concat(temp.getLocation()).concat(" ").concat(temp.getDuration()));
			if (txtFirst != null && txtSecond != null) btnOk.setEnabled(true);
		}
		dlg.dispose();
	}
}
