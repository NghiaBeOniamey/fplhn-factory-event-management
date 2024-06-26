package fplhn.udpm.identity.infrastructure.excel.commonio.staff;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffConfigJobRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StaffWriter implements ItemWriter<Staff> {

    @Setter(onMethod_ = @Autowired, onParam_ = @Qualifier("excelNhanVienRepository"))
    private StaffConfigJobRepository staffConfigJobRepository;

    @Override
    public void write(Chunk<? extends Staff> chunk) {
        for (Staff staff : chunk) {
            staffConfigJobRepository
                    .findByStaffCode(staff.getStaffCode())
                    .ifPresentOrElse(
                            s -> staffConfigJobRepository.save(s),
                            () -> staffConfigJobRepository.save(staff)
                    );
        }
    }

}
