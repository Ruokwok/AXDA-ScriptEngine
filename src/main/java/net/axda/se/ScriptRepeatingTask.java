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

    public ScriptRepeatingTask(Value value, int interval) {
        super();
        this.value = value;
        this.interval = interval;
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
                    value.executeVoid();
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
