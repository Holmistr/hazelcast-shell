package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;


@CommandLine.Command(name = "take",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "take item to queue")
public class Take extends AbstractCommand {

    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;


    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        try {
            Object result = HazelcastShell.getClient().getQueue(Context.name).take();
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
