package capstone3d.Server.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Data
@AllArgsConstructor
@Builder
public class AllResponse<T> {
    private int status;
    private String message;
    private int count;
    private T data;

    public static ResponseEntity<AllResponse> ErrorResponseEntity(StatusMessage e){
        return ResponseEntity
                .status(e.getStatus())
                .body(AllResponse.builder()
                        .status(e.getStatus())
                        .message(e.getMessage())
                        .build()
                );
    }
    public static ResponseEntity<AllResponse> ValidErrorResponseEntity(int status, BindingResult bindResult) {
        return ResponseEntity
                .status(status)
                .body(AllResponse.builder()
                        .status(status)
                        .message(bindResult.getFieldErrors().get(0).getDefaultMessage())
                        .build()
                );
    }
}