package fplhn.udpm.identity.infrastructure.excel.commonio.role;

import fplhn.udpm.identity.infrastructure.excel.model.ImportRoleExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RoleRowMapper implements RowMapper<ImportRoleExcel> {

    private final int count;

    public RoleRowMapper(int count) {
        this.count = count;
    }

    @Override
    public ImportRoleExcel mapRow(RowSet rowSet) {
        try {
            ImportRoleExcel importRoleExcel = new ImportRoleExcel();
            importRoleExcel.setStaffInfo(rowSet.getColumnValue(0));
            List<String> listRole = new ArrayList<>();
            for (int i = 1; i < count + 1; i++) {
                listRole.add(rowSet.getColumnValue(i));
            }
            importRoleExcel.setRoleCodeCheck(listRole);
            return importRoleExcel;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

}