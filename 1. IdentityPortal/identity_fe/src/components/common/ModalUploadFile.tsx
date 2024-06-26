import { Button, Form, Modal, Upload, message } from "antd";
import { useLayoutEffect, useState } from "react";

export interface FormInstanceValues {
  uploadFile: File;
}

const ModalUploadFile = ({
  onClose,
  visible,
  uploadFile,
}: {
  onClose: () => void;
  visible: boolean;
  uploadFile: (file: File) => void;
}) => {
  const [form] = Form.useForm<FormInstanceValues>();

  const [fileList, setFileList] = useState([]);

  const handleUpload = async () => {
    if (fileList.length === 0) {
      message.error("Vui lòng chọn file");
      return;
    }
    const file = fileList[0];
    uploadFile(file);
    onClose();
  };

  useLayoutEffect(() => {
    return () => {
      form.resetFields();
      setFileList([]);
    };
  }, [form, visible]);

  return (
    <Modal
      title={false}
      forceRender
      open={visible}
      onCancel={onClose}
      destroyOnClose
      centered
      footer={() => (
        <div className='flex justify-center gap-3'>
          <Button
            key='back'
            onClick={onClose}
            className='bg-orange-400 border-orange-400 hover:bg-orange-400 hover:border-orange-400 text-white'
          >
            Hủy
          </Button>
          <Button
            key='submit'
            type='primary'
            onClick={handleUpload}
            className='bg-[#052C65] border-[#052C65] hover:bg-[#052C65] hover:border-[#052C65]'
          >
            Tải lên
          </Button>
        </div>
      )}
      className='p-5'
    >
      <Form
        layout='vertical'
        initialValues={{ size: "default" }}
        className='w-full h-3/4'
        form={form}
      >
        <Form.Item label='' name='uploadFile' className='w-full'>
          <Upload
            accept='.xlsx'
            multiple={false}
            beforeUpload={(file) => {
              setFileList([file]);
              return false;
            }}
            fileList={fileList}
            maxCount={1}
            className='w-full flex justify-center items-center p-3'
            itemRender={() => null}
          >
            {fileList[0]?.name ? (
              <div className='flex justify-center items-center h-32 w-full border-2 border-dashed border-gray-300 rounded-lg'>
                <p className='text-gray-400 w-full p-2'>
                  {fileList?.map((file) => (
                    <span key={file.uid}>{file.name}</span>
                  ))}
                </p>
              </div>
            ) : (
              <div className='flex justify-center items-center h-32 w-full border-2 border-dashed border-gray-300 rounded-lg'>
                <p className='text-gray-400 w-full p-2'>
                  Ấn để chọn file hoặc kéo thả file vào đây
                </p>
              </div>
            )}
          </Upload>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ModalUploadFile;
