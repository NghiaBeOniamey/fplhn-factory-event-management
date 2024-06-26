package com.portalevent.core.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */
@Getter
@Setter
public class ResponseObject {

    private boolean isSuccess = false;
    private Object data;
    private String message;

    public <T> ResponseObject(T obj) {
        processResponseObject(obj);
    }

    public void processResponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
        }
    }
}
