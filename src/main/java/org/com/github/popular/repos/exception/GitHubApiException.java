package org.com.github.popular.repos.exception;

import lombok.Data;

@Data
public class GitHubApiException extends RuntimeException {
    private static final long serialVersionUID = -6783640101664019782L;

    private ErrorCode code;

    private Object data;

    public GitHubApiException(ErrorCode code, Object data) {
        super();
        this.data = data;
        this.code = code;
    }

    public GitHubApiException(ErrorCode code, Object data, Exception exception) {
        super(exception);
        this.data = data;
        this.code = code;
    }

    @Override
    public String toString() {
        return "DocumentException [code=" + code + ", data=" + data.toString() + "]";
    }
}