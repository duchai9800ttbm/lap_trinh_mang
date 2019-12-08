package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private ConnectionThread connectionThread = new ConnectionThread();
    private Consumer<Serializable> onReceiveCallback;

    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public void startConnection() throws Exception {
        connectionThread.start();
    }

    public void send(Serializable data) throws Exception {
        connectionThread.out.writeObject(data);
    }

    public void closeConnection() throws Exception {
        if (!isServer()) {
            connectionThread.socket.close();
        }
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        @Override
        public void run() {
            try {
                ServerSocket server = null;
                Socket socket = null;

                if (isServer()) {
                    server = new ServerSocket(getPort());
                    server.setReuseAddress(true);

                    while (true) {
                        socket = server.accept();

                        System.out.println(socket);

                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        this.socket = socket;
                        this.out = out;

                            while (true) {
                            Serializable data = (Serializable) in.readObject();
                            onReceiveCallback.accept(data);
                        }
                    }
                } else {
                    socket = new Socket(getIP(), getPort());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    this.socket = socket;
                    this.out = out;
                    socket.setTcpNoDelay(true);

                    while (true) {
                        Serializable data = (Serializable) in.readObject();
                        onReceiveCallback.accept(data);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
