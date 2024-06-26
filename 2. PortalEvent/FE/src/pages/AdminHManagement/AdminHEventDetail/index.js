import {
    UserOutlined,
    FileDoneOutlined,
    CloseCircleOutlined,
    CheckCircleOutlined, ExclamationCircleFilled,
} from "@ant-design/icons";
import { green, yellow } from "@mui/material/colors";
import {
    Button,
    Card,
    Col,
    DatePicker,
    Form,
    Input,
    Modal,
    Popconfirm,
    Row,
    Table,
    message,
    Switch,
} from "antd";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import CommentSection from "./Comment/comment";
import "./index.css";
import moment from "moment";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClipboard, faList, faStar } from "@fortawesome/free-solid-svg-icons";
import JoditEditor from "jodit-react";
import { Divider } from "antd";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import { APPROVER_EVENT_DETAIL } from "../../../constants/DisplayName";
import { AdminHEventDetailApi } from "./AdminHEventDetailApi";

export default function EventDetail() {
    const [open, setOpen] = useState(false);
    const [isModalImage, setIsModalImage] = useState(false);
    const [errMessage, setErrMessage] = useState("");
    const [reason, setReason] = useState("");
    const [name, setName] = useState("");
    const [background, setBackground] = useState("");
    const [banner, setBanner] = useState("");
    const [standee, setStandee] = useState("");
    const [status, setStatus] = useState("");
    const [statusColor, setStatusColor] = useState("");
    const [startTime, setStartTime] = useState(new Date());
    const [endTime, setEndTime] = useState(new Date());
    const [categoryName, setCategoryName] = useState("");
    const [description, setDescription] = useState("");
    const [object, setObject] = useState("");
    const [locations, setLocations] = useState([]);
    const [selectedImage, setSelectedImage] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const [listAgendaItem, setListAgendaItem] = useState([]);
    const [listEvidence, setListEvidence] = useState([]);
    const [listOrganizerInEvent, setListOrganizerInEvent] = useState([]);
    const [resources, setResources] = useState([]);
    const [objects, setObjects] = useState([]);
    const [listMajor, setListMajor] = useState([]);
    const [majors, setMajors] = useState([]);
    const [isHireDesignBackground, setIsHireDesignBackground] = useState(false);
    const [isHireDesignBanner, setIsHireDesignBanner] = useState(false);
    const [isHireDesignStandee, setIsHireDesignStandee] = useState(false);
    const [isWaitApprovalPeriodically, setIsWaitApprovalPeriodically] =
        useState(0);
    const [numberParticipants, setNumberParticipants] = useState();
    const [participants, setParticipants] = useState();
    const [avgRate, setAvgRate] = useState();
    const [isNotEnoughTimeApproval, setIsNotEnoughTimeApproval] = useState(false);
    const [departmentName, setDepartmentName] = useState("");
    const [majorName, setMajorName] = useState("");
    const [modalNoti, contextHolder] = Modal.useModal();
    const { id } = useParams();

    const cancel = (e) => {
        //console.log(e);
    };

    useEffect(() => {
        detailEventInfo(id);
        loadListAgendaItem(id);
        getResources(id);
        getObjects(id);
        getLocations(id);
        getMajors(id);
        getEvidence(id);
        handleListOrganizerInEvent(id);
    }, [id]);

    const detailEventInfo = (id) => {
        AdminHEventDetailApi.detailWaiting(id)
            .then((response) => {
                // //console.log(response.data.data)
                let obj = response.data.data;
                setName(obj.name);
                setBackground(obj.background);
                setBanner(obj.banner);
                setStandee(obj.standee);
                setCategoryName(obj.categoryName);
                setDescription(obj.description);
                setEndTime(new Date(obj.endTime));
                setStartTime(new Date(obj.startTime));
                setReason(obj.reason);
                setIsHireDesignBackground(obj.isHireDesignBackground);
                setIsHireDesignBanner(obj.isHireDesignBanner);
                setIsHireDesignStandee(obj.isHireDesignStandee);
                setIsWaitApprovalPeriodically(obj.isWaitApprovalPeriodically);
                setParticipants(obj.participants);
                setNumberParticipants(obj.numberParticipants);
                setAvgRate(obj.avgRate);
                setIsNotEnoughTimeApproval(obj.isNotEnoughTimeApproval);
                setDepartmentName(obj.departmentName);
                setMajorName(obj.majorName);
                if (obj.status === 0) {
                    setStatus("Đã đóng");
                    setStatusColor("status5Color");
                } else if (obj.status === 1) {
                    setStatus("Dự kiến tổ chức");
                    setStatusColor("status13Color");
                } else if (obj.status === 2) {
                    setStatus("Cần sửa");
                    setStatusColor("status2Color");
                } else if (obj.status === 3) {
                    setStatus("Chờ phê duyệt");
                    setStatusColor("status13Color");
                } else if (obj.status === 4) {
                    setStatus("Đã phê duyệt");
                    setStatusColor("status4Color");
                } else if (obj.status === 5) {
                    setStatus("Đã tổ chức");
                    setStatusColor("status5Color");
                } else if (obj.status === 6) {
                    setStatus("Đã được bộ môn thông qua");
                    setStatusColor("status13Color");
                }
                if (obj.eventType === 0) {
                    setObject("Sinh viên");
                } else if (obj.eventType === 1) {
                    setObject("Giảng viên");
                } else if (obj.eventType === 2) {
                    setObject("Giảng viên và sinh viên");
                }
            })
            .catch((error) => {
                //console.log(error);
            });
    };

    const showModal = () => {
        setOpen(true);
    };

    const closeModalImage = () => {
        setIsModalImage(false);
    };

    const openModalImage = (image) => {
        setSelectedImage(image);
        setIsModalImage(true);
    };

    const configJeditor = {
        readonly: true,
        toolbar: false,
        showCharsCounter: false,
        showWordsCounter: false,
        showStatusbar: false,
        showPoweredBy: false,
    };

    const handleOk = (id) => {
        setTimeout(() => {
            if (reason === null || reason.trim() === "") {
                setErrMessage("Bạn cần nhập lý do từ chối!");
                return;
            }
            if (reason.length >= 1000) {
                setErrMessage("Bạn đã nhập quá giới hạn số lượng từ");
                return;
            }
            setErrMessage("");
            AdminHEventDetailApi.approvalEvent({
                id: id,
                reason: reason,
                status: 2,
            })
                .then((response) => {
                    setOpen(false);
                    // setConfirmLoading(false);
                    message.success("Từ chối thành công");
                    detailEventInfo(id);
                })
                .catch((error) => {
                });
        }, 100);
    };

    const handleCancel = () => {
        setOpen(false);
        setErrMessage("");
    };

    const handleApprovalEvent = (id) => {
        AdminHEventDetailApi.countEventInTime(id, startTime.getTime(), endTime.getTime())
            .then((res) => {
                if (res.data.data) {
                    modalNoti.confirm({
                        title: "Cảnh báo!!!",
                        icon: <ExclamationCircleFilled />,
                        content:
                            <p>Thời gian diễn ra sự kiện này trùng với sự kiện khác đã được phê duyệt: <strong><i>{res.data.data}</i></strong>. Bạn có chắc chắn
                                muốn phê duyệt sự kiện này?</p>,
                        onOk() {
                            sendApprovalEvent(id);
                        },
                    })
                } else {
                    sendApprovalEvent(id);
                }

            }).catch((err) => {
                //console.log(err)
                message.error('Lỗi gửi yêu cầu phê duyệt');
            });
    };

    const sendApprovalEvent = (id) => {
        AdminHEventDetailApi.approvalEvent({
            id: id,
            reason: "",
            status: 4,
        })
            .then((response) => {
                message.success("Phê duyệt thành công");
                detailEventInfo(id);
            })
            .catch((error) => {
            });
    }

    const handleListOrganizerInEvent = (id) => {
        AdminHEventDetailApi.getListOrganizerByIdEvent(id)
            .then((response) => {
                // //console.log("response.data.data", response.data.data);
                setListOrganizerInEvent(response.data.data)
            })
            .catch((error) => {
            });
    };

    const renderDateTime = (startTime) => {
        const momentObject = moment(startTime);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const loadListAgendaItem = (idEvent) => {
        AdminHEventDetailApi.getAgendaItem(idEvent)
            .then((response) => {
                setListAgendaItem(response.data.data);
            })
            .catch((error) => {
            });
    };

    const getResources = (idEvent) => {
        AdminHEventDetailApi.getResoure(idEvent)
            .then((response) => {
                setResources(response.data.data);
            })
            .catch((error) => {
            });
    };

    const getObjects = (idEvent) => {
        AdminHEventDetailApi.getObject(idEvent)
            .then((response) => {
                setObjects(response.data.data);
            })
            .catch((error) => {
            });
    };

    const getLocations = (idEvent) => {
        AdminHEventDetailApi.getLocation(idEvent)
            .then((response) => {
                setLocations(response.data.data);
            })
            .catch((error) => {
            });
    };

    const getEvidence = (idEvent) => {
        AdminHEventDetailApi.getEvidenceByIdEvent(idEvent)
            .then((response) => {
                setListEvidence(response.data.data);
                // //console.log(response.data.data)
            })
            .catch((error) => {
                //console.log(error);
            });
    };

    const getMajors = (idEvent) => {
        AdminHEventDetailApi.getMajor(idEvent)
            .then((response) => {
                setMajors(response.data.data);
            })
            .catch((error) => {
            });
    };

    const columns = [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
        },
        {
            title: "Giờ bắt đầu",
            dataIndex: "startTime",
            key: "startTime",
        },
        {
            title: "Giờ kết thúc",
            dataIndex: "endTime",
            key: "endTime",
        },
        {
            title: "Người tổ chức",
            key: "organizerName",
            dataIndex: "organizerName",
        },
        {
            title: "Nội dung",
            key: "description",
            dataIndex: "description",
        },
    ];

    const columnEvidence = [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
            render: (text, record, index) => <span>{index + 1}</span>,
        },
        {
            title: "Tên",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Tài nguyên",
            dataIndex: "path",
            key: "path",
            render: (text, record) => {
                if (record.evidenceType === 0) {
                    return (
                        <Button type="primary">
                            <a href={record.path}>Xem</a>
                        </Button>
                    );
                }
                if (record.evidenceType === 1) {
                    return (
                        <Button type="primary" onClick={() => openModalImage(record.path)}>
                            Xem
                        </Button>
                    );
                }
            },
        },
        {
            title: "Nội dung",
            key: "description",
            dataIndex: "description",
        },
    ];

    const columnOrganizerInEvent = [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
            render: (text, record, index) => <span>{index + 1}</span>,
        },
        {
            title: "Tên",
            dataIndex: "userName",
            key: "name",
        },
        {
            title: "Email",
            dataIndex: "emailFPT",
            key: "Email",
        },
        {
            title: "Giờ F",
            dataIndex: "meetingHour",
            key: "meetingHour",
        },
    ];

    const navigate = useNavigate();

    const approverPeriodicEvent = () => {
        AdminHEventDetailApi.approverPeriodicEvent(id).then(
            (response) => {
                setIsWaitApprovalPeriodically(2);
                message.success("Phê duyệt thành công");
            },
            (error) => {
            }
        );
    };

    const [openWait, setOpenWait] = useState(false);
    const [reasonWait, setReasonWait] = useState("");
    const [errorReasonWait, setErrorReasonWait] = useState("");

    const openModalReasonWait = () => {
        setOpenWait(true);
    };

    const cancelModalReasonWait = () => {
        setOpenWait(false);
        setReasonWait("");
    };

    const noApproverPeriodicEvent = () => {
        let check = 0;

        if (reasonWait.trim() === "") {
            setErrorReasonWait("Lí do từ chối không được để trống");
            check++;
        } else {
            setErrorReasonWait("");
        }
        if (check === 0) {
            AdminHEventDetailApi.noApproverPeriodicEvent(id, reasonWait).then(
                (response) => {
                    setIsWaitApprovalPeriodically(0);
                    navigate("/approver-management/periodic-event-approved");
                    setOpenWait(false);
                    setReasonWait("");
                    message.success("Từ chối phê duyệt thành công");
                },
                (error) => {
                }
            );
        }
    };

    return (
        <div className="containers">
            <Card
                title={
                    <>
                        <div style={{ textAlign: "center", color: "#172b4d" }}>
                            <h3>
                                <FontAwesomeIcon
                                    icon={faClipboard}
                                    style={{ marginRight: "8px" }}
                                />
                                Chi tiết sự kiện
                            </h3>
                        </div>
                    </>
                }
                bordered={false}
            >
                <Form labelCol={{ span: 7 }}>
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                        }}
                    >
                        <Card
                            className="custom-card"
                            bordered={false}
                            style={{
                                width: "85%",
                                borderRadius: "10px",
                                boxShadow: "0px 1px 3px rgba(0, 0, 0, 0.2)",
                                marginBottom: "25px",
                            }}
                        >
                            {isNotEnoughTimeApproval &&
                                <Row style={{ marginBottom: "15px" }}>
                                    <Col className={"textCenter"} span={24}>
                                        <b style={{ color: "red", fontSize: "large" }}>* Sự kiện gửi yêu cầu phê duyệt
                                            không đủ thời gian quy định</b>
                                    </Col>
                                </Row>
                            }
                            <Row gutter={24}>
                                <Col span={12}>
                                    <Form.Item label="Trạng thái" className="form">
                                        <span className={statusColor}>
                                            <b>{status}</b>
                                        </span>
                                    </Form.Item>
                                    {status === "Cần sửa" && (
                                        <Form.Item label="Lý do từ chối" className="form">
                                            <Input.TextArea
                                                label=""
                                                placeholder="Lý do từ chối"
                                                autoSize={{ minRows: 2, maxRows: 2 }}
                                                value={reason}
                                                readOnly
                                                style={{
                                                    border: "none",
                                                }}
                                            />
                                        </Form.Item>
                                    )}
                                    {/* <Form.Item label="Đánh giá">
                                        <span>
                                            <b>{avgRate}%</b>
                                        </span>
                                    </Form.Item> */}
                                </Col>
                                <Col span={12}>
                                    <Form.Item label="Sự kiện cho" className="form text-color">
                                        <span>
                                            <b>{object}</b>
                                        </span>
                                    </Form.Item>
                                    <Form.Item style={{ marginBottom: "0px" }}>
                                        Số lượng người đăng kí:
                                        <span style={{ marginLeft: "7px" }}>
                                            <b>{numberParticipants} người</b>
                                        </span>
                                    </Form.Item>
                                    <Form.Item style={{ marginBottom: "0px" }}>
                                        Số người tham gia điểm danh:
                                        <span style={{ marginLeft: "7px" }}>
                                            <b>{participants} người</b>
                                        </span>
                                    </Form.Item>
                                    <Form.Item style={{ marginBottom: "0px" }}>
                                        Số người tham gia thực tế:
                                        <span style={{ marginLeft: "7px" }}>
                                            <b>{numberParticipants ? numberParticipants : 0} người</b>
                                        </span>
                                    </Form.Item>
                                </Col>
                            </Row>
                        </Card>
                    </div>

                    <Row gutter={16}>
                        <Col span={11} className="col-left">
                            <Form.Item label="Tên sự kiện">
                                <Input name="name" value={name} readOnly />
                            </Form.Item>

                            <Form.Item label="Mục tiêu sự kiên">
                                <JoditEditor
                                    readOnly={true}
                                    value={description}
                                    onBlur={onchange}
                                    config={configJeditor}
                                />
                            </Form.Item>
                            <Form.Item label="Bộ Môn" style={{ marginBottom: 10 }}>
                                <ul>
                                    {departmentName
                                        ?
                                        departmentName.split(",").map((item, index) => {
                                            return (
                                                <li key={index} className="bullet-list-item">
                                                    {item}
                                                </li>
                                            )
                                        })
                                        :
                                        <span style={{ color: "#1677ff" }}>
                                            Không có bộ môn nào!!!
                                        </span>
                                    }
                                </ul>
                            </Form.Item>
                            <Form.Item label="Tài nguyên" style={{ marginBottom: 10 }}>
                                {resources.length > 0 ? (
                                    <ul>
                                        {resources.map((resource, index) => (
                                            <li key={index} className="bullet-list-item">
                                                {resource.name}:{" "}
                                                <a
                                                    href={resource.path}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                >
                                                    Tại đây
                                                </a>
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>
                                        Không có tài nguyên nào!!!
                                    </span>
                                )}
                            </Form.Item>
                            <Form.Item label="Background" style={{ marginBottom: 10 }}>
                                {background ? (
                                    <span
                                        style={{ color: "#1677ff", cursor: "pointer" }}
                                        onClick={() => openModalImage(background)}
                                    >
                                        Xem
                                    </span>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>Không có ảnh!!!</span>
                                )}
                            </Form.Item>
                            <Form.Item label="Banner" style={{ marginBottom: 10 }}>
                                {banner ? (
                                    <span
                                        style={{ color: "#1677ff", cursor: "pointer" }}
                                        onClick={() => openModalImage(banner)}
                                    >
                                        Xem
                                    </span>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>Không có ảnh!!!</span>
                                )}
                            </Form.Item>
                        </Col>

                        <Col span={11}>
                            {/* <Form.Item label="Bộ môn">
                              <Select mode="multiple"
                                  // className='disabled'
                                  value={listMajor}
                              >
                                  {majors.map((major, index) => (
                                      <Option key={index} value={major.id}>
                                          {major.name}
                                      </Option>
                                  ))}
                              </Select>
                              <Select
                                  className='disabled'
                                  mode="multiple"
                                  // style={{ width: "100%" }}
                                  value={majors}
                                  options={majors.map((major, index) => ({
                                      key: index,
                                      value: major.id,
                                      label: major.name,
                                  }))}
                                  // onChange={(newMajor) => {
                                  //     setEventMajor(newMajor);
                                  // }}
                                  // placeholder="Select Item..."
                                  // maxTagCount="responsive"
                              />
                          </Form.Item> */}
                            <Form.Item label="Thể loại">
                                <Input value={categoryName} readOnly />
                            </Form.Item>

                            <Form.Item label="Ngày bắt đầu">
                                <Input
                                    name="name"
                                    value={moment(startTime).format("HH:mm:ss DD/MM/YYYY")}
                                    readOnly
                                />
                            </Form.Item>

                            <Form.Item label="Ngày kết thúc">
                                <Input
                                    name="name"
                                    value={moment(endTime).format("HH:mm:ss DD/MM/YYYY")}
                                    readOnly
                                />
                            </Form.Item>
                            <Form.Item label="Đối tượng" style={{ marginBottom: 10 }}>
                                {objects.length > 0 ? (
                                    <ul>
                                        {objects.map((obj, index) => (
                                            <li
                                                key={index}
                                                className={objects.length > 1 ? "bullet-list-item" : ""}
                                            >
                                                {obj.name}
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>
                                        Không có đối tượng!!!
                                    </span>
                                )}
                            </Form.Item>
                            <Form.Item label="Chuyên Ngành" style={{ marginBottom: 10 }}>
                                {majorName
                                    ?
                                    majorName.split(",").map((item, index) => {
                                        return (
                                            <li key={index} className="bullet-list-item">
                                                {item}
                                            </li>
                                        )
                                    })
                                    :
                                    <span style={{ color: "#1677ff" }}>
                                        Không có chuyên ngành nào!!!
                                    </span>
                                }
                            </Form.Item>
                            <Form.Item label="Địa điểm" style={{ marginBottom: 10 }}>
                                {locations.length > 0 ? (
                                    <ul>
                                        {locations.map((location, index) => (
                                            <li
                                                key={index}
                                                className={
                                                    locations.length > 1 ? "bullet-list-item" : ""
                                                }
                                            >
                                                {location.formality === "0" ? (
                                                    <span>
                                                        Online - {location.name}:{" "}
                                                        <a
                                                            href={location.path}
                                                            target="_blank"
                                                            rel="noopener noreferrer"
                                                        >
                                                            Tại đây
                                                        </a>
                                                    </span>
                                                ) : (
                                                    <span>
                                                        Offline - {location.name}: {location.path}{" "}
                                                    </span>
                                                )}
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>
                                        Chưa cập nhật địa điểm!!!
                                    </span>
                                )}
                            </Form.Item>

                            <Form.Item label="Standee" style={{ marginBottom: 10 }}>
                                {standee ? (
                                    <span
                                        style={{ color: "#1677ff", cursor: "pointer" }}
                                        onClick={() => openModalImage(standee)}
                                    >
                                        Xem
                                    </span>
                                ) : (
                                    <span style={{ color: "#1677ff" }}>Không có ảnh!!!</span>
                                )}
                            </Form.Item>

                            <Form.Item label="Thuê thiết kế" style={{ marginBottom: 10 }}>
                                <Switch
                                    className="disabled"
                                    checked={
                                        isHireDesignBackground ||
                                        isHireDesignBanner ||
                                        isHireDesignStandee
                                    }
                                    checkedChildren="Có"
                                    unCheckedChildren="Không"
                                />
                            </Form.Item>
                        </Col>
                    </Row>
                    {status === "Đã tổ chức" && (
                        <Row>
                            <Divider />
                            <Col span={24}>
                                <div style={{ marginBottom: "10px" }}>
                                    <h4 className="title-comment">
                                        <FontAwesomeIcon
                                            icon={faList}
                                            style={{ color: "#172b4d" }}
                                        />
                                        <span style={{ marginLeft: "7px" }}>Tài liệu báo cáo</span>
                                    </h4>
                                    <div className="table-container">
                                        <Table
                                            dataSource={listEvidence}
                                            columns={columnEvidence}
                                            bordered
                                            pagination={false}
                                        />
                                    </div>
                                </div>
                            </Col>
                        </Row>
                    )}
                    <Row>
                        <Col span={24}>
                            <div style={{ marginBottom: "10px" }}>
                                <h4 className="title-comment">
                                    <FontAwesomeIcon
                                        icon={faList}
                                        style={{ color: "#172b4d" }}
                                    />
                                    <span style={{ marginLeft: "7px" }}>Danh sách người tổ chức</span>
                                </h4>
                                <div classNđame="table-container">
                                    <Table
                                        dataSource={listOrganizerInEvent}
                                        columns={columnOrganizerInEvent}
                                        bordered
                                        pagination={false}
                                    />
                                </div>
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24}>
                            <div style={{ marginBottom: "10px" }}>
                                <h4 className="title-comment">
                                    <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
                                    <span style={{ marginLeft: "7px" }}>
                                        Các mục chương trình
                                    </span>
                                </h4>
                                <div className="table-container">
                                    <Table
                                        dataSource={listAgendaItem}
                                        columns={columns}
                                        bordered
                                        pagination={false}
                                    />
                                </div>
                            </div>
                        </Col>
                    </Row>
                    {status !== 0 && (
                        <Row justify="center">
                            <Form.Item style={{ marginTop: 20 }}>
                                {status !== "Dự kiến tổ chức" &&
                                    status !== "Cần sửa" &&
                                    status !== "Chờ phê duyệt" &&
                                    status !== "Đã được bộ môn thông qua" && (
                                        <Link to={`/admin-h-management/registration-list/${id}`}>
                                            <Button
                                                type="primary"
                                                style={{
                                                    marginRight: 10,
                                                    backgroundColor: green[700],
                                                }}
                                                htmlType="submit"
                                                icon={<UserOutlined />}
                                            >
                                                DS tham gia
                                            </Button>
                                        </Link>
                                    )}

                                {status !== "Dự kiến tổ chức" &&
                                    status !== "Cần sửa" &&
                                    status !== "Chờ phê duyệt" &&
                                    status !== "Đã được bộ môn thông qua" && (
                                        <Link to={`/admin-h-management/event-attendance/${id}`}>
                                            <Button
                                                style={{
                                                    marginRight: 10,
                                                    backgroundColor: yellow[900],
                                                }}
                                                type="primary"
                                                htmlType="button"
                                                icon={<FileDoneOutlined />}
                                            >
                                                DS điểm danh
                                            </Button>
                                        </Link>
                                    )}
                                {status !== "Đã tổ chức" &&
                                    status !== "Đã phê duyệt" &&
                                    status !== "Đã đóng" &&
                                    status !== "Cần sửa" &&
                                    status !== "Chờ phê duyệt" &&
                                    (
                                        <>
                                            <Button
                                                icon={<CloseCircleOutlined />}
                                                key="submit"
                                                type="primary"
                                                danger
                                                style={{ marginRight: 10 }}
                                                onClick={showModal}
                                            >
                                                Từ chối
                                            </Button>

                                            <Popconfirm
                                                title="Xác nhận phê duyệt!"
                                                description="Bạn có chắc muốn phê duyệt cho sự kiện này không?"
                                                onConfirm={() => handleApprovalEvent(id)}
                                                onCancel={cancel}
                                                okText="Yes"
                                                cancelText="No"
                                            >
                                                <Button icon={<CheckCircleOutlined />} type="primary">
                                                    Phê duyệt
                                                </Button>
                                            </Popconfirm>
                                        </>
                                    )}
                                {/* {status !== "Đã tổ chức" && status !== "Đã phê duyệt" && status !== "Đã đóng" && status !== "Cần sửa"&& (
                                  <>
                                      <Button
                                          icon={<CloseCircleOutlined/>}
                                          key="submit"
                                          type="primary"
                                          danger
                                          style={{marginRight: 10}}
                                          onClick={showModal}
                                      >
                                          Từ chối
                                      </Button>

                                      <Popconfirm
                                          title="Xác nhận phê duyệt!"
                                          description="Bạn có chắc muốn phê duyệt cho sự kiện này không?"
                                          onConfirm={() => handleApprovalEvent(id)}
                                          onCancel={cancel}
                                          okText="Yes"
                                          cancelText="No"
                                      >
                                          <Button icon={<CheckCircleOutlined/>} type="primary">
                                              Phê duyệt
                                          </Button>
                                      </Popconfirm>
                                  </>
                              )} */}
                                {/* {isWaitApprovalPeriodically === 1 && (
                                  <>
                                      {" "}
                                      <Popconfirm
                                          title="Bạn có chắc muốn phê duyệt sự kiện này làm sự kiện hàng kỳ ko không ?"
                                          placement="topLeft"
                                          okText="Yes"
                                          onConfirm={() => {
                                              approverPeriodicEvent();
                                          }}
                                          cancelText="No"
                                      >
                                          <Button
                                              style={{
                                                  marginRight: 10,
                                                  backgroundColor: yellow[900],
                                              }}
                                              type="primary"
                                              htmlType="button"
                                              icon={<FileDoneOutlined/>}
                                          >
                                              Phê duyệt SK hàng kỳ
                                          </Button>
                                      </Popconfirm>
                                      <Button
                                          style={{
                                              marginRight: 10,
                                              backgroundColor: yellow[900],
                                          }}
                                          type="primary"
                                          htmlType="button"
                                          icon={<FileDoneOutlined/>}
                                          onClick={openModalReasonWait}
                                      >
                                          Từ chối phê duyệt SK hàng kỳ
                                      </Button>
                                  </>
                              )} */}
                                <ShowHistoryModal displayName={APPROVER_EVENT_DETAIL} eventId={id} />
                            </Form.Item>
                        </Row>
                    )}

                    {/* Modal từ chối */}
                    <Modal
                        title="Xác nhận từ chối"
                        open={open}
                        onOk={() => handleOk(id)}
                        onCancel={handleCancel}
                    >
                        <Form>
                            <Form.Item label="Lý do: " required style={{ marginBottom: 10 }}>
                                <Input.TextArea
                                    rows={5}
                                    name="reason"
                                    value={reason}
                                    onChange={(e) => setReason(e.target.value)}
                                />
                            </Form.Item>
                            {errMessage && (
                                <span style={{ color: "red", marginLeft: 57 }}>
                                    {errMessage}
                                </span>
                            )}
                        </Form>
                    </Modal>
                    <Modal
                        title="Xác nhận từ chối sự kiện hàng kỳ"
                        open={openWait}
                        footer={null}
                        onCancel={cancelModalReasonWait}
                    >
                        <Form>
                            <Form.Item label="Lý do: " required style={{ marginBottom: 10 }}>
                                <Input.TextArea
                                    rows={5}
                                    name="reasonWait"
                                    value={reasonWait}
                                    onChange={(e) => setReasonWait(e.target.value)}
                                />
                                <span style={{ color: "red" }}>{errorReasonWait}</span>
                            </Form.Item>
                        </Form>
                        <div style={{ display: "flex", justifyContent: "flex-end" }}>
                            <Button
                                style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
                                onClick={noApproverPeriodicEvent}
                            >
                                Xác nhận
                            </Button>
                        </div>
                    </Modal>
                    {/* Modal hình ảnh */}
                    <Modal
                        width={"55%"}
                        zIndex={"99"}
                        open={isModalImage}
                        onCancel={closeModalImage}
                        footer={null}
                        closable={false}
                    >
                        {selectedImage && (
                            // eslint-disable-next-line jsx-a11y/img-redundant-alt
                            <img
                                style={{ maxWidth: "100%" }}
                                src={selectedImage}
                                alt="Selected Image"
                            />
                        )}
                    </Modal>

                    <CommentSection value={id} />
                </Form>
            </Card>
            {contextHolder}
        </div>
    );
}