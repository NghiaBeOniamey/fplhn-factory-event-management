import { useHandlePersonalModify } from "#components/ModalPersonalInfo/useHandlePersonalModify";
import useToggle from "#hooks/useToggle";
import { Campus, Department } from "#type/index.t";
import { InfoCircleOutlined } from "@ant-design/icons";
import { Button, Form, Input, Modal, Popconfirm, Select, Spin } from "antd";
import { id } from "date-fns/locale";

const ModalPersonalInfo = ({
  isOpen,
  onClosePersonalInfo,
}: {
  isOpen: boolean;
  onClosePersonalInfo: () => void;
}) => {
  const {
    searchSelect,
    listCampus,
    listDepartment,
    form,
    handleUpdateStaff,
    loading: isLoading,
  } = useHandlePersonalModify();

  const { value: isPopconfirmVisible, setValue: setPopconfirmVisible } =
    useToggle();

  return (
    <Modal
      title='Thông tin tài khoản'
      open={isOpen}
      onCancel={() => {
        onClosePersonalInfo();
        setPopconfirmVisible(false);
      }}
      footer={null}
      forceRender
      destroyOnClose
      zIndex={999999}
      centered
    >
      <Form
        layout='vertical'
        className='space-y-4'
        form={form}
        onFinish={async () => {
          await handleUpdateStaff();
          setPopconfirmVisible(false);
          onClosePersonalInfo();
        }}
      >
        <Form.Item
          name='staffName'
          label='Họ Và Tên'
          rules={[
            {
              required: true,
              message: "Vui lòng nhập họ và tên",
            },
          ]}
        >
          <Input placeholder='Họ và tên' />
        </Form.Item>
        <Form.Item
          name='staffCode'
          label='Mã Nhân Viên'
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã nhân viên",
            },
          ]}
        >
          <Input placeholder='Mã nhân viên' disabled={!!id} />
        </Form.Item>
        <Form.Item
          name='phoneNumber'
          label='Số Điện Thoại'
          tooltip={{
            title: "Không bắt buộc nhập số điện thoại",
            icon: <InfoCircleOutlined />,
          }}
        >
          <Input placeholder='Số điện thoại' />
        </Form.Item>
        <Form.Item
          name='emailFpt'
          label='Email FPT'
          // rules={[
          //   {
          //     required: true,
          //     message: "Vui lòng nhập email FPT",
          //   },
          //   {
          //     type: "email",
          //     message: "Email không hợp lệ",
          //   },
          //   {
          //     pattern: new RegExp(/^[a-zA-Z0-9._%+-]+@fpt.edu.vn$/),
          //     message: "Email phải có định dạng @fpt.edu.vn",
          //   },
          // ]}
        >
          <Input placeholder='Email FPT' disabled />
        </Form.Item>
        <Form.Item
          name='emailFe'
          label='Email FE'
          // rules={[
          //   {
          //     required: true,
          //     message: "Vui lòng nhập email FE",
          //   },
          //   {
          //     type: "email",
          //     message: "Email không hợp lệ",
          //   },
          //   {
          //     pattern: new RegExp(/^[a-zA-Z0-9._%+-]+@fe.edu.vn$/),
          //     message: "Email phải có định dạng @fe.edu.vn",
          //   },
          // ]}
        >
          <Input placeholder='Email FE' disabled />
        </Form.Item>
        <Form.Item
          name='departmentId'
          label='Bộ Môn'
          tooltip={{
            title: "Không bắt buộc chọn bộ môn",
            icon: <InfoCircleOutlined />,
          }}
        >
          <Select
            placeholder='Chọn bộ môn'
            showSearch
            optionFilterProp='children'
            filterOption={searchSelect}
            options={listDepartment.map((department: Department) => ({
              value: department.id,
              label: department.name,
            }))}
            disabled
          />
        </Form.Item>
        <Form.Item
          name='campusId'
          label='Cơ Sở'
          tooltip={{
            title: "Không bắt buộc chọn cơ sở",
            icon: <InfoCircleOutlined />,
          }}
        >
          <Select
            placeholder='Chọn cơ sở'
            showSearch
            optionFilterProp='children'
            filterOption={searchSelect}
            options={listCampus.map((campus: Campus) => ({
              value: campus.id,
              label: campus.name,
            }))}
            // disabled={!permissions.includes(PERMISSIONS.ADMIN)}
            disabled
          />
        </Form.Item>
        <Form.Item>
          <Popconfirm
            title='Bạn có chắc chắn muốn cập nhật thông tin không?'
            onConfirm={form.submit}
            onCancel={() => {
              setPopconfirmVisible(false);
            }}
            okText='Có'
            cancelText='Không'
            open={isPopconfirmVisible}
            zIndex={999999}
          >
            <Button
              type='primary'
              className='w-full bg-[#052C65] text-white py-2 rounded-md flex justify-center items-center'
              onClick={() => setPopconfirmVisible(true)}
            >
              <span>
                {isLoading ? (
                  <span className='flex justify-center items-center'>
                    <span className='mr-2'>Đang cập nhật</span>
                    <Spin size='small' />
                  </span>
                ) : (
                  "Cập nhật"
                )}
              </span>
            </Button>
          </Popconfirm>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ModalPersonalInfo;
