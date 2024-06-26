import React, { useEffect, useState } from "react";
import { Button, Col, Form, Input, Row, Select, Table, Tooltip } from "antd";
import Title from "antd/es/typography/Title";
import { FileExcelOutlined, FilterOutlined } from "@ant-design/icons";
import { Option } from "antd/es/mentions";
import { APEventClosedApi } from "./APEventClosedApi";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";

export default function APEventClosed() {
    const [params, setParams] = useState({}); // params của bộ lọc
    const [listEventClose, setListEventClose] = useState([]);
    const [totalPages, setTotalPages] = useState(0); // tổng số trang
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang

    const [listMajor, setListMajor] = useState([]); // DS bộ môn
    const [listObject, setListObject] = useState([]); // DS đối tượng
    const [listCategory, setListCategory] = useState([]); // DS thể loại
    const [listSemester, setListSemester] = useState([]); // DS học kỳ

    // load dữ liệu khi truy cập vào trang
    useEffect(() => {
        APEventClosedApi.fetchAll({})
            .then((response) => {
                setListEventClose(response.data.data);
                setTotalPages(response.data.totalPages);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
        APEventClosedApi.fetchCategory()
            .then((response) => {
                setListCategory(response.data.data);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
        APEventClosedApi.fetchObject()
            .then((response) => {
                setListObject(response.data.data);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
        APEventClosedApi.fetchMajor()
            .then((response) => {
                setListMajor(response.data.data);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
        APEventClosedApi.fetchSemester()
            .then((response) => {
                setListSemester(response.data.data);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
    }, []);

    // load lại dữ liệu khi params thay đổi
    useEffect(() => {
        APEventClosedApi.fetchAll(params)
            .then((response) => {
                ////  //console.log(response.data.data)
                setListEventClose(response.data.data);
                setTotalPages(response.data.totalPages);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
    }, [params]);

    const columns = [
        {
            title: "#",
            dataIndex: "stt",
            key: "stt",
            align: "center",
            render: (text) => <p>{text}</p>,
        },
        {
            title: "Tên sự kiện",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Học kỳ",
            dataIndex: "semester",
            key: "semester",
            render: (sesmester, record) => <>{sesmester === null ? (<p style={{ textAlign: "center" }}>-</p>)
                : `${sesmester} (${record.blockNumber})`}</>,
        },
        {
            title: "Thể loại",
            dataIndex: "category",
            key: "category",
            render: (category, record) => (
                <span>
                    {category === null ? (
                        <p style={{ textAlign: "center" }}>-</p>
                    ) : (
                        category
                    )}
                </span>
            ),
        },
        {
            title: "Đối tượng",
            dataIndex: "object",
            key: "object",
            render: (question, record) => (
                <span>
                    {question === null ? (
                        <p style={{ textAlign: "center" }}>-</p>
                    ) : (
                        question
                    )}
                </span>
            ),
        },
        {
            title: "Bộ môn",
            dataIndex: "major",
            key: "major",
            render: (feedback, record) => (
                <span>
                    {feedback === null ? (
                        <p style={{ textAlign: "center" }}>-</p>
                    ) : (
                        feedback
                    )}
                </span>
            ),
        },
        {
            title: "Lý do",
            dataIndex: "reason",
            key: "reason",
            render: (x) => (x == null ? "Không có lý do" : x),
        },
        {
            title: "Thao tác",
            dataIndex: "id",
            key: "id",
            width: 90,
            render: (x, record) => (
                <>
                    <div className="textCenter">
                        <Link to={`/approver-management/event-detail/${x}`}>
                            <Tooltip placement="top" title={"Xem chi tiết"}>
                                <Button shape="circle" size={"middle"} type="primary">
                                    <FontAwesomeIcon icon={faEye} />
                                </Button>
                            </Tooltip>
                        </Link>
                    </div>
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
                <Form layout="vertical" onFinish={(data) => setParams(data)}>
                    <Row gutter={12} style={{ marginBottom: "10px" }}>
                        <Col flex="1 1 125px">
                            <Form.Item name={"name"} label="Tên sự kiện">
                                <Input placeholder="Nhập tên sự kiện đã đóng..." />
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"semester"} label="Học kỳ">
                                <Select
                                    showSearch
                                    placeholder="Nhập học kỳ..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) =>
                                        (option?.children ?? "")
                                            .toLowerCase()
                                            .includes(input.toLowerCase())
                                    }
                                >
                                    <Option value="">Tất cả</Option>
                                    {listSemester.map((item, index) => (
                                        <Option key={index} value={item.id}>
                                            {item.name}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"category"} label="Thể loại">
                                <Select
                                    showSearch
                                    placeholder="Nhập thể loại..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) =>
                                        (option?.children ?? "")
                                            .toLowerCase()
                                            .includes(input.toLowerCase())
                                    }
                                >
                                    <Option value="">Tất cả</Option>
                                    {listCategory.map((item, index) => (
                                        <Option key={index} value={item.id}>
                                            {item.name}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"object"} label="Đối tượng">
                                <Select
                                    showSearch
                                    placeholder="Nhập đối tượng..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) =>
                                        (option?.children ?? "")
                                            .toLowerCase()
                                            .includes(input.toLowerCase())
                                    }
                                >
                                    <Option value="">Tất cả</Option>
                                    {listObject.map((item, index) => (
                                        <Option key={index} value={item.id}>
                                            {item.name}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"major"} label="Bộ môn">
                                <Select
                                    showSearch
                                    placeholder="Nhập bộ môn..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) =>
                                        (option?.children ?? "")
                                            .toLowerCase()
                                            .includes(input.toLowerCase())
                                    }
                                >
                                    <Option value="">Tất cả</Option>
                                    {listMajor.map((item, index) => (
                                        <Option key={index} value={item.id}>
                                            {item.name}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="0 1 ">
                            <Form.Item label="​">
                                <Button
                                    type="primary"
                                    htmlType="submit"
                                    icon={<FilterOutlined />}
                                >
                                    Lọc
                                </Button>
                            </Form.Item>
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
                <Row gutter={12}>
                    <Col flex="8 1">
                        <Title level={5}>Danh sách sự kiện đã đóng</Title>
                    </Col>
                    <Col flex="1 1">
                        <Button type="primary" style={{ backgroundColor: "#1E6E43" }}>
                            <FileExcelOutlined /> Export Excel
                        </Button>
                    </Col>
                </Row>
                <Table
                    bordered
                    id="my-table"
                    style={{ marginTop: "10px" }}
                    dataSource={listEventClose}
                    columns={columns}
                    pagination={{
                        showSizeChanger: true,
                        current: currentPage,
                        pageSize: pageSize,
                        pageSizeOptions: [10, 20, 50, 100],
                        total: totalPages * 10,
                        onChange: (page, pageSize) => {
                            const data = { ...params };
                            data.size = pageSize;
                            data.page = page - 1;
                            setParams(data);
                            setCurrentPage(page);
                            setPageSize(pageSize);
                        },
                    }}
                    scroll={{
                        x: 1300,
                    }}
                />
            </div>
        </>
    );
}
