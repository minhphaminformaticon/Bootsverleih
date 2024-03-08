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
        System.out.println("Calling action for request: " + req);
        System.out.println(req.session().get("test-cookie"));


        Http.Headers headers = req.getHeaders();
        Optional<String> optReferer = headers.get("Referer");
        if (optReferer.isPresent()) {
            String value = optReferer.get();
            String shortenedValue = value.substring(value.lastIndexOf("/"));
            switch (shortenedValue) {
                case "/drinks":
                    return CompletableFuture.completedFuture(redirect(routes.HomeController.login()));
                default:
                    return delegate.call(req);
            }

        } else {
            return delegate.call(req);
        }
    }
}
