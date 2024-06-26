import React, {useEffect, useState} from 'react';
import {Modal, Upload, Button, } from 'antd';
import {UploadOutlined} from '@ant-design/icons';
import {PAHomeApi} from "../PAHomeApi";

/**
 * UploadModal: Modal upload ảnh. có thể tạo 1 component với cách xử lý tương tựS
 * @returns {JSX.Element}
 * @constructor
 */
const UploadModal = () => {
    const [visible, setVisible] = useState(false);
    const [selectedImage, setSelectedImage] = useState(null);
    // Biến check xem sự kiện trong đã tồn tại ảnh hay chưa
    const [isExisted, setIsExisted] = useState(false);
    const [responseUrl, setResponseUrl] = useState('');
    const [event, setEvent] = useState({
        id: "",
        name: "",
        background: null
    })

    const showModal = () => {
        setVisible(true);
    };

    useEffect(() => {
        if (responseUrl === '') {
            setIsExisted(false)
        } else {
            setIsExisted(true);
        }
    }, [isExisted]);

    // Call API để lưu ảnh
    const handleSaveImage = async () => {
        const image = new FormData();
        image.append('file', selectedImage);
        await PAHomeApi.uploadImage(image).then((res) => {
            setResponseUrl(res.data.data);
           //  //console.log(res.data.data);
            alert('Upload thành công');
        }).catch((err) => {
           //  //console.log(err);
            alert('Upload thất bại');
            setResponseUrl('');
        });
    };

    const handleCancel = () => {
        setVisible(false);
    };

    const deleteImage = () => {
        PAHomeApi.deleteImage('UUID_cua_object').then((res) => {
            if (res.data.data === true) {
                alert('Xóa thành công');
            }
        }).catch((err) => {
           //  //console.log(err);
            alert('Xóa thất bại');
        })
    }

    //Hàm set ảnh được chọn vào state và hiển thị ảnh khi người dùng đã chọn ảnh nhưng chưa ấn lưu
    const handleImageUpload = (info) => {
        if (info.file.status === 'uploading') {
           //  //console.log(info.file.originFileObj);
            setSelectedImage(info.file.originFileObj);
        }
    };

    return (
        <>
            <Button type="primary" onClick={showModal} icon={<UploadOutlined/>}>
                Upload
            </Button>
            <Button type="primary" onClick={deleteImage}>
                Xóa ảnh
            </Button>
            <Modal
                title="Tải ảnh lên"
                visible={visible}
                onOk={handleSaveImage}
                onCancel={handleCancel}
            >
                <Upload
                    customRequest={() => {
                    }}
                    showUploadList={false}
                    onChange={handleImageUpload}
                >
                    <Button icon={<UploadOutlined/>}>Chọn ảnh</Button>
                </Upload>
                {isExisted && (
                    <div>
                        <h4>Ảnh đã lưu:</h4>
                        <img src={responseUrl} alt="Ảnh đã lưu" style={{maxWidth: "100%", borderRadius: "8px"}}/>
                    </div>
                )}
                {selectedImage && !isExisted && (
                    <div>
                        <h4>Ảnh đã chọn:</h4>
                        <img
                            src={URL.createObjectURL(selectedImage)}
                            alt="Selected"
                            style={{maxWidth: "100%", borderRadius: "8px"}}
                        />
                    </div>
                )}
            </Modal>
        </>
    );
}

export default UploadModal;
