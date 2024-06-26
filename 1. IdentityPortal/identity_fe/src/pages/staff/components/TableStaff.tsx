import IdentityTable from "#components/ui/Table/IdentityTable";
import { StaffPagination } from "#service/api/staff.api";
import { Staff } from "#type/index.t";
import { Button, Tag, Tooltip } from "antd";
import PropTypes from "prop-types";
import { Dispatch, SetStateAction, memo } from "react";
import { BsFillPencilFill } from "react-icons/bs";
import { GoIssueReopened } from "react-icons/go";
import { useNavigate } from "react-router-dom";

interface TableStaffProps {
  dataSource: Staff[];
  paginationParams: StaffPagination;
  setPaginationParams: Dispatch<SetStateAction<StaffPagination>>;
  totalPages: number | null | undefined;
  handleDelete: (id: string | number) => void;
  loading?: boolean;
  isChangeStatusLoading?: boolean;
}

interface ColumnTable {
  title: string | undefined;
  width?: number | undefined;
  dataIndex?: string | undefined;
  key?: string | undefined;
  align?: "center" | "left" | "right" | undefined;
  sorter?: boolean | undefined;
  responsive?: string[] | undefined;
  fixed?: "left" | "right" | undefined;
  ellipsis?: boolean | undefined;
  render?: (text: any, record: any) => JSX.Element | undefined | null;
}

const TableStaff = ({
  dataSource,
  paginationParams,
  setPaginationParams,
  totalPages,
  handleDelete,
  loading,
  isChangeStatusLoading,
}: TableStaffProps): JSX.Element => {
  const navigate = useNavigate();

  const handleTableChange = (_: any, __: any, sorter: any) => {
    switch (sorter.order) {
      case "ascend":
        setPaginationParams({
          ...paginationParams,
          sortBy: sorter.field,
          orderBy: "ASC",
        });
        break;
      case "descend":
        setPaginationParams({
          ...paginationParams,
          sortBy: sorter.field,
          orderBy: "DESC",
        });
        break;
      default:
        setPaginationParams({
          ...paginationParams,
          sortBy: undefined,
          orderBy: undefined,
        });
        break;
    }
  };

  const columnsTable: ColumnTable[] = [
    {
      title: "#",
      dataIndex: "orderNumber",
      key: "orderNumber",
      align: "center",
      width: 60,
      ellipsis: true,
      responsive: ["md", "lg"],
    },
    {
      title: "Mã nhân viên",
      dataIndex: "staffCode",
      key: "staffCode",
      width: 170,
      sorter: true,
      responsive: ["md", "lg"],
    },
    {
      title: "Họ tên",
      dataIndex: "staffName",
      key: "tenNhanVien",
      width: 200,
      sorter: true,
      ellipsis: true,
      responsive: ["sm", "md", "lg"],
    },
    {
      title: "Email FE",
      dataIndex: "accountFe",
      key: "taiKhoanFE",
      width: 200,
      responsive: ["md", "lg"],
    },
    {
      title: "Email FPT",
      dataIndex: "accountFpt",
      key: "taiKhoanFPT",
      responsive: ["md", "lg"],
      width: 200,
      render: (text) =>
        text ? text : <Tag color='warning'>Chưa cập nhật !</Tag>,
    },
    {
      title: "Bộ Môn",
      dataIndex: "departmentName",
      key: "tenBoMon",
      responsive: ["md", "lg"],
      width: 230,
      ellipsis: true,
      render: (text) =>
        text && text !== "-" ? (
          text
        ) : (
          <Tag color='warning'>Chưa cập nhật !</Tag>
        ),
    },
    {
      title: "Cơ Sở",
      dataIndex: "campusName",
      key: "coSo",
      responsive: ["md", "lg"],
      width: 200,
      ellipsis: true,
      render: (text) =>
        text && text !== "-" ? (
          text
        ) : (
          <Tag color='warning'>Chưa cập nhật !</Tag>
        ),
    },
    {
      title: "Số điện thoại",
      dataIndex: "phoneNumber",
      key: "soDienThoai",
      responsive: ["md", "lg"],
      width: 150,
      render: (text) =>
        text ? text : <Tag color='warning'>Chưa cập nhật !</Tag>,
    },
    {
      title: "Trạng thái",
      dataIndex: "staffStatus",
      key: "trangThai",
      align: "center",
      width: 150,
      responsive: ["md", "lg"],
      render: (text) => {
        switch (text) {
          case "NOT_DELETED":
            return <Tag color='success'>Hoạt động</Tag>;
          case "DELETED":
            return <Tag color='error'>Ngưng hoạt động</Tag>;
          default:
            return <Tag color='warning'>Chưa cập nhật !</Tag>;
        }
      },
    },
    {
      title: "Hành Động",
      align: "center",
      responsive: ["md", "lg"],
      fixed: "right",
      width: 230,
      render: (record: any) => {
        return (
          <div className='flex justify-center'>
            <Tooltip
              title='Chỉnh Sửa Nhân Viên'
              placement='top'
              color='#052C65'
            >
              <div>
                <Button
                  onClick={() => {
                    navigate(`update-staff/${record.id}`);
                  }}
                  className='mr-1 bg-[#052C65] border-none p-4'
                  size='small'
                  icon={<BsFillPencilFill color='white' size={10} />}
                />
              </div>
            </Tooltip>
            <Tooltip title='Cập Nhật Trạng Thái' placement='top' color='orange'>
              <div>
                <Button
                  onClick={() => handleDelete(record.id)}
                  size='small'
                  className=' bg-orange-500 border-none p-4'
                  icon={<GoIssueReopened color='white' size={10} />}
                />
              </div>
            </Tooltip>
          </div>
        );
      },
    },
  ];

  return (
    <IdentityTable
      paginationParams={paginationParams}
      dataSource={dataSource}
      columns={columnsTable}
      loading={loading || isChangeStatusLoading}
      totalPages={totalPages as number | undefined | null}
      handleTableChange={handleTableChange}
      setPaginationParams={setPaginationParams}
    />
  );
};

TableStaff.propTypes = {
  danhSachNhanVien: PropTypes.array,
  paginationObj: PropTypes.object,
  setPaginationObj: PropTypes.func,
  totalPages: PropTypes.number,
  handleViewDetail: PropTypes.func,
  handleDelete: PropTypes.func,
};

export default memo(TableStaff, (prevProps, nextProps) => {
  return (
    prevProps.dataSource === nextProps.dataSource &&
    prevProps.loading === nextProps.loading &&
    prevProps.isChangeStatusLoading === nextProps.isChangeStatusLoading
  );
});
