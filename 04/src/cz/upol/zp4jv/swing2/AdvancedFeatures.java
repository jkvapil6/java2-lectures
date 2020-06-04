package cz.upol.zp4jv.swing2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AdvancedFeatures extends JFrame {
	
	private static final long serialVersionUID = 2434326396258520046L;

	public static void main(String[] args) throws Exception  {
		AdvancedFeatures f = new AdvancedFeatures();
		f.setVisible(true);
	}

	private static String HTML_TEXT = "<html>Lorem ipsum dolor sit amet,"
			+ "consectetur adipiscing elit. <i>Nunc elit sem.</i><br>"
			+ "<span style=\"color: yellow; font-weight: normal;\">"
			+ "Suspendisse potenti. Suspendisse eget venenatis nisl.</span></html>"; 
	
	private JPanel panel;
	private JEditorPane editor;
	
	public AdvancedFeatures() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 300));
		this.setTitle("HTML in SWING");
		
		panel = new JPanel(new BorderLayout());

		// v ramci jednotlivych popisku lze pouzit formatovani
		// zalozene na podmnozine HTML
		panel.add(new JLabel(HTML_TEXT), BorderLayout.NORTH);
		panel.add(new JButton(HTML_TEXT), BorderLayout.SOUTH);
		
		// editor umoznujici formatovani textu (opet s vyuzitim
		// podmnoziny HTML)
		editor = new JEditorPane();
		editor.setContentType("text/html");
		editor.setText(HTML_TEXT);
		
		// jednotlive uzivatelske prvky nemaji posuvniky
		// lze je pridat zabalenim do objektu JScrollPane
		panel.add(new JScrollPane(editor), BorderLayout.CENTER);
		getContentPane().add(panel);
		
		pack();
	}
}
