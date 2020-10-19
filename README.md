# hazelcast-shell

## Prerequisites

GraalVM 20.2.2 together with the extension for building native images:
https://www.graalvm.org/docs/getting-started/#install-graalvm
https://www.graalvm.org/docs/getting-started/#native-images

## How to build

`mvn clean package` to build "normal" JARs. The JAR that includes all the dependencies is `target/hazelcast-shell-1.0-SNAPSHOT-cli.jar`

`mvn clean package -Pnative-image` to build the native image. The created native .sh script is `target/hazelcast-shell`

## How to run

With the normal JAR:
`java -cp target/hazelcast-shell-1.0-SNAPSHOT-cli.jar com.hazelcast.shell.HazelcastShell <commands>` 

Native image:
`./target/hazelcast-shell <commands>`

