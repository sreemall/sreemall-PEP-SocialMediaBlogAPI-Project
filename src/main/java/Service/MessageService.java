package Service;

import java.util.*;
import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public List<Message> getAllMessages() {
        return messageDAO.selectAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.selectMessageByMesageId (message_id);
    }

    public Message addMessage(Message message) {
        String message_text = message.getMessage_text();
        if ((message_text != null) && (message_text.length() > 0) && (message_text.length() <= 255)) {
            if (accountDAO.selectAccountById(message.getPosted_by()) != null) {
                return messageDAO.insertMessage(message);
            }
        }

        return messageDAO.insertMessage(message);
    }

    public Message deleteMessageById(int message_id) {
        Message message = messageDAO.selectMessageByMesageId(message_id);
        if (message != null) {
            messageDAO.deleteMessageById(message_id);
            return message;
        }
        else
            return null;
    }

    public Message UpdateMessageTextById(int message_id, String message_text) {
        if ((message_text != null) && (message_text.length() > 0) && (message_text.length() <= 255)) {
            Message message = messageDAO.selectMessageByMesageId (message_id);
            messageDAO.UpdateMessageTextById(message_id, message_text);
            message.setMessage_text (message_text);
            return message;
        }
        else
            return null;
    }

    public List<Message> getAllMessagesByUser (int account_id) {
        return messageDAO.selectAllMessagesByUser (account_id);
    }
}
