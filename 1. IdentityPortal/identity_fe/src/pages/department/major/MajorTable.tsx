import IdentityTable from "#components/ui/Table/IdentityTable";
import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  FetchListMajorResponse,
  MajorPaginationParams,
} from "#service/api/major.api";
import { Button, Tag, Tooltip } from "antd";
import { Dispatch, SetStateAction, memo, useMemo } from "react";
import { FaEdit } from "react-icons/fa";
import { TbExchange } from "react-icons/tb";
import { useSelector } from "react-redux";

const MajorTable = ({
  data,
  paginationParams,
  setPaginationParams,
  totalPages,
  handleOpenModalUpdate,
  handleChangeMajorStatus,
  handleSortMajor,
  isLoading,
}: {
  data: FetchListMajorResponse[] | undefined;
  paginationParams: MajorPaginationParams;
  setPaginationParams: Dispatch<SetStateAction<MajorPaginationParams>>;
  totalPages: number | undefined;
  handleOpenModalUpdate: (majorId: number) => void;
  handleChangeMajorStatus: (majorId: number) => void;
  handleSortMajor: (sort: string) => void;
  isLoading: boolean;
}) => {
  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const columnsMajor = useMemo(() => {
    const columns = [
      {
        title: "#",
        dataIndex: "orderNumber",
        key: "orderNumber",
        width: 50,
      },
      {
        title: "Mã Chuyên Ngành",
        dataIndex: "majorCode",
        key: "majorCode",
        sorter: true,
        width: 200,
      },
      {
        title: "Tên Chuyên Ngành",
        dataIndex: "majorName",
        key: "majorName",
        sorter: true,
        width: 200,
      },
      {
        title: "Trạng Thái",
        dataIndex: "majorStatus",
        key: "majorStatus",
        align: "center",
        width: 150,
        render: (majorStatus: string) => (
          <div className='flex justify-center'>
            {majorStatus === "NOT_DELETED" ? (
              <Tag color='success'>Hoạt Động</Tag>
            ) : (
              <Tag color='warning'>Không Hoạt Động</Tag>
            )}
          </div>
        ),
      },
      {
        title: "Hành Động",
        align: "center",
        hidden: !permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO),
        render: (record: any) => (
          <div className='flex justify-center gap-2'>
            <Tooltip
              title='Chỉnh sửa thông tin chuyên ngành'
              color='#052C65'
              key='edit'
            >
              <div>
                <Button
                  onClick={() => handleOpenModalUpdate(record.majorId)}
                  size='small'
                  className='bg-[#052C65] border-none p-4'
                  icon={<FaEdit color='white' />}
                />
              </div>
            </Tooltip>
            <Tooltip
              title='Thay Đổi Trạng Thái Chuyên Ngành'
              color='orange'
              key='status'
            >
              <div>
                <Button
                  onClick={() => handleChangeMajorStatus(record.majorId)}
                  className='bg-[orange] border-none p-4'
                  size='small'
                  icon={<TbExchange color='white' />}
                />
              </div>
            </Tooltip>
          </div>
        ),
      },
    ];

    return columns.filter((column) => !column.hidden);
  }, [handleChangeMajorStatus, handleOpenModalUpdate, permissions]);

  return (
    <IdentityTable
      columns={columnsMajor}
      {...{
        dataSource: data,
        paginationParams,
        setPaginationParams,
        totalPages,
        handleTableChange: handleSortMajor,
        loading: isLoading,
      }}
      size='small'
    />
  );
};

export default memo(MajorTable);
