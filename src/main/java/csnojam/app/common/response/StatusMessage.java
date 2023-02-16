package csnojam.app.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor
public enum StatusMessage {
    // 200 OK
    SUCCESS(OK, "요청을 성공적으로 처리했습니다"),
    VALID_FIELD(OK, "사용가능합니다"),

    // 409 CONFLICT
    INVALID_FIELD(CONFLICT, "중복된 데이터가 존재합니다")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
