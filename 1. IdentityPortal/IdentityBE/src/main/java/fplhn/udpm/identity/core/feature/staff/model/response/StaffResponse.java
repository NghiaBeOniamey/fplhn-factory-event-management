package fplhn.udpm.identity.core.feature.staff.model.response;

import fplhn.udpm.identity.core.common.HasOrderNumber;
import fplhn.udpm.identity.core.common.IsIdentify;

public interface StaffResponse extends IsIdentify, HasOrderNumber {

    String getStaffCode();

    String getStaffName();

    String getAccountFe();

    String getAccountFpt();

    String getDepartmentName();

    String getCampusName();

    String getPhoneNumber();

    String getStaffStatus();

}
