import React, { useEffect, useState } from "react";
import {
  Button,
  Col,
  Form,
  Input,
  message,
  Modal,
  Popconfirm,
  Radio,
  Row,
  Space,
  Table,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
  faPlus,
  faPencilAlt,
  faPenToSquare,
  faTrash,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";
import OREventDetailApi from "../OREventDetailApi";
import { useParams } from "react-router-dom";

const OREDLocation = ({ isDisable }) => {
  const [idEventLocation, setIdEventLocation] = useState("");
  const [formality, setFormality] = useState("");
  const [name, setName] = useState("");
  const [path, setPath] = useState("");
  const [listLocation, setListLocation] = useState([]);
  const [errName, setErrName] = useState();
  const [errPath, setErrPath] = useState();
  const [isUpdate, setIsUpdate] = useState(); //dùng để check xem modal là update hay là thêm

  const openModalUpdateEventLocation = (item) => {
    setIsUpdate(true);
    setErrName("");
    setErrPath("");
    setIdEventLocation(item.id);
    setName(item.name);
    setPath(item.path);
    setFormality(item.formality);
    setOpen(true);
  };

  const openModalAddEventLocation = () => {
    setName("");
    setPath("");
    setFormality(0);
    setIsUpdate(false);
    setOpen(true);
  };

  const columns = [
    {
      title: "STT",
      dataIndex: "recordNumber",
      key: "recordNumber",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên địa điểm",
      dataIndex: "name",
      key: "name",
      render: (text) => (text ? text : "_"),
    },
    {
      title: "Địa điểm cụ thể",
      dataIndex: "path",
      key: "path",
      render: (path, record) => {
        if (record.formality === 0) {
          return (
            <a
              href={path}
              target="_blank"
              style={{
                maxWidth: 280,
                whiteSpace: "nowrap",
                overflow: "hidden",
                textOverflow: "ellipsis",
              }}
            >
              {path}
            </a>
          );
        } else {
          return path;
        }
      },
    },
    {
      title: "Hình thức tổ chức",
      dataIndex: "formality",
      key: "formality",
      render: (formality) => (formality === 0 ? "Online" : formality === 1 ? "Offline" : "_"),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <Space size="middle">
          <FontAwesomeIcon
            icon={faPencilAlt}
            className="custom-icon"
            style={{ marginRight: "15px" }}
            onClick={() => {
              openModalUpdateEventLocation(record);
            }}
          />
          <Popconfirm
            title="Bạn chắc chắn muốn xóa địa điểm này không?"
            onConfirm={() => {
              deleteEventLocation(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            {" "}
            <FontAwesomeIcon icon={faTrash} className="custom-icon" />{" "}
          </Popconfirm>
        </Space>
      ),
      width: "10%",
    },
  ];
  const dynamicColumns = isDisable
    ? columns.filter((column) => column.key !== "actions")
    : columns;

  const { id } = useParams();

  const deleteEventLocation = (idEventLocation) => {
    OREventDetailApi.deleteEventLocationByIdEvent(idEventLocation).then(
      (res) => {
        let updateListEventLocation = listLocation.filter(
          (record) => record.id !== res.data.data
        );
        setListLocation(updateListEventLocation);
        message.success("Xóa thành công");
      },
      (err) => {
        message.warning(err.response.data.message);
      }
    );
  };

  useEffect(() => {
    OREventDetailApi.getLocationByIdEvent(id).then(
      (res) => {
        setListLocation(res.data.data);
      },
      (err) => {
        //  //console.log("Error API get all location");
      }
    );
  }, [id]);

  const handleAddOrUpdateEventLocation = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrName("Tên địa điểm không được để trống!");
      check += 1;
    } else if (name.length > 254) {
      setErrName("Tên địa điểm quá giới hạn ký tự!");
      check += 1;
    } else {
      setErrName("");
    }
    if (path === "") {
      setErrPath("Địa điểm chi tiết không được để trống!");
      check += 1;
    } else if (path.length > 254) {
      setErrPath("Tên địa điểm quá giới hạn ký tự!");
      check += 1;
    }
    else {
      setErrPath("");
    }
    if (check === 0) {
      if (isUpdate) {
        let data = {
          id: idEventLocation,
          formality: formality,
          name: name ? name.trim() : "",
          path: path ? path.trim() : "",
        };
        OREventDetailApi.updateEventLocation(data).then(
          (res) => {
            const index = listLocation.findIndex(
              (item) => item.id === idEventLocation
            );
            const newListEventLocation = [...listLocation];
            newListEventLocation.splice(index, 1);
            newListEventLocation.splice(index, 0, data);
            setListLocation(newListEventLocation);
            setOpen(false);
            message.success("Cập nhật thành công");
          }).catch((err) => {
            message.warning(err.response.data.message);
          });
      } else {
        let data = {
          idEvent: id,
          formality: formality,
          name: name ? name.trim() : "",
          path: path ? path.trim() : "",
        };
        OREventDetailApi.addEventLocation(data).then(
          (res) => {
            let eventLocation = {
              id: res.data.data.id,
              formality: formality,
              name: name,
              path: path,
            };
            setListLocation([eventLocation, ...listLocation]);
            setOpen(false);
            message.success("Thêm thành công");
          }).catch((err) => {
            message.warning(err.response.data.message);
          });
      }
    }
  };

  const [open, setOpen] = useState(false);
  return (
    <>
      <Modal
        centered
        onCancel={() => setOpen(false)}
        open={open}
        width="55%"
        bodyStyle={{
          maxHeight: "calc(100vh - 150px)",
          overflow: "auto",
        }}
        closable={false}
        footer={[
          <Button
            type="primary"
            className="btn-form-event"
            // hidden={true}
            // style={{display: eventStatus === 2 ? "" : "none"}}
            onClick={handleAddOrUpdateEventLocation}
          >
            {isUpdate ? "Cập nhật" : "Thêm"}
            <FontAwesomeIcon
              icon={isUpdate ? faPenToSquare : faPlus}
              style={{ color: "#ffffff", marginLeft: "7px" }}
            />
          </Button>,
          <Button
            style={{ backgroundColor: "red", color: "white" }}
            key="back"
            onClick={() => setOpen(false)}
          >
            Hủy
            <FontAwesomeIcon
              icon={faXmark}
              style={{ color: "#ffffff", marginLeft: "7px" }}
            />
          </Button>,
        ]}
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>
            {isUpdate ? "Cập nhật địa điểm" : "Thêm địa điểm"}
          </span>
        </div>
        <Form
          style={{
            marginTop: "15px",
            borderBottom: "1px solid black",
            marginBottom: "15px",
          }}
          layout="vertical"
          name="basic"
          // onFinish={onFinish}
          // onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Row>
            <Col span={12} style={{ marginRight: "60px" }}>
              <Form.Item
                label="Tên địa điểm tổ chức"
                rules={[{ required: true }]}
              >
                <Input
                  placeholder="Nhập vào tên địa điểm tổ chức"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                // disabled={isDisabled}
                />
                <span style={{ color: "red" }}>{errName}</span>
              </Form.Item>
            </Col>
            <Col span={9}>
              <Form.Item label="Hình thức tổ chức" rules={[{ required: true }]}>
                <Radio.Group
                  value={formality}
                  onChange={(e) => setFormality(e.target.value)}
                >
                  <Radio value={0} checked={formality === 0}>
                    Online
                  </Radio>
                  <Radio value={1} checked={formality === 1}>
                    Offline
                  </Radio>
                </Radio.Group>
              </Form.Item>
            </Col>
          </Row>

          <Form.Item label="Địa điểm chi tiết" rules={[{ required: true }]}>
            <Input
              placeholder="Nhập vào địa điểm chi tiết"
              value={path}
              onChange={(e) => setPath(e.target.value)}
            // disabled={isDisabled}
            />
            <span style={{ color: "red" }}>{errPath}</span>
          </Form.Item>
        </Form>
      </Modal>
      <div style={{ marginTop: "20px" }}>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            marginBottom: "20px",
          }}
        >
          <h4 className="title-comment">
            <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
            <span style={{ marginLeft: "7px" }}>Danh sách địa điểm</span>
          </h4>
          <Button
            type="primary"
            className="btn-form-event"
            onClick={() => {
              openModalAddEventLocation();
            }}
            style={{
              backgroundColor: "#0098d1",
              marginLeft: "auto",
              display: isDisable ? "none" : "", // Thêm điều kiện hiển thị dựa trên giá trị của isDisable
            }}
          >
            Thêm địa điểm
            {/*<FontAwesomeIcon*/}
            {/*    icon={faCloudArrowUp}*/}
            {/*    style={{color: "#ffffff", marginLeft: "7px"}}*/}
            {/*/>*/}
          </Button>
        </div>

        <Table
          columns={dynamicColumns}
          dataSource={listLocation}
          pagination={false}
          scroll={{
            x: 1300,
          }}
        />
      </div>
    </>
  );
};
export default OREDLocation;
