package net.axda.se.api.function;

import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptAsyncTask;
import org.graalvm.polyglot.Value;

public class SetTimeoutFunction extends Function {

    @Override
    public Object execute(Value... arguments) {
        if (arguments.length != 2) return null;
        int msec = arguments[1].asInt();
        ScriptAsyncTask task = new ScriptAsyncTask(arguments[0], msec, engine, false);
        TaskHandler taskHandler = Server.getInstance().getScheduler()
                .scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
        task.setTaskId(taskHandler.getTaskId());
        engine.putCloseable(task);
        return taskHandler.getTaskId();
    }
}
