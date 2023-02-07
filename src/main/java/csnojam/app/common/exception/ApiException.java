package csnojam.app.common.exception;

import csnojam.app.common.response.StatusMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final StatusMessage statusMessage;
}
