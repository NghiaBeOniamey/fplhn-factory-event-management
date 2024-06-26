package fplhn.udpm.identity.infrastructure.excel.commonio.role;


import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.infrastructure.excel.model.ImportRoleExcel;
import fplhn.udpm.identity.infrastructure.excel.repository.ModuleExcelRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.RoleExcelRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffConfigJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RoleProcessor implements ItemProcessor<ImportRoleExcel, List<StaffRoleModule>> {

    private RoleExcelRepository roleExcelRepository;

    private ModuleExcelRepository moduleExcelRepository;

    private StaffConfigJobRepository staffConfigJobRepository;

    @Autowired
    public void setModuleExcelRepository(ModuleExcelRepository moduleExcelRepository) {
        this.moduleExcelRepository = moduleExcelRepository;
    }

    @Autowired
    public void setRoleExcelRepository(RoleExcelRepository roleExcelRepository) {
        this.roleExcelRepository = roleExcelRepository;
    }

    @Autowired
    public void setStaffConfigJobRepository(StaffConfigJobRepository staffConfigJobRepository) {
        this.staffConfigJobRepository = staffConfigJobRepository;
    }

    @Override
    public List<StaffRoleModule> process(ImportRoleExcel item) throws Exception {
        if (item.getStaffInfo() == null) return null;

        List<StaffRoleModule> staffRoleModuleList = new ArrayList<>();
        String[] staffModuleInfoArr = item.getStaffInfo().split("-");
        String staffCode = staffModuleInfoArr[0];
        String moduleCode = staffModuleInfoArr[1];

        Module module = moduleExcelRepository
                .findModuleByModuleCode(moduleCode)
                .orElseThrow(() -> new Exception("Module not found for code: " + moduleCode));

        List<String> roleList = item.getRoleCodeCheck();
        List<Role> roleListFromDB = roleExcelRepository.findAll();

        if (roleList.size() != roleListFromDB.size())
            throw new Exception("Role list size mismatch between input and database.");


        Staff staff = staffConfigJobRepository
                .findByStaffCode(staffCode)
                .orElseThrow(() -> new Exception("Staff not found for code: " + staffCode));

        for (int i = 0; i < roleList.size(); i++) {
            if (roleList.get(i).equalsIgnoreCase("x")) {
                StaffRoleModule staffRoleModule = new StaffRoleModule();
                staffRoleModule.setStaff(staff);
                staffRoleModule.setModule(module);
                staffRoleModule.setRole(roleListFromDB.get(i));
                staffRoleModuleList.add(staffRoleModule);
            }
        }

        return staffRoleModuleList;
    }

}
