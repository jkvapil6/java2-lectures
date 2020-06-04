package cz.upol.zp4jv.swing1;

public class CaesarCipher {

    /**
     * Metoda zakoduje daný vstup posunutím všech znaků o n mist, predikát encode udává jestli dopředu (true) nebo dozadu (false)
     */
    public static String caesarEncode(String input, int n, boolean encode) {

        if (!encode) n = -n;

        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ. ";
        String output = "";
        int newPos;

        for (int i = 0; i < input.length(); i++) {
            newPos = ((charSet.indexOf( input.charAt(i) ) + n) % charSet.length());
            if (newPos < 0) newPos = charSet.length() + newPos;
            output = output.concat(String.valueOf(charSet.charAt(newPos)));
        }

        return output;
    }

    /**
     * Metoda zkontroluje, zda zadany vstup odpovida nasi abecede
     */
    public static boolean checkInput(String input) {

        for (int i = 0; i < input.length(); i++) {
            if (!Character.toString(input.charAt(i)).matches("[A-Z. ?]")) return false;
        }
        return  true;
    }
}
