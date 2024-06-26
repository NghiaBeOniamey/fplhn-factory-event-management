import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import {
  useCreateStudent,
  useGetAllListCampus,
  useGetAllListDepartment,
} from "#service/action/student.action";
import { faGraduationCap } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, Input, Modal, Select } from "antd";
import { useMemo } from "react";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";

const StudentAddModal = ({
  isOpenAdd,
  onCancelAdd,
}: {
  isOpenAdd: boolean;
  onCancelAdd: () => void;
}) => {
  const campusId = useSelector(
    (state: RootState) => state?.auth?.authorization?.userInfo?.campusId
  );

  const [form] = Form.useForm();

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

  const { mutateAsync: createStudent } = useCreateStudent();

  const handleCreateStudent = async () => {
    try {
      await createStudent({
        ...form.getFieldsValue(),
        campusId,
      });
      toast.success("Thêm sinh viên thành công !");
      form.resetFields();
      onCancelAdd();
    } catch (error) {
      toast.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  const filterOption = (input, option) => {
    return (
      option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0 ||
      option.value.toLowerCase().indexOf(input.toLowerCase()) >= 0
    );
  };

  const defaultCampus = useMemo(() => {
    return campusList.filter((item) => item.value === campusId);
  }, [campusId, campusList]);

  return (
    <Modal
      title={
        <h3 className='flex gap-2 items-center text-[#052C65]'>
          <FontAwesomeIcon icon={faGraduationCap} />
          Thêm sinh viên
        </h3>
      }
      className='pt-2'
      centered
      open={isOpenAdd}
      onCancel={onCancelAdd}
      onOk={() => {
        Modal.confirm({
          centered: true,
          title: "Thêm sinh viên",
          content: "Bạn có muốn thêm sinh viên không ?",
          okButtonProps: { style: { backgroundColor: "#052c65" } },
          onOk: () => {
            form.submit();
          },
          footer: (_, { OkBtn, CancelBtn }) => (
            <>
              <CancelBtn />
              <OkBtn />
            </>
          ),
        });
      }}
      width={700}
      okText='Thêm Mới Sinh Viên'
      cancelText='Hủy'
      okButtonProps={{ style: { backgroundColor: "#052c65" } }}
    >
      <Form
        className='mt-4'
        layout='vertical'
        form={form}
        onFinish={handleCreateStudent}
      >
        <div className='row mt-2'>
          <div className='col-span-6'>
            <Form.Item
              label='Mã sinh viên'
              name='studentCode'
              rules={[
                {
                  required: true,
                  message: "Mã sinh viên không được trống !",
                },
              ]}
            >
              <Input placeholder='Nhập mã sinh viên' />
            </Form.Item>
          </div>
          <div className='col-span-6'>
            <Form.Item
              label='Tên sinh viên'
              name='studentName'
              rules={[{ required: true, message: "Tên không được trống !" }]}
            >
              <Input placeholder='Nhập họ và tên' />
            </Form.Item>
          </div>
          <div className='col-span-6'>
            <Form.Item label='Email' name='studentMail'>
              <Input placeholder='Nhập email' />
            </Form.Item>
          </div>
          <div className='col-span-6'>
            <Form.Item label='Số điện thoại' name='studentPhoneNumber'>
              <Input placeholder='Nhập số điện thoại' />
            </Form.Item>
          </div>
        </div>
        <div className='row mt-2'>
          <div className='col-span-6'>
            <Form.Item
              label='Cơ sở'
              name='campusId'
              initialValue={defaultCampus}
            >
              <Select
                placeholder='Chọn cơ sở'
                options={campusList}
                filterOption={filterOption}
                disabled
              />
            </Form.Item>
          </div>
        </div>
        <div className='row mt-2'>
          <div className='col-span-6'>
            <Form.Item
              label='Bộ môn'
              name='departmentId'
              rules={[{ required: true, message: "Bộ môn không được trống !" }]}
            >
              <Select
                placeholder='Chọn bộ môn'
                options={departmentList}
                filterOption={filterOption}
              />
            </Form.Item>
          </div>
        </div>
      </Form>
    </Modal>
  );
};
export default StudentAddModal;
