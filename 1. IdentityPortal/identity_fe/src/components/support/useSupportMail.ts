import { openOrCloseModal } from "#context/redux/slice/ModalSlice";
import { sendSupportMail } from "#service/no-permission";
import { Form } from "antd";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { toast } from "react-toastify";

export const useSupportMail = () => {
  const dispatch = useDispatch();

  const [form] = Form.useForm();

  const [loadingSendMail, setLoadingSendMail] = useState(false);

  const resetMailObject = () => {
    form.resetFields();
  };

  const handleSendMail = async () => {
    const values = await form.validateFields();

    if (!values.subject || !values.body) return;
    if (values?.file && values?.file?.length > 0) {
      const file = values.file[0];
      const fileType = file.type;
      if (!fileType.includes("image")) {
        toast.error("File đính kèm không hợp lệ");
        return;
      }
      if (file.size > 5 * 1024 * 1024) {
        toast.error("File đính kèm không được vượt quá 5MB");
        return;
      }
    }
    setLoadingSendMail(true);
    try {
      await sendSupportMail({
        subject: values.subject as string,
        body: values.body as string,
        file: values.file?.length > 0 ? values.file[0] : null,
        moduleAddress: "Quản lý Phân Quyền Bộ Môn Ứng Dụng Phần Mềm",
      });
      toast.success("Gửi Thông Tin Thành Công");
      resetMailObject();
      dispatch(openOrCloseModal());
    } catch (e) {
      toast.error("Gửi Thông Tin Thất Bại ! Vui lòng thử lại sau");
    } finally {
      setLoadingSendMail(false);
    }
  };

  useEffect(() => {
    return () => {
      if (!form) return;
      form.resetFields();
    };
  }, [form]);

  return {
    form,
    loadingSendMail,
    resetMailObject,
    handleSendMail,
  };
};
