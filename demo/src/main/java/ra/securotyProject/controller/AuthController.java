package ra.securotyProject.controller;

import ra.securotyProject.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ra.securotyProject.exception.UserException;
import ra.securotyProject.model.dto.request.FormSignInDto;
import ra.securotyProject.model.dto.request.FormSignUpDto;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.sercurity.jwt.JwtProvider;
import ra.securotyProject.sercurity.user_principle.UserPrincile;
import ra.securotyProject.service.IUserService;
import ra.securotyProject.service.impl.MailService;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v4/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping
    public ResponseEntity<String> home() {

        return ResponseEntity.ok("vào được rồi nè");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signin(@Valid @RequestBody FormSignInDto formSignInDto,BindingResult result) throws LoginException {
        if (result.hasErrors()) {
            // StringBuilder là một lớp trong Java dùng để biểu diễn một chuỗi ký tự có thể thay đổi được.
            // nó cung cấp nhiều phương thức thay đổi nôi dung chuôi không tạo lại bộ nhớ như String
            StringBuilder errorMsg = new StringBuilder();
            // FieldError là 1 lớp trong springframework chứa THÔNG TIN VỀ CÁC LỖI
            for (FieldError error : result.getFieldErrors()) {
                errorMsg.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errorMsg.toString(), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = null;
        try{
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(formSignInDto.getUsername(), formSignInDto.getPassword())
            );
        }catch (AuthenticationException e){
            throw new LoginException("UserName hoặc mật khẩu không hợp lệ");
        }
        // tạo đối tương authentiction để xác thực thông qua username va password
        UserPrincile userPrinciple = (UserPrincile) authentication.getPrincipal();
        return ResponseEntity.ok(userService.convertJwtResponse(userPrinciple));
    }



    @PostMapping("/sign-up")
    //@Valid dược khỏi tạo tư springframework trong HỆ THÔNG validation nhằm tHÔNG BÁO CHO BIẾT CẦN KIỂM TRA VALIDATE CHO FORMSIGNUP
    //BindingResult dược khỏi tạo tư springframework là 1 interface  giúp lưu chữ thông tin nội trong quá trinh validate
    public FormSignUpDto create(@RequestBody @Valid FormSignUpDto formSignUpDto, BindingResult result) throws UserException{
        if (result.hasErrors()) {
            // StringBuilder là một lớp trong Java dùng để biểu diễn một chuỗi ký tự có thể thay đổi được.
            // nó cung cấp nhiều phương thức thay đổi nôi dung chuôi không tạo lại bộ nhớ như String
            StringBuilder errorMsg = new StringBuilder();
            // FieldError là 1 lớp trong springframework chứa THÔNG TIN VỀ CÁC LỖI
            for (FieldError error : result.getFieldErrors()) {
                errorMsg.append(error.getDefaultMessage()).append("\n");
            }
            new ResponseEntity<>(errorMsg.toString(), HttpStatus.BAD_REQUEST);
        }
        FormSignUpDto newformSignUpDto = userService.RandomStringGenerator(formSignUpDto);
        userService.checkUser(formSignUpDto);
        mailService.sendEmail(formSignUpDto.getEmail(), "Mã đăng ký hãy nhập vào!!!",newformSignUpDto.getRandom());
        return newformSignUpDto;
    }

    @PostMapping("/sign-up/{random}")
    private ResponseEntity<String> signup(@Valid @RequestBody FormSignUpDto formSignUpDto, @PathVariable String random) throws UserException {
        if(new Date().getTime() >= formSignUpDto.getDateLoading().getTime()) {
            throw new UserException("đã quá hạn thời gian hãy đăng ký lại đi !!!");
        }else if (formSignUpDto.getRandom().equals(random)){
            userService.save(formSignUpDto);
            return new ResponseEntity("success", HttpStatus.CREATED);
        }
        throw new UserException("hãy nhập đúng chuỗi ký tự đã gửi cho bạn qua gmail");
    }

}
