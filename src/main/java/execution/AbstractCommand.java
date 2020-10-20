package execution;

import com.hazelcast.shell.context.Context;
import com.hazelcast.shell.context.Use;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.Objects;

public abstract class AbstractCommand implements Runnable {
    @Override
    public void run() {
        if (type() == null) {
            doRun();
            return;
        } else if (Objects.isNull(Context.name)) {
            out().println(String.format("Select the datastructure first using \"%s %s <%s name>\"",
                    new CommandLine(new Use()).getCommandName(),
                    type(),
                    type()));
            return;
        } else if (type() != Context.type) {
            out().println(String.format("This operation cannot be called on %s. " +
                            "Select the correct datastructure using \"%s %s <%s name>\"",
                    Context.type,
                    new CommandLine(new Use()).getCommandName(),
                    type(),
                    type()));
            return;
        }
        doRun();
    }

    protected abstract PrintWriter out();

    protected abstract void doRun();

    protected abstract Context.Type type();
}
