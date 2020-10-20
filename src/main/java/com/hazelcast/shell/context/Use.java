package com.hazelcast.shell.context;

import picocli.CommandLine;

@CommandLine.Command(name = "use", mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
        description = "Choose datastructure to be used.")
public class Use implements Runnable {

    @CommandLine.Mixin
    private Context context;

    @Override
    public void run() {

    }
}
