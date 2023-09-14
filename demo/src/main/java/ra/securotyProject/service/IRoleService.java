package ra.securotyProject.service;


import ra.securotyProject.model.domain.Role;
import ra.securotyProject.model.domain.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
