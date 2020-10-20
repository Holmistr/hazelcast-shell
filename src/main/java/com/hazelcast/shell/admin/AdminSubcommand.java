/*
 * Copyright 2020 Hazelcast Inc.
 *
 * Licensed under the Hazelcast Community License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://hazelcast.com/hazelcast-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.hazelcast.shell.admin;

import com.hazelcast.shell.AbstractCommandLine;
import com.hazelcast.shell.VersionProvider;
import picocli.CommandLine;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Commandline class for Hazelcast Management Center operations
 */
@CommandLine.Command(name = "admin", description = "Utility for managing Hazelcast cluster.",
        versionProvider = VersionProvider.class, mixinStandardHelpOptions = true, sortOptions = false)
public class AdminSubcommand extends AbstractCommandLine {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    public AdminSubcommand(PrintStream out, PrintStream err) throws IOException {
        super(out, err);
    }

    @Override
    public void run() {
        List<CommandLine> parsed = spec.commandLine().getParseResult().asCommandLineList();
        if (parsed != null && parsed.size() == 1) {
            spec.commandLine().usage(out);
        }
    }

    @CommandLine.Command(name = "enable-diagnostics", description = "Enables diagnostics on all members", mixinStandardHelpOptions = true,
            sortOptions = false)
    void enableDiagnostics() throws IOException, InterruptedException {
        out.println("Executing enable-diagnostics command");
    }

    @CommandLine.Command(name = "disable-diagnostics", description = "Disables diagnostics on all members", mixinStandardHelpOptions = true,
            sortOptions = false)
    void disableDiagnostics() throws IOException, InterruptedException {
        out.println("Executing disable-diagnostics command");
    }
}
