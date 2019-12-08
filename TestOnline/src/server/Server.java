package server;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {
    private int port;
    private boolean isServer = true;

    public Server(int port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return isServer;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
