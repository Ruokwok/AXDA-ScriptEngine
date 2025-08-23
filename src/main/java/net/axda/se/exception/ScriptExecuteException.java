package net.axda.se.exception;

public class ScriptExecuteException extends RuntimeException {

    private String path;

    public ScriptExecuteException(Exception e, String path) {
        super(e);
        this.path = path;
    }

    public ScriptExecuteException() {
    }

//    @Override
//    public String getMessage() {
//        return "An error occurred while executing this JavaScript plugin: "+ path + "\n" + getMessage();
//    }
}
