import { Button, Form, FormInstance, Input, Modal } from "antd";
import { memo } from "react";

export type MajorAddUpdateField = {
  majorCode: string;
  majorName: string;
  departmentId: number;
};

const MajorAddUpdate = ({
  form,
  open,
  handleClose,
  majorId,
  handleUpdate,
  handleAdd,
}: {
  form: FormInstance<MajorAddUpdateField>;
  open: boolean;
  handleClose: () => void;
  majorId: number | null;
  handleUpdate: (majorId: number) => void;
  handleAdd: () => void;
}) => {
  return (
    <Modal
      title={majorId ? "Cập nhật chuyên ngành" : "Thêm mới chuyên ngành"}
      open={open}
      onCancel={handleClose}
      footer={false}
      centered
      destroyOnClose
      width={800}
    >
      <Form
        form={form}
        layout='vertical'
        name='major'
        onFinish={() => {
          Modal.confirm({
            title: majorId
              ? "Bạn có chắc chắn muốn cập nhật chuyên ngành này?"
              : "Bạn có chắc chắn muốn thêm mới chuyên ngành này?",
            centered: true,
            onOk: () => {
              if (majorId) {
                handleUpdate(majorId);
              } else {
                handleAdd();
              }
            },
            okButtonProps: {
              className: "bg-[#052C65] text-white",
            },
            cancelButtonProps: {
              className: "border-[#052C65] text-[#052C65]",
            },
            cancelText: "Hủy",
            okText: "Xác nhận",
          });
        }}
      >
        <Form.Item
          label='Mã ngành'
          name='majorCode'
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã chuyên ngành!",
            },
          ]}
        >
          <Input placeholder='Ví dụ: CNTT' />
        </Form.Item>
        <Form.Item
          label='Tên ngành'
          name='majorName'
          rules={[
            {
              required: true,
              message: "Vui lòng nhập tên chuyên ngành!",
            },
          ]}
        >
          <Input placeholder='Ví dụ: Công nghệ thông tin' />
        </Form.Item>
        <Form.Item className='flex justify-end'>
          <Button
            type='primary'
            htmlType='submit'
            className='bg-[#052C65] text-[white]'
          >
            {majorId ? "Cập nhật" : "Thêm mới"}
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default memo(MajorAddUpdate);
