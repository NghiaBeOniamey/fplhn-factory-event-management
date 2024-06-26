import React, { useEffect, useState } from "react";
import { Button, Col, Divider, Form, Grid, Input, message, Modal, Popconfirm, Row, Space, Table, Tooltip, } from "antd";
import moment from "moment";
import { ADSemesterApi } from "./ADSemesterApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faArrowsRotate,
    faFilter,
    faList,
    faMagnifyingGlass,
    faPenToSquare,
    faPlus,
    faTrash,
} from "@fortawesome/free-solid-svg-icons";
import { ADMIN_SEMESTER_MANAGEMENT } from "../../../constants/DisplayName";
import ShowHistoryModal from "../../../components/ShowHistoryModal";

export default function Semester() {
    const [name, setName] = useState("");
    const [startTime, setStartTime] = useState();
    const [endTime, setEndTime] = useState();
    const [startTimeFirstBlock, setStartTimeFirstBlock] = useState();
    const [endTimeFirstBlock, setEndTimeFirstBlock] = useState();
    const [startTimeSecondBlock, setStartTimeSecondBlock] = useState();
    const [endTimeSecondBlock, setEndTimeSecondBlock] = useState();
    const [listSemester, setListSemester] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setToTalPages] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [searchName, setSearchName] = useState("");

    const [errName, setErrName] = useState("");
    const [errStartTime, setErrStartTime] = useState("");
    const [errEndTime, setErrEndTime] = useState("");
    const [errStartTimeFirstBlock, setErrStartTimeFirstBlock] = useState("");
    const [errEndTimeFirstBlock, setErrEndTimeFirstBlock] = useState("");
    const [errStartTimeSecondBlock, setErrStartTimeSecondBlock] = useState("");
    const [errEndTimeSecondBlock, setErrEndTimeSecondBlock] = useState("");
    const [isOpenModal, setIsOpenModal] = useState(false);
    const [idUpdate, setIdUpdate] = useState('');

    useEffect(() => {
        loadListTable(currentPage);
    }, [currentPage]);

    const loadListTable = (currentPage) => {
        ADSemesterApi.fetchAll(currentPage, searchName).then((response) => {
            setListSemester(response.data.data.data);
            setToTalPages(response.data.data.totalPages);
            setCurrentPage(response.data.data.currentPage);
        });
    };

    const searchSemester = (currentPage,searchName) => {
        ADSemesterApi.fetchAll(currentPage, searchName).then((response) => {
            setListSemester(response.data.data.data);
            setToTalPages(response.data.data.totalPages);
            setCurrentPage(response.data.data.currentPage);
        });
    };

    const handleShowModal = (record) => {
        setName("");
        setStartTime("");
        setEndTime("");
        setStartTimeFirstBlock("");
        setEndTimeFirstBlock("");
        setStartTimeSecondBlock("");
        setEndTimeSecondBlock("");
        setErrName("");
        setErrStartTime("");
        setErrEndTime("");
        setErrStartTimeFirstBlock("");
        setErrEndTimeFirstBlock("");
        setErrStartTimeSecondBlock("");
        setErrEndTimeSecondBlock("");
        setIsOpenModal(true);
        if (record) {
            setIdUpdate(record.id);
            handleDetail(record);
        } else {
            setIdUpdate(undefined)
            setIdUpdate(false);
        }
    };

    const handleCancel = () => {
        setIsOpenModal(false);
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
            title: "Tên học kỳ",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "TG BĐ",
            dataIndex: "startTime",
            key: "startTime",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "TG KT",
            dataIndex: "endTime",
            key: "endTime",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "TG BĐ Block 1",
            dataIndex: "startTimeFirstBlock",
            key: "startTimeFirstBlock",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "TG KT Block 1",
            dataIndex: "endTimeFirstBlock",
            key: "endTimeFirstBlock",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "TG BĐ Block 2",
            dataIndex: "startTimeSecondBlock",
            key: "startTimeSecondBlock",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "TG KT Block 2",
            dataIndex: "endTimeSecondBlock",
            key: "endTimeSecondBlock",
            render: (text, record) => (
                <span>{moment(text).format("DD/MM/YYYY")}</span>
            ),
        },
        {
            title: "Thao tác",
            key: "action",
            width: 120,
            render: (text, record) => (
                <div className={"textCenter"}>
                    <Space size="small">
                        <Tooltip placement="top" title={"Sửa học kỳ"}>
                            <Button
                                key="update"
                                type="primary"
                                shape={"circle"}
                                size={"middle"}
                                htmlType="submit"
                                onClick={() => handleShowModal(record)}
                            >
                                <FontAwesomeIcon icon={faPenToSquare} />
                            </Button>
                        </Tooltip>
                        {/* <Popconfirm
                            title="Xóa học kỳ"
                            description="Bạn có chắc chắn muốn xóa học kỳ này không?"
                            onConfirm={() => {
                                handleDelete(record.id);
                            }}
                            okText="Có"
                            cancelText="Không"
                        >
                            <Tooltip placement="top" title={"Xóa học kỳ"}>
                                <Button
                                    key="delete" danger
                                    shape={"circle"}
                                    size={"middle"}
                                    type="primary"
                                    htmlType="button">
                                    <FontAwesomeIcon icon={faTrash} />
                                </Button>
                            </Tooltip>
                        </Popconfirm> */}
                    </Space>
                </div>
            ),
        },
    ];

    const handleDelete = (id) => {
        ADSemesterApi.delete(id)
            .then((response) => {
                loadListTable(currentPage);
                message.success("Xóa thành công");
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            });
    };

    const handleResets = () => {
        setSearchName("");
        searchSemester(currentPage,"");
    }

    const handleDetail = (obj) => {
        setName(obj.name);
        setStartTime(obj.startTime);
        setEndTime(obj.endTime);
        setStartTimeFirstBlock(obj.startTimeFirstBlock);
        setEndTimeFirstBlock(obj.endTimeFirstBlock);
        setStartTimeSecondBlock(obj.startTimeSecondBlock);
        setEndTimeSecondBlock(obj.endTimeSecondBlock);
    };

    const update = () => {
        let check = 0;
        if (!name.trim("")) {
            setErrName("Tên học kỳ không được để trống!");
            check += 1;
        } else if (/^\s+|\s+$/.test(name)) {
            setErrName("Tên học kỳ không được chứa dấu cách ở đầu và cuối chuỗi!");
            check += 1;
        } else {
            setErrName("");
        }

        //START TIME

        if (startTime == null || startTime === "") {
            setErrStartTime("Thời gian BĐ học kỳ không được để trống!");
            check += 1;
        } else if (startTime > endTime) {
            setErrStartTime("Thời gian BĐ học kỳ phải sau thời gian KT Học kỳ!");
            check += 1;
        } else {
            setErrStartTime("");
        }

        //END TIME

        if (endTime == null || endTime === "") {
            setErrEndTime("Thời gian KT học kỳ không được để trống!");
            check += 1;
        } else if (startTime > endTime) {
            setErrEndTime("Thời gian KT học kỳ phải trước thời gian BĐ Học kỳ!");
            check += 1;
        } else {
            setErrEndTime("");
        }

        //START TIME FIRST BLOCK

        // if (startTimeFirstBlock == null || startTimeFirstBlock === "") {
        //     setErrStartTimeFirstBlock("Thời gian BĐ block 1 không được để trống!");
        //     check += 1;
        // } else if (startTime > startTimeFirstBlock) {
        //     setErrStartTimeFirstBlock(
        //         "Thời gian BĐ block 1 không được sau thời gian BĐ học kỳ!"
        //     );
        //     check += 1;
        // } else if (
        //     startTimeFirstBlock > startTimeSecondBlock &&
        //     endTimeFirstBlock > endTimeSecondBlock
        // ) {
        //     setErrStartTimeFirstBlock(
        //         "Thời gian diễn ra block 1 phải trước thời gian diễn ra block 2!"
        //     );
        //     check += 1;
        // } else {
        //     setErrStartTimeFirstBlock("");
        // }

        //END TIME FIRST BLOCK

        if (endTimeFirstBlock == null || endTimeFirstBlock === "") {
            setErrEndTimeFirstBlock("Thời gian KT block 1 không được để trống!");
            check += 1;
        } else if (startTimeFirstBlock > endTimeFirstBlock) {
            setErrEndTimeFirstBlock(
                "Thời gian KT block 1 không được trước thời gian BĐ Block 1!"
            );
            check += 1;
        } else if (endTime < endTimeFirstBlock) {
            setErrEndTimeFirstBlock(
                "Thời gian KT block 1 phải sau thời gian KT học kỳ!"
            );
            check += 1;
        } else if (
            startTimeFirstBlock > startTimeSecondBlock &&
            endTimeFirstBlock > endTimeSecondBlock
        ) {
            setErrEndTimeFirstBlock(
                "Thời gian diễn ra block 1 phải trước thời gian diễn ra block 2!"
            );
            check += 1;
        } else {
            setErrEndTimeFirstBlock("");
        }

        //START TIME SECOND BLOCK

        // if (startTimeSecondBlock == null || startTimeSecondBlock === "") {
        //     setErrStartTimeSecondBlock("Thời gian BĐ block 2 không được để trống!");
        //     check += 1;
        // } else if (
        //     startTimeFirstBlock > startTimeSecondBlock &&
        //     endTimeFirstBlock > endTimeSecondBlock
        // ) {
        //     setErrStartTimeSecondBlock(
        //         "Thời gian diễn ra block 2 phải sau thời gian diễn ra block 1!"
        //     );
        //     check += 1;
        // } else if (startTime > startTimeSecondBlock) {
        //     setErrStartTimeSecondBlock(
        //         "Thời gian BĐ block 2 phải trước thời gian BĐ học kỳ!"
        //     );
        //     check += 1;
        // } else if (
        //     startTimeFirstBlock < endTimeSecondBlock &&
        //     endTimeFirstBlock > startTimeSecondBlock
        // ) {
        //     setErrStartTimeSecondBlock(
        //         "Thời gian diễn ra block 2 phải sau thời gian diễn ra block 1!"
        //     );
        //     check += 1;
        // } else if (startTime > startTimeSecondBlock) {
        //     setErrStartTimeSecondBlock(
        //         "Thời gian BĐ block 2 phải trước thời gian BĐ học kỳ!"
        //     );
        //     check += 1;
        // } else {
        //     setErrStartTimeSecondBlock("");
        // }
        //END TIME SECOND BLOCK

        // if (endTimeSecondBlock == null || endTimeSecondBlock === "") {
        //     setErrEndTimeSecondBlock("Thời gian KT block 2 không được để trống!");
        //     check += 1;
        // } else if (startTimeSecondBlock > endTimeSecondBlock) {
        //     setErrEndTimeSecondBlock(
        //         "Thời gian KT block 2 phải sau thời gian BĐ Block 2!"
        //     );
        //     check += 1;
        // } else if (
        //     startTimeFirstBlock > startTimeSecondBlock &&
        //     endTimeFirstBlock > endTimeSecondBlock
        // ) {
        //     setErrEndTimeSecondBlock(
        //         "Thời gian diễn ra block 2 phải sau thời gian diễn ra block 1!"
        //     );
        //     check += 1;
        // } else if (endTime < endTimeSecondBlock) {
        //     setErrEndTimeSecondBlock(
        //         "Thời gian KT block 2 phải sau thời gian KT học kỳ!"
        //     );
        //     check += 1;
        // } else {
        //     setErrEndTimeSecondBlock("");
        // }
        if (check === 0) {
            if (idUpdate) {
                let obj = {
                    id: idUpdate,
                    name: name,
                    startTime: new Date(startTime).getTime(),
                    endTime: new Date(endTime).getTime(),
                    startTimeFirstBlock: new Date(startTime).getTime(),
                    endTimeFirstBlock: new Date(endTimeFirstBlock).getTime(),
                    startTimeSecondBlock: new Date(endTimeFirstBlock).getTime(),
                    endTimeSecondBlock: new Date(endTime).getTime(),
                };
                ADSemesterApi.update(obj)
                    .then((response) => {
                        loadListTable(currentPage);
                        setIsOpenModal(false);
                        message.success("Cập nhật thành công !");
                    })
                    .catch((error) => {
                        message.warning(error.response.data.message);
                    });
            } else {
                let obj = {
                    // name: name,
                    // startTime: new Date(startTime).getTime(),
                    // endTime: new Date(endTime).getTime(),
                    // // startTimeFirstBlock: new Date(startTimeFirstBlock).getTime(),
                    // startTimeFirstBlock: startTime,
                    // endTimeFirstBlock: new Date(endTimeFirstBlock).getTime(),
                    // startTimeSecondBlock: moment(endTimeFirstBlock).add(1, 'days').toDate().getTime(),
                    // endTimeSecondBlock: endTime,

                    name: name,
                    startTime: new Date(startTime).getTime(),
                    endTime: new Date(endTime).getTime(),
                    startTimeFirstBlock: new Date(startTime).getTime(),
                    endTimeFirstBlock: new Date(endTimeFirstBlock).getTime(),
                    // startTimeSecondBlock: moment(new Date(endTimeFirstBlock).getTime()).add(1, 'days').toDate().getTime(),
                    startTimeSecondBlock: new Date(endTimeFirstBlock).getTime(),
                    endTimeSecondBlock: new Date(endTime).getTime(),
                };
                ADSemesterApi.create(obj)
                    .then((response) => {
                        loadListTable(currentPage);
                        setName("");
                        setStartTime("");
                        setEndTime("");
                        setStartTimeFirstBlock("");
                        setEndTimeFirstBlock("");
                        setStartTimeSecondBlock("");
                        setEndTimeSecondBlock("");
                        setIsOpenModal(false);
                        message.success("Thêm thành công!");
                    })
                    .catch((error) => {
                        message.warning(error.response.data.message);
                    });
            }
        }
    };

    return (
        <div
            style={{
                padding: 20,
                backgroundColor: "white",
                borderRadius: "1rem",
                boxShadow: "0 0 2px rgba(0, 2 ",
            }}
        >
            <div style={{ color: "#000066" }}>
                <Space size={8}>
                    {<FontAwesomeIcon icon={faFilter} />}
                    <p style={{ size: "100", padding: "5px" }}>Bộ lọc</p>
                </Space>
            </div>
            <Row style={{ justifyContent: "center" }}>
                <Col span={10} offset={-3}>
                    <Form.Item
                        label="Tên học kỳ"
                        name="name"
                    >
                        <Row>
                            <Input
                                placeholder="Nhập tên học kỳ"
                                value={searchName}
                                onChange={(e) => setSearchName(e.target.value)}
                            />
                        </Row>
                        <Row
                            style={{
                                display: "flex",
                                flexDirection: "row",
                                justifyContent: "center",
                                gap: "30px",
                                alignItems: "center",
                                width: "100%",
                                padding: "20px 0",
                            }}
                        >
                            <Button type="primary" htmlType="button"
                                onClick={() => searchSemester(currentPage,searchName)}>
                                <FontAwesomeIcon
                                    icon={faMagnifyingGlass}
                                    style={{ color: "#ffffff" }}
                                />
                                Tìm kiếm
                            </Button>
                            <Button
                                type="primary"
                                htmlType="button"
                                style={{ backgroundColor: "green" }}
                                onClick={handleResets}
                            >
                                <FontAwesomeIcon icon={faArrowsRotate} style={{ color: "#ffffff" }} />
                                Làm mới bộ lọc
                            </Button>
                        </Row>
                    </Form.Item>
                </Col>
            </Row>
            <Divider />
            <div
                style={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "space-between",
                    alignItems: "center",
                    width: "100%",
                    padding: "20px 0",
                }}
            >
                <div style={{ color: "#000066" }}>
                    <Space size={7}>
                        {<FontAwesomeIcon icon={faList} />}
                        <p style={{ size: "100" }}>HỌC KỲ</p>
                    </Space>
                </div>
                <Button key="add" type="primary" htmlType="submit" onClick={() => handleShowModal(undefined)}>
                    <FontAwesomeIcon icon={faPlus} /> &nbsp;Thêm học kỳ
                </Button>
                <ShowHistoryModal displayName={ADMIN_SEMESTER_MANAGEMENT} />
            </div>
            <Table
                dataSource={listSemester}
                columns={columns}
                rowKey="id"
                pagination={{
                    current: currentPage + 1,
                    pageSize: pageSize,
                    total: totalPages,
                    onChange: (current, pageSize) => {
                        setCurrentPage(current - 1);
                    },
                }}
                scroll={{
                    x: 1300,
                }}
            />

            {/*modal update */}
            <Modal
                title={idUpdate ? "Cập nhật học kỳ" : "Thêm học kỳ"}
                open={isOpenModal}
                onOk={() => update()}
                onCancel={handleCancel}
                width={500}
            >
                <hr />
                <br />
                <Form
                    name="myForm"
                    labelCol={{
                        span: 24,
                    }}
                    wrapperCol={{
                        span: 24,
                    }}
                >
                    <Row gutter={16}>
                        {/* Cột 1 (3 input) */}
                        {/*<Col span={12}>*/}
                        <Col span={24}>
                            <Form.Item
                                label="Tên học kỳ"
                                name="name"
                                rules={[
                                    {
                                        required: true,
                                        message: "Vui lòng nhập tên",
                                    },
                                ]}>
                                <Input
                                    name="name"
                                    value={name}
                                    onChange={(e) => {
                                        setName(e.target.value);
                                    }}
                                />
                                <span style={{ color: "red" }}>{errName}</span>
                            </Form.Item>

                            <Form.Item
                                label="Thời gian BĐ học kỳ"
                                name="endTime"
                                rules={[
                                    {
                                        required: true,
                                        message: "Vui lòng chọn thời gian bắt đầu",
                                    },
                                ]}>
                                <Input
                                    value={moment(startTime).format("YYYY-MM-DD")}
                                    onChange={(e) => {
                                        setStartTime(e.target.value);
                                    }}
                                    type="date"
                                />
                                <span style={{ color: "red" }}>{errStartTime}</span>
                            </Form.Item>

                            <Form.Item
                                label="Thời gian KT học kỳ"
                                name="endTime"
                                rules={[
                                    {
                                        required: true,
                                        message: "Vui lòng chọn thời gian kết thúc",
                                    },
                                ]}>
                                <Input
                                    value={moment(endTime).format("YYYY-MM-DD")}
                                    onChange={(e) => {
                                        setEndTime(e.target.value);
                                    }}
                                    type="date"
                                />
                                <span style={{ color: "red" }}>{errEndTime}</span>
                            </Form.Item>

                            <Form.Item
                                label="Thời gian KT Block 1"
                                name="endTimeBlock1"
                                rules={[
                                    {
                                        required: true,
                                        message: "Vui lòng chọn thời gian KT block1",
                                    },
                                ]}>
                                <Input
                                    value={moment(endTimeFirstBlock).format("YYYY-MM-DD")}
                                    onChange={(e) => {
                                        setEndTimeFirstBlock(e.target.value);
                                    }}
                                    type="date"
                                />
                                <span style={{ color: "red" }}>{errEndTimeFirstBlock}</span>
                            </Form.Item>
                        </Col>

                        {/* Cột 2 (4 input) */}
                        {/*<Col span={12}>*/}
                        {/*<Form.Item*/}
                        {/*    label="Thời gian BĐ Block 1"*/}
                        {/*    name="startTimeBlock1"*/}
                        {/*    rules={[*/}
                        {/*        {*/}
                        {/*            required: true,*/}
                        {/*            message: "Vui lòng chọn thời gian BĐ block 1",*/}
                        {/*        },*/}
                        {/*    ]}>*/}
                        {/*    <Input*/}
                        {/*        value={moment(startTimeFirstBlock).format("YYYY-MM-DD")}*/}
                        {/*        onChange={(e) => {*/}
                        {/*            setStartTimeFirstBlock(e.target.value);*/}
                        {/*        }}*/}
                        {/*        type="date"*/}
                        {/*    />*/}
                        {/*    <span style={{color: "red"}}>{errStartTimeFirstBlock}</span>*/}
                        {/*</Form.Item>*/}

                        {/*<Form.Item*/}
                        {/*    label="Thời gian KT Block 1"*/}
                        {/*    name="endTimeBlock1"*/}
                        {/*    rules={[*/}
                        {/*        {*/}
                        {/*            required: true,*/}
                        {/*            message: "Vui lòng chọn thời gian KT block1",*/}
                        {/*        },*/}
                        {/*    ]}>*/}
                        {/*    <Input*/}
                        {/*        value={moment(endTimeFirstBlock).format("YYYY-MM-DD")}*/}
                        {/*        onChange={(e) => {*/}
                        {/*            setEndTimeFirstBlock(e.target.value);*/}
                        {/*        }}*/}
                        {/*        type="date"*/}
                        {/*    />*/}
                        {/*    <span style={{color: "red"}}>{errEndTimeFirstBlock}</span>*/}
                        {/*</Form.Item>*/}

                        {/*<Form.Item*/}
                        {/*    label="Thời gian BĐ Block 2"*/}
                        {/*    name="startTimeBlock2"*/}
                        {/*    rules={[*/}
                        {/*        {*/}
                        {/*            required: true,*/}
                        {/*            message: "Vui lòng chọn thời gian BĐ block 2",*/}
                        {/*        },*/}
                        {/*    ]}>*/}
                        {/*    <Input*/}
                        {/*        value={moment(startTimeSecondBlock).format("YYYY-MM-DD")}*/}
                        {/*        onChange={(e) => {*/}
                        {/*            setStartTimeSecondBlock(e.target.value);*/}
                        {/*        }}*/}
                        {/*        type="date"*/}
                        {/*    />*/}
                        {/*    <span style={{color: "red"}}>{errStartTimeSecondBlock}</span>*/}
                        {/*</Form.Item>*/}

                        {/*<Form.Item*/}
                        {/*    label="Thời gian KT Block 2"*/}
                        {/*    name="endTimeBlock2"*/}
                        {/*    rules={[*/}
                        {/*        {*/}
                        {/*            required: true,*/}
                        {/*            message: "Vui lòng chọn thời gian KT block 2",*/}
                        {/*        },*/}
                        {/*    ]}>*/}
                        {/*    <Input*/}
                        {/*        value={moment(endTimeSecondBlock).format("YYYY-MM-DD")}*/}
                        {/*        onChange={(e) => {*/}
                        {/*            setEndTimeSecondBlock(e.target.value);*/}
                        {/*        }}*/}
                        {/*        type="date"*/}
                        {/*    />*/}
                        {/*    <span style={{color: "red"}}>{errEndTimeSecondBlock}</span>*/}
                        {/*</Form.Item>*/}
                        {/*</Col>*/}
                    </Row>
                </Form>
            </Modal>
        </div>
    );
}
