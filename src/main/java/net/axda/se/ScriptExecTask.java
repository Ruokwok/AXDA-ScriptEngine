package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;

import java.io.File;

/**
 * 此类原为异步加载脚本设计，后来更改为在主线程加载脚本，仍继续使用此类。
 * @author Ruok
 */
public class ScriptExecTask extends AsyncTask {

    private final int threadId;
    private final String script;
    private final File file;
    private ScriptEngine engine;

    public ScriptExecTask(int threadId, String script, File file) {
        this.engine = new ScriptEngine(script, file, this);
        this.threadId = threadId;
        this.script = script;
        this.file = file;
    }

    public ScriptEngine getEngine() {
        return this.engine;
    }

    @Override
    public void onRun() {
        try {
            ScriptLoader.getInstance().putEngine(this.engine);
            this.engine.execute();
        } catch (Exception e) {
            Server.getInstance().getLogger().logException(e);
            Server.getInstance().getLogger().error("JavaScript plugin failed to load: " + this.engine.getDescription().getFile().getName());
            ScriptLoader.getInstance().disablePlugin(engine);
        }
    }
}
