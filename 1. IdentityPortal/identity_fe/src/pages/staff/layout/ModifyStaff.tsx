import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { PERMISSIONS } from "#constant/index";
import { Campus, Department } from "#type/index.t";
import { InfoCircleOutlined } from "@ant-design/icons";
import { Button, Col, Form, Input, Modal, Row, Select } from "antd";
import { IoIosArrowBack } from "react-icons/io";
import { Link, useParams } from "react-router-dom";
import { useModifyStaff } from "../hooks/useModifyStaff";

const ModifyStaff = () => {
  const { id } = useParams();

  const {
    form,
    handleModifyStaff,
    listCampus,
    listDepartment,
    permissions,
    searchSelect,
    loading,
  } = useModifyStaff(id ? parseInt(id) : null);

  return (
    <div className='p-4'>
      {loading && <MagnifyingGlassLoading />}
      <div>
        <Link to='/management/manage-staff' className='p-1 mb-5'>
          <IoIosArrowBack className='mb-1 fill-[#052C65]' size={30} />
        </Link>
      </div>
      <div className='shadow-xl mt-5 p-4 rounded-lg bg-white'>
        <h2 className='my-5 text-[#052C65]'>
          {id ? "Cập Nhật Nhân Viên" : "Thêm Nhân Viên"}
        </h2>
        <div>
          <Form
            form={form}
            layout='vertical'
            initialValues={{
              modifier: "public",
            }}
            onFinish={() => {
              Modal.confirm({
                title: id ? "Cập nhật thông tin nhân viên" : "Thêm nhân viên",
                content: id
                  ? "Bạn có chắc chắn muốn cập nhật thông tin nhân viên này không?"
                  : "Bạn có chắc chắn muốn thêm nhân viên này không?",
                okText: "Xác nhận",
                cancelText: "Hủy",
                okButtonProps: {
                  className: "bg-[#052C65] border-none",
                },
                cancelButtonProps: {
                  className: "border-[#052C65]",
                },
                onOk: handleModifyStaff,
                centered: true,
              });
            }}
          >
            <Row gutter={[16, 16]}>
              <Col span={8}>
                <Form.Item
                  name='staffName'
                  label='Họ Và Tên'
                  rules={[
                    {
                      required: true,
                      message: "Vui lòng nhập họ và tên",
                    },
                  ]}
                >
                  <Input placeholder='Họ và tên' />
                </Form.Item>
              </Col>
              <Col span={8}>
                <Form.Item
                  name='staffCode'
                  label='Mã Nhân Viên'
                  rules={[
                    {
                      required: true,
                      message: "Vui lòng nhập mã nhân viên",
                    },
                  ]}
                >
                  <Input placeholder='Mã nhân viên' disabled={!!id} />
                </Form.Item>
              </Col>
              <Col span={8}>
                <Form.Item
                  name='phoneNumber'
                  label='Số Điện Thoại'
                  tooltip={{
                    title: "Không bắt buộc nhập số điện thoại",
                    icon: <InfoCircleOutlined />,
                  }}
                >
                  <Input placeholder='Số điện thoại' />
                </Form.Item>
              </Col>
            </Row>
            <Row gutter={[16, 16]}>
              <Col span={12}>
                <Form.Item
                  name='emailFpt'
                  label='Email FPT'
                  rules={[
                    {
                      required: true,
                      message: "Vui lòng nhập email FPT",
                    },
                    {
                      type: "email",
                      message: "Email không hợp lệ",
                    },
                    {
                      pattern: new RegExp(/^[a-zA-Z0-9._%+-]+@fpt.edu.vn$/),
                      message: "Email phải có định dạng @fpt.edu.vn",
                    },
                  ]}
                >
                  <Input placeholder='Email FPT' />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  name='emailFe'
                  label='Email FE'
                  rules={[
                    {
                      required: true,
                      message: "Vui lòng nhập email FE",
                    },
                    {
                      type: "email",
                      message: "Email không hợp lệ",
                    },
                    {
                      pattern: new RegExp(/^[a-zA-Z0-9._%+-]+@fe.edu.vn$/),
                      message: "Email phải có định dạng @fe.edu.vn",
                    },
                  ]}
                >
                  <Input placeholder='Email FE' />
                </Form.Item>
              </Col>
            </Row>
            <Row gutter={[16, 16]}>
              <Col span={12}>
                <Form.Item
                  name='departmentId'
                  label='Bộ Môn'
                  tooltip={{
                    title: "Không bắt buộc chọn bộ môn",
                    icon: <InfoCircleOutlined />,
                  }}
                >
                  <Select
                    placeholder='Chọn bộ môn'
                    showSearch
                    optionFilterProp='children'
                    filterOption={searchSelect}
                    options={listDepartment?.map((department: Department) => ({
                      value: department.id,
                      label: department.name,
                    }))}
                    allowClear
                  />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  name='campusId'
                  label='Cơ Sở'
                  tooltip={{
                    title: "Không bắt buộc chọn cơ sở",
                    icon: <InfoCircleOutlined />,
                  }}
                >
                  <Select
                    placeholder='Chọn cơ sở'
                    showSearch
                    optionFilterProp='children'
                    filterOption={searchSelect}
                    options={listCampus?.map((campus: Campus) => ({
                      value: campus.id,
                      label: campus.name,
                    }))}
                    allowClear
                    disabled={
                      !permissions?.includes(
                        PERMISSIONS.CHU_NHIEM_BO_MON ||
                          PERMISSIONS.ADMIN ||
                          PERMISSIONS.QUAN_LY_DAO_TAO ||
                          PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO
                      )
                    }
                  />
                </Form.Item>
              </Col>
            </Row>
            <Form.Item className='flex justify-end'>
              <Button
                type='primary'
                htmlType='submit'
                size='large'
                className='bg-[#052C65] border-none'
              >
                {id ? "Cập Nhật Thông Tin Nhân Viên" : "Thêm Nhân Viên"}
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default ModifyStaff;
