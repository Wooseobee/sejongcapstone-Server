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
        String request = e.getObjectName();
        int status;
        switch (request) {
            case "LoginRequest":
                status = StatusMessage.Login_Request_Error.getStatus();
                break;
            case "SingUpRequest":
                status = StatusMessage.SignUp_Request_Error.getStatus();
                break;
            default:
                status = StatusMessage.Request_Error.getStatus();
                break;
        }

        return AllResponse.ValidErrorResponseEntity(status, bindingResult);
    }
}