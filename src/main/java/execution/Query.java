package execution;


import com.hazelcast.shell.CommandLineTable;
import com.hazelcast.shell.HazelcastShell;
import com.hazelcast.shell.ShellCommands;
import com.hazelcast.shell.context.Context;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.stream.Collectors;


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
        int i = 0;
        try (SqlResult result = HazelcastShell.getClient().getSql().execute(query)) {
            CommandLineTable table = new CommandLineTable(parent.out);
            //st.setRightAlign(true);//if true then cell text is right aligned
            table.setShowVerticalLines(true);//if false (default) then no vertical lines are shown

            for (SqlRow row : result) {
                if (i == 0) {
                    table.setHeaders(row.getMetadata().getColumns().stream().map(column -> column.getName()).collect(Collectors.<String>toList()).toArray(new String[]{}));
                }


                int columnCount = row.getMetadata().getColumnCount();
                String[] cells = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    cells[j] = row.getObject(j).toString();
                }

                table.addRow(cells);

                i++;
            }

            table.print();
        }

        parent.out.println("Number of results: " + i);
    }

    @Override
    protected Context.Type type() {
        return null;
    }
}
