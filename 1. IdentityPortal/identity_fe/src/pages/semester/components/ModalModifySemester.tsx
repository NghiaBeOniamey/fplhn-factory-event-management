import { Button, DatePicker, Form, FormInstance, Input, Modal } from "antd";
import { useModifySemester } from "../hooks/useModifySemester";

const { RangePicker } = DatePicker;

const ModalModifySemester = ({
  isOpen,
  handleCloseModal,
  semesterId,
}: {
  isOpen: boolean;
  handleCloseModal: () => void;
  semesterId?: number;
}) => {
  const {
    form,
    isLoading,
    handleCreateSemester,
    handleTimeChange,
    handleUpdateSemester,
  } = useModifySemester(semesterId);

  return (
    <Modal
      title={semesterId ? "Cập nhật học kỳ" : "Thêm học kỳ"}
      open={isOpen}
      onCancel={handleCloseModal}
      footer={null}
      destroyOnClose
      forceRender
      centered
    >
      <Form
        form={form as FormInstance}
        layout='vertical'
        onFinish={() => {
          Modal.confirm({
            title: "Xác nhận",
            content: "Bạn có chắc chắn muốn thêm học kỳ này không?",
            okText: "Xác nhận",
            cancelText: "Hủy",
            okButtonProps: {
              type: "primary",
              className: "bg-[#052C65]",
            },
            cancelButtonProps: {
              type: "default",
            },
            centered: true,
            onOk: async () => {
              if (semesterId) {
                await handleUpdateSemester();
              } else {
                await handleCreateSemester();
              }
              handleCloseModal();
            },
          });
        }}
      >
        <Form.Item
          label='Tên học kỳ'
          name='name'
          rules={[{ required: true, message: "Vui lòng nhập tên học kỳ" }]}
        >
          <Input placeholder='Nhập tên học kỳ' />
        </Form.Item>
        <Form.Item
          label='Thời gian bắt đầu học kỳ'
          name='startTime'
          rules={[
            { required: true, message: "Vui lòng nhập thời gian bắt đầu" },
          ]}
        >
          <DatePicker
            className='w-[100%]'
            onChange={handleTimeChange}
            placeholder='Thời gian bắt đầu'
          />
        </Form.Item>

        <Form.Item
          label='Thời gian kết thúc học kỳ'
          name='endTime'
          rules={[
            { required: true, message: "Vui lòng nhập thời gian kết thúc" },
          ]}
        >
          <DatePicker
            className='w-[100%]'
            onChange={handleTimeChange}
            placeholder='Thời gian kết thúc'
          />
        </Form.Item>
        <Form.Item
          label='Thời gian block 1'
          name='timeFirstBlock'
          rules={[
            { required: true, message: "Vui lòng nhập thời gian của block 1" },
          ]}
        >
          <RangePicker
            className='w-[100%]'
            onChange={handleTimeChange}
            placeholder={["Thời gian bắt đầu", "Thời gian kết thúc"]}
          />
        </Form.Item>
        <Form.Item
          label='Thời gian block 2'
          name='timeSecondBlock'
          rules={[
            {
              required: true,
              message: "Vui lòng nhập thời gian bắt đầu block 2",
            },
          ]}
        >
          <RangePicker
            className='w-[100%]'
            readOnly
            placeholder={["Thời gian bắt đầu", "Thời gian kết thúc"]}
          />
        </Form.Item>
        <Form.Item className='text-center mt-5'>
          <Button
            type='primary'
            htmlType='submit'
            color='#052C65'
            className='w-[100%] bg-[#052C65]'
            loading={isLoading}
          >
            {semesterId ? "Cập nhật" : "Thêm"}
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ModalModifySemester;
