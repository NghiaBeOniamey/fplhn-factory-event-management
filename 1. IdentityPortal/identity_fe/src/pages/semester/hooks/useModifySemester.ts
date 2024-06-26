import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { Form, message } from "antd";
import dayjs from "dayjs";
import { useEffect } from "react";
import {
  useCreateSemester,
  useGetSemesterDetail,
  useUpdateSemester,
} from "#service/action/semester.action";
import { ModifySemester } from "#type/index.t";

export const useModifySemester = (semesterId?: number) => {
  const [form] = Form.useForm();

  const {
    mutateAsync: createSemester,
    isLoading,
    isError,
  } = useCreateSemester();

  const { mutateAsync: updateSemester } = useUpdateSemester();

  const { data: semesterDetailResponse } = useGetSemesterDetail(semesterId);

  const handleCreateSemester = async () => {
    try {
      const values = await form.validateFields();

      const semester: ModifySemester = {
        name: values.name,
        startTime: values.startTime.unix(),
        endTime: values.endTime.unix(),
        startTimeFirstBlock: values.timeFirstBlock[0].unix(),
        endTimeFirstBlock: values.timeFirstBlock[1].unix(),
        startTimeSecondBlock: values.timeSecondBlock[0].unix(),
        endTimeSecondBlock: values.timeSecondBlock[1].unix(),
      };

      const res = await createSemester(semester);
      if (res?.success) {
        message.success("Thêm học kỳ thành công");
      } else {
        message.error(res?.message || "Thêm học kỳ thất bại");
      }
    } catch (e) {
      message.error(e?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  const handleUpdateSemester = async () => {
    try {
      const values = await form.validateFields();

      const semester: ModifySemester = {
        name: values.name,
        startTime: values.startTime.unix(),
        endTime: values.endTime.unix(),
        startTimeFirstBlock: values.timeFirstBlock[0].unix(),
        endTimeFirstBlock: values.timeFirstBlock[1].unix(),
        startTimeSecondBlock: values.timeSecondBlock[0].unix(),
        endTimeSecondBlock: values.timeSecondBlock[1].unix(),
      };

      const res = await updateSemester({
        id: semesterId,
        semester,
      });
      if (res?.success) {
        message.success("Cập nhật học kỳ thành công");
      } else {
        message.error(res?.message || "Cập nhật học kỳ thất bại");
      }
    } catch (e) {
      message.error(e?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  const handleTimeChange = () => {
    const values = form.getFieldsValue();
    const { startTime, endTime, timeFirstBlock } = values;

    if (startTime && endTime) {
      if (startTime.isAfter(endTime)) {
        message.error("Thời gian bắt đầu không thể sau thời gian kết thúc");
        return;
      }
      if (!timeFirstBlock || !timeFirstBlock[0]) {
        form.setFieldsValue({
          timeFirstBlock: [startTime],
        });
      }
    }

    if (timeFirstBlock) {
      form.setFieldsValue({
        timeSecondBlock: [timeFirstBlock[1], endTime],
      });
    }
  };

  useEffect(() => {
    if (semesterId) {
      const semesterDetail = semesterDetailResponse?.data;
      form.setFieldsValue({
        name: semesterDetail?.semesterName,
        startTime: dayjs.unix(semesterDetail?.startTime),
        endTime: dayjs.unix(semesterDetail?.endTime),
        timeFirstBlock: [
          dayjs.unix(semesterDetail?.startTimeFirstBlock),
          dayjs.unix(semesterDetail?.endTimeFirstBlock),
        ],
        timeSecondBlock: [
          dayjs.unix(semesterDetail?.startTimeSecondBlock),
          dayjs.unix(semesterDetail?.endTimeSecondBlock),
        ],
      });
    }
    return () => {
      form.resetFields();
    };
  }, [form, semesterDetailResponse, semesterId]);

  return {
    form,
    isLoading,
    isError,
    handleCreateSemester,
    handleTimeChange,
    handleUpdateSemester,
  };
};
