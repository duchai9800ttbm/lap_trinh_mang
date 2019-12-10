package Server;

import Server.Model.User;
import Server.Utils.Service;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ServerLogin extends ServerThread {
    private Thread t;
    private int SERVER_PORT = 6;
    private byte[] BUFFER = new byte[4096];
    private DatagramSocket ds = null;

    @Override
    public void run() {
        System.out.println("Server login port: "+ SERVER_PORT +" is running...");

        try {
            ds = new DatagramSocket(SERVER_PORT);

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                User user = onLogin(onReceive(incoming));

                onSend(user, incoming);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private User onReceive(DatagramPacket incoming) throws IOException, ClassNotFoundException {
        byte[] data = incoming.getData();

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);

        User user = (User) is.readObject();

        return user;
    }

    private User onLogin(User user) throws SQLException, ClassNotFoundException {
        return Service.login(user);
    }

    private void onSend(User user, DatagramPacket incoming) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);

        os.writeObject(user);
        byte[] data = outputStream.toByteArray();

        DatagramPacket outsending = new DatagramPacket(data, data.length, incoming.getAddress(), incoming.getPort());
        ds.send(outsending);
    }

    @Override
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
