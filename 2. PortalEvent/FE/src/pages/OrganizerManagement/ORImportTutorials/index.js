import { Button, Col, Empty, Form, Input, Modal, Row, Select, Space, Table, Upload, message } from "antd";
import { useState } from "react";
import { AppContextImportTutorials } from "./contex";
import { useContext } from "react";
import { UploadOutlined } from "@mui/icons-material";
import { useEffect } from "react";
import ORImportTutorialsApi from "./ORImportTutorialsApi";
import { Option } from "@mui/base";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleInfo, faFileImport } from "@fortawesome/free-solid-svg-icons";
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

export default function OREImportTutorials() {

    const [listTutorials, setListTutorials] = useState([]);
    const [fileListData, setFileListData] = useState();
    const [listSemester, setListSemester] = useState([]);
    const [blockNumber, setBlockNumber] = useState(true);
    const [idSemester, setIdSemester] = useState("");
    const [listDataImport, setListDataImport] = useState([]);
    const [uploadFileStatus, setUploadFileStatus] = useState(true);
    const [countFileUpload, setCountFileUpload] = useState(0);

    return (
        <AppContextImportTutorials.Provider
            value={{
                listTutorials, setListTutorials,
                uploadFileStatus, setUploadFileStatus,
                countFileUpload, setCountFileUpload,
                listSemester, setListSemester,
                idSemester, setIdSemester,
                blockNumber, setBlockNumber,
                fileListData, setFileListData,
                listDataImport, setListDataImport
            }}>
            <ORAImportTutorialWrapper />
        </AppContextImportTutorials.Provider>
    );
}


const ORAImportTutorialWrapper = () => {
    const { listTutorials, setListTutorials, listSemester, setListSemester } = useContext(AppContextImportTutorials);

    useEffect(() => {
        getSemesters();
    }, [], [listSemester]);

    //init semesters
    const getSemesters = () => {
        ORImportTutorialsApi.getSemesters()
            .then((response) => {
                setListSemester(response.data.data);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            });
    }

    useEffect(() => {
        getTutorials();
    }, [], [listTutorials]);

    //get all tutorials
    const getTutorials = () => {
        var data = {
            idSemester: null,
            blockNumber: null
        }
        ORImportTutorialsApi.getAllTutorials(data)
            .then((response) => {
                setListTutorials(response.data.data);
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            });
    }
    return (
        <div style={{
            backgroundColor: 'white', borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)'
            , padding: '2rem'
        }}>
            <h3 style={{ margin: '1rem 0 1rem 0' }}>Import Tutorials</h3>
            {/* <WrapperContainner>
                <span>Thông tin</span>
                <FormComponent />
            </WrapperContainner> */}
            <WrapperContainner>
                <TableComponent />
            </WrapperContainner>
        </div>
    );
}

//wrapper
const WrapperContainner = ({ children }) => {
    return (
        <div style={{
            borderRadius: '0.5rem', boxShadow: '0.1rem 0.1rem 1rem rgba(0, 0, 0, 0.1)', padding: '1.4rem',
            marginTop: '0.5rem', marginBottom: '1.4rem'
        }}>
            {children}
        </div>
    );
}


//form
// const FormComponent = () => {
//     const {
//         fileListData, setFileListData,
//         uploadFileStatus, setUploadFileStatus,
//         countFileUpload, setCountFileUpload,
//         listSemester, setListSemester,
//         idSemester, setIdSemester,
//         blockNumber, setBlockNumber,
//         listDataImport, setListDataImport,
//         setListTutorials
//     } = useContext(AppContextImportTutorials);

