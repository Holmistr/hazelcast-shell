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

package com.hazelcast.shell;

import java.io.PrintStream;

/**
 * Abstract command line class. The methods and properties in this class are shared in other commandline implementations.
 */
public abstract class AbstractCommandLine implements Runnable {

    protected final PrintStream out;
    protected final PrintStream err;

    public AbstractCommandLine(PrintStream out, PrintStream err) {
        this.out = out;
        this.err = err;
    }
}
