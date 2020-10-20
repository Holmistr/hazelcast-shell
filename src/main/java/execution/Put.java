package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.context.Context;
import picocli.CommandLine;

@CommandLine.Command(name = "put", mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
        description = "put")
public class Put implements Runnable {

    @CommandLine.Parameters(index = "0")
    private String key;

    @CommandLine.Parameters(index = "1")
    private String value;

    @Override
    public void run() {
        Object oldValue = HazelcastShell.getClient().getMap(Context.name).put(key, value);
        System.out.println(oldValue);
    }
}
