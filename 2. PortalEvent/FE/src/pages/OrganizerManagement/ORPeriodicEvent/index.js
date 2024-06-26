import {
  faEdit,
  faEye,
  faFilter,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Card,
  Col,
  Input,
  Modal,
  Pagination,
  Popconfirm,
  Row,
  Select,
  Table,
  Tooltip,
  message,
} from "antd";
import { useEffect, useState } from "react";
import ORPeriodicEventAPI from "./ORPeriodicEventAPI";
import JoditEditor from "jodit-react";
import { useNavigate } from "react-router-dom";

const ORPeriodicEvent = () => {
  const [listPeriodicEvents, setListPeriodicEvents] = useState([]);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [nameSearch, setNameSearch] = useState("");
  const [categoryIdSearch, setCategoryIdSearch] = useState("");
  const [eventTypeSearch, setEventTypeSearch] = useState("");
  const [majorIdSearch, setMajorIdSearch] = useState("");
  const [objectIdSearch, setObjectIdSearch] = useState("");

  const [listCategory, setListCategory] = useState([]);
  const [listObject, setListObject] = useState([]);
  const [listMajor, setListMajor] = useState([]);

  useEffect(() => {
    loadDataPeriodicEvents();
  }, [current]);

  const clearFilter = () => {
    setNameSearch("");
    setCategoryIdSearch("");
    setEventTypeSearch("");
    setMajorIdSearch("");
    setObjectIdSearch("");
  };

  useEffect(() => {
    ORPeriodicEventAPI.getListCategory().then((response) => {
      setListCategory(response.data.data);
    });

    ORPeriodicEventAPI.getListMajor().then((response) => {
      setListMajor(response.data.data);
    });

    ORPeriodicEventAPI.getListObject().then((response) => {
      setListObject(response.data.data);
    });
  }, []);

  const loadDataPeriodicEvents = () => {
    let filter = {
      name: nameSearch,
      categoryId: categoryIdSearch,
      eventType: eventTypeSearch === "" ? null : eventTypeSearch,
      majorId: majorIdSearch,
      objectId: objectIdSearch,
      page: current - 1,
    };
    ORPeriodicEventAPI.getPage(filter).then((response) => {
      setListPeriodicEvents(response.data.data.data);
      setTotalPages(response.data.data.totalPages);
    });
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
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
      title: "Loại sự kiện",
      dataIndex: "eventType",
      key: "eventType",
      align: "center",
      render: (text, record) => {
        if (record.eventType === 0) {
          return <span>Dành cho sinh viên</span>;
        } else if (record.eventType === 1) {
          return <span>Dành cho giảng viên</span>;
        } else if (record.eventType === 2) {
          return <span>Dành cho SV và GV</span>;
        } else if (record.eventType === 3) {
          return <span>Tutorial</span>;
        } else {
          return <span>Chưa có</span>;
        }
      },
    },
    {
      title: "SL người dự kiến",
      dataIndex: "expectedParticipants",
      key: "expectedParticipants",
      align: "center",
    },
    {
      title: "Thể loại",
      dataIndex: "categoryName",
      key: "categoryName",
      align: "center",
    },
    {
      title: "Bộ môn",
      dataIndex: "major",
      key: "major",
      align: "center",
      render: (text, record) => {
        if (record.major != null && record.major.trim() !== "") {
          return <span>{record.major}</span>;
        } else {
          return <span>Chưa có</span>;
        }
      },
    },
    {
      title: "Đối tượng",
      dataIndex: "object",
      key: "object",
      align: "center",
      render: (text, record) => {
        if (record.object != null && record.object.trim() !== "") {
          return <span>{record.object}</span>;
        } else {
          return <span>Chưa có</span>;
        }
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      align: "center",
      render: (text, record) => (
        <div style={{ display: "flex", alignItems: "center" }}>
          <Tooltip title="Xem chi tiết">
            <FontAwesomeIcon
              icon={faEye}
              onClick={() => {
                openModalDetail(record.id);
              }}
              style={{
                color: "rgb(38, 144, 214)",
                marginRight: "20px",
                cursor: "pointer",
              }}
            />
          </Tooltip>
          <Popconfirm
            title="Bạn có chắc muốn đăng ký tổ chức sự kiện này không ?"
            placement="topLeft"
            okText="Yes"
            onConfirm={() => { }}
            cancelText="No"
          >
            <Tooltip title="Đăng ký tổ chức">
              <FontAwesomeIcon
                icon={faRegistered}
                onClick={() => {
                  registerPeriodicEvent(record.id);
                }}
                style={{
                  color: "rgb(38, 144, 214)",
                  cursor: "pointer",
                }}
              />
            </Tooltip>
          </Popconfirm>
        </div>
      ),
    },
  ];

  const [isModalDetailOpen, setIsModalDetailOpen] = useState(false);
  const [idDetail, setIdDetail] = useState("");
  const [nameDetail, setNameDetail] = useState("");
  const [errorNameDetail, setErrorNameDetail] = useState("");
  const [eventTypeDetail, setEventTypeDetail] = useState("");
  const [errorEventTypeDetail, setErrorEventTypeDetail] = useState("");
  const [expectedParticipantsDetail, setExpectedParticipantsDetail] =
    useState(0);
  const [errorExpectedParticipantsDetail, setErrorExpectedParticipantsDetail] =
    useState("");
  const [descriptionDetail, setDescriptionDetail] = useState("");
  const [categoryIdDetail, setCategoryIdDetail] = useState("");
  const [errorCategoryIdDetail, setErrorCategoryIdDetail] = useState("");
  const [majorDetail, setMajorDetail] = useState([]);
  const [objectDetail, setObjectDetail] = useState([]);

  const openModalDetail = (id) => {
    setIsModalDetailOpen(true);
    loadDataDetail(id);
  };

  const loadDataDetail = (id) => {
    setIdDetail(id);
    ORPeriodicEventAPI.detail(id).then((response) => {
      let data = response.data.data;
      setNameDetail(data.name);
      setEventTypeDetail(data.eventType + "");
      setExpectedParticipantsDetail(data.expectedParticipants);
      setDescriptionDetail(data.description);
      setCategoryIdDetail(data.categoryId);
      setMajorDetail(data.listMajor);
      setObjectDetail(data.listObject);
    });
  };

  const handleDetailCancel = () => {
    setIdDetail("");
    setNameDetail("");
    setEventTypeDetail("");
    setExpectedParticipantsDetail(0);
    setDescriptionDetail("");
    setCategoryIdDetail("");
    setErrorNameDetail("");
    setErrorEventTypeDetail("");
    setErrorExpectedParticipantsDetail("");
    setErrorCategoryIdDetail("");
    setMajorDetail([]);
    setObjectDetail([]);
    setIsModalDetailOpen(false);
  };

  const navigate = useNavigate();

  const registerPeriodicEvent = (id) => {
    ORPeriodicEventAPI.registerPeriodicEvent(id).then(
      (response) => {
        message.success("Đăng ký sự kiện thành công");
        navigate("/organizer-management/event-detail/" + response.data.data.id);
      },
      (error) => { }
    );
  };

  const configJeditor = {
    readonly: true,
    toolbar: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showStatusbar: false,
    showPoweredBy: false,
  };

  return (
    <div className="rounded-lg shadow-md w-full bg-white p-8">
      <div style={{ marginTop: 20 }}>
        <Card
          title={
            <span>
              <FontAwesomeIcon icon={faFilter} style={{ marginRight: "8px" }} />
              Bộ lọc
            </span>
          }
          bordered={false}
        >
          <Row>
            <Col span={12} style={{ padding: 5 }}>
              Tên sự kiện:
              <Input
                value={nameSearch}
                onChange={(e) => {
                  setNameSearch(e.target.value);
                }}
                type="text"
                placeholder="Nhập tên sự kiện"
              />
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              Loại sự kiện:
              <Select
                value={eventTypeSearch}
                onChange={(e) => {
                  setEventTypeSearch(e);
                }}
                style={{ width: "100%" }}
              >
                <Select.Option value="">Chọn loại sự kiện</Select.Option>
                <Select.Option value="0">Dành cho sinh viên</Select.Option>
                <Select.Option value="1">Dành cho giảng viên</Select.Option>
                <Select.Option value="2">Dành cho SV và GV</Select.Option>
                <Select.Option value="3">Tutorial</Select.Option>
              </Select>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              Thể loại:
              <Select
                value={categoryIdSearch}
                onChange={(e) => {
                  setCategoryIdSearch(e);
                }}
                style={{ width: "100%" }}
              >
                <Select.Option value="">Chọn thể loại:</Select.Option>
                {listCategory.map((item) => {
                  return (
                    <Select.Option value={item.id} key={item.id}>
                      {item.name}
                    </Select.Option>
                  );
                })}
              </Select>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              Đối tượng:
              <Select
                value={objectIdSearch}
                onChange={(e) => {
                  setObjectIdSearch(e);
                }}
                style={{ width: "100%" }}
              >
                <Select.Option value="">Chọn đối tượng</Select.Option>
                {listObject.map((item) => {
                  return (
                    <Select.Option value={item.id} key={item.id}>
                      {item.name}
                    </Select.Option>
                  );
                })}
              </Select>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              Bộ môn:
              <Select
                value={majorIdSearch}
                onChange={(e) => {
                  setMajorIdSearch(e);
                }}
                style={{ width: "100%" }}
              >
                <Select.Option value="">Chọn bộ môn</Select.Option>
                {listMajor.map((item) => {
                  return (
                    <Select.Option value={item.id} key={item.id}>
                      {item.name}
                    </Select.Option>
                  );
                })}
              </Select>
            </Col>
          </Row>
          <Row
            style={{ display: "flex", justifyContent: "center", marginTop: 20 }}
          >
            <Button
              style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
              onClick={loadDataPeriodicEvents}
            >
              Tìm kiếm
            </Button>
            <Button
              style={{
                backgroundColor: "rgb(38, 144, 214)",
                color: "white",
                marginLeft: 5,
              }}
              onClick={clearFilter}
            >
              Làm mới bộ lọc
            </Button>
          </Row>
        </Card>
      </div>
      <div style={{ marginTop: 20 }}>
        <Card title="Danh sách sự kiện hàng kỳ" extra={null} bordered={false}>
          {" "}
          <Table
            dataSource={listPeriodicEvents}
            columns={columns}
            bordered
            pagination={false}
            scroll={{
              x: 1300,
            }}
          />
          <div
            className="pagination_box"
            style={{
              display: "flex",
              marginTop: 30,
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <Pagination
              simple
              current={current}
              onChange={(value) => {
                setCurrent(value);
              }}
              total={totalPages * 10}
            />
          </div>
        </Card>
      </div>
      <Modal
        title="Chi tiết sự kiện hàng kỳ"
        open={isModalDetailOpen}
        onCancel={handleDetailCancel}
        footer={null}
        width={800}
      >
        <Row>
          <Col span={12} style={{ padding: 5 }}>
            Tên sự kiện:
            <Input
              readOnly={true}
              value={nameDetail}
              onChange={(e) => {
                setNameDetail(e.target.value);
              }}
              type="text"
              placeholder="Nhập tên sự kiện"
            />
            <span style={{ color: "red" }}>{errorNameDetail}</span>
          </Col>
          <Col span={12} style={{ padding: 5 }}>
            Loại sự kiện:
            <Select
              value={eventTypeDetail}
              readOnly={true}
              onChange={(e) => {
                setEventTypeDetail(e);
              }}
              style={{ width: "100%" }}
              open={false}
            >
              <Select.Option value="">Chọn loại sự kiện</Select.Option>
              <Select.Option value="0">Dành cho sinh viên</Select.Option>
              <Select.Option value="1">Dành cho giảng viên</Select.Option>
              <Select.Option value="2">Dành cho SV và GV</Select.Option>
              <Select.Option value="3">Tutorial</Select.Option>
            </Select>
            <span style={{ color: "red" }}>{errorEventTypeDetail}</span>
          </Col>
          <Col span={12} style={{ padding: 5 }}>
            Số người dự kiến tham gia:
            <Input
              value={expectedParticipantsDetail}
              readOnly={true}
              onChange={(e) => {
                setExpectedParticipantsDetail(e.target.value);
              }}
              type="number"
            />
            <span style={{ color: "red" }}>
              {errorExpectedParticipantsDetail}
            </span>
          </Col>{" "}
          <Col span={12} style={{ padding: 5 }}>
            Thể loại:
            <Select
              value={categoryIdDetail}
              readOnly={true}
              onChange={(e) => {
                setCategoryIdDetail(e);
              }}
              style={{ width: "100%" }}
              open={false}
            >
              <Select.Option value="">Chọn thể loại:</Select.Option>
              {listCategory.map((item) => {
                return (
                  <Select.Option value={item.id} key={item.id}>
                    {item.name}
                  </Select.Option>
                );
              })}
            </Select>
            <span style={{ color: "red" }}>{errorCategoryIdDetail}</span>
          </Col>
          <Col span={24} style={{ padding: 5 }}>
            Đối tượng:
            <Select
              open={false}
              value={objectDetail}
              readOnly={true}
              onChange={(e) => {
                setObjectDetail(e);
              }}
              mode="multiple"
              style={{ width: "100%" }}
              placeholder="Chọn đối tượng"
            >
              {listObject.map((item) => {
                return (
                  <Select.Option value={item.id} key={item.id}>
                    {item.name}
                  </Select.Option>
                );
              })}
            </Select>
          </Col>
          <Col span={24} style={{ padding: 5 }}>
            Bộ môn:
            <Select
              open={false}
              value={majorDetail}
              readOnly={true}
              onChange={(e) => {
                setMajorDetail(e);
              }}
              mode="multiple"
              style={{ width: "100%" }}
              placeholder="Chọn bộ môn"
            >
              {listMajor.map((item) => {
                return (
                  <Select.Option value={item.id} key={item.id}>
                    {item.name}
                  </Select.Option>
                );
              })}
            </Select>
          </Col>
          <Col span={24} style={{ padding: 5 }}>
            Mục tiêu sự kiện:
            <JoditEditor
              tabIndex={1}
              readOnly={true}
              ref={null}
              value={descriptionDetail}
              config={configJeditor}
            />
          </Col>
        </Row>
      </Modal>
    </div>
  );
};

export default ORPeriodicEvent;
