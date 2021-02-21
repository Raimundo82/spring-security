package com.raims.springsecurity.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessage {
    STUDENT_NOT_FOUND_ID("Student with id %d not found"),
    STUDENT_NOT_FOUND_EMAIL("Student with id %s not found"),
    EMAIL_ALREADY_REGISTERED("Email %s already registered"),
    EMAIL_INVALID("Email %s is invalid"),
    PASSWORD_INVALID("Password %s is invalid"),
    STUDENT_IS_NOT_ADULT("Student is under 18 : %d"),
    USER_NOT_FOUND("User with username %s not found"),
    AUTHENTICATION_ERROR("Authentication error"),
    TOKEN_INVALID("Token %s invalid");


    public final String label;

}
