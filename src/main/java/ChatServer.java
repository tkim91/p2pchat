package main.java;

import java.net.*;
import java.io.*;

public class ChatServer {

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream = null;

    public ChatServer(int port) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");
            socket = serverSocket.accept();
            System.out.println("Client accepted: " + socket);
            open();
            boolean done = false;
            while (!done) {
                try {
                    String line = dataInputStream.readUTF();
                    System.out.println(line);
                    done = line.equals(".bye");
                }
                catch(IOException ioe) {
                    done = true;
                }
            }
        close();
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void open() throws IOException {
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void close() throws IOException {
        if (socket != null)
            socket.close();
        if (dataInputStream != null)
            dataInputStream.close();
    }

    public static void main(String args[]) {
        ChatServer serverSocket = null;
        if (args.length != 1)
            System.out.println("Usage: java ChatServer port");
        else
            serverSocket = new ChatServer(Integer.parseInt(args[0]));
    }
}