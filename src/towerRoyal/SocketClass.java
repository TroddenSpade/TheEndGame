package towerRoyal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClass {
    private Socket socket = null;
    private static SocketClass client = new SocketClass();

    public SocketClass getClient() {
        return client;
    }

    public static void run() throws UnknownHostException, IOException, ClassNotFoundException {
        String ip = "127.0.0.1";
        int port = 5000;
        client.socketConnect(ip, port);

    }

    private void socketConnect(String ip, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(ip, port);
    }

    public String echo(String message) {
        try {
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            out.println(message);
            String returnStr = in.readLine();
            return returnStr;
        }catch (Exception e){
            System.out.println("e");
        }
        return null;
    }

    private Socket getSocket() {
        return socket;

    }

}