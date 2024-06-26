package fplhn.udpm.identity.infrastructure.excel.commonio.staff;


import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.excel.model.ImportExcelRequest;
import fplhn.udpm.identity.infrastructure.excel.repository.CourseCampusStaffRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffConfigJobRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class StaffProcessor implements ItemProcessor<ImportExcelRequest, Staff> {

    @Setter(onMethod_ = @Autowired)
    private StaffConfigJobRepository staffConfigJobRepository;

    @Setter(onMethod_ = @Autowired)
    private CourseCampusStaffRepository courseCampusStaffRepository;

    @Override
    public Staff process(ImportExcelRequest item) {
        String tenBoMonCoSo = item.getTenBoMonTheoCoSo();
        String tenBoMon = tenBoMonCoSo.split("-")[0];
        String tenCoSo = tenBoMonCoSo.split("-")[1];

        if (tenBoMon == null || tenCoSo == null) {
            String maNhanVien = validateAndGetMaNhanVien(item);
            if (maNhanVien == null) return null;

            return getNhanVien(item, null, maNhanVien);
        }
        Optional<DepartmentCampus> boMonTheoCoSo = courseCampusStaffRepository.findByBoMonCoSo(tenBoMon, tenCoSo);
        if (boMonTheoCoSo.isEmpty()) return null;

        String maNhanVien = validateAndGetMaNhanVien(item);
        if (maNhanVien == null) return null;

        return getNhanVien(item, boMonTheoCoSo.get(), maNhanVien);
    }

    private String validateAndGetMaNhanVien(ImportExcelRequest item) {
        if (item.getMaGiangVien() == null || item.getMaGiangVien().isEmpty()) return null;
        if (!item.getEmailFe().contains(item.getMaGiangVien()) || !item.getEmailFpt().contains(item.getMaGiangVien()))
            return null;
        return item.getMaGiangVien();
    }

    private Staff getNhanVien(ImportExcelRequest item, DepartmentCampus departmentCampus, String maNhanVien) {
        Optional<Staff> existingNhanVien = staffConfigJobRepository.findByStaffCode(maNhanVien);
        Staff nhanVien;
        if (existingNhanVien.isPresent()) {
            nhanVien = existingNhanVien.get();
        } else {
            nhanVien = new Staff();
            nhanVien.setStaffCode(maNhanVien);
        }

        if (departmentCampus == null) {
            nhanVien.setFullName(item.getHoTen());
            nhanVien.setAccountFE(item.getEmailFe());
            nhanVien.setAccountFPT(item.getEmailFpt());
            nhanVien.setDepartmentCampus(null);
            nhanVien.setEntityStatus(EntityStatus.NOT_DELETED);
            return nhanVien;
        }

        nhanVien.setFullName(item.getHoTen());
        nhanVien.setAccountFE(item.getEmailFe());
        nhanVien.setAccountFPT(item.getEmailFpt());
        nhanVien.setDepartmentCampus(departmentCampus);
        nhanVien.setEntityStatus(EntityStatus.NOT_DELETED);
        return nhanVien;
    }

}
