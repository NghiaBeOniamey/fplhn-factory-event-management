import { FileExcelOutlined, SearchOutlined, UndoOutlined } from "@ant-design/icons";
import {
    Col,
    Form,
    Input,
    Row,
    Select,
    DatePicker,
    Button,
    Card,
    Space,
    Table,
    message, Tooltip, Modal,
} from "antd";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { APEventWaitingApprovalApi } from "./APRegistrationListApi";
import moment from "moment/moment";
// import ExportCSV from './ExportExcel';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faFilter } from "@fortawesome/free-solid-svg-icons";
import "./index.css";
import APStatisticsEventApi from "../APStatisticsEvent/APStatisticsEventApi";
import dayjs from "dayjs";

const { Option } = Select;

export default function EventWaitingApproval() {
    let [listCategory, setListCategory] = useState([]);
    let [listEventApproved, setListEventApproved] = useState([]);
    let [name, setName] = useState("");
    let [formality, setFormality] = useState("");
    let [status, setStatus] = useState([]);
    let [startTime, setStartTime] = useState();
    let [endTime, setEndTime] = useState();
    let [idCategory, setIdCategory] = useState([]);
    let [idEventMajor, setIdEventMajor] = useState([]);
    let [currentPage, setCurrentPage] = useState(1);
    let [totalPages, setToTalPages] = useState(0);
    let [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    let [dataSource, setDataSource] = useState([]);
    let { RangePicker } = DatePicker;

    // const [selectedSemesterParticipants, setSelectedSemesterParticipants] = useState(null);
    // const [selectedSemesterParticipantsLecturer, setSelectedSemesterParticipantsLecturer] = useState(null);
    const [selectedSemesterEvent, setSelectedSemesterEvent] = useState(null);

    const [isModalOpenParticipants, setIsModalOpenParticipants] = useState(false);
    const [isModalOpenParticipantsLecturer, setIsModalOpenParticipantsLecturer] = useState(false);
    const [isModalOpenEvent, setIsModalOpenEvent] = useState(false);

    const [idSemester, setIdSemester] = useState("");
    const [listSemester, setListSemester] = useState([]);

    useEffect(() => {
        loadCategory();
        getAllSemester();
    }, []);

    useEffect(() => {
        searchOrBtn();
    }, [currentPage]);

    const getAllSemester = () => {
        APStatisticsEventApi.getAllSemester().then(
            (res) => {
                setListSemester(res.data.data);
                // Lấy ngày hiện tại cho lần chạy đầu tiên
            }
        );
    }

    const loadCategory = () => {
        APEventWaitingApprovalApi.fetchListCategory()
            .then((response) => {
                setListCategory(response.data.data);
            })
            .catch((error) => {
            });
    };

    const handleOkEvent = () => {
        APEventWaitingApprovalApi.handleExportEvent(idSemester);
        setIsModalOpenEvent(false);
        handResetForm();
    };

    const handleCancelEvent = () => {
        setIsModalOpenEvent(false);
        handResetForm();
    };

    const handResetForm = () => {
        setIdSemester("");
    }

    const showModalEvent = () => {
        setIsModalOpenEvent(true);
    };

    const searchOrBtn = () => {
        let isSearched = false;

        if (name === "" || idCategory || idEventMajor || startTime || endTime) {
            isSearched = true;
        }

        if (isSearched) {
            handleSearch();
        } else {
            loadListEvent();
        }
    }

    const loadListEvent = () => {
        APEventWaitingApprovalApi.fetchListEventWaiting({
            page: currentPage - 1,
            size: 5,
            name: null,
            eventGroup: null,
            categoryId: null,
            startTime: null,
            endTime: null,
            formality: null,
            status: "0,2,3,4,5,6"
        })
            .then((response) => {
                setListEventApproved(response.data.data);
                setCurrentPage(response.data.currentPage + 1);
                setToTalPages(response.data.totalPages);
            })
            .catch((error) => {
                //  //console.log(error.message);
            });
    };


    const handleSearch = () => {
        let startDate = new Date(startTime).getTime();
        let endDate = new Date(endTime).getTime();
        APEventWaitingApprovalApi.fetchListEventWaiting({
            page: currentPage - 1,
            size: 5,
            name: name,
            majorId: Array.isArray(idEventMajor)
                ? idEventMajor.length === 0
                    ? null
                    : idEventMajor.join(",")
                : null,
            categoryId: Array.isArray(idCategory)
                ? idCategory.length === 0
                    ? null
                    : idCategory.join(",")
                : null,
            startTime: startDate === 0 ? null : startDate,
            endTime: endDate === 0 ? null : endDate,
            formality: formality === "null" ? null : formality,
            status: Array.isArray(status)
                ? status.length === 0
                    ? "0,2,3,4,5,6" : status.join(",")
                : null,
        })
            .then((response) => {
                setListEventApproved(response.data.data);
                setCurrentPage(response.data.currentPage + 1);
                setToTalPages(response.data.totalPages);
            })
            .catch((error) => {
            });
    };

    const [form] = Form.useForm();
    const handleResetForm = () => {
        form.resetFields();
        setName(null);
        setFormality(null);
        setStatus([]);
        setIdCategory(null);
        setStartTime(null);
        setEndTime(null);
        loadListEvent();
        // handleSearch()
    };

    const renderStatus = (status) => {
        return status === "0"
            ? "Đã đóng"
            : status === "1"
                ? "Dự kiến tổ chức"
                : status === "2"
                    ? "Cần sửa"
                    : status === "3"
                        ? "Chờ phê duyệt"
                        : status === "4"
                            ? "Đã phê duyệt"
                            : status === "5"
                                ? "Đã tổ chức"
                                : status === "6"
                                    ? "Đã được bộ môn thông qua" : "";
    };

    const renderDateTime = (startTime) => {
        const momentObject = moment(startTime);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const renderAction = (record) => (
        <div class="textCenter">
            <Tooltip title="Xem chi tiết" placement="top">
                <Link to={`/approver-management/event-detail/${record.id}`}>
                    <Button size={"middle"} shape={"circle"} type="primary"><FontAwesomeIcon icon={faEye} /></Button>
                </Link>
            </Tooltip>
        </div>
    );

    const columnsEvent = [
        {
            title: "#",
            dataIndex: "index",
            key: "index",
        },
        {
            title: "Tên sự kiện",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Ngày bắt đầu",
            dataIndex: "startTime",
            key: "startTime",
            render: renderDateTime,
        },
        {
            title: "Ngày kết thúc",
            key: "endTime",
            dataIndex: "endTime",
            render: renderDateTime,
        },
        {
            title: "Danh mục",
            key: "nameCategory",
            dataIndex: "nameCategory",
        },
        {
            title: "Trạng thái",
            key: "status",
            dataIndex: "status",
            render: renderStatus,
        },
        {
            title: "Thao tác",
            key: "action",
            width: 90,
            render: renderAction,
        },
    ];

    return (
        <>
            <div className="rounded-lg shadow-md w-full bg-white p-8">
                <div style={{ marginTop: 5 }}>
                    <Card
                        title={
                            <span>
                                <FontAwesomeIcon icon={faFilter} style={{ marginRight: "8px" }} />
                                Bộ lọc
                            </span>
                        }
                        bordered={false}
                    >
                        <Form labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} form={form}>
                            <Row gutter={16}>
                                <Col span={12}>
                                    <Form.Item className="formItem" label="Tên sự kiện" name="name">
                                        <Input
                                            placeholder="Nhập tên sự kiện ..."
                                            value={name}
                                            onChange={(e) => {
                                                setName(e.target.value);
                                            }}
                                        />
                                    </Form.Item>

                                    <Form.Item
                                        className="formItem"
                                        label="Danh mục sự kiện"
                                        name="categoryName"
                                    >
                                        <Select
                                            mode="multiple"
                                            value={idCategory}
                                            onChange={(e) => {
                                                setIdCategory(e);
                                            }}
                                            placeholder="Chọn danh mục sự kiện ..."
                                            showSearch
                                            filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                            options={listCategory?.map((item) => ({
                                                value: item.id,
                                                label: item.name
                                            }))}
                                        />
                                        {/* {listCategory.map((category, index) => (
                                                <Option key={index} value={category.id}>
                                                    {category.name}
                                                </Option>
                                            ))}
                                        </Select> */}
                                    </Form.Item>
                                </Col>

                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Trạng thái phê duyệt"
                                        name="approvalStatus"
                                    >
                                        <Select
                                            mode="multiple"
                                            value={status}
                                            onChange={(e) => {
                                                setStatus(e);
                                            }}
                                            placeholder="Chọn trạng thái ..."
                                        >
                                            <Option key="0" value="0">
                                                Đã đóng
                                            </Option>
                                            <Option key="2" value="2">
                                                Cần sửa
                                            </Option>
                                            <Option key="3" value="3">
                                                Chờ phê duyệt
                                            </Option>
                                            <Option key="6" value="6">
                                                Đã được bộ môn thông qua
                                            </Option>
                                            <Option key="4" value="4">
                                                Đã phê duyệt
                                            </Option>
                                            <Option key="5" value="5">
                                                Đã tổ chức
                                            </Option>
                                        </Select>
                                    </Form.Item>

                                    <Form.Item
                                        label="Thời gian sự kiện"
                                        rules={[{ required: true }]}
                                    >
                                        <RangePicker
                                            showTime={{
                                                format: "HH:mm",
                                            }}
                                            style={{
                                                width: "100%"
                                            }}
                                            format="YYYY-MM-DD HH:mm"
                                            value={[startTime, endTime]}
                                            onChange={(e) => {
                                                if (e && e.length === 2) {
                                                    setStartTime(e[0]);
                                                    setEndTime(e[1]);
                                                } else {
                                                    setStartTime(null);
                                                    setEndTime(null);
                                                }
                                            }}
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Form.Item
                                wrapperCol={{
                                    offset: 9,
                                    span: 15,
                                }}
                                style={{ margin: 0 }}
                            >
                                <Button
                                    type="primary"
                                    style={{ margin: 5 }}
                                    htmlType="submit"
                                    onClick={() => handleSearch()}
                                    icon={<SearchOutlined fontSize="20px" />}
                                >
                                    Tìm kiếm
                                </Button>
                                <Button
                                    type="primary"
                                    danger
                                    onClick={() => handleResetForm()}
                                    htmlType="button"
                                    icon={<UndoOutlined />}
                                >
                                    Làm mới bộ lọc
                                </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </div>

                <div style={{ marginTop: 20 }}>
                    <Card
                        title="Danh sách sự kiện"
                        // extra={<ExportCSV csvData={listEventApproved} fileName="sample_data" />}
                        extra={<>
                            {/*<Button type='primary' style={{backgroundColor: "#1E6E43", marginRight: 5}}*/}
                            {/*        onClick={showModalParticipants}><FileExcelOutlined/> Danh sách sinh viên</Button>*/}
                            {/*<Button type='primary' style={{backgroundColor: "#1E6E43", marginRight: 5}}*/}
                            {/*        onClick={showModalParticipantsLecturer}><FileExcelOutlined/> Danh sách giảng*/}
                            {/*    viên</Button>*/}
                            <Button type='primary' style={{ backgroundColor: "#1E6E43" }}
                                onClick={showModalEvent}><FileExcelOutlined /> Danh
                                sách sự kiện</Button>
                        </>}
                        bordered={false}
                    >
                        <Table
                            columns={columnsEvent}
                            dataSource={listEventApproved}
                            rowKey="index"
                            pagination={{
                                current: currentPage,
                                pageSize: pageSize,
                                total: pageSize * totalPages,
                                onChange: (current, pageSize) => {
                                    setCurrentPage(current);
                                    setPageSize(pageSize);
                                },
                            }}
                            scroll={{
                                x: 1300,
                            }}
                        />
                    </Card>
                </div>
            </div>
            {/*<Modal*/}
            {/*    title={"Export danh sách sinh viên"}*/}
            {/*    open={isModalOpenParticipants}*/}
            {/*    onOk={handleOkParticipants}*/}
            {/*    onCancel={handleCancelParticipants}*/}
            {/*>*/}
            {/*    <hr/>*/}
            {/*    <br/>*/}
            {/*    /!*<Button onClick={() => ADEventApprovedApi.handleExportEvent()}>Export Excel</Button>*!/*/}
            {/*    <Form.Item name={"semester"} label={"Học kỳ"}>*/}
            {/*        <Select className="me-2" showSearch optionFilterProp="children"*/}
            {/*                style={{width: '100%'}}*/}
            {/*                defaultValue={semesterList[0]?.name}*/}
            {/*                onChange={(value) => {*/}
            {/*                    setSelectedSemesterParticipants(value);*/}
            {/*                }}>*/}
            {/*            {semesterList.map((item) => (*/}
            {/*                <Option key={item.id} value={item.name}>*/}
            {/*                    {item.name}*/}
            {/*                </Option>*/}
            {/*            ))}*/}
            {/*        </Select>*/}
            {/*    </Form.Item>*/}
            {/*</Modal>*/}
            {/*<Modal*/}
            {/*    title={"Export danh sách giảng viên"}*/}
            {/*    open={isModalOpenParticipantsLecturer}*/}
            {/*    onOk={handleOkParticipantsLecturer}*/}
            {/*    onCancel={handleCancelParticipantsLecturer}*/}
            {/*>*/}
            {/*    <hr/>*/}
            {/*    <br/>*/}
            {/*    <Form.Item name={"semester"} label={"Học kỳ"}>*/}
            {/*        <Select className="me-2" showSearch optionFilterProp="children"*/}
            {/*                style={{width: '100%'}}*/}
            {/*                defaultValue={semesterList[0]?.name}*/}
            {/*                onChange={(value) => {*/}
            {/*                    setSelectedSemesterParticipantsLecturer(value);*/}
            {/*                }}>*/}
            {/*            {semesterList.map((item) => (*/}
            {/*                <Option key={item.id} value={item.name}>*/}
            {/*                    {item.name}*/}
            {/*                </Option>*/}
            {/*            ))}*/}
            {/*        </Select>*/}
            {/*    </Form.Item>*/}
            {/*</Modal>*/}
            <Modal
                title={"Export danh sách sự kiện"}
                open={isModalOpenEvent}
                onOk={handleOkEvent}
                onCancel={handleCancelEvent}
            >
                <hr />
                <br />
                <Form.Item label="Học kỳ" direction="vertical">
                    <Select
                        mode="single"
                        style={{ width: "100%" }}
                        options={listSemester.map((semester) => ({
                            value: semester.id,
                            label:
                                semester.name +
                                " (" +
                                dayjs(semester.startTime).format("DD/MM/YYYY") +
                                " - " +
                                dayjs(semester.endTime).format("DD/MM/YYYY") +
                                ")",
                        }))}
                        value={idSemester}
                        onChange={(value) => setIdSemester(value)}
                        placeholder="Select Item..."
                        maxTagCount="responsive"
                        showSearch
                        filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                    />
                </Form.Item>
            </Modal>
        </>
    );
}
