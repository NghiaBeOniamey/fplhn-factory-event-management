package fplhn.udpm.identity.infrastructure.config.database;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.infrastructure.config.database.repository.RoleDefaultRepository;
import fplhn.udpm.identity.infrastructure.constant.RoleConstants;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultDatabaseData {

    private final RoleDefaultRepository roleDefaultRepository;

    List<String> defaultRoles = RoleConstants.getDefaultRoles();

    @PostConstruct
    public void generateDefaultRoles() {
        defaultRoles.forEach(role -> {
            if (roleDefaultRepository.findByCode(role).isEmpty()) {
                Role roleDefault = new Role();
                roleDefault.setCode(role);
                roleDefault.setName(role);
                roleDefault.setEntityStatus(EntityStatus.NOT_DELETED);
                roleDefaultRepository.save(roleDefault);
            }
        });
    }


}
