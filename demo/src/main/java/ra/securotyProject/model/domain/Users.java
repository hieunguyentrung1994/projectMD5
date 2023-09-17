package ra.securotyProject.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", length = 20, unique = true)
    private String userName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "phonenumber", length = 11, unique = true)
    private String phonenumber;

    @Column(name = "fullName", length = 50)
    private String fullName;

    @Column(name = "avantar",columnDefinition = "TEXT")
    private String avantar;

    @Column(name = "address")
    private String address;

    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;


}
