package org.com.github.popular.repos.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ErrorCode(int code) {
        this.setCode(code);
    }

    public void setCode(int code) {
        this.code = code;
    }
}
