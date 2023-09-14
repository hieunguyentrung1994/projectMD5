package ra.securotyProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.securotyProject.model.domain.Role;
import ra.securotyProject.model.domain.RoleName;
import ra.securotyProject.repository.IRoleRepositoty;
import ra.securotyProject.service.IRoleService;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepositoty roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(()->new RuntimeException("Role Not Found"));
    }
}
