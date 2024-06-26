import IdentityTable from "#components/ui/Table/IdentityTable";
import {
  FetchListStudent,
  StudentPaginationParams,
} from "#service/api/student.api";
import {
  faCircleInfo,
  faUser,
  faUserSecret,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Flex, Popconfirm, Tag, Tooltip } from "antd";
import { Dispatch, SetStateAction, memo } from "react";

const StudentTable = ({
  dataStudent,
  isLoadingStudent,
  showModalAdd,
  showModalUpdate,
  handleTableChange,
  paginationParams,
  setPaginationParams,
  totalPages,
  handleChangeStatus,
}: {
  dataStudent: FetchListStudent[] | undefined;
  isLoadingStudent: boolean;
  showModalAdd: () => void;
  showModalUpdate: (id: number) => void;
  paginationParams: StudentPaginationParams;
  setPaginationParams: Dispatch<SetStateAction<StudentPaginationParams>>;
  totalPages: number | undefined;
  handleTableChange: (pagination, filters, sorter) => void;
  handleChangeStatus: (id: number) => void;
}) => {
  const columnsStudent = [
    {
      title: "#",
      dataIndex: "orderNumber",
      align: "center",
      key: "orderNumber",
      render: (text: string) => <span>{text}</span>,
      width: "5%",
    },
    {
      title: "Mã sinh viên",
      dataIndex: "studentCode",
      key: "studentCode",
      sorter: true,
      render: (text: string) => {
        return text === null || text === "" || text === "-" ? (
          <Tag color='warning'>Chưa có mã sinh viên</Tag>
        ) : (
          <span>{text}</span>
        );
      },
      width: "12%",
    },
    {
      title: "Tên sinh viên",
      dataIndex: "studentName",
      key: "studentName",
      sorter: true,
      render: (text: string) => <span>{text}</span>,
      width: "20%",
    },
    {
      title: "Email",
      dataIndex: "studentMail",
      key: "studentEmail",
      sorter: true,
      render: (text: string) => {
        return text === null || text === "" || text === "-" ? (
          <Tag color='warning'>Chưa có email</Tag>
        ) : (
          <span>{text}</span>
        );
      },
      width: "15%",
    },
    {
      title: "Bộ Môn - Cơ Sở",
      dataIndex: "departmentNameAndCampusName",
      key: "departmentNameAndCampusName",
      sorter: true,
      align: "center",
      render: (text: string) => {
        if (text === null || text === "" || text === "-") {
          return <Tag color='warning'>Chưa có bộ môn</Tag>;
        }
        return <Tag color='green'>{text}</Tag>;
      },
      width: "20%",
    },
    {
      title: "Trạng Thái",
      dataIndex: "studentStatus",
      key: "studentStatus",
      sorter: true,
      render: (text: string) => (
        <Tag color={text === "NOT_DELETED" ? "green" : "red"}>
          {text === "NOT_DELETED" ? "Họat động" : "Khóa"}
        </Tag>
      ),
      width: "10%",
    },
    {
      title: "Hành động",
      align: "center",
      render: (_: string, row: FetchListStudent) => (
        <Flex gap='small' wrap='wrap' justify='center'>
          <Tooltip title='Chi tiết sinh viên' color='#052C65'>
            <div>
              <Button
                icon={<FontAwesomeIcon icon={faCircleInfo} />}
                size='small'
                type='primary'
                onClick={() => showModalUpdate(row.studentId)}
                className='bg-[#052C65] text-white border-none p-4'
              />
            </div>
          </Tooltip>
          <Tooltip title='Chuyển đổi trạng thái' color='orange'>
            <div>
              <Popconfirm
                placement='left'
                title='Thông báo'
                description='Bạn có muốn cập nhật trạng thái không ?'
                okText='Có'
                cancelText='Không'
                onConfirm={() => handleChangeStatus(row.studentId)}
              >
                <Button
                  size='small'
                  type='primary'
                  icon={
                    row.studentStatus === "DELETED" ? (
                      <FontAwesomeIcon icon={faUserSecret} />
                    ) : (
                      <FontAwesomeIcon icon={faUser} />
                    )
                  }
                  className='bg-orange-500 text-white border-none p-4'
                />
              </Popconfirm>
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
      loading={isLoadingStudent}
      columns={columnsStudent}
      dataSource={dataStudent ?? []}
      handleTableChange={handleTableChange}
      paginationParams={paginationParams}
      totalPages={totalPages}
      setPaginationParams={setPaginationParams}
    />
  );
};

export default memo(StudentTable);
