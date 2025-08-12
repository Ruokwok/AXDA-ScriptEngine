package net.axda.se.api.gui;

import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import org.graalvm.polyglot.HostAccess;

import java.util.List;

public class CustomForm implements Form {

    private FormWindowCustom form = new FormWindowCustom("Title");

    @HostAccess.Export
    public CustomForm setTitle(String title) {
        this.form.setTitle(title);
        return this;
    }

    @HostAccess.Export
    public CustomForm addHeader(String title) {
        this.form.addElement(new ElementHeader(title));
        return this;
    }

    @HostAccess.Export
    public CustomForm addLabel(String title) {
        this.form.addElement(new ElementLabel(title));
        return this;
    }

    @HostAccess.Export
    public CustomForm addDivider() {
        this.form.addElement(new ElementDivider());
        return this;
    }

    @HostAccess.Export
    public CustomForm addInput(String title, String placeholder, String def, String tooltip) {
        ElementInput input = new ElementInput(title, placeholder, def);
        this.form.addElement(input);
        return this;
    }

    @HostAccess.Export
    public CustomForm addInput(String title, String placeholder, String def) {
        this.addInput(title, placeholder, def, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addInput(String title, String placeholder) {
        this.addInput(title, placeholder, "", null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addInput(String title) {
        this.addInput(title, "", "", null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSwitch(String title, boolean def, String tooltip) {
        ElementToggle toggle = new ElementToggle(title, def);
        this.form.addElement(toggle);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSwitch(String title, boolean def) {
        this.addSwitch(title, def, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSwitch(String title) {
        this.addSwitch(title, false, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addDropdown(String title, List<String> items, int def, String tooltip) {
        ElementDropdown dd = new ElementDropdown(title, items);
        this.form.addElement(dd);
        return this;
    }

    @HostAccess.Export
    public CustomForm addDropdown(String title, List<String> items, int def) {
        this.addDropdown(title, items, def, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addDropdown(String title, List<String> items) {
        this.addDropdown(title, items, 0, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSlider(String title, int max, int min, int step, int def, String tooltip) {
        ElementSlider slider = new ElementSlider(title, max, min, step);
        this.form.addElement(slider);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSlider(String title, int max, int min, int step, int def) {
        this.addSlider(title, max, min, step, def, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSlider(String title, int max, int min, int step) {
        this.addSlider(title, max, min, step, 0, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addSlider(String title, int max, int min) {
        this.addSlider(title, max, min, 1, 0, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addStepSlider(String title, List<String> items, int def, String tooltip) {
        ElementStepSlider slider = new ElementStepSlider(title, items, def);
        this.form.addElement(slider);
        return this;
    }

    @HostAccess.Export
    public CustomForm addStepSlider(String title, List<String> items, int def) {
        this.addStepSlider(title, items, def, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm addStepSlider(String title, List<String> items) {
        this.addStepSlider(title, items, 0, null);
        return this;
    }

    @HostAccess.Export
    public CustomForm setSubmitButton(String text) {
        form.setSubmitButtonText(text);
        return this;
    }

    @Override
    public String toJson() {
        return form.getJSONData();
    }

    @Override
    public FormWindow getForm() {
        return form;
    }
}
