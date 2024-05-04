package com.app.nailappointment.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private Validator() {}

    public static CustomError isEmailValid(String email) {
        if (email.isEmpty()) {
            return CustomError.INPUT_IS_EMPTY;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher m = pattern.matcher(email);

        return m.matches() ? CustomError.NO_ERROR : CustomError.EMAIL_IS_INVALID;
    }

    public static CustomError isUsernameValid(String username) {
        return !username.isEmpty() ? CustomError.NO_ERROR : CustomError.INPUT_IS_EMPTY;
    }

    public static CustomError isPasswordValid(String password) {
        CustomError error;

//        if (password.isEmpty()) {
//            error = CustomError.INPUT_IS_EMPTY;
//        } else if (password.length() < 8) {
//            error = CustomError.PASSWORD_IS_SHORT;
//        } else if (!password.matches("/\\d/g")) {
//            error = CustomError.PASSWORD_NUMBER;
//        } else if (!password.matches("/[A-Z]/g")) {
//            error = CustomError.PASSWORD_UPPERCASE_LETTER;
//        } else if (!password.matches("/[a-z]/g")) {
//            error = CustomError.PASSWORD_LOWERCASE_LETTER;
//        } else if (!password.matches("/[-’/`~!#*$@_%+=.,^]/g")) {
//            error = CustomError.PASSWORD_SPECIAL_CHARACTER;
//        } else {
//            error = CustomError.NO_ERROR;
//        }
        error = CustomError.NO_ERROR;
        return error;
    }

    public static CustomError doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword) ? CustomError.NO_ERROR : CustomError.PASSWORDS_DO_NOT_MATCH;
    }
}
