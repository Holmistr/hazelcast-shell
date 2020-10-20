package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;


@CommandLine.Command(name = "offer", description = "Puts an item to the queue")
public class Offer extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Value to be put")
    private String value;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        boolean result = HazelcastShell.getClient().getQueue(Context.name).offer(value);
        parent.out.println(result);
    }

    @Override
    protected Context.Type type() {
        return Context.Type.queue;
    }
}
