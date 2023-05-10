package capstone3d.Server.exception;

import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<AllResponse> handle(BadRequestException e) {
        return AllResponse.ErrorResponseEntity(e.getStatusMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AllResponse> handleValidError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        return AllResponse.ValidErrorResponseEntity(StatusMessage.SignUp_Request_Error.getStatus(),bindingResult);
    }
}