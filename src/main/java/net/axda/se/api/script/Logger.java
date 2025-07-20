package net.axda.se.api.script;

import cn.nukkit.Server;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;

public class Logger extends API {

    private String title;
    private int logLevel;
    private cn.nukkit.utils.Logger logger = Server.getInstance().getLogger();

    @HostAccess.Export
    public void log(String... data) {
        StringBuilder sb = getStringBuilder();
        for (String s : data) {
            sb.append(s);
        }
        logger.info(sb.toString());
    }

    @HostAccess.Export
    public void setTitle(String title) {
        this.title = title;
    }

    @HostAccess.Export
    public void setLogLevel(int level) {
        this.logLevel = level;
    }

    private StringBuilder getStringBuilder() {
        StringBuilder builder = new StringBuilder();
        if (title != null) {
            builder.append("[").append(title).append("] ");
        }
        return builder;
    }

}
