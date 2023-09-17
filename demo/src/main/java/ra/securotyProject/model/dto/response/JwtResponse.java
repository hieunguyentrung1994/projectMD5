package ra.securotyProject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token ;
    private String type = "Bearer";
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean status;
    private MultipartFile uploadFile;
    private String address;
    private List<String> roles = new ArrayList<>();
}
