package main.java;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socket = null;
    private DataInputStream  dataInputStream  = null;
    private DataOutputStream dataOutputStream = null;

    public Client(String serverName, int serverPort) {

        System.out.println("Establishing connection. Please wait ...");

        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            start();
        } catch(UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch(IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }

        String line = "";

        while (!line.equals(".bye")) {
            try {
                line = dataInputStream.readLine();
                dataOutputStream.writeUTF(line);
                dataOutputStream.flush();
            } catch(IOException ioe) {
                System.out.println("Sending error: " + ioe.getMessage());
            }
        }
    }

    public void start() throws IOException {
        dataInputStream   = new DataInputStream(System.in);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void stop() {
        try {
            if (dataInputStream   != null)  dataInputStream.close();
            if (dataOutputStream != null)  dataOutputStream.close();
            if (socket    != null)  socket.close();
        } catch(IOException ioe) {
            System.out.println("Error closing ...");
        }
    }

    public static void main(String args[]) {
        Client client = null;
        if (args.length != 2)
            System.out.println("Usage: java ChatClient host port");
        else
            client = new Client(args[0], Integer.parseInt(args[1]));
    }
}
