package cz.upol.zp4jv.swing1;

import javax.swing.JFrame;

public class Launcher {

	public static void main(String[] args) {


		JFrame form = null;
		int option = 2;
		switch (option) {
			case 1: form = new SimpleLayoutForm(); break;
			case 2: form = new BorderLayoutForm(); break; // predelany na ukol 3
			case 3: form = new MenuForm(); break;
			default: break;
		}
		form.setVisible(true);


		System.out.println(CaesarCipher.checkInput("He llo."));
		System.out.println(CaesarCipher.checkInput("HE  .LOLO"));

		System.out.println(CaesarCipher.caesarEncode("AH J", 4, true));
		System.out.println(CaesarCipher.caesarEncode("AH J", 4, false));

		System.out.println(CaesarCipher.caesarEncode(CaesarCipher.caesarEncode("AH J", 4, true), 4, false));


	}
}
