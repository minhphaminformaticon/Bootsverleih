package actions;

import controllers.routes;
import io.ebeaninternal.server.expression.Op;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AdminAction extends Action.Simple{
    public CompletionStage<Result> call(Http.Request request){
        Optional session = request.session().get("admin");
        if (session.isPresent()){
            return delegate.call(request);
        } else {
            return CompletableFuture.completedFuture(redirect(routes.HomeController.adminLogin()));
        }
    }
}
