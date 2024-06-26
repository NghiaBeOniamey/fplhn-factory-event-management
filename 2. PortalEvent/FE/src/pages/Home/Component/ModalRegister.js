import {faPenToSquare, faPlus, faXmark} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Modal, Button, Form, Input, Select, message} from "antd";
import {useEffect, useState} from "react";
import {PAHomeApi} from "../PAHomeApi";

const ModalRegister = ({visible, onCancel, eventId, updateIsRegisted, isLecturer}) => {
    const [listLecturer, setListLecturer] = useState([]);
    const [lecturers, setLecturers] = useState([]);
    const [className, setClassName] = useState('');
    const [question, setQuestion] = useState('');

    // err
    const [errClassName, setErrClassName] = useState('');
    const [errLecturer, setErrLecturer] = useState('');

    useEffect(() => {
        if (visible) {
            setErrClassName("");
            setErrLecturer("");
            getListLecturer();
        }
    }, [visible]);

    const getListLecturer = () => {
        PAHomeApi.listOrganizer().then((res) => {
            setListLecturer(res.data.data);
        }).catch((err) => {
           //  //console.log(err);
        })
    }

    const handleCancel = () => {
        onCancel();
        setClassName('');
        setLecturers([]);
        setQuestion('');
    }

    const handleRegister = () => {
        let check = 0;
        if (className === "" && !isLecturer) {
            setErrClassName("Vui lòng nhập tên lớp!");
            check += 1;
        } else {
            setErrClassName("");
        }
        if (lecturers.length === 0 && !isLecturer) {
            setErrLecturer("Vui lòng chọn giảng viên!");
            check += 1;
        } else {
            setErrLecturer("");
        }
        if (check === 0) {
            const obj = {
                className: className,
                question: question,
                lecturerName: lecturers.length === 0 ? null : lecturers.join(","),
                eventId: eventId
            }
            PAHomeApi.registerEvent(obj).then((res) => {
                message.success("Đăng ký sự kiện thành công!!!");
                updateIsRegisted();
                handleCancel();
            }).catch((err) => {
                message.warning(err.response.data.message);
            })
        }
    }

    return (
        <>
            <Modal
                visible={visible}
                onCancel={handleCancel}
                closable={false}
                footer={[
                    <Button
                        type="primary"
                        className="btn-form-event"
                        onClick={() => handleRegister()}
                    >
                        Đăng ký
                        <FontAwesomeIcon
                            style={{color: "#ffffff", marginLeft: "7px"}}
                            icon={faPenToSquare}/>
                    </Button>,
                    <Button
                        style={{backgroundColor: "red", color: "white"}}
                        key="back"
                        onClick={() => handleCancel()}
                    >
                        Hủy
                        <FontAwesomeIcon
                            icon={faXmark}
                            style={{color: "#ffffff", marginLeft: "7px"}}
                        />
                    </Button>,
                ]}
            >
                <div style={{paddingTop: "0", borderBottom: "1px solid black"}}>
                    <span style={{fontSize: "18px"}}>
                        Đăng ký sự kiện
                    </span>
                </div>
                <div>
                    <Form
                        style={{
                            marginTop: "15px",
                            borderBottom: "1px solid black",
                            marginBottom: "15px",
                        }}
                        layout="vertical"
                        name="basic"
                    >
                        {!isLecturer &&
                            <>
                                <Form.Item label="Tên lớp" required>
                                    <Input
                                        maxLength={100}
                                        placeholder="Nhập vào tên lớp"
                                        value={className}
                                        onChange={(e) => setClassName(e.target.value)}
                                    />
                                    <span style={{color: "red"}}>{errClassName}</span>
                                </Form.Item>
                                <Form.Item label="Tên giảng viên" required>
                                    <Select
                                        allowClear={true}
                                        placeholder="Chọn giảng viên"
                                        mode="multiple"
                                        value={lecturers}
                                        onChange={(value) => setLecturers(value)}
                                        options={listLecturer.map((lecturer) => ({
                                            value: lecturer.userName,
                                            label: lecturer.userName,
                                        }))}
                                    />
                                    <span style={{color: "red"}}>{errLecturer}</span>
                                </Form.Item>
                            </>
                        }
                        <Form.Item label="Câu hỏi cho sự kiện">
                            <Input.TextArea
                                rows={4}
                                placeholder="Nhập vào câu hỏi"
                                value={question}
                                maxLength={1000}
                                onChange={(e) => setQuestion(e.target.value)}
                            />
                        </Form.Item>
                    </Form>
                </div>
            </Modal>
        </>
    )
}

export default ModalRegister;