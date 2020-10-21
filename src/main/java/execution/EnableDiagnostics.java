package execution;


import com.hazelcast.core.IExecutorService;
import com.hazelcast.shell.EnableDiagnosticsTask;
import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

import java.io.PrintWriter;

@CommandLine.Command(name = "enable-diagnostics", description = "Enables diagnostics on the fly")
public class EnableDiagnostics extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Directory where you want to store the diagnostics")
    private String directory;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
        IExecutorService executor = HazelcastShell.getClient().getExecutorService("exec");
        executor.execute(new EnableDiagnosticsTask(directory));

        parent.out.println("OK");
    }

    @Override
    protected Context.Type type() {
        return null;
    }
}
