package ra.securotyProject.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignInDto {
    @Size(min = 8, max = 20, message = "UserName phải có độ dài từ 8 đến 20 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "UserName chỉ được chứa chữ và số, không có ký tự đặc biệt.")
    private String username;
    @Size(min = 8,max =20, message = "UserName phải có độ dài từ 8 đến 20 ký tự.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "password phải có ít nhất 1 chữ hoa ,1 chữ thường,1 số, không có ký tự đặc biệt !!!")
    private String password;
}
