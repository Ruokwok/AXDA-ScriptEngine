package net.axda.se.exception;

public class ScriptExecuteException extends RuntimeException {

    public ScriptExecuteException(Exception e) {
        super(e);
    }

    public ScriptExecuteException() {
    }

//    @Override
//    public String getMessage() {
//        return "An error occurred while executing this JavaScript plugin: "+ path + "\n" + getMessage();
//    }
}
