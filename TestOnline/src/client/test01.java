package client;

import server.NetworkConnection;

public class test01 {
    public static void main(String[] args) throws Exception {
        NetworkConnection networkConnection = new Client("127.0.0.1", 5050, data -> {
            System.out.println(data);
        });

        networkConnection.startConnection();

        while (true) {
//            TimeUnit.SECONDS.sleep(5);
//            networkConnection.send("hello");
        }
    }
}
