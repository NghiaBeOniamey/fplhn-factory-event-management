import ModalUploadFile from "#components/common/ModalUploadFile";
import Container from "#components/ui/Container";
import IdentityTable from "#components/ui/Table/IdentityTable";
import useToggle from "#hooks/useToggle";
import { useFiles } from "#pages/module/hook/useFiles";
import { useSearchOptions } from "#pages/module/hook/useSearchOptions";
import { Button, Select } from "antd";
import { useRef } from "react";
import { FaDownload, FaUpload } from "react-icons/fa6";
import { IoIosArrowBack } from "react-icons/io";
import { Link, useLocation } from "react-router-dom";
import { useDecentralization } from "../hook/useDecentralization";

const Decentralization = () => {
  const {
    state: { moduleId, moduleName, moduleCode },
  } = useLocation();  

  const {
    columns,
    dataSource,
    totalPages,
    setPaginationParams,
    paginationParams,
    buttonSave,
    isDataChanged,
    buttonCancel,
    handleSearch,
    loadingTable,
  } = useDecentralization(moduleId);

  const inputFile = useRef<HTMLInputElement>(null);

  const { downloadTemplate, uploadFile, downloadTemplateLoading } = useFiles(
    inputFile,
    moduleCode
  );

  const { dataStaffSearch, handleSearchValue, isLoadingStaffInfoSearch } =
    useSearchOptions();

  const { value: modalVisible, setValue: setModalVisible } = useToggle();

  return (
    <Container>
      <div className='mb-4'>
        <div>
          <Link to='/management/manage-module' className='p-2'>
            <IoIosArrowBack size={30} color='#052C65' />
          </Link>
        </div>
      </div>
      <Container className='shadow-lg p-3 mb-4 rounded-lg'>
        <h2 className='p-2'>Tìm Kiếm Nhân Viên</h2>
        <Select
          mode='multiple'
          placeholder='Nhập mã nhân viên'
          className='w-full m-2'
          onSearch={(value) => handleSearchValue(value)}
          onChange={(value) => handleSearch(value)}
          options={dataStaffSearch || []}
          loading={isLoadingStaffInfoSearch}
        />
      </Container>
      <Container className='shadow-lg p-3 rounded-lg'>
        <div className='flex justify-between items-center'>
          <h2 className='p-2'>Phân Quyền Nhân Viên - {moduleName}</h2>
          <div className='flex flex-wrap gap-2'>
            <Button
              type='primary'
              className='text-white bg-[#052C65] border-[#052C65] hover:bg-[#052C65] hover:border-[#052C65]'
              onClick={downloadTemplate}
              loading={downloadTemplateLoading}
            >
              {downloadTemplateLoading ? null : <FaDownload className='mr-2' />}
              Tải Dữ Liệu Hiện Tại
            </Button>
            <Button
              type='primary'
              className='text-white bg-[#052C65] border-[#052C65] hover:bg-[#052C65] hover:border-[#052C65]'
              onClick={() => setModalVisible(true)}
            >
              <FaUpload className='mr-2' />
              Tải Lên
            </Button>
            {isDataChanged && (
              <div>
                {buttonCancel()}
                {buttonSave()}
              </div>
            )}
          </div>
        </div>
        <IdentityTable
          columns={columns}
          loading={loadingTable}
          dataSource={dataSource}
          totalPages={totalPages}
          setPaginationParams={setPaginationParams}
          paginationParams={paginationParams}
          isPagination={isDataChanged ? false : true}
        />
      </Container>
      {modalVisible && (
        <ModalUploadFile
          onClose={() => {
            setModalVisible(false);
          }}
          visible={modalVisible}
          uploadFile={async (file) => await uploadFile(file)}
        />
      )}
    </Container>
  );
};

export default Decentralization;
