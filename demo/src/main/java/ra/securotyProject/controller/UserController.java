package ra.securotyProject.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.repository.IUserRepository;
import ra.securotyProject.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @PatchMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@RequestParam MultipartFile file, @PathVariable Long id) throws NotfoundException {
   return  new ResponseEntity<>(userService.updateAvantar(file, id), HttpStatus.OK) ;
    }

}
