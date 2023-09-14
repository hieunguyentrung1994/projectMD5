package ra.securotyProject.advice;


import ra.securotyProject.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.securotyProject.exception.UserException;


@RestControllerAdvice
public class ControllerAsvice {
    @ExceptionHandler(LoginException.class)
    public  ResponseEntity<String> loginFail(LoginException loginException){
        return  new ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> UserNotFount(UserException userException){
        return new ResponseEntity<>(userException.getMessage(), HttpStatus.CONFLICT);
    }
}