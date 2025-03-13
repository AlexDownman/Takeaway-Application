package Tablehandlers;

/**
 * Represents a customer entity with all relevant fields.
 */
public class Customer {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String postcode;

    public Customer(String name, String phone, String email, String address, String postcode) {
        this.name = name;
        this.phone = Validations.normalizePhoneNumber(phone); // Normalize phone on creation
        this.email = email;
        this.address = address;
        this.postcode = postcode;
    }

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPostcode() { return postcode; }

    // Validate the customer object
    public boolean isValid() {
        return Validations.isValidCustomerName(name) &&
                Validations.isValidPhoneNumber(phone) &&
                Validations.isValidEmail(email) &&
                Validations.isValidAddress(address) &&
                Validations.isValidPostCode(postcode);
    }

    /**
     * Returns a string representation of the customer in a formatted table layout.
     * The table includes column headers and aligns data with fixed widths for readability in the terminal.
     *
     * @return a formatted table string representing the customer
     */
    @Override
    public String toString() {
        // Define column widths
        final int NAME_WIDTH = 20;
        final int PHONE_WIDTH = 15;
        final int EMAIL_WIDTH = 30;
        final int ADDRESS_WIDTH = 35;
        final int POSTCODE_WIDTH = 14;

        // Truncate or pad strings to fit column widths
        String nameField = truncateOrPad(name, NAME_WIDTH);
        String phoneField = truncateOrPad(phone, PHONE_WIDTH);
        String emailField = truncateOrPad(email, EMAIL_WIDTH);
        String addressField = truncateOrPad(address, ADDRESS_WIDTH);
        String postcodeField = truncateOrPad(postcode, POSTCODE_WIDTH);

        // Build the table
        StringBuilder table = new StringBuilder();
        String horizontalLine = "+" + "-".repeat(NAME_WIDTH) + "+" + "-".repeat(PHONE_WIDTH) + "+" +
                "-".repeat(EMAIL_WIDTH) + "+" + "-".repeat(ADDRESS_WIDTH) + "+" +
                "-".repeat(POSTCODE_WIDTH) + "+\n";

        table.append(horizontalLine);
        table.append(String.format("| %-" + NAME_WIDTH + "s| %-" + PHONE_WIDTH + "s| %-" + EMAIL_WIDTH +
                        "s| %-" + ADDRESS_WIDTH + "s| %-" + POSTCODE_WIDTH + "s|\n",
                "Name", "Phone", "Email", "Address", "Postcode"));
        table.append(horizontalLine);
        table.append(String.format("| %-" + NAME_WIDTH + "s| %-" + PHONE_WIDTH + "s| %-" + EMAIL_WIDTH +
                        "s| %-" + ADDRESS_WIDTH + "s| %-" + POSTCODE_WIDTH + "s|\n",
                nameField, phoneField, emailField, addressField, postcodeField));
        table.append(horizontalLine);

        return table.toString();
    }

    /**
     * Truncates a string if it exceeds the specified width or pads it with spaces if shorter.
     *
     * @param value the string to adjust
     * @param width the target width
     * @return the truncated or padded string
     */
    private String truncateOrPad(String value, int width) {
        if (value == null) {
            value = "N/A";
        }
        if (value.length() > width) {
            return value.substring(0, width - 3) + "..."; // Truncate with ellipsis
        }
        return String.format("%-" + width + "s", value); // Left-pad with spaces
    }
}