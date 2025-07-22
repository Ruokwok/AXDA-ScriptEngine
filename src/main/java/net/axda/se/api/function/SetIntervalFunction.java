package net.axda.se.api.function;

import cn.nukkit.Server;
import cn.nukkit.scheduler.TaskHandler;
import net.axda.se.AXDAScriptEngine;
import org.graalvm.polyglot.Value;

public class SetIntervalFunction extends Function {

    @Override
    public Object execute(Value... arguments) {
        if (arguments.length != 2) return null;
        int msec = arguments[1].asInt();
        int tick = msec / 50;
        TaskHandler task = Server.getInstance().getScheduler().scheduleRepeatingTask(AXDAScriptEngine.getPlugin(), () -> {
            try {
                arguments[0].executeVoid();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, tick);
        return task.getTaskId();
    }
}
