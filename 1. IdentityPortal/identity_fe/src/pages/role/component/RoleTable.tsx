import IdentityTable from "#components/ui/Table/IdentityTable";
import { FetchListRoleResponse } from "#service/api/role.api";
import { EyeOutlined } from "@ant-design/icons";
import { Button, Flex, Tag, Tooltip } from "antd";
import { memo } from "react";

const RoleTable = ({
  dataRole,
  totalPages,
  handleUpdateRole,
  paginationParams,
  setPaginationParams,
  handleTableChange,
  isLoadingRole,
}: {
  dataRole: FetchListRoleResponse[] | undefined;
  totalPages: number | undefined;
  handleUpdateRole: (row: any) => void;
  paginationParams: any;
  setPaginationParams: any;
  handleTableChange: (pagination: any, filters: any, sorter: any) => void;
  isLoadingRole: boolean;
  totalElements: number;
}) => {
  const columnsRole = [
    {
      title: "#",
      dataIndex: "orderNumber",
      align: "center",
      key: "id",
      sorter: true,
      width: "5%",
    },
    {
      title: "Mã vai trò",
      dataIndex: "roleCode",
      key: "roleCode",
      sorter: true,
      render: (text) => <span>{text}</span>,
      width: "20%",
    },
    {
      title: "Tên vai trò",
      dataIndex: "roleName",
      key: "roleName",
      sorter: true,
      render: (text) => <span>{text}</span>,
      width: "20%",
    },
    {
      title: "Trạng thái",
      dataIndex: "roleStatus",
      key: "roleStatus",
      render: (text) => (
        <Tag color={text === "DELETED" ? "orange" : "green"}>
          {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
        </Tag>
      ),
      align: "center",
      width: "20%",
    },
    {
      title: "Hành động",
      key: "action",
      align: "center",
      render: (row) => (
        <Flex gap='small' wrap='wrap' justify='center'>
          <Tooltip title='Chi tiết vai trò' color='#052C65'>
            <div>
              <Button
                icon={<EyeOutlined />}
                size='small'
                type='primary'
                className='bg-[#052c65] text-white border-none p-4'
                onClick={() => handleUpdateRole(row)}
              />
            </div>
          </Tooltip>
        </Flex>
      ),
      center: "true",
      width: "30%",
    },
  ];

  return (
    <IdentityTable
      loading={isLoadingRole}
      columns={columnsRole}
      dataSource={dataRole ?? []}
      handleTableChange={handleTableChange}
      paginationParams={paginationParams}
      totalPages={totalPages}
      setPaginationParams={setPaginationParams}
      tableLayout='fixed'
    />
  );
};

export default memo(RoleTable);
