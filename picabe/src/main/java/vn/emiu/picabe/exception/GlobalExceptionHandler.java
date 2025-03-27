package vn.emiu.picabe.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.emiu.picabe.dto.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collecting the validation error messages into a single string
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String allErrorMessages = String.join(", ", errorMessages);

        // Log validation failure details
        logger.error("Validation failed: {}", allErrorMessages);

        ApiResponse response = new ApiResponse(400, "Validation failed: " + allErrorMessages, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex) {
        // Log the bad request exception
        logger.error("Bad Request: {}", ex.getMessage(), ex);

        ApiResponse response = new ApiResponse(400, ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex) {
        // Log the not found exception
        logger.error("Not Found: {}", ex.getMessage(), ex);

        ApiResponse response = new ApiResponse(404, ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(UnauthorizedException ex) {
        // Log the not found exception
        logger.error("Not Found: {}", ex.getMessage(), ex);

        ApiResponse response = new ApiResponse(401, ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(ForbiddenException ex) {
        // Log the not found exception
        logger.error("Not Found: {}", ex.getMessage(), ex);

        ApiResponse response = new ApiResponse(403, ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        // Log the generic exception details
        logger.error("Internal Server Error at {}: {} - Method: {} - Exception: {}",
                request.getRequestURI(),
                ex.getMessage(),
                request.getMethod(),
                ex);

        ApiResponse response = new ApiResponse(500, "Internal Server Error: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
