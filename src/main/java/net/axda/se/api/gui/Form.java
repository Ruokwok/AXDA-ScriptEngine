package net.axda.se.api.gui;

import cn.nukkit.form.window.FormWindow;

public interface Form {

    abstract String toJson();

    abstract FormWindow getForm();

}
