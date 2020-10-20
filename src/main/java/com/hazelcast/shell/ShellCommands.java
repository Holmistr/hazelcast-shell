package com.hazelcast.shell;

import com.hazelcast.shell.context.Use;
import execution.Get;
import execution.Offer;
import execution.Put;
import execution.Take;
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
    @Command(name = "cmd",
            description = {
                    "Example interactive shell with completion and autosuggestions. " +
                            "Hit @|magenta <TAB>|@ to see available commands.",
                    "Hit @|magenta ALT-S|@ to toggle tailtips.",
                    ""},
            footer = {"", "Press Ctl-D to exit."},
            subcommands = {Use.class, Get.class, Put.class, Take.class, Offer.class, CommandLine.HelpCommand.class})
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

    private static Path workDir() {
        return Paths.get(System.getProperty("user.dir"));
    }


    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        try {
            // set up JLine built-in commands
            Set<Builtins.Command> onlyHistory = new HashSet<>();
            onlyHistory.add(Builtins.Command.HISTORY);
            Builtins builtins = new Builtins(onlyHistory, workDir(), null, null);
//            builtins.rename(Builtins.Command.TTOP, "top");
//            builtins.alias("zle", "widget");
//            builtins.alias("bindkey", "keymap");

            // set up picocli commands
            ShellCommands.CliCommands commands = new ShellCommands.CliCommands();
            CommandLine cmd = new CommandLine(commands);
            PicocliCommands picocliCommands = new PicocliCommands(ShellCommands::workDir, cmd);

            Parser parser = new DefaultParser();
            try (Terminal terminal = TerminalBuilder.builder().build()) {
                SystemRegistry systemRegistry = new SystemRegistryImpl(parser, terminal, ShellCommands::workDir, null);
                systemRegistry.setCommandRegistries(builtins, picocliCommands);

                LineReader reader = LineReaderBuilder.builder()
                        .terminal(terminal)
                        .completer(systemRegistry.completer())
                        .parser(parser)
                        .variable(LineReader.LIST_MAX, 50)   // max tab completion candidates
                        .build();
                builtins.setLineReader(reader);
                commands.setReader(reader);
                new TailTipWidgets(reader, systemRegistry::commandDescription, 5, TailTipWidgets.TipType.COMPLETER);
                KeyMap<Binding> keyMap = reader.getKeyMaps().get("main");
                keyMap.bind(new Reference("tailtip-toggle"), KeyMap.alt("s"));

                String prompt = "prompt> ";
                String rightPrompt = null;

                // start the shell and process input until the user quits with Ctrl-D
                String line;
                while (true) {
                    try {
                        systemRegistry.cleanUp();
                        line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
                        systemRegistry.execute(line);
                    } catch (UserInterruptException e) {
                        // Ignore
                    } catch (EndOfFileException e) {
                        return;
                    } catch (Exception e) {
                        systemRegistry.trace(e);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            AnsiConsole.systemUninstall();
        }
    }
}
