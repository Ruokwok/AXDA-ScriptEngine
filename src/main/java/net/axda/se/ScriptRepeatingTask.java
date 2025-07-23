package net.axda.se;

import cn.nukkit.scheduler.AsyncTask;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
import java.util.Timer;
import java.util.TimerTask;

public class ScriptRepeatingTask extends AsyncTask implements Closeable {

    private Value value;
    private int interval;
    private Timer timer;
    private ScriptEngine engine;

    public ScriptRepeatingTask(Value value, int interval, ScriptEngine engine) {
        super();
        this.value = value;
        this.interval = interval;
        this.engine = engine;
        this.timer = new Timer();
    }

    @Override
    public void onRun() {
        if (value == null) return;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread().setName("jst-" + getTaskId());
                try {
                    if (value.isString()) {
                        engine.getContext().eval("js", value.asString());
                    } else {
                        value.executeVoid();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, interval);
    }

    @Override
    public void close() {
        timer.cancel();
    }
}
