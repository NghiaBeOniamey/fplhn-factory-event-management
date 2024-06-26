import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  useStaffModuleDecentralization,
  useUpdateStaffModuleDecentralization,
} from "#service/action/decentralization.action";
import { getAllRoleAvailable } from "#service/api/decentralization.api";
import { PaginationParams } from "#type/index.t";
import { Button, Checkbox, Modal, Tooltip } from "antd";
import { debounce } from "lodash";
import { useEffect, useMemo, useState } from "react";
import { FaRegSave } from "react-icons/fa";
import { GiCardDiscard } from "react-icons/gi";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import { useImmer } from "use-immer";

export type ColumnsRole = {
  id: number;
  name: string;
  code: string;
};

export type DefineColumns = {
  title: string | (() => JSX.Element);
  dataIndex: string;
  key: string;
  render: (text: any, record: any) => JSX.Element;
};

export const useDecentralization = (moduleId: number) => {
  const [columns, setColumns] = useState<DefineColumns[]>([]);

  const [dataSource, setDataSource] = useState<any[]>([]);

  const [totalPages, setTotalPages] = useState<number | null>();

  const [initialDataSource, setInitialDataSource] = useState<any[]>([]);

  const [paginationParams, setPaginationParams] = useImmer<PaginationParams>({
    page: 1,
    size: 5,
  });

  const [isDataChanged, setIsDataChanged] = useState<boolean>(false);

  const [listStaffCodeSearch, setListStaffCodeSearch] = useState<string>("");

  const [loadingTable, setLoadingTable] = useState<boolean>(false);

  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const {
    data: staffModuleDecentralizationFetch,
    isLoading: isLoadingStaffModuleDecentralization,
    refetch: refetchStaffModuleDecentralization,
  } = useStaffModuleDecentralization(
    moduleId,
    listStaffCodeSearch,
    paginationParams
  );

  const { mutateAsync: updateStaffModuleDecentralization } =
    useUpdateStaffModuleDecentralization();

  const data = useMemo(() => {
    return staffModuleDecentralizationFetch?.data?.data;
  }, [staffModuleDecentralizationFetch]);

  const totalPagesState = useMemo(() => {
    return staffModuleDecentralizationFetch?.data?.totalPages;
  }, [staffModuleDecentralizationFetch]);

  useEffect(() => {
    if (!data) return;
    setDataSource(transformData(data));
    setInitialDataSource(transformData(data));
    setTotalPages(totalPagesState);
  }, [data, totalPagesState]);

  useEffect(() => {
    if (isLoadingStaffModuleDecentralization) {
      setLoadingTable(true);
    } else {
      setLoadingTable(false);
    }
  }, [isLoadingStaffModuleDecentralization]);

  useEffect(() => {
    setPaginationParams((prev) => {
      return {
        ...prev,
        page: 1,
      };
    });
    refetchStaffModuleDecentralization();
  }, [listStaffCodeSearch]);

  useEffect(() => {
    const getAllColumnsAvailable = async () => {
      try {
        const response = await getAllRoleAvailable();
        if (response.data.data) {
          const columns = response.data.data;
          const columnsRole = createColumns(columns);
          setColumns(columnsRole);
        }
      } catch (e) {
        toast.error("Lỗi lấy dữ liệu");
      }
    };
    getAllColumnsAvailable();
  }, []);

  const handleSearch = debounce((value: string[]) => {
    setListStaffCodeSearch(
      value
        .map((item) => item.trim())
        .filter((item) => item !== "")
        .join(",")
    );
  }, 500);

  const handleCheckboxChange = (
    staffId: number,
    roleCode: string,
    checked: boolean
  ) => {
    setDataSource((prevDataSource) => {
      return prevDataSource.map((item) => {
        if (item.staffId === staffId) {
          setIsDataChanged(true);
          return {
            ...item,
            [roleCode]: checked ? roleCode : "",
          };
        }
        return item;
      });
    });
  };

  const transformData = (data: any) => {
    return data.map((item: any) => {
      return {
        key: item.staffId,
        staffId: item.staffId,
        staffInfo: item.staffInfo,
        ...(item.listRoleCode
          ? item.listRoleCode
              .split(",")
              .map((item: string) => item.trim())
              .reduce((acc: any, cur: any) => {
                return {
                  ...acc,
                  [cur]: cur,
                };
              }, {})
          : {}),
      };
    });
  };

  const createColumns = (columns: ColumnsRole[]) => {
    const staffColumns = {
      title: "Thông Tin Nhân Viên",
      dataIndex: "staffInfo",
      key: "staffInfo",
      width: 180,
      style: {
        whiteSpace: "nowrap",
        overflow: "hidden",
        textOverflow: "ellipsis",
      },
      render: (text: any, record: any) => {
        return <span>{record?.staffInfo}</span>;
      },
    };

    const roleColumns = columns?.map((item) => {
      return {
        title: () => {
          return (
            <Tooltip title={item.name}>
              <span>{item.name}</span>
            </Tooltip>
          );
        },
        dataIndex: item.code,
        key: item.code,
        width: 250,
        ellipsis: true,
        align: "center",
        render: (text: any, record: any) => {
          return permissions?.includes(PERMISSIONS.ADMIN) ? (
            <Checkbox
              className='text-[#052C65] p-4'
              key={`${record.staffId}-${item.code}`}
              checked={record[item.code]}
              onChange={(e) =>
                handleCheckboxChange(
                  record.staffId,
                  item.code,
                  e.target.checked
                )
              }
            />
          ) : (
            <Checkbox
              className='text-[#052C65] p-4'
              key={`${record.staffId}-${item.code}`}
              checked={record[item.code]}
              onChange={(e) =>
                handleCheckboxChange(
                  record.staffId,
                  item.code,
                  e.target.checked
                )
              }
              disabled={
                item.code === PERMISSIONS.ADMIN ||
                item.code === PERMISSIONS.BAN_DAO_TAO_HO
              }
            />
          );
        },
      };
    });
    return [staffColumns, ...roleColumns];
  };

  const updateDecentralization = async () => {
    try {
      const data = {
        moduleId,
        staffs: dataSource.map((item) => {
          return {
            staffId: item.staffId,
            roles: Object.keys(item)
              .filter(
                (key) =>
                  item[key] !== "" &&
                  key !== "staffInfo" &&
                  key !== "staffId" &&
                  key !== "key"
              )
              .join(","),
          };
        }),
      };
      await updateStaffModuleDecentralization(data);
      toast.success("Cập nhật thành công");
      setIsDataChanged(false);
    } catch (e) {
      toast.error("Lỗi cập nhật phân quyền");
    }
  };

  const buttonSave = () => {
    return (
      <Button
        type='primary'
        className='text-white bg-[#052C65] hover:bg-[#052C65] animation duration-300 ease-in-out transform hover:scale-110'
        onClick={() => {
          Modal.confirm({
            title: "Thông báo",
            content: "Bạn có muốn lưu thay đổi không?",
            onOk: updateDecentralization,
            onCancel: () => {
              handleCancelChanges();
            },
            centered: true,
            okButtonProps: {
              className: "text-white bg-[#052C65] hover:bg-[#052C65]",
            },
            cancelButtonProps: {
              className: "text-white bg-[#FFA500] hover:bg-[#FFA500]",
            },
            okText: "Lưu Thay Đổi",
            cancelText: "Hủy Thay Đổi",
          });
        }}
        icon={<FaRegSave />}
      >
        Lưu Thay Đổi
      </Button>
    );
  };

  const handleCancelChanges = () => {
    setDataSource(initialDataSource);
    setIsDataChanged(false);
  };

  const buttonCancel = () => {
    return (
      <Button
        onClick={handleCancelChanges}
        className='animation duration-300 ease-in-out transform hover:scale-110 mr-[10px] bg-[#FFA500] hover:bg-[#FFA500] text-white hover:border-transparent'
        icon={<GiCardDiscard />}
      >
        Hủy Thay Đổi
      </Button>
    );
  };

  // useEffect(() => {
  //   if (isDataChanged) {
  //     Modal.confirm({
  //       title: "Thông báo",
  //       content: "Bạn có muốn lưu thay đổi không?",
  //       onOk: updateDecentralization,
  //       onCancel: () => {
  //         setIsDataChanged(false);
  //       },
  //     });
  //     return;
  //   }
  //   refetchStaffModuleDecentralization();
  // }, [
  //   isDataChanged,
  //   paginationParams,
  //   refetchStaffModuleDecentralization,
  //   updateDecentralization,
  // ]);

  // OLD LOGIC
  // const fetchStaffModuleDecentralization = async () => {
  //   try {
  //     setLoadingTable(true);
  //     const response = await getStaffModuleDecentralization(
  //       moduleId,
  //       listStaffCodeSearch,
  //       paginationParams
  //     );
  //     if (response.data.data) {
  //       const data = response.data.data;
  //       setDataSource(transformData(data));
  //       setInitialDataSource(transformData(data));
  //       setTotalPages(response.data.totalPages);
  //     }
  //   } catch (e) {
  //     toast.error("Lỗi lấy dữ liệu");
  //   } finally {
  //     setLoadingTable(false);
  //   }
  // };

  // useEffect(() => {
  //   const getAllColumnsAvailable = async () => {
  //     try {
  //       const response = await getAllRoleAvailable();
  //       if (response.data.data satisfies ColumnsRole[]) {
  //         const columns = response.data.data;
  //         const columnsRole = createColumns(columns);
  //         setColumns(columnsRole);
  //       }
  //     } catch (e) {
  //       toast.error("Lỗi lấy dữ liệu");
  //     }
  //   };
  //   getAllColumnsAvailable();
  //   fetchStaffModuleDecentralization();
  // }, []);

  // useEffect(() => {
  //   if (isDataChanged) {
  //     Modal.confirm({
  //       title: "Thông báo",
  //       content: "Bạn có muốn lưu thay đổi không?",
  //       onOk: updateDecentralization,
  //       onCancel: () => {
  //         setIsDataChanged(false);
  //       },
  //     });
  //     return;
  //   }
  //   fetchStaffModuleDecentralization();
  // }, [paginationParams]);

  // useEffect(() => {
  //   fetchStaffModuleDecentralization();
  // }, [listStaffCodeSearch]);

  return {
    columns,
    dataSource,
    totalPages,
    paginationParams,
    setPaginationParams,
    buttonSave,
    isDataChanged,
    buttonCancel,
    handleSearch,
    loadingTable,
  };
};
