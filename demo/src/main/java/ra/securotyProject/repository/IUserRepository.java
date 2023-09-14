package ra.securotyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.sercurity.user_principle.UserPrincile;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<Users,Long> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhonenumber(String phonenumber);
    Optional<Users> findByUserName(String userName);


}
