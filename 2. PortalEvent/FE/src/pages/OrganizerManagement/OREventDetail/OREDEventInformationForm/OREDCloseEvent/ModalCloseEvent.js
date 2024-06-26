import {
    Button,
    Popconfirm,
    Modal,
    Form,
    Input,
    message
} from "antd";
import { useState } from "react";
import OREventDetailApi from "../../OREventDetailApi";
import { useParams } from "react-router-dom";

const ModalCloseEvent = ({ visible, onCancel, detailEvent, isRole }) => {
    const [reason, setReason] = useState("");
    const [errMessage, setErrMessage] = useState('');
    const { id } = useParams();

    // Hủy sự kiện
    const handleOk = () => {
        if (isRole === true) {
            if (reason === null || reason === '') {
                setErrMessage("Bạn cần nhập lý do hủy!");
                return;
            }
            if (reason.length >= 1000) {
                setErrMessage("Bạn đã nhập quá giới hạn số lượng từ");
                return;
            }
            setErrMessage('');
            let data = {
                idEvent: id,
                reason: reason,
                status: 0
            }
            OREventDetailApi.closeEvent(data).then((response) => {
                onCancel();
                message.success("Hủy sự kiện thành công");
                detailEvent(id);
                setErrMessage('');
                setReason('');
            })
            .catch((error) => {
                message.warning(error.response.data.message);
            })
        } else {
            message.error("Bạn không có quyền hủy sự kiện");
            onCancel();
        }
    };

    return (
        <>

            <Modal title="Xác nhận hủy"
                visible={visible}
                onCancel={onCancel}
                footer={null}
            >
                <Form>
                    <Form.Item label="Lý do hủy" required style={{ marginBottom: 10 }}>
                        <Input.TextArea rows={5} name="reason" value={reason}
                            onChange={(e) => setReason(e.target.value)}
                        />
                    </Form.Item>
                    {errMessage && (
                        <span style={{ color: 'red', marginLeft: 57 }}>{errMessage}</span>
                    )}
                </Form>
                <div style={{ textAlign: "right", marginTop: "15px" }}>
                    <div style={{ paddingTop: "15px" }}>
                    <Popconfirm
                        title="Bạn chắc chắn muốn đóng sự kiện này không?"
                        onConfirm={() => {
                            handleOk();
                        }}
                        okText="Có"
                        cancelText="Không"
                        >
                            <Button
                                style={{
                                    marginRight: "5px",
                                    backgroundColor: "rgb(61, 139, 227)",
                                    color: "white",
                                }}
                            >
                                Đóng sự kiện
                            </Button>
                        </Popconfirm>

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
            </Modal>
        </>
    )
}

export default ModalCloseEvent;