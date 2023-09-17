package ra.securotyProject.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.securotyProject.advice.ControllerAsvice;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.exception.AlreadyExistException;
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
//                .uploadFile(userPrinciple.getAvantar())
                .roles(roles)
                .address(userPrinciple.getAddress())
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
    public JwtResponse updateRole(Long id) throws NotfoundException, AlreadyExistException {
          Optional<Users> users =  userRepository.findById(id);
            Users user = users.get();
          if (!users.isPresent()) {
              throw new NotfoundException("Id này không tồn tại User tương ứng");
          }
        for (Role role : user.getRoles()) {
              if(role.equals(roleService.findByRoleName(RoleName.ROLE_SELLER))){
              throw new AlreadyExistException("User này đã có là người bán hàng");}
          }
          // lưu thêm mới role Seller
         Set<Role> list = user.getRoles();
          list.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
          user.setRoles(list);
          userRepository.save(user);
          // chuyển đổi user thành jwtResponse
       UserPrincile userPrincile= UserPrincile.build(user);
        return convertJwtResponse(userPrincile);
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
    public boolean checkUser(FormSignUpDto form) throws AlreadyExistException {
        // kiểm tra tồn tại của Username,Email,PhoneNumber
        if (userRepository.existsByUserName(form.getUsername())){
            throw new AlreadyExistException("UserName này đã tồn tại");
        }else if(userRepository.existsByEmail(form.getEmail())){
            throw new AlreadyExistException("Email này đã tồn tại");
        } else if (userRepository.existsByPhonenumber(form.getPhoneNumber())) {
            throw new AlreadyExistException("PhoneNumber này đã tồn tại");
        }
        return true;
    }


    @Override
    public String block_user(Long id) throws NotfoundException {
            Optional<Users> users = userRepository.findById(id);
            if (!users.isPresent()){
                throw new NotfoundException("Không tìm thấy tài khoản !!!");
            }
            Users newUser = users.get();
        for (Role role : newUser.getRoles()) {
            if(role.equals(roleService.findByRoleName(RoleName.ROLE_ADMIN))){
                throw new NotfoundException("Đây là tài khoản admin Bạn không thể blog ");
             }}
            if(newUser.isStatus() == false){
                newUser.setStatus(true);
                userRepository.save(newUser);
                return "tài khoản "+ newUser.getUserName() + " đã được mở hoạt động !!!";
            }else{
                newUser.setStatus(false);
                userRepository.save(newUser);
                return "tài khoản "+ newUser.getUserName() + " đã Chặn hoạt động !!!";
            }
    }
    @Autowired
    private UploadService uploadService;
    @Override
    public Users updateAvantar(MultipartFile file,Long id) throws NotfoundException {
        String url = uploadService.uploadFile(file);

        Optional<Users> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotfoundException("Không tìm thấy tài khoản !! ");
        }
        Users user1 = user.get();
        user1.setAvantar(url);
       return userRepository.save(user1);

    }
}
