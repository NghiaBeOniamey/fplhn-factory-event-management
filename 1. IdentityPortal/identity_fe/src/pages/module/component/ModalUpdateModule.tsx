import FormCustom from "#components/ui/FormCustom";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { useUpdateModule } from "#service/action/module.action";
import { InfoCircleOutlined } from "@ant-design/icons";
import { faBuildingWheat } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, Input, Modal } from "antd";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";

const ModalUpdateModule = ({
  isModalUpdateModuleOpen,
  setIsModalUpdateModuleOpen,
  dataUpdate,
}: {
  isModalUpdateModuleOpen: boolean;
  setIsModalUpdateModuleOpen: (isOpen: boolean) => void;
  dataUpdate: any;
}) => {
  const [form] = Form.useForm();

  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const { mutateAsync: updateModule, isLoading: loading } = useUpdateModule();

  const handleCloseUpdateModuleOpen = () => {
    setIsModalUpdateModuleOpen(false);
    form.resetFields();
  };

  const handleUpdateModule = async () => {
    try {
      await form.validateFields();
      const formValue = await form.getFieldsValue();
      const res = await updateModule({
        id: dataUpdate.moduleId,
        data: {
          moduleName: formValue.moduleName,
          moduleCode: formValue.moduleCode,
          moduleUrl: formValue.moduleUrl,
          redirectRoute: formValue.redirectRoute,
        },
      });
      if (res?.success) {
        toast.success(res?.message);
        handleCloseUpdateModuleOpen();
      } else {
        toast.error(res?.message);
      }
    } catch (error) {
      toast.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  useEffect(() => {
    if (isModalUpdateModuleOpen) {
      form.setFieldsValue({
        moduleName: dataUpdate?.moduleName,
        moduleCode: dataUpdate?.moduleCode,
        moduleUrl: dataUpdate?.moduleUrl,
        redirectRoute: dataUpdate?.redirectRoute,
      });
    }
  }, [
    dataUpdate?.moduleCode,
    dataUpdate?.moduleName,
    dataUpdate?.moduleUrl,
    dataUpdate?.redirectRoute,
    form,
    isModalUpdateModuleOpen,
  ]);

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3
            className='flex gap-2 items-center'
            style={{
              color: "#052C65",
            }}
          >
            <FontAwesomeIcon icon={faBuildingWheat} />
            Chỉnh sửa thông tin mô-đun
          </h3>
        }
        open={isModalUpdateModuleOpen}
        onCancel={handleCloseUpdateModuleOpen}
        width={"50vw"}
        footer={null}
      >
        <div className='shadow-lg p-4 rounded-lg mt-4'>
          <FormCustom
            form={form}
            isButtonDisabled={isButtonDisabled}
            setIsButtonDisabled={setIsButtonDisabled}
            handleOk={handleUpdateModule}
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
                  <Input size={"large"} placeholder={"Nhập vào URL"} />
                </Form.Item>
                <Form.Item
                  label='Link chuyển hướng: '
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

export default ModalUpdateModule;
