package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


@CommandLine.Command(name = "poll",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "poll item from queue")
public class Poll extends AbstractCommand {

    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;


    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        try {
            Object result = HazelcastShell.getClient().getQueue(Context.name).poll(250, TimeUnit.MILLISECONDS);
            parent.out.println(result);
        } catch (InterruptedException e) {
            parent.out.println(e.getMessage());
        }
    }

    @Override
    protected Context.Type type() {
        return Context.Type.queue;
    }
}
