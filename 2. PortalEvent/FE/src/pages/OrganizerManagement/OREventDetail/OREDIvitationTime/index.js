import { faList, faPlus, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, DatePicker, Input, Popconfirm, Table, message, Tooltip } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import OREventDetailApi from "../OREventDetailApi";
import moment from "moment/moment";
import dayjs from "dayjs";
import { range } from "react-big-calendar/lib/utils/dates";

const OREDInvitationTime = ({ startTime, eventStatus }) => {
    const [listInvitationTime, setListInvitationTime] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        loadDataInvitationTime();
    }, [id]);

    const deleteInvitationTime = (id, index) => {
        if (id === '') {
            let updateListAgenda = listInvitationTime.filter(
                (record) => record.index !== index
            );
            setListInvitationTime(updateListAgenda);
            message.success("Xóa thành công");
        } else {
            OREventDetailApi.deleteInvitationTime(id).then(
                (res) => {
                    let updateListInvitationTime = listInvitationTime.filter(
                        (record) => record.id !== res.data.data
                    );
                    setListInvitationTime(updateListInvitationTime);
                    message.success("Xóa thành công");
                }
            ).catch((err) => {
                message.warning(err.response.data.message);
            });
        }
    };

    const handleInputChange = (e, key, dataIndex) => {
        const updatedData = [...listInvitationTime];
        const record = updatedData.find((item) => item.index === key);
        if (record) {
            record[dataIndex] = e.target.value;
            setListInvitationTime(updatedData);
        }
    };

    const handleDateChange = (dates, key, dataIndex) => {
        const updatedData = listInvitationTime.map((item) => ({ ...item }));
        const record = updatedData.find((item) => item.index === key);
        if (dates.valueOf() > startTime) {
            message.error('Chỉ có thể đặt lịch gửi mail trước thời gian diễn ra sự kiện');
            return;
        }
        if (dates.valueOf() < moment().valueOf()) {
            message.error('Chỉ có thể đặt lịch gửi mail trong tương lai');
            return;
        }
        if (record) {
            record[dataIndex] = dates ? dates.valueOf() : null;
            setListInvitationTime(updatedData);
        }
    };

    const disabledDate = (current) => {
        const startDate = moment().subtract(1, 'day');
        const endDate = moment(new Date(startTime));
        // Chỉ cho phép chọn từ ngày hiện tại đến ngày diễn ra sự kiện
        return current && (current < startDate.endOf('day') || current > endDate.endOf('day'));
    }

    const columnsInvitationTime = eventStatus === 4 ? [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
            render: (text, record, index) => <span>{index + 1}</span>,
            // width: "5%",
        },
        {
            title: "Thời gian",
            dataIndex: "time",
            key: "time",
            editable: true,
            render: (text, record, index) => (
                <DatePicker
                    style={{ width: "100%" }}
                    showTime
                    disabled={record.status}
                    disabledDate={disabledDate}
                    inputReadOnly={true}
                    format="HH:mm DD-MM-YYYY"
                    value={text ? dayjs(text) : null}
                    onChange={(dates) => handleDateChange(dates, record.index, "time")}
                />
            ),
        },
        {
            title: "Trạng thái",
            dataIndex: "status",
            key: "status",
            editable: false,
            render: (text, record, index) => (
                record.status ? <b style={{ color: "green" }}>Đã gửi</b> : <b style={{ color: "#d7d304" }}>Chưa gửi</b>
            ),
        },
        {
            title: "Hành động",
            dataIndex: "actions",
            key: "actions",
            render: (text, record, index) => (
                <div>
                    <Popconfirm
                        disabled={eventStatus !== 4}
                        title="Bạn chắc chắn muốn xóa bản ghi này không?"
                        onConfirm={() => {
                            deleteInvitationTime(record.id, record.index);
                        }}
                        okText="Có"
                        cancelText="Không"
                    >
                        <FontAwesomeIcon style={{ marginRight: "15px", marginLeft: 15 }} icon={faTrash} className="custom-icon" />
                    </Popconfirm>

                    {index + 1 === listInvitationTime.length && listInvitationTime.length < 5 && eventStatus === 4 &&
                        <FontAwesomeIcon
                            icon={faPlus}
                            className="custom-icon"
                            style={{ marginRight: "15px" }}
                            onClick={() => {
                                handleAddRow();
                            }}
                        />
                    }
                </div>
            ),
            // width: "15%",
        }
    ] : [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
            render: (text, record, index) => <span>{index + 1}</span>,
            // width: "5%",
        },
        {
            title: "Thời gian",
            dataIndex: "time",
            key: "time",
            editable: true,
            render: (text, record, index) => (
                <DatePicker
                    style={{ width: "100%" }}
                    showTime
                    disabled={record.status}
                    disabledDate={disabledDate}
                    inputReadOnly={true}
                    format="HH:mm DD-MM-YYYY"
                    value={text ? dayjs(text) : null}
                    onChange={(dates) => handleDateChange(dates, record.index, "time")}
                />
            ),
        },
        {
            title: "Trạng thái",
            dataIndex: "status",
            key: "status",
            editable: false,
            render: (text, record, index) => (
                record.status ? <b style={{ color: "green" }}>Đã gửi</b> : <b style={{ color: "#d7d304" }}>Chưa gửi</b>
            ),
        },
    ];

    // Lấy danh sách invitationTime
    const loadDataInvitationTime = () => {
        OREventDetailApi.getListInvitationTime(id).then(
            (res) => {
                setListInvitationTime(res.data.data);
            }
        ).catch((err) => {
            message.error('Lỗi hệ thống. Không thể lấy danh sách invitation time');
        });
    };

    // Thêm dòng trong bảng
    const handleAddRow = () => {
        if (listInvitationTime.length > 4) {
            return;
        }
        let randomNumber = '';
        let check = true;
        while (check) {
            randomNumber = (Math.floor(Math.random() * 1000) + 1) + '';
            // eslint-disable-next-line no-loop-func
            let obj = listInvitationTime.find((item) => {
                return item.index === randomNumber;
            });
            if (typeof obj === 'undefined') {
                check = false;
            }
        }
        const newRow = {
            index: randomNumber,
            id: '',
            eventId: id,
            key: listInvitationTime.length,
            time: null,
        };
        setListInvitationTime([...listInvitationTime, newRow]);
    };

    const saveListInvitationTime = () => {
        let hasEmptyFields = false;
        const listInvitationNew = listInvitationTime.map((item, index) => {
            const newItem = {
                id: item.id,
                eventId: item.eventId,
                time: item.time,
            };

            if (newItem.time == null || newItem.time === "") {
                message.error(`Vui lòng nhập thời gian ở dòng ${index + 1}.`);
                hasEmptyFields = true;
            }
            return newItem;
        });
        if (hasEmptyFields) {
            return;
        }
        OREventDetailApi.saveListInvitationTime(listInvitationNew).then(
            (res) => {
                message.success("Lưu thông tin thành công");
                loadDataInvitationTime();
            }
        ).catch((err) => {
            message.warning(err.response.data.message);
        });
    }

    return (
        <>
            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                    marginTop: "20px",
                }}
            >
                <h4 className="title-comment">
                    <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
                    <span style={{ marginLeft: "7px" }}>Lịch gửi mail thư mời sự kiện</span>
                </h4>
                {listInvitationTime.length !== 0 && eventStatus === 4 &&
                    <Tooltip title="Lưu lịch gửi mail thư mời sự kiện">
                        <Button
                            onClick={() => saveListInvitationTime()}
                            style={{
                                color: "white",
                                backgroundColor: "rgb(38, 144, 214)",
                                // display: isDisable ? "none" : "",
                            }}

                        >
                            Lưu lịch
                        </Button>
                    </Tooltip>
                }
            </div>
            <br />
            <Table
                dataSource={listInvitationTime}
                columns={columnsInvitationTime}
                rowKey="id"
                pagination={false}
                locale={{
                    emptyText: (
                        eventStatus === 4 && (
                            <div>
                                <Button type="primary"
                                    onClick={() => {
                                        handleAddRow();
                                    }}
                                >
                                    <FontAwesomeIcon
                                        icon={faPlus}
                                    />
                                </Button>
                            </div>
                        )
                    ),
                }}
                scroll={{
                    x: 1300,
                }}
            />
        </>
    );
};

export default OREDInvitationTime;