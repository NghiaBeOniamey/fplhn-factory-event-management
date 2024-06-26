import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import { Button, Modal } from "antd";
import { useSelector } from "react-redux";
import { useMajorCampus } from "../hook/useMajorCampus";
import AddMajorCampusModal from "./AddMajorCampusModal";
import MajorCampusFilter from "./MajorCampusFilter";
import MajorCampusTable from "./MajorCampusTable";

type MajorCampusModalProps = {
  open: boolean;
  handleClose: () => void;
  departmentCampusId: number;
};

const MajorCampusModal = (props: MajorCampusModalProps) => {
  const {
    paginationParams,
    setPaginationParams,
    majorData,
    totalPages,
    handleFilter,
    handleOpenModalAdd,
    handleOpenModalUpdate,
    handleChangeMajorStatus,
    isModalVisible,
    majorCampusId,
    setFalse,
    form,
    handleAdd,
    handleUpdate,
    campusCode,
  } = useMajorCampus(props.departmentCampusId);

  const { permissions, userInfo } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  return (
    <Modal
      title={false}
      open={props.open}
      onCancel={props.handleClose}
      footer={false}
      width={1000}
      destroyOnClose
      centered
    >
      <h3 className='mb-4 mt-2 text-[#052C65] text-xl'>
        Quản Lý Chuyên Ngành Theo Cơ Sở
      </h3>
      <div className='p-4 shadow-lg rounded-lg'>
        <h4 className='text-[#052C65] text-lg'>
          Tìm Kiếm Chuyên Ngành Theo Cơ Sở
        </h4>
        <MajorCampusFilter handleFilter={handleFilter} />
      </div>
      <div className='p-4 shadow-lg rounded-lg mt-4'>
        <div className='flex justify-between'>
          <h4 className='text-[#052C65] text-lg'>Danh Sách Chuyên Ngành</h4>
          {/*{permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO) ||*/}
          {/*  (userInfo?.campusCode === campusCode && (*/}
              <Button
                type='primary'
                onClick={handleOpenModalAdd}
                className='m-2 text-[#fff] bg-[#052C65] '
              >
                Tạo Chuyên Ngành Theo Cơ Sở
              </Button>
            {/*))}*/}
        </div>
        <MajorCampusTable
          {...{
            data: majorData,
            paginationParams,
            setPaginationParams,
            totalPages,
            handleOpenModalUpdate,
            handleChangeMajorStatus,
          }}
        />
      </div>
      <AddMajorCampusModal
        {...{
          open: isModalVisible,
          majorCampusId,
          handleClose: setFalse,
          form,
          handleAdd,
          handleUpdate,
        }}
      />
    </Modal>
  );
};

export default MajorCampusModal;
