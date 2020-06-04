package cz.upol.zp4jv.swing2;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;


public class PlaylistTable extends JFrame {

    private static final long serialVersionUID = 1541752522315393245L;

    private JPanel panel = new JPanel();
    private JPanel trackPanel = new JPanel();
    private JPanel playlistPanel = new JPanel();

    private JTable table;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdit;
    private JPanel bottomPanel;

    private JList<String> trackList;
    private DefaultListModel<String> listModel; // seznam treku

    private String[] columns = new String[] { "Name", "Genre" };
    private List<Object[]> values = new ArrayList<>();

    private MyTableModel tableModel;

    private Playlist selectedPlaylist;
    private List<Playlist> playlists = new ArrayList<>(); // seznam vsech playlistu

    public PlaylistTable() {

        // nastavi celemu oknu
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(476, 630));
        setTitle("Playlists");

        // tabulka s vlastnim modelem
        tableModel = new MyTableModel();
        table = new JTable(tableModel);

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(this::addActionAddPlaylist);
        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(this::addActionEditPlaylist);
        //btnTracks = new JButton("Add Track");
        //btnTracks.addActionListener(this::addActionAddTrack);
        btnDel = new JButton("Del");
        btnDel.addActionListener((ActionEvent e) -> {
            if (table.getSelectedRow() >= 0) {
                // todo(3) : vyresit mazani objektu z tabulky
                tableModel.removeRow(table.getSelectedRow());

            }
        });

        listModel = new DefaultListModel<>();
        trackList = new JList<>(listModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() > -1) {
                    // proc se vola 2x?
                    btnEdit.setVisible(true);
                    btnDel.setVisible(true);
                    updateTrackList();
                }
            }
        });

        playlistPanel.add(new JScrollPane(table), BorderLayout.NORTH);
        //playlistPanel.setBackground(Color.RED);

        //panel.add(new JScrollPane(trackList), BorderLayout.EAST);
        trackPanel.setLayout(new BorderLayout());
        trackPanel.add(new JScrollPane(trackList), BorderLayout.NORTH);
        trackPanel.setBackground(Color.green);
        //trackPanel.setPreferredSize(new Dimension(500, 150));

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        // hlavni panel
        panel.setLayout(new BorderLayout());
        panel.add(playlistPanel, BorderLayout.NORTH);
        //panel.add(new JScrollPane(table), BorderLayout.NORTH);

        //table.setMaximumSize(new Dimension(20, 20));

        panel.add(trackPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);


        bottomPanel.add(btnAdd, BorderLayout.SOUTH);
        bottomPanel.add(btnEdit, BorderLayout.SOUTH);
        btnEdit.setVisible(false);
        bottomPanel.add(btnDel, BorderLayout.SOUTH);
        btnDel.setVisible(false);
        //bottomPanel.add(btnTracks, BorderLayout.EAST);
        getContentPane().add(panel);

        pack();
    }


    /**
     * Udalost projde playlisty a podle nazvu playlistu, na ktery se kliklo pak aktualizuje tracklist
     */
    private void updateTrackList() {
        String name = getNameOfSelectedPlaylist();

        if (name != null) {
            // todo prvni vymaz tracklist
            listModel.clear();
            setSelectedPlaylistByRow(name);

            for (Track t : selectedPlaylist.getTracks()) {
                listModel.addElement(t.getName().concat(" ").concat(t.getLocation()).concat(" ").concat(t.getDuration()));
            }
        }
    }

    private void setSelectedPlaylistByRow(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equals(name)) {
                System.out.println(name);
                selectedPlaylist = p;
                break;
            }
        }
    }

    private String getNameOfSelectedPlaylist() {
        if (table.getSelectedRow() >= 0) return tableModel.getValueAt(table.getSelectedRow(), 0).toString();
        else return null;
    }

    /**
     * Udalost, ktera zobrazi formular pro pridani playlistu
     */
    private void addActionAddPlaylist(ActionEvent e) {
        NewPlaylistDialog dlg = new NewPlaylistDialog(this, null);
        dlg.setModal(true);
        dlg.setVisible(true);

        if (dlg.getResult() != null) {
            playlists.add(dlg.getResult());
            tableModel.addRow(dlg.getResult().getName(), dlg.getResult().getGenre());
        }
        dlg.dispose();
    }

    /**
     * Udalost, ktera zobrazi formular pro editaci playlistu
     */
    private void addActionEditPlaylist(ActionEvent e) {
        if (table.getSelectedRow() >= 0) {
            NewPlaylistDialog dlg = new NewPlaylistDialog(this, selectedPlaylist);
            dlg.setModal(true);
            dlg.setVisible(true);

            if (dlg.getResult() != null) {
                selectedPlaylist = dlg.getResult();
                tableModel.setValueAt(dlg.getResult().getName(), table.getSelectedRow(), 0);
                tableModel.setValueAt(dlg.getResult().getGenre(), table.getSelectedRow(), 1);
                updateTrackList();
            }
            dlg.dispose();
        }
    }


    /**
     * Vlastni model popisujici data v tabulce a jejich typy
     */
    private final class MyTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 8720445364835231767L;

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public int getRowCount() {
            return values.size();
        }

        @Override
        public Object getValueAt(int r, int c) {
            return values.get(r)[c];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // nutne k tomu, abychom rozlisili typ dat k vykresleni
            if (columnIndex == 2) return Double.class;
            return super.getColumnClass(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex >= 1);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            values.get(rowIndex)[rowIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void addRow(String name, String genre) {
            values.add(new Object[] {name, genre});
            fireTableDataChanged();
        }

        public void removeRow(int index) {
            values.remove(index);
            fireTableDataChanged();
        }
    }
}
