import ModalUploadFile from "#components/common/ModalUploadFile";
import Container from "#components/ui/Container";
import { PERMISSIONS } from "#constant/index";
import useToggle from "#hooks/useToggle";
import ModalChooseCampusExport from "#pages/staff/components/ModalChooseCampusExport";
import { Button } from "antd";
import { FaDownload, FaFilter, FaListAlt, FaUpload } from "react-icons/fa";
import { GiTeacher } from "react-icons/gi";
import { IoIosAddCircle } from "react-icons/io";
import { useNavigate } from "react-router-dom";
import StaffFilter from "./components/StaffFilter";
import TableStaff from "./components/TableStaff";
import { useFiles } from "./hooks/useFiles";
import { useStaffs } from "./hooks/useStaffs";

const Staff = () => {
  const {
    staffs,
    paginationParams,
    setPaginationParams,
    totalPages,
    handleSearch,
    handleDelete,
    loading: tableLoading,
    isChangeStatusLoading,
    campusCode,
    permissions,
  } = useStaffs();

  const {
    downloadTemplate,
    uploadFile,
    downloadCurrentStaffData,
    loadingState,
    loadingStateTemplate,
  } = useFiles();

  const navigate = useNavigate();

  const { value: openModal, setValue: setOpenModal } = useToggle();

  const { value: openModalExport, setValue: setOpenModalExport } = useToggle();

  return (
    <Container>
      <h2 className='flex items-center p-4 gap-3'>
        <GiTeacher className='mr-2' color='#052C65' size={30} />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý nhân viên
        </span>
      </h2>
      <div className='shadow-lg mb-5 p-3 rounded-lg'>
        <h3 className='text-2xl font-semibold flex items-center gap-2'>
          <FaFilter size={25} className='mr-2' color='#052C65' />
          <span className='text-[#052c65] text-xl'>Tìm kiếm nhân viên</span>
        </h3>
        <StaffFilter onSearch={handleSearch} />
      </div>
      <div className='shadow-lg p-3 rounded-lg'>
        <div className='mb-3 flex items-center justify-between'>
          <h3 className='text-2xl font-semibold flex items-center gap-2'>
            <FaListAlt size={25} color='#052c65' />
            <span className='text-[#052c65] text-xl'>Danh sách nhân viên</span>
          </h3>
          <div className='mb-3 flex flex-wrap justify-end gap-3'>
            <Button
              onClick={() => setOpenModal(true)}
              className='bg-[#052C65] text-[white]'
              icon={<FaUpload />}
            >
              Tải lên
            </Button>
            <Button
              onClick={() => {
                if (permissions.includes(PERMISSIONS.BAN_DAO_TAO_HO)) {
                  setOpenModalExport(true);
                } else {
                  downloadCurrentStaffData(campusCode);
                }
              }}
              loading={loadingState}
              className='bg-[#052C65] text-[white]'
              icon={<FaDownload />}
            >
              Tải danh sách nhân viên
            </Button>
            <Button
              onClick={downloadTemplate}
              type='primary'
              loading={loadingStateTemplate}
              className='bg-[#052C65] text-[white] hover:bg-[#052C65]'
              icon={<FaDownload />}
            >
              Tải xuống mẫu
            </Button>
            <Button
              onClick={() => navigate("add-staff")}
              className='bg-[#052C65] text-[white] '
              icon={<IoIosAddCircle />}
            >
              Thêm Nhân Viên
            </Button>
          </div>
        </div>
        <TableStaff
          dataSource={staffs}
          loading={tableLoading}
          {...{
            handleDelete,
            paginationParams,
            setPaginationParams,
            totalPages,
            isChangeStatusLoading,
          }}
        />
      </div>
      <ModalUploadFile
        onClose={() => setOpenModal(false)}
        uploadFile={async (file: File) => await uploadFile(file)}
        visible={openModal}
      />
      <ModalChooseCampusExport
        isOpen={openModalExport}
        onClose={() => setOpenModalExport(false)}
        onConfirm={(campusCode: string) => {
          setOpenModalExport(false);
          downloadCurrentStaffData(campusCode);
        }}
      />
    </Container>
  );
};

export default Staff;
