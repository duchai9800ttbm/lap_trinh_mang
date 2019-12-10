package Server;

import Server.Model.Question;
import Server.Model.User;
import Server.Utils.Service;
import com.sun.javaws.IconUtil;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerSendExam extends ServerThread {
    private Thread t;
    private int SERVER_PORT = 7;
    private byte[] BUFFER = new byte[4096];
    private DatagramSocket ds = null;

    @Override
    public void run() {
        System.out.println("Server send Exam port: "+ SERVER_PORT +" is running...");

        try {
            ds = new DatagramSocket(SERVER_PORT);

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                if (onReceive(incoming)) {
                    onSend(incoming);
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean onReceive(DatagramPacket incoming) throws IOException, ClassNotFoundException {
        byte[] data = incoming.getData();

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);

        return ((String) is.readObject()).equals("send");
    }

    private void onSend(DatagramPacket incoming) throws IOException, SQLException, ClassNotFoundException {
        ArrayList<Question> list = Service.getQuestions(10);

        for (Question question : list) {
//            System.out.println(question);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(question);
            byte[] data = outputStream.toByteArray();

            DatagramPacket outsending = new DatagramPacket(data, data.length, incoming.getAddress(), incoming.getPort());
            ds.send(outsending);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(null);
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
