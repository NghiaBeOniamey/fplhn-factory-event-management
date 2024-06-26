import IdentityTable from "#components/ui/Table/IdentityTable";
import {
  CampusPaginationParams,
  ResponseFetchListCampus,
} from "#service/api/campus.api";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons";
import { faBuildingCircleArrowRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Flex, Modal, Tag, Tooltip } from "antd";
import { ColumnType } from "antd/es/table";
import { Dispatch, SetStateAction, memo } from "react";

const CampusTable = ({
  dataSource,
  handleUpdateCampus,
  paginationParams,
  setPaginationParams,
  totalPages,
  loading,
  handleChangeStatus,
}: {
  dataSource: ResponseFetchListCampus[] | undefined;
  handleUpdateCampus: (row: any) => void;
  paginationParams: CampusPaginationParams;
  setPaginationParams: Dispatch<SetStateAction<CampusPaginationParams>>;
  totalPages: number | undefined;
  loading: boolean;
  handleChangeStatus: (row: any) => void;
}) => {
  const columnsCampus: ColumnType<ResponseFetchListCampus>[] = [
    {
      title: "#",
      dataIndex: "orderNumber",
      align: "center",
      key: "campusId",
      render: (text) => <span>{text}</span>,
      width: "5%",
    },
    {
      title: "Tên cơ sở",
      key: "campusName",
      sorter: true,
      render: (row: any) => (
        <span>
          {row.campusName +
            " - " +
            (row.campusCode ? row.campusCode : "Chưa xác định")}
        </span>
      ),
      width: "20%",
    },
    {
      title: "Trạng thái",
      dataIndex: "campusStatus",
      key: "campusStatus",
      render: (text) => (
        <Tag color={text === "DELETED" ? "orange" : "green"}>
          {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
        </Tag>
      ),
      align: "center",
      width: "20%",
    },
    {
      title: "Hành động",
      align: "center",
      render: (row) => (
        <div className='button-table-col'>
          <Flex gap='small' wrap='wrap' className='justify-center'>
            <Tooltip title='Chi tiết cơ sở' color={"#052C65"}>
              <div>
                <Button
                  icon={<FontAwesomeIcon icon={faBuildingCircleArrowRight} />}
                  size='small'
                  type='primary'
                  className='mr-1 bg-[#052C65] border-none p-4'
                  onClick={() => {
                    handleUpdateCampus(row);
                  }}
                />
              </div>
            </Tooltip>
            <Tooltip title='Chuyển đổi trạng thái' color='orange'>
              <div>
                <Button
                  size='small'
                  type='primary'
                  icon={
                    row.campusStatus === "NOT_DELETED" ? (
                      <CloseOutlined />
                    ) : (
                      <CheckOutlined />
                    )
                  }
                  className='mr-1 bg-[orange] border-none p-4'
                  onClick={() => {
                    Modal.confirm({
                      title: "Thông báo",
                      centered: true,
                      content:
                        row.campusStatus === "NOT_DELETED"
                          ? "Bạn có muốn ngừng hoạt động cơ sở này không?"
                          : "Bạn có muốn hoạt động cơ sở này không?",
                      okText: "Có",
                      cancelText: "Không",
                      onOk: () => {
                        handleChangeStatus(row);
                      },
                      okButtonProps: {
                        className: "bg-[orange] text-white hover:bg-[orange]",
                      },
                      cancelButtonProps: {
                        className: "bg-[#052c65] text-white hover:bg-[#052c65]",
                      },
                    });
                  }}
                />
              </div>
            </Tooltip>
          </Flex>
        </div>
      ),
      width: "30%",
    },
  ];

  return (
    <IdentityTable
      columns={columnsCampus}
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

export default memo(CampusTable);
