package csnojam.app.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum StatusMessage {
    // 200 OK
    SUCCESS(OK, "요청을 성공적으로 처리했습니다"),
    VALID_FIELD(OK, "사용가능합니다"),

    // 400 Error
    LOGIN_FAIL(BAD_REQUEST, "로그인에 실패했습니다."),
    WRONG_PASSWORD(BAD_REQUEST, "잘못된 패스워드입니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),

    // 409 CONFLICT
    INVALID_FIELD(CONFLICT, "중복된 데이터가 존재합니다"),

    // 422 UNPROCESSABLE_ENTITY
    NOT_VALID_PARAMETER(UNPROCESSABLE_ENTITY, "입력된 매개변수가 비어있거나 정확하지 않습니다"),

    // 423 LOCKED
    EMAIL_LOCKED(LOCKED, "이미 가입된 이메일입니다."),
    NICKNAME_LOCKED(LOCKED, "이미 존재하는 닉네임입니다."),

    // 500 INTERNAL_SERVER_ERROR
    ERROR(INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 에러가 발생했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
