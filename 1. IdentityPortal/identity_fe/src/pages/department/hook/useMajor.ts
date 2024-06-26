import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import useDebounce from "#hooks/useDebounce";
import useToggle from "#hooks/useToggle";
import {
  useChangeMajorStatus,
  useCreateMajor,
  useGetAllMajor,
  useGetDetailMajor,
  useUpdateMajor,
} from "#service/action/major.action";
import { MajorPaginationParams } from "#service/api/major.api";
import { Form, message } from "antd";
import { useCallback, useEffect, useMemo, useState } from "react";
import { useImmer } from "use-immer";

export interface SearchMajor {
  majorCode?: string;
  majorName?: string;
}

export const useMajor = (departmentId: number) => {
  const [paginationParams, setPaginationParams] =
    useImmer<MajorPaginationParams>({
      page: 1,
      size: 10,
      departmentId,
    });

  const [form] = Form.useForm();

  const { value: isOpen, toggle } = useToggle();

  const [majorId, setMajorId] = useState<number | null>(0);

  const [searchValue, setSearchValue] = useState<SearchMajor>({
    majorCode: undefined,
    majorName: undefined,
  });

  const debounceSearch = useDebounce(searchValue);

  const { data: campusResponse, isLoading } = useGetAllMajor({
    ...paginationParams,
    ...debounceSearch,
  });

  const { data: majorDetail } = useGetDetailMajor(majorId);

  const { mutateAsync: createMajor } = useCreateMajor();

  const { mutateAsync: updateMajor } = useUpdateMajor();

  const { mutateAsync: changeMajorStatus } = useChangeMajorStatus();

  const majorData = useMemo(
    () =>
      campusResponse?.data?.data.map((item) => ({
        ...item,
        key: item.majorId,
      })),
    [campusResponse]
  );

  const totalPages = useMemo(
    () => campusResponse?.data?.totalPages || 0,
    [campusResponse]
  );

  const handleCloseModal = () => {
    toggle();
  };

  const handleOpenModalUpdate = useCallback(
    (majorId: number) => {
      setMajorId(majorId);
      toggle();
    },
    [toggle]
  );

  const handleOpenModalAdd = () => {
    setMajorId(null);
    toggle();
    form.resetFields();
  };

  const handleChangeMajorStatus = useCallback(
    async (majorId: number) => {
      try {
        const response = await changeMajorStatus(majorId);
        if (response.data.success) {
          message.success(response.data.message);
          setPaginationParams((draft) => {
            draft.page = 1;
            draft.size = 10;
          });
        } else {
          message.error(response.data.message);
        }
      } catch (error) {
        message.error(INTERNAL_SERVER_ERROR);
      }
    },
    [setPaginationParams]
  );

  const handleFilter = (value: SearchMajor) => {
    setSearchValue(value);
  };

  const handelAddMajor = useCallback(async () => {
    const handleClose = () => {
      toggle();
    };
    try {
      const values = await form.validateFields();
      const response = await createMajor({
        ...values,
        departmentId,
      });
      if (response.success) {
        message.success(response.message);
        handleClose();
      } else {
        message.error(response.message);
      }
    } catch (error) {
      message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  }, [createMajor, departmentId, form, toggle]);

  const handelUpdateMajor = useCallback(
    async (majorId: number) => {
      const handleClose = () => {
        toggle();
      };
      try {
        const values = await form.validateFields();
        const response = await updateMajor({
          id: majorId,
          major: values,
        });
        if (response.success) {
          message.success(response.message);
          handleClose();
        } else {
          message.error(response?.message || INTERNAL_SERVER_ERROR);
        }
      } catch (error) {
        message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
      }
    },
    [form, toggle, updateMajor]
  );

  const handleSort = useCallback(
    (field: string) => {
      setPaginationParams((draft) => {
        draft.sortBy = field;
        draft.orderBy = draft.orderBy === "asc" ? "desc" : "asc";
      });
    },
    [setPaginationParams]
  );

  useEffect(() => {
    if (majorId) {
      const major = majorDetail?.data;
      form.setFieldsValue({
        majorCode: major?.majorCode,
        majorName: major?.majorName,
      });
    }

    return () => {
      form.resetFields();
    };
  }, [form, majorDetail, majorId]);

  return {
    data: majorData,
    paginationParams,
    setPaginationParams,
    totalPages,
    form,
    handleOpenModalUpdate,
    handleChangeMajorStatus,
    handleFilter,
    isOpen,
    handleClose: handleCloseModal,
    handleOpenModalAdd,
    majorId,
    handelAddMajor,
    handelUpdateMajor,
    handleSort,
    isLoading,
  };
};
