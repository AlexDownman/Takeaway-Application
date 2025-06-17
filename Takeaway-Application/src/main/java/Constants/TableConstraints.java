package Constants;

public enum TableConstraints {
    PRIMARY_KEY,
    NOT_NULL,
    UNIQUE,
    CHECK,
    FOREIGN_KEY,
    DEFAULT;

    /**
     * Checks whether str is an enum and returns the strings enum equivalent
     * @param str : enum string
     * @return TableConstraints enum constant
     */
    public static TableConstraints fromString(String str) {
        try {
            return TableConstraints.valueOf(str.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Invalid constraint
        }
    }

}
