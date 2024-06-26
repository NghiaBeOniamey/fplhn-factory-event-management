package fplhn.udpm.identity.infrastructure.excel.commonio.staff;

import fplhn.udpm.identity.infrastructure.excel.model.ImportExcelRequest;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class StaffRowMapper implements RowMapper<ImportExcelRequest> {


    @Override
    public ImportExcelRequest mapRow(RowSet rowSet) {
        try {
            ImportExcelRequest importExcelRequest = new ImportExcelRequest();
            importExcelRequest.setMaGiangVien(rowSet.getColumnValue(1));
            importExcelRequest.setHoTen(rowSet.getColumnValue(2));
            importExcelRequest.setEmailFe(rowSet.getColumnValue(3));
            importExcelRequest.setEmailFpt(rowSet.getColumnValue(4));
            importExcelRequest.setTenBoMonTheoCoSo(rowSet.getColumnValue(5));
            return importExcelRequest;
        } catch (Exception e) {
            return null;
        }
    }
}
