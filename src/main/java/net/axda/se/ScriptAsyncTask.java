package net.axda.se;

import cn.nukkit.scheduler.AsyncTask;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
import java.util.Timer;
import java.util.TimerTask;

public class ScriptAsyncTask extends AsyncTask implements Closeable {

    private Value value;
    private int interval;
    private Timer timer;
    private ScriptEngine engine;
    private boolean type;

    /**
     * @param value
     * @param interval
     * @param engine
     * @param type true 为循环任务，false 为延时任务
     */
    public ScriptAsyncTask(Value value, int interval, ScriptEngine engine, boolean type) {
        super();
        this.value = value;
        this.interval = interval;
        this.engine = engine;
        this.type = type;
        this.timer = new Timer();
    }

    @Override
    public void onRun() {
        if (value == null) return;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
//                Thread.currentThread().setName(engine.getThreadName() + "/" + getTaskId());
                Thread.currentThread().setName("js-" + getTaskId());
                try {
                    engine.getContext().enter();
                    if (value.isString()) {
                        engine.getContext().eval("js", value.asString());
                    } else {
                        value.executeVoid();
                    }
                    engine.getContext().leave();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        };
        if (type) {
            timer.schedule(task, 0, interval);
        } else {
            timer.schedule(task, interval);
        }
    }

    @Override
    public void close() {
        timer.cancel();
    }
}