//     const onFinish = (values) => {
//         if (uploadFileStatus && countFileUpload !== 0) {
//             var request = {
//                 idSemester: idSemester,
//                 blockNumber: blockNumber,
//                 listDataImport: listDataImport,
//             }
//             ORImportTutorialsApi.importTutorials(request)
//                 .then((response) => {
//                     message.success('Import successfully');
//                     var data = {
//                         idSemester: null,
//                         blockNumber: null
//                     }
//                     ORImportTutorialsApi.getAllTutorials(data)
//                         .then((response) => {
//                             setListTutorials(response.data.data);
//                         })
//                 })
//                 .catch((error) => {
//                     message.error('Vui lòng liên hệ hỗ trợ');
//                 })
//                //  //console.log(request);
//         }
//         else if (countFileUpload === 0) {
//             message.error('Bạn chưa tải lên file nào !!!');
//         }
//         else {
//             message.error('Định dạng tệp không hợp lệ. Chỉ chấp nhận .xls, .xlsx, hoặc .csv');
//         }
//     };

//     const customRequest = ({ file, onSuccess, onError }) => {
//         const acceptedFormats = [".xls", ".xlsx", ".csv"];
//         const fileName = file.name;
//         const fileExtension = fileName.slice(((fileName.lastIndexOf(".") - 1) >>> 0) + 2);
//         if (acceptedFormats.includes(`.${fileExtension}`)) {
//             message.success(fileName + ' upload success');
//             setTimeout(() => {
//                 setUploadFileStatus(true);
//                 setCountFileUpload(1);
//                 onSuccess('ok');
//             }, 0);
//         } else {
//             setUploadFileStatus(false);
//             message.error('Định dạng tệp không hợp lệ. Chỉ chấp nhận .xls, .xlsx, hoặc .csv');
//             onError(new Error('File format is not supported'));
//         }
//     };

//     const handleFileChange = (info) => {
//         const reader = new FileReader();
//         if (info.file.status === 'done') {
//             const file = info.file.originFileObj;
//             // Đọc tệp Excel
//             const reader = new FileReader();
//             reader.onload = (e) => {
//                 const data = new Uint8Array(e.target.result);
//                 const workbook = XLSX.read(data, { type: 'array' });

//                 // Lấy dữ liệu từ tệp Excel
//                 const sheetName = workbook.SheetNames[0]; // Lấy tên sheet (hoặc chọn sheet cụ thể)
//                 const worksheet = workbook.Sheets[sheetName];

//                 const configHeader = {
//                     header: ['name', 'startTime', 'endTime', 'majorStr', 'objectStr', 'locationStr']
//                 };

//                 // Sử dụng SheetJS để trích xuất dữ liệu
//                 const jsonData = XLSX.utils.sheet_to_json(worksheet, configHeader);
//                //  //console.log(configHeader);
//                 setListDataImport(jsonData.slice(1, jsonData.length));
//             };
//             reader.readAsArrayBuffer(file);
//         }
//     };

//     const [formInfomation] = Form.useForm();

//     return (
//         <div>
//             <Form
//                 labelCol={{ span: 24 }}
//                 onFinish={() => onFinish()}
//                 form={formInfomation}
//                 style={{
//                     padding: '0.4rem 1rem',
//                 }}
//             >
//                 <Row style={{ justifyContent: 'space-between' }}>
//                     <Col span={9} offset={-3}>
//                         <Form.Item
//                             label="Học kỳ"
//                             name="semester"
//                         >
//                             <Select onChange={(value) => setIdSemester(value)}>
//                                 {listSemester.map((item) => (
//                                     <Select.Option key={item.id} value={item.id}>
//                                         {item.name}
//                                     </Select.Option>
//                                 ))}
//                             </Select>
//                         </Form.Item>
//                     </Col>
//                     <Col span={9} offset={-3}>
//                         <Form.Item
//                             label="Block"
//                             name="block"
//                         >
//                             <Select onChange={(value) => setBlockNumber(value)}>
//                                 <Option value={false}>Block 1</Option>
//                                 <Option value={true}>Block 2</Option>
//                             </Select>
//                         </Form.Item>
//                     </Col>
//                 </Row>
//                 <Space wrap style={{ justifyContent: 'center', alignItems: 'center', width: '100%' }}>
//                     <Form.Item style={{
//                         marginBottom: '0',
//                     }}>
//                         <Button type="primary" style={{ marginRight: '0.5rem' }} onClick={() => onFinish()}>
//                             Import
//                         </Button>
//                         <Button type="primary" style={{ backgroundColor: '#dc3545', marginLeft: '0.5rem' }}
//                             htmlType="reset">
//                             Clear
//                         </Button>
//                     </Form.Item>
//                 </Space>
//             </Form>
//         </div>
//     );
// }

