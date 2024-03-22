package actions;

import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class LogInOrLogOut extends play.mvc.Action.Simple{
    public CompletionStage<Result> call(Http.Request req) {
        return delegate.call(req);
    }
}
