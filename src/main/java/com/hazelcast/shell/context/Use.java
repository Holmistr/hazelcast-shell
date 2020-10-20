package com.hazelcast.shell.context;

import com.hazelcast.shell.ShellCommands;
import picocli.CommandLine;

@CommandLine.Command(name = "use", description = "Select data structure to be used")
public class Use implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.ParentCommand
    ShellCommands.CliCommands parent;

    @CommandLine.Mixin
    private Context context;

    @Override
    public void run() {
        parent.out.println(String.format("Using %s %s", Context.type, Context.name));
    }
}
