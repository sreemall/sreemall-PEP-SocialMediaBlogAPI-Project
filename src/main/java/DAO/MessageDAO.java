package DAO;

import java.sql.*;
import java.util.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    Message message;

    public MessageDAO () {
        this.message = new Model.Message();
    }
    public MessageDAO (Message message) {
        this.message = message;
    }

    public List<Message> selectAllMessages () {
        Connection dbConnection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message";

            PreparedStatement prepStatement = dbConnection.prepareStatement(sql);
            ResultSet rs = prepStatement.executeQuery();

            while (rs.next()) {
                messages.add (new Message(rs.getInt (1),
                                            rs.getInt (2),
                                            rs.getString (3),
                                            rs.getLong(4)));
            }
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }

        return messages;
    }

    public Message selectMessageByMesageId (int message_id) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT posted_by, message_text, time_posted_epoch FROM message WHERE message_id = ?";
            PreparedStatement prepStatement = dbConnection.prepareStatement(sql);
            prepStatement.setInt (1, message_id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {  //message found
                return new Message (message_id, rs.getInt(1), rs.getString(2), rs.getLong(3));
            }
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }

        return null;  //message not found
    }

    public List<Message> selectAllMessagesByUser (int account_id) {
        Connection dbConnection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message where posted_by = ?";

            PreparedStatement prepStatement = dbConnection.prepareStatement(sql);
            prepStatement.setInt (1, account_id);
            
            ResultSet rs = prepStatement.executeQuery();

            while (rs.next()) {
                messages.add (new Message(rs.getInt (1),
                                            rs.getInt (2),
                                            rs.getString (3),
                                            rs.getLong(4)));
            }
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }

        return messages;
    }

    public Message insertMessage (Message message) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement prepStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setInt (1, message.getPosted_by());
            prepStatement.setString (2, message.getMessage_text());
            prepStatement.setLong (3, message.getTime_posted_epoch());
            prepStatement.executeUpdate();

            ResultSet pkResultSet = prepStatement.getGeneratedKeys();

            if (pkResultSet.next()) {
                return new Message (pkResultSet.getInt(1),
                                    message.getPosted_by(),
                                    message.getMessage_text(),
                                    message.getTime_posted_epoch());
            }
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }

        return null;  //couldn't insert new message
    }

    public void deleteMessageById (int message_id) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement prepStatement = dbConnection.prepareStatement(sql);
            prepStatement.setInt (1, message_id);
            prepStatement.executeUpdate();
            
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }
    }

    public void UpdateMessageTextById (int message_id, String message_text) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement prepStatement = dbConnection.prepareStatement(sql);
            prepStatement.setString (1, message_text);
            prepStatement.setInt (2, message_id);
            prepStatement.executeUpdate();
            
        }
        catch (SQLException exp) {
            System.out.println (exp.getMessage());
        }
    }
}
