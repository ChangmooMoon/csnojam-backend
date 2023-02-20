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
    EMPTY_EMAIL(BAD_REQUEST, "이메일을 입력해주세요."),
    EMPTY_PASSWORD(BAD_REQUEST, "비밀번호를 입력해주세요"),

    // 404 NOT_FOUND
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),

    // 409 CONFLICT
    INVALID_FIELD(CONFLICT, "중복된 데이터가 존재합니다"),

    // 500 INTERNAL_SERVER_ERROR
    ERROR(INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 에러가 발생했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
