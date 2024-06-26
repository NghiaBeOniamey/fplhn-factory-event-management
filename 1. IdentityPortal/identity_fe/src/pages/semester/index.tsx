import { Button } from "antd";
import { useState } from "react";
import { FaFilter, FaList } from "react-icons/fa";
import { SiStudyverse } from "react-icons/si";
import useToggle from "../../hooks/useToggle";
import ModalModifySemester from "./components/ModalModifySemester";
import SemesterFilter from "./components/SemesterFilter";
import SemesterTable from "./components/SemesterTable";
import { useSemester } from "./hooks/useSemester";
import Container from "#components/ui/Container";
import { FaPlus } from "react-icons/fa6";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const Semester = () => {
  const {
    semesters,
    totalPages,
    isLoading,
    setPaginationParams,
    paginationParams,
    setSearchValue,
    handleTableChange,
  } = useSemester();

  const { value: isModalAddOpen, toggle } = useToggle();

  const [semesterId, setSemesterId] = useState<number | undefined>(undefined);

  const handleEditSemester = (id: number) => {
    setSemesterId(id);
    toggle();
  };

  const handleCloseModal = () => {
    setSemesterId(undefined);
    toggle();
  };

  return (
    <Container>
      <h2 className='flex items-center p-4 gap-3'>
        <SiStudyverse className='me-2' size={30} color='#052C65' />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý học kỳ
        </span>
      </h2>
      <Container className='shadow-lg p-3 rounded-lg mb-3 mt-5'>
        <h3 className='flex items-center'>
          <FaFilter size={20} className='me-2' color='#052C65' />
          <span className='text-[#052C65] ml-2 text-xl font-semibold'>
            Tìm kiếm học kỳ
          </span>
        </h3>
        <SemesterFilter {...{ setSearchValue }} />
      </Container>
      <Container className='shadow-lg p-3 rounded-lg mt-4'>
        <div className='flex justify-between items-center p-2'>
          <h3 className=' flex items-center'>
            <FaList size={20} className='me-2' color='#052C65' />
            <span className='text-xl font-semibold text-[#052c65] ml-2'>
              Danh sách học kỳ
            </span>
          </h3>
          <div className='flex justify-end'>
            <Button
              type='primary'
              onClick={toggle}
              className='text-[#fff] bg-[#052c65] border-[#052c65] rounded-lg hover:bg-[#052c65] hover:border-[#052c65] hover:text-[#fff]'
              icon={<FontAwesomeIcon icon={faCirclePlus} />}
            >
              Thêm học kỳ
            </Button>
          </div>
        </div>
        <SemesterTable
          data={semesters}
          {...{
            paginationParams,
            setPaginationParams,
            totalPages,
            loading: isLoading,
            handleEditSemester,
            handleTableChange,
          }}
        />
      </Container>
      {isModalAddOpen && (
        <ModalModifySemester
          isOpen={isModalAddOpen}
          handleCloseModal={handleCloseModal}
          semesterId={semesterId}
        />
      )}
    </Container>
  );
};

export default Semester;
