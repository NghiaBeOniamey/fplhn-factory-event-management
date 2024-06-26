package fplhn.udpm.identity.infrastructure.excel.commonio.role;

import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffModuleRoleExcelRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RoleWriter implements ItemWriter<List<StaffRoleModule>> {

    @Setter(onMethod_ = @Autowired)
    private StaffModuleRoleExcelRepository staffModuleRoleExcelRepository;

    @Override
    @Transactional
    @Modifying
    public void write(Chunk<? extends List<StaffRoleModule>> chunk) {
        String moduleCode = chunk.getItems().get(0).get(0).getModule().getMa();
        staffModuleRoleExcelRepository.deleteAllByModuleCode(moduleCode);
        List<StaffRoleModule> staffRoleModulesToSave = chunk
                .getItems()
                .stream()
                .flatMap(List::stream)
                .filter(staffRoleModule -> staffRoleModule.getRole() != null)
                .peek(staffRoleModule -> staffRoleModule.setEntityStatus(EntityStatus.NOT_DELETED))
                .collect(Collectors.toList());
        staffModuleRoleExcelRepository.saveAll(staffRoleModulesToSave);
    }

}
