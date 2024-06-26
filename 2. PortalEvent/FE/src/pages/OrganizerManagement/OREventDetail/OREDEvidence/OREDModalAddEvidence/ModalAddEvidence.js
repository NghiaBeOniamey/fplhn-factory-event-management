import { faPlus, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useParams } from "react-router-dom";
import OREventDetailApi from "../../OREventDetailApi";

const React = require("react");
const { useState } = require("react");
const { Upload, Modal, message, Button, Form, Radio, Input } = require("antd");
const { UploadOutlined } = require("@ant-design/icons");

const getBase64 = (file) =>
    new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
    });

const ModalAddEvidence = ({ visible, onCancel, getEvidences }) => {
    const [fileList, setFileList] = useState([]);
    const [previewOpen, setPreviewOpen] = useState(false);
    const [previewImage, setPreviewImage] = useState("");
    const [previewTitle, setPreviewTitle] = useState("");
    const [selectedOption, setSelectedOption] = useState(0);
    const [path, setPath] = useState("");
    const [name, setName] = useState("");
    const { id } = useParams();
    //err
    const [errPath, setErrPath] = useState();
    const [errName, setErrName] = useState();
    const [errImg, setErrImg] = useState();

    const beforeUpload = (file) => {
        const isImage = file.type.startsWith("image/");
        if (!isImage) {
            message.error("Chỉ được tải lên các tệp hình ảnh!");
            return false;
        } else if (file.size > 1024 * 1024) {
            message.error("Ảnh tải lên vượt quá giới hạn 1MB");
            return false;
        }

        return false;
    };

    const handlePreview = async (file) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj);
        }
        setPreviewImage(file.url || file.preview);
        setPreviewOpen(true);
        setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf("/") + 1));
    };

    const handleCancel = () => setPreviewOpen(false);

    const handleChange = ({ fileList: newFileList }) => {
        if (newFileList.length <= 10) {
            setFileList(newFileList);
        } else {
            message.error("Chỉ được phép tải lên tối đa 10 ảnh.");
        }
    };

    //Thêm evidence
    const handleAdd = () => {
        if (selectedOption === 0) {
            let check = 0;
            if (name === "") {
                setErrName("Tên evidence không được để trống!");
                check += 1;
            } else {
                setErrName("");
            }
            if (path === "") {
                setErrPath("Đường dẫn không được để trống!");
                check += 1;
            } else {
                setErrPath("");
            }
            if (check === 0) {
                let data = {
                    idEvent: id,
                    name: name,
                    path: path,
                    description: '',
                    evidenceType: selectedOption
                };
                OREventDetailApi.createEvidence(data).then(
                    (res) => {
                        onCancel();
                        getEvidences();
                        message.success("Thêm thành công");
                        setName('');
                        setPath('');
                        setSelectedOption(0);
                        setFileList([]);
                    },
                    (err) => {
                        message.error("Thêm thất bại")
                       //  //console.log(err.message);
                    }
                );
            }
        } else {
            let check = 0;
            if (fileList.length === 0) {
                setErrImg("Bạn chưa có hình ảnh nào!");
                check += 1;
            } else {
                setErrName("");
            }
            if (check === 0) {
                const filesData = fileList.map((file) => file.originFileObj);
                const formData = new FormData();
                for (const fileData of filesData) {
                    formData.append("file", fileData);
                }
                OREventDetailApi.addEvidenceImg(formData, id).then(
                    (res) => {
                        onCancel();
                        getEvidences();
                        message.success("Thêm thành công");
                        setName('');
                        setPath('');
                        setSelectedOption(0);
                        setFileList([]);
                    },
                    (err) => {
                        message.error("Thêm thất bại")
                       //  //console.log(err.message);
                    }
                );
            };
        }
    }

    //Cancel evidence
    const handleCancelModal = () => {
        onCancel();
        setName('');
        setPath('');
        setSelectedOption(0);
        setFileList([]);
        setErrImg('')
        setErrName('')
        setErrPath('')
    }

    return (
        <>
            <Modal
                visible={visible}
                onCancel={onCancel}
                closable={false}
                footer={[
                    <Button
                        type="primary"
                        className="btn-form-event"
                        onClick={() => handleAdd()}
                    >
                        Thêm
                        <FontAwesomeIcon
                            icon={faPlus}
                            style={{ color: "#ffffff", marginLeft: "7px" }}
                        />
                    </Button>,
                    <Button
                        style={{ backgroundColor: "red", color: "white" }}
                        key="back"
                        onClick={() => handleCancelModal()}
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
                        Thêm evidence
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
                        autoComplete="off">
                        <Form.Item label="Thể loại" rules={[{ required: true }]}>
                            <Radio.Group onChange={(e) => {
                                setSelectedOption(e.target.value);
                            }} value={selectedOption}>
                                <Radio value={0}>Đường Link</Radio>
                                <Radio value={1}>Ảnh</Radio>
                            </Radio.Group>
                        </Form.Item>
                        {selectedOption === 0 ? (
                            <>
                                <Form.Item
                                    label="Tên evidence"
                                    rules={[{ required: true }]}
                                >
                                    <Input
                                        placeholder="Nhập vào tên Evidence"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                    />
                                    <span style={{ color: "red" }}>{errName}</span>
                                </Form.Item>
                                <Form.Item label="Đường dẫn" >
                                    <Input
                                        placeholder="Nhập vào đường dẫn"
                                        value={path}
                                        onChange={(e) => setPath(e.target.value)}
                                    />
                                    <span style={{ color: "red" }}>{errPath}</span>
                                </Form.Item>
                            </>
                        ) : (
                            <>
                                <Form.Item label="Hình ảnh" >
                                    <Upload
                                        customRequest={() => { }}
                                        beforeUpload={beforeUpload}
                                        fileList={fileList}
                                        listType="picture-card"
                                        showUploadList={{ showRemoveIcon: true }}
                                        onPreview={handlePreview}
                                        onChange={handleChange}
                                    >
                                        <div>
                                            <UploadOutlined />
                                            <div
                                                style={{
                                                    marginTop: 8,
                                                }}
                                            >
                                                Upload
                                            </div>
                                        </div>
                                    </Upload>
                                    <span style={{ color: "red" }}>{errImg}</span>
                                </Form.Item>
                            </>
                        )}
                    </Form>

                    <Modal
                        open={previewOpen}
                        title={previewTitle}
                        footer={null}
                        onCancel={handleCancel}
                    >
                        <img alt="example" style={{ width: "100%" }} src={previewImage} />
                    </Modal>
                </div>
            </Modal>

        </>
    );
}

export default ModalAddEvidence;
