package Client.Views;

import Client.MainClient;
import Client.Utils.MessageService;
import Server.Model.Answer;
import Server.Model.Question;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Exam {
    ArrayList<Question> questionList = null;
    private int interval = 10*60;
    private Timer timer;

    @FXML
    private Label lbUsername;

    @FXML
    private VBox listContent;

    @FXML
    private Button btnClick;

    @FXML
    private Label lbTimer;

    @FXML
    public void initialize() {
        lbUsername.setText(MainClient.getUserCurrent().getUsername());
    }

    @FXML
    private void onLogout() throws IOException {
        MainClient.setUserCurrent(null);
        MainClient.showLogin();
    }

    @FXML
    private void onClick() throws IOException, ClassNotFoundException {
        if (btnClick.getText().equals("Start")) {
            questionList = MainClient.getQuestionList();

            for (int i = 0; i < questionList.size(); i++) {
                Label label = new Label();
                label.setFont(new Font("System", 16));
                label.setPadding(new Insets(20, 0, 10, 0));
                label.setText("Câu " + (i + 1) + ": " + questionList.get(i).getDetails());
                listContent.getChildren().add(label);

                ToggleGroup group = new ToggleGroup();
                for (Answer answer : questionList.get(i).getAnswerList()) {
                    RadioButton ans = new RadioButton(answer.getDetails());
                    ans.setFont(new Font("System", 16));
                    ans.setPadding(new Insets(5, 0, 0, 20));
                    ans.setToggleGroup(group);
                    listContent.getChildren().add(ans);
                }
            }

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if(interval > 0) {
                        interval--;
                        int mins = interval / 60;
                        int seconds = interval % 60;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                lbTimer.setText((mins > 9 ? mins : "0" + mins) + ":" + (seconds > 9 ? seconds : "0" + seconds));
                            }
                        });
                    }
                    else {
                        timer.cancel();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                testExam();
                            }
                        });
                    }

                }
            }, 1000,1000);

            btnClick.setText("Submit");
        } else {
            testExam();
        }
    }

    private void testExam() {
        if (timer != null) {
            timer.cancel();
        }
        int i = -1;
        int point = 0;
        btnClick.setText("Start");

        for (Node ans : listContent.getChildren()) {
            if (ans instanceof Label) {
                i++;
            } else if (ans instanceof RadioButton && ((RadioButton) ans).isSelected()) {
                if (questionList.get(i).isResult(((RadioButton) ans).getText())) {
                    point++;
                }
            }
        }

        String str = String.format("%d / %d", point, questionList.size());
        MessageService.showMessage("Số điểm Đã đạt được", str);
        lbTimer.setText("00:00");
        listContent.getChildren().remove(0, listContent.getChildren().size());
        questionList = null;
        btnClick.setDisable(true);
    }
}
