package com.hazelcast.shell.execute;

import com.hazelcast.map.EntryProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.util.Map;

public class ScriptEP<K, V, R> implements EntryProcessor<K, V, R>, IdentifiedDataSerializable {
    private final String script;
    private ScriptEP(String script) {
        this.script = script;
    }
    public static <K, V, R> EntryProcessor<K, V, R> kotlin(String script) {
        return new ScriptEP<>(script);
    }
    @Override
    public int getFactoryId() {
        return Constants.FACTORY_ID;
    }
    @Override
    public int getClassId() {
        return Constants.EP_CLASS_ID;
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
    public R process(Map.Entry<K, V> entry) {
        throw new UnsupportedOperationException("This is a dummy implementation");
    }
}
