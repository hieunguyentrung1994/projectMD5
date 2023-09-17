package ra.securotyProject.sercurity.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.securotyProject.model.domain.Role;
import ra.securotyProject.model.domain.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincile implements UserDetails {

    private Long id;


    private String userName;

    @JsonIgnore
    private String password;


    private String email;


    private String phonenumber;


    private String fullName;

    private String address;

    private String avantar;

    private boolean status;

    private Set<Role> roles;

    private Collection<? extends GrantedAuthority> authorities;



    public static UserPrincile build(Users user){
        List<GrantedAuthority> list =  user.getRoles().stream().map(
                role-> new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());
        return UserPrincile.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .status(user.isStatus())
                .password(user.getPassword())
                .avantar(user.getAvantar())
                .phonenumber(user.getPhonenumber())
                .address(user.getAddress())
                .authorities(list).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
