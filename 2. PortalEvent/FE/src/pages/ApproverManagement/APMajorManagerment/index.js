import React, { useEffect, useState } from "react";
import MajorModal from "./MajorModal/MajorModal";
import {
    Button,
    Col,
    Form,
    Input,
    Modal,
    Radio,
    Row,
    Space,
    Table, Tooltip,
} from "antd";
import { APMajorManagermentApi } from "./APMajorManagermentApi";
import Title from "antd/es/typography/Title";
import { DeleteOutlined, FilterOutlined } from "@ant-design/icons";
import { Alert, Snackbar } from "@mui/material";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import { APPROVER_MAJOR_MANAGEMENT } from "../../../constants/DisplayName";

export default function APMajorManagerment() {
    const [listMajor, setListMajor] = useState([]); //DS bộ môn
    const [filter, setFilter] = useState({}); // params bộ lọc
    const [currentPage, setCurrentPage] = useState(1); // trang hiện tại

    const [openAlert, setOpenAlert] = useState(false); // check mở thông báo

    const [messageAlert, setMessageAlert] = useState(""); // thông báo thành công
    const [isSuccess, setIsSuccess] = useState(true); // check thêm/cập nhật thành công

    useEffect(() => {
        loadMajor(filter);
    }, [filter]);

    const loadMajor = (data) => {
        APMajorManagermentApi.fetchAll(data)
            .then((response) => {
                setListMajor(response.data);
            })
            .catch((e) => {
                //  //console.log(e);
            });
    };

    const handleFilter = (data) => {
        loadMajor(data);
        setFilter(data);
        //  //console.log(data);
    };

    const handleDelete = (id) => {
        Modal.confirm({
            title: "Xác nhận",
            maskClosable: true,
            content: `Xác nhận xóa bộ môn ?`,
            okText: "Ok",
            cancelText: "Cancel",
            onOk: () => {
                APMajorManagermentApi.delete(id)
                    .then((response) => {
                        setIsSuccess(true);
                        setMessageAlert(response.data);
                        loadMajor();
                    })
                    .catch((e) => {
                        setIsSuccess(false);
                        setMessageAlert(e.response.data.message);
                    });
                setOpenAlert(true);
            },
        });
    };

    const handleCloseAlert = (event, reason) => {
        if (reason === "clickaway") {
            return;
        }
        setOpenAlert(false);
    };

    const columns = [
        {
            title: "#",
            dataIndex: "index",
            key: "index",
        },
        {
            title: "Mã bộ môn",
            dataIndex: "code",
            key: "code",
        },
        {
            title: "Tên bộ môn",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Email",
            dataIndex: "mailOfManager",
            key: "mail",
        },
        {
            title: "Bộ môn",
            dataIndex: "mainMajorCode",
            key: "mainMajor",
            render: (mainMajorCode, record) => (
                <span>
                    {mainMajorCode === null ? (
                        <p style={{ textAlign: "center" }}>-</p>
                    ) : (
                        `${mainMajorCode} - ${record.mainMajorName}`
                    )}
                </span>
            ),
        },
        {
            title: "Thao tác",
            dataIndex: "id",
            width: 90,
            render: (id) => (
                <>
                    <Space>
                        <MajorModal majorId={id} isUpdate={true} onSuccess={loadMajor} />
                        <Tooltip placement="top" title={"Xóa bộ môn"}>
                            <Button
                                type="primary"
                                size="middle"
                                shape="circle"
                                danger
                                onClick={() => handleDelete(id)}
                                icon={<DeleteOutlined />}
                            ></Button>
                        </Tooltip>
                    </Space>
                </>
            ),
        },
    ];

    return (
        <>
            <div
                style={{
                    backgroundColor: "#FFF",
                    padding: "10px",
                    borderRadius: "10px",
                    marginBottom: "10px",
                }}
            >
                <Title level={5}>
                    <FilterOutlined /> Bộ lọc
                </Title>
                <Form onFinish={handleFilter}>
                    <Row gutter={12} style={{ marginBottom: "10px" }}>
                        <Col flex="1 1 200px">
                            <Form.Item name={"value"}>
                                <Input placeholder="Tìm kiếm bộ môn / bộ môn theo mã, tên, email trưởng bộ môn..." />
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 200px">
                            <Form.Item name={"mainMajor"}>
                                <Radio.Group defaultValue={"all"}>
                                    <Radio value={"all"} defaultChecked={true}>
                                        Tất cả
                                    </Radio>
                                    <Radio value={"false"}>Bộ môn</Radio>
                                    <Radio value={"true"}>Bộ môn</Radio>
                                </Radio.Group>
                            </Form.Item>
                        </Col>
                        <Col flex="0 1 ">
                            <Button
                                type="primary"
                                htmlType="submit"
                                icon={<FilterOutlined />}
                            >
                                Lọc
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div
                style={{
                    backgroundColor: "#FFF",
                    padding: "10px",
                    borderRadius: "10px",
                }}
            >
                <MajorModal isUpdate={false} onSuccess={loadMajor} />
                <ShowHistoryModal displayName={APPROVER_MAJOR_MANAGEMENT} />
                <Table
                    style={{ marginTop: "10px" }}
                    dataSource={listMajor}
                    columns={columns}
                    pagination={{
                        current: currentPage,
                        pageSize: 5,
                        total: listMajor.length,
                        onChange: (page) => {
                            setCurrentPage(page);
                            loadMajor(filter);
                        },
                    }}
                    scroll={{
                        x: 1300,
                    }}
                />
            </div>

            <Snackbar
                open={openAlert}
                autoHideDuration={2000}
                onClose={handleCloseAlert}
            >
                <Alert
                    onClose={handleCloseAlert}
                    severity={isSuccess ? "success" : "error"}
                    sx={{ width: "100%" }}
                >
                    {messageAlert}
                </Alert>
            </Snackbar>
        </>
    );
}
