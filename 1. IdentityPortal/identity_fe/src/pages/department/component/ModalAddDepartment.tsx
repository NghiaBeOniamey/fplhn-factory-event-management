import { faBookOpen } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, message, Modal } from "antd";
import { memo, useCallback, useState } from "react";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { useCreateDepartment } from "#service/action/department.action";
import FormCustom from "#components/ui/FormCustom";

const ModalAddDepartment = ({
  isModalAddBoMonOpen,
  setIsModalAddBoMonOpen,
}: {
  isModalAddBoMonOpen: boolean;
  setIsModalAddBoMonOpen: (value: boolean) => void;
}) => {
  const [form] = Form.useForm();
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);
  const { mutateAsync: createDepartment, isLoading: loading } =
    useCreateDepartment();

  const handleCloseAddBoMonOpen = useCallback(() => {
    setIsModalAddBoMonOpen(false);
    form.resetFields();
    setIsButtonDisabled(false);
  }, [setIsModalAddBoMonOpen, form, setIsButtonDisabled]);

  const handleCreateDepartment = useCallback(async () => {
    try {
      const formValue = await form.getFieldsValue();
      const res = await createDepartment({
        departmentName: formValue.departmentName,
        departmentCode: formValue.departmentCode,
      });
      if (res.success) {
        message.success("Thêm bộ môn thành công");
        handleCloseAddBoMonOpen();
      } else {
        message.error(res.message);
      }
      handleCloseAddBoMonOpen();
    } catch (error) {
      message.error(error?.response?.data?.message || "Thêm bộ môn thất bại");
    }
  }, [form, createDepartment, handleCloseAddBoMonOpen]);

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3 className='flex gap-2 items-center'>
            <FontAwesomeIcon
              icon={faBookOpen}
              className='text-xl text-[#052C65]'
            />
            <span className='text-xl text-[#052C65]'>Thêm bộ môn</span>
          </h3>
        }
        open={isModalAddBoMonOpen}
        onCancel={handleCloseAddBoMonOpen}
        width='50vw'
        footer={null}
      >
        <div className='shadow-lg p-4 rounded-lg mt-4'>
          <FormCustom
            form={form}
            isButtonDisabled={isButtonDisabled}
            setIsButtonDisabled={setIsButtonDisabled}
            handleOk={handleCreateDepartment}
            titleTooltip='Thêm bộ môn'
            titlePopconfirm='Thông báo'
            contentPopconfirm='Bạn có muốn thêm bộ môn mới không?'
            placeholderInput='Nhập tên bộ môn'
            sizeInput='large'
            nameFormInput='departmentName'
            codeFormInput='departmentCode'
            sizeButton='large'
            typeButton='primary'
          />
        </div>
      </Modal>
    </>
  );
};

export default memo(ModalAddDepartment);
