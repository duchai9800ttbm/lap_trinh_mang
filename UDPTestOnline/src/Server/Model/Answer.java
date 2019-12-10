package Server.Model;

import java.io.Serializable;

public class Answer implements Serializable {
    private String details;
    private boolean truth;

    public Answer(String details, boolean truth) {
        this.details = details;
        this.truth = truth;
    }

    public String getDetails() {
        return details;
    }

    public boolean isTruth() {
        return truth;
    }
}
