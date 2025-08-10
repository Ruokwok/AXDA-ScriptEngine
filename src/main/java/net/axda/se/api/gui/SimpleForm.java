package net.axda.se.api.gui;

import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowSimple;
import org.graalvm.polyglot.HostAccess;

public class SimpleForm implements Form {

    private FormWindowSimple form = new FormWindowSimple("Title", "Content");

    @HostAccess.Export
    public SimpleForm setTitle(String title) {
        this.form.setTitle(title);
        return this;
    }

    @HostAccess.Export
    public SimpleForm setContent(String content) {
        this.form.setContent(content);
        return this;
    }

    @HostAccess.Export
    public SimpleForm addButton(String text, String image) {
        String type = null;
        if (image.startsWith("http://") || image.startsWith("https://")) {
            type = ElementButtonImageData.IMAGE_DATA_TYPE_URL;
        } else {
            type = ElementButtonImageData.IMAGE_DATA_TYPE_PATH;
        }
        ElementButtonImageData imageData = new ElementButtonImageData(type, image);
        this.form.addButton(new ElementButton(text, imageData));
        return this;
    }

    @HostAccess.Export
    public SimpleForm addButton(String text) {
        this.form.addButton(new ElementButton(text));
        return this;
    }

    @HostAccess.Export
    public SimpleForm addHeader(String text) {
        this.form.addElement(new ElementHeader(text));
        return this;
    }

    @HostAccess.Export
    public SimpleForm addLabel(String text) {
        this.form.addElement(new ElementLabel(text));
        return this;
    }

    @HostAccess.Export
    public SimpleForm addDivider() {
        this.form.addElement(new ElementDivider());
        return this;
    }

    @Override
    public FormWindowSimple getForm() {
        return this.form;
    }

    @Override
    public String toJson() {
        return this.form.getJSONData();
    }

}
