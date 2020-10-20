package com.hazelcast.shell.execute;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;

public final class ScriptRunnable implements Runnable, IdentifiedDataSerializable {
    private final String script;

    private ScriptRunnable(String script) {
        this.script = script;
    }

    public static ScriptRunnable kotlin(String script) {
        return new ScriptRunnable(script);
    }

    @Override
    public int getFactoryId() {
        return Constants.FACTORY_ID;
    }

    @Override
    public int getClassId() {
        return Constants.RUNNABLE_CLASS_ID;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(script);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        throw new UnsupportedOperationException("This is a dummy implementation");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("This is a dummy implementation");
    }
}
