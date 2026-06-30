package Controller;

//import java.sql.Connection;
import io.javalin.Javalin;
import io.javalin.http.Context;

//import Util.ConnectionUtil;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        //Connection conn = ConnectionUtil.getConnection ();
        //ConnectionUtil.resetTestDatabase();

        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterUserHandler);

        return app;
    }

    /**
     * This is an example handler for an ex
     * ample endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterUserHandler (Context ctx) {

    }


    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}