import React, {useEffect, useState} from 'react';
import {Button, Card, Col, DatePicker, Form, Input, Pagination, Row, Table} from 'antd';
import {SearchOutlined, UndoOutlined} from '@ant-design/icons';
import {ADRegistrationListApi} from './ADRegistrationListApi';
import {useParams} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter} from "@fortawesome/free-solid-svg-icons";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import {ORGANIZER_REGISTRATION_LIST} from "../../../constants/DisplayName";
import moment from "moment";
import ADRegistrationExportExcel from "./ADRegistrationExportExcel";

export default function APRegistrationList() {
    const [listRegistration, setListRegistration] = useState([]);
    const [registration, setRegistration] = useState({});
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    const [totalPages, setTotalPages] = useState(0);
    let {RangePicker} = DatePicker;
    let [startTime, setStartTime] = useState(null);
    let [endTime, setEndTime] = useState(null);
    let [participantCode, setParticipantCode] = useState(null);
    let [participantName, setParticipantName] = useState(null);

    const {id} = useParams();
    const [form] = Form.useForm();

    useEffect(() => {
        handleResetForm();
        //  //console.log(pageSize);
    }, []);

    const handleResetForm = () => {
        form.resetFields();
        setRegistration({
            email: null,
            timeRegistration: null,
        });
        setStartTime(null);
        setEndTime(null);
        setParticipantName(null);
        setParticipantCode(null);
        ADRegistrationListApi.fetchAll({
            page: currentPage - 1,
            size: pageSize,
            idEvent: id
        })
            .then((response) => {
                // //console.log(response.data.data.data);
                setListRegistration(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                setCurrentPage(response.data.data.currentPage + 1);
            })
            .catch((error) => {
            });
    };

    const handleSearch = () => {
        let startDate = new Date(startTime).getTime();
        let endDate = new Date(endTime).getTime();
        const obj = {
            page: 0,
            size: pageSize,
            idEvent: id,
            email: registration.email ? registration.email.toString().trim() : null,
            participantCode: participantCode ? participantCode.toString().trim() : null,
            participantName: participantName ? participantName.toString().trim() : null,
            startTimeSearch: startDate === 0 ? null : startDate,
            endTimeSearch: endDate === 0 ? null : endDate
        }
        // //console.log(obj)
        ADRegistrationListApi.fetchAll(obj)
            .then((response) => {
                // //console.log(response.data.data.data);
                setListRegistration(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                setCurrentPage(response.data.data.currentPage + 1);
            })
            .catch((error) => {
            });
    };

    const renderDateTime = (createDate) => {
        const momentObject = moment(createDate);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const columnsRegistrationList = [
        {
            title: "STT",
            dataIndex: "indexs",
            key: "indexs",
        },
        {
            title: "Email",
            dataIndex: "email",
            key: "email",
        },
        {
            title: "Mã",
            key: "participantCode",
            dataIndex: "participantCode",
        },
        {
            title: "Tên",
            dataIndex: "participantName",
            key: "participantName",
        },
        {
            title: "Chúc vụ",
            key: "role",
            dataIndex: "role",
        },
        {
            title: "Thời gian đăng ký",
            key: "createDate",
            dataIndex: "createDate",
            render: renderDateTime,
        }
    ];

    const handleChangePagination = (currentPage, pageSize) => {
        let startDate = new Date(startTime).getTime();
        let endDate = new Date(endTime).getTime();
        ADRegistrationListApi.fetchAll({
            page: currentPage - 1,
            size: pageSize,
            idEvent: id,
            email: registration.email ? registration.email.toString().trim() : null,
            participantCode: participantCode ? participantCode.toString().trim() : null,
            participantName: participantName ? participantName.toString().trim() : null,
            startTimeSearch: startDate === 0 ? null : startDate,
            endTimeSearch: endDate === 0 ? null : endDate
        })
            .then((response) => {
                // //console.log(response.data.data.data);
                setListRegistration(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                setCurrentPage(response.data.data.currentPage + 1);
            })
            .catch((error) => {
            });
    };
    // const [params, setParams] = useState({});
    // const [listParticipant, setListParticipant] = useState([]);
    // const [totalPages, setTotalPages] = useState(0);
    // const [currentPage, setCurrentPage] = useState(1);// Trang hiện tại
    // const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    // const [listExport, setListExport] = useState([]);
    // const [listGetAll, setListGetAll] = useState([]);
    // const { id } = useParams();
    // // const id = '8c7e8006-366a-4f4e-a5bc-ea62c06ac4e5';
    // useEffect(() => {
    //     ADRegistrationListApi.fetchAll(id, {}).then(response => {
    //         setListParticipant(response.data.data);
    //         setTotalPages(response.data.totalPages);
    //     }).catch(e => {
    //         //console.log(e);
    //     })
    //     ADRegistrationListApi.getListExport(id, {}).then(response => {
    //         setListGetAll(response.data.data);
    //     }).catch(e => {
    //         //console.log(e);
    //     })
    // }, [])
    // useEffect(() => {
    //     ADRegistrationListApi.fetchAll(id, params).then(response => {
    //         setListParticipant(response.data.data);
    //         setTotalPages(response.data.totalPages);
    //     }).catch(e => {
    //         //console.log(e);
    //     })
    //     ADRegistrationListApi.getListExport(id, params).then(response => {
    //         setListExport(response.data.data);
    //     }).catch(e => {
    //         //console.log(e);
    //     })
    // }, [params])
    //
    // const columns = [
    //     {
    //         title: '#',
    //         dataIndex: 'index',
    //         key: 'index',
    //     },
    //     {
    //         title: 'Email',
    //         dataIndex: 'email',
    //         key: 'email',
    //     },
    //     {
    //         title: 'Lớp',
    //         dataIndex: 'className',
    //         key: 'className',
    //     },
    //     {
    //         title: 'Câu hỏi',
    //         dataIndex: 'question',
    //         key: 'question',
    //         render: (question, record) => (
    //             <span>
    //                 {question === null ? (<p style={{ textAlign: "center" }}>-</p>) : question}
    //             </span>
    //         ),
    //     },
    //     {
    //         title: 'Giảng viên',
    //         dataIndex: 'lecturerName',
    //         key: 'lecturerName'
    //     },
    //     {
    //         title: 'Ngày đăng ký',
    //         dataIndex: 'createDate',
    //         key: 'createDate',
    //         render: (x) => dateTimeFromLong(x)
    //     },
    // ];
    //
    // const handleExportExcel = () => {
    //     // //console.log(listExport);
    //     const excelData = listExport.map(item => ({
    //         'STT': item.index,
    //         'Lớp': item.className,
    //         'Email': item.email,
    //         'Giảng viên': item.lecturerName,
    //         'Câu hỏi': item.question || '',
    //     }));
    //     const ws = XLSX.utils.json_to_sheet(excelData);
    //     ws['!auto'] = 1;
    //     const wb = XLSX.utils.book_new();
    //     XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    //     XLSX.writeFile(wb, 'data.xlsx');
    // }

    return (
        <>
            <div className="rounded-lg shadow-md w-full bg-white p-8">
                <div style={{marginTop: 5}}>
                    <Card
                        title={
                            <span>
                                <FontAwesomeIcon
                                    icon={faFilter}
                                    style={{marginRight: "8px"}}
                                />
                                Bộ lọc
                            </span>
                        }
                        bordered={false}
                    >
                        <Form
                            labelCol={{span: 7}}
                            wrapperCol={{span: 15}}
                            form={form}
                        >
                            <Row gutter={16}>
                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Email"
                                        name="email"
                                    >
                                        <Input
                                            placeholder="Nhập email ..."
                                            value={registration.email}
                                            onChange={(e) => {
                                                setRegistration({
                                                    ...registration,
                                                    email: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        className="formItem"
                                        label="Mã SV/GV"
                                        name="participantCode"
                                    >
                                        <Input
                                            placeholder="Nhập mã sinh viên/giảng viên ..."
                                            value={registration.participantCode}
                                            onChange={(e) => {
                                                setParticipantCode(e.target.value)
                                            }}
                                        />
                                    </Form.Item>
                                </Col>

                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Tên người đăng ký"
                                        name="participantName"
                                    >
                                        <Input
                                            placeholder="Nhập tên người đăng ký tham gia ..."
                                            value={registration.participantName}
                                            onChange={(e) => {
                                                setParticipantName(e.target.value)
                                            }}
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        className="formItem"
                                        label="Thời gian đăng ký"
                                        name="timeRegistration"
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
                                style={{margin: 0}}
                            >
                                <Button
                                    type="primary"
                                    style={{margin: 5}}
                                    htmlType="submit"
                                    onClick={() => handleSearch()}
                                    icon={<SearchOutlined fontSize="20px"/>}
                                >
                                    Tìm kiếm
                                </Button>
                                <Button
                                    type="primary"
                                    danger
                                    onClick={() => handleResetForm()}
                                    htmlType="button"
                                    icon={<UndoOutlined/>}
                                >
                                    Làm mới bộ lọc
                                </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </div>

                <div style={{marginTop: 20}}>
                    <Card
                        title="Danh sách đăng ký"
                        extra={<>
                            <ADRegistrationExportExcel eventId={id}/>
                            <ShowHistoryModal displayName={ORGANIZER_REGISTRATION_LIST} eventId={id}/>
                        </>}
                        bordered={false}
                    >
                        <Table
                            columns={columnsRegistrationList}
                            dataSource={listRegistration}
                            rowKey="index"
                            pagination={false}
                            scroll={{
                                x: 1300,
                            }}
                        />
                        <Row style={{display: 'flex', justifyContent: 'end', marginTop: '20px'}}>
                            {totalPages > 0 ? (
                                <Pagination
                                    pageSizeOptions={[10, 20, 50, 100]}
                                    defaultPageSize={10}
                                    current={currentPage}
                                    total={totalPages * pageSize}
                                    showSizeChanger
                                    onChange={(pageNumber, pageSize) => {
                                        setCurrentPage(pageNumber)
                                        setPageSize(pageSize)
                                        handleChangePagination(pageNumber, pageSize);
                                    }}
                                />
                            ) : null}
                        </Row>
                    </Card>
                </div>
            </div>
        </>
    )
}