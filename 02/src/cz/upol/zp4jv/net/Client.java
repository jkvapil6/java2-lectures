package cz.upol.zp4jv.net;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client {

    public static void main(String[] args) throws IOException {

        try (Socket s = new Socket("localhost", 4242);
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
             BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            /* klient 1

            sendMsg(wr, "CONNECT bob bob");
            System.out.println(getResponse(rd)); // ERR (spatne heslo)

            sendMsg(wr, "CONNECT bob bobo");
            System.out.println(getResponse(rd)); // OK

            sendMsg(wr, "MSG FOR alice Hello, Alice"); // OK
            System.out.println(getResponse(rd));

            sendMsg(wr,"MSG FOR alice Hello, Dave"); // OK
            System.out.println(getResponse(rd));

            sendMsg(wr,"READ"); // FROM alice ..
            System.out.println(getResponse(rd));

            sendMsg(wr,"LOGOUT"); // OK
            System.out.println(getResponse(rd));
            */

            /* klient 2 */

            sendMsg(wr, "CONNECT alice passw");
            System.out.println(getResponse(rd)); // OK

            sendMsg(wr, "Blah"); // ERR (neplatny pozadavek)
            System.out.println(getResponse(rd));

            sendMsg(wr,"MSG FOR amin Thanks."); // ERR (amin neexistuje)
            System.out.println(getResponse(rd));

            sendMsg(wr,"READ"); // FROM bob ..
            System.out.println(getResponse(rd));

            sendMsg(wr,"LOGOUT"); // OK
            System.out.println(getResponse(rd));
        }
    }

    private static List<String> getResponse(BufferedReader rd ) throws IOException {
        String response;
        List<String> responses = new LinkedList<>();

        while (true)  {
            response = rd.readLine();
            responses.add(response);
            if (response == null || response.equals("OK") || response.equals("ERR")) break; //
        }
        return responses;
    }

    private static void sendMsg(BufferedWriter wr, String msg) throws IOException {
        wr.write(msg);
        wr.newLine();
        wr.flush();
    }
}
