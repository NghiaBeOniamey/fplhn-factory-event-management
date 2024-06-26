import React, { useEffect, useState } from "react";
import {
    Button,
    Col,
    Form,
    Input,
    message,
    Modal,
    Popconfirm,
    Row,
    Space,
    Table,
    Radio,
    Upload,
    Image, InputNumber,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faList,
    faPencilAlt,
    faPenToSquare,
    faPlus,
    faTrash,
    faXmark,
    faCirclePlus,
    faPaperPlane,
} from "@fortawesome/free-solid-svg-icons";
import { UploadOutlined, DownloadOutlined, ExclamationCircleFilled } from '@ant-design/icons';
import OREventDetailApi from "../OREventDetailApi";
import { useParams } from "react-router-dom";
import ModalAddEvidence from "./OREDModalAddEvidence/ModalAddEvidence";
import '../OREDEvidence/evidence.css'

const { confirm } = Modal;

/**
 *
 * @param eventDetail: chi tiết sự kiện
 * @param numberAttendees: Số người tham gia điểm danh thực tế
 * @param actualNumberParticipants Số người tham gia thực tế (cao nhất)
 * @param percentage
 * @param numberRegistrants: Số người đăng ký
 * @param eventStatus: Trạng thái sự kiện
 * @returns {JSX.Element}
 * @constructor
 */
