package DatabaseManager;

import java.sql.SQLException;

public class DatabaseConnection extends SQLException {
    public DatabaseConnection(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConnection(String message) {
        super(message);
    }
}
