import logo from "#assets/image/Logo_FPT.png";
import logoUDPM from "#assets/image/logo-udpm-dark.png";
import HashLoading from "#components/ui/HashLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import {
  useGetAllListCampus,
  useGetAllListDepartment,
  useGetDetailStudent,
  useUpdateStudent,
} from "#service/action/student.action";
import { switchModule } from "#service/api/auth.api";
import { Button, Form, Input, Modal, Select, message } from "antd";
import { useEffect, useMemo, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const StudentLanding = () => {
  const navigation = useNavigate();

  const [form] = Form.useForm();

  const [waitRedirect, setWaitRedirect] = useState(false);

  const { userInfo, token } = useSelector(
    (state: RootState) => state.auth.authorization
  );

  const {
    data: studentDetail,
    isLoading: loadingStudentDetail,
    isFetching,
  } = useGetDetailStudent(userInfo?.userId);

  const { data: dataCampus } = useGetAllListCampus();

  const { data: dataDepartment } = useGetAllListDepartment();

  const departmentList = useMemo(() => {
    if (!dataDepartment) return [];
    return dataDepartment?.data?.map((item) => ({
      label: item.name,
      value: item.id,
    }));
  }, [dataDepartment]);

  const campusList = useMemo(() => {
    if (!dataCampus) return [];
    return dataCampus?.data?.map((item) => ({
      label: item.name,
      value: item.id,
    }));
  }, [dataCampus]);

  const { mutateAsync: updateStudent, isLoading } = useUpdateStudent();

  useEffect(() => {
    if (!userInfo?.userId) navigation("/not-authentication");
  }, [userInfo?.userId, navigation]);

  useEffect(() => {
    if (studentDetail) {
      if (studentDetail?.data?.campusId && studentDetail?.data?.departmentId) {
        setWaitRedirect(true);
        sendRedirectToServer({
          userId: userInfo?.userId,
          identityToken: token,
          moduleCode: "QLSKBMUDPM",
        });
      } else {
        form.setFieldsValue({
          studentName: studentDetail?.data?.studentName,
          studentCode: studentDetail?.data?.studentCode,
          departmentId: studentDetail?.data?.departmentId,
          campusId: studentDetail?.data?.campusId,
        });
      }
    }
  }, [form, studentDetail, token, userInfo?.userId]);

  const handleUpdateStudent = async () => {
    try {
      const res = await updateStudent({
        id: userInfo?.userId,
        student: {
          ...form.getFieldsValue(),
          studentMail: userInfo?.email,
        },
      });
      message.success(res?.message);
      setWaitRedirect(true);
      sendRedirectToServer({
        userId: userInfo?.userId,
        identityToken: token,
        moduleCode: "QLSKBMUDPM",
      });
    } catch (error) {
      message.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  if (loadingStudentDetail || isLoading || isFetching || waitRedirect)
    return <HashLoading />;

  return (
    <>
      <div className='my-4 flex justify-center items-center'>
        <img src={logo} alt='Logo' className='mb-2 w-44' />
        <img src={logoUDPM} alt='Logo' className='mb-2 w-48' />
      </div>
      <h2 className='text-lg font-semibold text-center  text-[#052C65]'>
        Cập nhật thông tin sinh viên
      </h2>
      <div
        className='container mx-auto
        bg-white p-5
        shadow-md rounded-md
        mt-5
        w-[80%]
      '
      >
        <Form
          form={form}
          layout='vertical'
          onFinish={() => {
            Modal.confirm({
              centered: true,
              title: "Cập nhật thông tin sinh viên",
              content: "Xác nhận lại thông tin - thông tin không thể thay đổi",
              okButtonProps: { className: "bg-[#052C65]" },
              onOk: () => handleUpdateStudent(),
              footer: (_, { OkBtn, CancelBtn }) => (
                <>
                  <CancelBtn />
                  <OkBtn />
                </>
              ),
            });
          }}
        >
          <Form.Item
            label='Họ và tên'
            name='studentName'
            rules={[
              {
                required: true,
                message: "Họ và tên",
              },
            ]}
          >
            <Input placeholder='VD: Nguyễn Văn A' />
          </Form.Item>
          <Form.Item
            label='Mã sinh viên'
            name='studentCode'
            rules={[
              {
                required: true,
                message: "Mã sinh viên không được để trống",
              },
              {
                pattern: new RegExp(
                  /^(PH|PG|PD|PT|PQ|PN|PB|PK|PL|PV|PS|PC|PM|PF|PX|PY|PZ|PU|PW)\d{5}$/
                ),
                message: "Mã sinh viên không hợp lệ",
              },
            ]}
          >
            <Input placeholder='VD: PH12345' />
          </Form.Item>
          <Form.Item
            label='Bộ môn'
            name='departmentId'
            rules={[
              {
                required: true,
                message: "Chọn bộ môn",
              },
            ]}
            required
          >
            <Select
              placeholder='Ứng dụng phần mềm'
              filterOption={(input, option) =>
                option?.label.toLowerCase().includes(input.toLowerCase())
              }
              filterSort={(optionA, optionB) => {
                return optionA.label.toLowerCase() > optionB.label.toLowerCase()
                  ? 1
                  : -1;
              }}
              showSearch
              options={departmentList}
            />
          </Form.Item>
          <Form.Item
            label='Cơ Sở'
            name='campusId'
            rules={[
              {
                required: true,
                message: "Chọn cơ sở",
              },
            ]}
            required
          >
            <Select
              placeholder='Chọn cơ sở'
              filterOption={(input, option) =>
                option?.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }
              filterSort={(optionA, optionB) => {
                return optionA.label.toLowerCase() > optionB.label.toLowerCase()
                  ? 1
                  : -1;
              }}
              showSearch
              options={campusList}
            />
          </Form.Item>
          <Form.Item>
            <Button
              type='primary'
              htmlType='submit'
              className='mt-5 w-full bg-[#052C65]'
            >
              Xác nhận
            </Button>
          </Form.Item>
        </Form>
      </div>
    </>
  );
};

const sendRedirectToServer = ({
  userId,
  identityToken,
  moduleCode,
}: {
  userId: number;
  identityToken: string;
  moduleCode: string;
}) => {
  return switchModule({
    moduleCode,
    userId,
    userType: "SINH_VIEN",
    identityToken,
  });
};

export default StudentLanding;
