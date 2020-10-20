package com.hazelcast.shell;

import com.hazelcast.shell.context.Use;
import execution.*;
import org.fusesource.jansi.AnsiConsole;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.TailTipWidgets;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.shell.jline3.PicocliCommands;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

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
                    Execute.class,
                    Query.class,
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
