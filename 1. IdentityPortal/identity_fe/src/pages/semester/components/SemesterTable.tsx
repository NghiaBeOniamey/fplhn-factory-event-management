import IdentityTable from "#components/ui/Table/IdentityTable";
import {
  FetchListSemesterResponse,
  SemesterPaginationParams,
} from "#service/api/semester.api";
import { Button } from "antd";
import { ColumnType } from "antd/es/table";
import dayjs from "dayjs";
import { Dispatch } from "react";
import { BsFillPencilFill } from "react-icons/bs";

const SemesterTable = ({
  data,
  paginationParams,
  setPaginationParams,
  totalPages,
  loading,
  handleEditSemester,
  handleTableChange,
}: {
  data: FetchListSemesterResponse[] | undefined;
  paginationParams: SemesterPaginationParams;
  setPaginationParams: Dispatch<SemesterPaginationParams>;
  totalPages: number | undefined;
  loading: boolean;
  handleEditSemester: (id: number) => void;
  handleTableChange: (pagination, filters, sorter) => void;
}) => {
  const columns: ColumnType<FetchListSemesterResponse>[] = [
    {
      title: "#",
      dataIndex: "orderNumber",
      key: "orderNumber",
      width: 50,
    },
    {
      title: "Tên học kỳ",
      dataIndex: "semesterName",
      key: "semesterName",
      sorter: true,
    },
    {
      title: "Ngày bắt đầu học kỳ",
      dataIndex: "startTime",
      render: (record) => dayjs.unix(record).format("DD/MM/YYYY"),
      key: "startTime",
      sorter: true,
    },
    {
      title: "Ngày kết thúc học kỳ",
      dataIndex: "endTime",
      render: (record) => dayjs.unix(record).format("DD/MM/YYYY"),
      key: "endTime",
      sorter: true,
    },
    {
      title: "Block 1",
      render: (record: FetchListSemesterResponse) =>
        `${dayjs
          .unix(record.startTimeFirstBlock)
          .format("DD/MM/YYYY")} - ${dayjs
          .unix(record.endTimeFirstBlock)
          .format("DD/MM/YYYY")}`,
    },
    {
      title: "Block 2",
      render: (record: FetchListSemesterResponse) =>
        `${dayjs
          .unix(record.startTimeSecondBlock)
          .format("DD/MM/YYYY")} - ${dayjs
          .unix(record.endTimeSecondBlock)
          .format("DD/MM/YYYY")}`,
    },
    {
      title: "Hành động",
      align: "center",
      render: (record: any) => (
        <div className='flex justify-center'>
          <Button
            type='primary'
            className='mr-1 bg-[#052C65] border-none p-4'
            size='small'
            onClick={() => {
              handleEditSemester(record.id);
            }}
            icon={<BsFillPencilFill color='white' size={10} />}
          />
        </div>
      ),
    },
  ];

  return (
    <IdentityTable
      loading={loading}
      columns={columns}
      dataSource={data}
      paginationParams={paginationParams}
      totalPages={totalPages}
      setPaginationParams={setPaginationParams}
      handleTableChange={handleTableChange}
    />
  );
};

export default SemesterTable;
