import IdentityTable from "#components/ui/Table/IdentityTable";
import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  DepartmentCampusPaginationParams,
  FetchListDepartmentCampusResponse,
} from "#service/api/departmentcampus.api";
import { faPenToSquare, faRetweet } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Modal, Tag, Tooltip } from "antd";
import { Dispatch, SetStateAction, memo, useMemo } from "react";
import { IoMdInformationCircle } from "react-icons/io";
import { useSelector } from "react-redux";

const DepartmentCampusTable = ({
  data,
  totalPages,
  paginationParams,
  setPaginationParams,
  handleUpdateDepartmentCampus,
  handleModalAddDepartmentCampus,
  handleChangeStatusDepartmentCampus,
  handleOpenModal,
  loading,
}: {
  data: FetchListDepartmentCampusResponse[] | undefined;
  totalPages: number | undefined;
  paginationParams: DepartmentCampusPaginationParams;
  setPaginationParams: Dispatch<
    SetStateAction<DepartmentCampusPaginationParams>
  >;
  handleUpdateDepartmentCampus: (row: any) => void;
  handleModalAddDepartmentCampus: () => void;
  handleChangeStatusDepartmentCampus: (row: any) => void;
  handleOpenModal: (id: number) => void;
  loading: boolean;
}) => {
  const { permissions, userInfo } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const columns = useMemo(() => {
    return [
      {
        title: "#",
        dataIndex: "orderNumber",
        align: "center",
        key: "departmentCampusId",
        render: (text) => <span>{text}</span>,
        width: "5%",
      },
      {
        title: "Tên cơ sở",
        dataIndex: "campusName",
        key: "campusId",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: "20%",
      },
      {
        title: "Tên chủ nhiệm bộ môn",
        dataIndex: "headDepartmentCampusName",
        key: "headDepartmentCampusId",
        sorter: true,
        render: (text: any, record: any) => (
          <span>
            {text
              ? text + " - " + record?.headDepartmentCampusCode
              : "Chưa xác định"}
          </span>
        ),
        width: "20%",
      },
      {
        title: "Trạng thái",
        dataIndex: "departmentCampusStatus",
        key: "departmentCampusStatus",
        render: (text) => (
          <Tag color={text === "DELETED" ? "red" : "green"}>
            {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
          </Tag>
        ),
        align: "center",
        width: "10%",
      },
      {
        title: "Hành động",
        align: "center",
        render: (text, row) => {
          return (
            <div className='flex justify-center gap-2'>
              {(permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO) ||
                row?.campusCode === userInfo?.campusCode) && (
                <>
                  <Tooltip title='Cập nhật bộ môn theo cơ sở' color={"#052C65"}>
                    <div>
                      <Button
                        icon={
                          <FontAwesomeIcon
                            icon={faPenToSquare}
                            size='sm'
                            style={{ color: "#ffff" }}
                          />
                        }
                        size='small'
                        type='primary'
                        className='bg-[#052C65] border-none p-4'
                        onClick={() => handleUpdateDepartmentCampus(row)}
                      />
                    </div>
                  </Tooltip>
                  <Tooltip title='Chuyển đổi trạng thái' color='orange'>
                    <div>
                      <div>
                        <Button
                          size='small'
                          type='primary'
                          icon={
                            <FontAwesomeIcon
                              icon={faRetweet}
                              size='sm'
                              color='#ffff'
                            />
                          }
                          onClick={() => {
                            Modal.confirm({
                              title: "Thông báo",
                              centered: true,
                              content:
                                row.departmentCampusStatus === "DELETED"
                                  ? "Bạn có muốn khôi phục trạng thái không ?"
                                  : "Bạn có muốn ngừng hoạt động không ?",
                              okText: "Có",
                              cancelText: "Không",
                              onOk: () =>
                                handleChangeStatusDepartmentCampus(row),
                              okButtonProps: {
                                className:
                                  "bg-[orange] text-white font-semibold",
                              },
                              cancelButtonProps: {
                                className: "bg-[gray] text-white font-semibold",
                              },
                            });
                          }}
                          className='bg-[orange] border-none p-4'
                        />
                      </div>
                    </div>
                  </Tooltip>
                </>
              )}
              <Tooltip title='Chi Tiết Chuyên Ngành Cơ Sở' color='gray'>
                <div>
                  <Button
                    size='small'
                    type='primary'
                    className='bg-[gray] border-none p-4'
                    onClick={() => handleOpenModal(row.departmentCampusId)}
                    icon={<IoMdInformationCircle size={15} />}
                  />
                </div>
              </Tooltip>
            </div>
          );
        },
        center: "true",
        width: "30%",
      },
    ];
  }, [
    handleChangeStatusDepartmentCampus,
    handleOpenModal,
    handleUpdateDepartmentCampus,
    permissions,
    userInfo?.campusCode,
  ]);

  return (
    <IdentityTable
      columns={columns}
      dataSource={data || []}
      {...{
        totalPages,
        paginationParams,
        setPaginationParams,
        loading,
      }}
    />
  );
};

export default memo(DepartmentCampusTable);
