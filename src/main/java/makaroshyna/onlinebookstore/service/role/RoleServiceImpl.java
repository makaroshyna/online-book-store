package makaroshyna.onlinebookstore.service.role;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.model.Role;
import makaroshyna.onlinebookstore.model.Role.RoleName;
import makaroshyna.onlinebookstore.repository.role.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getAllByName(RoleName name) {
        return roleRepository.getAllByName(name);
    }
}
