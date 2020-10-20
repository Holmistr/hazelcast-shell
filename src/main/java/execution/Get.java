package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

@CommandLine.Command(name = "get", mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
        description = "get")
public class Get implements Runnable {

    @CommandLine.Parameters(index = "0")
    private String key;

    @Override
    public void run() {
        Object val = HazelcastShell.getClient().getMap(Context.name).get(key);
        System.out.println(val);
    }
}
