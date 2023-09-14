package ra.securotyProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ra.securotyProject.advice.ControllerAsvice;
import ra.securotyProject.exception.UserException;
import ra.securotyProject.model.domain.Role;
import ra.securotyProject.model.domain.RoleName;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.request.FormSignUpDto;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.repository.IUserRepository;
import ra.securotyProject.sercurity.jwt.JwtProvider;
import ra.securotyProject.sercurity.user_principle.UserPrincile;
import ra.securotyProject.service.IRoleService;
import ra.securotyProject.service.IUserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ControllerAsvice controllerAsvice;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }
    @Autowired
    private IRoleService roleService;
    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public JwtResponse convertJwtResponse(UserPrincile userPrinciple) {
        // tạo token và trả về cho người dùng
        String token = jwtProvider.generateToken(userPrinciple);
        // lấy ra user principle
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return JwtResponse.builder().token(token)
                .fullName(userPrinciple.getFullName())
                .username(userPrinciple.getUsername())
                .email(userPrinciple.getEmail())
                .phoneNumber(userPrinciple.getPhonenumber())
                .avantar(userPrinciple.getAvantar())
                .roles(roles)
                .address(userPrinciple.getAddress())
                .type("Bearer")
                .status(userPrinciple.isStatus()).build();
    }

    @Override
    public FormSignUpDto RandomStringGenerator(FormSignUpDto formSignUpDto) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        long timeInMillis = new Date().getTime() + 120000;
        formSignUpDto.setRandom(sb.toString());
        formSignUpDto.setDateLoading(new Date(timeInMillis));
        return formSignUpDto;
    }

    @Override
    public Users save(FormSignUpDto form)  {
            // kiểm tra role để sét laại quyền cho Uers được trả về để lưu lên trên database
            Set<Role> roles  = new HashSet<>();
            if (form.getRoles()==null||form.getRoles().isEmpty()){
                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
            }else {
                form.getRoles().stream().forEach(
                        role->{
                            switch (role){
                                case "admin":
                                    roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                                case "seller":
                                    roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                                case "user":
                                    roles.add(roleService.findByRoleName(RoleName.ROLE_USER));}}
                );
            }
            // lấy ra danh sách các quyền và chuyển thành đối tượng Users
            Users users = Users.builder()
                    .userName(form.getUsername())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .status(true)
                    .roles(roles)
                    .fullName(form.getFullname())
                    .email(form.getEmail())
                    .phonenumber(form.getPhoneNumber())
                    .build();

            return userRepository.save(users);
        }


    @Override
    public boolean checkUser(FormSignUpDto form) throws UserException{
        // kiểm tra tồn tại của Username,Email,PhoneNumber
        if (userRepository.existsByUserName(form.getUsername())){
            throw new UserException("UserName này đã tồn tại");
        }else if(userRepository.existsByEmail(form.getEmail())){
            throw new UserException("Email này đã tồn tại");
        } else if (userRepository.existsByPhonenumber(form.getPhoneNumber())) {
            throw new UserException("PhoneNumber này đã tồn tại");
        }
        return true;
    }
}
