package net.axda.se;

import cn.nukkit.scheduler.Task;
import org.graalvm.polyglot.Value;

public class ScriptScheduleTask extends Task {

    private ScriptEngine engine;
    private Value value;

    public ScriptScheduleTask(ScriptEngine engine, Value value) {
        this.engine = engine;
        this.value = value;
    }

    @Override
    public void onRun(int i) {
        engine.execute(value);
    }

    public void close() throws Exception {
        cancel();
    }
}
