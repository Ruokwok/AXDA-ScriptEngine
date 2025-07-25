package net.axda.se.exception;

public class ScriptExecuteException extends Exception {

    private Exception e;
    private String path;

    public ScriptExecuteException(Exception e, String path) {
        super(e.getMessage());
        this.e = e;
        this.path = path;
    }

    public ScriptExecuteException() {
    }

    @Override
    public String getMessage() {
        if (e != null) return "An error occurred while executing this JavaScript plugin: "+ path + "\n" + e.getMessage();
        return super.getMessage();
    }
}
