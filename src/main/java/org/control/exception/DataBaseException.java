package org.control.exception;

import java.sql.SQLException;

public class DataBaseException extends SQLException {
    private String reason = "";

    public DataBaseException(String reason) {
        super(reason);
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }
}
