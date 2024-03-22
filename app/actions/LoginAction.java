package actions;

import controllers.HomeController;
import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class LoginAction extends play.mvc.Action.Simple {
    public CompletionStage<Result> call(Http.Request req) {
        System.out.println("Calling action for request: " + req.toString());
        System.out.println(req.session().get("logged"));
        Optional session = req.session().get("logged");

        if(session.isPresent()){
            return delegate.call(req);
        } else {
            return CompletableFuture.completedFuture(redirect(routes.HomeController.login()).addingToSession(req, "desiredSite", req.toString()));
        }
    }
}
