package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;


@CommandLine.Command(name = "get", description = "Get an entry from the map.")
public class Get extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Key of the map entry.")
    private String key;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        Object val = HazelcastShell.getClient().getMap(Context.name).get(key);
        parent.out.println(val);
    }

    @Override
    protected Context.Type type() {
        return Context.Type.map;
    }
}
