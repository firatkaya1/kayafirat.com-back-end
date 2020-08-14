package com.firatkaya.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
public class APIError {

    public static class Builder {

        private HttpStatus status;
        private int errorCode;
        private String errorMessage;
        private String path;
        private List<String> errorDetails;

        public Builder httpStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder errorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder message(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder path(String path) {
            this.path = path.substring(path.indexOf("=") + 1, path.indexOf(";"));
            return this;
        }

        public Builder errorDetails(List<String> errors) {
            this.errorDetails = errors;
            return this;
        }

        public APIError build() {
            return new APIError(this);
        }

    }

    private HttpStatus status;
    private int errorCode;
    private String errorMessage;
    private List<String> errorDetails;
    private LocalDateTime timestamp;
    private String path;


    public APIError(Builder build) {
        this.status = build.status;
        this.errorCode = build.errorCode;
        this.errorMessage = build.errorMessage;
        this.timestamp = LocalDateTime.now();
        this.path = build.path;
        this.errorDetails = build.errorDetails;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getPath() {
        return path;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public List<String> getErrorDetails() {
        return errorDetails;
    }


}
