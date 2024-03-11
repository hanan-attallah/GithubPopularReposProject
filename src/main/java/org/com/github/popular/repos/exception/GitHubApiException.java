package org.com.github.popular.repos.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GitHubApiException extends RuntimeException {

    private HttpStatus httpStatus;

    private String errorMessage;

    public GitHubApiException(HttpStatus httpStatus, String errorMessage, Exception exception) {
        super(exception);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "DocumentException [code=" + httpStatus.name() + ", error message=" + errorMessage + "]";
    }
}
