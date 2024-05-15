package makaroshyna.onlinebookstore.repository.role;

import java.util.Set;
import makaroshyna.onlinebookstore.model.Role;
import makaroshyna.onlinebookstore.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getAllByName(RoleName name);
}
