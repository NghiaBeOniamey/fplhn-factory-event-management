import { faPlus, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal, Button, Form, Input , message, Rate } from "antd";
import { useEffect, useState } from "react";
import { PAHomeApi } from "../PAHomeApi";
import { CheckCircleOutlined, CheckSquareOutlined } from "@ant-design/icons";

const { TextArea } = Input;

const ModalAttendance = ({ visible, onCancel, eventId, updateIsAttended}) => {
    const [feedback, setFeedback] = useState('');
    const [rate, setRate] = useState(5);

    // err
    const [errRate, setErrRate] = useState('');

    const desc = ['Rất kém', 'Kém', 'Trung bình', 'Tốt', 'Xuất sắc'];

    const handleCancel = () => {
        onCancel();
        setFeedback('');
    }
    const handleAttendance = () => {
        let check = 0;
        if (rate === 0) {
            setErrRate("Vui lòng đánh giá cho sự kiện!");
            check += 1;
        } else {
            setErrRate("");
        }
        
        if (check === 0) {
            const obj = {
                eventId: eventId,
                feedback: feedback,
                rate: rate,
            }
            PAHomeApi.rollCallEvent(obj).then((res) => {
                message.success("Điểm danh sự kiện thành công!!!");
                updateIsAttended();
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
                onCancel={() => handleCancel()}
                closable={false}
                footer={[
                    <Button
                        type="primary"
                        className="btn-form-event"
                        onClick={() => handleAttendance()}
                    >
                        Điểm danh
                        <CheckSquareOutlined style={{ fontSize: '15px', color: "#ffffff", marginLeft: "7px" }}/>
                    </Button>,
                    <Button
                        style={{ backgroundColor: "red", color: "white" }}
                        key="back"
                        onClick={() => handleCancel()}
                    >
                        Hủy
                        <FontAwesomeIcon
                            icon={faXmark}
                            style={{ color: "#ffffff", marginLeft: "7px" }}
                        />
                    </Button>,
                ]}
            >
                <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
                    <span style={{ fontSize: "18px" }}>
                        Điểm danh sự kiện
                    </span>
                </div>
                <div>
                    <Form
                        style={{
                            marginTop: "15px",
                            borderBottom: "1px solid black",
                            marginBottom: "15px",
                        }}
                        name="basic"
                    >
                        <Form.Item label="Đánh giá" required style={{marginBottom: '7px'}}>
                            <Rate tooltips={desc} onChange={setRate} value={rate} style={{marginLeft: '7px'}}/>
                            {rate ? <span className="ant-rate-text">{desc[rate - 1]}</span> : ''}
                            <span style={{ color: "red" }}>{errRate}</span>
                        </Form.Item>
                        <Form.Item label="Bạn có góp ý gì cho chương trình không?" labelCol={{ span: 24 }}>
                            <TextArea rows={4} onChange={(e) => setFeedback(e.target.value)}/>
                        </Form.Item>
                    </Form>
                </div>
            </Modal>
        </>
    )
}

export default ModalAttendance;