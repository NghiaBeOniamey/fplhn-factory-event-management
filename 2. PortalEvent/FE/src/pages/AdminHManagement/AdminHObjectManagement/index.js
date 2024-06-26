import React, { useEffect, useState } from "react";
import {
  Button,
  Form,
  Input,
  Modal,
  Pagination,
  Space,
  Table,
  message,
} from "antd";

import "./style.css";
import { Tooltip } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
  faMagnifyingGlass,
  faPenToSquare,
  faPlus,
  faRotateRight,
} from "@fortawesome/free-solid-svg-icons";
import { Empty } from "antd/lib";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import { ADMIN_OBJECT_MANAGEMENT } from "../../../constants/DisplayName";
import { AdminHObjectManagementApi } from './AdminHObjectManagementApi';

export default function ObjectList() {
  const [form] = Form.useForm();
  //hứng list object từ api
  const [listObject, setListObject] = useState([]);
  //Phân trang
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  //Theo dõi modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  //Tìm kiếm tên
  const [searchName, setSearchName] = useState("");
  //Hứng data từ bàn phím
  const [objectName, setObjectName] = useState("");
  //Hứng lỗi của validate
  const [objectNameError, setObjectNameError] = useState("");
  //theo dõi modal là thêm hay cập nhật
  const [modalAction, setModalAction] = useState(1); // Mặc định là chức năng thêm
  // hứng id của object
  const [id, setId] = useState("");

  //UseEffect theo dõi list thể loại và phân trang
  useEffect(() => {
    loadDataObject(currentPage);
  }, [currentPage]);
  //END useEffect

  //load data object
  const loadDataObject = (page) => {
    AdminHObjectManagementApi.objectList(page, searchName)
      .then((response) => {
        setListObject(response.data.data);
        setCurrentPage(page);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        message.error("Đã xảy ra lỗi, Thông báo cho quản trị viên");
      });
  };
  //END load data object

  //Search Object
  // const searchObject = (page) => {
  //   AdminHObjectApi.objectList(page, searchName)
  //     .then((response) => {
  //       setListObject(response.data.data);
  //       setCurrentPage(0);
  //       setTotalPages(response.data.totalPages);
  //     })
  //     .catch((error) => {
  //       message.error("Đã xảy ra lỗi, Thông báo cho quản trị viên");
  //     });
  // };

  //END Search Object

  //Cập nhật object
  const updateObject = (id) => {
    let obj = {
      name: objectName,
    };
    AdminHObjectManagementApi.updateObject(id, obj)
      .then((response) => {
        message.success("Cập nhật thành công");
        loadDataObject(currentPage);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END cập nhật object

  //Xóa object
  const deleteObject = (id) => {
    AdminHObjectManagementApi.deleteObject(id)
      .then((response) => {
        message.success("Xóa thành công");
        loadDataObject(currentPage);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END Xóa object

  //Lấy ra 1 phần tử trong list object
  const detailObject = (id) => {
    AdminHObjectManagementApi.detailObject(id)
      .then((response) => {
        let objDetail = response.data.data;
        setObjectName(objDetail.name);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END lấy ra 1 phần tử trong list object

  //Thêm object
  const postObject = () => {
    let obj = {
      name: objectName,
    };

    AdminHObjectManagementApi.addObject(obj)
      .then((response) => {
        message.success("Thêm thành công");
        loadDataObject(currentPage);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END Thêm object

  //Show modal thêm hoặc cập nhật
  const showModal = (id, action) => {
    setIsModalOpen(true);
    setModalAction(action);
    if (action === 2 || action === 3) {
      detailObject(id);
    }
  };
  //END Show modal thêm hoặc cập nhật

  //Xác nhận thêm hoặc sửa
  const handleOk = () => {
    if (objectName.trim() === "") {
      setObjectNameError("Tên đối tượng không được để trống.");
      return; // Không thực hiện xử lý khi trường nhập liệu trống
    } else if (objectName.length >= 100) {
      setObjectNameError("Tên đối tượng không được lớn hơn 100 ký tự.");
      return;
    } else if (/[~`!@#$%^&*()_+\-={}\[\]:;"?><|\\]/.test(objectName)) {
      setObjectNameError("Tên đối tượng không được chứa các ký tự đặc biệt.");
      return;
    }
    if (modalAction === 1) {
      // Thực hiện thêm thể loại mới
      postObject();
    } else if (modalAction === 3) {
      updateObject(id);
      // Thực hiện cập nhật thể loại
    }
    setIsModalOpen(false);
    setIsModalOpen(false);
    setObjectName("");
    setObjectNameError("");
  };
  //END xác nhận thêm hoặc sửa

  //Tắt modal
  const handleCancel = () => {
    setIsModalOpen(false);
    setObjectName("");
    setObjectNameError("");
  };
  //END tắt modal

  //Xóa data trong input tìm kiếm
  const resets = () => {
    setSearchName("");
    loadDataObject(currentPage);
  };
  //END xóa data trong input tìm kiếm

  const confirm = (e) => {
    deleteObject(id);
  };
  const cancel = (e) => {};

  //Các cột object
  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
      width: "20%",
      align: "center",
      render: (text) => <p>{text}</p>,
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      width: "50%",
      align: "center",
      render: (text, record) => <p>{text}</p>,
    },
    {
      title: "Action",
      key: "action",
      align: "center",
      width: "30%",
      render: (_, record) => (
        <Space size="middle">
          {/* Chức năng cập nhật */}
          <Tooltip title="Sửa đối tượng" placement="top">
            <Button
              type="primary"
              size={"middle"}
              shape={"circle"}
              onClick={() => {
                detailObject(record.id);
                showModal(record.id, 3);
                setId(record.id);
              }}
            >
              <FontAwesomeIcon icon={faPenToSquare} />
            </Button>
          </Tooltip>
          {/*END Chức năng cập nhật */}

          {/* Chức năng Xóa */}
          {/* <Popconfirm
            title="Xóa đối tượng"
            description="Bạn có chắc chắn muốn xóa đối tượng này không?"
            onConfirm={() => {
              deleteObject(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            <Tooltip placement="top" title={"Xóa đối tượng"}>
              <Button
                key="delete"
                danger
                shape={"circle"}
                size={"middle"}
                type="primary"
                htmlType="button"
              >
                <FontAwesomeIcon icon={faTrash} />
              </Button>
            </Tooltip>
          </Popconfirm> */}
          {/*END Chức năng Xóa */}
        </Space>
      ),
    },
  ];

  return (
    <>
      <div className="table-category-container">
        <h4 className="title-category">
          <FontAwesomeIcon icon={faList} /> &nbsp;Danh sách đối tượng
        </h4>
        <div className="row">
          <div className="size-input">
            <Input
              placeholder="Tìm kiếm theo tên"
              value={searchName}
              onChange={(e) => {
                setSearchName(e.target.value);
              }}
            />
          </div>
          <div className="style-button">
            <Button
              type="primary"
              onClick={() => {
                loadDataObject(currentPage);
              }}
            >
              <FontAwesomeIcon icon={faMagnifyingGlass} />
              &nbsp; Tìm kiếm
            </Button>
            &nbsp;&nbsp;
            <Button
              type="primary"
              onClick={() => {
                resets();
              }}
            >
              <FontAwesomeIcon icon={faRotateRight} />
              &nbsp; Làm mới
            </Button>
          </div>
          <div className="button-object">
            <Button
              type="primary"
              onClick={() => {
                showModal("", 1);
              }}
            >
              <FontAwesomeIcon icon={faPlus} />
              &nbsp; Thêm đối tượng
            </Button>
            <ShowHistoryModal displayName={ADMIN_OBJECT_MANAGEMENT} />
          </div>
        </div>
        <Modal
          title={modalAction === 1 ? "Thêm đối tượng" : "Cập nhập đối tượng"}
          open={isModalOpen}
          onOk={handleOk}
          onCancel={handleCancel}
        >
          <hr />
          <br />
          {modalAction !== "detail" && ( // Chỉ hiển thị Input khi không phải là chức năng chi tiết
            <>
              <h5>Tên:</h5>
              <Input
                placeholder="Enter object name"
                value={objectName}
                onChange={(e) => setObjectName(e.target.value)}
                required
              />
              {objectNameError && (
                <p style={{ color: "red" }}>{objectNameError}</p>
              )}
            </>
          )}
        </Modal>
        <Form form={form}>
          <div>
            {listObject.length > 0 ? (
              <Table
                bordered
                style={{ marginBottom: "20px" }}
                columns={columns}
                dataSource={listObject}
                pagination={false}
                scroll={{
                  x: 1300,
                }}
              />
            ) : (
              <Empty />
            )}

            {listObject.length > 0 && (
              <div className="phan-trang">
                <Pagination
                  simple
                  current={currentPage + 1}
                  onChange={(page) => {
                    setCurrentPage(page - 1);
                  }}
                  total={totalPages * 10}
                />
              </div>
            )}
          </div>
        </Form>
      </div>
    </>
  );
}
