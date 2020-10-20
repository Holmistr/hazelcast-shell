package execution;


import com.hazelcast.core.IExecutorService;
import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import com.hazelcast.shell.execute.ScriptRunnable;
import picocli.CommandLine;

import java.io.PrintWriter;

@CommandLine.Command(name = "execute-task", description = "Execute Kotlin script")
public class ExecuteTask extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "Code to be executed")
    private String code;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
//        ScriptRunnable script = ScriptRunnable.kotlin(
//                "import java.lang.management.ManagementFactory\n" +
//                        "val mxBeans = ManagementFactory.getThreadMXBean()\n" +
//                        "val threadCount = mxBeans.getThreadCount();\n" +
//                        "println(threadCount)"
//        );

        ScriptRunnable script = ScriptRunnable.kotlin(code);

        IExecutorService myExecutor = HazelcastShell.getClient().getExecutorService("myExecutor");
        myExecutor.execute(script);
    }

    @Override
    protected Context.Type type() {
        return null;
    }
}
