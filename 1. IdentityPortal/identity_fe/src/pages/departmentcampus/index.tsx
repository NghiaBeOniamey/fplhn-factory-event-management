import Container from "#components/ui/Container";
import SearchBar from "#components/ui/SearchBar";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import useToggle from "#hooks/useToggle";
import {
  useDeleteDepartmentCampus,
  useGetAllDepartmentCampus,
  useGetDepartmentName,
  useGetListDepartmentCampus,
} from "#service/action/department.action";
import { DepartmentCampusPaginationParams } from "#service/api/departmentcampus.api";
import { faCirclePlus, faSwatchbook } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Tooltip, message } from "antd";
import { useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { IoMdArrowRoundBack } from "react-icons/io";
import { Link, useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import DepartmentCampusTable from "./component/DepartmentCampusTable";
import ModalAddDepartmentCampus from "./component/ModalCreateDepartmentCampus";
import ModalUpdateDepartmentCampus from "./component/ModalUpdateDepartmentCampus";
import MajorCampusModal from "./majorcampus/MajorCampusModal";

const ManageDepartmentCampus = () => {
  const [isModalAddOpen, setIsModalAddOpen] = useState(false);

  const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);

  const [paginationParams, setPaginationParams] =
    useImmer<DepartmentCampusPaginationParams>({
      page: 1,
      size: 10,
    });

  const [departmentCampusId, setDepartmentCampusId] = useState<number>();

  const { departmentId } = useParams();

  const [dataUpdate, setDataUpdate] = useState();

  const { value, setFalse, setTrue } = useToggle();

  const { data: dataDepartmentCampusFetching, isLoading } =
    useGetAllDepartmentCampus(parseInt(departmentId), paginationParams);

  const { mutateAsync: deleteDepartmentCampus } = useDeleteDepartmentCampus();

  const departmentCampusData = useMemo(() => {
    return dataDepartmentCampusFetching?.data?.data.map((item) => {
      return {
        ...item,
        key: item.departmentCampusId,
      };
    });
  }, [dataDepartmentCampusFetching]);

  const totalPages = useMemo(() => {
    return dataDepartmentCampusFetching?.data?.totalPages;
  }, [dataDepartmentCampusFetching]);

  const { data: dataSearchFetching } = useGetListDepartmentCampus(
    parseInt(departmentId)
  );

  const dataSearch = useMemo(() => {
    return dataSearchFetching?.data.map((item) => {
      return {
        value: item.campusId,
        label: item.campusName,
      };
    });
  }, [dataSearchFetching]);

  const { data: departmentNameFetching } = useGetDepartmentName(
    parseInt(departmentId)
  );

  const departmentName = useMemo(() => {
    return departmentNameFetching?.data;
  }, [departmentNameFetching]);

  const handleCloseModal = () => {
    setFalse();
  };

  const handleOpenModal = useCallback(
    (departmentCampusId: number) => {
      setDepartmentCampusId(departmentCampusId);
      setTrue();
    },
    [setTrue]
  );

  const handleChangeStatusDepartmentCampus = useCallback(
    async (row: { departmentCampusId: number }) => {
      try {
        const res = await deleteDepartmentCampus(row?.departmentCampusId);
        message.success(res?.message);
      } catch (error) {
        message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
      }
    },
    [deleteDepartmentCampus]
  );

  const handleModalAddDepartmentCampus = useCallback(() => {
    setIsModalAddOpen(true);
  }, []);

  const handleUpdateDepartmentCampus = useCallback((row) => {
    setDataUpdate(row);
    setIsModalUpdateOpen(true);
  }, []);

  const handleChangeSetValuesSearch = useCallback(
    (val: number[]) => {
      if (val.length === 0) {
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

  return (
    <>
      <div className='mb-3'>
        <Link to='/management/manage-department' className='m-2'>
          <IoMdArrowRoundBack size={30} color='#052c65' />
        </Link>
      </div>
      <h2 className='text-[#052c65] p-4 gap-3 flex'>
        <FontAwesomeIcon icon={faSwatchbook} />
        Quản lý Bộ môn: <span className='text-[red]'>{departmentName}</span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <SearchBar
          dataSearch={dataSearch}
          contentSearch='cơ sở - chủ nhiệm bộ môn'
          placeholder='Tìm kiếm theo cơ sở'
          onChange={handleChangeSetValuesSearch}
        />
      </Container>
      <Container className='shadow-lg p-4 rounded-3 mt-5'>
        <Row className='flex justify-between items-center text-[#052c65]'>
          <h3 className={"flex justify-between items-center gap-3"}>
            <FaListAlt size={25} />
            Danh sách bộ môn theo cơ sở
          </h3>
          <Tooltip title='Thêm bộ môn' color={"#052c65"}>
            <div>
              <Button
                type='primary'
                icon={<FontAwesomeIcon icon={faCirclePlus} />}
                size='middle'
                onClick={handleModalAddDepartmentCampus}
                className='bg-[#052c65] text-white font-semibold'
              >
                Thêm bộ môn theo cơ sở
              </Button>
            </div>
          </Tooltip>
        </Row>
        <DepartmentCampusTable
          data={departmentCampusData}
          loading={isLoading}
          {...{
            totalPages,
            paginationParams,
            setPaginationParams,
            handleOpenModal,
            handleUpdateDepartmentCampus,
            handleChangeStatusDepartmentCampus,
            handleModalAddDepartmentCampus,
          }}
        />
      </Container>
      <ModalAddDepartmentCampus
        {...{
          isModalCreateMonHocTheoCoSoOpen: isModalAddOpen,
          setIsModalCreateMonHocTheoCoSoOpen: setIsModalAddOpen,
          departmentId: parseInt(departmentId),
          departmentName,
        }}
      />
      <ModalUpdateDepartmentCampus
        {...{
          isModalUpdateMonHocTheoCoSoOpen: isModalUpdateOpen,
          setIsModalUpdateMonHocTheoCoSoOpen: setIsModalUpdateOpen,
          departmentId: parseInt(departmentId),
          dataUpdate,
        }}
      />
      {value && departmentCampusId && (
        <MajorCampusModal
          {...{
            open: value,
            handleClose: handleCloseModal,
            departmentCampusId,
          }}
        />
      )}
    </>
  );
};

export default ManageDepartmentCampus;
