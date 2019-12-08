package server;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class WorkerThread extends Thread {
    private Socket socket;
    private Consumer<Serializable> onReceiveCallback;

    public WorkerThread(Socket socket, Consumer<Serializable> onReceiveCallback) {
        this.socket = socket;
        this.onReceiveCallback = onReceiveCallback;
    }

    public void run() {
        System.out.println("Processing: " + socket);
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Serializable data = (Serializable) in.readObject();
                onReceiveCallback.accept(data);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}
