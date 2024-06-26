import { PERMISSIONS } from "#constant/index";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import useDebounce from "#hooks/useDebounce";
import {
  useGetAllStaff,
  useUpdateStaffStatus,
} from "#service/action/staff.action";
import { StaffPagination } from "#service/api/staff.api";
import { Modal, message } from "antd";
import { useCallback, useMemo, useState } from "react";
import { useSelector } from "react-redux";
import { useImmer } from "use-immer";

export const useStaffs = () => {
  const { userInfo, permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const [paginationParams, setPaginationParams] = useImmer<StaffPagination>(
    () => {
      const initialPagination: StaffPagination = {
        page: 1,
        size: 10,
      };

      if (permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO)) {
        return initialPagination;
      }

      return {
        ...initialPagination,
        campusCode: userInfo?.campusCode,
      };
    }
  );

  const [searchValue, setSearchValue] = useState({
    staffCode: undefined,
    staffName: undefined,
  });

  const searchValueDebounce = useDebounce(searchValue);

  const { data: dataStaff, isLoading } = useGetAllStaff({
    ...paginationParams,
    ...searchValueDebounce,
  });

  const { mutateAsync: updateStaffStatus, isLoading: isChangeStatusLoading } =
    useUpdateStaffStatus();

  const staffs = useMemo(() => {
    return (
      dataStaff?.data?.data.map((staff) => ({
        ...staff,
        key: staff.id,
      })) || []
    );
  }, [dataStaff?.data?.data]);

  const totalPages = useMemo(() => {
    return dataStaff?.data?.totalPages || 0;
  }, [dataStaff?.data?.totalPages]);

  const handleDelete = useCallback(
    async (id: number) => {
      Modal.confirm({
        title: "Xác nhận",
        content: "Bạn có chắc chắn muốn cập trạng thái nhân viên này?",
        okButtonProps: {
          danger: true,
        },
        centered: true,
        onOk: async () => {
          try {
            const res = await updateStaffStatus(id);
            if (res?.success) {
              message.success(
                res?.message || "Cập nhật trạng thái nhân viên thành công"
              );
            }
          } catch (error) {
            message.error(
              error?.response?.data?.message || INTERNAL_SERVER_ERROR
            );
          }
        },
      });
    },
    [updateStaffStatus]
  );

  const handleSearch = async (value: {
    staffCode: string;
    staffName: string;
  }) => {
    setPaginationParams((draft) => {
      draft.page = 1;
    });
    setSearchValue(value);
  };

  return {
    staffs,
    paginationParams,
    setPaginationParams,
    totalPages,
    handleDelete,
    handleSearch,
    loading: isLoading,
    isChangeStatusLoading,
    campusCode: userInfo?.campusCode,
    permissions,
  };
};
