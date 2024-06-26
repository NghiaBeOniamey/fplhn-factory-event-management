import { FilterOutlined, SyncOutlined } from "@ant-design/icons";
import {
    Button,
    Col,
    Form,
    Input,
    Row,
    Select,
    Table, Tag,
    message
} from "antd";
import Title from "antd/es/typography/Title";
import React, { useCallback, useEffect, useLayoutEffect, useState } from "react";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import { ADMIN_MAJOR_MANAGEMENT } from "../../../constants/DisplayName";
import { ADMajorManagermentApi } from "./ADMajorManagermentApi";
import Loading from './../../../utils/Loading/Loading';

export default function APMajorManagerment() {
    const [listMajor, setListMajor] = useState([]); //DS bộ môn

    const [filter, setFilter] = useState({}); // params bộ lọc

    const [currentPage, setCurrentPage] = useState(1); // trang hiện tại

    // const [campusList, setCampusList] = useState([]);

    const [loading, setLoading] = useState(true);

    // const loadCampus = () => {
    //     ADMajorManagermentApi.fetchAllCampus()
    //         .then((response) => {
    //             setCampusList(response.data);
    //             setFilter((prev) => {
    //                 return {
    //                     ...prev,
    //                     campusCode: response.data[0].code
    //                 }
    //             })
    //             setLoading(false);
    //         })
    //         .catch((e) => {
    //             //console.log(e);
    //         });
    // };

    const loadDepartment = (data) => {
        ADMajorManagermentApi.fetchAll(data)
            .then((response) => {
                setListMajor(response.data);
                setLoading(false);
            })
            .catch((e) => {
                ////  //console.log(e);
            });
    };

    const handleFilter = useCallback((data) => {
        setFilter(data);
    }, []);

    const handleMajorFetch = () => {
        ADMajorManagermentApi.synchronizedIdentity()
            .then(() => {
                message.info("Successfully fetched")
                loadDepartment(filter);
            })
            .catch((e) => {
                //console.log(e);
            });
    }

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
            render: (mainMajorCode, record) => (
                <Tag color={record.mainMajorCode && "magenta" || "cyan"}>
                    {
                        (
                            record.mainMajorCode &&
                            `${record.mainMajorCode} - ${mainMajorCode}`
                        ) || mainMajorCode
                    }
                </Tag>
            ),
        },
        {
            title: "Tên bộ môn",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Email chủ nhiệm bộ môn / trưởng môn",
            dataIndex: "mailOfManager",
            key: "mail"
        }
    ];

    useEffect(() => {
        loadDepartment(filter);
    }, [filter]);

    return (
        <>
            {loading ? <Loading /> :
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
                                <Col flex="1 1">
                                    <Form.Item name={"value"}>
                                        <Input placeholder="Tìm kiếm bộ môn theo mã, tên, email trưởng bộ môn..." />
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
                        <ShowHistoryModal displayName={ADMIN_MAJOR_MANAGEMENT} />
                        <Button type='primary' style={{ backgroundColor: "green", marginRight: 5 }}
                            onClick={handleMajorFetch}><SyncOutlined /> Đồng bộ dữ liệu</Button>
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
                                    loadDepartment(filter);
                                },
                            }}
                            scroll={{
                                x: 1300,
                            }}
                        />
                    </div>
                </>
            }
        </>
    );
}
