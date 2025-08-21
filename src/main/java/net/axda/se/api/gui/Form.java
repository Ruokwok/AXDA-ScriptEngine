package net.axda.se.api.gui;

import cn.nukkit.form.window.FormWindow;
import org.graalvm.polyglot.Value;

public abstract class Form {

    Value callback = null;

    public abstract String toJson();

    public abstract FormWindow getForm();

    public void setCallback(Value value) {
        this.callback = value;
    }

    public Value getCallback() {
        return callback;
    }

}
