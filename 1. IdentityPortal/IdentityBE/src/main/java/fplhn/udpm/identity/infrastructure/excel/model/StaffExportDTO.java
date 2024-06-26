package fplhn.udpm.identity.infrastructure.excel.model;

import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface StaffExportDTO extends HasOrderNumber {

    String getStaffCode();

    String getFullName();

    String getMailFpt();

    String getMailFe();

    String getDepartmentCampusInfo();

}
