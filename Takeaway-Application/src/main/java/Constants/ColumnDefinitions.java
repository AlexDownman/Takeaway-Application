package Constants;

public enum ColumnDefinitions {
    INT,
    INTEGER,
    TINYINT,
    CHAR,
    CLOB,
    TEXT,
    BLOB,
    REAL,
    DOUBLE,
    FLOAT,
    NUMERIC,
    DECIMAL;

    /**
     * Checks if str is an enum and returns the enum constant
     * @param str : enum string
     * @return ColumnDefinitions enum constant
     */
    public static ColumnDefinitions fromString(String str) {
        try {
            return ColumnDefinitions.valueOf(str.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
