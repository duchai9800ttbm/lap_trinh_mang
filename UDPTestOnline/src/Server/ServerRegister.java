package Server;

import Server.Model.User;
import Server.Utils.Service;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ServerRegister extends ServerThread {
    private Thread t;
    private int SERVER_PORT = 8;
    private byte[] BUFFER = new byte[4096];
    private DatagramSocket ds = null;

    @Override
    public void run() {
        System.out.println("Server Register port: "+ SERVER_PORT + " is running...");

        try {
            ds = new DatagramSocket(SERVER_PORT);

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                boolean isRegister = onRegister(onReceive(incoming));

                onSend(isRegister, incoming);
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

    private boolean onRegister(User user) throws SQLException, ClassNotFoundException {
        return Service.register(user);
    }

    private void onSend(boolean isRegister, DatagramPacket incoming) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);

        os.writeObject(isRegister);
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
