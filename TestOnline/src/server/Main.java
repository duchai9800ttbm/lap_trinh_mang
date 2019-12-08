package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Main {
    static int interval;


    public static void main(String[] args) throws Exception {
        int time = 100;
        ArrayList<Socket> socketList = new ArrayList<>();

        ServerSocket server = new ServerSocket(5050);
        server.setReuseAddress(true);

        while (true) {
            Socket socket = server.accept();

            System.out.println(socket);

            WorkerThread workerThread = new WorkerThread(socket, data -> {
                System.out.println(data);
            });
        }

//        networkConnection.startConnection();
//
//        TimeUnit.SECONDS.sleep(5);
//
//        TimerCountDown(100);

//        while (true) {
//
//        }
    }

//    public static void TimerCountDown(int time) {
//        Timer timer = new Timer();
//        interval = time;
//
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                try {
//                    interval--;
//                    networkConnection.send(Integer.toString(interval));
//
//                    if (interval == 0) {
//                        timer.cancel();
//                        networkConnection.send("Time Over!");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1000, 1000);
//    }
}
