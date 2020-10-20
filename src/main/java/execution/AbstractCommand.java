package execution;

import com.hazelcast.shell.context.Context;
import com.hazelcast.shell.context.Use;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.Objects;

public abstract class AbstractCommand implements Runnable {
    @Override
    public void run() {
        if (type() != Context.type || Objects.isNull(Context.name)) {
            out().println(String.format("Please call <%s> first for <%s> data structure \n%s",
                    new CommandLine(new Use()).getCommandName(),
                    type(),
                    new CommandLine(new Use()).getHelp().fullSynopsis()));
            return;
        }
        doRun();
    }

    protected abstract PrintWriter out();

    protected abstract void doRun();

    protected abstract Context.Type type();
}
