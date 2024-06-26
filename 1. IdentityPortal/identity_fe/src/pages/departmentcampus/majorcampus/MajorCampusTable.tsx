import IdentityTable from "#components/ui/Table/IdentityTable";
import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  FetchMajorCampusResponse,
  PaginationParamsMajorCampus,
} from "#service/api/majorcampus.api";
import { WarningOutlined } from "@ant-design/icons";
import { Button, Modal, Tag, Tooltip } from "antd";
import { Dispatch, SetStateAction, memo, useMemo } from "react";
import { FaEdit } from "react-icons/fa";
import { TbExchange } from "react-icons/tb";
import { useSelector } from "react-redux";

const MajorCampusTable = ({
  data,
  paginationParams,
  setPaginationParams,
  totalPages,
  handleOpenModalUpdate,
  handleChangeMajorStatus,
}: {
  data: FetchMajorCampusResponse[] | undefined;
  paginationParams: PaginationParamsMajorCampus;
  setPaginationParams: Dispatch<SetStateAction<PaginationParamsMajorCampus>>;
  totalPages: number | undefined;
  handleOpenModalUpdate: (majorCampusId: number) => void;
  handleChangeMajorStatus: (majorCampusId: number) => void;
}) => {
  const { permissions, userInfo } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const columns = useMemo(() => {
    return [
      {
        title: "#",
        dataIndex: "orderNumber",
        key: "orderNumber",
        width: 50,
      },
      {
        title: "Thông Tin Chuyên Ngành",
        dataIndex: "majorCodeName",
        key: "majorCodeName",
        width: 200,
      },
      {
        title: "Trưởng Môn Chuyên Ngành",
        dataIndex: "headMajorCodeName",
        key: "headMajorCodeName",
        width: 200,
      },
      {
        title: "Trạng Thái",
        dataIndex: "majorCampusStatus",
        key: "majorCampusStatus",
        width: 150,
        render: (text: any) => {
          return (
            <Tag color={text === "NOT_DELETED" ? "green" : "red"}>
              {text === "NOT_DELETED" ? "Hoạt động" : "Ngừng hoạt động"}
            </Tag>
          );
        },
      },
      {
        title: "Hành Động",
        align: "center",
        key: "action",
        width: 100,
        render: (text: any, record: any) => {
          return (
            <div className='flex justify-center gap-2'>
              {permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO) ||
              userInfo?.campusCode === record?.campusCode ? (
                <>
                  <Button
                    type='primary'
                    onClick={() => handleOpenModalUpdate(record.majorCampusId)}
                    className='bg-[#052C65] text-white border-none p-4'
                    icon={<FaEdit />}
                  />
                  <Button
                    type='primary'
                    onClick={() => {
                      Modal.confirm({
                        title: "Xác nhận",
                        centered: true,
                        content:
                          record.majorCampusStatus === "NOT_DELETED" ? (
                            <span>
                              Bạn có chắc chắn muốn ngừng hoạt động chuyên ngành
                              này?
                            </span>
                          ) : (
                            <span>
                              Bạn có chắc chắn muốn mở hoạt động chuyên ngành
                              này?
                            </span>
                          ),
                        onOk() {
                          handleChangeMajorStatus(record.majorCampusId);
                        },
                        okText: "Xác nhận",
                        cancelText: "Hủy",
                        okButtonProps: {
                          className: "bg-[orange] text-white font-semibold",
                        },
                        cancelButtonProps: {
                          className: "bg-[gray] text-white font-semibold",
                        },
                      });
                    }}
                    className='border-none p-4 bg-[orange] text-white'
                    size='small'
                    icon={<TbExchange />}
                  />
                </>
              ) : (
                <Tooltip
                  title='Bạn không có quyền thao tác ở cơ sở này'
                  placement='top'
                  color='red'
                >
                  <div>
                    <Tag
                      color='red'
                      icon={<WarningOutlined />}
                      className='text-2xl'
                    >
                      <WarningOutlined />
                    </Tag>
                  </div>
                </Tooltip>
              )}
            </div>
          );
        },
      },
    ];
  }, [
    handleChangeMajorStatus,
    handleOpenModalUpdate,
    permissions,
    userInfo?.campusCode,
  ]);

  return (
    <IdentityTable
      {...{
        columns,
        dataSource: data,
        paginationParams,
        setPaginationParams,
        totalPages,
      }}
    />
  );
};

export default memo(MajorCampusTable);
