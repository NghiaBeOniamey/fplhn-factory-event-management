package com.portalevent.infrastructure.exeption.rest;/*
package com.portalevent.infrastructure.exeption.rest;

import com.portalevent.infrastructure.exeption.PortalEventExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

*/
/**
 * @author SonPT
 *//*

public abstract class PortalEventExceptionRestHandler<Z extends Exception>
        extends PortalEventExceptionHandler<ResponseEntity<?>,Z> {

    @Override
    protected ResponseEntity<?> wrap(Z ex) {
        return new ResponseEntity<>(wrapApi(ex), HttpStatus.BAD_REQUEST);
    }

    protected abstract Object wrapApi(Z ex);
}*/
