package Server.Utils;

import Server.Model.Answer;
import Server.Model.Question;
import Server.Model.User;

import java.sql.*;
import java.util.ArrayList;

public class Service {
    public static User login(User user) throws SQLException, ClassNotFoundException {
        Connection connection = SQLServerConnUtils.connectionMSSQL();
        Statement statement = connection.createStatement();
        String sql = "Select * from Users";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            if (user.getUsername().equals(rs.getString("username"))
                    && user.getPassword().equals(rs.getString("password"))) {
                connection.close();
                return user;
            }
        }

        rs.close();
        connection.close();

        return null;
    }

    public static ArrayList<Question> getQuestions(int number) throws SQLException, ClassNotFoundException {
        ArrayList<Question> questionList = new ArrayList<>();
        Connection connection = SQLServerConnUtils.connectionMSSQL();
        Statement statement = connection.createStatement();
        String sql = String.format("Select top %s id, details from Questions ORDER BY NEWID()", number);
        String queryAnswers = "Select * from Answers where idQuestion = ?";
        PreparedStatement pstm = connection.prepareStatement(queryAnswers);
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Question question = new Question(rs.getString("details"), new ArrayList<>());
            pstm.setInt(1, rs.getInt("id"));
            ResultSet rsAnswers = pstm.executeQuery();
            while (rsAnswers.next()) {
                question.addAnswer(new Answer(
                        rsAnswers.getString("details"),
                        rsAnswers.getInt("truth") == 1));
            }
            rsAnswers.close();
            questionList.add(question);
        }

        rs.close();
        connection.close();

        return questionList;
    }

    public static boolean register(User user) throws SQLException, ClassNotFoundException {
        if (isExisted(user)) {
            String sql = "insert into Users values (?, ?, 0)";
            Connection connection = SQLServerConnUtils.connectionMSSQL();
            Statement statement = connection.createStatement();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.executeUpdate();
            connection.close();
            return true;
        }
        return false;
    }

    public static boolean isExisted(User user) throws SQLException, ClassNotFoundException {
        Connection connection = SQLServerConnUtils.connectionMSSQL();
        Statement statement = connection.createStatement();
        String sql = String.format("Select COUNT(*) from Users where username = '%s'", user.getUsername());
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        boolean cond = rs.getInt(1) == 0;
        rs.close();
        connection.close();
        return cond;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(isExisted(new User("a", "")));
    }
}
