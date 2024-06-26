import Container from "#components/ui/Container";
import SearchBar from "#components/ui/SearchBar";
import useToggle from "#hooks/useToggle";
import {
  useGetAllDepartment,
  useGetAllDepartmentDataSearch,
} from "#service/action/department.action";
import {
  DepartmentPaginationParams,
  ModifyDepartment,
} from "#service/api/department.api";
import { faCirclePlus, faSwatchbook } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Tooltip } from "antd";
import { useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { useImmer } from "use-immer";
import DepartmentTable from "./component/DepartmentTable";
import ModalAddDepartment from "./component/ModalAddDepartment";
import ModalUpdateDepartment from "./component/ModalUpdateDepartment";
import { useMajorModal } from "./hook/useMajorModal";
import MajorModal from "./major/MajorModal";
import "./style/index.css";

const ManageDepartment = () => {
  const [dataUpdate, setDataUpdate] = useState<ModifyDepartment | null>();

  const {
    value: isModalAddDepartmentOpen,
    setValue: setIsModalAddDepartmentOpen,
  } = useToggle();

  const {
    value: isModalUpdateDepartmentOpen,
    setValue: setIsModalUpdateDepartmentOpen,
  } = useToggle();

  const [paginationParams, setPaginationParams] =
    useImmer<DepartmentPaginationParams>({
      page: 1,
      size: 10,
    });

  const { data: dataFetchingDepartment, isLoading } =
    useGetAllDepartment(paginationParams);

  const { open, handleOpen, handleClose, departmentId } = useMajorModal();

  const departmentData = useMemo(() => {
    return dataFetchingDepartment?.data?.data.map((item) => {
      return {
        ...item,
        key: item.departmentId,
      };
    });
  }, [dataFetchingDepartment]);

  const totalPages = useMemo(() => {
    return dataFetchingDepartment?.data.totalPages || 0;
  }, [dataFetchingDepartment]);

  const { data: dataSearchFetching } = useGetAllDepartmentDataSearch();

  const dataSearch = useMemo(() => {
    return (
      dataSearchFetching?.data.map((item) => {
        return {
          value: item.departmentId,
          label: item.departmentName + " - " + item.departmentCode,
        };
      }) || []
    );
  }, [dataSearchFetching]);

  const handleChangeSetValuesSearch = useCallback(
    (val: number[]) => {
      if (val?.length === 0) {
        setPaginationParams((draft) => {
          draft.page = 1;
          draft.size = 10;
          draft.searchValues = null;
        });
        return;
      }

      setPaginationParams((draft) => {
        draft.page = 1;
        draft.searchValues = val.join(",") || "";
      });
    },
    [setPaginationParams]
  );

  const handleAddDepartment = useCallback(() => {
    setIsModalAddDepartmentOpen(true);
  }, [setIsModalAddDepartmentOpen]);

  const handleUpdateDepartment = useCallback(
    (row: any) => {
      setIsModalUpdateDepartmentOpen(true);
      setDataUpdate(row);
    },
    [setIsModalUpdateDepartmentOpen]
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
        <FontAwesomeIcon icon={faSwatchbook} size='2xl' color='#052C65' />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý bộ môn
        </span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <SearchBar
          dataSearch={dataSearch || []}
          contentSearch='bộ môn'
          placeholder='Tìm kiếm theo tên bộ môn'
          onChange={handleChangeSetValuesSearch}
        />
      </Container>
      <Container className='shadow-lg p-4 rounded-lg mt-5'>
        <Row className='flex justify-between items-center text-[#052c65]'>
          <h3 className='text-2xl font-semibold flex items-center gap-2'>
            <FaListAlt size={25} color='#052c65' />
            <span className='text-[#052c65] text-xl'>Danh sách bộ môn</span>
          </h3>
          <Tooltip title='Thêm bộ môn' color={"#052c65"}>
            <div>
              <Button
                type='primary'
                icon={<FontAwesomeIcon icon={faCirclePlus} />}
                size='middle'
                onClick={handleAddDepartment}
                className='bg-[#052c65] text-white rounded-lg shadow-lg mb-4 mt-4 mr-4'
              >
                Thêm bộ môn
              </Button>
            </div>
          </Tooltip>
        </Row>
        <DepartmentTable
          dataSource={departmentData || []}
          {...{
            handleOpen,
            paginationParams,
            setPaginationParams,
            totalPages,
            loading: isLoading,
            handleUpdateDepartment,
            handleAddDepartment,
            handleTableChange,
          }}
        />
      </Container>
      <ModalUpdateDepartment
        {...{
          isModalUpdateBoMonOpen: isModalUpdateDepartmentOpen,
          setIsModalUpdateBoMonOpen: setIsModalUpdateDepartmentOpen,
          dataUpdate,
          setDataUpdate,
        }}
      />
      <ModalAddDepartment
        {...{
          isModalAddBoMonOpen: isModalAddDepartmentOpen,
          setIsModalAddBoMonOpen: setIsModalAddDepartmentOpen,
          dataUpdate,
          setDataUpdate,
        }}
      />
      {open && departmentId && (
        <MajorModal {...{ open, handleClose, departmentId }} />
      )}
    </Container>
  );
};

export default ManageDepartment;
