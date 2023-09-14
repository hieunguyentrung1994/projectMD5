package ra.securotyProject.service;



import org.springframework.validation.BindingResult;
import ra.securotyProject.exception.UserException;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.request.FormSignUpDto;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.sercurity.user_principle.UserPrincile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findByUserName(String username);
    Users save(FormSignUpDto users) throws UserException;
    boolean checkUser(FormSignUpDto formSignUpDto) throws UserException;

    JwtResponse convertJwtResponse(UserPrincile userPrinciple);
    FormSignUpDto RandomStringGenerator(FormSignUpDto formSignUpDto);
}
