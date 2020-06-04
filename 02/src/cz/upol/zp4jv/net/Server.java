package cz.upol.zp4jv.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Vicevlaknova varianta, rozsireni ServerExample pro textovou komunikaci mezi klienty
 */
public class Server {
	
	private static boolean stopServer = false;

	public static void main(String[] args) throws IOException {
		ServerSocket srvSocket = new ServerSocket(4242);
		
		while (!isStopServer()) {
			Socket clientSocket = srvSocket.accept();
			Thread serverThread = new ServerThread(clientSocket);
			serverThread.start();
		}
		srvSocket.close();
	}

	/**
	 * Vlakno komunikujici s klientem
	 */
	private static class ServerThread extends Thread {
		
		private Socket clientSocket;

		public ServerThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			     BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

				processRequests(rd, wr);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Metoda zasila klientovi informaci o uspesnosti vyrizeni jeho pozadavku
	 */
	private static void flushMSG(BufferedWriter wr, String msg) throws IOException {
		wr.write(msg);
		wr.newLine();
		wr.flush();
	}
	
	/**
	 * Metoda zpracovava a vyrizuje pozadavky od klienta
	 */
	private static void processRequests(BufferedReader rd, BufferedWriter wr) throws IOException {

		String user = "";

		while (true) {
			String line = rd.readLine();
			if (line == null) break;

			String[] splited = line.split(" ");

			switch (splited[0]) {

				case "LOGOUT":
					flushMSG(wr,"OK");
					return;

				case "CONNECT":
					user = connectUser(splited[1], splited[2], wr);
					break;

				case "MSG" :
					tryToSendMsg(user, splited, wr);
					break;

				case "READ":
					tryToReadMsgs(user, wr);
					break;

				default:
					flushMSG(wr,"ERR");
			}
		}
	}

	/**
	 * Metoda pro cteni zprav konkretniho uzivatele
	 */
	private static void tryToReadMsgs(String user, BufferedWriter wr) throws IOException {
		if (user == "") {
			flushMSG(wr,"ERR");
		} else {
			List<String> msgs = readMsgs(user);
			for (String m : msgs) {
				wr.write(m);
				wr.newLine();
			}
			flushMSG(wr,"OK");
		}
	}

	/**
	 * Metoda pro odeslani zpravy
	 */
	private static void tryToSendMsg(String user, String[] splited, BufferedWriter wr) throws IOException {
		if (user == "" || !receiverExists(splited[2])) {
			flushMSG(wr,"ERR");
		} else {
			String msg = "";
			for (int i = 3; i < splited.length; i++) {
				msg = msg.concat(" ").concat(splited[i]);
			}
			sendMsg(user, splited[2], msg);
			flushMSG(wr,"OK");
		}
	}

	/**
	 * Metoda pro prihlaseni uzivatele, vraci jmeno uzivatele, pokud se prihlaseni povede, jinak vraci prazdny string
	 */
	private static String connectUser(String user, String pass, BufferedWriter wr) throws IOException {
		if (checkLogin(user, pass)) {
			flushMSG(wr,"OK");
			return user;
		} else {
			flushMSG(wr,"ERR");
			return "";
		}
	}

	/**
	 * Metoda vraci true, pokud najde hledaneho uzivatele, v opacnem pripade false
	 */
	private static synchronized boolean receiverExists(String receiver) throws FileNotFoundException{

		File file = new File("users.txt");
		Scanner s = new Scanner(file);
		while (s.hasNextLine()) {
			String [] user = s.nextLine().split(" ");
			if (user[1].equals(receiver)) {
				s.close();
				return true;
			}
		}
		s.close();

		return false;
	}

	/**
	 * Metoda prida na konec souboru zpravu
	 */
	private static void sendMsg(String from, String to, String msg) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("msgs.txt", true))){
            out.newLine();
            out.write(to + " " + from + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Metoda projde soubor se zpravami, vybere zpravy konkretniho uzivatele, ktere vraci. Zbytek ulozi zpet do souboru.
	 */
	private static synchronized List<String> readMsgs(String user) {
        List<String> msgs = new LinkedList<>();
        File file = new File("msgs.txt");
        File tmp = new File("tmp.txt");

	    try (BufferedReader rd = new BufferedReader(new FileReader(file));
            BufferedWriter wr = new BufferedWriter(new FileWriter(tmp, true))) {

			loadUsersMsgsToList(msgs, user, wr, rd);

            rd.close();
            wr.flush();
            wr.close();
            file.delete();
            tmp.renameTo(file);

	    } catch (IOException e) {
            e.printStackTrace();
		}

		return msgs;
	}

	/**
	 * Metoda ulozi do seznamu vsechny zpravy pro konkretniho uzivatele
	 */
	private static void loadUsersMsgsToList(List<String> msgs, String user, BufferedWriter wr, BufferedReader rd) throws IOException{

		String line = rd.readLine();
		while (line != null) {
			String[] splited = line.split(" ");
			if (splited[0].equals(user)) {
				String msg = "FROM ";
				msg = msg.concat(splited[1]).concat(":");
				for(int i = 2; i < splited.length; i++) {
					msg = msg.concat(" ").concat(splited[i]);
				}
				msgs.add(msg);
			} else {
				wr.write(line);
				wr.newLine();
			}
			line = rd.readLine();
		}
	}

	/**
	 * Metoda projde soubor s uzivateli a zkontroluje prihlasovaci udaje. Vraci true pokud sedi jmeno i heslo, jinak false.
	 */
	private static synchronized boolean checkLogin(String login, String pass) throws FileNotFoundException{

		File file = new File("users.txt");
		Scanner s = new Scanner(file);
		while (s.hasNextLine()) {
			String [] user = s.nextLine().split(" ");
			if (user[1].equals(login) && user[2].equals(pass)) {
				s.close();
				return true;
			}
		}
		s.close();

		return false;
	}

	private static synchronized void setStopServer(boolean value) {
		stopServer = value;
	}
	
	private static synchronized boolean isStopServer() {
		return stopServer;
	}
}
