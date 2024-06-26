import Container from "#components/ui/Container";
import SearchBar from "#components/ui/SearchBar";
import RoleTable from "#pages/role/component/RoleTable";
import { useGetAllListRole, useGetAllRole } from "#service/action/role.action";
import { RolePaginationParams } from "#service/api/role.api";
import { showToast } from "#utils/common.helper";
import { faCirclePlus, faCity } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Row } from "antd";
import { useCallback, useMemo, useState } from "react";
import { FaListAlt } from "react-icons/fa";
import { useImmer } from "use-immer";
import ModalAddRole from "./component/ModalAddRole";
import ModalUpdateRole from "./component/ModalUpdateRole";

const ManageRole = () => {
  const [dataUpdate, setDataUpdate] = useState();

  const [isModalAddRoleOpen, setIsModalAddRoleOpen] = useState(false);

  const [isModalUpdateRoleOpen, setIsModalUpdateRoleOpen] = useState(false);

  const [paginationParams, setPaginationParams] =
    useImmer<RolePaginationParams>({
      page: 1,
      size: 10,
    });

  const { data: dataRoleFetching, isLoading: isLoadingRole } =
    useGetAllRole(paginationParams);

  const dataRole = useMemo(() => {
    return dataRoleFetching?.data?.data.map((item) => {
      return {
        ...item,
        key: item.id,
      };
    });
  }, [dataRoleFetching]);

  const totalPages = useMemo(() => {
    return dataRoleFetching?.data.totalPages || 0;
  }, [dataRoleFetching]);

  const totalElements = useMemo(() => {
    return dataRoleFetching?.data.totalElements || 0;
  }, [dataRoleFetching]);

  const { data: dataSearchFetching } = useGetAllListRole();

  const dataSearch = useMemo(() => {
    return dataSearchFetching?.data.map((item) => {
      return {
        value: item.id,
        label: item.roleName,
      };
    });
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

  const handleAddRole = useCallback(() => {
    setIsModalAddRoleOpen(true);
  }, []);

  const handleUpdateRole = useCallback((row: any) => {
    setIsModalUpdateRoleOpen(true);
    setDataUpdate(row);
  }, []);

  const handleDeleteRole = useCallback((id) => {
    showToast("info", "Tính năng đang phát triển");
  }, []);

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
        <FontAwesomeIcon icon={faCity} size='2xl' color='#052C65' />
        <span className='text-2xl font-semibold ml-2 text-[#052c65]'>
          Quản lý vai trò
        </span>
      </h2>
      <Container className='shadow-lg p-4 rounded-lg'>
        <SearchBar
          dataSearch={dataSearch ?? []}
          contentSearch='vai trò'
          placeholder='Tìm kiếm theo tên vai trò'
          onChange={handleChangeSetValuesSearch}
        />
      </Container>
      <Container className='shadow-lg p-4 rounded-lg mt-5'>
        <Row className='flex justify-between items-center text-[#052c65]'>
          <h3 className='text-2xl font-semibold flex items-center gap-2'>
            <FaListAlt size={25} color='#052c65' />
            <span className='text-[#052c65] text-xl'>Danh sách vai trò</span>
          </h3>
          <div>
            <Button
              type='primary'
              size='middle'
              icon={<FontAwesomeIcon icon={faCirclePlus} />}
              onClick={handleAddRole}
              className='bg-[#052c65] text-white hover:bg-[#052c65] hover:bg-opacity-80 hover:border-[#052c65]'
            >
              Thêm vai trò
            </Button>
          </div>
        </Row>
        <RoleTable
          {...{
            dataRole,
            paginationParams,
            setPaginationParams,
            totalPages,
            handleUpdateRole,
            handleAddRole,
            handleTableChange,
            handleDeleteRole,
            isLoadingRole,
            totalElements,
          }}
        />
      </Container>
      <ModalUpdateRole
        {...{
          isModalUpdateRoleOpen,
          setIsModalUpdateRoleOpen,
          dataUpdate,
        }}
      />
      <ModalAddRole
        {...{
          isModalAddRoleOpen,
          setIsModalAddRoleOpen,
        }}
      />
    </Container>
  );
};

export default ManageRole;
