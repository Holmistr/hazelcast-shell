package execution;


import com.hazelcast.shell.context.Context;
import com.hazelcast.shell.utils.Cluster;
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
        Cluster.getClient().getMap(Context.name).put(key, value);
        System.out.println(Cluster.getClient().getMap(Context.name).get(key));
    }
}
