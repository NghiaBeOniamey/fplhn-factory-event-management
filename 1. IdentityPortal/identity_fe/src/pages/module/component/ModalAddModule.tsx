import FormCustom from "#components/ui/FormCustom";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { useCreateModule } from "#service/action/module.action";
import { InfoCircleOutlined } from "@ant-design/icons";
import { faBuildingWheat } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, Input, Modal } from "antd";
import { useState } from "react";
import { toast } from "react-toastify";

export interface ModalAddModuleProps {
  isModalAddModuleOpen: boolean;
  setIsModalAddModuleOpen: (isOpen: boolean) => void;
}

const ModalAddModule = (props: ModalAddModuleProps) => {
  const [form] = Form.useForm();

  const { mutateAsync: createModule, isLoading: loading } = useCreateModule();

  const { isModalAddModuleOpen, setIsModalAddModuleOpen } = props;

  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const handleCloseAddModuleOpen = () => {
    setIsModalAddModuleOpen(false);
    form.resetFields();
    setIsButtonDisabled(false);
  };

  const handleAddModule = async () => {
    try {
      await form.validateFields();
      const formValue = await form.getFieldsValue();
      const res = await createModule(formValue);
      if (res?.success) {
        toast.success(res?.message);
        handleCloseAddModuleOpen();
      } else {
        toast.error(res?.message);
      }
    } catch (error) {
      toast.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3
            className='d-flex gap-2 align-items-center'
            style={{
              color: "#052C65",
            }}
          >
            <FontAwesomeIcon icon={faBuildingWheat} />
            Thêm mô-đun
          </h3>
        }
        open={isModalAddModuleOpen}
        onCancel={handleCloseAddModuleOpen}
        width='50vw'
        okText='Hoàn tất'
        cancelText='Quay lại'
        footer={null}
      >
        <div className='shadow-lg p-4 rounded-lg mt-4'>
          <FormCustom
            form={form}
            isButtonDisabled={isButtonDisabled}
            setIsButtonDisabled={setIsButtonDisabled}
            handleOk={handleAddModule}
            titleTooltip='Thêm mô-đun'
            titlePopconfirm='Thông báo'
            contentPopconfirm='Bạn có muốn thêm mô-đun mới không?'
            placeholderInput='Nhập tên mô-đun'
            sizeInput='large'
            nameFormInput='moduleName'
            codeFormInput='moduleCode'
            sizeButton='large'
            typeButton='primary'
            anotherFormItem={
              <>
                <Form.Item
                  label='Domain Module'
                  name='moduleUrl'
                  rules={[
                    {
                      validator: (_, value) => {
                        if (value && value.trim().length <= 0) {
                          return Promise.reject(
                            new Error(
                              "Trường không được để trống hoặc khoảng trắng không"
                            )
                          );
                        } else if (value && value.trim().length >= 255) {
                          return Promise.reject(
                            new Error("Trường không được quá 255 ký tự")
                          );
                        } else if (
                          value &&
                          !/^(http|https):\/\/[^ "]+$/.test(value)
                        ) {
                          return Promise.reject(
                            new Error("Trường phải là URL hợp lệ")
                          );
                        } else if (value && value.trim().endsWith("/")) {
                          return Promise.reject(
                            new Error("Trường không được kết thúc bằng dấu /")
                          );
                        } else {
                          return Promise.resolve();
                        }
                      },
                    },
                  ]}
                  required={true}
                >
                  <Input size='large' placeholder='Nhập vào URL' />
                </Form.Item>
                <Form.Item
                  label='Link chuyển hướng'
                  name='redirectRoute'
                  tooltip={{
                    title:
                      "Link chuyển hướng sẽ được sử dụng khi người dùng truy cập vào mô-đun này, nếu không nhập sẽ mặc định chuyển hướng về domain module",
                    icon: <InfoCircleOutlined />,
                  }}
                  rules={[
                    {
                      validator: (_, value) => {
                        if (value && value.trim().length <= 0) {
                          return Promise.reject(
                            new Error(
                              "Trường không được để trống hoặc khoảng trắng không"
                            )
                          );
                        } else if (value && value.trim().length >= 255) {
                          return Promise.reject(
                            new Error("Trường không được quá 255 ký tự")
                          );
                        } else if (value && value.trim().includes("/")) {
                          return Promise.reject(
                            new Error("Trường không được chứa dấu /")
                          );
                        } else {
                          return Promise.resolve();
                        }
                      },
                    },
                  ]}
                  required={false}
                >
                  <Input
                    size='large'
                    placeholder='Nhập vào link chuyển hướng'
                  />
                </Form.Item>
              </>
            }
          />
        </div>
      </Modal>
    </>
  );
};

export default ModalAddModule;
