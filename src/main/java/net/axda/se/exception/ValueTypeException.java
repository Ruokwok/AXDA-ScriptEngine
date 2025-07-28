package net.axda.se.exception;

public class ValueTypeException extends RuntimeException {

    private String message;

    public ValueTypeException(Exception e) {
        this.message = e.getMessage();
    }

    public ValueTypeException() {
        this.message = "Value type error";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
