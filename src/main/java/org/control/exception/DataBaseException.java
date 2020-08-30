package org.control.exception;

public class DataBaseException extends Exception {
    private String reason = "";

    public DataBaseException(String reason) {
        super(reason);
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }
}
