package cz.upol.zp4jv.swing2;

import javax.swing.JFrame;

public class Launcher {
	
	public static void main(String[] args) {
		JFrame form = null;
		int option = 5;
		switch (option) {
		case 1: form = new AdvancedFeatures(); break;
		case 2: form = new ListAndDialogExample(); break;
		case 3: form = new CustomModelListExample(); break;
		case 4: form = new TableExample(); break;
		case 5: form = new PlaylistTable(); break;
		}
		form.setVisible(true);
	}
}
