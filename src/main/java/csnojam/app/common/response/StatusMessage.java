package csnojam.app.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StatusMessage {
    // 200 OK
    SUCCESS(HttpStatus.OK, "요청을 성공적으로 처리했습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
