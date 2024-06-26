import { faBuildingWheat } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, Modal } from "antd";
import { memo, useState } from "react";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { useCreateCampus } from "#service/action/campus.action";
import { showToast } from "#utils/common.helper";
import FormCustom from "#components/ui/FormCustom";
import { HTTP_STATUS } from "#constant/index";

export interface ModalAddCampusProps {
  isModalAddCoSoOpen: boolean;
  setIsModalAddCoSoOpen: (value: boolean) => void;
}

const ModalAddCampus = (props: ModalAddCampusProps) => {
  const [form] = Form.useForm();

  const { isModalAddCoSoOpen, setIsModalAddCoSoOpen } = props;

  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const { mutateAsync: createCampus, isLoading: loading } = useCreateCampus();

  const handleCloseAddCoSoOpen = () => {
    setIsModalAddCoSoOpen(false);
    form.resetFields();
    setIsButtonDisabled(false);
  };

  const handleCreateCampus = async () => {
    try {
      const formValue = await form.getFieldsValue();

      const res = await createCampus({
        campusName: formValue.campusName,
        campusCode: formValue.campusCode,
      });

      if (res.status === HTTP_STATUS.OK) {
        showToast("success", res.message);
        handleCloseAddCoSoOpen();
      } else {
        showToast("error", res.message);
      }
    } catch (error) {
      if (error.code && error.code === "ERR_BAD_REQUEST") {
        showToast("error", error?.response?.data?.message);
      }
    }
  };

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3 className='flex gap-2 items-center text-2xl text-[#052C65] font-semibold'>
            <FontAwesomeIcon icon={faBuildingWheat} />
            Thêm cơ sở
          </h3>
        }
        open={isModalAddCoSoOpen}
        onCancel={handleCloseAddCoSoOpen}
        width='50vw'
        footer={null}
      >
        <div className='shadow-lg p-4 rounded-lg mt-4'>
          <FormCustom
            form={form}
            isButtonDisabled={isButtonDisabled}
            setIsButtonDisabled={setIsButtonDisabled}
            handleOk={handleCreateCampus}
            titleTooltip='Thêm cơ sở'
            titlePopconfirm='Thông báo'
            contentPopconfirm='Bạn có muốn thêm cơ sở mới không?'
            placeholderInput='Nhập tên cơ sở'
            sizeInput='large'
            nameFormInput='campusName'
            codeFormInput='campusCode'
            sizeButton='large'
            typeButton='primary'
          />
        </div>
      </Modal>
    </>
  );
};

export default memo(ModalAddCampus);
