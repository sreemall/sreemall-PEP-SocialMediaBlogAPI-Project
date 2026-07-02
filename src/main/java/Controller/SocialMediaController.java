package Controller;

//import java.sql.Connection;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

//import Util.ConnectionUtil;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        // Connection conn = ConnectionUtil.getConnection ();
        // ConnectionUtil.resetTestDatabase();

        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post ("/register", this::postAccountHandler);
        app.post ("/login", this::loginHandler);
        app.post ("/messages", this::postMessageHandler);
        app.get ("/messages", this::getAllMessagesHandler);
        app.get ("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete ("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch ("/messages{message_id}", this::updateMessageTextByIdHandler);
        app.get ("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an ex
     * ample endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account newAccount = accountService.addAccount(account);
        if (newAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(newAccount);
        }
    }

    private void loginHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper ();
        Account account = mapper.readValue(ctx.body(), Account.class);

        account = accountService.login (account);
        if (account != null) {
            ctx.json (account);
        }
        else
            ctx.status (400);
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(ctx.body(), Message.class);

        Message newMesage = messageService.addMessage(message);
        if (newMesage == null) {
            ctx.status(400);
        } else {
            ctx.json(newMesage);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler (Context ctx) {
        messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
    }

    private void deleteMessageByIdHandler (Context ctx) {
        Message message = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (message == null)
            ctx.status (200);
        else
            ctx.json (message);
    }

    private void updateMessageTextByIdHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        message = messageService.UpdateMessageTextById(Integer.parseInt(
                                                ctx.pathParam ("message_id")),
                                                message.getMessage_text());
        if (message == null)
            ctx.status (400);
        else
            ctx.json (message);
    }

    private void getAllMessagesByUserHandler (Context ctx) {
        ctx.json (messageService.getAllMessagesByUser (Integer.parseInt(ctx.pathParam ("account_id"))));
    }

    // private void exampleHandler(Context context) {
    // context.json("sample text");
    // }

}