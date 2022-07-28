package pl.tbiadacz.ApplicationManager.application.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.tbiadacz.ApplicationManager.application.common.IllegalStateChangeException;
import pl.tbiadacz.ApplicationManager.application.service.ErrorDto;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final int LOGGING_LIMIT = 5;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnhandledException(Exception exception) {

        logException(exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDto(exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleUnhandledException(IllegalArgumentException exception) {

        logException(exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDto(exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateChangeException.class)
    public ResponseEntity<ErrorDto> handleUnhandledException(IllegalStateChangeException exception) {

        logException(exception);

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDto(exception.getMessage()));
    }

    private void logException(Exception exception) {

        String stackTrace = Stream.of(exception.getStackTrace())
                .limit(LOGGING_LIMIT)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        String message = exception.getMessage() + "\n" + stackTrace;

        logger.error(message);
    }
}
