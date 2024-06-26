package com.portalevent.core.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObjectAPI<T> {

    private boolean isSuccess = false;

    private HttpStatus status;

    private T data;

    private String message;

    public <V> ResponseObjectAPI(T obj, HttpStatus status, String message) {
        processResponseObject(obj);
        this.status = status;
        this.message = message;
    }

    public static <T> ResponseObjectAPI<T> successForward(T obj, String message) {
        ResponseObjectAPI<T> responseObject = new ResponseObjectAPI<>();
        responseObject.setSuccess(true);
        responseObject.setStatus(HttpStatus.OK);
        responseObject.setData(obj);
        responseObject.setMessage(message);
        return responseObject;
    }

    public static <T> ResponseObjectAPI<T> errorForward(String message, HttpStatus status) {
        ResponseObjectAPI<T> responseObject = new ResponseObjectAPI<>();
        responseObject.setSuccess(false);
        responseObject.setStatus(status);
        responseObject.setMessage(message);
        return responseObject;
    }

    public void processResponseObject(T obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
        }
    }

    public ResponseObjectAPI<T> success(T obj, String message) {
        processResponseObject(obj);
        this.status = HttpStatus.OK;
        this.message = message;
        return this;
    }

    public ResponseObjectAPI<T> success(String message) {
        this.isSuccess = true;
        this.status = HttpStatus.OK;
        this.message = message;
        return this;
    }

    public ResponseObjectAPI<T> error(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        return this;
    }

}

