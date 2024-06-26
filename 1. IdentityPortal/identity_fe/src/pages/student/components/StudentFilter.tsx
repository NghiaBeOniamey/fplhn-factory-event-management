import { useGetAllListDepartment } from "#service/action/student.action";
import { Button, Form, Input, Select } from "antd";
import { useMemo } from "react";

const StudentFilter = ({
  onSubmit,
  onReset,
}: {
  onSubmit: (values: any) => void;
  onReset: () => void;
}) => {
  const [form] = Form.useForm();

  const { data: dataDepartment, isLoading: loadingDepartment } =
    useGetAllListDepartment();

  const departmentOptions = useMemo(() => {
    return dataDepartment?.data?.map((item) => {
      return {
        label: item?.name,
        value: item?.id,
      };
    });
  }, [dataDepartment]);

  return (
    <>
      <Form
        layout='vertical'
        className='grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-4'
        form={form}
      >
        <Form.Item label='Mã sinh viên' name='studentCode'>
          <Input className='max-w-md' placeholder='Nhập mã sinh viên' />
        </Form.Item>
        <Form.Item label='Họ và tên' name='studentName'>
          <Input className='max-w-md' placeholder='Nhập họ và tên' />
        </Form.Item>
        <Form.Item label='Email' name='studentMail'>
          <Input className='max-w-md' placeholder='Nhập email' />
        </Form.Item>
        <Form.Item label='Bộ môn' name='listDepartmentId'>
          <Select
            className='max-w-md'
            placeholder='Chọn bộ môn'
            allowClear
            mode='multiple'
            loading={loadingDepartment}
            maxCount={5}
            maxTagCount={2}
          >
            {departmentOptions?.map((item) => (
              <Select.Option key={item?.value} value={item?.value}>
                {item?.label}
              </Select.Option>
            ))}
          </Select>
        </Form.Item>
      </Form>
      <div className='flex justify-center gap-4'>
        <Button
          type='primary'
          className='bg-[#052C65] hover:bg-blue-700 text-white'
          title='Tìm kiếm sinh viên'
          onClick={async () => {
            const values = await form.validateFields();
            onSubmit(values);
          }}
        >
          Tìm kiếm
        </Button>
        <Button
          type='primary'
          className='bg-[#052C65] hover:bg-blue-700 text-white'
          onClick={() => {
            form.resetFields();
            onReset();
          }}
        >
          Xóa bộ lọc
        </Button>
      </div>
    </>
  );
};

export default StudentFilter;
