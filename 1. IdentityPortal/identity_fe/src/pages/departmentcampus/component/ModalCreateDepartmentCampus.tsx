import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import {
  useCreateDepartmentCampus,
  useGetListCampus,
  useListHeadDepartmentCampusApi,
} from "#service/action/department.action";
import { faFireBurner } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Form, Modal, Popconfirm, Select } from "antd";
import { useMemo, useState } from "react";
import { toast } from "react-toastify";

const ModalAddDepartmentCampus = ({
  isModalCreateMonHocTheoCoSoOpen,
  setIsModalCreateMonHocTheoCoSoOpen,
  departmentId,
  departmentName,
}: {
  isModalCreateMonHocTheoCoSoOpen: boolean;
  setIsModalCreateMonHocTheoCoSoOpen: (value: boolean) => void;
  departmentId: number;
  departmentName: string;
}) => {
  const [form] = Form.useForm();

  const { data: dataCampusFetching } = useGetListCampus();

  const { data: dataStaffFetching } = useListHeadDepartmentCampusApi();

  const { mutateAsync: createDepartmentCampus, isLoading: loading } =
    useCreateDepartmentCampus();

  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const dataCampus = useMemo(() => {
    return (
      dataCampusFetching?.data.map((item) => {
        return {
          value: item.campusId,
          label: item.campusName,
          key: item.campusId,
        };
      }) ?? []
    );
  }, [dataCampusFetching]);

  const dataStaff = useMemo(() => {
    return (
      dataStaffFetching?.data.map((item) => {
        return {
          value: item.staffId,
          label: item.staffName + " - " + item.staffCode,
          key: item.staffId,
        };
      }) ?? []
    );
  }, [dataStaffFetching]);

  const handleCloseCreateMonHocTheoCoSoOpen = () => {
    setIsModalCreateMonHocTheoCoSoOpen(false);
    form.resetFields();
    setIsButtonDisabled(false);
  };

  const handleFieldsChange = (allFields: any) => {
    const allFieldsFilled = Object.keys(allFields).every(
      (field) => !!allFields[field].value
    );
    setIsButtonDisabled(allFieldsFilled);
  };

  const handleSubmitCreateDepartmentCampus = async () => {
    try {
      const formValue = await form.getFieldsValue();
      const res = await createDepartmentCampus({
        departmentId,
        campusId: formValue.campusId,
        headDepartmentCampusId: formValue.headDepartmentCampusId,
      });
      if (res?.success) {
        toast.success(res?.message);
        form.resetFields();
        setIsButtonDisabled(false);
        setIsModalCreateMonHocTheoCoSoOpen(false);
      } else {
        toast.error(res?.message);
      }
    } catch (error) {
      toast.error(error?.response?.data?.message ?? INTERNAL_SERVER_ERROR);
    }
  };

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h5 className='p-3 text-[#052c65]'>
            <FontAwesomeIcon icon={faFireBurner} size='2xl' />
            <span className='ml-2 text-lg'>
              Tạo bộ môn theo cơ sở cho bộ môn:
            </span>
            <span className='text-[red] text-lg ml-2'>{departmentName}</span>
          </h5>
        }
        destroyOnClose
        open={isModalCreateMonHocTheoCoSoOpen}
        onCancel={handleCloseCreateMonHocTheoCoSoOpen}
        width={"40vw"}
        footer={[
          isButtonDisabled && (
            <Popconfirm
              placement='top'
              title='Thông báo'
              description='Bạn có muốn thêm không ?'
              okText='Có'
              cancelText='Không'
              onConfirm={() => handleSubmitCreateDepartmentCampus()}
            >
              <Button
                type='primary'
                className='bg-[#052c65] border-[#052c65] hover:bg-[#052c65] hover:border-[#052c65] m-2'
              >
                Xác nhận
              </Button>
            </Popconfirm>
          ),
        ]}
      >
        <Form
          className='mt-4'
          form={form}
          onFieldsChange={(allFields) => handleFieldsChange(allFields)}
        >
          <Form.Item
            name='campusId'
            label='Chọn Cơ sở'
            wrapperCol={{ span: 24 }}
            labelCol={{ span: 24 }}
            validateTrigger={"onBlur"}
            rules={[
              {
                required: true,
                message: "Tên cơ sở không được để trống!",
              },
            ]}
          >
            <Select
              size='large'
              showSearch
              style={{
                width: "100%",
              }}
              placeholder='Tìm kiếm cơ sở'
              optionFilterProp='children'
              filterOption={(input, option) =>
                (option?.label ?? "").includes(input)
              }
              filterSort={(optionA, optionB) =>
                (optionA?.label ?? "")
                  .toLowerCase()
                  .localeCompare((optionB?.label ?? "").toLowerCase())
              }
              options={dataCampus ? dataCampus : []}
            />
          </Form.Item>

          <Form.Item
            name='headDepartmentCampusId'
            label='Chọn chủ nhiệm bộ môn'
            wrapperCol={{ span: 24 }}
            labelCol={{ span: 24 }}
            validateTrigger={"onBlur"}
            rules={[
              {
                required: true,
                message: "Tên chủ nhiệm bộ môn không được để trống!",
              },
            ]}
          >
            <Select
              size='large'
              showSearch
              style={{
                width: "100%",
              }}
              placeholder='Tìm kiếm chủ nhiệm bộ môm'
              optionFilterProp='children'
              filterOption={(input, option) =>
                (option?.label ?? "").includes(input)
              }
              filterSort={(optionA, optionB) =>
                (optionA?.label ?? "")
                  .toLowerCase()
                  .localeCompare((optionB?.label ?? "").toLowerCase())
              }
              options={dataStaff ? dataStaff : []}
            />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default ModalAddDepartmentCampus;