// numberRegistrants: Số người đăng ký; countNumberParticipant: Số người điểm danh hệ thống
const OREDEvidence = ({
    eventDetail,
    numberAttendees,
    actualNumberParticipants,
    percentage,
    numberRegistrants,
    eventStatus,
    updateEventStatus
}) => {

    const [listEvidence, setListEvidence] = useState([]);
    const [idEvidence, setIdEvidence] = useState('');
    const [name, setName] = useState("");
    const [path, setPath] = useState("");
    const [description, setdescription] = useState("");
    const [numberParticipant, setNumberParticipant] = useState(0);
    const [visible, setVisible] = useState(false);
    const [imageSrc, setImageSrc] = useState("");

    // modal
    const [open, setOpen] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isExisted, setIsExisted] = useState(false);
    const [isUpdate, setIsUpdate] = useState();

    // image
    const [selectedOption, setSelectedOption] = useState(0);
    const [selectedImageChange, setSelectedImageChange] = useState('');
    const [selectedImage, setSelectedImage] = useState('');

    // err
    const [errName, setErrName] = useState();
    const [errPath, setErrPath] = useState();
    const [errImg, setErrImg] = useState();

    const [isRoleHost, setIsRoleHost] = useState(false);
    const { id } = useParams();

    useEffect(() => {
        checkRole();
        getEvidences();
        setNumberParticipant(actualNumberParticipants === null ? '' : actualNumberParticipants);
    }, [])

    useEffect(() => {
        if (selectedImage === '' || selectedImage === null) {
            setIsExisted(false)
        } else {
            setIsExisted(true);
        }
    }, [selectedImage]);

    // Kiểm tra quyền là Host hay co-host
    const checkRole = () => {
        OREventDetailApi.checkOrganizerRole(id).then(
            (res) => {
                setIsRoleHost(res.data.data);
            }
        ).catch((err) => {
            ////  //console.log(err);
        });
    };

    // Lấy danh sách evidence
    const getEvidences = () => {
        OREventDetailApi.getEvidenceByIdEvent(id).then(
            (res) => {
                setListEvidence(res.data.data);
            }
        ).catch((err) => {
            message.error('Không thể lấy danh sách evidence');
        });
    }

    //Modal add evidence
    const showModal = () => {
        setIsModalOpen(true);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };

    //Modal Update evidence
    const openModalUpdateEventEvidence = (item) => {
        setIsUpdate(true);
        setErrName("");
        setErrPath("");
        setIdEvidence(item.id);
        setName(item.name);
        setdescription(item.description);
        setSelectedOption(item.evidenceType);
        if (item.evidenceType === 0) {
            setSelectedImage("");
            setPath(item.path);
        } else {
            setPath('');
            setSelectedImage(item.path);
        }
        setOpen(true);
    };

    const handleOptionChange = (e) => {
        setSelectedOption(e.target.value);
    };

    const columns = [
        {
            title: "STT",
            width: "5%",
            dataIndex: "recordNumber",
            key: "recordNumber",
            render: (text, record, index) => <span>{index + 1}</span>,
        },
        {
            title: "Tên evidence",
            width: "25%",
            dataIndex: "name",
            key: "name",
            render: (text) => (text ? text : "_"),
        },
        {
            title: "Đường dẫn",
            width: "28%",
            style: {
                background: "red",
            },
            dataIndex: "path",
            key: "path",
            render: (path, record) => {
                if (record.evidenceType === 0) {
                    // evidenceType là 0, hiển thị đường dẫn dưới dạng liên kết
                    return (
                        <a
                            href={path}
                            target="_blank"
                            style={{
                                maxWidth: 280,
                                whiteSpace: "nowrap",
                                overflow: "hidden",
                                textOverflow: "ellipsis",
                                display: "inline-block",
                                verticalAlign: "middle",
                            }}
                            rel="noreferrer"
                        >
                            Tại đây
                        </a>
                    );
                } else if (record.evidenceType === 1) {
                    // evidenceType là 1, hiển thị một nút để mở modal
                    return (
                        <>
                            <Button type="link" style={{ padding: 0 }}
                                onClick={() => {
                                    setImageSrc(path);
                                    setVisible(true);
                                }}>
                                Xem ảnh
                            </Button>
                        </>
                    );
                } else {
                    return "_";
                }
            },
        },
        {
            title: "Mô tả",
            width: "30%",
            dataIndex: "description",
            key: "description",
            render: (text) => (text ? text : "_"),
        },
        {
            title: "Hành động",
            width: "12%",
            dataIndex: "actions",
            key: "actions",
            render: (text, record) => (
                <Space size="middle">
                    <FontAwesomeIcon
                        className="custom-icon"
                        icon={faPencilAlt}
                        style={{ marginRight: "15px" }}
                        onClick={() => {
                            openModalUpdateEventEvidence(record);
                        }}
                    />
                    <Popconfirm
                        title="Bạn chắc chắn muốn xóa evidence này không?"
                        onConfirm={() => {
                            deleteEventEvidence(record.id);
                        }}
                        okText="Có"
                        cancelText="Không"
                    >
                        {" "}
                        <FontAwesomeIcon icon={faTrash} className="custom-icon" />{" "}
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    // Update evidence
    const handleUpdateEventEvidence = () => {
        let check = 0;
        if (name === "") {
            setErrName("Tên evidence không được để trống!");
            check += 1;
        } else {
            setErrName("");
        }
        if (selectedOption === 0) {
            if (path === "") {
                setErrPath("Đường dẫn không được để trống!");
                setErrImg('')
                check += 1;
            } else {
                setErrPath("");
                setErrImg('')
            }
        } else {
            if (selectedImage === "") {
                setErrImg("Bạn chưa lưu ảnh!");
                setErrPath('')
                check += 1;
            } else {
                setErrPath("");
                setErrImg('')
            }
        }
        if (check === 0) {
            if (isRoleHost === false) {
                message.error("Bạn không có quyền cập nhật thông tin của sự kiện");
            } else {
                if (isUpdate) {
                    let data = {
                        idEvent: id,
                        name: name,
                        path: path,
                        description: description,
                        evidenceType: selectedOption
                    };
                    OREventDetailApi.updateEvidence(idEvidence, data).then(
                        (res) => {
                            let newdata = {
                                id: res.data.data.id,
                                ...data
                            };
                            const index = listEvidence.findIndex(
                                (item) => item.id === idEvidence
                            );
                            const newListEvidence = [...listEvidence];
                            newListEvidence.splice(index, 1);
                            newListEvidence.splice(index, 0, newdata);
                            setListEvidence(newListEvidence);
                            setOpen(false);
                            message.success("Cập nhật thành công");
                        },
                        (err) => {
                            //  //console.log(err.message);
                        }
                    );
                }
            }
        }
    };

    // Delete evidence
    const deleteEventEvidence = (idEventEvidence) => {
        OREventDetailApi.deleteEvidence(idEventEvidence).then(
            (res) => {
                let updateListEventEvidence = listEvidence.filter(
                    (record) => record.id !== idEventEvidence
                );
                setListEvidence(updateListEventEvidence);
                message.success("Xóa thành công");
            },
            (err) => {
                //  //console.log(err);
            }
        );
    };

    //upload image
    const handleImageUpload = (info) => {
        if (info.file.status === 'uploading') {
            setSelectedImageChange(info.file.originFileObj);
            setSelectedImage('');
        }
    };
    const handleSaveImage = async () => {
        if (isRoleHost === false) {
            message.error("Bạn không có quyền cập nhật thông tin của sự kiện");
        } else {
            let random = (Math.floor(Math.random() * 1000) + 1) + '';
            const formData = new FormData();
            formData.append('file', selectedImageChange);
            formData.append('type', 3);
            await OREventDetailApi.uploadImage(formData, id + random).then((res) => {
                message.success('Upload thành công');
                setPath(res.data.data);
                setSelectedImage(res.data.data);
                setSelectedImageChange('');
            }).catch((err) => {
                message.error('Upload thất bại');
                setSelectedImage('');
            });
        }
    };
    //End upload image

    //updateNumberParticipant
    const showConfirmUpdateActualNumberParticipants = () => {
        confirm({
            title: "Cảnh báo!!!",
            icon: <ExclamationCircleFilled />,
            content:
                "Số người tham gia bạn báo cáo chênh lệch so với số người tham gia đã điểm danh!!!",
            onOk() {
                updateNumberParticipant();
            },
            onCancel() {
            },
        });
    };

    const showConfirmSendMail = () => {
        confirm({
            title: eventStatus === 5 ? "Xác nhận" : "Lưu ý!!!",
            icon: <ExclamationCircleFilled />,
            content:
                eventStatus === 5 ? "Bạn chắc chắn muốn gửi mail"
                    : "Sau khi gửi mail báo cáo hệ thống sẽ cập nhật trạng thái sự kiện thành `Đã tổ chức`. Bạn chắc chắn muốn thực hiện???",
            onOk() {
                sendMailEvidence();
            },
            onCancel() {
            },
        });
    };

    const handleUpdateNumberParticipants = () => {
        let check = 0;
        if (numberParticipant === '') {
            message.error("Vui lòng nhập số người tham gia cao nhất");
            check += 1;
        } else if (numberParticipant < 0) {
            message.error("Số người tham gia không thể âm!");
            check += 1;
        } else if (!(/^\d+$/.test(numberParticipant))) {
            message.error("Số người tham gia phải là số!");
            check += 1;
        }
        if (check === 0) {
            if (numberParticipant !== numberAttendees) {
                showConfirmUpdateActualNumberParticipants();
            } else {
                updateNumberParticipant();
            }
        }
    };
    const updateNumberParticipant = () => {
        OREventDetailApi.updateNumberParticipant({
            id: id,
            numberParticipant: numberParticipant
        }).then(
            (res) => {
                eventDetail(id);
                message.success("Lưu thông tin thành công");
            },
            (err) => {
                message.warning(err.response.data.message);
                //  //console.log(err.response);
            }
        );
    };

    // Gửi mail báo cáo sau sự kiện
    const sendMailEvidence = () => {
        if (listEvidence.length === 0) {
            message.error("Chưa có evidence cho sự kiện.");
        } else if (numberParticipant === 0) {
            message.error("Bạn chưa cập nhật số người tham gia cao nhất");
        } else {
            let data = {
                idEvent: id,
                percentage: percentage,
                countParticipant: numberRegistrants
            }
            OREventDetailApi.sendMailEvidence(data).then(
                (res) => {
                    message.success("Gửi mail thành công");
                    if (eventStatus === 4) {
                        updateEventStatus(5);
                    }
                },
                (err) => {
                    message.error("Gửi mail thất bại");
                    //  //console.log(err.response);
                }
            )
        }
    }

    return (
        <>
            <div style={{ marginTop: "20px" }}>
                <div
                    style={{
                        display: "flex",
                        alignItems: "center",
                        marginBottom: "10px",
                    }}
                >
                    <h4 className="title-comment">
                        <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
                        <span style={{ marginLeft: "7px" }}>Báo cáo</span>
                    </h4>
                </div>
                <div style={{ marginBottom: "10px" }}>
                    <Form.Item
                        label="Số người tham gia cao nhất"
                        style={{ marginLeft: "30px" }}
                    >
                        <Space.Compact>
                            <InputNumber
                                min={1}
                                max={32000}
                                value={numberParticipant ? numberParticipant : ''}
                                onChange={(value) => setNumberParticipant(value)}
                            />
                            <Popconfirm
                                title="Bạn chắc chắn muốn cập nhật số người tham gia cao nhất?"
                                onConfirm={() => {
                                    handleUpdateNumberParticipants();
                                }}
                                okText="Có"
                                cancelText="Không"
                            >
                                <Button type="primary">
                                    Lưu
                                </Button>
                            </Popconfirm>
                        </Space.Compact>

                        <Button
                            type="primary"
                            className="btn-form-event"
                            onClick={() => showModal()}
                            style={{
                                backgroundColor: "#0098d1",
                                float: "right",
                                marginRight: '20px'
                            }}
                        >
                            Thêm Evidence
                            <FontAwesomeIcon
                                icon={faCirclePlus}
                                style={{ color: "#ffffff", marginLeft: "7px" }}
                            />
                        </Button>
                        {/*<Popconfirm*/}
                        {/*    title="Bạn có chắc muốn gửi mail báo cáo cho sự kiện này?"*/}
                        {/*    onConfirm={() => {*/}
                        {/*        sendMailEvidence();*/}
                        {/*    }}*/}
                        {/*    okText="Có"*/}
                        {/*    cancelText="Không"*/}
                        {/*>*/}
                        <Button
                            type="primary"
                            className="btn-form-event"
                            style={{
                                backgroundColor: "#0098d1",
                                float: "right",
                                marginRight: '10px'
                            }}
                            onClick={showConfirmSendMail}
                        >
                            Gửi mail báo cáo
                            <FontAwesomeIcon icon={faPaperPlane} style={{ color: "#ffffff", marginLeft: "7px" }} />
                        </Button>
                        {/*</Popconfirm>*/}
                    </Form.Item>
                </div>

                <Table
                    columns={columns}
                    dataSource={listEvidence}
                    pagination={false}
                    scroll={{
                        x: 1300,
                    }}
                />
            </div>

            {/* Modal add */}
            <ModalAddEvidence
                visible={isModalOpen}
                onCancel={() => handleCancel()}
                getEvidences={() => getEvidences()}
            />

            {/* Modal update*/}
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
                        onClick={handleUpdateEventEvidence}
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
                        {isUpdate ? "Cập nhật evidence" : "Thêm evidence"}
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
                    autoComplete="off"
                >
                    <Row>
                        <Col span={11} style={{ marginRight: "20px" }}>
                            <Form.Item
                                label="Tên evidence"
                                rules={[{ required: true }]}
                            >
                                <Input
                                    placeholder="Nhập vào tên Evidence"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                />
                                <span style={{ color: "red" }}>{errName}</span>
                            </Form.Item>
                            <Form.Item label="Thể loại" rules={[{ required: true }]}>
                                <Radio.Group onChange={handleOptionChange} value={selectedOption}>
                                    <Radio value={0}>Đường Link</Radio>
                                    <Radio value={1}>Ảnh</Radio>
                                </Radio.Group>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Mục tiêu sự kiện"
                                rules={[{ required: true }]}>
                                <Input.TextArea
                                    rows={2}
                                    value={description}
                                    onChange={(e) => {
                                        setdescription(e.target.value);
                                    }}
                                />
                            </Form.Item>

                            <Form.Item label="Đường dẫn">
                                {selectedOption === 0 ? (
                                    <>
                                        <Input
                                            placeholder="Nhập vào đường dẫn"
                                            value={path}
                                            onChange={(e) => setPath(e.target.value)}
                                        />
                                        <span style={{ color: "red" }}>{errPath}</span>
                                    </>
                                ) : (
                                    <>
                                        <Upload
                                            customRequest={() => {
                                            }}
                                            showUploadList={false}
                                            onChange={handleImageUpload}
                                        >
                                            <Button icon={<UploadOutlined />}>Tải ảnh</Button>
                                        </Upload>

                                    </>
                                )}
                            </Form.Item>
                        </Col>
                        <Col>
                            {selectedOption === 0 ? (
                                <></>
                            ) : (
                                <>
                                    {isExisted && (
                                        <div style={{ width: "95%" }}>
                                            <h4>Ảnh đã lưu:</h4>
                                            <img src={selectedImage} alt="Ảnh đã lưu"
                                                style={{
                                                    maxWidth: "100%",
                                                    borderRadius: "8px",
                                                    height: "100%",
                                                    objectFit: "cover"
                                                }} />
                                        </div>
                                    )}
                                    {selectedImageChange && !isExisted && (
                                        <div>
                                            <div style={{ width: "95%" }}>
                                                <h4>Ảnh đã chọn:</h4>
                                                <div>
                                                    <img
                                                        src={URL.createObjectURL(selectedImageChange)}
                                                        alt="Selected"
                                                        style={{
                                                            width: "100%",
                                                            borderRadius: "8px",
                                                            height: "100%",
                                                            objectFit: "cover"
                                                        }}
                                                    />
                                                </div>

                                            </div>
                                            <div style={{ textAlign: "right", marginTop: "10px" }}>
                                                <Button type="primary"
                                                    style={{ background: "#388e3c" }}
                                                    icon={<DownloadOutlined />}
                                                    onClick={() => handleSaveImage()}
                                                >
                                                    Lưu
                                                </Button>
                                            </div>
                                        </div>
                                    )}
                                    <span style={{ color: "red" }}>{errImg}</span>
                                </>
                            )}
                        </Col>
                    </Row>
                </Form>
            </Modal>

            {/* Xem hình ảnh */}
            <Image
                className="top-element"
                preview={{
                    visible: visible,
                    src: imageSrc,
                    onVisibleChange: (value) => {
                        setVisible(value);
                    },
                }}
            />
        </>
    )
};

export default OREDEvidence;