package net.axda.se;

import cn.nukkit.scheduler.AsyncTask;

import java.io.File;

public class ScriptExecTask extends AsyncTask {

    private final int threadId;
    private final String script;
    private final File file;
    private ScriptEngine engine;

    public ScriptExecTask(int threadId, String script, File file) {
        this.threadId = threadId;
        this.script = script;
        this.file = file;
    }

    @Override
    public void onRun() {
        this.engine = new ScriptEngine(script, file, threadId, this);
        ScriptLoader.getInstance().putEngine(this.engine);
        this.engine.execute();
    }
}
