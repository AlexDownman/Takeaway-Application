package DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;

public class DBOperationException extends Exception {
    private final Exception cause;

    public DBOperationException(SQLException exception) {
        super("Database Error Occurred: " + exception.getMessage());
        this.cause = exception;
    }

    public DBOperationException(IOException exception) {
        super("I/O Error Occurred: " + exception.getMessage());
        this.cause = exception;
    }

    /**
     * Constructor for unknown exceptions
     * @param e Some exception
     */
    public DBOperationException(Exception e) {
        super("Exception Occurred: " + e.getMessage());
        this.cause = e;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    /**
     * Checks whether the exception is SQL type
     * @return boolean
     */
    public boolean isSQLException() {
        return this.cause instanceof SQLException;
    }

    /**
     * Checks whether the exception is I/O type
     * @return boolean
     */
    public boolean isIOException() {
        return this.cause instanceof IOException;
    }

    /**
     * returns the full exception message
     * @return String
     */
    public String getDetailedMessage() {
        if (isSQLException()) {
            return "SQL Error" + cause.getMessage();
        } else if (isIOException()) {
            return "IO Error" + cause.getMessage();
        } else {
            return "Unknown Error" + cause.getMessage();
        }
    }
}
