package net.axda.se.api.function;

import cn.nukkit.Server;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptAsyncTask;
import net.axda.se.ScriptScheduleTask;
import net.axda.se.api.API;
import org.graalvm.polyglot.Value;

public class SetTimeoutFunction extends Function {

    @Override
    public Object execute(Value... args) {
        if (args.length != 2) return null;
        int tick = API.msToTick(args[1].asInt());
//        ScriptAsyncTask task = new ScriptAsyncTask(arguments[0], msec, engine, false);
//        TaskHandler taskHandler = Server.getInstance().getScheduler()
//                .scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
//        task.setTaskId(taskHandler.getTaskId());
//        engine.putCloseable(task);
//        return taskHandler.getTaskId();
        ScriptScheduleTask task = new ScriptScheduleTask(engine, args[0]);
        ServerScheduler scheduler = Server.getInstance().getScheduler();
        TaskHandler handler = scheduler.scheduleDelayedTask(AXDAScriptEngine.getPlugin(), task, tick);
        engine.putTask(task);
        return handler.getTaskId();
    }
}
