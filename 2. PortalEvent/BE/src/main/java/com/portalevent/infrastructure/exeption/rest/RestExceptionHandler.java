package com.portalevent.infrastructure.exeption.rest;//package com.portalevent.infrastructure.exeption.rest;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.Path;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author SonPT
// */
//@RestControllerAdvice
//public final class RestExceptionHandler extends
//        PortalEventExceptionRestHandler<ConstraintViolationException> {
//
//    @ExceptionHandler(RestApiException.class)
//    public ResponseEntity<?> handlerException(RestApiException restApiException) {
//        ApiError apiError = new ApiError(restApiException.getMessage());
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<?> handlerForbiddentException(ForbiddenException forbiddenException) {
//        ApiError apiError = new ApiError(forbiddenException.getMessage());
//        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
//    }
//
//    @Override
//    protected Object wrapApi(ConstraintViolationException ex) {
//        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
//        List<ErrorModel> errors = violations.stream()
//                .map(violation ->
//                        new ErrorModel(getPropertyName(violation.getPropertyPath()), violation.getMessage()))
//                .collect(Collectors.toList());
//        return errors;
//    }
//
//    private String getPropertyName(Path path) {
//        String pathStr = path.toString();
//        String[] comps = pathStr.split("\\.");
//        if (comps.length > 0) {
//            return comps[comps.length - 1];
//        } else {
//            return pathStr;
//        }
//    }
//
//}
