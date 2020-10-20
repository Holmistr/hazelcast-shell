package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;

@CommandLine.Command(name = "put",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "put")
public class Put extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0")
    private String key;

    @CommandLine.Parameters(index = "1")
    private String value;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        Object oldValue = HazelcastShell.getClient().getMap(Context.name).put(key, value);
        parent.out.println(oldValue);
    }

    @Override
    protected Context.Type type() {
        return Context.Type.map;
    }
}