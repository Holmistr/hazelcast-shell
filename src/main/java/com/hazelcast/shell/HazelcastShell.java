package com.hazelcast.shell;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.shell.admin.AdminSubcommand;
import org.fusesource.jansi.AnsiConsole;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.TailTipWidgets;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.shell.jline3.PicocliCommands;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hazelcast.client.HazelcastClient.newHazelcastClient;

/**
 * Main command class for Hazelcast operations
 */
@Command(name = "hz-cli", description = "HZ-CLI", versionProvider = VersionProvider.class, mixinStandardHelpOptions = true, sortOptions = false)
public class HazelcastShell extends AbstractCommandLine {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    private static HazelcastInstance client;

    HazelcastShell(PrintStream out, PrintStream err, HazelcastInstance client) {
        super(out, err);
    }

    public static void main(String[] args) throws IOException {
        getClient();

        runCommandLine(args, client);

        if (client != null) {
            client.shutdown();
        }
    }

    public static HazelcastInstance getClient() {
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.setProperty("hazelcast.logging.type", "none");
            client = newHazelcastClient(clientConfig);
        }

        return client;
    }

    @Override
    public void run() {
        out.println("Executing run!");
    }

    private static void runCommandLine(String[] args, HazelcastInstance client) throws IOException {
        PrintStream out = System.out;
        PrintStream err = System.err;

        CommandLine cmd = new CommandLine(new HazelcastShell(out, err, client))
                .addSubcommand("admin", new AdminSubcommand(out, err))
                .setOut(createPrintWriter(out))
                .setErr(createPrintWriter(err))
                .setTrimQuotes(true)
                .setExecutionExceptionHandler(new ExceptionHandler());
        cmd.execute(args);
    }

    @Command(name = "connect", description = "Default command", mixinStandardHelpOptions = true, sortOptions = false)
    void connect() throws IOException, InterruptedException {

        AnsiConsole.systemInstall();
        try {
            // set up JLine built-in commands
            Builtins builtins = new Builtins(workDir(), null, null);
            builtins.rename(Builtins.Command.TTOP, "top");
            builtins.alias("zle", "widget");
            builtins.alias("bindkey", "keymap");

            // set up picocli commands
            ShellCommands.CliCommands commands = new ShellCommands.CliCommands();
            CommandLine cmd = new CommandLine(commands);
            PicocliCommands picocliCommands = new PicocliCommands(HazelcastShell::workDir, cmd);

            Parser parser = new DefaultParser();
            try (Terminal terminal = TerminalBuilder.builder().build()) {
                SystemRegistry systemRegistry = new SystemRegistryImpl(parser, terminal, HazelcastShell::workDir, null);
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

    private static PrintWriter createPrintWriter(PrintStream printStream) {
        return new PrintWriter(new OutputStreamWriter(printStream, StandardCharsets.UTF_8));
    }

    private static Path workDir() {
        return Paths.get(System.getProperty("user.dir"));
    }

}