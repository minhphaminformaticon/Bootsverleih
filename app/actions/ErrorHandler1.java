package actions;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.typesafe.config.Config;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.http.HttpErrorHandler;
import play.mvc.*;
import play.mvc.Http.*;

@Singleton
public class ErrorHandler1 extends DefaultHttpErrorHandler  {
    @Inject
    public ErrorHandler1(Config config, Environment environment, OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(config, environment, sourceMapper, routes);
    }

    protected CompletionStage<Result> onForbidden(RequestHeader request, String message) {
        System.out.println("test forbidden");
        String test = "";

        return CompletableFuture.completedFuture(
                Results.forbidden("You're not allowed to access this resource."));
    }

    @Override
    protected CompletionStage<Result> onNotFound(RequestHeader request, String message) {
        System.out.println("test not found");
        return super.onNotFound(request, message);
    }

    @Override
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return super.onClientError(request, statusCode, message);
    }

    @Override
    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        return super.onServerError(request, exception);
    }
}