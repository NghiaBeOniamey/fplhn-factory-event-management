import { useSupportMail } from "#components/support/useSupportMail";
import { UploadOutlined } from "@ant-design/icons";
import { Button, Form, Input, Modal, Popconfirm, Upload } from "antd";
import { memo } from "react";
import { toast } from "react-toastify";

type ModalProps = {
  isOpen: boolean;
  onClose: () => void;
};

const buttonSend = ({
  form,
  loadingSendMail,
  handleSendMail,
}: {
  form: any;
  loadingSendMail: boolean;
  handleSendMail: () => void;
}) => {
  return (
    <Popconfirm
      title='Bạn có chắc chắn muốn gửi thông tin này?'
      onConfirm={async () => {
        try {
          const values = await form.validateFields();
          const { subject, body } = values;
          if (!subject || !body) {
            toast.error("Vui lòng điền đầy đủ thông tin");
            return;
          }
          handleSendMail();
        } catch (e) {
          toast.error("Vui lòng điền đầy đủ thông tin");
        }
      }}
      okText='Có'
      cancelText='Không'
    >
      <Button
        type='primary'
        loading={loadingSendMail}
        size='large'
        className='bg-[#253741] text-white'
      >
        Gửi
      </Button>
    </Popconfirm>
  );
};

const ModalSendSupport = (props: ModalProps) => {
  const { form, loadingSendMail, handleSendMail } = useSupportMail();

  return (
    <Modal
      title='Hỗ trợ kỹ thuật'
      open={props.isOpen}
      onCancel={props.onClose}
      footer={buttonSend({ form, loadingSendMail, handleSendMail })}
      centered
      destroyOnClose
      forceRender
    >
      <Form layout='vertical' form={form}>
        <Form.Item
          label='Chủ đề'
          name='subject'
          rules={[{ required: true, message: "Vui lòng điền chủ đề" }]}
        >
          <Input placeholder='Chủ đề' className='mt-[10px]' />
        </Form.Item>
        <Form.Item
          label='Nội dung'
          name='body'
          rules={[{ required: true, message: "Vui lòng điền nội dung" }]}
        >
          <Input.TextArea
            placeholder='Vui lòng mô tả chi tiết vấn đề bạn gặp phải'
            className='mt-[10px]'
            maxLength={500}
            minLength={10}
          />
        </Form.Item>
        <Form.Item
          label='File đính kèm'
          name='file'
          valuePropName='fileList'
          getValueFromEvent={(e: any) => {
            if (Array.isArray(e)) {
              return e;
            }
            return e?.fileList;
          }}
        >
          <Upload
            accept='image/*'
            maxCount={1}
            listType='picture'
            beforeUpload={() => false}
          >
            <Button icon={<UploadOutlined />}>Đính kèm file</Button>
          </Upload>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default memo(ModalSendSupport);
