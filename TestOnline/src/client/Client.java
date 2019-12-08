package client;

import server.NetworkConnection;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection {
    private int port;
    private String ip;

    public Client(String ip, int port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
        this.ip = ip;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
