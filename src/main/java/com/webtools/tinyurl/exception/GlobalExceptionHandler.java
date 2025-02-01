package com.webtools.tinyurl.exception;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.webtools.tinyurl.dto.MessageRunnerDTO;
import com.webtools.tinyurl.config.EnvironmentContext;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private EnvironmentContext environmentContext;

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * global error logger and stack trace printer which will
     * print stack trace only if the application is running on dev mode.
     */
    private void logError(Exception exception) {

        if(environmentContext.isInDevMode()) {
            exception.printStackTrace(System.out);
        }
        log.error(exception.getMessage());
    }

    @ExceptionHandler(CannotCreateUrlException.class)
    public ResponseEntity<MessageRunnerDTO> handleCannotCreateUrlException(CannotCreateUrlException cannotCreateUrlException) {
        logError(cannotCreateUrlException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(cannotCreateUrlException.getMessage()), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<MessageRunnerDTO> handleUrlNotFoundException(UrlNotFoundException urlNotFoundException) {
        logError(urlNotFoundException);
        return new ResponseEntity<MessageRunnerDTO>(new MessaeRunnerDTO(urlNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntryAlreadyExistsException.class)
    public ResponseEntity<MessageRunnerDTO> handleEntryAlreadyExistsException(EntryAlreadyExistsException entryAlreadyExistsException) {
        logError(entryAlreadyExistsException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(entryAlreadyExistsException.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CannotDeleteUrlException.class)
    public ResponseEntity<MessageRunnerDTO> handleCannotDeleteUrlException(CannotDeleteUrlException cannotDeleteUrlException) {
        logError(cannotDeleteUrlException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(cannotDeleteUrlException.getMessage()), HttpStatus.GONE);
    }

    @ExceptionHandler(FailedToDeleteUrlException.class)
    public ResponseEntity<MessageRunnerDTO> handleFailedToDeleteUrlException(FailedToDeleteUrlException failedToDeleteUrlException) {
        logError(failedToDeleteUrlException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(failedToDeleteUrlException.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<MessageRunnerDTO> handleJDBCConnectionException(JDBCConnectionException jdbcConnectionException) {
        logError(jdbcConnectionException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(jdbcConnectionException.getMessage()), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageRunnerDTO> handleOtherException(Exception exception) {
        logError(exception);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageRunnerDTO> handleOtherRuntimeException(RuntimeException runtimeException) {
        logError(runtimeException);
        return new ResponseEntity<MessageRunnerDTO>(new MessageRunnerDTO(runtimeException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
