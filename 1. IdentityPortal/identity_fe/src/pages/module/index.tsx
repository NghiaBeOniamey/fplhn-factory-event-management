import Container from "#components/ui/Container";
import SearchBar from "#components/ui/SearchBar";
import ModuleTable from "#pages/module/component/ModuleTable";
import {
  useGetAllModule,
  useGetListModule,
} from "#service/action/module.action";
import { ModulePaginationParams } from "#service/api/module.api";
import { Client } from "#type/index.t";
import {
  faCirclePlus,
  faNetworkWired,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row, Tooltip, message } from "antd";
import { useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { useImmer } from "use-immer";
import ModalAddModule from "./component/ModalAddModule";
import ModalClient from "./component/ModalClient";
import ModalUpdateModule from "./component/ModalUpdateModule";
import { useModalClient } from "./hook/useModalClient";

const ManageModule = () => {
  const [dataUpdate, setDataUpdate] = useState();

  const { show, onHide, client, onShow } = useModalClient();

  const [paginationParams, setPaginationParams] =
    useImmer<ModulePaginationParams>({
      page: 1,
      size: 5,
    });

  const [isModalAddModuleOpen, setIsModalAddModuleOpen] = useState(false);

  const handleAddModule = (row) => {
    setIsModalAddModuleOpen(true);
    setDataUpdate(row);
  };

  const [isModalUpdateModuleOpen, setIsModalUpdateModuleOpen] = useState(false);

  const handleUpdateModule = (row) => {
    setIsModalUpdateModuleOpen(true);
    setDataUpdate(row);
  };

  const { data: dataModuleFetch, isLoading } =
    useGetAllModule(paginationParams);

  const dataModule = useMemo(() => {
    return dataModuleFetch?.data?.data?.map((item) => {
      return {
        ...item,
        key: item.moduleId,
      };
    });
  }, [dataModuleFetch]);

  const totalPages = useMemo(() => {
    return dataModuleFetch?.data?.totalPages;
  }, [dataModuleFetch]);

  const { data: dataSearchFetch } = useGetListModule();

  const dataSearch = useMemo(() => {
    return dataSearchFetch?.data?.map((item) => {
      return {
        value: item.moduleId,
        label: item.moduleName,
        key: item.moduleId,
      };
    });
  }, [dataSearchFetch]);

  const handleChangeSetValuesSearch = useCallback(
    (val: number[]) => {
      if (val?.length === 0) {
        setPaginationParams((draft) => {
          draft.page = 1;
          draft.size = 10;
          draft.listId = null;
        });
        return;
      }

      setPaginationParams((draft) => {
        draft.page = 1;
        draft.listId = val.join(",") || "";
      });
    },
    [setPaginationParams]
  );

  return (
    <Container>
      <h2 className='flex items-center p-4 gap-3'>
        <FontAwesomeIcon icon={faNetworkWired} size='2xl' color='#052C65' />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý mô-đun
        </span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <SearchBar
          dataSearch={dataSearch}
          contentSearch='Module'
          placeholder='Tìm kiếm theo tên mô-đun'
          onChange={handleChangeSetValuesSearch}
        />
      </Container>

      <Container className='shadow-lg p-4 rounded-lg mt-5 text-[#052C65]'>
        <Row className='flex justify-between items-center text-[#052C65]'>
          <h3 className='flex justify-between items-center gap-3 text-xl font-medium'>
            <FaListAlt />
            Danh sách mô-đun
          </h3>
          <div>
            <Button
              type='primary'
              size='middle'
              onClick={handleAddModule}
              icon={<FontAwesomeIcon icon={faCirclePlus} />}
              className='bg-[#052C65] text-white hover:bg-[#052C65] hover:border-[#052C65] hover:bg-opacity-80'
            >
              Thêm mô-đun
            </Button>
          </div>
        </Row>
        <ModuleTable
          dataSource={dataModule || []}
          loading={isLoading}
          paginationParams={paginationParams}
          setPaginationParams={setPaginationParams}
          handleAddModule={handleAddModule}
          handleUpdateModule={handleUpdateModule}
          handleDeleteModule={(id) =>
            message.info("Tính năng đang phát triển!")
          }
          totalPages={totalPages}
          onShow={onShow}
        />
      </Container>
      <ModalAddModule
        isModalAddModuleOpen={isModalAddModuleOpen}
        setIsModalAddModuleOpen={setIsModalAddModuleOpen}
      />
      <ModalClient show={show} onHide={onHide} client={client as Client} />
      <ModalUpdateModule
        isModalUpdateModuleOpen={isModalUpdateModuleOpen}
        setIsModalUpdateModuleOpen={setIsModalUpdateModuleOpen}
        dataUpdate={dataUpdate}
      />
    </Container>
  );
};

export default ManageModule;
