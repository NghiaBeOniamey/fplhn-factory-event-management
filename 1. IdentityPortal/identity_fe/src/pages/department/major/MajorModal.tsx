import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Modal } from "antd";
import { useSelector } from "react-redux";
import { useMajor } from "../hook/useMajor";
import MajorAddUpdate from "./MajorAddUpdate";
import MajorFilter from "./MajorFilter";
import MajorTable from "./MajorTable";

export interface MajorModalProps {
  open: boolean;
  handleClose: () => void;
  departmentId: number;
}

const MajorModal = ({ open, handleClose, departmentId }: MajorModalProps) => {
  const {
    data: majorData,
    paginationParams,
    setPaginationParams,
    totalPages,
    form,
    handleOpenModalUpdate,
    handleOpenModalAdd,
    handleChangeMajorStatus,
    handleFilter,
    handleClose: handleCloseModal,
    isOpen,
    majorId,
    handelAddMajor,
    handelUpdateMajor,
    handleSort,
    isLoading,
  } = useMajor(departmentId);

  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  return (
    <Modal
      title={false}
      open={open}
      onCancel={handleClose}
      footer={false}
      width={1000}
      destroyOnClose
      centered
      forceRender
    >
      <h3 className='mb-4 mt-2 text-[#052C65] flex items-center text-2xl'>
        Quản Lý Chuyên Ngành
      </h3>
      <div className='p-4 shadow-lg rounded-lg'>
        <h4 className='text-[#052C65] text-xl'>Tìm Kiếm Chuyên Ngành</h4>
        <MajorFilter handleFilter={handleFilter} />
      </div>
      <div className='p-4 shadow-lg rounded-lg mt-4'>
        {permissions.includes(PERMISSIONS.BAN_DAO_TAO_HO) && (
          <div className='flex justify-between items-center'>
            <h4 className='text-[#052C65] text-xl'>Danh Sách Chuyên Ngành</h4>
            <Button
              type='primary'
              onClick={handleOpenModalAdd}
              className='m-2 bg-[#052C65] text-[white]'
              icon={<FontAwesomeIcon icon={faPlus} />}
            >
              Tạo Chuyên Ngành
            </Button>
          </div>
        )}
        <MajorTable
          data={majorData}
          handleSortMajor={handleSort}
          {...{
            paginationParams,
            setPaginationParams,
            totalPages,
            handleOpenModalUpdate,
            handleChangeMajorStatus,
            isLoading,
          }}
        />
      </div>
      <MajorAddUpdate
        open={isOpen}
        handleAdd={handelAddMajor}
        handleUpdate={handelUpdateMajor}
        {...{
          form,
          handleClose: handleCloseModal,
          majorId,
        }}
      />
    </Modal>
  );
};

export default MajorModal;
