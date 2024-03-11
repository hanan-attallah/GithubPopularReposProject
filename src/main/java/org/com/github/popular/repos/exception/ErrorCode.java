package org.com.github.popular.repos.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    BAD_REQUEST(400);

    private int code;

    ErrorCode(int code) {
        this.setCode(code);
    }

    public void setCode(int code) {
        this.code = code;
    }
}
