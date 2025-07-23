package net.axda.se.api.function;

import cn.nukkit.Server;
import org.graalvm.polyglot.Value;

public class ClearIntervalFunction extends Function {

    @Override
    public Object execute(Value... arguments) {
        if (arguments.length == 0) return false;
        try {
            int id = arguments[0].asInt();
            Server.getInstance().getScheduler().cancelTask(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
