import {useMemo} from "react";
import {useGetAllMajorList, useGetAllStaffList,} from "../../../service/action/major.action";

export const useStaffMajorInfor = () => {
    const {data: staffsFetching, isLoading: isLoadingStaffs} =
        useGetAllStaffList();

    const {data: majorsFetching, isLoading: isLoadingMajors} =
        useGetAllMajorList();

    const staffs = useMemo(() => {
        return staffsFetching?.data?.map((item) => {
            return {
                value: item.staffId,
                label: item.staffNameCode,
            };
        });
    }, [staffsFetching]);

    const majors = useMemo(() => {
        return majorsFetching?.data?.map((item) => {
            return {
                value: item.majorId,
                label: item.majorName,
            };
        });
    }, [majorsFetching]);

    return {
        staffs,
        majors,
    };
};
