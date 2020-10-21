package com.hazelcast.shell;

import com.hazelcast.shell.context.Use;
import execution.*;
import org.jline.reader.*;
import org.jline.reader.impl.LineReaderImpl;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.PrintWriter;

public class ShellCommands {

    /**
     * Top-level command that just prints help.
     */
    @Command(name = "",
            description = {
                    "Example interactive shell with completion and autosuggestions. " +
                            "Hit @|magenta <TAB>|@ to see available commands.",
                    "Hit @|magenta ALT-S|@ to toggle tailtips.",
                    ""},
            footer = { "", "Press Ctl-D to exit." },
            subcommands = {
                    Use.class,
                    Get.class,
                    Put.class,
                    Poll.class,
                    Offer.class,
                    ExecuteTask.class,
                    ExecuteOnEntry.class,
                    Query.class,
                    EnableDiagnostics.class,
                    CommandLine.HelpCommand.class
            })
    public static class CliCommands implements Runnable {
        public LineReaderImpl reader;
        public PrintWriter out;

        CliCommands() {
        }

        public void setReader(LineReader reader) {
            this.reader = (LineReaderImpl) reader;
            out = reader.getTerminal().writer();
        }

        public void run() {
            out.println(new CommandLine(this).getUsageMessage());
        }
    }

}
