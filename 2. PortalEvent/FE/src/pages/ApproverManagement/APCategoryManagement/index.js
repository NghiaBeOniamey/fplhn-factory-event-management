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
  Empty,
} from "antd";
import { APCategoryApi } from "./APCategoryManagementApi";
import "../APCategoryManagement/style.css";
import {
  faList,
  faMagnifyingGlass,
  faPenToSquare,
  faPlus,
  faRotateRight,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import { Tooltip } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Popconfirm } from "antd/lib";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import { APPROVER_CATEGORY_MANAGEMENT } from "../../../constants/DisplayName";

export default function CategoryList() {
  const [form] = Form.useForm();
  //hứng list category từ api
  const [listCategory, setListCategory] = useState([]);
  //Phân trang
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  //theo dõi modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  //hứng dữ liệu từ bàn phím
  const [categoryName, setCategoryName] = useState("");
  //thông báo validate
  const [categoryNameError, setCategoryNameError] = useState("");
  //theo dõi modal là thêm hay cập nhật
  const [modalAction, setModalAction] = useState(1); // Mặc định là chức năng thêm
  //hứng id của category
  const [id, setId] = useState("");
  //hứng data tìm kiếm từ bàn phím
  const [searchName, setSearchName] = useState("");

  //UseEffect theo dõi list thể loại và phân trang
  useEffect(() => {
    loadDataCategory(currentPage);
  }, [currentPage]);
  //END useEffect

  //load data category lên
  const loadDataCategory = (page) => {
    APCategoryApi.categoryList(page, searchName)
      .then((response) => {
        setListCategory(response.data.data);
        setCurrentPage(page);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END load data category lên

  //Search Thể loại
  const searchTheLoai = (page) => {
    APCategoryApi.categoryList(page, searchName)
      .then((response) => {
        setListCategory(response.data.data);
        setCurrentPage(0);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END search Thể loại

  //Cập nhật thể loại
  const updateCategory = (id) => {
    let obj = {
      name: categoryName,
    };
    APCategoryApi.updateCategory(id, obj)
      .then((response) => {
        message.success("Cập nhật thành công");
        loadDataCategory(currentPage);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END cập nhật thể loại

  //xóa thể loại
  const deleteCategory = (id) => {
    APCategoryApi.deleteCategory(id)
      .then((response) => {
        message.success("Xóa thành công");
        loadDataCategory(currentPage);
      })
      .catch((error) => {
        message.error("Xóa thất bại");
      });
  };
  //END xóa thể loại

  //Lấy ra 1 phần tử trong list category
  const detailCategory = (id) => {
    APCategoryApi.detailCategory(id)
      .then((response) => {
        let objDetail = response.data.data;
        setCategoryName(objDetail.name);
      })
      .catch((error) => {
        message.error("Xảy ra lỗi, liên hệ với quản trị viên");
      });
  };
  //END lấy ra 1 phần tử trong list category

  //Thêm thể loại
  const postCategory = () => {
    let obj = {
      name: categoryName,
    };
    APCategoryApi.addCategory(obj)
      .then((response) => {
        message.success("Thêm thành công");
        loadDataCategory(currentPage);
      })
      .catch((error) => {
        message.warning(error.response.data.message);
      });
  };
  //END thêm thể loại

  //Show modal thêm hoặc cập nhật thể loại
  const showModal = (id, action) => {
    setIsModalOpen(true);
    setModalAction(action);
    ////  //console.log(id);
    if (action === 2 || action === 3) {
      detailCategory(id);
    }
  };
  //END show modal thêm hoặc cập nhật thể loại

  //Xác nhận thêm hoặc sửa thể loại
  const handleOk = () => {
    if (categoryName.trim() === "") {
      setCategoryNameError("Tên thể loại không được để trống.");
      return; // Không thực hiện xử lý khi trường nhập liệu trống
    }
    if (/[~`!@#$%^&*()_+\-={}\[\]:;"?><|\\]/.test(categoryName)) {
      setCategoryNameError("Tên thể loại không được chứa các ký tự đặc biệt.");
      return;
    }
    if (modalAction === 1) {
      // Thực hiện thêm thể loại mới
      postCategory();
    } else if (modalAction === 3) {
      // detailCategory(id);
      updateCategory(id);
      // Thực hiện cập nhật thể loại
    }
    setIsModalOpen(false);
    setIsModalOpen(false);
    setCategoryName("");
    setCategoryNameError("");
  };
  //END xác nhận thêm hoặc sửa thể loại

  //Tắt modal
  const handleCancel = () => {
    setIsModalOpen(false);
    setCategoryName("");
    setCategoryNameError("");
  };
  //END tắt modal

  //Xóa data trong input tìm kiếm
  const resets = () => {
    setSearchName("");
    loadDataCategory(currentPage);
  };
  //END Xóa data trong input tìm kiếm

  const confirm = (e) => {
    deleteCategory(id);
  };
  const cancel = (e) => { };

  //Các cột của thể loại
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
      align: "center",
      render: (text, record) => <p>{text}</p>,
    },
    {
      title: "Thao tác",
      key: "action",
      width: 150,
      align: "center",
      render: (_, record) => (
        <div className={"textCenter"}>
          {/*Chức năng cập nhật thể loại */}
          <Tooltip title="Sửa thể loại" placement="top">
            <Button
              style={{ marginRight: "10px" }}
              size={"middle"}
              shape={"circle"}
              type="primary"
              onClick={() => {
                detailCategory(record.id);
                showModal(record.id, 3);
                setId(record.id);
              }}
            >
              <FontAwesomeIcon icon={faPenToSquare} />
            </Button>
          </Tooltip>
          {/*END Chức năng cập nhật thể loại */}

          {/*Chức năng xóa thể loại */}
          {/* <Popconfirm
            title="Bạn có chắc muốn xóa không ?"
            onConfirm={confirm}
            onCancel={cancel}
            okText="Yes"
            cancelText="No"
          >
            <Tooltip title="Xoá thể loại" placement="top">
              <Button
                danger
                size={"middle"}
                shape={"circle"}
                type="primary"
                onClick={() => {
                  setId(record.id);
                }}
              >
                <FontAwesomeIcon icon={faTrash} />
              </Button>
            </Tooltip>
          </Popconfirm> */}
          {/* <Popconfirm
            title="Xóa bộ môn"
            description="Bạn có chắc chắn muốn xóa bộ môn này không?"
            onConfirm={() => {
              deleteCategory(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            <Tooltip placement="top" title={"Xóa bộ môn"}>
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
          {/*END Chức năng xóa thể loại */}
        </div>
      ),
    },
  ];
  //END các cột của thể loại

  return (
    <>
      <div className="table-category-container">
        <h4 className="title-category">
          <FontAwesomeIcon icon={faList} /> &nbsp; Danh sách thể loại
        </h4>
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
              searchTheLoai(currentPage);
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
        <div className="button-category">
          <Button
            type="primary"
            onClick={() => {
              showModal("", 1);
            }}
          >
            <FontAwesomeIcon icon={faPlus} />
            &nbsp; Thêm thể loại
          </Button>
          <ShowHistoryModal displayName={APPROVER_CATEGORY_MANAGEMENT} />
        </div>
      </div>

      <Modal
        title={modalAction === 1 ? "Thêm thể loại" : "Cập nhật thể loại"}
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
              placeholder="Enter Category Name"
              value={categoryName}
              onChange={(e) => setCategoryName(e.target.value)}
              required
            />
            {categoryNameError && (
              <p style={{ color: "red" }}>{categoryNameError}</p>
            )}
          </>
        )}
      </Modal>
      <Form form={form}>
        <div>
          {listCategory.length > 0 ? (
            <Table
              bordered
              style={{ marginBottom: "20px" }}
              columns={columns}
              dataSource={listCategory}
              pagination={false}
              scroll={{
                x: 1300,
              }}
            />
          ) : (
            <Empty />
          )}

          {listCategory.length > 0 && (
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
    </>
  );
}
