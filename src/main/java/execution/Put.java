package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;

@CommandLine.Command(name = "put", description = "Puts an key/value entry to the map")
public class Put extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Key of the entry")
    private String key;

    @CommandLine.Parameters(index = "1", description = "Value of the entry")
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
