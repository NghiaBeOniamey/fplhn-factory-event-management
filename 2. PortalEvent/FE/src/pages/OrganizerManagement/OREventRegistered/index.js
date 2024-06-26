import { faBan, faEye, faFilter, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button, Col, Empty, Form, Input, Pagination, Row, Select, Space, Switch, Table, Tooltip, message } from 'antd';
import * as FileSaver from 'file-saver';
import { Option } from 'rc-select';
import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import * as XLSX from 'xlsx';
import { OREventRegisteredApi } from './OREventRegisteredApi';
import { AppContext } from './context';

//localhost:  /organizer-management/event-registed
export default function OREventRegistered() {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [listEvents, setListEvents] = useState([]);
    const [category, setCategory] = useState([]);
    const [semester, setSemester] = useState([]);
    const [status, setStatus] = useState();
    const [name, setName] = useState("");
    const [idCategory, setIdCategory] = useState("");
    const [idSemester, setIdSemester] = useState("");
    const [formality, setFormality] = useState(-1);
    const [statusSort, setStatusSort] = useState(1);
    const [isIncreasing, setIsIncreasing] = useState(false);
    const [isMe, setIsMe] = useState(true);
    return (
        <AppContext.Provider
            value={{
                listEvents, setListEvents,
                category, setCategory,
                status, setStatus,
                name, setName,
                idCategory, setIdCategory,
                formality, setFormality,
                statusSort, setStatusSort,
                semester, setSemester,
                idSemester, setIdSemester,
                isIncreasing, setIsIncreasing,
                currentIndex, setCurrentIndex,
                totalPages, setTotalPages,
                isMe, setIsMe
            }}>
            <OREventRegisteredWrapper />
        </AppContext.Provider>
    );
}

