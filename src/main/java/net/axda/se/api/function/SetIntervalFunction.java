package net.axda.se.api.function;

import cn.nukkit.Server;
import cn.nukkit.scheduler.TaskHandler;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptRepeatingTask;
import org.graalvm.polyglot.Value;

public class SetIntervalFunction extends Function  {

    @Override
    public Object execute(Value... arguments) {
        if (arguments.length != 2) return null;
        int msec = arguments[1].asInt();
        ScriptRepeatingTask task = new ScriptRepeatingTask(arguments[0], msec, engine);
        TaskHandler taskHandler = Server.getInstance().getScheduler()
                .scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
        task.setTaskId(taskHandler.getTaskId());
        engine.putCloseable(task);
        return taskHandler.getTaskId();
    }
}
