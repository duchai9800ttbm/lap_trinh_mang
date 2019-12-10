package Client;

import Server.Model.Question;
import Server.Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class MainClient extends Application {
    private static String SERVER_IP = "127.0.0.1";
    private static int SERVER_LOGIN_PORT = 6; // Port of Server login
    private static int SERVER_SEND_PORT = 7; // Port of Server send exam
    private static int SERVER_REGISTER_PORT = 8; // Port of Server register
    private static byte[] BUFFER = new byte[4096];
    private static DatagramSocket ds = null;
    private static InetAddress server = null;
    private static Stage primaryStage;
    private static User userCurrent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ds = new DatagramSocket();
        server = InetAddress.getByName(SERVER_IP);
        this.primaryStage = primaryStage;

        showLogin();
    }

    public static void showLogin() throws IOException {
        close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainClient.class.getResource("Views/Login.fxml"));
        AnchorPane layoutLogin = (AnchorPane) loader.load();

        Scene scene = new Scene(layoutLogin);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showSignup() throws IOException {
        close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainClient.class.getResource("Views/Register.fxml"));
        AnchorPane layoutRegister = (AnchorPane) loader.load();

        Scene scene = new Scene(layoutRegister);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showExam() throws IOException {
        close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainClient.class.getResource("Views/Exam.fxml"));
        AnchorPane layoutLogin = (AnchorPane) loader.load();

        Scene scene = new Scene(layoutLogin);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void close() {
        if (primaryStage.isShowing()) {
            primaryStage.close();
        }
    }

    public static User getUserCurrent() {
        return userCurrent;
    }

    public static void setUserCurrent(User userCurrent) {
        MainClient.userCurrent = userCurrent;
    }

    public static User login(User user) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);

        os.writeObject(user);

        byte[] data = outputStream.toByteArray();

        DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_LOGIN_PORT);
        ds.send(dp);

        DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
        ds.receive(incoming);

        ByteArrayInputStream in = new ByteArrayInputStream(incoming.getData());
        ObjectInputStream is = new ObjectInputStream(in);

        return (User) is.readObject();
    }

    public static boolean register(User user) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);

        os.writeObject(user);

        byte[] data = outputStream.toByteArray();

        DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_REGISTER_PORT);
        ds.send(dp);

        DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
        ds.receive(incoming);

        ByteArrayInputStream in = new ByteArrayInputStream(incoming.getData());
        ObjectInputStream is = new ObjectInputStream(in);

        return (boolean) is.readObject();
    }

    public static ArrayList<Question> getQuestionList() throws IOException, ClassNotFoundException {
        ArrayList<Question> questionList = new ArrayList<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);

        os.writeObject("send");

        byte[] data = outputStream.toByteArray();

        DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_SEND_PORT);
        ds.send(dp);

        DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);

        while (true) {
            ds.receive(incoming);

            ByteArrayInputStream in = new ByteArrayInputStream(incoming.getData());
            ObjectInputStream is = new ObjectInputStream(in);

            Question question = (Question) is.readObject();

            if (question == null) {
                break;
            }

            questionList.add(question);
        }

        return questionList;
    }
}
