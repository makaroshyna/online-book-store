package makaroshyna.onlinebookstore.service.role;

import java.util.Set;
import makaroshyna.onlinebookstore.model.Role;
import makaroshyna.onlinebookstore.model.Role.RoleName;

public interface RoleService {
    Set<Role> getAllByName(RoleName name);
}
