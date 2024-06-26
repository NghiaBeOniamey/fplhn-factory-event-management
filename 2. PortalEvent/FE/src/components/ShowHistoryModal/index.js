import React, { useEffect, useState } from "react";
import {
    Modal,
    Button,
    Select,
    Table,
    Pagination,
} from "antd";
import { HistoryAPI } from "./HistoryAPI";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClock, faFileArrowDown } from "@fortawesome/free-solid-svg-icons";

const { Option } = Select;

const ShowHistoryModal = ({ displayName, eventId }) => {
    const [visibleHistory, setVisibleHistory] = useState(false);


    const columns = [
        {
            title: "Email người thực hiện",
            dataIndex: "mail",
            key: "mail",
            sorter: (a, b) => a.mail.localeCompare(b.mail),
        },
        {
            title: "Thời gian thực hiện",
            dataIndex: "createDate",
            key: "createDate",
            sorter: (a, b) => a.createDate.localeCompare(b.createDate),
        },
        {
            title: "Vai trò",
            dataIndex: "author",
            key: "author",
            sorter: (a, b) => a.author.localeCompare(b.author),
        },
        {
            title: "Nội dung",
            dataIndex: "content",
            key: "content",
        },
    ];

    const [dataHistory, setDataHistory] = useState([]);
    const [current, setCurrent] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [size, setSize] = useState("50");

    const loadDataHistory = () => {
        HistoryAPI.showHistory({
            page: current - 1,
            size: parseInt(size),
            displayName: displayName,
            eventId: eventId
        }).then((response) => {
            setDataHistory(response.data.data);
            setTotalPages(response.data.totalPages);
        }).catch((err) => {
            ////  //console.log(err);
        });
    };

    useEffect(() => {
        if (visibleHistory) {
            loadDataHistory();
        }
    }, [current, size, visibleHistory]);

    const dowloadLog = () => {
        const fileName = displayName + '.csv';
        HistoryAPI.dowloadLog(displayName, eventId).then(
            (response) => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const a = document.createElement("a");
                a.href = url;
                a.download = fileName;
                a.click();
                window.URL.revokeObjectURL(url);
            },
            (error) => {
                ////  //console.log(error);
            }
        );
    };

    const onCancel = () => {
        setVisibleHistory(false);
    }

    return (
        <>
            {/* <Button type="primary" style={{ marginLeft: 6 }} onClick={() => setVisibleHistory(true)}>
                <FontAwesomeIcon icon={faClock} />&nbsp; Lịch sử
            </Button>
            <Button type="primary" style={{ marginLeft: 6 }} onClick={dowloadLog}>
                <FontAwesomeIcon icon={faFileArrowDown} />&nbsp;Tải file log
            </Button> */}
            <Modal visible={visibleHistory} onCancel={onCancel} width={1300} footer={null}>
                <div>
                    <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
                        <span style={{ fontSize: "18px" }}>Lịch sử</span>
                    </div>
                    <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
                        <Table
                            dataSource={dataHistory}
                            rowKey="id"
                            columns={columns}
                            pagination={false}
                            scroll={{
                                x: 1300,
                            }}
                        />
                        <div
                            className="pagination_box"
                            style={{
                                display: "flex",
                                marginTop: 20,
                                marginBottom: 20,
                                alignItems: "center",
                                justifyContent: "center",
                            }}
                        >
                            <Pagination
                                style={{ marginRight: "10px" }}
                                simple
                                current={current}
                                onChange={(value) => {
                                    setCurrent(value);
                                }}
                                total={totalPages * 10}
                            />
                            <Select
                                style={{ width: "100px", marginLeft: "10px" }}
                                value={size}
                                onChange={(e) => {
                                    setSize(e);
                                }}
                                // showSearch
                                // optionFilterProp="children"
                                // filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                            >
                                <Option value="50">50</Option>
                                <Option value="100">100</Option>
                                <Option value="250">250</Option>
                                <Option value="500">500</Option>
                                <Option value="1000">1000</Option>
                                <Option value="1000">10000</Option>
                            </Select>
                        </div>
                    </div>

                    <div style={{ textAlign: "right" }}>
                        <div style={{ paddingTop: "15px" }}>
                            <Button
                                style={{
                                    backgroundColor: "red",
                                    color: "white",
                                }}
                                onClick={onCancel}
                            >
                                Hủy
                            </Button>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    );
};
export default ShowHistoryModal;
