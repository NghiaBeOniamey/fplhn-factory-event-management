import { Campus, Department } from "#type/index.t";
import {
  useGetAllCampus,
  useGetAllDepartment,
} from "#service/action/staff.action";

export const useModifyStaffInformation = (): {
  listDepartment: Department[] | [];
  listCampus: Campus[] | [];
} => {
  const { data: listDepartmentResponse } = useGetAllDepartment();
  const { data: listCampusResponse } = useGetAllCampus();
  const listDepartment = listDepartmentResponse?.data || [];
  const listCampus = listCampusResponse?.data || [];
  return {
    listDepartment,
    listCampus,
  };
};
