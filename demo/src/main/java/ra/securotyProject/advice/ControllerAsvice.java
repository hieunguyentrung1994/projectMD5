package ra.securotyProject.advice;


import ra.securotyProject.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.exception.AlreadyExistException;


@RestControllerAdvice
public class ControllerAsvice {
    @ExceptionHandler(LoginException.class)
    public  ResponseEntity<String> loginFail(LoginException loginException){
        return  new ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String>  NotUser(AlreadyExistException userException){
        return new ResponseEntity<>(userException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NotfoundException.class)
    public ResponseEntity<String> UserNotFount(NotfoundException notUserException){
        return new ResponseEntity<>(notUserException.getMessage(), HttpStatus.NOT_FOUND);
    }
}