import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import useDebounce from "#hooks/useDebounce";
import useToggle from "#hooks/useToggle";
import {
  useChangeMajorCampusStatus,
  useCreateMajorCampus,
  useGetAllMajorCampus,
  useGetDetailMajorCampus,
  useUpdateMajorCampus,
} from "#service/action/major.action";
import {
  FetchMajorCampusResponse,
  PaginationParamsMajorCampus,
} from "#service/api/majorcampus.api";
import { Form, message } from "antd";
import { useCallback, useLayoutEffect, useMemo, useState } from "react";
import { useImmer } from "use-immer";

export interface SearchMajorCampus {
  majorCode?: string;
  headMajorName?: string;
  majorName?: string;
  headMajorCode?: string;
}

export const useMajorCampus = (departmentCampusId: number) => {
  const [paginationParams, setPaginationParams] =
    useImmer<PaginationParamsMajorCampus>({
      page: 1,
      size: 5,
      departmentCampusId,
    });

  const [searchValue, setSearchValue] = useState<SearchMajorCampus>(
    {} as SearchMajorCampus
  );

  const debounceSearch = useDebounce(searchValue, 500);

  const [majorCampusId, setMajorCampusId] = useState<number | null>();

  const { value: isModalVisible, setFalse, setTrue } = useToggle();

  const [form] = Form.useForm();

  const { data: majorCampusFetching } = useGetAllMajorCampus({
    ...paginationParams,
    ...debounceSearch,
  } as PaginationParamsMajorCampus);

  const majorData = useMemo(() => {
    return majorCampusFetching?.data?.data.map(
      (item: FetchMajorCampusResponse) => {
        return {
          ...item,
          key: item.majorCampusId,
        };
      }
    );
  }, [majorCampusFetching]);

  const totalPages = useMemo(() => {
    return majorCampusFetching?.data?.totalPages;
  }, [majorCampusFetching]);

  const { data: detailMajorCampus } = useGetDetailMajorCampus(majorCampusId);

  const campusCode = useMemo(() => {
    return majorData?.[0]?.campusCode;
  }, [majorData]);

  const { mutateAsync: createMajorCampus } = useCreateMajorCampus();

  const { mutateAsync: updateMajorCampus } = useUpdateMajorCampus();

  const { mutateAsync: changeMajorCampusStatus } = useChangeMajorCampusStatus();

  const handleFilter = useCallback((searchValue: SearchMajorCampus) => {
    setSearchValue(searchValue);
  }, []);

  const handleOpenModalUpdate = useCallback(
    (majorCampusId: number) => {
      try {
        setMajorCampusId(majorCampusId);
        setTrue();
      } catch (error) {
        message.error(INTERNAL_SERVER_ERROR);
      }
    },
    [setTrue]
  );

  const handleOpenModalAdd = () => {
    setMajorCampusId(null);
    setTrue();
  };

  const handleChangeMajorStatus = useCallback(
    async (majorCampusId: number) => {
      try {
        const response = await changeMajorCampusStatus(majorCampusId);
        if (response?.success) {
          message.success(response?.message);
        } else {
          message.error(response?.message);
        }
      } catch (error) {
        message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
      }
    },
    [changeMajorCampusStatus]
  );

  const handleAdd = async () => {
    try {
      const values = await form.validateFields();
      const response = await createMajorCampus({
        ...values,
        departmentCampusId,
      });
      if (response?.success) {
        message.success(response?.message);
        setFalse();
      } else {
        message.error(response?.message);
      }
    } catch (error) {
      message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  const handleUpdate = async (majorCampusId: number) => {
    try {
      const values = await form.validateFields();
      const response = await updateMajorCampus({
        id: majorCampusId,
        majorCampus: values,
      });
      if (response?.success) {
        message.success(response?.message);
        setFalse();
      } else {
        message.error(response?.message);
      }
    } catch (error) {
      message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  useLayoutEffect(() => {
    const resetForm = () => {
      form.resetFields();
    };

    if (majorCampusId) {
      form.setFieldsValue(detailMajorCampus?.data);
    }

    return () => {
      resetForm();
    };
  }, [detailMajorCampus?.data, form, majorCampusId]);

  return {
    paginationParams,
    setPaginationParams,
    majorData,
    totalPages,
    handleFilter,
    handleOpenModalUpdate,
    handleOpenModalAdd,
    handleChangeMajorStatus,
    isModalVisible,
    setFalse,
    setTrue,
    majorCampusId,
    form,
    handleAdd,
    handleUpdate,
    campusCode,
  };
};
