package net.axda.se.api.function;

import net.axda.se.ScriptEngine;
import net.axda.se.api.API;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;

public abstract class Function extends API implements ProxyExecutable {

    private Value value;

    public Value getValue() {
        return value;
    }

    @Override
    public API setEngine(ScriptEngine engine) {
        super.setEngine(engine);
        ProxyExecutable function = this::execute;
        this.value = engine.getContext().asValue(function);
        return this;
    }
}