const OREventRegisteredWrapper = () => {
    const {
        setCategory,
        setSemester
    } = useContext(AppContext);

    useEffect(() => {
        fetchAllCategories();
        fetchAllSemtester();
    }, []);

    const fetchAllCategories = async () => {
        await OREventRegisteredApi.fetchAllCategories()
            .then((response) => {
                setCategory(response.data.data);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    const fetchAllSemtester = async () => {
        await OREventRegisteredApi.fetchAllSemester()
            .then((response) => {
                setSemester(response.data.data);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    return (
        <div style={{
            backgroundColor: 'white', borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)'
        }}>
            <h3 style={{ margin: '1rem 0 1rem 1rem' }}>Quản lý sự kiện</h3>
            <Wrapper>
                <span style={{ marginRight: '0.4rem', marginLeft: '1rem' }}>
                    <FontAwesomeIcon icon={faFilter} />
                </span>
                <span>Bộ lọc</span>
                <FormComponent />
            </Wrapper>
            <Wrapper>
                <ListEventsComponent />
            </Wrapper>
        </div>
    )
}

//wrapper
const Wrapper = ({ children }) => {
    return (
        <div style={{
            borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)', paddingTop: '1.4rem',
            marginTop: '0.5rem', marginBottom: '1.4rem', paddingBottom: '1rem',
            maxWidth: '100%',
        }}>
            {children}
        </div>
    );
}

//form
const FormComponent = () => {
    const {
        setListEvents,
        category,
        status, setStatus,
        name, setName,
        idCategory, setIdCategory,
        formality, setFormality,
        statusSort, setStatusSort,
        semester,
        idSemester, setIdSemester,
        currentIndex, setCurrentIndex,
        setTotalPages, totalPages,
        isMe
    } = useContext(AppContext);

    const [formSearch] = Form.useForm();

    useEffect(() => {
        searchOrBtn();
    }, [currentIndex]);

    const searchOrBtn = () => {
        let isSearched = false;

        if (name || idCategory || idSemester) {
            isSearched = true;
        }

        if (isSearched) {
            handleSearchBtn();
        } else {
            search();
        }
    }

    const search = () => {
        let data = {
            status: status,
            name: name,
            idCategory: idCategory,
            formality: formality,
            statusSort: statusSort,
            idSemester: idSemester,
            page: currentIndex,
            isMe: isMe
        }
        OREventRegisteredApi.fetchAll(data)
            .then((response) => {
                setListEvents(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }
    //onclick search
    const handleSearchBtn = () => {
        //  //console.log(semester);
        setStatus(null);
        let data = {
            name: name,
            idCategory: idCategory,
            formality: formality,
            statusSort: statusSort,
            idSemester: idSemester,
            page: currentIndex,
            isMe: isMe
        }
        OREventRegisteredApi.fetchAll(data)
            .then((response) => {
                setListEvents(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                if (response.data.data.currentPage + 1 > response.data.data.totalPages && response.data.data.totalPages >= 1) {
                    setCurrentIndex(0);
                }
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    function cleanDataSearch() {
        setStatus(null);
        setName("");
        setIdCategory("");
        setIdSemester("");
        setFormality(-1);
    }
    //onclick btn get all data
    const onClickBtnGetAll = () => {
        let data = {
            statusSort: statusSort,
            formality: -1,
            page: currentIndex,
            isMe: isMe
        }
        OREventRegisteredApi.fetchAll(data)
            .then((response) => {
                setListEvents(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                cleanDataSearch();
                formSearch.resetFields();
                setCurrentIndex(0);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    const searchByStatusEvent = (s) => {
        setStatus(s);
        let data = {
            status: s,
            statusSort: statusSort,
            formality: -1,
            page: currentIndex,
            isMe: isMe
        }
        OREventRegisteredApi.fetchAll(data)
            .then((response) => {
                setListEvents(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                formSearch.resetFields();
                setCurrentIndex(0);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    return (
        <div>
            <Space wrap style={{
                justifyContent: 'center', alignItems: 'center', width: '100%',
                margin: '1rem 0 1rem 0', marginBottom: '2rem',
            }}>
                <Button type="primary"
                    htmlType="reset"
                    onClick={() => onClickBtnGetAll()}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Tất cả
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => searchByStatusEvent(1)}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Dự kiến
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => searchByStatusEvent(2)}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Cần sửa
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => searchByStatusEvent(3)}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Chờ phê duyệt
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => {
                        searchByStatusEvent(4);
                    }}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Đã phê duyệt
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => {
                        searchByStatusEvent(5);
                    }}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Đã tổ chức
                </Button>
                <Button type="primary" style={{ backgroundColor: 'rgb(242, 139, 75)' }}
                    onClick={() => {
                        searchByStatusEvent(0);
                    }}
                    onFocus={(e) => {
                        e.target.style.border = '2px solid rgb(214 193 193)';
                    }}
                    onBlur={(e) => {
                        e.target.style.border = '2px solid transparent';
                    }}
                >
                    Đã đóng
                </Button>
            </Space>
            <Form
                form={formSearch}
                labelCol={{ span: 24 }}
            >
                <Row style={{ justifyContent: 'space-around' }}>
                    <Col span={9} offset={-3}>
                        <Form.Item
                            label="Tên sự kiện"
                            name="eventName"
                            rules={[{ required: false, message: 'Vui lòng điền tên sự kiện!' }]}
                        >
                            <Input placeholder='Nhập tên sự kiện' onChange={(e) => setName(e.target.value)} value={name} />
                        </Form.Item>
                    </Col>
                    <Col span={9} offset={-3}>
                        <Form.Item
                            label="Thể loại"
                            name="category"
                            rules={[{ required: false, message: 'Vui lòng chọn thể loại!' }]}
                        >
                            <Select
                                onChange={(value) => setIdCategory(value)}
                                value={idCategory}
                                showSearch
                                optionFilterProp="children"
                                filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                options={category?.map((item) => ({
                                    value: item.id,
                                    label: item.name
                                }))}
                            />
                        </Form.Item>
                    </Col>
                </Row>
                <Row style={{ justifyContent: 'space-around' }}>
                    <Col span={9} offset={-3}>
                        <Form.Item
                            label="Hình thức diễn ra"
                            name="formality"
                            rules={[{ required: false, message: 'Vui lòng chọn hình thức diễn ra!' }]}
                        >
                            <Select defaultValue={-1} onChange={(value) => setFormality(value)} value={formality}>
                                <Option value={-1}>--Hình thức--</Option>
                                <Option value={0}>Online</Option>
                                <Option value={1}>Offline</Option>
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={9} offset={-3}>
                        <Form.Item
                            label="Học kỳ"
                            name="semester"
                            rules={[{ required: false, message: 'Vui lòng chọn hình thức diễn ra!' }]}
                        >
                            <Select
                                onChange={(value) => setIdSemester(value)}
                                value={idSemester}
                                showSearch
                                optionFilterProp="children"
                                filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                options={semester?.map((item) => ({
                                    value: item.id,
                                    label: item.name
                                }))}
                            />
                        </Form.Item>
                    </Col>
                </Row>
                <Space wrap style={{ justifyContent: 'center', alignItems: 'center', width: '100%' }}>
                    <Form.Item
                        style={{
                            marginBottom: '0',
                        }}
                    >
                        <Button type="primary" htmlType='submit' style={{ marginRight: '0.5rem' }}
                            onClick={() => handleSearchBtn()}
                        >
                            <FontAwesomeIcon icon={faMagnifyingGlass} style={{ marginRight: '0.5rem' }} />
                            Tìm kiếm
                        </Button>
                        <Button type="primary" style={{ backgroundColor: '#dc3545', marginLeft: '0.5rem' }}
                            htmlType="reset" onClick={cleanDataSearch}>
                            <FontAwesomeIcon icon={faBan} style={{ marginRight: '0.5rem' }} />
                            Làm mới bộ lọc
                        </Button>
                    </Form.Item>
                </Space>
            </Form>
        </div>
    );
}

//list events registered 
const ListEventsComponent = () => {
    const { listEvents, setListEvents, category,
        statusSort,
        setStatusSort,
        isIncreasing,
        setIsIncreasing,
        currentIndex,
        setTotalPages,
        status,
        idCategory,
        formality,
        name,
        idSemester,
        isMe, setIsMe
    } = useContext(AppContext);

    const handleChange = () => {
        if (listEvents.length === 0) {
            message.error("Không có dữ liệu");
        }
        var checked = isIncreasing;
        setIsIncreasing(!checked);
        setStatusSort(isIncreasing === true ? 1 : 0);
    };

    const handleChangeEvent = () => {
        var checked = isMe;
        setIsMe(!checked);
    };

    useEffect(() => {
        fillterByStatusSort();
    }, [isIncreasing, isMe]);

    const fillterByStatusSort = () => {

        let data = {
            status: status,
            name: name,
            idCategory: idCategory,
            formality: formality,
            statusSort: statusSort,
            idSemester: idSemester,
            page: currentIndex,
            isMe: isMe ? true : false,
        }

        OREventRegisteredApi.fetchAll(data)
            .then((response) => {
                setListEvents(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
    }

    const convertListLocationResponse = (listResponse) => {
        let list = [];
        for (let i = 0; i < listResponse.length; i++) {
            let nameLocation = '';
            let pathLocation = '';
            let formalityLocation = '';

            for (let j = 0; j < listResponse[i].listLocation.length; j++) {
                nameLocation += listResponse[i].listLocation[j].name + '       ';
                formalityLocation += listResponse[i].listLocation[j].formality.toString() + '       ';
                pathLocation += listResponse[i].listLocation[j].path + '       ';
            }
            let element = {
                index: i + 1,
                eventName: listResponse[i].eventResponse.name,
                listUserNameOrganizer: listResponse[i].listUserNameOrganizer,
                categoryName: findCategoryById(category, listResponse[i].eventResponse.categoryId) === undefined ? '--' : findCategoryById(category, listResponse[i].eventResponse.categoryId).name,
                formality: formalityLocation.includes('1') && formalityLocation.includes('0') ? 'Online, Offline' : formalityLocation.includes('0') ? 'Online'
                    : formalityLocation.includes('1') ? 'Offline' : '',
                startTime: convertDate(Number(listResponse[i].eventResponse.startTime)),
                endTime: convertDate(Number(listResponse[i].eventResponse.endTime)),
                status: listResponse[i].eventResponse.status,
                location: pathLocation
            }
            list.push(element);
        }
        return list
    };

    const onClickButtonExportData = (csvData) => {
        if (csvData.length === 0 || csvData === undefined) {
            message.error("Hiện không có dữ liệu nào, vui lòng kiểm tra lại !!!!");
            return;
        }
        const dateObject = new Date();
        const day = dateObject.getDate();
        const month = dateObject.getMonth() + 1;
        const year = dateObject.getFullYear();

        const formattedDate = `${day.toString().padStart(2, '0')}${month.toString().padStart(2, '0')}${year}`;
        var fileName = "Report" + formattedDate;
        const listData = convertListLocationResponse(csvData);
        const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        const fileExtension = '.xlsx';
        const ws = XLSX.utils.json_to_sheet(listData);
        ws['A1'] = { v: 'STT' };
        ws['B1'] = { v: 'Tên sự kiện' };
        ws['C1'] = { v: 'Danh sách người tổ chức' };
        ws['D1'] = { v: 'Thể loại' };
        ws['E1'] = { v: 'Hình thức diễn ra' };
        ws['F1'] = { v: 'Thời gian bắt đầu' };
        ws['G1'] = { v: 'Thời gian kết thúc' };
        ws['H1'] = { v: 'Trạng thái sự kiện' };
        ws['I1'] = { v: 'Địa điểm diễn ra' };

        const columnWidths = [
            { wch: 10 },
            { wch: 50 },
            { wch: 30 },
            { wch: 20 },
            { wch: 20 },
            { wch: 20 },
            { wch: 20 },
            { wch: 25 },
            { wch: 50 },
        ];
        ws['!cols'] = columnWidths;

        const dataCellStyle = {
            alignment: { horizontal: 'center', vertical: 'center' }, // Căn giữa
        };

        for (let row = 2; row <= listData.length; row++) {
            for (let col = 0; col < 8; col++) {
                const cellAddress = XLSX.utils.encode_cell({ c: col, r: row });
                ws[cellAddress].s = dataCellStyle; // Áp dụng định dạng cho ô
            }
        }

        const wb = { Sheets: { 'Danh sách sự kiện': ws }, SheetNames: ['Danh sách sự kiện'] };
        const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
        const data = new Blob([excelBuffer], { type: fileType });
        FileSaver.saveAs(data, fileName + fileExtension);
    }

    return (
        <div>
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    padding: '10px',
                }}
            >
                <span>
                    Danh sách sự kiện
                </span>
                <Row style={{

                    display: 'inline-flex',
                    justifyContent: 'right',
                    textAlign: 'center',
                    alignItems: 'center',
                    gap: '10px'
                }} direction="vertical">
                    {/* <Button
                        onClick={
                            () => { onClickButtonExportData(listEvents) }
                        }
                        style={{
                            display: 'inline-block',
                            background: 'rgb(0,115,59)',
                            color: '#ffffff',
                            marginRight: '0.6rem',
                        }}
                    >
                        <FontAwesomeIcon icon={faFileExport} style={{ color: "#ffffff", marginRight: '0.4rem' }} />
                        Export Excel
                    </Button> */}
                    <div
                        style={{
                            display: 'flex',
                            marginRight: '0.6rem',
                        }}
                    >
                        <p style={{ marginRight: '0.4rem' }}>Sắp xếp theo thời gian: </p>
                        <Switch
                            checkedChildren="Giảm dần"
                            unCheckedChildren="Tăng dần"
                            defaultChecked={true}
                            onChange={() => handleChange()}
                        />
                    </div>
                    <div
                        style={{
                            display: 'flex',
                            marginRight: '0.6rem',
                        }}
                    >
                        <p style={{ marginRight: '0.4rem' }}>Sự kiện của: </p>
                        <Switch
                            checkedChildren="Sự kiện của tất cả"
                            unCheckedChildren="Sự kiện của mình tôi"
                            defaultChecked={true}
                            onChange={() => handleChangeEvent()}
                        />
                    </div>
                </Row>

            </div>
            <TableComponent
                style={{
                    maxWidth: '100%',
                }}
            />
        </div>
    );
}

const TableComponent = () => {

    const { listEvents, category,
        currentIndex, setCurrentIndex,
        totalPages, setTotalPages
    } = useContext(AppContext);

    const columns = [
        {
            title: 'Tên', dataIndex: 'name', key: 'name',
            render: (text, record) => {
                return <span>{record.eventResponse.name} </span>
            }
        },
        {
            title: 'Người tổ chức', dataIndex: 'organizer', key: 'organizer',
            render: (text, record) => {
                return <span>{record.listUserNameOrganizer} </span>
            }
        },
        {
            title: 'Thể loại', dataIndex: 'categoryId', key: 'categoryId',
            render: (text, record) => {
                return (
                    //get Name category by idCategory
                    findCategoryById(category, record.eventResponse.categoryId) === undefined ? '--' : findCategoryById(category, record.eventResponse.categoryId).name
                );
            }
        },
        {
            title: 'Hình thức', dataIndex: 'formality', key: 'formality',
            render: (index, record) => {
                //get the formality event if 0 ? Online : Offline
                return record.listLocation.length === 0 ? <span>--</span> :
                    (record.listLocation.length === 1 && record.listLocation[0].formality === 0) ? <span>Online</span> :
                        (record.listLocation.length === 1 && record.listLocation[0].formality === 1) ? <span>Offline</span> :
                            record.listLocation.length >= 2 ? <span>Offline</span> : <span>--</span>
                    ;
            }
        },
        {
            title: 'Thời gian bắt đầu', dataIndex: 'startTime', key: 'startTime',
            render: (text, record) => {
                //convert start time event to String (HH:MM DD-MM-YYYY)
                return record.eventResponse.startTime === undefined || record.eventResponse.startTime == '' ? '--' : convertDate(record.eventResponse.startTime);
            },
        },
        {
            title: 'Thời gian kết thúc', dataIndex: 'endTime', key: 'endTime',
            render: (text, record) => {
                //convert end time event to String (HH:MM DD-MM-YYYY)
                return record.eventResponse.endTime === undefined || record.eventResponse.endTime == '' ? '--' : convertDate(record.eventResponse.endTime);
            }
        },
        {
            title: 'Trạng thái', dataIndex: 'status', key: 'status',
            render: (text, record) => {
                return record.eventResponse.status === 0 ? <span>Đã đóng</span> :
                    record.eventResponse.status === 1 ? <span>Dự kiến</span> :
                        record.eventResponse.status === 2 ? <span>Cần sửa</span> :
                            record.eventResponse.status === 3 ? <span>Chờ phê duyệt</span> :
                                record.eventResponse.status === 4 ? <span>Đã phê duyệt</span> :
                                    record.eventResponse.status === 5 ? <span>Đã tổ chức</span> : 
                                        record.eventResponse.status === 6 ? <span>Đã được bộ môn thông qua</span> : 
                                            <span></span>
                    ;
            }
        },
        {
            title: 'Địa điểm', dataIndex: 'path', key: 'path',
            render: (text, record) => {
                const locationSubList = record.listLocation;
                return record.listLocation.length === 0 ? <span>--</span> :
                    record.listLocation.length === 1 && record.listLocation[0].formality === 0 ? <a href={record.listLocation[0].path} target="_blank">{record.listLocation[0].path}</a> :
                        record.listLocation.length === 1 && record.listLocation[0].formality === 1 ? <span>{record.listLocation[0].path}</span> :
                            record.listLocation.length >= 2
                                ? locationSubList.map((location, locationIndex) => (
                                    location.formality === 0 ?
                                        <a href={location.path} key={locationIndex} target="_blank">{
                                            location.path + '\n'
                                        }
                                        </a>
                                        :
                                        <span key={locationIndex}>{
                                            location.path + '\n'
                                        }
                                        </span>
                                ))
                                : null;
                ;
            }
        },
        {
            title: 'Thao tác', dataIndex: 'action', key: 'action',
            render: (text, record) => (
                <>
                    <Link to={`/organizer-management/event-detail/${record.eventResponse.id}`}>
                        <Tooltip placement="top" title={"Xem chi tiết"}>
                            <Button shape="circle" size="middle" type='primary' htmlType='button'>
                                <FontAwesomeIcon icon={faEye} />
                            </Button>
                        </Tooltip>
                    </Link>
                </>
            ),
        },
    ];
    return (
        <div
            style={{
                position: 'relative',
            }}
        >
            <div>
                {listEvents.length === 0 ? (
                    <Empty description="Không có dữ liệu">
                    </Empty>
                ) : (
                    <>
                        <Table columns={columns} dataSource={listEvents} size='middle' bordered
                            pagination={false}
                            scroll={{
                                x: 1300,
                            }} />
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
                                current={currentIndex + 1}
                                onChange={(value) => {
                                    setCurrentIndex(value - 1);
                                }}
                                total={totalPages * 10}
                            />
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}
//convert long to HH:MM dd/mm/yyyy
const convertDate = (timestamp) => {
    const dateObject = new Date(timestamp);
    const hours = dateObject.getHours();
    const minutes = dateObject.getMinutes();
    const day = dateObject.getDate();
    const month = dateObject.getMonth() + 1;
    const year = dateObject.getFullYear();

    const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    const formattedDate = `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
    return formattedTime + ' ' + formattedDate;
}

//find the Category by id
const findCategoryById = (category, id) => {
    var categoryObject = category.find((item) => item.id === id);
    return categoryObject;
}

