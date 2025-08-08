package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;

import java.io.File;

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
