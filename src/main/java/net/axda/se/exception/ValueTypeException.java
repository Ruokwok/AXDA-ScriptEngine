package net.axda.se.exception;

public class ValueTypeException extends RuntimeException {

    private String message;

    public ValueTypeException(String message) {
        this.message = message;
    }

    public ValueTypeException() {
        this.message = "Value type error";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
