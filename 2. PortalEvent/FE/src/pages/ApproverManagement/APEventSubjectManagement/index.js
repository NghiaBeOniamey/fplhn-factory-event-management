import { SearchOutlined, UndoOutlined } from "@ant-design/icons";
import { faFilter, faLayerGroup, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Card, Col, Form, Input, Row, Table, Tag, message } from "antd";
import { useEffect, useState } from "react";
import { getCurrentUser } from "../../../utils/Common";
import { APMajorManagementApi } from "./APMajorManagementApi";

export const APSubjectManagerment = () => {

    //useForm
    const [form] = Form.useForm();
    //page
    const pageSize = 10;
    const [totalElement, setTotalElements] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    //listSubject
    const [listSubject, setListSubject] = useState([]);
    const [subjectCode, setSubjectCode] = useState("");

    //columns Table
    const columnsSubject = [
        {
            title: "#",
            render: (text, record, index) => {
                if (currentPage === 1) {
                    return index + 1;
                } else {
                    return (currentPage - 1) * pageSize + (index + 1);
                }
            }
        },
        {
            title: "Mã chuyên ngành", dataIndex: "code", key: "code",
            render: (code) => {
                return (
                    <Tag color="cyan">{code}</Tag>
                )
            }
        },
        { title: "Tên Chuyên ngành", dataIndex: "name", key: "name" }
    ];


    useEffect(() => {
        if (subjectCode === "") {
            const { subjectCode } = getCurrentUser();
            setSubjectCode(subjectCode)
            fetchListSubject(subjectCode);
        } else {
            fetchListSubject(subjectCode);
        }
    }, [currentPage]);


    const fetchListSubject = async (subjectCode) => {
        const { name, code } = form.getFieldsValue();
        try {
            const response = await APMajorManagementApi.fetchListSubject(name, code, subjectCode, currentPage);
            setTotalElements(response.data.data.length * response.data.totalPages);
            setListSubject(response.data.data);
        } catch (e) {
            for (let errMessage in e.response.data) {
                message.error(e.response.data[errMessage]);
            }
        }
    };


    const searchListSubject = async (name, code) => {
        try {
            const response = await APMajorManagementApi.fetchListSubject(name, code, subjectCode, currentPage);
            //console.log(response);
            setTotalElements(response.data.data.length * response.data.totalPages);
            setListSubject(response.data.data);
        } catch (e) {
            for (let errMessage in e.response.data) {
                message.error(e.response.data[errMessage]);
            }
        }
    };

    // //export Excel
    // const handleExportExcel = () => {

    // }

    //This function is used to filter majors by subject
    const handleFilterSubject = () => {
        const { name, code } = form.getFieldsValue();
        searchListSubject(name, code);
    }

    const handleClearFilter = () => {
        form.resetFields();
        searchListSubject("", "");
    }

    const handleSynchronizedSemesterIdentity = () => {
        APMajorManagementApi.synchronizedIdentity()
            .then(() => {
                message.info("Successfully fetched")
                if (subjectCode === "") {
                    const { subjectCode } = getCurrentUser();
                    setSubjectCode(subjectCode)
                    fetchListSubject(subjectCode);
                } else {
                    fetchListSubject(subjectCode);
                }
            })
            .catch((e) => {
                //console.log(e);
            });
    };

    return (
        <div className="rounded-lg shadow-md w-full bg-white p-8">
            <Card
                title={
                    <span>
                        <FontAwesomeIcon icon={faFilter} style={{ marginRight: "8px" }} />
                        Bộ lọc
                    </span>
                }
            >
                <Form
                    form={form}
                    onFinish={handleFilterSubject}
                    style={{
                        paddingRight: "20px"
                    }}
                >
                    <Row style={{ justifyContent: "end" }}>
                        <Col span={11} style={{ marginRight: "50px" }}>
                            <Form.Item
                                label="Tên Chuyên Ngành"
                                name="name"
                            >
                                <Input placeholder="Nhập tên chuyên ngành!" />
                            </Form.Item>
                        </Col>
                        <Col span={11}>
                            <Form.Item
                                label="Mã Chuyên Ngành"
                                name="code"
                            >
                                <Input placeholder="Nhập mã chuyên ngành!" />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{
                        justifyContent: "center",
                        alignItems: "center"
                    }}>
                        <Button
                            type="primary"
                            style={{ margin: 5 }}
                            htmlType="submit"
                            // onClick={() => handleSearch()}
                            icon={<SearchOutlined fontSize="20px" />}
                        >
                            Tìm kiếm
                        </Button>
                        <Button
                            type="primary"
                            danger
                            onClick={() => handleClearFilter()}
                            htmlType="button"
                            icon={<UndoOutlined />}
                        >
                            Làm mới bộ lọc
                        </Button>
                    </Row>
                </Form>

            </Card>
            <Card
                title={
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
                        <span>
                            <FontAwesomeIcon icon={faLayerGroup} style={{
                                marginRight: "10px"
                            }} />
                            Danh sách chuyên ngành
                        </span>
                        <Button key="add" type="primary" htmlType="submit" onClick={() => handleSynchronizedSemesterIdentity()}>
                            <FontAwesomeIcon icon={faSpinner} /> &nbsp; &nbsp;Đồng bộ chuyên ngành
                        </Button>
                    </div>
                }
                bordered={false}
                style={{
                    marginTop: "20px"
                }}
            >
                <Table

                    columns={columnsSubject}
                    dataSource={listSubject}
                    rowKey="index"
                    pagination={{
                        current: currentPage,
                        pageSize: pageSize,
                        total: totalElement, //total Element
                        onChange: (currentPage) => {
                            setCurrentPage(currentPage);
                        },
                    }}
                />
            </Card>
        </div >
    )
}