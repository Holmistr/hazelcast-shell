package execution;


import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import com.hazelcast.shell.execute.ScriptEP;
import com.hazelcast.shell.execute.ScriptRunnable;
import picocli.CommandLine;

import java.io.PrintWriter;

@CommandLine.Command(name = "execute-on-entry", description = "Executes a Kotlin script on an entry")
public class ExecuteOnEntry extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Key that the script will be executed on")
    private String key;

    @CommandLine.Parameters(index = "1", description = "Code to be executed")
    private String code;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        EntryProcessor script = ScriptEP.kotlin(code);
        HazelcastShell.getClient().getMap(Context.name).executeOnKey(key, script);
    }

    @Override
    protected Context.Type type() {
        return Context.Type.map;
    }
}
