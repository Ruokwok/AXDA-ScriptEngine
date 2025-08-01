package net.axda.se.api.function;

import cn.nukkit.Server;
import org.graalvm.polyglot.Value;

public class LogFunction extends Function {

    @Override
    public Object execute(Value... args) {
        StringBuilder sb = new StringBuilder();
        for (Value arg : args) {
            if (arg.isString()) {
                sb.append(arg.asString());
            } else {
                sb.append(arg);
            }
        }
        Server.getInstance().getLogger().info(sb.toString());
        return null;
    }
}
