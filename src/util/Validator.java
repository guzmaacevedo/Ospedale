/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author domtr
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validator {

    public static boolean isValidUserId(String id) {
        return id != null && id.matches("\\d{12}") && Long.parseLong(id) > 0;
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$");
    }

    public static boolean isValidDate(String date) {
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) return false;
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidTime(String time) {
        return time != null && time.matches("([01]\\d|2[0-3]):(00|15|30|45)");
    }

    public static boolean isValidLicense(String license) {
        return license != null && license.matches("L-\\d{10} MTL");
    }

    public static boolean isValidOffice(String office) {
        return office != null && office.matches("O-\\d{3}");
    }

    public static boolean passwordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}