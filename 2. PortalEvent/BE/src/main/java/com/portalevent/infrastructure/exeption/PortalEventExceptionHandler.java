package com.portalevent.infrastructure.exeption;//package com.portalevent.infrastructure.exeption;
//
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
///**
// * @author SonPT
// */
//public abstract class PortalEventExceptionHandler<T, Z extends Exception> {
//
//    @ExceptionHandler(Exception.class)
//    public T handle(Z ex) {
//        log(ex);
//        return wrap(ex);
//    }
//
//    protected abstract T wrap(Z ex);
//
//    private void log(Z ex) {
//        System.out.println(ex.getMessage());
//    }
//}
