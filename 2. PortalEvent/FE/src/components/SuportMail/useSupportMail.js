
import { Form, message } from "antd";
import { useEffect, useState } from "react";
import { sendSupportMail } from "./no-permission";

export const useSupportMail = () => {

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
        message.error("File đính kèm không hợp lệ");
        return;
      }
      if (file.size > 5 * 1024 * 1024) {
        message.error("File đính kèm không được vượt quá 5MB");
        return;
      }
    }
    const data = {
      subject: values.subject,
      body: values.body,
      file: values.file?.length > 0 ? values.file[0] : null,
      moduleAddress: "Quản lý Sự Kiện Bộ Môn Ứng Dụng Phần Mềm Hà Nội",
    };
    setLoadingSendMail(true);
    try {
      await sendSupportMail(data);
      message.success("Gửi thông tin thành công");
      resetMailObject();
    } catch (e) {
      message.error("Gửi thông tin thất bại ! Vui lòng thử lại sau");
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
