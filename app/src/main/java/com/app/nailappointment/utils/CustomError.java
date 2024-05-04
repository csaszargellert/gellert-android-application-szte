package com.app.nailappointment.utils;

public enum CustomError {

    NO_ERROR(false, ""),

    INPUT_IS_EMPTY(true, "Cannot be empty"),

    EMAIL_IS_INVALID(true,"Email is invalid"),

    PASSWORD_IS_SHORT(true,"At least 8 characters long"),

    PASSWORD_UPPERCASE_LETTER(true,"At least 1 uppercase letter"),

    PASSWORD_LOWERCASE_LETTER(true, "At least 1 lowercase letter"),

    PASSWORD_SPECIAL_CHARACTER(true, "At least 1 special character"),

    PASSWORD_NUMBER(true, "At least 1 number"),

    PASSWORDS_DO_NOT_MATCH(true, "Passwords do not match");

    final String message;

    final boolean hasError;

    CustomError(boolean hasError, String message) {
        this.message = message;
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public boolean getHasError() {
        return hasError;
    }
}
