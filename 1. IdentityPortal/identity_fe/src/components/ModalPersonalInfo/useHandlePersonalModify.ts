import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import {
  useGetAllCampus,
  useGetAllDepartment,
  useGetDetailStaff,
  useUpdateStaff,
} from "#service/action/staff.action";
import { Form } from "antd";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";

export const useHandlePersonalModify = () => {
  const [form] = Form.useForm();

  const searchSelect = (input: string, option: any) => {
    return option.children.toLowerCase().includes(input.toLowerCase());
  };

  const [loading, setLoading] = useState(false);

  const {
    permissions,
    userInfo: { userId },
  } = useSelector((state: RootState) => state.auth.authorization);

  const { data: listDepartmentResponse } = useGetAllDepartment();

  const { data: listCampusResponse } = useGetAllCampus();

  const { mutateAsync: updateStaff } = useUpdateStaff();

  const listDepartment = listDepartmentResponse?.data || [];

  const listCampus = listCampusResponse?.data || [];

  const { data, isLoading, isError } = useGetDetailStaff(userId);

  const handleUpdateStaff = async () => {
    if (form.getFieldsError().some(({ errors }) => errors.length)) {
      return;
    }

    const formData = form.getFieldsValue();

    const response = await updateStaff({ id: userId, staff: formData });

    if (response?.status === 200) {
      toast.success("Cập nhật thông tin thành công");
    } else {
      toast.error(response?.data?.message);
    }
  };

  useEffect(() => {
    if (userId) {
      setLoading(isLoading);
      if (isError) {
        toast.error(INTERNAL_SERVER_ERROR);
      }
      const detailStaffData = data?.data;
      if (detailStaffData) {
        form.setFieldsValue({
          staffName: detailStaffData?.staffName,
          staffCode: detailStaffData?.staffCode,
          departmentId: detailStaffData?.departmentId,
          campusId: detailStaffData?.campusId,
          emailFpt: detailStaffData?.emailFpt,
          emailFe: detailStaffData?.emailFe,
          phoneNumber: detailStaffData?.phoneNumber,
        });
      }
    }
  }, [userId, data, isLoading, isError, setLoading, form]);

  return {
    searchSelect,
    listDepartment,
    listCampus,
    permissions,
    form,
    handleUpdateStaff,
    loading,
  };
};
