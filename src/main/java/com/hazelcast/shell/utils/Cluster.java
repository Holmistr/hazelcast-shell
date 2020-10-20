package com.hazelcast.shell.utils;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import picocli.CommandLine;

import java.util.Objects;

import static com.hazelcast.client.HazelcastClient.newHazelcastClient;

@CommandLine.Command(name = "cluster", mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
        description = "Test commands to start cluster")
public class Cluster implements Runnable {

    static HazelcastInstance member = null;
    static HazelcastInstance client = null;

    @Override
    public void run() {
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
            description = "Starts a test cluster")
    public void start() {
        if (Objects.isNull(member)) {
            Config config = new Config();
            config.setProperty("hazelcast.logging.type", "none");
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.setProperty("hazelcast.logging.type", "none");
            member = Hazelcast.newHazelcastInstance(config);
            client = newHazelcastClient(clientConfig);
        }
    }

    @CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {CommandLine.HelpCommand.class},
            description = "Stops a test cluster")
    public void stop() {
        if (!Objects.isNull(member)) {
            member.shutdown();
            client.shutdown();
            member = null;
            client = null;
        }
    }

    public static HazelcastInstance getMember() {
        return member;
    }

    public static HazelcastInstance getClient() {
        return client;
    }
}
