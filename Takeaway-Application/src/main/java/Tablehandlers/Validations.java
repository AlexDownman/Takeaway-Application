package Tablehandlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class providing validation and normalization methods for common data types
 * such as names, email addresses, phone numbers, addresses, and postal codes.
 */
public class Validations {

    /**
     * Validates a customer name to ensure it contains only alphanumeric characters
     * and meets length requirements.
     *
     * @param name the customer name to validate
     * @return {@code true} if the name is non-empty, <= 50 characters, and contains only
     *         alphanumeric characters; {@code false} otherwise
     */
    public static boolean isValidCustomerName(String name) {
        if (name.trim().isEmpty() || name.length() > 50) {
            return false;
        }

        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("I am a string");
        boolean b = m.find();

        if (b) {
            System.out.println("There is a special character in my string");
            return false;
        }
        return true;
    }

    /**
     * Validates an email address based on basic length constraints.
     * <p>
     * Note: This is a basic check and does not verify email format compliance
     * with RFC 5322 or similar standards.
     *
     * @param email the email address to validate
     * @return {@code true} if the email is non-empty and <= 255 characters;
     *         {@code false} otherwise
     */
    public static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() && email.length() <= 255;
    }

    /**
     * Normalizes a UK phone number to the "07..." format by removing formatting
     * characters and converting "+44" prefixes to "0".
     *
     * @param phoneNumber the phone number to normalize
     * @return the normalized phone number starting with "07" if valid input;
     *         {@code null} if the input is null or empty
     */
    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        String cleanedPhone = phoneNumber.replaceAll("[\\s()-]", "");
        if (cleanedPhone.startsWith("+44")) {
            cleanedPhone = "0" + cleanedPhone.substring(3);
        }
        return cleanedPhone;
    }

    /**
     * Validates a UK mobile phone number, accepting both "07..." and "+44..."
     * formats. The number must consist of 11 digits starting with "07" after normalization.
     *
     * @param phoneNumber the phone number to validate
     * @return {@code true} if the phone number is a valid UK mobile number;
     *         {@code false} otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String normalized = normalizePhoneNumber(phoneNumber);
        if (normalized == null) {
            return false;
        }
        String ukMobilePattern = "^07[0-9]{9}$";
        return normalized.matches(ukMobilePattern);
    }

    /**
     * Validates an address to ensure it contains only allowed characters
     * (letters, numbers, spaces, and specific punctuation).
     *
     * @param address the address to validate
     * @return {@code true} if the address matches the allowed pattern;
     *         {@code false} otherwise
     */
    public static boolean isValidAddress(String address) {
        // Basic address validation regex
        String addressPattern = "^[a-zA-Z0-9\\s.,#-]+$";

        Pattern pattern = Pattern.compile(addressPattern);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    /**
     * Validates a postal code, supporting both US ZIP (5 digits or 5+4) and
     * UK postcode formats.
     *
     * @param postCode the postal code to validate
     * @return {@code true} if the postal code matches either US ZIP or UK format;
     *         {@code false} otherwise
     */
    public static boolean isValidPostCode(String postCode) {
        // Regex for US ZIP (5 digits or 5+4) and UK postcode formats
        String usZipPattern = "^\\d{5}(-\\d{4})?$";
        String ukPostcodePattern = "^[A-Z]{1,2}[0-9][0-9A-Z]?\\s?[0-9][A-Z]{2}$";

        if (postCode.matches(usZipPattern)) {
            Pattern p = Pattern.compile(usZipPattern);
            Matcher m = p.matcher(postCode);
            return m.matches();
        }

        if (postCode.matches(ukPostcodePattern)) {
            Pattern p = Pattern.compile(ukPostcodePattern);
            Matcher m = p.matcher(postCode);
            return m.matches();
        }
        return false;
    }

    /**
     * Checks if an object is null.
     *
     * @param obj the object to check
     * @return {@code true} if the object is null; {@code false} otherwise
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Main method for testing validation methods.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        /*String name = "12412";
        System.out.println(isValidCustomerName(name));

        String phone = "dsfsdf";
        System.out.println(isValidPhoneNumber(phone));

        String phone2 = "+447398890900";
        System.out.println(isValidPhoneNumber(phone2));
        String normalised = normalizePhoneNumber(phone2);
        System.out.println(normalised);*/
    }
}
