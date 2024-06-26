import Container from "#components/ui/Container";
import SearchBar from "#components/ui/SearchBar";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import useToggle from "#hooks/useToggle";
import {
  useGetAllCampus,
  useGetAllCampusDataSearch,
  useUpdateCampusStatus,
} from "#service/action/campus.action";
import { CampusPaginationParams } from "#service/api/campus.api";
import { faCirclePlus, faCity } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Tooltip, message } from "antd";
import { useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { useImmer } from "use-immer";
import CampusTable from "./component/CampusTable";
import ModalAddCampus from "./component/ModalAddCampus";
import ModalUpdateCampus from "./component/ModalUpdateCampus";

export type CampusDetail = {
  campusId: number;
  campusName: string;
  campusCode: string;
};

const ManageCampus = () => {
  const [dataUpdate, setDataUpdate] = useState<CampusDetail | null>();

  const { value: isModalAddCoSoOpen, setValue: setIsModalAddCoSoOpen } =
    useToggle();

  const { value: isModalUpdateCoSoOpen, setValue: setIsModalUpdateCoSoOpen } =
    useToggle();

  const [paginationParams, setPaginationParams] =
    useImmer<CampusPaginationParams>({
      page: 1,
      size: 10,
    });

  const { mutateAsync: changeStatusCampus } = useUpdateCampusStatus();

  const { data: dataFetchingCampus, isLoading } =
    useGetAllCampus(paginationParams);

  const campusData = useMemo(() => {
    return dataFetchingCampus?.data?.data?.map((item) => {
      return {
        ...item,
        key: item.campusId,
      };
    });
  }, [dataFetchingCampus]);

  const totalPages = useMemo(() => {
    return dataFetchingCampus?.data?.totalPages || 0;
  }, [dataFetchingCampus]);

  const { data: dataSearchFetching } = useGetAllCampusDataSearch();

  const dataSearch = useMemo(() => {
    return (
      dataSearchFetching?.data?.map((item) => {
        return {
          value: item.campusId,
          label: item.campusName + " - " + item.campusCode,
        };
      }) || []
    );
  }, [dataSearchFetching]);

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

  const handleAddCampus = useCallback(() => {
    setIsModalAddCoSoOpen(true);
  }, [setIsModalAddCoSoOpen]);

  const handleUpdateCampus = useCallback(
    (row: any) => {
      setIsModalUpdateCoSoOpen(true);
      setDataUpdate(row);
    },
    [setIsModalUpdateCoSoOpen]
  );

  const handleUpdateStatus = useCallback(
    async (row: any) => {
      try {
        const res = await changeStatusCampus(row.campusId);
        message.success(res?.message);
      } catch (error) {
        message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
      }
    },
    [changeStatusCampus]
  );

  return (
    <Container>
      <h2 className='p-4 flex items-center text-primary text-3xl font-semibold'>
        <FontAwesomeIcon icon={faCity} color='#052c65' size='lg' />
        <span className='ml-2 text-2xl text-[#052c65]'>Quản lý cơ sở</span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <SearchBar
          dataSearch={dataSearch}
          contentSearch='cơ sở'
          placeholder='Tìm kiếm theo tên cơ sở'
          onChange={handleChangeSetValuesSearch}
        />
      </Container>
      <Container className='shadow-lg p-4 rounded-lg mt-5'>
        <Row className='flex justify-between items-center'>
          <h3 className='flex justify-between items-center gap-3'>
            <FaListAlt size={30} color={"#052c65"} />
            <span className='text-primary text-xl font-semibold text-[#052c65]'>
              Danh sách cơ sở
            </span>
          </h3>
          <Tooltip title='Thêm cơ sở' color='#052c65'>
            <div>
              <Button
                type='primary'
                icon={<FontAwesomeIcon icon={faCirclePlus} />}
                size='middle'
                onClick={handleAddCampus}
                className='bg-[#052c65] text-white hover:bg-[#052c65]'
              >
                Thêm cơ sở
              </Button>
            </div>
          </Tooltip>
        </Row>
        <CampusTable
          dataSource={campusData}
          loading={isLoading}
          {...{
            handleUpdateCampus,
            handleAddCampus,
            paginationParams,
            setPaginationParams,
            totalPages,
            handleChangeStatus: handleUpdateStatus,
          }}
        />
      </Container>
      <ModalUpdateCampus
        {...{
          isModalUpdateCoSoOpen,
          setIsModalUpdateCoSoOpen,
          dataUpdate,
          setDataUpdate,
        }}
      />
      <ModalAddCampus
        {...{
          isModalAddCoSoOpen,
          setIsModalAddCoSoOpen,
        }}
      />
    </Container>
  );
};

export default ManageCampus;
