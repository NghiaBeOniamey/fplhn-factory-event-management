import Container from "#components/ui/Container";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import StudentFilter from "#pages/student/components/StudentFilter";
import StudentTable from "#pages/student/components/StudentTable";
import {
  useGetAllStudent,
  useUpdateStudentStatus,
} from "#service/action/student.action";
import { StudentPaginationParams } from "#service/api/student.api";
import {
  faCirclePlus,
  faGraduationCap,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Tooltip, message } from "antd";
import { memo, useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { FaFilter } from "react-icons/fa6";
import { useSelector } from "react-redux";
import { useImmer } from "use-immer";
import StudentAddModal from "./components/AddStudent";
import StudentUpdateModal from "./components/UpdateStudent";

const ManageStudent = () => {
  const campusCode = useSelector(
    (state: RootState) => state?.auth?.authorization?.userInfo?.campusCode
  );

  const [paginationParams, setPaginationParams] =
    useImmer<StudentPaginationParams>({
      page: 1,
      size: 10,
      campusCode: campusCode || null,
    });

  const {
    data,
    isLoading: loadingTable,
    refetch,
  } = useGetAllStudent(paginationParams);

  const [isModalAddOpen, setIsModalAddOpen] = useState(false);

  const showModalAdd = () => setIsModalAddOpen(true);

  const handleCancelAdd = () => {
    setIsModalAddOpen(false);
    refetch();
  };

  const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);

  const [studentId, setStudentId] = useState<number | null>();

  const { mutateAsync: updateStudentStatus } = useUpdateStudentStatus();

  const showModalUpdate = (id: number) => {
    setStudentId(id);
    setIsModalUpdateOpen(true);
  };

  const handleCancelUpdate = () => {
    setIsModalUpdateOpen(false);
  };

  const dataStudent = useMemo(() => {
    return data?.data?.data?.map((item) => {
      return {
        ...item,
        key: item.studentId,
      };
    });
  }, [data]);

  const totalPages = useMemo(() => {
    return data?.data?.totalPages || 0;
  }, [data]);

  const handleSubmit = useCallback(
    (values: any) => {
      setPaginationParams((draft) => {
        draft.page = 1;
        // draft.size = 10;
        draft.campusCode = campusCode || null;
        draft.studentCode = values?.studentCode;
        draft.studentName = values?.studentName;
        draft.studentMail = values?.studentMail;
        draft.listDepartmentId = values?.listDepartmentId?.join(",");
      });
    },
    [campusCode, setPaginationParams]
  );

  const handleReset = useCallback(() => {
    setPaginationParams((draft) => {
      draft.page = 1;
      draft.size = 10;
      draft.campusCode = campusCode || null;
      draft.listDepartmentId = null;
    });
  }, [campusCode, setPaginationParams]);

  const handleChangeStatus = useCallback(
    async (id: number) => {
      try {
        const res = await updateStudentStatus(id);
        message.success(res?.message);
      } catch (error) {
        message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
      }
    },
    [updateStudentStatus]
  );

  const handleTableChange = useCallback(
    (_, __, sorter) => {
      setPaginationParams((draft) => {
        draft.sortBy = sorter.field;
        draft.orderBy = sorter.order === "ascend" ? "ASC" : "DESC";
      });
    },
    [setPaginationParams]
  );

  return (
    <Container>
      <h2 className='flex items-center p-4 gap-3'>
        <FontAwesomeIcon icon={faGraduationCap} size='2xl' color='#052C65' />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý sinh viên
        </span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <h3 className='text-xl font-semibold text-[#052c65] flex items-center'>
          <FaFilter className='inline-block mr-2' />
          Tìm kiếm sinh viên
        </h3>
        <StudentFilter onSubmit={handleSubmit} onReset={handleReset} />
      </Container>
      <Container className='shadow-lg p-4 rounded-lg mt-5'>
        <Row className='flex justify-between items-center text-[#052C65]'>
          <h3 className='flex justify-between items-center gap-3 text-[#052C65] text-xl font-medium'>
            <FaListAlt size={25} />
            Danh sách sinh viên
          </h3>
          <Tooltip title='Thêm sinh viên' color='#052c65'>
            <div>
              <Button
                type='primary'
                icon={<FontAwesomeIcon icon={faCirclePlus} />}
                size='middle'
                className='text-[#ffff] bg-[#052C65] border-[#052C65]'
                onClick={showModalAdd}
              >
                Thêm sinh viên
              </Button>
            </div>
          </Tooltip>
        </Row>
        <StudentTable
          isLoadingStudent={loadingTable}
          {...{
            dataStudent,
            paginationParams,
            totalPages,
            setPaginationParams,
            showModalAdd,
            showModalUpdate,
            handleTableChange,
            handleChangeStatus,
          }}
        />
      </Container>
      <StudentAddModal
        isOpenAdd={isModalAddOpen}
        onCancelAdd={handleCancelAdd}
      />
      <StudentUpdateModal
        studentId={studentId}
        isOpenUpdate={isModalUpdateOpen}
        onCancelUpdate={handleCancelUpdate}
      />
    </Container>
  );
};

export default memo(ManageStudent);
