import IdentityTable from "#components/ui/Table/IdentityTable";
import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  DepartmentPaginationParams,
  FetchListDepartmentResponse,
} from "#service/api/department.api";
import { ContainerOutlined, EyeOutlined } from "@ant-design/icons";
import { faCircleInfo } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Tag, Tooltip } from "antd";
import { ColumnType } from "antd/es/table";
import { Dispatch, SetStateAction, memo } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

const DepartmentTable = ({
  dataSource,
  paginationParams,
  setPaginationParams,
  totalPages,
  loading,
  handleOpen,
  handleUpdateDepartment,
  handleAddDepartment,
  handleTableChange,
}: {
  dataSource: FetchListDepartmentResponse[] | undefined;
  paginationParams: DepartmentPaginationParams;
  setPaginationParams: Dispatch<SetStateAction<DepartmentPaginationParams>>;
  totalPages: number | undefined;
  loading: boolean;
  handleOpen: any;
  handleUpdateDepartment: any;
  handleAddDepartment: any;
  handleTableChange: (pagination, filters, sorter) => void;
}) => {
  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const columnsDepartment: ColumnType<FetchListDepartmentResponse>[] = [
    {
      title: "#",
      dataIndex: "orderNumber",
      align: "center",
      key: "orderNumber",
      render: (text) => <span>{text}</span>,
      width: "5%",
      sorter: true,
    },
    {
      title: "Tên Bộ môn",
      key: "departmentName",
      sorter: true,
      render: (row) => (
        <span>
          {row.departmentName +
            " - " +
            (row.departmentCode ? row.departmentCode : "Chưa xác định")}
        </span>
      ),
      width: "20%",
    },
    {
      title: "Trạng thái",
      dataIndex: "departmentStatus",
      key: "departmentStatus",
      render: (text) => (
        <Tag color={text === "DELETED" ? "red" : "green"}>
          {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
        </Tag>
      ),
      align: "center",
      width: "20%",
    },
    {
      title: "Hành động",
      align: "center",
      render: (row) => (
        <div className='flex justify-center gap-2 items-center w-full text-center text-lg font-semibold text-primary-emphasis'>
          <Tooltip
            title='Quản Lý Chuyên Ngành Thuộc Bộ Môn'
            color='#496989'
            placement='leftTop'
          >
            <div>
              <Button
                icon={<ContainerOutlined />}
                size='small'
                type='primary'
                className='bg-[#496989] border-none p-4'
                onClick={() => handleOpen(row?.departmentId)}
              />
            </div>
          </Tooltip>
          {permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO) && (
            <Tooltip title='Chi tiết bộ môn' color='#052C65'>
              <div>
                <Button
                  icon={<EyeOutlined />}
                  size='small'
                  type='primary'
                  className='bg-[#052C65] text-white border-none p-4'
                  onClick={() => handleUpdateDepartment(row)}
                />
              </div>
            </Tooltip>
          )}
          <Link to={`/management/manage-department-campus/${row.departmentId}`}>
            <Tooltip title='Chi tiết bộ môn cơ sở' color='#6c757d'>
              <Button
                size='small'
                className='bg-[#6c757d] border-none p-4'
                type='primary'
                icon={
                  <FontAwesomeIcon
                    icon={faCircleInfo}
                    size='lg'
                    color='#ffff'
                  />
                }
              />
            </Tooltip>
          </Link>
        </div>
      ),
      width: "30%",
    },
  ];

  return (
    <IdentityTable
      columns={columnsDepartment}
      dataSource={dataSource || []}
      {...{
        paginationParams,
        setPaginationParams,
        totalPages,
        loading,
        handleTableChange,
      }}
    />
  );
};

export default memo(DepartmentTable);
