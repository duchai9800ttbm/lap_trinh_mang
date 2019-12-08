package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain extends Application {
    private int interval;
    private Stage primaryStage;
    private AnchorPane dashboardLayout;
    private NetworkConnection networkConnection = createServer();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        networkConnection.startConnection();
    }

    @Override
    public void stop() throws Exception {
        networkConnection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        initDashboard();
    }

    private Server createServer() {
        return new Server(5050, data -> {
            Platform.runLater(() -> {
                System.out.println(data);
            });
        });
    }

    private void initDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/dashboard.fxml"));
        dashboardLayout = (AnchorPane) loader.load();

        Scene scene = new Scene(dashboardLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void TimerCountDown(int time) {
        Timer timer = new Timer();
        interval = time;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    interval--;
                    networkConnection.send(Integer.toString(interval));

                    if (interval == 0) {
                        timer.cancel();
                        networkConnection.send("Time Over!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);
    }
}
