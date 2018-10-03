package httpserver.server;

import java.util.concurrent.Executor;

public class CurrentThreadExecutor implements Executor {

    @Override
    public void execute(Runnable clientThread) {
        clientThread.run();
    }
}
