package com.foundationProject.bookstore.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(404, "User does not exist", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(401, "User is not authenticated", HttpStatus.UNAUTHORIZED),
    USER_LOGGED_OUT(401, "User is logged out", HttpStatus.UNAUTHORIZED),
    USER_TOKEN_INCORRECT(403, "User token is incorrect", HttpStatus.FORBIDDEN),

    ORDER_NOT_EXISTED(404, "Order does not exist", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_EXISTED(404, "Category does not exist", HttpStatus.NOT_FOUND),




    EMAIL_EXISTED(400, "Email already exists", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(404, "Email not found", HttpStatus.NOT_FOUND),

   PHONE_EXISTED(400, "Phone already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(400, "Category already exists", HttpStatus.BAD_REQUEST),
    USER_EMAIL_OR_PHONE_CAN_NOT_CHANGE(400, "User email or phone can not change", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(400, "Token expired", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST),
    User_NAME_CAN_NOT_BE_CHANGED(400, "User name can not be changed", HttpStatus.BAD_REQUEST),
    BOOK_EXISTED(400, "Book already exists", HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus status;
}
