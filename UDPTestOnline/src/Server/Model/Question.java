package Server.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String details;
    private ArrayList<Answer> answerList;

    public Question(String details, ArrayList<Answer> answerList) {
        this.details = details;
        this.answerList = answerList;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public void addAnswer(Answer answer) {
        answerList.add(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "detail='" + details + '\'' +
                ", answerList=" + answerList.size() +
                '}';
    }

    public String getResult() {
        for (Answer answer : answerList) {
            if (answer.isTruth()) {
                return answer.getDetails();
            }
        }
        return null;
    }

    public boolean isResult(String s) {
        return s.equals(getResult());
    }
}
