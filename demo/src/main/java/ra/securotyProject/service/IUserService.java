package ra.securotyProject.service;




import org.springframework.web.multipart.MultipartFile;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.request.FormSignUpDto;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.sercurity.user_principle.UserPrincile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findByUserName(String username);
    Users save(FormSignUpDto users) throws AlreadyExistException;
    boolean checkUser(FormSignUpDto formSignUpDto) throws AlreadyExistException;

    JwtResponse convertJwtResponse(UserPrincile userPrinciple);
    FormSignUpDto RandomStringGenerator(FormSignUpDto formSignUpDto);
    JwtResponse updateRole(Long id) throws NotfoundException, AlreadyExistException;
    String block_user(Long id) throws NotfoundException;
    Users updateAvantar(MultipartFile file,Long id) throws NotfoundException;
}
