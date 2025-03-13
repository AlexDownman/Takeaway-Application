package Tablehandlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

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

    public static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() && email.length() <= 255;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Remove spaces, hyphens, and parentheses for consistent validation
        String cleanedPhone = phoneNumber.replaceAll("[\\s()-]", "");

        // UK phone regex pattern
        String ukPhonePattern = "^(?:\\+44|0)(?:7[0-9]{9}|1[0-9]{9}|2[0-9]{9}|3[0-9]{9}|8[0-9]{9})$";

        if (cleanedPhone.matches(ukPhonePattern)) {
            return true;
        }

        Pattern pattern = Pattern.compile(ukPhonePattern);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isValidAddress(String address) {
        // Basic address validation regex
        String addressPattern = "^[a-zA-Z0-9\\s.,#-]+$";

        Pattern pattern = Pattern.compile(addressPattern);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

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

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static void main(String[] args) {
        String name = "12412";
        System.out.println(isValidCustomerName(name));

        String phone = "dsfsdf";
        System.out.println(isValidPhoneNumber(phone));

        String phone2 = "07398 890 9000";
        System.out.println(isValidPhoneNumber(phone2));

    }


}