const TableComponent = () => {
    const [formInfomation] = Form.useForm();
    const { listTutorials } = useContext(AppContextImportTutorials);
    const columns = [
        {
            title: 'Tên sự kiện', dataIndex: 'name', key: 'name',
            render: (text, record) => {
                return <span>{record.tutorial.name}</span>
            }
        },
        {
            title: 'Thời gian bắt đầu', dataIndex: 'startTime', key: 'startTime',
            render: (text, record) => {
                return <span>{convertDate(record.tutorial.startTime)}</span>
            }
        },
        {
            title: 'Thời gian kết thúc', dataIndex: 'endTime', key: 'endTime',
            render: (text, record) => {
                return <span>{convertDate(record.tutorial.endTime)}</span>
            }
        },
        {
            title: 'Bộ môn', dataIndex: 'majorName', key: 'major',
            render: (text, record) => {
                return <span>{record.majorName}</span>
            }
        },
        {
            title: 'Đối tượng', dataIndex: 'objectName', key: 'object',
            render: (text, record) => {
                return <span>{record.objectName}</span>
            }
        },
        {
            title: 'Địa điểm', dataIndex: 'locationPath', key: 'location',
            render: (text, record) => {
                const locationStr = record.locationPath;
                const locationArr = locationStr.split(',');

                return locationStr === "" ? "--"
                    :
                    locationArr.map((location, index) => (
                        <a href={location} key={index} target="_blank">
                            {location + '\n'}
                        </a>
                    ))
            }
        },
    ]
    const {
        fileListData, setFileListData,
        uploadFileStatus, setUploadFileStatus,
        countFileUpload, setCountFileUpload,
        listSemester, setListSemester,
        idSemester, setIdSemester,
        blockNumber, setBlockNumber,
        listDataImport, setListDataImport,
        setListTutorials
    } = useContext(AppContextImportTutorials);

    const customRequest = ({ file, onSuccess, onError }) => {
        const acceptedFormats = [".xls", ".xlsx", ".csv"];
        const fileName = file.name;
        const fileExtension = fileName.slice(((fileName.lastIndexOf(".") - 1) >>> 0) + 2);
        if (acceptedFormats.includes(`.${fileExtension}`)) {
            message.success(fileName + ' upload success');
            onSuccess(fileName + ' upload success');
            setTimeout(() => {
                setUploadFileStatus(true);
                setCountFileUpload(1);
            }, 0);
        }
        else {
            setUploadFileStatus(false);
            message.error('Định dạng tệp không hợp lệ. Chỉ chấp nhận .xls, .xlsx, hoặc .csv');
            onError(new Error('File format is not supported'));
            return;
        }
    };

    const handleFileChange = (info) => {
        if (info.file.status === 'done') {
            const file = info.file.originFileObj;
            // Đọc tệp Excel
            const reader = new FileReader();
            reader.onload = (e) => {
                const data = new Uint8Array(e.target.result);
                const workbook = XLSX.read(data, { type: 'array' });

                // Lấy dữ liệu từ tệp Excel
                const sheetName = workbook.SheetNames[0]; // Lấy tên sheet (hoặc chọn sheet cụ thể)
                const worksheet = workbook.Sheets[sheetName];

                const configHeader = {
                    header: ['name', 'startTime', 'endTime', 'majorStr', 'objectStr', 'locationStr']
                };

                // Sử dụng SheetJS để trích xuất dữ liệu
                const jsonData = XLSX.utils.sheet_to_json(worksheet, configHeader);
                setListDataImport(jsonData.slice(1, jsonData.length));
            };
            reader.readAsArrayBuffer(file);
        }
    };

    const [isModalOpen, setIsModalOpen] = useState(false);
    const handleCancel = () => {
        setIsModalOpen(false);
    };

    function cleanDataImport() {
        setCountFileUpload(0);
        setIdSemester("");
        setBlockNumber(true);
        setListDataImport([]);
    }

    const onFinish = (values) => {
        if (uploadFileStatus && countFileUpload !== 0) {
            var request = {
                idSemester: idSemester,
                blockNumber: blockNumber,
                listDataImport: listDataImport,
            }
            if (idSemester === "") {
                message.error('Bạn chưa chọn học kỳ diễn ra sự kiện !!!');
            }
            else {
                ORImportTutorialsApi.importTutorials(request)
                    .then((response) => {
                        var data = {
                            idSemester: null,
                            blockNumber: null
                        }
                        setIsModalOpen(false);
                        message.success('Import successfully');
                        formInfomation.resetFields();
                        cleanDataImport();
                        ORImportTutorialsApi.getAllTutorials(data)
                            .then((response) => {
                                setListTutorials(response.data.data);
                            })
                            .catch((error) => {
                                message.warning(error.response.data.message);
                            });
                    })
                    .catch((error) => {
                        formInfomation.resetFields();
                        cleanDataImport();
                        message.warning(error.response.data.message);
                    })
            }
        }
        else if (countFileUpload === 0) {
            message.error('Bạn chưa tải lên file nào !!!');
        }
        else {
            message.error('Định dạng tệp không hợp lệ. Chỉ chấp nhận .xls, .xlsx, hoặc .csv');
        }
    };

    const dowloadTemplate = () => {
        const dateObject = new Date();
        const day = dateObject.getDate().toString().padStart(2, '0');
        const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
        const year = dateObject.getFullYear().toString();
        const hours = Number(dateObject.getHours().toString().padStart(2, '0')) + 1;
        const minutes = dateObject.getMinutes().toString().padStart(2, '0');

        var startTimeStr = `${day}${month}${year} ${hours}:${minutes}`
        var endTimeStr = `${Number(Number(day) + 1)}${month}${year} ${hours}:${minutes}`
        var dataTemplate =
            [
                {
                    eventName: "Event Demo",
                    startTime: startTimeStr,
                    endTime: endTimeStr,
                    majorName: "Phát triển phần mềm",
                    objectName: "Kỳ 1",
                    locationPath: "https://meet.google.com/cpx-kevh-dcw",
                },
            ]
        var fileName = "Template import"
        const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        const fileExtension = '.xlsx';
        const ws = XLSX.utils.json_to_sheet(dataTemplate)
        // XLSX.utils.sheet_add_aoa(ws, [[note]], { origin: 'F5' });
        ws['A1'] = { v: 'Tên' };
        ws['B1'] = { v: 'Thời gian bắt đầu' };
        ws['C1'] = { v: 'Thời gian kết thúc' };
        ws['D1'] = { v: 'Bộ môn' };
        ws['E1'] = { v: 'Đối tượng' };
        ws['F1'] = { v: 'Địa điểm' };

        const columnWidths = [
            { wch: 20 },
            { wch: 20 },
            { wch: 20 },
            { wch: 20 },
            { wch: 20 },
            { wch: 40 },
        ];
        ws['!cols'] = columnWidths;
        const wb = { Sheets: { 'Danh sách Import': ws }, SheetNames: ['Danh sách Import'] };
        const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
        const data = new Blob([excelBuffer], { type: fileType });
        FileSaver.saveAs(data, fileName + fileExtension);
    }

    let note = "(*)Lưu ý: \n "
        + "Hãy đảm bảo tất cả các trường không bỏ trống. "
        + "Thời gian bắt đầu và kết thúc được điền đúng định dạng: ddMMyyyy hh:ss. "
        + "Bộ môn, đối tượng, địa điểm: nếu có nhiều hơn một hãy đảm bảo các dữ liệu được ngăn cách nhau bởi dấu phẩy (,)"
    return (
        <div>
            <span style=
                {{
                    position: 'relative',
                }}
            >
                Danh sách tutorials
            </span>
            <Row style={{
                position: 'absolute',
                right: '6%',
                display: 'inline-flex',
                justifyContent: 'right',
                textAlign: 'center',
                alignItems: 'center',
            }} direction="vertical">
                <Button
                    style={{
                        display: 'inline-block',
                        background: 'rgb(0,115,59)',
                        color: '#ffffff'
                    }}
                    onClick={() => setIsModalOpen(true)}
                >
                    <FontAwesomeIcon icon={faFileImport} style={{ color: "#ffffff", marginRight: '0.4rem' }} />
                    Import Tutorials
                </Button>
                <Modal
                    open={isModalOpen}
                    footer={null}
                    closable={false}
                >
                    <h2
                        style={{
                            margin: 'auto',
                            textAlign: 'center',
                        }}
                    >
                        <FontAwesomeIcon icon={faCircleInfo} style={{ marginRight: '0.5rem' }} />
                        Thông tin sự kiện
                    </h2>
                    <Form
                        form={formInfomation}
                        labelCol={{ span: 24 }}
                        onFinish={() => onFinish()}
                    >
                        <Row style={{ justifyContent: 'space-between' }}>
                            <Col span={9} offset={-3}>
                                <Form.Item
                                    label="Học kỳ"
                                    name="semester"
                                >
                                    <Select onChange={(value) => setIdSemester(value)}>
                                        {listSemester.map((item) => (
                                            <Select.Option key={item.id} value={item.id}>
                                                {item.name}
                                            </Select.Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>
                            <Col span={9} offset={-3}>
                                <Form.Item
                                    label="Block"
                                    name="block"
                                >
                                    <Select onChange={(value) => setBlockNumber(value)}>
                                        <Option value={false}>Block 1</Option>
                                        <Option value={true}>Block 2</Option>
                                    </Select>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row style={{ justifyContent: 'space-between' }}>
                            <Col span={9} offset={-3}>
                                <Form.Item
                                    label="File Input"
                                    name="fileInput"
                                    style={{
                                        marginBottom: '10px',
                                    }}
                                >
                                    <Upload
                                        fileList={fileListData}
                                        customRequest={customRequest}
                                        onChange={handleFileChange}
                                        listType="text"
                                        maxCount={1}
                                    >
                                        <Button icon={<UploadOutlined />}>Tải lên tệp</Button>
                                    </Upload>

                                </Form.Item>
                            </Col>
                            <p style={{
                                marginBottom: '1rem',
                            }}>{note}</p>
                        </Row>
                        <Space wrap style={{ justifyContent: 'center', alignItems: 'center', width: '100%' }}>
                            <Form.Item style={{
                                marginBottom: '0',
                            }}>
                                <Button type="primary" style={{ marginRight: '0.5rem' }} onClick={() => dowloadTemplate()}>
                                    Tải file mẫu
                                </Button>
                                <Button type="primary" style={{ marginRight: '0.5rem' }} onClick={() => onFinish()}>
                                    Import
                                </Button>
                                <Button type="primary" style={{ backgroundColor: '#dc3545', marginLeft: '0.5rem' }}
                                    onClick={() => handleCancel()}
                                >
                                    Close
                                </Button>
                            </Form.Item>
                        </Space>
                    </Form>
                </Modal>
            </Row>
            {
                listTutorials.length === 0 ? (
                    <Empty description="Không có dữ liệu"></Empty>
                ) : (
                    <>
                        <Table columns={columns} dataSource={listTutorials.map(item => ({ ...item, key: item.id }))} style={{}} scroll={{
                            x: 1300,
                        }} />
                    </>
                )
            }
        </div>
    );
}

//convert long to HH:MM dd/mm/yyyy
const convertDate = (timestamp) => {
    let dateObject = new Date(Number(timestamp));
    let hours = dateObject.getHours();
    let minutes = dateObject.getMinutes();
    let day = dateObject.getDate();
    let month = dateObject.getMonth() + 1;
    let year = dateObject.getFullYear();

    let formattedTime = `${hours.toString().padStart(2, '0')}:${minutes === undefined ? '' : minutes.toString().padStart(2, '0')}`;
    let formattedDate = `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
    return formattedTime + ' ' + formattedDate
}
