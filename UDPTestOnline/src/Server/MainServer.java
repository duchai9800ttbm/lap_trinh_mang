package Server;

public class MainServer {
    public static void main(String[] args) {
        ServerThread login = new ServerLogin();
        ServerThread sendExam = new ServerSendExam();
        ServerThread test = new ServerRegister();

        login.start();
        sendExam.start();
        test.start();
    }
}
