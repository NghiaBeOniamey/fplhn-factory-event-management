import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import {
  useAddStaff,
  useGetDetailStaff,
  useUpdateStaff,
} from "#service/action/staff.action";
import { ModifyStaff } from "#type/index.t";
import { Form } from "antd";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useModifyStaffInformation } from "./useModifyStaffInformation";

export const useModifyStaff = (id: number | null) => {
  const [form] = Form.useForm<ModifyStaff>();

  const navigate = useNavigate();

  const { listCampus, listDepartment } = useModifyStaffInformation();

  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const { mutateAsync: addStaff, isLoading: loadingAddStaff } = useAddStaff();

  const { mutateAsync: updateStaff, isLoading: loadingUpdateStaff } =
    useUpdateStaff();

  const handleAddStaff = async (staff: ModifyStaff) => {
    const response = await addStaff(staff);
    if (response?.status === 200) {
      toast.success("Thêm nhân viên thành công");
      navigate("/management/manage-staff");
    } else {
      toast.error(response?.data?.message);
    }
  };

  const handleUpdateStaff = async (id: number, staff: ModifyStaff) => {
    const response = await updateStaff({ id, staff });
    if (response?.status === 200) {
      toast.success("Cập nhật nhân viên thành công");
      navigate("/management/manage-staff");
    } else {
      toast.error(response?.data?.message);
    }
  };

  const handleModifyStaff = async () => {
    if (form.getFieldsError().some(({ errors }) => errors.length)) {
      return;
    }

    const formData = form.getFieldsValue();

    const staff = {
      staffName: formData?.staffName,
      staffCode: formData?.staffCode,
      departmentId: formData?.departmentId,
      campusId: formData?.campusId,
      emailFpt: formData?.emailFpt,
      emailFe: formData?.emailFe,
      phoneNumber: formData?.phoneNumber,
    };

    try {
      if (id) {
        await handleUpdateStaff(id, staff);
      } else {
        await handleAddStaff(staff);
      }
    } catch (error: any) {
      toast.error(error?.response?.data?.message);
    }
  };

  const searchSelect = (input: string, option: any) => {
    return option.children.toLowerCase().includes(input.toLowerCase());
  };

  const { data, isFetching, isError } = useGetDetailStaff(id);

  useEffect(() => {
    if (!id) return;

    if (isError) toast.error(INTERNAL_SERVER_ERROR);

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
  }, [id, data, isError, form]);

  return {
    form,
    loading: loadingAddStaff || loadingUpdateStaff || isFetching,
    listCampus,
    listDepartment,
    handleModifyStaff,
    searchSelect,
    permissions,
  };
};
