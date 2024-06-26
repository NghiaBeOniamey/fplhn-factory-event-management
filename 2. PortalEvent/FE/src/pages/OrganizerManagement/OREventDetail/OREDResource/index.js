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
} from "@fortawesome/free-solid-svg-icons";
import OREventDetailApi from "../OREventDetailApi";
import { useParams } from "react-router-dom";
import "./index.css";

const OREDResource = ({ isDisableResource, status }) => {
    const [idResource, setIdResource] = useState("");
    const [description, setdescription] = useState("");
    const [name, setName] = useState("");
    const [path, setPath] = useState("");
    const [listResource, setListResource] = useState([]);
    const [errdescription, setErrdescription] = useState();
    const [errName, setErrName] = useState();
    const [errPath, setErrPath] = useState();
    const [isUpdate, setIsUpdate] = useState(); //dùng để check xem modal là update hay là thêm

    const openModalUpdateEventResource = (item) => {
        setIsUpdate(true);
        setErrName("");
        setErrPath("");
        setIdResource(item.id);
        setName(item.name);
        setPath(item.path);
        setdescription(item.description);
        setOpen(true);
    };

    const openModalAddEventResource = () => {
        setName("");
        setPath("");
        setdescription("");
        setIsUpdate(false);
        setOpen(true);
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
            title: "Tên tài nguyên",
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
                // color: 'yellow'
            },
            dataIndex: "path",
            key: "path",
            render: (path) => (path ? (
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
                    {path}
                </a>
            ) : "_"),
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
                        icon={faPencilAlt}
                        className="custom-icon"
                        style={{ marginRight: "15px" }}
                        onClick={() => {
                            openModalUpdateEventResource(record);
                        }}
                    />
                    <Popconfirm
                        title="Bạn chắc chắn muốn xóa tài nguyên này không?"
                        onConfirm={() => {
                            deleteEventResource(record.id);
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
    const dynamicColumns = (isDisableResource || status === 5)
        ? columns.filter((column) => column.key !== "actions")
        : columns;

    const { id } = useParams();

    const deleteEventResource = (idResource) => {
        OREventDetailApi.deleteResource(idResource).then(
            (res) => {
                let updateListResource = listResource.filter(
                    (record) => record.id !== res.data.data
                );
                setListResource(updateListResource);
                message.success("Xóa thành công");
            },
            (err) => {
                message.warning(err.response.data.message);
            }
        );
    };

    useEffect(() => {
        OREventDetailApi.getAllResourceByIdEvent(id).then(
            (res) => {
                setListResource(res.data.data);
            },
            (err) => {
                //  //console.log("Error API get all resource");
            }
        );
    }, [id]);

    const handleAddOrUpdateEventLocation = () => {
        let check = 0;
        if (name.trim() === "" || name === null) {
            setErrName("Tên tài nguyên không được để trống!");
            check += 1;
        } else if (name.length > 254) {
            setErrName("Tên tài nguyên quá giới hạn ký tự!");
            check += 1;
        } else {
            setErrName("");
        }
        if (path === "" || path === null) {
            setErrPath("Đường dẫn không được để trống!");
            check += 1;
        } else if (path.length > 254) {
            setErrPath("Đường dẫn quá giới hạn ký tự!");
            check += 1;
        } else {
            setErrPath("");
        }
        if (description === "" || description === null) {
            setErrdescription("Mô tả không được để trống!");
            check += 1;
        } else if (description.length > 254) {
            setErrdescription("Mô tả quá giới hạn ký tự!");
            check += 1;
        } else {
            setErrdescription("");
        }
        if (check === 0) {
            if (isUpdate) {
                let data = {
                    id: idResource ? idResource.trim() : "",
                    description: description ? description.trim() : "",
                    name: name ? name.trim() : "",
                    path: path ? path.trim() : "",
                };
                OREventDetailApi.updateResource(data).then(
                    (res) => {
                        const index = listResource.findIndex(
                            (item) => item.id === idResource
                        );
                        const newListResource = [...listResource];
                        newListResource.splice(index, 1);
                        newListResource.splice(index, 0, data);
                        setListResource(newListResource);
                        setOpen(false);
                        message.success("Cập nhật thành công");
                    },
                    (err) => {
                        message.warning(err.response.data.message);
                    }
                );
            } else {
                let data = {
                    idEvent: id,
                    description: description ? description.trim() : "",
                    name: name ? name.trim() : "",
                    path: path ? path.trim() : "",
                };
                OREventDetailApi.createResource(data).then(
                    (res) => {
                        let resource = {
                            id: res.data.data.id,
                            description: description,
                            name: name,
                            path: path,
                        };
                        setListResource([resource, ...listResource]);
                        setOpen(false);
                        message.success("Thêm thành công");
                    },
                    (err) => {
                        message.warning(err.response.data.message);
                        //  //console.log("Error API add resource");
                    }
                );
            }
        }
    };

    const [open, setOpen] = useState(false);

    const handleCancel = () => {
        setOpen(false);
        setErrName("");
        setErrPath("");
        setErrdescription("");
        setName("");
        setPath("");
        setdescription("");
    }
    return (
        <>
            <Modal
                centered
                onCancel={handleCancel}
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
                        onClick={handleCancel}
                    >
                        Hủy
                        <FontAwesomeIcon
                            icon={faXmark}
                            style={{ color: "#ffffff", marginLeft: "7px" }}
                        />
                    </Button>,
                ]}
            >
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
                        <Col flex={1} style={{ marginRight: "60px" }}>
                            <Form.Item
                                label="Tên tài nguyên"
                                rules={[{ required: true }]}
                            >
                                <Input
                                    placeholder="Nhập vào tên tài nguyên"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                />
                                <span style={{ color: "red" }}>{errName}</span>
                            </Form.Item>
                        </Col>
                        <Col flex={1}>
                            <Form.Item label="Đường dẫn" rules={[{ required: true }]}>
                                <Input
                                    placeholder="Nhập vào đường dẫn"
                                    value={path}
                                    onChange={(e) => setPath(e.target.value)}
                                />
                                <span style={{ color: "red" }}>{errPath}</span>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Form.Item label="Mục tiêu sự kiên" rules={[{ required: true }]}>
                        <Input.TextArea
                            rows={4}
                            value={description}
                            onChange={(e) => {
                                setdescription(e.target.value);
                            }}
                        />
                        <span style={{ color: "red" }}>{errdescription}</span>
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
                        <span style={{ marginLeft: "7px" }}>Danh sách tài nguyên</span>
                    </h4>
                    <Button
                        type="primary"
                        className="btn-form-event"
                        onClick={() => {
                            openModalAddEventResource();
                        }}
                        style={{
                            backgroundColor: "#0098d1",
                            marginLeft: "auto",
                            display: isDisableResource ? "none" : "block", // Thêm điều kiện hiển thị dựa trên giá trị của isDisable
                        }}
                    >
                        Thêm tài nguyên
                        <FontAwesomeIcon
                            icon={faCirclePlus}
                            style={{ color: "#ffffff", marginLeft: "7px" }}
                        />
                    </Button>
                </div>

                <Table
                    columns={dynamicColumns}
                    dataSource={listResource}
                    pagination={false}
                    scroll={{
                        x: 1300,
                    }}
                />
            </div>
        </>
    );
};
export default OREDResource;
