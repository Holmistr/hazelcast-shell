package com.hazelcast.shell.context;

import picocli.CommandLine;

@CommandLine.Command(name = "use", mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
        description = "choose map and commands to execute")
public class Use implements Runnable {

    @CommandLine.Mixin
    private Context context;

    @Override
    public void run() {

    }
}
