package ra.securotyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.securotyProject.model.domain.Role;
import ra.securotyProject.model.domain.RoleName;

import java.util.Optional;
@Repository
public interface IRoleRepositoty extends JpaRepository<Role,Long> {
    // tìm kiếm  theo roleName
    Optional<Role> findByRoleName(RoleName roleName);
}
