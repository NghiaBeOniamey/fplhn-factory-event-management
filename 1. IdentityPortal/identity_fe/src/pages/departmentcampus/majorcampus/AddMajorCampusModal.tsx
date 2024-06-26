import { Button, Form, FormInstance, Modal, Select } from "antd";
import { useStaffMajorInfor } from "../hook/useStaffMajorInfor";

export type MajorAddUpdateField = {
  majorId: number;
  headMajorId: number;
  departmentCampusId: number;
};

const AddMajorCampusModal = ({
  form,
  open,
  handleClose,
  majorCampusId,
  handleUpdate,
  handleAdd,
}: {
  form: FormInstance<MajorAddUpdateField>;
  open: boolean;
  handleClose: () => void;
  majorCampusId: number | null | undefined;
  handleUpdate: (majorId: number) => void;
  handleAdd: () => void;
}) => {
  const { majors, staffs } = useStaffMajorInfor();

  const filterOption = (
    input: string,
    option?: { label: string; value: number }
  ) => (option?.label ?? "").toLowerCase().includes(input.toLowerCase());

  return (
    <Modal
      title={majorCampusId ? "Cập nhật Chuyên Ngành" : "Thêm mới Chuyên Ngành"}
      open={open}
      onCancel={handleClose}
      footer={false}
      destroyOnClose
      width={800}
      centered
    >
      <Form
        form={form}
        layout='vertical'
        name='majorCampus'
        onFinish={() => {
          Modal.confirm({
            title: "Xác nhận",
            centered: true,
            content: majorCampusId
              ? "Bạn có muốn cập nhật chuyên ngành theo cơ sở này không?"
              : "Bạn có muốn thêm mới chuyên ngành theo cơ sở này không?",
            okText: "Xác nhận",
            cancelText: "Hủy",
            okButtonProps: {
              style: {
                color: "white",
                backgroundColor: "#052C65",
              },
            },
            cancelButtonProps: {
              style: {
                color: "white",
                backgroundColor: "#ccc",
              },
            },
            onOk: () => {
              if (majorCampusId) {
                handleUpdate(majorCampusId);
              } else {
                handleAdd();
              }
            },
          });
        }}
        initialValues={{ remember: false }}
      >
        <Form.Item
          label='Chuyên Ngành'
          name='majorId'
          rules={[
            {
              required: true,
              message: "Vui lòng chọn chuyên ngành!",
            },
          ]}
        >
          <Select
            placeholder='Chọn Chuyên Ngành'
            style={{ width: "100%" }}
            showSearch
            optionFilterProp='children'
            allowClear
            filterOption={filterOption}
            options={majors}
          />
        </Form.Item>
        <Form.Item
          label='Trưởng Môn Chuyên Ngành'
          name='headMajorId'
          rules={[
            {
              required: true,
              message: "Vui lòng chọn trưởng môn!",
            },
          ]}
        >
          <Select
            placeholder='Chọn Trưởng Môn'
            style={{ width: "100%" }}
            showSearch
            optionFilterProp='children'
            allowClear
            filterOption={filterOption}
            options={staffs}
          />
        </Form.Item>
        <Form.Item className='flex justify-end'>
          <Button
            type='primary'
            htmlType='submit'
            style={{ color: "white", backgroundColor: "#052C65" }}
            className='m-2'
          >
            {majorCampusId ? "Cập nhật" : "Thêm mới"}
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default AddMajorCampusModal;
