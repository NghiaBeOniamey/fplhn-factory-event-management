import { UndoOutlined } from "@ant-design/icons";
import {
    faEdit,
    faEye,
    faFilter,
    faTrash,
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
import { APPeriodicEventAPI } from "./APPeriodicEventAPI";
import JoditEditor from "jodit-react";
import { object } from "prop-types";
import { APEventDetailApi } from "../APEventDetail/APEventDetailApi";
import { Link } from "react-router-dom";

const PeriodicEvent = () => {
    const [listPeriodicEvents, setListPeriodicEvents] = useState([]);
    const [listPeriodicEventsWait, setListPeriodicEventsWait] = useState([]);
    const [current, setCurrent] = useState(1);
    const [currentWait, setCurrentWait] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [totalPagesWait, setTotalPagesWait] = useState(0);
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

    useEffect(() => {
        loadDataPeriodicEventsWaitApprover(currentWait);
    }, [currentWait]);

    const clearFilter = () => {
        setNameSearch("");
        setCategoryIdSearch("");
        setEventTypeSearch("");
        setMajorIdSearch("");
        setObjectIdSearch("");
    };

    const loadDataPeriodicEventsWaitApprover = (currentWaitParam) => {
        let filter = {
            name: "",
            categoryId: "",
            eventType: null,
            majorId: "",
            objectId: "",
            page: currentWaitParam - 1,
        };
        APPeriodicEventAPI.getPageEventWaitApprover(filter).then((response) => {
            setListPeriodicEventsWait(response.data.data.data);
            setTotalPagesWait(response.data.data.totalPages);
        });
    };

    const loadDataPeriodicEvents = () => {
        let filter = {
            name: nameSearch,
            categoryId: categoryIdSearch,
            eventType: eventTypeSearch === "" ? null : eventTypeSearch,
            majorId: majorIdSearch,
            objectId: objectIdSearch,
            page: current - 1,
        };
        APPeriodicEventAPI.getPage(filter).then((response) => {
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
            title: "Thao tác",
            dataIndex: "actions",
            key: "actions",
            align: "center",
            width: 110,
            render: (text, record) => (
                <div className={"textCenter"}>
                    <Tooltip title="Cập nhật">
                        <Button
                            style={{ marginRight: "5px" }}
                            size={"middle"}
                            shape={"circle"}
                            type={"primary"}
                            onClick={() => { openModalUpdate(record.id); }}>
                            <FontAwesomeIcon icon={faEdit} />
                        </Button>
                    </Tooltip>
                    <Popconfirm
                        title="Bạn có chắc muốn xóa không ?"
                        placement="topLeft"
                        okText="Yes"
                        onConfirm={() => {
                            deletePeriodicEvent(record.id);
                        }}
                        cancelText="No"
                    >
                        <Tooltip title="Xóa">
                            <Button type={"primary"} shape={"circle"} danger size={"middle"}>
                                <FontAwesomeIcon icon={faTrash} />
                            </Button>
                        </Tooltip>
                    </Popconfirm>
                </div>
            ),
        },
    ];

    const columnsWait = [
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
                <div>
                    <Link to={`/approver-management/event-detail/${record.id}`}>
                        <Tooltip title="Xem chi tiết">
                            <FontAwesomeIcon
                                icon={faEye}
                                style={{
                                    color: "rgb(38, 144, 214)",
                                    marginRight: "20px",
                                    cursor: "pointer",
                                }}
                            />
                        </Tooltip>
                    </Link>
                </div>
            ),
        },
    ];

    useEffect(() => {
        APPeriodicEventAPI.getListCategory().then((response) => {
            setListCategory(response.data.data);
        });

        APPeriodicEventAPI.getListMajor().then((response) => {
            setListMajor(response.data.data);
        });

        APPeriodicEventAPI.getListObject().then((response) => {
            setListObject(response.data.data);
        });
    }, []);

    const [isModalAddOpen, setIsModalAddOpen] = useState(false);
    const [nameAdd, setNameAdd] = useState("");
    const [errorNameAdd, setErrorNameAdd] = useState("");
    const [eventTypeAdd, setEventTypeAdd] = useState("");
    const [errorEventTypeAdd, setErrorEventTypeAdd] = useState("");
    const [expectedParticipantsAdd, setExpectedParticipantsAdd] = useState(0);
    const [errorExpectedParticipantsAdd, setErrorExpectedParticipantsAdd] =
        useState("");
    const [descriptionAdd, setDescriptionAdd] = useState("");
    const [categoryIdAdd, setCategoryIdAdd] = useState("");
    const [errorCategoryIdAdd, setErrorCategoryIdAdd] = useState("");
    const [majorAdd, setMajorAdd] = useState([]);
    const [objectAdd, setObjectAdd] = useState([]);

    const openModalAdd = () => {
        setNameAdd("");
        setEventTypeAdd("");
        setExpectedParticipantsAdd(0);
        setDescriptionAdd("");
        setCategoryIdAdd("");
        setErrorNameAdd("");
        setErrorEventTypeAdd("");
        setErrorExpectedParticipantsAdd("");
        setErrorCategoryIdAdd("");
        setMajorAdd([]);
        setObjectAdd([]);
        setIsModalAddOpen(true);
    };

    const handleAddCancel = () => {
        setIsModalAddOpen(false);
    };

    const handleAdd = () => {
        let check = 0;
        if (nameAdd.trim() === "") {
            check++;
            setErrorNameAdd("Tên không được để trống");
        } else {
            setErrorNameAdd("");
        }

        if (eventTypeAdd.trim() === "") {
            check++;
            setErrorEventTypeAdd("Loại sự kiện không được để trống");
        } else {
            setErrorEventTypeAdd("");
        }

        if (!isNaN(expectedParticipantsAdd) && expectedParticipantsAdd === "") {
            check++;
            setErrorExpectedParticipantsAdd(
                "Số lượng người dự kiến không được trống"
            );
        } else {
            if (expectedParticipantsAdd > 25000 || expectedParticipantsAdd <= 0) {
                check++;
                setErrorExpectedParticipantsAdd(
                    "Số lượng người dự kiến phải > 0 và không được vượt quá 25000"
                );
            } else {
                setErrorExpectedParticipantsAdd("");
            }
        }
        if (categoryIdAdd.trim() === "") {
            check++;
            setErrorCategoryIdAdd("Thể loại không được trống");
        } else {
            setErrorCategoryIdAdd("");
        }

        if (check === 0) {
            let obj = {
                name: nameAdd,
                eventType: parseInt(eventTypeAdd),
                categoryId: categoryIdAdd,
                expectedParticipants: expectedParticipantsAdd,
                description: descriptionAdd,
                listObject: objectAdd,
                listMajor: majorAdd,
            };
            APPeriodicEventAPI.create(obj).then(
                (response) => {
                    message.success("Thêm thành công");
                    loadDataPeriodicEvents();
                    setIsModalAddOpen(false);
                },
                (err) => {
                }
            );
        }
    };

    const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);
    const [idUpdate, setIdUpdate] = useState("");
    const [nameUpdate, setNameUpdate] = useState("");
    const [errorNameUpdate, setErrorNameUpdate] = useState("");
    const [eventTypeUpdate, setEventTypeUpdate] = useState("");
    const [errorEventTypeUpdate, setErrorEventTypeUpdate] = useState("");
    const [expectedParticipantsUpdate, setExpectedParticipantsUpdate] =
        useState(0);
    const [errorExpectedParticipantsUpdate, setErrorExpectedParticipantsUpdate] =
        useState("");
    const [descriptionUpdate, setDescriptionUpdate] = useState("");
    const [categoryIdUpdate, setCategoryIdUpdate] = useState("");
    const [errorCategoryIdUpdate, setErrorCategoryIdUpdate] = useState("");
    const [majorUpdate, setMajorUpdate] = useState([]);
    const [objectUpdate, setObjectUpdate] = useState([]);

    const openModalUpdate = (id) => {
        setIsModalUpdateOpen(true);
        loadDataDetail(id);
    };

    const loadDataDetail = (id) => {
        setIdUpdate(id);
        APPeriodicEventAPI.detail(id).then((response) => {
            let data = response.data.data;
            setNameUpdate(data.name);
            setEventTypeUpdate(data.eventType + "");
            setExpectedParticipantsUpdate(data.expectedParticipants);
            setDescriptionUpdate(data.description);
            setCategoryIdUpdate(data.categoryId);
            setMajorUpdate(data.listMajor);
            setObjectUpdate(data.listObject);
        });
    };

    const handleUpdateCancel = () => {
        setIdUpdate("");
        setNameUpdate("");
        setEventTypeUpdate("");
        setExpectedParticipantsUpdate(0);
        setDescriptionUpdate("");
        setCategoryIdUpdate("");
        setErrorNameUpdate("");
        setErrorEventTypeUpdate("");
        setErrorExpectedParticipantsUpdate("");
        setErrorCategoryIdUpdate("");
        setMajorUpdate([]);
        setObjectUpdate([]);
        setIsModalUpdateOpen(false);
    };

    const handleUpdate = () => {
        let check = 0;
        if (nameUpdate.trim() === "") {
            check++;
            setErrorNameUpdate("Tên không được để trống");
        } else {
            setErrorNameUpdate("");
        }

        if (eventTypeUpdate.trim() === "") {
            check++;
            setErrorEventTypeUpdate("Loại sự kiện không được để trống");
        } else {
            setErrorEventTypeUpdate("");
        }

        if (
            !isNaN(expectedParticipantsUpdate) &&
            expectedParticipantsUpdate === ""
        ) {
            check++;
            setErrorExpectedParticipantsUpdate(
                "Số lượng người dự kiến không được trống"
            );
        } else {
            if (
                expectedParticipantsUpdate > 25000 ||
                expectedParticipantsUpdate <= 0
            ) {
                check++;
                setErrorExpectedParticipantsUpdate(
                    "Số lượng người dự kiến phải > 0 và không được vượt quá 25000"
                );
            } else {
                setErrorExpectedParticipantsUpdate("");
            }
        }
        if (categoryIdUpdate.trim() === "") {
            check++;
            setErrorCategoryIdUpdate("Thể loại không được trống");
        } else {
            setErrorCategoryIdUpdate("");
        }

        if (check === 0) {
            let obj = {
                id: idUpdate,
                name: nameUpdate,
                eventType: parseInt(eventTypeUpdate),
                categoryId: categoryIdUpdate,
                expectedParticipants: expectedParticipantsUpdate,
                description: descriptionUpdate,
                listObject: objectUpdate,
                listMajor: majorUpdate,
            };
            APPeriodicEventAPI.update(obj).then(
                (response) => {
                    message.success("Cập nhật thành công");
                    loadDataPeriodicEvents();
                    setIsModalUpdateOpen(false);
                },
                (err) => {
                }
            );
        }
    };

    const deletePeriodicEvent = (id) => {
        APPeriodicEventAPI.delete(id).then(
            (response) => {
                loadDataPeriodicEvents();
                message.success("Xóa thành công");
            },
            (error) => {
            }
        );
    };

    return (
        <div className="rounded-lg shadow-md w-full bg-white p-8">
            {listPeriodicEventsWait.length > 0 && (
                <div style={{ marginTop: 20 }}>
                    <Card
                        title="Danh sách sự kiện đang chờ người phê duyệt, phê duyệt để được phong chức lên thành sự kiện hàng kỳ"
                        extra={null}
                        bordered={false}
                    >
                        <Table
                            dataSource={listPeriodicEventsWait}
                            columns={columnsWait}
                            bordered
                            pagination={{
                                current: currentWait,
                                pageSize: 10,
                                total: 10 * totalPagesWait,
                                onChange: (current) => {
                                    setCurrentWait(current);
                                },
                            }}
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
                        {" "}
                    </Card>
                </div>
            )}
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
                            Bộ môn
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
                <Card
                    title="Danh sách sự kiện hàng kỳ"
                    extra={
                        <Button htmlType="submit" onClick={openModalAdd}>
                            Thêm{" "}
                        </Button>
                    }
                    bordered={false}
                >
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
                title="Thêm sự kiện hàng kỳ"
                open={isModalAddOpen}
                onCancel={handleAddCancel}
                footer={null}
                width={800}
            >
                <Row>
                    <Col span={12} style={{ padding: 5 }}>
                        Tên sự kiện:
                        <Input
                            value={nameAdd}
                            onChange={(e) => {
                                setNameAdd(e.target.value);
                            }}
                            type="text"
                            placeholder="Nhập tên sự kiện"
                        />
                        <span style={{ color: "red" }}>{errorNameAdd}</span>
                    </Col>
                    <Col span={12} style={{ padding: 5 }}>
                        Loại sự kiện:
                        <Select
                            value={eventTypeAdd}
                            onChange={(e) => {
                                setEventTypeAdd(e);
                            }}
                            style={{ width: "100%" }}
                        >
                            <Select.Option value="">Chọn loại sự kiện</Select.Option>
                            <Select.Option value="0">Dành cho sinh viên</Select.Option>
                            <Select.Option value="1">Dành cho giảng viên</Select.Option>
                            <Select.Option value="2">Dành cho SV và GV</Select.Option>
                            <Select.Option value="3">Tutorial</Select.Option>
                        </Select>
                        <span style={{ color: "red" }}>{errorEventTypeAdd}</span>
                    </Col>
                    <Col span={12} style={{ padding: 5 }}>
                        Số người dự kiến tham gia:
                        <Input
                            value={expectedParticipantsAdd}
                            onChange={(e) => {
                                setExpectedParticipantsAdd(e.target.value);
                            }}
                            type="number"
                        />
                        <span style={{ color: "red" }}>{errorExpectedParticipantsAdd}</span>
                    </Col>{" "}
                    <Col span={12} style={{ padding: 5 }}>
                        Thể loại:
                        <Select
                            value={categoryIdAdd}
                            onChange={(e) => {
                                setCategoryIdAdd(e);
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
                        <span style={{ color: "red" }}>{errorCategoryIdAdd}</span>
                    </Col>
                    <Col span={24} style={{ padding: 5 }}>
                        Đối tượng:
                        <Select
                            value={objectAdd}
                            onChange={(e) => {
                                setObjectAdd(e);
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
                        Bộ môn
                        <Select
                            value={majorAdd}
                            onChange={(e) => {
                                setMajorAdd(e);
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
                            ref={null}
                            value={descriptionAdd}
                            onChange={(e) => {
                                setDescriptionAdd(e);
                            }}
                        />
                    </Col>
                </Row>
                <div
                    style={{ display: "flex", justifyContent: "flex-end", marginTop: 10 }}
                >
                    <Button
                        style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
                        onClick={handleAdd}
                    >
                        Thêm
                    </Button>
                </div>
            </Modal>
            <Modal
                title="Cập nhật sự kiện hàng kỳ"
                open={isModalUpdateOpen}
                onCancel={handleUpdateCancel}
                footer={null}
                width={800}
            >
                <Row>
                    <Col span={12} style={{ padding: 5 }}>
                        Tên sự kiện:
                        <Input
                            value={nameUpdate}
                            onChange={(e) => {
                                setNameUpdate(e.target.value);
                            }}
                            type="text"
                            placeholder="Nhập tên sự kiện"
                        />
                        <span style={{ color: "red" }}>{errorNameUpdate}</span>
                    </Col>
                    <Col span={12} style={{ padding: 5 }}>
                        Loại sự kiện:
                        <Select
                            value={eventTypeUpdate}
                            onChange={(e) => {
                                setEventTypeUpdate(e);
                            }}
                            style={{ width: "100%" }}
                        >
                            <Select.Option value="">Chọn loại sự kiện</Select.Option>
                            <Select.Option value="0">Dành cho sinh viên</Select.Option>
                            <Select.Option value="1">Dành cho giảng viên</Select.Option>
                            <Select.Option value="2">Dành cho SV và GV</Select.Option>
                            <Select.Option value="3">Tutorial</Select.Option>
                        </Select>
                        <span style={{ color: "red" }}>{errorEventTypeUpdate}</span>
                    </Col>
                    <Col span={12} style={{ padding: 5 }}>
                        Số người dự kiến tham gia:
                        <Input
                            value={expectedParticipantsUpdate}
                            onChange={(e) => {
                                setExpectedParticipantsUpdate(e.target.value);
                            }}
                            type="number"
                        />
                        <span style={{ color: "red" }}>
                            {errorExpectedParticipantsUpdate}
                        </span>
                    </Col>{" "}
                    <Col span={12} style={{ padding: 5 }}>
                        Thể loại:
                        <Select
                            value={categoryIdUpdate}
                            onChange={(e) => {
                                setCategoryIdUpdate(e);
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
                        <span style={{ color: "red" }}>{errorCategoryIdUpdate}</span>
                    </Col>
                    <Col span={24} style={{ padding: 5 }}>
                        Đối tượng:
                        <Select
                            value={objectUpdate}
                            onChange={(e) => {
                                setObjectUpdate(e);
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
                        Bộ môn
                        <Select
                            value={majorUpdate}
                            onChange={(e) => {
                                setMajorUpdate(e);
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
                            ref={null}
                            value={descriptionUpdate}
                            onChange={(e) => {
                                setDescriptionUpdate(e);
                            }}
                        />
                    </Col>
                </Row>
                <div
                    style={{ display: "flex", justifyContent: "flex-end", marginTop: 10 }}
                >
                    <Button
                        style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
                        onClick={handleUpdate}
                    >
                        Cập nhật
                    </Button>
                </div>
            </Modal>
        </div>
    );
};

export default PeriodicEvent;
