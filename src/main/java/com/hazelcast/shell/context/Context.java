package com.hazelcast.shell.context;

import picocli.CommandLine;

public class Context {
    public enum Type {map, queue}

    @CommandLine.Parameters(index = "0", arity = "1", description = "Enum values: ${COMPLETION-CANDIDATES}")
    public static Type type;

    @CommandLine.Parameters(index = "1", arity = "1", description = "Name of the data structure")
    public static String name;

}
