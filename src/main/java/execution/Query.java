package execution;


import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import com.hazelcast.sql.SqlResult;
import picocli.CommandLine;

import java.io.PrintWriter;


@CommandLine.Command(name = "query", description = "Query the maps using SQL")
public class Query extends AbstractCommand {
    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Parameters(index = "0", description = "SQL query")
    private String query;

    @Override
    protected PrintWriter out() {
        return parent.out;
    }

    @Override
    protected void doRun() {
//        try (SqlResult result = HazelcastShell.getClient().getSql().execute(", 30)) {
//            for (SqlRow row : result) {
//                String name = row.getObject(0);
//
//                System.out.println(name);
//            }
//        }
//        Object val = HazelcastShell.getClient().getMap(Context.name).get(key);
//        parent.out.println(val);
    }

    @Override
    protected Context.Type type() {
        return Context.Type.map;
    }
}
