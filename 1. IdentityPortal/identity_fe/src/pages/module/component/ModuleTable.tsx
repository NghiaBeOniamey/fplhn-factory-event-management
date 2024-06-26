import IdentityTable from "#components/ui/Table/IdentityTable";
import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import { FetchListModuleResponse } from "#service/api/module.api";
import { faPencil, faShieldHalved } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Flex, Tag, Tooltip } from "antd";
import { memo, useMemo } from "react";
import { FaUserShield } from "react-icons/fa";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const ModuleTable = ({
  dataSource,
  paginationParams,
  setPaginationParams,
  totalPages,
  loading,
  handleAddModule,
  handleUpdateModule,
  handleDeleteModule,
  onShow,
}: {
  dataSource: FetchListModuleResponse[] | undefined;
  paginationParams: any;
  setPaginationParams: any;
  totalPages: number | undefined;
  loading: boolean;
  handleAddModule: any;
  handleUpdateModule: any;
  handleDeleteModule: any;
  onShow: any;
}) => {
  const navigation = useNavigate();

  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const columnsModule = useMemo(() => {
    return [
      {
        title: "#",
        dataIndex: "orderNumber",
        align: "center",
        key: "stt",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: 50,
      },
      {
        title: "Mã module",
        dataIndex: "moduleCode",
        key: "moduleCode",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: 100,
      },
      {
        title: "Tên module",
        dataIndex: "moduleName",
        key: "tenModule",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: 100,
      },
      {
        title: "Địa chỉ mô-đun",
        dataIndex: "moduleUrl",
        key: "urlModule",
        render: (text, record) => (
          <span>
            {record.redirectRoute
              ? `${record.moduleUrl}/${record.redirectRoute}`
              : record.moduleUrl}
          </span>
        ),
        width: 100,
      },
      {
        title: "Trạng thái",
        dataIndex: "moduleStatus",
        key: "xoaMemModule",
        render: (text) => (
          <Tag color={text === "DELETED" ? "orange" : "green"}>
            {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
          </Tag>
        ),
        align: "center",
        width: 100,
      },
      {
        title: "Hành động",
        key: "moduleId",
        align: "center",
        render: (row) => (
          <div>
            <Flex gap='small' wrap='wrap' justify='center'>
              <Tooltip
                title={`Phân Quyền Nhân Viên Cho Module ${row.moduleName}`}
                color='#380303'
              >
                <div>
                  <Button
                    icon={<FaUserShield />}
                    size='small'
                    type='primary'
                    className='border-none p-4 bg-[#380303] text-white'
                    onClick={() => {
                      navigation(
                        `/management/manage-module/decentralization-staff`,
                        {
                          state: {
                            moduleId: row.moduleId,
                            moduleName: row.moduleName,
                            moduleCode: row.moduleCode,
                          },
                        }
                      );
                    }}
                  />
                </div>
              </Tooltip>
              {row?.moduleCode !== "QLPQ" && (
                <>
                  {permissions.includes(PERMISSIONS.ADMIN) && (
                    <Tooltip title='Chi Tiết Client' color='#9B4444'>
                      <div>
                        <Button
                          icon={<FontAwesomeIcon icon={faShieldHalved} />}
                          size='small'
                          type='primary'
                          className='bg-[#9B4444] text-white border-none p-4'
                          onClick={() => onShow(row.moduleId)}
                        />
                      </div>
                    </Tooltip>
                  )}
                </>
              )}
              {row?.moduleCode !== "QLPQ" && (
                <>
                  {permissions.includes(PERMISSIONS.ADMIN) && (
                    <Tooltip title='Chi tiết module' color='#052C65'>
                      <div>
                        <Button
                          icon={<FontAwesomeIcon icon={faPencil} />}
                          size='small'
                          type='primary'
                          className='bg-[#052c65] text-white border-none p-4'
                          onClick={() => handleUpdateModule(row)}
                        />
                      </div>
                    </Tooltip>
                  )}
                </>
              )}
              {/* {row?.moduleCode !== "QLPQ" && (
                <>
                  {permissions.includes(PERMISSIONS.ADMIN) && (
                    <Tooltip title='Cập nhật trạng thái' color='orange'>
                      <div>
                        <Popconfirm
                          placement='left'
                          title={"Thông báo"}
                          description='Bạn có chắc chắn muốn cập nhật trạng thái mô-đun này không?'
                          okText='Có'
                          cancelText='Không'
                          onConfirm={() => {
                            handleDeleteModule(row.moduleId);
                          }}
                        >
                          <Button
                            size={"large"}
                            type={"primary"}
                            icon={
                              row.moduleStatus === "DELETED" ? (
                                <FontAwesomeIcon icon={faArrowsRotate} />
                              ) : (
                                <FontAwesomeIcon icon={faRetweet} />
                              )
                            }
                            className='bg-orange-500 text-white'
                          />
                        </Popconfirm>
                      </div>
                    </Tooltip>
                  )}
                </>
              )} */}
            </Flex>
          </div>
        ),
        center: "true",
        width: "30%",
      },
    ];
  }, [handleUpdateModule, navigation, onShow, permissions]);

  return (
    <IdentityTable
      columns={columnsModule}
      dataSource={dataSource || []}
      {...{
        paginationParams,
        setPaginationParams,
        totalPages,
        loading,
      }}
    />
  );
};

export default memo(ModuleTable);
