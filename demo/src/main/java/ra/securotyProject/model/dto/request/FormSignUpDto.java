package ra.securotyProject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignUpDto {
    private String fullname;
    @Size(min = 8, max = 20, message = "UserName phải có độ dài từ 8 đến 20 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "UserName chỉ được chứa chữ và số, không có ký tự đặc biệt.")
    private String username;
    @Size(min = 8, max = 20, message = "UserName phải có độ dài từ 8 đến 20 ký tự.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "password phải có ít nhất 1 chữ hoa ,1 chữ thường,1 số, không có ký tự đặc biệt !!!")
    private String password;
    @Size(min = 8, max = 100, message = "Email phải có độ dài từ 8 đến 100 ký tự.")
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email của bạn nhập không đúng.")
    private String email;
    @Size(min = 10, max = 11, message = "PhoneNumBer phải có độ dài từ 10 đến 11 ký tự.")
    @Pattern(regexp = "^(84|0[35789])([0-9]{8})$", message = "Số điện thoại không hợp lệ.")
    private String phoneNumber;
    private List<String> roles;
    private String random;
    private Date dateLoading;
}
